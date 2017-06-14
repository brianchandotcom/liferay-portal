/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.exportimport.lar;

import aQute.bnd.annotation.ProviderType;

import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.exportimport.kernel.lar.DefaultConfigurationPortletDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportDateUtil;
import com.liferay.exportimport.kernel.lar.ExportImportHelper;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.MissingReference;
import com.liferay.exportimport.kernel.lar.MissingReferences;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.exportimport.kernel.lar.UserIdStrategy;
import com.liferay.exportimport.portlet.data.handler.provider.PortletDataHandlerProvider;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.StagedGroupedModel;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.xml.SecureXMLFactoryProviderUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.SystemEventLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.DateRange;
import com.liferay.portal.kernel.util.Digester;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.ElementHandler;
import com.liferay.portal.kernel.xml.ElementProcessor;
import com.liferay.portal.kernel.zip.ZipReader;
import com.liferay.portal.kernel.zip.ZipReaderFactoryUtil;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

/**
 * @author Zsolt Berentey
 * @author Levente Hudák
 * @author Julio Camarero
 * @author Mate Thurzo
 */
@Component(immediate = true)
@ProviderType
public class ExportImportHelperImpl implements ExportImportHelper {

	@Override
	public long[] getAllLayoutIds(long groupId, boolean privateLayout) {
		List<Layout> layouts = _layoutLocalService.getLayouts(
			groupId, privateLayout);

		return getLayoutIds(layouts);
	}

