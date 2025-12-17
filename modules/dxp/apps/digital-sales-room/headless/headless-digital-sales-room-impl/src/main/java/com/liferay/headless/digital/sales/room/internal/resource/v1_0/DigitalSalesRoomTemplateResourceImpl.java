/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.digital.sales.room.internal.resource.v1_0;

import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactory;
import com.liferay.exportimport.kernel.configuration.constants.ExportImportConfigurationConstants;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalService;
import com.liferay.exportimport.kernel.service.ExportImportLocalService;
import com.liferay.headless.digital.sales.room.dto.v1_0.DigitalSalesRoomTemplate;
import com.liferay.headless.digital.sales.room.dto.v1_0.FileEntry;
import com.liferay.headless.digital.sales.room.internal.dto.v1_0.converter.DigitalSalesRoomTemplateDTOConverterContext;
import com.liferay.headless.digital.sales.room.resource.v1_0.DigitalSalesRoomTemplateResource;
import com.liferay.layout.util.LayoutServiceContextHelper;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.events.ServicePreAction;
import com.liferay.portal.events.ThemeServicePreAction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.servlet.DummyHttpServletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UniqueUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.util.comparator.GroupNameComparator;
import com.liferay.portal.liveusers.LiveUsers;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.site.initializer.SiteInitializerRegistry;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalService;

