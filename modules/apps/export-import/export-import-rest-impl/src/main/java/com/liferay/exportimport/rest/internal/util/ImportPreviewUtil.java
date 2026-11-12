/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.rest.internal.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactoryUtil;
import com.liferay.exportimport.kernel.configuration.constants.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.lar.ExportImportGroup;
import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.exportimport.kernel.lar.UserIdStrategy;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalServiceUtil;
import com.liferay.exportimport.kernel.service.ExportImportLocalServiceUtil;
import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.exportimport.rest.dto.v1_0.AssetLibrary;
import com.liferay.exportimport.rest.dto.v1_0.ImportPreview;
import com.liferay.exportimport.rest.dto.v1_0.PortletDataHandler;
import com.liferay.exportimport.rest.dto.v1_0.Site;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipReaderFactory;

import jakarta.ws.rs.BadRequestException;

import java.io.File;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author Daniel Raposo
 * @author Jaime León
 */
public class ImportPreviewUtil {

	public static ImportPreview getImportPreview(
			String authorName, File file, Locale locale, String portletId,
			Group scopeGroup, String uploadFileName, long uploadFileSize,
			long userId)
		throws Exception {

		ZipReaderFactory zipReaderFactory = _zipReaderFactorySnapshot.get();

		try (ZipReader zipReader = zipReaderFactory.getZipReader(file)) {
			PortletDataContext portletDataContext =
				PortletDataContextFactoryUtil.createImportPortletDataContext(
					scopeGroup.getCompanyId(), scopeGroup.getGroupId(),
					new HashMap<>(),
					ExportImportHelperUtil.getUserIdStrategy(
						userId, UserIdStrategy.CURRENT_USER_ID),
					zipReader);

			ManifestSummary manifestSummary =
				ExportImportHelperUtil.getManifestSummary(portletDataContext);

			boolean portletScoped = !Validator.isBlank(portletId);

			List<Portlet> portlets = manifestSummary.getDataPortlets();

			if (portletScoped) {
				portlets = ListUtil.fromArray(
					PortletLocalServiceUtil.getPortletById(
						scopeGroup.getCompanyId(), portletId));
			}

			PortletDataHandler[] importPortletDataHandlers =
				PortletDataHandlerUtil.getImportPortletDataHandlers(
					true, scopeGroup, locale, manifestSummary, portlets,
					portletScoped);

			List<ExportImportGroup> exportImportGroups = _getExportImportGroups(
				scopeGroup.getCompanyId(), portletDataContext);

			return new ImportPreview() {
				{
					setAdditionCount(
						() -> PortletDataHandlerUtil.getAdditionCount(
							importPortletDataHandlers));
					setAssetLibraries(
						() -> _getAssetLibraries(
							scopeGroup.getCompanyId(), exportImportGroups));
					setAuthor(() -> authorName);
					setDeletionCount(
						() -> PortletDataHandlerUtil.getDeletionCount(
							importPortletDataHandlers));
					setExportDate(manifestSummary::getExportDate);
					setFileName(() -> uploadFileName);
					setFileSize(() -> uploadFileSize);
					setPortletDataHandlers(() -> importPortletDataHandlers);
					setSites(
						() -> _getSites(
							scopeGroup.getCompanyId(), exportImportGroups));
				}
			};
		}
	}

	public static String getMessage(JSONObject jsonObject) {
		JSONArray messageListItemsJSONArray = jsonObject.getJSONArray(
			"messageListItems");

		if (JSONUtil.isEmpty(messageListItemsJSONArray)) {
			return jsonObject.getString("message");
		}

		StringBundler sb = new StringBundler(
			(messageListItemsJSONArray.length() * 10) + 1);

		sb.append(jsonObject.getString("message"));

		for (int i = 0; i < messageListItemsJSONArray.length(); i++) {
			JSONObject messageListItemJSONObject =
				messageListItemsJSONArray.getJSONObject(i);

			sb.append(StringPool.NEW_LINE);
			sb.append(messageListItemJSONObject.getString("type"));

			String site = messageListItemJSONObject.getString("site");

			if (Validator.isNotNull(site)) {
				sb.append(StringPool.SPACE);
				sb.append(site);
			}

			sb.append(StringPool.COLON);
			sb.append(StringPool.SPACE);
			sb.append(messageListItemJSONObject.getString("name"));

			String info = messageListItemJSONObject.getString("info");

			if (Validator.isNull(info)) {
				continue;
			}

			sb.append(StringPool.SPACE);
			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(info);
			sb.append(StringPool.CLOSE_PARENTHESIS);
		}

		return sb.toString();
	}

	public static ImportPreview toImportPreview(String json) throws Exception {
		return _objectMapper.readValue(json, ImportPreview.class);
	}

	public static String toJSON(ImportPreview importPreview) throws Exception {
		return _objectMapper.writeValueAsString(importPreview);
	}