	@Override
	public Map<Long, Boolean> getAllLayoutIdsMap(
		long groupId, boolean privateLayout) {

		List<Layout> layouts = _layoutLocalService.getLayouts(
			groupId, privateLayout, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

		Map<Long, Boolean> layoutIdMap = new HashMap<>();

		for (Layout layout : layouts) {
			layoutIdMap.put(layout.getPlid(), true);
		}

		return layoutIdMap;
	}

	/**
	 * @deprecated As of 3.0.0, moved to {@link
	 *             ExportImportDateUtil#getCalendar(PortletRequest, String,
	 *             boolean)}
	 */
	@Deprecated
	@Override
	public Calendar getCalendar(
		PortletRequest portletRequest, String paramPrefix,
		boolean timeZoneSensitive) {

		return ExportImportDateUtil.getCalendar(
			portletRequest, paramPrefix, timeZoneSensitive);
	}

	@Override
	public List<Portlet> getDataSiteLevelPortlets(long companyId)
		throws Exception {

		return getDataSiteLevelPortlets(companyId, false);
	}

	@Override
	public List<Portlet> getDataSiteLevelPortlets(
			long companyId, boolean excludeDataAlwaysStaged)
		throws Exception {

		List<Portlet> dataSiteLevelPortlets = new ArrayList<>();

		Map<Integer, List<Portlet>> rankedPortletsMap = new TreeMap<>();

		List<Portlet> portlets = _portletLocalService.getPortlets(companyId);

		for (Portlet portlet : portlets) {
			if (!portlet.isActive()) {
				continue;
			}

			PortletDataHandler portletDataHandler =
				portlet.getPortletDataHandlerInstance();

			if ((portletDataHandler == null) ||
				!portletDataHandler.isDataSiteLevel() ||
				(excludeDataAlwaysStaged &&
				 portletDataHandler.isDataAlwaysStaged())) {

				continue;
			}

			List<Portlet> rankedPortlets = rankedPortletsMap.get(
				portletDataHandler.getRank());

			if (rankedPortlets == null) {
				rankedPortlets = new ArrayList<>();
			}

			rankedPortlets.add(portlet);

			rankedPortletsMap.put(portletDataHandler.getRank(), rankedPortlets);
		}

		for (List<Portlet> rankedPortlets : rankedPortletsMap.values()) {
			dataSiteLevelPortlets.addAll(rankedPortlets);
		}

		return dataSiteLevelPortlets;
	}

	/**
	 * @deprecated As of 3.0.0, moved to {@link
	 *             ExportImportDateUtil#getDateRange(PortletRequest, long,
	 *             boolean, long, String, String)}
	 */
	@Deprecated
	@Override
	public DateRange getDateRange(
			PortletRequest portletRequest, long groupId, boolean privateLayout,
			long plid, String portletId, String defaultRange)
		throws Exception {

		return ExportImportDateUtil.getDateRange(
			portletRequest, groupId, privateLayout, plid, portletId,
			defaultRange);
	}

	/**
	 * @deprecated As of 3.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public Layout getExportableLayout(ThemeDisplay themeDisplay)
		throws PortalException {

		return themeDisplay.getLayout();
	}

	@Override
	public String getExportableRootPortletId(long companyId, String portletId)
		throws Exception {

		Portlet portlet = _portletLocalService.getPortletById(
			companyId, portletId);

		if ((portlet == null) || portlet.isUndeployedPortlet()) {
			return null;
		}

		return PortletIdCodec.decodePortletName(portletId);
	}

	/**
	 * @deprecated As of 3.0.0, replaced by {@link
	 *             #getExportPortletControlsMap(long, String, Map)}
	 */
	@Deprecated
	@Override
	public boolean[] getExportPortletControls(
			long companyId, String portletId,
			Map<String, String[]> parameterMap)
		throws Exception {

		return getExportPortletControls(
			companyId, portletId, parameterMap, "layout-set");
	}

	/**
	 * @deprecated As of 3.0.0, replaced by {@link
	 *             #getExportPortletControlsMap(long, String, Map, String)}
	 */
	@Deprecated
	@Override
	public boolean[] getExportPortletControls(
			long companyId, String portletId,
			Map<String, String[]> parameterMap, String type)
		throws Exception {

		Map<String, Boolean> exportPortletControlsMap =
			getExportPortletControlsMap(
				companyId, portletId, parameterMap, type);

		return new boolean[] {
			exportPortletControlsMap.get(
				PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS),
			exportPortletControlsMap.get(PortletDataHandlerKeys.PORTLET_DATA),
			exportPortletControlsMap.get(PortletDataHandlerKeys.PORTLET_SETUP),
			exportPortletControlsMap.get(
				PortletDataHandlerKeys.PORTLET_USER_PREFERENCES)
		};
	}

	@Override
	public Map<String, Boolean> getExportPortletControlsMap(
			long companyId, String portletId,
			Map<String, String[]> parameterMap)
		throws Exception {

		return getExportPortletControlsMap(
			companyId, portletId, parameterMap, "layout-set");
	}

	@Override
	public Map<String, Boolean> getExportPortletControlsMap(
			long companyId, String portletId,
			Map<String, String[]> parameterMap, String type)
		throws Exception {

		Map<String, Boolean> exportPortletControlsMap = new HashMap<>();

		boolean exportPortletData = getExportPortletData(
			companyId, portletId, parameterMap);

		exportPortletControlsMap.put(
			PortletDataHandlerKeys.PORTLET_DATA, exportPortletData);

		exportPortletControlsMap.putAll(
			getExportPortletSetupControlsMap(
				companyId, portletId, parameterMap, type));

		return exportPortletControlsMap;
	}

	/**
	 * @deprecated As of 3.0.0, replaced by {@link
	 *             #getImportPortletControlsMap(long, String, Map, Element,
	 *             ManifestSummary)}
	 */
	@Deprecated
	@Override
	public boolean[] getImportPortletControls(
			long companyId, String portletId,
			Map<String, String[]> parameterMap, Element portletDataElement)
		throws Exception {

		return getImportPortletControls(
			companyId, portletId, parameterMap, portletDataElement, null);
	}

	/**
	 * @deprecated As of 3.0.0, replaced by {@link
	 *             #getImportPortletControlsMap(long, String, Map, Element,
	 *             ManifestSummary)}
	 */
	@Deprecated
	@Override
	public boolean[] getImportPortletControls(
			long companyId, String portletId,
			Map<String, String[]> parameterMap, Element portletDataElement,
			ManifestSummary manifestSummary)
		throws Exception {

		Map<String, Boolean> importPortletControlsMap =
			getImportPortletControlsMap(
				companyId, portletId, parameterMap, portletDataElement,
				manifestSummary);

		return new boolean[] {
			importPortletControlsMap.get(
				PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS),
			importPortletControlsMap.get(PortletDataHandlerKeys.PORTLET_DATA),
			importPortletControlsMap.get(PortletDataHandlerKeys.PORTLET_SETUP),
			importPortletControlsMap.get(
				PortletDataHandlerKeys.PORTLET_USER_PREFERENCES)
		};
	}

	@Override
	public Map<String, Boolean> getImportPortletControlsMap(
			long companyId, String portletId,
			Map<String, String[]> parameterMap, Element portletDataElement,
			ManifestSummary manifestSummary)
		throws Exception {

		boolean importCurPortletData = getImportPortletData(
			companyId, portletId, parameterMap, portletDataElement);

		Map<String, Boolean> importPortletControlsMap = new HashMap<>();

		importPortletControlsMap.put(
			PortletDataHandlerKeys.PORTLET_DATA, importCurPortletData);

		importPortletControlsMap.putAll(
			getImportPortletSetupControlsMap(
				companyId, portletId, parameterMap, manifestSummary));

		return importPortletControlsMap;
	}

	@Override
	public Map<Long, Boolean> getLayoutIdMap(PortletRequest portletRequest)
		throws PortalException {

		Map<Long, Boolean> layoutIdMap = new LinkedHashMap<>();

		String layoutIdsJSON = GetterUtil.getString(
			portletRequest.getAttribute("layoutIdMap"));

		if (Validator.isNull(layoutIdsJSON)) {
			return layoutIdMap;
		}

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(layoutIdsJSON);

		for (int i = 0; i < jsonArray.length(); ++i) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			long plid = jsonObject.getLong("plid");
			boolean includeChildren = jsonObject.getBoolean("includeChildren");

			layoutIdMap.put(plid, includeChildren);
		}

		return layoutIdMap;
	}

	@Override
	public long[] getLayoutIds(List<Layout> layouts) {
		long[] layoutIds = new long[layouts.size()];

		for (int i = 0; i < layouts.size(); i++) {
			Layout layout = layouts.get(i);

			layoutIds[i] = layout.getLayoutId();
		}

		return layoutIds;
	}

	@Override
	public long[] getLayoutIds(Map<Long, Boolean> layoutIdMap)
		throws PortalException {

		return getLayoutIds(layoutIdMap, GroupConstants.DEFAULT_LIVE_GROUP_ID);
	}

	@Override
	public long[] getLayoutIds(
			Map<Long, Boolean> layoutIdMap, long targetGroupId)
		throws PortalException {

		if (MapUtil.isEmpty(layoutIdMap)) {
			return new long[0];
		}

		List<Layout> layouts = new ArrayList<>();

		for (Map.Entry<Long, Boolean> entry : layoutIdMap.entrySet()) {
			long plid = GetterUtil.getLong(String.valueOf(entry.getKey()));
			boolean includeChildren = entry.getValue();

			Layout layout = _layoutLocalService.getLayout(plid);

			if (!layouts.contains(layout)) {
				layouts.add(layout);
			}

			List<Layout> parentLayouts = Collections.emptyList();

			if (targetGroupId != GroupConstants.DEFAULT_LIVE_GROUP_ID) {
				parentLayouts = getMissingParentLayouts(layout, targetGroupId);
			}

			for (Layout parentLayout : parentLayouts) {
				if (!layouts.contains(parentLayout)) {
					layouts.add(parentLayout);
				}
			}

			if (includeChildren) {
				for (Layout childLayout : layout.getAllChildren()) {
					if (!layouts.contains(childLayout)) {
						layouts.add(childLayout);
					}
				}
			}
		}

		return getLayoutIds(layouts);
	}

