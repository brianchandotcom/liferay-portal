/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.digital.sales.room.internal.resource.v1_0;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryService;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactory;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalService;
import com.liferay.exportimport.kernel.service.ExportImportLocalService;
import com.liferay.headless.digital.sales.room.dto.v1_0.DigitalSalesRoom;
import com.liferay.headless.digital.sales.room.dto.v1_0.FileEntry;
import com.liferay.headless.digital.sales.room.dto.v1_0.UserAccountBrief;
import com.liferay.headless.digital.sales.room.internal.dto.v1_0.converter.DigitalSalesRoomDTOConverterContext;
import com.liferay.headless.digital.sales.room.internal.util.v1_0.ExportImportUtil;
import com.liferay.headless.digital.sales.room.resource.v1_0.DigitalSalesRoomResource;
import com.liferay.headless.digital.sales.room.resource.v1_0.UserAccountBriefResource;
import com.liferay.layout.util.LayoutServiceContextHelper;
import com.liferay.object.constants.ObjectActionKeys;
import com.liferay.object.definition.security.permission.resource.ObjectDefinitionPortletResourcePermissionRegistryUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectEntryService;
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
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionRegistryUtil;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
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

import java.io.Serializable;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Stefano Motta
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/digital-sales-room.properties",
	scope = ServiceScope.PROTOTYPE, service = DigitalSalesRoomResource.class
)
public class DigitalSalesRoomResourceImpl
	extends BaseDigitalSalesRoomResourceImpl {

	@Override
	public void deleteDigitalSalesRoom(Long digitalSalesRoomId)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-66359")) {

			throw new UnsupportedOperationException();
		}

		Group group = _groupService.getGroup(digitalSalesRoomId);
		ObjectDefinition objectDefinition = _getObjectDefinition();

		com.liferay.object.model.ObjectEntry serviceBuilderObjectEntry =
			_objectEntryLocalService.getObjectEntry(
				group.getExternalReferenceCode(), group.getGroupId(),
				objectDefinition.getObjectDefinitionId());

		Map<String, Serializable> values =
			serviceBuilderObjectEntry.getValues();

		long templateGroupId = GetterUtil.getLong(
			values.get("templateGroupId"));

		if (templateGroupId > 0) {
			Group templateGroup = _groupLocalService.fetchGroup(
				templateGroupId);

			if (templateGroup != null) {
				ObjectDefinition dsrTemplateObjectDefinition =
					_objectDefinitionLocalService.
						getObjectDefinitionByExternalReferenceCode(
							"L_DSR_TEMPLATE", contextCompany.getCompanyId());

				_updateDSRTemplateObjectEntryUsages(
					templateGroup,
					dsrTemplateObjectDefinition.getObjectDefinitionId(), -1);
			}
		}

		_checkPermission(ActionKeys.DELETE, serviceBuilderObjectEntry);

		_objectEntryLocalService.deleteObjectEntry(
			serviceBuilderObjectEntry.getObjectEntryId());

		_groupLocalService.deleteGroup(group.getGroupId());
	}

	@Override
	public DigitalSalesRoom getDigitalSalesRoom(Long digitalSalesRoomId)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-66359")) {

			throw new UnsupportedOperationException();
		}

		Group group = _groupService.getGroup(digitalSalesRoomId);
		ObjectDefinition objectDefinition = _getObjectDefinition();

		return _toDigitalSalesRoom(
			group,
			_objectEntryLocalService.getObjectEntry(
				group.getExternalReferenceCode(), group.getGroupId(),
				objectDefinition.getObjectDefinitionId()));
	}

	@Override
	public Page<DigitalSalesRoom> getDigitalSalesRoomsPage(
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
			HashMapBuilder.<String, Map<String, String>>put(
				"create",
				() -> addAction(
					ObjectActionKeys.ADD_OBJECT_ENTRY, "postDigitalSalesRoom",
					objectDefinition.getResourceName(),
					contextCompany.getCompanyId())
			).build(),
			transform(
				_groupService.search(
					contextCompany.getCompanyId(), classNameIds, search, null,
					params, true, pagination.getStartPosition(),
					pagination.getEndPosition(), new GroupNameComparator()),
				group -> _toDigitalSalesRoom(
					group,
					_objectEntryLocalService.getObjectEntry(
						group.getExternalReferenceCode(), group.getGroupId(),
						objectDefinition.getObjectDefinitionId()))),
			pagination,
			_groupService.searchCount(
				contextCompany.getCompanyId(), classNameIds, search, params));
	}

	@Override
	public Page<DigitalSalesRoom>
			getDigitalSalesRoomTemplateDigitalSalesRoomsPage(
				Long digitalSalesRoomTemplateId)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	@Override
	public DigitalSalesRoom patchDigitalSalesRoom(
			Long digitalSalesRoomId, DigitalSalesRoom digitalSalesRoom)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-66359")) {

			throw new UnsupportedOperationException();
		}

		Group group = _groupService.getGroup(digitalSalesRoomId);
		ObjectDefinition objectDefinition = _getObjectDefinition();

		com.liferay.object.model.ObjectEntry serviceBuilderObjectEntry =
			_objectEntryLocalService.getObjectEntry(
				group.getExternalReferenceCode(), group.getGroupId(),
				objectDefinition.getObjectDefinitionId());

		_checkPermission(ActionKeys.UPDATE, serviceBuilderObjectEntry);

		group = _groupLocalService.updateGroup(
			group.getGroupId(), group.getParentGroupId(),
			_getNameMap(group, digitalSalesRoom),
			_getDescriptionMap(group, digitalSalesRoom), group.getType(),
			group.getTypeSettings(), group.isManualMembership(),
			group.getMembershipRestriction(),
			GetterUtil.get(
				digitalSalesRoom.getFriendlyUrlPath(), group.getFriendlyURL()),
			group.isInheritContent(), group.isActive(), _getServiceContext());

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

		ObjectEntry objectEntry = objectEntryManager.partialUpdateObjectEntry(
			objectDefinition.getCompanyId(), defaultDTOConverterContext,
			group.getExternalReferenceCode(), objectDefinition,
			_toObjectEntry(digitalSalesRoom, group, 0), group.getGroupKey());

		_updateFrontendTokensValues(digitalSalesRoom, group);
		_updateNestedResources(digitalSalesRoom, group);

		return _toDigitalSalesRoom(
			group,
			_objectEntryLocalService.getObjectEntry(objectEntry.getId()));
	}

	@Override
	public DigitalSalesRoom postDigitalSalesRoom(
			DigitalSalesRoom digitalSalesRoom)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-66359")) {

			throw new UnsupportedOperationException();
		}

		ObjectDefinition objectDefinition = _getObjectDefinition();

		PortletResourcePermission portletResourcePermission =
			ObjectDefinitionPortletResourcePermissionRegistryUtil.getService(
				objectDefinition.getResourceName());

		portletResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(), 0L,
			ObjectActionKeys.ADD_OBJECT_ENTRY);

		Group group = _addGroup(
			digitalSalesRoom.getExternalReferenceCode(),
			digitalSalesRoom.getDescription(),
			digitalSalesRoom.getFriendlyUrlPath(),
			"com.liferay.digital.sales.room.site.initializer",
			digitalSalesRoom.getName());

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

		ObjectEntry objectEntry = objectEntryManager.addObjectEntry(
			defaultDTOConverterContext, objectDefinition,
			_toObjectEntry(digitalSalesRoom, group, 0), group.getGroupKey());

		group.setClassPK(objectEntry.getId());

		group = _groupLocalService.updateGroup(group);

		_updateFrontendTokensValues(digitalSalesRoom, group);
		_updateNestedResources(digitalSalesRoom, group);

		return _toDigitalSalesRoom(
			group,
			_objectEntryLocalService.getObjectEntry(objectEntry.getId()));
	}

	@Override
	public DigitalSalesRoom postDigitalSalesRoomTemplateDigitalSalesRoom(
			Long digitalSalesRoomTemplateId, DigitalSalesRoom digitalSalesRoom)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-66359")) {

			throw new UnsupportedOperationException();
		}

		ObjectDefinition dsrTemplateObjectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_DSR_TEMPLATE", contextCompany.getCompanyId());
		Group sourceGroup = _groupService.getGroup(digitalSalesRoomTemplateId);

		if (!Objects.equals(
				dsrTemplateObjectDefinition.getClassName(),
				sourceGroup.getClassName())) {

			throw new UnsupportedOperationException();
		}

		long[] layoutIds = ListUtil.toLongArray(
			_layoutLocalService.getLayouts(sourceGroup.getGroupId(), false),
			Layout::getLayoutId);

		if (ArrayUtil.isEmpty(layoutIds)) {
			throw new UnsupportedOperationException();
		}

		Group targetGroup = _addGroup(
			digitalSalesRoom.getExternalReferenceCode(),
			digitalSalesRoom.getDescription(),
			digitalSalesRoom.getFriendlyUrlPath(), "blank-site-initializer",
			digitalSalesRoom.getName());

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

		ObjectEntry objectEntry = objectEntryManager.addObjectEntry(
			defaultDTOConverterContext, objectDefinition,
			_toObjectEntry(
				digitalSalesRoom, targetGroup, digitalSalesRoomTemplateId),
			targetGroup.getGroupKey());

		targetGroup.setClassPK(objectEntry.getId());

		targetGroup = _groupLocalService.updateGroup(targetGroup);

		ExportImportUtil.importLayouts(
			_exportImportConfigurationLocalService,
			_exportImportConfigurationSettingsMapFactory,
			_exportImportLocalService, layoutIds, sourceGroup.getGroupId(),
			targetGroup.getGroupId(), contextUser);

		_updateFrontendTokensValues(digitalSalesRoom, targetGroup);
		_updateNestedResources(digitalSalesRoom, targetGroup);

		_updateDSRTemplateObjectEntryUsages(
			sourceGroup, dsrTemplateObjectDefinition.getObjectDefinitionId(),
			1);

		return _toDigitalSalesRoom(
			targetGroup,
			_objectEntryLocalService.getObjectEntry(objectEntry.getId()));
	}

	private Map<String, String> _addAction(
		String actionName, long groupId, String methodName,
		ModelResourcePermission<com.liferay.object.model.ObjectEntry>
			modelResourcePermission,
		long objectEntryId) {

		Map<String, String> action = addAction(
			actionName, objectEntryId, methodName, modelResourcePermission);

		if (action == null) {
			return null;
		}

		action.compute(
			"href",
			(key, value) -> StringUtil.replace(
				value, String.valueOf(objectEntryId), String.valueOf(groupId)));

		return action;
	}

	private Group _addGroup(
			String externalReferenceCode, String description,
			String friendlyURLPath, String siteTemplateKey, String name)
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
				externalReferenceCode, contextUser.getUserId(),
				GroupConstants.DEFAULT_PARENT_GROUP_ID, null, 0,
				GroupConstants.DEFAULT_LIVE_GROUP_ID,
				HashMapBuilder.put(
					LocaleUtil.getDefault(), name
				).build(),
				HashMapBuilder.put(
					LocaleUtil.getDefault(), description
				).build(),
				GroupConstants.TYPE_SITE_RESTRICTED, null, true,
				GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, friendlyURLPath,
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

	private void _checkPermission(
			String actionId,
			com.liferay.object.model.ObjectEntry serviceBuilderObjectEntry)
		throws Exception {

		ModelResourcePermission<com.liferay.object.model.ObjectEntry>
			modelResourcePermission =
				_objectEntryService.getModelResourcePermission(
					serviceBuilderObjectEntry.getObjectDefinitionId());

		modelResourcePermission.check(
			PermissionThreadLocal.getPermissionChecker(),
			serviceBuilderObjectEntry.getObjectEntryId(), actionId);
	}

	private Map<Locale, String> _getDescriptionMap(
		Group group, DigitalSalesRoom digitalSalesRoom) {

		if (Validator.isNull(digitalSalesRoom.getDescription())) {
			return group.getDescriptionMap();
		}

		return HashMapBuilder.put(
			LocaleUtil.getDefault(), digitalSalesRoom.getDescription()
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

	private Map<Locale, String> _getNameMap(
		Group group, DigitalSalesRoom digitalSalesRoom) {

		if (Validator.isNull(digitalSalesRoom.getName())) {
			return group.getNameMap();
		}

		return HashMapBuilder.put(
			LocaleUtil.getDefault(), digitalSalesRoom.getName()
		).build();
	}

	private ObjectDefinition _getObjectDefinition() throws Exception {
		return _objectDefinitionLocalService.
			getObjectDefinitionByExternalReferenceCode(
				"L_DSR_ROOM", contextCompany.getCompanyId());
	}

	private Map<String, Serializable> _getProperties(
		DigitalSalesRoom digitalSalesRoom, Group group) {

		return HashMapBuilder.<String, Serializable>put(
			"accountId",
			() -> {
				if (GetterUtil.getLong(digitalSalesRoom.getAccountId()) == 0) {
					return null;
				}

				AccountEntry accountEntry =
					_accountEntryService.getAccountEntry(
						digitalSalesRoom.getAccountId());

				return accountEntry.getAccountEntryId();
			}
		).put(
			"banner",
			() -> {
				FileEntry fileEntry = digitalSalesRoom.getBanner();

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
			"channelId", digitalSalesRoom::getChannelId
		).put(
			"channelName", digitalSalesRoom::getChannelName
		).put(
			"clientLogo",
			() -> {
				FileEntry fileEntry = digitalSalesRoom.getClientLogo();

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
			"clientName", digitalSalesRoom::getClientName
		).put(
			"externalReferenceCode", group.getExternalReferenceCode()
		).put(
			"primaryColor", digitalSalesRoom::getPrimaryColor
		).put(
			"secondaryColor", digitalSalesRoom::getSecondaryColor
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

	private DigitalSalesRoom _toDigitalSalesRoom(
			Group group,
			com.liferay.object.model.ObjectEntry serviceBuilderObjectEntry)
		throws Exception {

		ModelResourcePermission<com.liferay.object.model.ObjectEntry>
			modelResourcePermission =
				ModelResourcePermissionRegistryUtil.getModelResourcePermission(
					serviceBuilderObjectEntry.getModelClassName());

		return _digitalSalesRoomDTOConverter.toDTO(
			new DigitalSalesRoomDTOConverterContext(
				true,
				HashMapBuilder.<String, Map<String, String>>put(
					"delete",
					_addAction(
						ActionKeys.DELETE, group.getGroupId(),
						"deleteDigitalSalesRoom", modelResourcePermission,
						serviceBuilderObjectEntry.getObjectEntryId())
				).put(
					"update",
					_addAction(
						ActionKeys.UPDATE, group.getGroupId(),
						"patchDigitalSalesRoom", modelResourcePermission,
						serviceBuilderObjectEntry.getObjectEntryId())
				).build(),
				_dtoConverterRegistry, group.getGroupId(),
				contextAcceptLanguage.getPreferredLocale(),
				serviceBuilderObjectEntry, contextUriInfo, contextUser),
			group);
	}

	private ObjectEntry _toObjectEntry(
		DigitalSalesRoom digitalSalesRoom, Group group, long templateGroupId) {

		return new ObjectEntry() {
			{
				setProperties(
					() -> {
						Map<String, Serializable> propertiesMap =
							_getProperties(digitalSalesRoom, group);

						if (templateGroupId > 0) {
							propertiesMap.put(
								"templateGroupId", templateGroupId);
						}

						return Collections.unmodifiableMap(propertiesMap);
					});
			}
		};
	}

	private void _updateDSRTemplateObjectEntryUsages(
			Group group, long objectDefinitionId, int value)
		throws Exception {

		com.liferay.object.model.ObjectEntry serviceBuilderObjectEntry =
			_objectEntryLocalService.getObjectEntry(
				group.getExternalReferenceCode(), group.getGroupId(),
				objectDefinitionId);

		Map<String, Serializable> values =
			serviceBuilderObjectEntry.getValues();

		values.put(
			"usages",
			Math.max(0, GetterUtil.getLong(values.get("usages")) + value));

		_objectEntryLocalService.partialUpdateObjectEntry(
			serviceBuilderObjectEntry.getUserId(),
			serviceBuilderObjectEntry.getObjectEntryId(),
			serviceBuilderObjectEntry.getObjectEntryFolderId(), values,
			new ServiceContext());
	}

	private void _updateFrontendTokensValues(
			DigitalSalesRoom digitalSalesRoom, Group group)
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
				digitalSalesRoom.getPrimaryColor(),
				digitalSalesRoom.getSecondaryColor()));

		_styleBookEntryLocalService.publishDraft(styleBookEntry);
	}

	private void _updateNestedResources(
		DigitalSalesRoom digitalSalesRoom, Group group) {

		UserAccountBrief[] userAccountBriefs =
			digitalSalesRoom.getUserAccountBriefs();

		if ((userAccountBriefs == null) ||
			ArrayUtil.isEmpty(userAccountBriefs)) {

			return;
		}

		_userAccountBriefResource.setContextAcceptLanguage(
			contextAcceptLanguage);
		_userAccountBriefResource.setContextCompany(contextCompany);
		_userAccountBriefResource.setContextUser(contextUser);
		_userAccountBriefResource.setContextUriInfo(contextUriInfo);

		for (UserAccountBrief userAccountBrief :
				digitalSalesRoom.getUserAccountBriefs()) {

			try {
				_userAccountBriefResource.postDigitalSalesRoomUserAccountBrief(
					group.getGroupId(), userAccountBrief);
			}
			catch (Exception exception) {
				if (log.isWarnEnabled()) {
					log.warn(exception);
				}
			}
		}
	}

	@Reference
	private AccountEntryService _accountEntryService;

	@Reference(
		target = "(component.name=com.liferay.headless.digital.sales.room.internal.dto.v1_0.converter.DigitalSalesRoomDTOConverter)"
	)
	private DTOConverter<Group, DigitalSalesRoom> _digitalSalesRoomDTOConverter;

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
	private ObjectEntryService _objectEntryService;

	@Reference
	private Portal _portal;

	@Reference
	private SiteInitializerRegistry _siteInitializerRegistry;

	@Reference
	private StyleBookEntryLocalService _styleBookEntryLocalService;

	@Reference
	private UserAccountBriefResource _userAccountBriefResource;

}