	public static void validateImportFile(
			File file, long groupId, Locale locale, long plid, String portletId,
			TimeZone timeZone, long userId)
		throws Exception {

		ExportImportConfiguration exportImportConfiguration =
			_addExportImportConfiguration(
				groupId, locale, plid, portletId, timeZone, userId);

		try {
			if (Validator.isBlank(portletId)) {
				ExportImportLocalServiceUtil.validateImportLayoutsFile(
					exportImportConfiguration, file);
			}
			else {
				ExportImportLocalServiceUtil.validateImportPortletInfo(
					exportImportConfiguration, file);
			}
		}
		catch (PortalException portalException) {
			throw new BadRequestException(
				getMessage(
					StagingUtil.getExceptionMessagesJSONObject(
						locale, portalException, exportImportConfiguration)),
				portalException);
		}
		finally {
			ExportImportConfigurationLocalServiceUtil.
				deleteExportImportConfiguration(exportImportConfiguration);
		}
	}

	private static ExportImportConfiguration _addExportImportConfiguration(
			long groupId, Locale locale, long plid, String portletId,
			TimeZone timeZone, long userId)
		throws Exception {

		if (Validator.isBlank(portletId)) {
			return ExportImportConfigurationLocalServiceUtil.
				addExportImportConfiguration(
					userId, groupId, null, null,
					ExportImportConfigurationConstants.TYPE_IMPORT_LAYOUT,
					ExportImportConfigurationSettingsMapFactoryUtil.
						buildImportLayoutSettingsMap(
							userId, groupId, false, null, new HashMap<>(),
							locale, timeZone),
					new ServiceContext());
		}

		return ExportImportConfigurationLocalServiceUtil.
			addExportImportConfiguration(
				userId, groupId, null, null,
				ExportImportConfigurationConstants.TYPE_IMPORT_PORTLET,
				ExportImportConfigurationSettingsMapFactoryUtil.
					buildImportPortletSettingsMap(
						userId, plid, groupId, portletId, new HashMap<>(),
						locale, timeZone),
				new ServiceContext());
	}

	private static AssetLibrary[] _getAssetLibraries(
		long companyId, List<ExportImportGroup> exportImportGroups) {

		if (!FeatureFlagManagerUtil.isEnabled(companyId, "LPD-41307")) {
			return new AssetLibrary[0];
		}

		return TransformUtil.transformToArray(
			ListUtil.filter(exportImportGroups, ExportImportGroup::isDepot),
			exportImportGroup -> new AssetLibrary() {
				{
					setDescriptiveName(exportImportGroup::getDescriptiveName);
					setExistsInInstance(
						() -> _hasMatchingGroup(companyId, exportImportGroup));
					setExternalReferenceCode(
						exportImportGroup::getExternalReferenceCode);
					setType(
						() -> AssetLibraryUtil.toAssetLibraryType(
							exportImportGroup.getDepotType()));
				}
			},
			AssetLibrary.class);
	}

	private static List<ExportImportGroup> _getExportImportGroups(
			long companyId, PortletDataContext portletDataContext)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(companyId, "LPD-41307") &&
			!FeatureFlagManagerUtil.isEnabled(companyId, "LPD-85946")) {

			return Collections.emptyList();
		}

		return ExportImportHelperUtil.getExportImportGroups(portletDataContext);
	}

	private static Site[] _getSites(
		long companyId, List<ExportImportGroup> exportImportGroups) {

		if (!FeatureFlagManagerUtil.isEnabled(companyId, "LPD-85946")) {
			return new Site[0];
		}

		return TransformUtil.transformToArray(
			ListUtil.filter(
				exportImportGroups,
				exportImportGroup -> !exportImportGroup.isDepot()),
			exportImportGroup -> new Site() {
				{
					setChildSitesCount(exportImportGroup::getChildGroupsCount);
					setDescriptiveName(exportImportGroup::getDescriptiveName);
					setExistsInInstance(
						() -> _hasMatchingGroup(companyId, exportImportGroup));
					setExternalReferenceCode(
						exportImportGroup::getExternalReferenceCode);
					setPath(exportImportGroup::getPath);
				}
			},
			Site.class);
	}

	private static boolean _hasMatchingGroup(
		long companyId, ExportImportGroup exportImportGroup) {

		Group group = GroupLocalServiceUtil.fetchGroupByExternalReferenceCode(
			exportImportGroup.getExternalReferenceCode(), companyId);

		if ((group != null) &&
			ExportImportHelperUtil.isGroupMatching(exportImportGroup, group)) {

			return true;
		}

		return false;
	}

	private static final ObjectMapper _objectMapper = new ObjectMapper() {
		{
			configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			setAnnotationIntrospector(
				new JacksonAnnotationIntrospector() {

					@Override
					public JsonProperty.Access findPropertyAccess(
						Annotated annotated) {

						return JsonProperty.Access.AUTO;
					}

				});
			setFilterProvider(
				new SimpleFilterProvider() {
					{
						addFilter(
							"Liferay.Vulcan",
							SimpleBeanPropertyFilter.serializeAll());
					}
				});
			setSerializationInclusion(JsonInclude.Include.NON_NULL);
		}
	};

	private static final Snapshot<ZipReaderFactory> _zipReaderFactorySnapshot =
		new Snapshot<>(ImportPreviewUtil.class, ZipReaderFactory.class);

}