	@Override
	public long[] getLayoutIds(PortletRequest portletRequest)
		throws PortalException {

		return getLayoutIds(
			getLayoutIdMap(portletRequest),
			GroupConstants.DEFAULT_LIVE_GROUP_ID);
	}

	@Override
	public long[] getLayoutIds(
			PortletRequest portletRequest, long targetGroupId)
		throws PortalException {

		return getLayoutIds(getLayoutIdMap(portletRequest), targetGroupId);
	}

	@Override
	public ZipWriter getLayoutSetZipWriter(long groupId) {
		StringBundler sb = new StringBundler(4);

		sb.append(groupId);
		sb.append(StringPool.DASH);
		sb.append(Time.getTimestamp());
		sb.append(".lar");

		return getZipWriter(sb.toString());
	}

	/**
	 * @deprecated As of 3.0.0, replaced by {@link
	 *             #getManifestSummary(PortletDataContext)}
	 */
	@Deprecated
	@Override
	public ManifestSummary getManifestSummary(
			long userId, long groupId, Map<String, String[]> parameterMap,
			File file)
		throws Exception {

		final Group group = _groupLocalService.getGroup(groupId);
		String userIdStrategy = MapUtil.getString(
			parameterMap, PortletDataHandlerKeys.USER_ID_STRATEGY);
		ZipReader zipReader = ZipReaderFactoryUtil.getZipReader(file);

		PortletDataContext portletDataContext =
			PortletDataContextFactoryUtil.createImportPortletDataContext(
				group.getCompanyId(), groupId, parameterMap,
				getUserIdStrategy(userId, userIdStrategy), zipReader);

		try {
			return getManifestSummary(portletDataContext);
		}
		finally {
			zipReader.close();
		}
	}

	@Override
	public ManifestSummary getManifestSummary(
			long userId, long groupId, Map<String, String[]> parameterMap,
			FileEntry fileEntry)
		throws Exception {

		File file = FileUtil.createTempFile("lar");
		InputStream inputStream = _dlFileEntryLocalService.getFileAsStream(
			fileEntry.getFileEntryId(), fileEntry.getVersion(), false);
		ZipReader zipReader = null;

		ManifestSummary manifestSummary = null;

		try {
			FileUtil.write(file, inputStream);

			Group group = _groupLocalService.getGroup(groupId);
			String userIdStrategy = MapUtil.getString(
				parameterMap, PortletDataHandlerKeys.USER_ID_STRATEGY);

			zipReader = ZipReaderFactoryUtil.getZipReader(file);

			PortletDataContext portletDataContext =
				PortletDataContextFactoryUtil.createImportPortletDataContext(
					group.getCompanyId(), groupId, parameterMap,
					getUserIdStrategy(userId, userIdStrategy), zipReader);

			manifestSummary = getManifestSummary(portletDataContext);
		}
		finally {
			StreamUtil.cleanUp(inputStream);

			if (zipReader != null) {
				zipReader.close();
			}

			FileUtil.delete(file);
		}

		return manifestSummary;
	}

	@Override
	public ManifestSummary getManifestSummary(
			PortletDataContext portletDataContext)
		throws Exception {

		XMLReader xmlReader = SecureXMLFactoryProviderUtil.newXMLReader();

		Group group = _groupLocalService.getGroup(
			portletDataContext.getGroupId());
		ManifestSummary manifestSummary = new ManifestSummary();

		ElementHandler elementHandler = new ElementHandler(
			new ManifestSummaryElementProcessor(group, manifestSummary),
			new String[] {"header", "portlet", "staged-model"});

		xmlReader.setContentHandler(elementHandler);

		xmlReader.parse(
			new InputSource(
				portletDataContext.getZipEntryAsInputStream("/manifest.xml")));

		return manifestSummary;
	}

	/**
	 * @see com.liferay.exportimport.kernel.backgroundtask.LayoutRemoteStagingBackgroundTaskExecutor#getMissingRemoteParentLayouts(
	 *      com.liferay.portal.kernel.security.auth.HttpPrincipal, Layout, long)
	 */
	@Override
	public List<Layout> getMissingParentLayouts(Layout layout, long liveGroupId)
		throws PortalException {

		List<Layout> missingParentLayouts = new ArrayList<>();

		long parentLayoutId = layout.getParentLayoutId();

		Layout parentLayout = null;

		while (parentLayoutId > 0) {
			parentLayout = _layoutLocalService.getLayout(
				layout.getGroupId(), layout.isPrivateLayout(), parentLayoutId);

			if (_layoutLocalService.hasLayout(
					parentLayout.getUuid(), liveGroupId,
					parentLayout.isPrivateLayout())) {

				// If one parent is found, all others are assumed to exist

				break;
			}
			else {
				missingParentLayouts.add(parentLayout);

				parentLayoutId = parentLayout.getParentLayoutId();
			}
		}

		return missingParentLayouts;
	}