import java.io.File;
import java.io.Serializable;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Stefano Motta
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/digital-sales-room-template.properties",
	scope = ServiceScope.PROTOTYPE,
	service = DigitalSalesRoomTemplateResource.class
)
public class DigitalSalesRoomTemplateResourceImpl
	extends BaseDigitalSalesRoomTemplateResourceImpl {

	@Override
	public void deleteDigitalSalesRoomTemplate(Long digitalSalesRoomTemplateId)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-66359")) {

			throw new UnsupportedOperationException();
		}

		Group group = _groupService.getGroup(digitalSalesRoomTemplateId);
		ObjectDefinition objectDefinition = _getObjectDefinition();

		ObjectEntry objectEntry = _objectEntryLocalService.getObjectEntry(
			group.getExternalReferenceCode(), group.getGroupId(),
			objectDefinition.getObjectDefinitionId());

		_objectEntryLocalService.deleteObjectEntry(
			objectEntry.getObjectEntryId());

		_groupLocalService.deleteGroup(group.getGroupId());
	}

	@Override
	public DigitalSalesRoomTemplate getDigitalSalesRoomTemplate(
			Long digitalSalesRoomTemplateId)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-66359")) {

			throw new UnsupportedOperationException();
		}

		Group group = _groupService.getGroup(digitalSalesRoomTemplateId);
		ObjectDefinition objectDefinition = _getObjectDefinition();

		return _toDigitalSalesRoomTemplate(
			group,
			_objectEntryLocalService.getObjectEntry(
				group.getExternalReferenceCode(), group.getGroupId(),
				objectDefinition.getObjectDefinitionId()));
	}

	@Override
	public Page<DigitalSalesRoomTemplate> getDigitalSalesRoomTemplatesPage(
			String search, Pagination pagination)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-66359")) {

			throw new UnsupportedOperationException();
		}

		ObjectDefinition objectDefinition = _getObjectDefinition();

		long[] classNameIds = {
			_portal.getClassNameId(objectDefinition.getClassName())
		};

		LinkedHashMap<String, Object> params =
			LinkedHashMapBuilder.<String, Object>put(
				"active", true
			).put(
				"site", true
			).build();

		return Page.of(
			null,
			transform(
				_groupService.search(
					contextCompany.getCompanyId(), classNameIds, search, null,
					params, true, pagination.getStartPosition(),
					pagination.getEndPosition(), new GroupNameComparator()),
				group -> _toDigitalSalesRoomTemplate(
					group,
					_objectEntryLocalService.getObjectEntry(
						group.getExternalReferenceCode(), group.getGroupId(),
						objectDefinition.getObjectDefinitionId()))),
			pagination,
			_groupService.searchCount(
				contextCompany.getCompanyId(), classNameIds, search, params));
	}

	@Override
	public DigitalSalesRoomTemplate
			postDigitalSalesRoomDigitalSalesRoomTemplate(
				Long digitalSalesRoomId)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-66359")) {

			throw new UnsupportedOperationException();
		}

		Group sourceGroup = _groupService.getGroup(digitalSalesRoomId);

		long[] layoutIds = ListUtil.toLongArray(
			_layoutLocalService.getLayouts(sourceGroup.getGroupId(), false),
			Layout::getLayoutId);

		if (ArrayUtil.isEmpty(layoutIds)) {
			throw new UnsupportedOperationException();
		}

		Group targetGroup = _addGroup(
			sourceGroup.getDescription(
				contextAcceptLanguage.getPreferredLocale()),
			"blank-site-initializer",
			UniqueUtil.getUniqueValue(
				"template",
				uniqueValue -> {
					Group group = _groupLocalService.fetchGroup(
						contextCompany.getCompanyId(), uniqueValue);

					if (group == null) {
						return true;
					}

					return false;
				},
				sourceGroup.getName(
					contextAcceptLanguage.getPreferredLocale())));

		ObjectDefinition objectDefinition = _getObjectDefinition();

		targetGroup.setClassName(objectDefinition.getClassName());

		ObjectEntryManager objectEntryManager =
			_objectEntryManagerRegistry.getObjectEntryManager(
				objectDefinition.getCompanyId(),
				objectDefinition.getStorageType());

		DefaultDTOConverterContext defaultDTOConverterContext =
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(), null,
				_dtoConverterRegistry, contextHttpServletRequest, null,
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser);

		defaultDTOConverterContext.setAttribute("addActions", Boolean.FALSE);

		ObjectDefinition dsrRoomObjectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_DSR_ROOM", contextCompany.getCompanyId());
		Group finalTargetGroup = targetGroup;

		ObjectEntry sourceObjectEntry = _objectEntryLocalService.getObjectEntry(
			sourceGroup.getExternalReferenceCode(), sourceGroup.getGroupId(),
			dsrRoomObjectDefinition.getObjectDefinitionId());

		com.liferay.object.rest.dto.v1_0.ObjectEntry targetObjectEntry =
			objectEntryManager.addObjectEntry(
				defaultDTOConverterContext, objectDefinition,
				new com.liferay.object.rest.dto.v1_0.ObjectEntry() {
					{
						setProperties(
							() -> {
								Map<String, Serializable> values =
									sourceObjectEntry.getValues();

								values.put(
									"externalReferenceCode",
									finalTargetGroup.
										getExternalReferenceCode());

								return Collections.unmodifiableMap(values);
							});
					}
				},
				targetGroup.getGroupKey());

		targetGroup.setClassPK(targetObjectEntry.getId());

		targetGroup = _groupLocalService.updateGroup(targetGroup);

		_importLarFile(
			targetGroup.getGroupId(),
			_generateLarFile(sourceGroup.getGroupId(), layoutIds), layoutIds);

		return _toDigitalSalesRoomTemplate(
			targetGroup,
			_objectEntryLocalService.getObjectEntry(targetObjectEntry.getId()));
	}

	@Override
	public DigitalSalesRoomTemplate postDigitalSalesRoomTemplate(
			DigitalSalesRoomTemplate digitalSalesRoomTemplate)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-66359")) {

			throw new UnsupportedOperationException();
		}

		ObjectDefinition objectDefinition = _getObjectDefinition();

		Group group = _addGroup(
			digitalSalesRoomTemplate.getDescription(),
			"com.liferay.digital.sales.room.site.initializer",
			digitalSalesRoomTemplate.getName());

		group.setClassName(objectDefinition.getClassName());

		ObjectEntryManager objectEntryManager =
			_objectEntryManagerRegistry.getObjectEntryManager(
				objectDefinition.getCompanyId(),
				objectDefinition.getStorageType());

		DefaultDTOConverterContext defaultDTOConverterContext =
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(), null,
				_dtoConverterRegistry, contextHttpServletRequest, null,
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser);

		defaultDTOConverterContext.setAttribute("addActions", Boolean.FALSE);

		com.liferay.object.rest.dto.v1_0.ObjectEntry objectEntry =
			objectEntryManager.addObjectEntry(
				defaultDTOConverterContext, objectDefinition,
				_toObjectEntry(digitalSalesRoomTemplate, group),
				group.getGroupKey());

		group.setClassPK(objectEntry.getId());

		group = _groupLocalService.updateGroup(group);

		_updateFrontendTokensValues(digitalSalesRoomTemplate, group);

		return _toDigitalSalesRoomTemplate(
			group,
			_objectEntryLocalService.getObjectEntry(objectEntry.getId()));
	}

	private Group _addGroup(
			String description, String siteTemplateKey, String name)
		throws Exception {

		SiteInitializer siteInitializer =
			_siteInitializerRegistry.getSiteInitializer(siteTemplateKey);

		if (siteInitializer == null) {
			throw new IllegalArgumentException(
				"No site initializer was found for site template key " +
					siteTemplateKey);
		}

		_initThemeDisplay();

		try (AutoCloseable autoCloseable =
				_layoutServiceContextHelper.getServiceContextAutoCloseable(
					contextCompany, contextUser)) {

			Group group = _groupLocalService.addGroup(
				StringPool.BLANK, contextUser.getUserId(),
				GroupConstants.DEFAULT_PARENT_GROUP_ID, null, 0,
				GroupConstants.DEFAULT_LIVE_GROUP_ID,
				HashMapBuilder.put(
					LocaleUtil.getDefault(), name
				).build(),
				HashMapBuilder.put(
					LocaleUtil.getDefault(), description
				).build(),
				GroupConstants.TYPE_SITE_RESTRICTED, null, true,
				GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, StringPool.BLANK,
				true, false, true, _getServiceContext());

			LiveUsers.joinGroup(
				contextCompany.getCompanyId(), group.getGroupId(),
				contextUser.getUserId());

			siteInitializer.initialize(group.getGroupId());

			return group;
		}
		catch (Exception exception) {

			// LPS-169057

			PermissionCacheUtil.clearCache(contextUser.getUserId());

			throw exception;
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	private File _generateLarFile(long groupId, long[] layoutIds)
		throws Exception {

		Map<String, Serializable> exportLayoutSettingsMap =
			_exportImportConfigurationSettingsMapFactory.
				buildExportLayoutSettingsMap(
					contextUser, groupId, false, layoutIds,
					_getExportParameterMap());

		ExportImportConfiguration exportImportConfiguration =
			_exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					contextUser.getUserId(),
					ExportImportConfigurationConstants.TYPE_EXPORT_LAYOUT,
					exportLayoutSettingsMap);

		return _exportImportLocalService.exportLayoutsAsFile(
			exportImportConfiguration);
	}

	private Map<String, String[]> _getExportParameterMap() {
		return LinkedHashMapBuilder.put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.PORTLET_SETUP_ALL,
			new String[] {Boolean.TRUE.toString()}
		).build();
	}

	private String _getFrontendTokensValues(
			String frontendTokensValues, String primaryColor,
			String secondaryColor)
		throws Exception {

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			GetterUtil.get(frontendTokensValues, "{}"));

		if (!Validator.isBlank(primaryColor)) {
			jsonObject = jsonObject.put(
				"brandColor1",
				JSONUtil.put(
					"cssVariableMapping", "brand-color-1"
				).put(
					"name", "primaryColor"
				).put(
					"value", primaryColor
				)
			).put(
				"btnPrimaryBackgroundColor",
				JSONUtil.put(
					"cssVariableMapping", "btn-primary-background-color"
				).put(
					"name", "primaryColor"
				).put(
					"value", primaryColor
				)
			).put(
				"btnPrimaryBorderColor",
				JSONUtil.put(
					"cssVariableMapping", "btn-primary-border-color"
				).put(
					"name", "primaryColor"
				).put(
					"value", primaryColor
				)
			).put(
				"btnPrimaryHoverBackgroundColor",
				JSONUtil.put(
					"cssVariableMapping", "btn-primary-hover-background-color"
				).put(
					"name", "primaryColor"
				).put(
					"value", primaryColor
				)
			).put(
				"primaryColor",
				JSONUtil.put(
					"cssVariableMapping", "primary"
				).put(
					"value", primaryColor
				)
			);
		}

		if (!Validator.isBlank(secondaryColor)) {
			jsonObject = jsonObject.put(
				"brandColor2",
				JSONUtil.put(
					"cssVariableMapping", "brand-color-2"
				).put(
					"name", "secondaryColor"
				).put(
					"value", secondaryColor
				)
			).put(
				"btnSecondaryBackgroundColor",
				JSONUtil.put(
					"cssVariableMapping", "btn-secondary-background-color"
				).put(
					"name", "secondaryColor"
				).put(
					"value", secondaryColor
				)
			).put(
				"btnSecondaryBorderColor",
				JSONUtil.put(
					"cssVariableMapping", "btn-secondary-border-color"
				).put(
					"name", "secondaryColor"
				).put(
					"value", secondaryColor
				)
			).put(
				"btnSecondaryHoverBackgroundColor",
				JSONUtil.put(
					"cssVariableMapping", "btn-secondary-hover-background-color"
				).put(
					"name", "secondaryColor"
				).put(
					"value", secondaryColor
				)
			).put(
				"secondaryColor",
				JSONUtil.put(
					"cssVariableMapping", "secondary"
				).put(
					"value", secondaryColor
				)
			);
		}

		return jsonObject.toString();
	}

	private Map<String, String[]> _getImportParameterMap() {
		return LinkedHashMapBuilder.putAll(
			_getExportParameterMap()
		).put(
			PortletDataHandlerKeys.DATA_STRATEGY,
			new String[] {PortletDataHandlerKeys.DATA_STRATEGY_MIRROR_OVERWRITE}
		).put(
			PortletDataHandlerKeys.LAYOUT_SET_PROTOTYPE_SETTINGS,
			new String[] {Boolean.TRUE.toString()}
		).put(
			PortletDataHandlerKeys.LAYOUT_SET_SETTINGS,
			new String[] {Boolean.TRUE.toString()}
		).build();
	}

	private ObjectDefinition _getObjectDefinition() throws Exception {
		return _objectDefinitionLocalService.
			getObjectDefinitionByExternalReferenceCode(
				"L_DSR_TEMPLATE", contextCompany.getCompanyId());
	}

	private Map<String, Serializable> _getProperties(
		DigitalSalesRoomTemplate digitalSalesRoomTemplate, Group group) {

		return HashMapBuilder.<String, Serializable>put(
			"banner",
			() -> {
				FileEntry fileEntry = digitalSalesRoomTemplate.getBanner();

				if ((fileEntry == null) ||
					Validator.isBlank(fileEntry.getFileBase64())) {

					return null;
				}

				return HashMapBuilder.put(
					"fileBase64", fileEntry.getFileBase64()
				).put(
					"name",
					GetterUtil.getString(
						fileEntry.getFileName(), StringUtil.randomString())
				).build();
			}
		).put(
			"clientLogo",
			() -> {
				FileEntry fileEntry = digitalSalesRoomTemplate.getClientLogo();

				if ((fileEntry == null) ||
					Validator.isBlank(fileEntry.getFileBase64())) {

					return null;
				}

				return HashMapBuilder.put(
					"fileBase64", fileEntry.getFileBase64()
				).put(
					"name",
					GetterUtil.getString(
						fileEntry.getFileName(), StringUtil.randomString())
				).build();
			}
		).put(
			"clientName", digitalSalesRoomTemplate.getClientName()
		).put(
			"externalReferenceCode", group.getExternalReferenceCode()
		).put(
			"primaryColor", digitalSalesRoomTemplate.getPrimaryColor()
		).put(
			"secondaryColor", digitalSalesRoomTemplate.getSecondaryColor()
		).build();
	}

	private ServiceContext _getServiceContext() throws PortalException {
		ServiceContext serviceContext = null;

		if (contextHttpServletRequest != null) {
			serviceContext = ServiceContextFactory.getInstance(
				contextHttpServletRequest);
		}
		else {
			serviceContext = new ServiceContext();

			serviceContext.setCompanyId(contextCompany.getCompanyId());
			serviceContext.setRequest(contextHttpServletRequest);
			serviceContext.setUserId(contextUser.getUserId());
		}

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		return serviceContext;
	}

	private void _importLarFile(long groupId, File larFile, long[] layoutIds)
		throws Exception {

		Map<String, Serializable> importLayoutSettingsMap =
			_exportImportConfigurationSettingsMapFactory.
				buildImportLayoutSettingsMap(
					contextUser, groupId, false, layoutIds,
					_getImportParameterMap());

		ExportImportConfiguration exportImportConfiguration =
			_exportImportConfigurationLocalService.
				addDraftExportImportConfiguration(
					contextUser.getUserId(), StringPool.BLANK,
					ExportImportConfigurationConstants.TYPE_IMPORT_LAYOUT,
					importLayoutSettingsMap);

		_exportImportLocalService.importLayouts(
			exportImportConfiguration, larFile);
	}

	private void _initThemeDisplay() throws Exception {
		if (contextHttpServletRequest == null) {
			return;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)contextHttpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (themeDisplay != null) {
			return;
		}

		ServicePreAction servicePreAction = new ServicePreAction();

		servicePreAction.servicePre(
			contextHttpServletRequest, contextHttpServletResponse, false);

		ThemeServicePreAction themeServicePreAction =
			new ThemeServicePreAction();

		themeServicePreAction.run(
			contextHttpServletRequest, contextHttpServletResponse);

		themeDisplay = (ThemeDisplay)contextHttpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		themeDisplay.setResponse(new DummyHttpServletResponse());
	}

	private DigitalSalesRoomTemplate _toDigitalSalesRoomTemplate(
			Group group, ObjectEntry objectEntry)
		throws Exception {

		return _digitalSalesRoomTemplateDTOConverter.toDTO(
			new DigitalSalesRoomTemplateDTOConverterContext(
				true, null, _dtoConverterRegistry, group.getGroupId(),
				contextAcceptLanguage.getPreferredLocale(), objectEntry,
				contextUriInfo, contextUser),
			group);
	}

	private com.liferay.object.rest.dto.v1_0.ObjectEntry _toObjectEntry(
		DigitalSalesRoomTemplate digitalSalesRoomTemplate, Group group) {

		return new com.liferay.object.rest.dto.v1_0.ObjectEntry() {
			{
				setProperties(
					() -> Collections.unmodifiableMap(
						_getProperties(digitalSalesRoomTemplate, group)));
			}
		};
	}

	private void _updateFrontendTokensValues(
			DigitalSalesRoomTemplate digitalSalesRoomTemplate, Group group)
		throws Exception {

		StyleBookEntry styleBookEntry =
			_styleBookEntryLocalService.fetchStyleBookEntry(
				group.getGroupId(), "dsr-classic");

		if (styleBookEntry.isHead()) {
			styleBookEntry = _styleBookEntryLocalService.getDraft(
				styleBookEntry.getStyleBookEntryId());
		}

		styleBookEntry = _styleBookEntryLocalService.updateFrontendTokensValues(
			styleBookEntry.getStyleBookEntryId(),
			_getFrontendTokensValues(
				styleBookEntry.getFrontendTokensValues(),
				digitalSalesRoomTemplate.getPrimaryColor(),
				digitalSalesRoomTemplate.getSecondaryColor()));

		_styleBookEntryLocalService.publishDraft(styleBookEntry);
	}

	@Reference(
		target = "(component.name=com.liferay.headless.digital.sales.room.internal.dto.v1_0.converter.DigitalSalesRoomTemplateDTOConverter)"
	)
	private DTOConverter<Group, DigitalSalesRoomTemplate>
		_digitalSalesRoomTemplateDTOConverter;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;

	@Reference
	private ExportImportConfigurationSettingsMapFactory
		_exportImportConfigurationSettingsMapFactory;

	@Reference
	private ExportImportLocalService _exportImportLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private GroupService _groupService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutServiceContextHelper _layoutServiceContextHelper;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ObjectEntryManagerRegistry _objectEntryManagerRegistry;

	@Reference
	private Portal _portal;

	@Reference
	private SiteInitializerRegistry _siteInitializerRegistry;

	@Reference
	private StyleBookEntryLocalService _styleBookEntryLocalService;

}