	@Override
	public long getModelDeletionCount(
			final PortletDataContext portletDataContext,
			final StagedModelType stagedModelType)
		throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			_systemEventLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					doAddCriteria(
						portletDataContext, stagedModelType, dynamicQuery);
				}

			});
		actionableDynamicQuery.setCompanyId(portletDataContext.getCompanyId());

		return actionableDynamicQuery.performCount();
	}

	@Override
	public ZipWriter getPortletZipWriter(String portletId) {
		StringBundler sb = new StringBundler(4);

		sb.append(portletId);
		sb.append(StringPool.DASH);
		sb.append(Time.getTimestamp());
		sb.append(".lar");

		return getZipWriter(sb.toString());
	}

	@Override
	public String getSelectedLayoutsJSON(
		long groupId, boolean privateLayout, String selectedNodes) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<Layout> layouts = _layoutLocalService.getLayouts(
			groupId, privateLayout, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

		for (Layout layout : layouts) {
			populateLayoutsJSON(
				jsonArray, layout, StringUtil.split(selectedNodes, 0L));
		}

		return jsonArray.toString();
	}

	@Override
	public FileEntry getTempFileEntry(
			long groupId, long userId, String folderName)
		throws PortalException {

		String[] tempFileNames = _layoutService.getTempFileNames(
			groupId, folderName);

		if (tempFileNames.length == 0) {
			return null;
		}

		return TempFileEntryUtil.getTempFileEntry(
			groupId, userId,
			DigesterUtil.digestHex(Digester.SHA_256, folderName),
			tempFileNames[0]);
	}

	@Override
	public UserIdStrategy getUserIdStrategy(long userId, String userIdStrategy)
		throws PortalException {

		User user = _userLocalService.getUserById(userId);

		if (UserIdStrategy.ALWAYS_CURRENT_USER_ID.equals(userIdStrategy)) {
			return new AlwaysCurrentUserIdStrategy(user);
		}

		return new CurrentUserIdStrategy(user);
	}

	@Override
	public boolean isReferenceWithinExportScope(
		PortletDataContext portletDataContext, StagedModel stagedModel) {

		if (!(stagedModel instanceof StagedGroupedModel)) {
			return true;
		}

		StagedGroupedModel stagedGroupedModel = (StagedGroupedModel)stagedModel;

		if (portletDataContext.getGroupId() ==
				stagedGroupedModel.getGroupId()) {

			return true;
		}

		Group group = null;

		try {
			group = _groupLocalService.getGroup(
				stagedGroupedModel.getGroupId());
		}
		catch (Exception e) {
			return false;
		}

		String className = group.getClassName();

		if (className.equals(Layout.class.getName())) {
			Layout scopeLayout = null;

			try {
				scopeLayout = _layoutLocalService.getLayout(group.getClassPK());
			}
			catch (Exception e) {
				return false;
			}

			if (scopeLayout.getGroupId() == portletDataContext.getGroupId()) {
				return true;
			}
		}

		return false;
	}

	/**
	 * @deprecated As of 3.0.0, replaced by {@link
	 *             com.liferay.exportimport.content.processor.ExportImportContentProcessor#replaceExportContentReferences(
	 *             PortletDataContext, StagedModel, String, boolean, boolean)}
	 */
	@Deprecated
	@Override
	public String replaceExportContentReferences(
			PortletDataContext portletDataContext,
			StagedModel entityStagedModel, Element entityElement,
			String content, boolean exportReferencedContent)
		throws Exception {

		return content;
	}

	/**
	 * @deprecated As of 3.0.0, replaced by {@link
	 *             com.liferay.exportimport.content.processor.ExportImportContentProcessor#replaceExportContentReferences(
	 *             PortletDataContext, StagedModel, String, boolean, boolean)}
	 */
	@Deprecated
	@Override
	public String replaceExportContentReferences(
			PortletDataContext portletDataContext,
			StagedModel entityStagedModel, String content,
			boolean exportReferencedContent)
		throws Exception {

		return content;
	}

	/**
	 * @deprecated As of 3.0.0, replaced by {@link
	 *             com.liferay.exportimport.content.processor.ExportImportContentProcessor#replaceExportContentReferences(
	 *             PortletDataContext, StagedModel, String, boolean, boolean)}
	 */
	@Deprecated
	@Override
	public String replaceExportContentReferences(
			PortletDataContext portletDataContext,
			StagedModel entityStagedModel, String content,
			boolean exportReferencedContent, boolean escapeContent)
		throws Exception {

		return content;
	}

	/**
	 * @deprecated As of 3.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public String replaceExportDLReferences(
			PortletDataContext portletDataContext,
			StagedModel entityStagedModel, Element entityElement,
			String content, boolean exportReferencedContent)
		throws Exception {

		return content;
	}

	/**
	 * @deprecated As of 3.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public String replaceExportDLReferences(
			PortletDataContext portletDataContext,
			StagedModel entityStagedModel, String content,
			boolean exportReferencedContent)
		throws Exception {

		return content;
	}

	/**
	 * @deprecated As of 3.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public String replaceExportLayoutReferences(
			PortletDataContext portletDataContext, String content)
		throws Exception {

		return content;
	}

	/**
	 * @deprecated As of 3.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public String replaceExportLayoutReferences(
			PortletDataContext portletDataContext, String content,
			boolean exportReferencedContent)
		throws Exception {

		return content;
	}

	/**
	 * @deprecated As of 3.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public String replaceExportLinksToLayouts(
			PortletDataContext portletDataContext,
			StagedModel entityStagedModel, Element entityElement,
			String content, boolean exportReferencedContent)
		throws Exception {

		return content;
	}

	/**
	 * @deprecated As of 3.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public String replaceExportLinksToLayouts(
			PortletDataContext portletDataContext,
			StagedModel entityStagedModel, String content)
		throws Exception {

		return content;
	}

	/**
	 * @deprecated As of 3.0.0, replaced by {@link
	 *             com.liferay.exportimport.content.processor.ExportImportContentProcessor#replaceImportContentReferences(
	 *             PortletDataContext, StagedModel, String)}
	 */
	@Deprecated
	@Override
	public String replaceImportContentReferences(
			PortletDataContext portletDataContext, Element entityElement,
			String content, boolean importReferencedContent)
		throws Exception {

		return content;
	}

	/**
	 * @deprecated As of 3.0.0, replaced by {@link
	 *             com.liferay.exportimport.content.processor.ExportImportContentProcessor#replaceImportContentReferences(
	 *             PortletDataContext, StagedModel, String)}
	 */
	@Deprecated
	@Override
	public String replaceImportContentReferences(
			PortletDataContext portletDataContext,
			StagedModel entityStagedModel, String content)
		throws Exception {

		return content;
	}

	/**
	 * @deprecated As of 3.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public String replaceImportDLReferences(
			PortletDataContext portletDataContext, Element entityElement,
			String content, boolean importReferencedContent)
		throws Exception {

		return content;
	}

	/**
	 * @deprecated As of 3.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public String replaceImportDLReferences(
			PortletDataContext portletDataContext,
			StagedModel entityStagedModel, String content)
		throws Exception {

		return content;
	}

	/**
	 * @deprecated As of 3.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public String replaceImportLayoutReferences(
			PortletDataContext portletDataContext, String content)
		throws Exception {

		return content;
	}

	/**
	 * @deprecated As of 3.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public String replaceImportLayoutReferences(
			PortletDataContext portletDataContext, String content,
			boolean importReferencedContent)
		throws Exception {

		return content;
	}

	/**
	 * @deprecated As of 3.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public String replaceImportLinksToLayouts(
			PortletDataContext portletDataContext, String content)
		throws Exception {

		return content;
	}

	/**
	 * @deprecated As of 3.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public String replaceImportLinksToLayouts(
			PortletDataContext portletDataContext, String content,
			boolean importReferencedContent)
		throws Exception {

		return content;
	}

	/**
	 * @deprecated As of 3.0.0, see {@link
	 *             DefaultConfigurationPortletDataHandler#updateExportPortletPreferencesClassPKs(
	 *             PortletDataContext, Portlet, PortletPreferences, String,
	 *             String)}
	 */
	@Deprecated
	@Override
	public void updateExportPortletPreferencesClassPKs(
			PortletDataContext portletDataContext, Portlet portlet,
			PortletPreferences portletPreferences, String key, String className)
		throws Exception {
	}

	/**
	 * @deprecated As of 3.0.0, replaced by {@link
	 *             #updateExportPortletPreferencesClassPKs(PortletDataContext,
	 *             Portlet, PortletPreferences, String, String)}
	 */
	@Deprecated
	@Override
	public void updateExportPortletPreferencesClassPKs(
			PortletDataContext portletDataContext, Portlet portlet,
			PortletPreferences portletPreferences, String key, String className,
			Element rootElement)
		throws Exception {

		updateExportPortletPreferencesClassPKs(
			portletDataContext, portlet, portletPreferences, key, className);
	}

	/**
	 * @deprecated As of 3.0.0, see {@link
	 *             DefaultConfigurationPortletDataHandler#updateImportPortletPreferencesClassPKs(
	 *             PortletDataContext, PortletPreferences, String, Class, long)}
	 */
	@Deprecated
	@Override
	public void updateImportPortletPreferencesClassPKs(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences, String key, Class<?> clazz,
			long companyGroupId)
		throws Exception {
	}

	/**
	 * @deprecated As of 3.0.0, replaced by {@link
	 *             #validateMissingReferences(PortletDataContext)}
	 */
	@Deprecated
	@Override
	public MissingReferences validateMissingReferences(
			long userId, long groupId, Map<String, String[]> parameterMap,
			File file)
		throws Exception {

		Group group = _groupLocalService.getGroup(groupId);
		String userIdStrategy = MapUtil.getString(
			parameterMap, PortletDataHandlerKeys.USER_ID_STRATEGY);
		ZipReader zipReader = ZipReaderFactoryUtil.getZipReader(file);

		PortletDataContext portletDataContext =
			PortletDataContextFactoryUtil.createImportPortletDataContext(
				group.getCompanyId(), groupId, parameterMap,
				getUserIdStrategy(userId, userIdStrategy), zipReader);

		try {
			return validateMissingReferences(portletDataContext);
		}
		finally {
			zipReader.close();
		}
	}

	@Override
	public MissingReferences validateMissingReferences(
			final PortletDataContext portletDataContext)
		throws Exception {

		final MissingReferences missingReferences = new MissingReferences();

		XMLReader xmlReader = SecureXMLFactoryProviderUtil.newXMLReader();

		ElementHandler elementHandler = new ElementHandler(
			new ElementProcessor() {

				@Override
				public void processElement(Element element) {
					MissingReference missingReference =
						validateMissingReference(portletDataContext, element);

					if (missingReference != null) {
						missingReferences.add(missingReference);
					}
				}

			},
			new String[] {"missing-reference"});

		xmlReader.setContentHandler(elementHandler);

		xmlReader.parse(
			new InputSource(
				portletDataContext.getZipEntryAsInputStream("/manifest.xml")));

		return missingReferences;
	}

	@Override
	public void writeManifestSummary(
		Document document, ManifestSummary manifestSummary) {

		Element rootElement = document.getRootElement();

		Element manifestSummaryElement = rootElement.addElement(
			"manifest-summary");

		for (String manifestSummaryKey :
				manifestSummary.getManifestSummaryKeys()) {

			Element element = manifestSummaryElement.addElement("staged-model");

			element.addAttribute("manifest-summary-key", manifestSummaryKey);

			long modelAdditionCount = manifestSummary.getModelAdditionCount(
				manifestSummaryKey);

			if (modelAdditionCount > 0) {
				element.addAttribute(
					"addition-count", String.valueOf(modelAdditionCount));
			}

			long modelDeletionCount = manifestSummary.getModelDeletionCount(
				manifestSummaryKey);

			if (modelDeletionCount > 0) {
				element.addAttribute(
					"deletion-count", String.valueOf(modelDeletionCount));
			}
		}
	}

	protected void addCreateDateProperty(
		PortletDataContext portletDataContext, DynamicQuery dynamicQuery) {

		if (!portletDataContext.hasDateRange()) {
			return;
		}

		Property createDateProperty = PropertyFactoryUtil.forName("createDate");

		Date startDate = portletDataContext.getStartDate();

		dynamicQuery.add(createDateProperty.ge(startDate));

		Date endDate = portletDataContext.getEndDate();

		dynamicQuery.add(createDateProperty.le(endDate));
	}

	protected void doAddCriteria(
		PortletDataContext portletDataContext, StagedModelType stagedModelType,
		DynamicQuery dynamicQuery) {

		Disjunction disjunction = RestrictionsFactoryUtil.disjunction();

		Property groupIdProperty = PropertyFactoryUtil.forName("groupId");

		disjunction.add(groupIdProperty.eq(0L));
		disjunction.add(
			groupIdProperty.eq(portletDataContext.getScopeGroupId()));

		dynamicQuery.add(disjunction);

		Property classNameIdProperty = PropertyFactoryUtil.forName(
			"classNameId");

		dynamicQuery.add(
			classNameIdProperty.eq(stagedModelType.getClassNameId()));

		long referrerClassNameId = stagedModelType.getReferrerClassNameId();

		Property referrerClassNameIdProperty = PropertyFactoryUtil.forName(
			"referrerClassNameId");

		if ((referrerClassNameId !=
				StagedModelType.REFERRER_CLASS_NAME_ID_ALL) &&
			(referrerClassNameId !=
				StagedModelType.REFERRER_CLASS_NAME_ID_ANY)) {

			dynamicQuery.add(
				referrerClassNameIdProperty.eq(
					stagedModelType.getReferrerClassNameId()));
		}
		else if (referrerClassNameId ==
					StagedModelType.REFERRER_CLASS_NAME_ID_ANY) {

			dynamicQuery.add(referrerClassNameIdProperty.isNotNull());
		}

		Property typeProperty = PropertyFactoryUtil.forName("type");

		dynamicQuery.add(typeProperty.eq(SystemEventConstants.TYPE_DELETE));

		addCreateDateProperty(portletDataContext, dynamicQuery);
	}

	protected boolean getExportPortletData(
			long companyId, String portletId,
			Map<String, String[]> parameterMap)
		throws Exception {

		boolean exportPortletData = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_DATA);
		boolean exportPortletDataAll = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_DATA_ALL);

		if (_log.isDebugEnabled()) {
			_log.debug("Export portlet data " + exportPortletData);
			_log.debug("Export all portlet data " + exportPortletDataAll);
		}

		if (!exportPortletData) {
			return false;
		}

		PortletDataHandler portletDataHandler =
			_portletDataHandlerProvider.provide(companyId, portletId);

		if (portletDataHandler == null) {
			return false;
		}

		if (exportPortletDataAll || !portletDataHandler.isDataSiteLevel()) {
			return true;
		}

		return MapUtil.getBoolean(
			parameterMap,
			PortletDataHandlerKeys.PORTLET_DATA + StringPool.UNDERLINE +
				PortletIdCodec.decodePortletName(portletId));
	}

	protected Map<String, Boolean> getExportPortletSetupControlsMap(
			long companyId, String portletId,
			Map<String, String[]> parameterMap, String type)
		throws Exception {

		boolean exportPortletConfiguration = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_CONFIGURATION);
		boolean exportPortletConfigurationAll = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL);

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Export portlet configuration " + exportPortletConfiguration);
		}

		boolean exportCurPortletArchivedSetups = exportPortletConfiguration;
		boolean exportCurPortletConfiguration = exportPortletConfiguration;
		boolean exportCurPortletSetup = exportPortletConfiguration;
		boolean exportCurPortletUserPreferences = exportPortletConfiguration;

		String rootPortletId = getExportableRootPortletId(companyId, portletId);

		if (exportPortletConfigurationAll ||
			(exportPortletConfiguration && type.equals("layout-prototype"))) {

			exportCurPortletConfiguration = true;

			exportCurPortletArchivedSetups = MapUtil.getBoolean(
				parameterMap,
				PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS_ALL);
			exportCurPortletSetup = MapUtil.getBoolean(
				parameterMap, PortletDataHandlerKeys.PORTLET_SETUP_ALL);
			exportCurPortletUserPreferences = MapUtil.getBoolean(
				parameterMap,
				PortletDataHandlerKeys.PORTLET_USER_PREFERENCES_ALL);
		}
		else if (rootPortletId != null) {
			exportCurPortletConfiguration =
				exportPortletConfiguration &&
				MapUtil.getBoolean(
					parameterMap,
					PortletDataHandlerKeys.PORTLET_CONFIGURATION +
						StringPool.UNDERLINE + rootPortletId);

			exportCurPortletArchivedSetups =
				exportCurPortletConfiguration &&
				MapUtil.getBoolean(
					parameterMap,
					PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS +
						StringPool.UNDERLINE + rootPortletId);
			exportCurPortletSetup =
				exportCurPortletConfiguration &&
				MapUtil.getBoolean(
					parameterMap,
					PortletDataHandlerKeys.PORTLET_SETUP +
						StringPool.UNDERLINE + rootPortletId);
			exportCurPortletUserPreferences =
				exportCurPortletConfiguration &&
				MapUtil.getBoolean(
					parameterMap,
					PortletDataHandlerKeys.PORTLET_USER_PREFERENCES +
						StringPool.UNDERLINE + rootPortletId);
		}

		Map<String, Boolean> exportPortletSetupControlsMap = new HashMap<>();

		exportPortletSetupControlsMap.put(
			PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS,
			exportCurPortletArchivedSetups);
		exportPortletSetupControlsMap.put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION,
			exportCurPortletConfiguration);
		exportPortletSetupControlsMap.put(
			PortletDataHandlerKeys.PORTLET_SETUP, exportCurPortletSetup);
		exportPortletSetupControlsMap.put(
			PortletDataHandlerKeys.PORTLET_USER_PREFERENCES,
			exportCurPortletUserPreferences);

		return exportPortletSetupControlsMap;
	}

	protected boolean getImportPortletData(
			long companyId, String portletId,
			Map<String, String[]> parameterMap, Element portletDataElement)
		throws Exception {

		boolean importPortletData = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_DATA);
		boolean importPortletDataAll = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_DATA_ALL);

		if (_log.isDebugEnabled()) {
			_log.debug("Import portlet data " + importPortletData);
			_log.debug("Import all portlet data " + importPortletDataAll);
		}

		if (!importPortletData) {
			return false;
		}

		PortletDataHandler portletDataHandler =
			_portletDataHandlerProvider.provide(companyId, portletId);

		if ((portletDataHandler == null) ||
			((portletDataElement == null) &&
			 !portletDataHandler.isDisplayPortlet())) {

			return false;
		}

		if (importPortletDataAll || !portletDataHandler.isDataSiteLevel()) {
			return true;
		}

		return MapUtil.getBoolean(
			parameterMap,
			PortletDataHandlerKeys.PORTLET_DATA + StringPool.UNDERLINE +
				PortletIdCodec.decodePortletName(portletId));
	}

	protected Map<String, Boolean> getImportPortletSetupControlsMap(
			long companyId, String portletId,
			Map<String, String[]> parameterMap, ManifestSummary manifestSummary)
		throws Exception {

		boolean importPortletConfiguration = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_CONFIGURATION);
		boolean importPortletConfigurationAll = MapUtil.getBoolean(
			parameterMap, PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL);

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Import portlet configuration " + importPortletConfiguration);
		}

		boolean importCurPortletArchivedSetups = importPortletConfiguration;
		boolean importCurPortletConfiguration = importPortletConfiguration;
		boolean importCurPortletSetup = importPortletConfiguration;
		boolean importCurPortletUserPreferences = importPortletConfiguration;

		String rootPortletId = getExportableRootPortletId(companyId, portletId);

		if (importPortletConfigurationAll) {
			importCurPortletConfiguration = true;

			if ((manifestSummary != null) &&
				(manifestSummary.getConfigurationPortletOptions(
					rootPortletId) == null)) {

				importCurPortletConfiguration = false;
			}

			importCurPortletArchivedSetups =
				importCurPortletConfiguration &&
				MapUtil.getBoolean(
					parameterMap,
					PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS_ALL);
			importCurPortletSetup =
				importCurPortletConfiguration &&
				MapUtil.getBoolean(
					parameterMap, PortletDataHandlerKeys.PORTLET_SETUP_ALL);
			importCurPortletUserPreferences =
				importCurPortletConfiguration &&
				MapUtil.getBoolean(
					parameterMap,
					PortletDataHandlerKeys.PORTLET_USER_PREFERENCES_ALL);
		}
		else if (rootPortletId != null) {
			importCurPortletConfiguration =
				importPortletConfiguration &&
				MapUtil.getBoolean(
					parameterMap,
					PortletDataHandlerKeys.PORTLET_CONFIGURATION +
						StringPool.UNDERLINE + rootPortletId);

			importCurPortletArchivedSetups =
				importCurPortletConfiguration &&
				MapUtil.getBoolean(
					parameterMap,
					PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS +
						StringPool.UNDERLINE + rootPortletId);
			importCurPortletSetup =
				importCurPortletConfiguration &&
				MapUtil.getBoolean(
					parameterMap,
					PortletDataHandlerKeys.PORTLET_SETUP +
						StringPool.UNDERLINE + rootPortletId);
			importCurPortletUserPreferences =
				importCurPortletConfiguration &&
				MapUtil.getBoolean(
					parameterMap,
					PortletDataHandlerKeys.PORTLET_USER_PREFERENCES +
						StringPool.UNDERLINE + rootPortletId);
		}

		Map<String, Boolean> importPortletSetupMap = new HashMap<>();

		importPortletSetupMap.put(
			PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS,
			importCurPortletArchivedSetups);
		importPortletSetupMap.put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION,
			importCurPortletConfiguration);
		importPortletSetupMap.put(
			PortletDataHandlerKeys.PORTLET_SETUP, importCurPortletSetup);
		importPortletSetupMap.put(
			PortletDataHandlerKeys.PORTLET_USER_PREFERENCES,
			importCurPortletUserPreferences);

		return importPortletSetupMap;
	}

	protected ZipWriter getZipWriter(String fileName) {
		if (!ExportImportThreadLocal.isStagingInProcess() ||
			(PropsValues.STAGING_DELETE_TEMP_LAR_ON_FAILURE &&
			 PropsValues.STAGING_DELETE_TEMP_LAR_ON_SUCCESS)) {

			return ZipWriterFactoryUtil.getZipWriter();
		}

		return ZipWriterFactoryUtil.getZipWriter(
			new File(
				SystemProperties.get(SystemProperties.TMP_DIR) +
					StringPool.SLASH + fileName));
	}

	protected boolean populateLayoutsJSON(
		JSONArray layoutsJSONArray, Layout layout, long[] selectedLayoutIds) {

		List<Layout> childLayouts = layout.getChildren();
		JSONArray childLayoutsJSONArray = null;
		boolean includeChildren = true;

		if (ListUtil.isNotEmpty(childLayouts)) {
			childLayoutsJSONArray = JSONFactoryUtil.createJSONArray();

			for (Layout childLayout : childLayouts) {
				if (!populateLayoutsJSON(
						childLayoutsJSONArray, childLayout,
						selectedLayoutIds)) {

					includeChildren = false;
				}
			}
		}

		boolean checked = ArrayUtil.contains(
			selectedLayoutIds, layout.getLayoutId());

		if (checked) {
			JSONObject layoutJSONObject = JSONFactoryUtil.createJSONObject();

			layoutJSONObject.put("includeChildren", includeChildren);
			layoutJSONObject.put("plid", layout.getPlid());

			layoutsJSONArray.put(layoutJSONObject);
		}

		if (checked && includeChildren) {
			return true;
		}

		if (childLayoutsJSONArray != null) {

			// We want a 1 level array and not an array of arrays

			for (int i = 0; i < childLayoutsJSONArray.length(); i++) {
				layoutsJSONArray.put(childLayoutsJSONArray.getJSONObject(i));
			}
		}

		return false;
	}

	@Reference(unbind = "-")
	protected void setDlFileEntryLocalService(
		DLFileEntryLocalService dlFileEntryLocalService) {

		_dlFileEntryLocalService = dlFileEntryLocalService;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		_layoutLocalService = layoutLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutService(LayoutService layoutService) {
		_layoutService = layoutService;
	}

	@Reference(unbind = "-")
	protected void setPortletLocalService(
		PortletLocalService portletLocalService) {

		_portletLocalService = portletLocalService;
	}

	@Reference(unbind = "-")
	protected void setSystemEventLocalService(
		SystemEventLocalService systemEventLocalService) {

		_systemEventLocalService = systemEventLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	protected MissingReference validateMissingReference(
		PortletDataContext portletDataContext, Element element) {

		String className = element.attributeValue("class-name");

		StagedModelDataHandler<?> stagedModelDataHandler =
			StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(
				className);

		if (!stagedModelDataHandler.validateReference(
				portletDataContext, element)) {

			MissingReference missingReference = new MissingReference(element);

			Map<Long, Long> groupIds =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					Group.class);

			long groupId = MapUtil.getLong(
				groupIds,
				GetterUtil.getLong(element.attributeValue("group-id")));

			missingReference.setGroupId(groupId);

			return missingReference;
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExportImportHelperImpl.class);

	private DLFileEntryLocalService _dlFileEntryLocalService;
	private GroupLocalService _groupLocalService;
	private LayoutLocalService _layoutLocalService;
	private LayoutService _layoutService;

	@Reference
	private PortletDataHandlerProvider _portletDataHandlerProvider;

	private PortletLocalService _portletLocalService;
	private SystemEventLocalService _systemEventLocalService;
	private UserLocalService _userLocalService;

	private class ManifestSummaryElementProcessor implements ElementProcessor {

		public ManifestSummaryElementProcessor(
			Group group, ManifestSummary manifestSummary) {

			_group = group;
			_manifestSummary = manifestSummary;
		}

		@Override
		public void processElement(Element element) {
			String elementName = element.getName();

			if (elementName.equals("header")) {
				String exportDateString = element.attributeValue("export-date");

				Date exportDate = GetterUtil.getDate(
					exportDateString,
					DateFormatFactoryUtil.getSimpleDateFormat(
						Time.RFC822_FORMAT));

				_manifestSummary.setExportDate(exportDate);
			}
			else if (elementName.equals("portlet")) {
				String portletId = element.attributeValue("portlet-id");

				Portlet portlet = null;

				try {
					portlet = _portletLocalService.getPortletById(
						_group.getCompanyId(), portletId);
				}
				catch (Exception e) {
					return;
				}

				PortletDataHandler portletDataHandler =
					_portletDataHandlerProvider.provide(portlet);

				if (portletDataHandler == null) {
					return;
				}

				String[] configurationPortletOptions = StringUtil.split(
					element.attributeValue("portlet-configuration"));

				if (!(portletDataHandler instanceof
						DefaultConfigurationPortletDataHandler) &&
					portletDataHandler.isDataSiteLevel() &&
					GetterUtil.getBoolean(
						element.attributeValue("portlet-data"))) {

					_manifestSummary.addDataPortlet(
						portlet, configurationPortletOptions);
				}
				else {
					_manifestSummary.addLayoutPortlet(
						portlet, configurationPortletOptions);
				}
			}
			else if (elementName.equals("staged-model")) {
				String manifestSummaryKey = element.attributeValue(
					"manifest-summary-key");

				if (Validator.isNull(manifestSummaryKey)) {
					return;
				}

				long modelAdditionCount = GetterUtil.getLong(
					element.attributeValue("addition-count"));

				_manifestSummary.addModelAdditionCount(
					manifestSummaryKey, modelAdditionCount);

				long modelDeletionCount = GetterUtil.getLong(
					element.attributeValue("deletion-count"));

				_manifestSummary.addModelDeletionCount(
					manifestSummaryKey, modelDeletionCount);
			}
		}

		private final Group _group;
		private final ManifestSummary _manifestSummary;

	}

}