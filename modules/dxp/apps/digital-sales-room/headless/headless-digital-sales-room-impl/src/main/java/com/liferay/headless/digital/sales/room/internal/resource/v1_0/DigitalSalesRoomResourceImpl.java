/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.digital.sales.room.internal.resource.v1_0;

import com.liferay.headless.digital.sales.room.dto.v1_0.DigitalSalesRoom;
import com.liferay.headless.digital.sales.room.dto.v1_0.FileEntry;
import com.liferay.headless.digital.sales.room.resource.v1_0.DigitalSalesRoomResource;
import com.liferay.layout.util.LayoutServiceContextHelper;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.events.ServicePreAction;
import com.liferay.portal.events.ThemeServicePreAction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.servlet.DummyHttpServletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
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

import java.util.LinkedHashMap;

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
	public DigitalSalesRoom getDigitalSalesRoom(Long digitalSalesRoomId)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-66359")) {

			throw new UnsupportedOperationException();
		}

		return _toDigitalSalesRoom(_groupService.getGroup(digitalSalesRoomId));
	}

	@Override
	public Page<DigitalSalesRoom> getDigitalSalesRoomsPage(
			String search, Pagination pagination)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-66359")) {

			throw new UnsupportedOperationException();
		}

		long[] classNameIds = {_portal.getClassNameId(Group.class.getName())};
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
				this::_toDigitalSalesRoom),
			pagination,
			_groupService.searchCount(
				contextCompany.getCompanyId(), classNameIds, search, params));
	}

	@Override
	public DigitalSalesRoom postDigitalSalesRoom(
			DigitalSalesRoom digitalSalesRoom)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-66359")) {

			throw new UnsupportedOperationException();
		}

		Group group = _addGroup(
			digitalSalesRoom.getExternalReferenceCode(),
			digitalSalesRoom.getDescription(),
			digitalSalesRoom.getFriendlyUrlPath(),
			"com.liferay.digital.sales.room.site.initializer",
			digitalSalesRoom.getName());

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_DSR_ROOM", contextCompany.getCompanyId());

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

		objectEntryManager.addObjectEntry(
			defaultDTOConverterContext, objectDefinition,
			_toObjectEntry(digitalSalesRoom, group), group.getGroupKey());

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
				digitalSalesRoom.getPrimaryColor(),
				digitalSalesRoom.getSecondaryColor()));

		_styleBookEntryLocalService.publishDraft(styleBookEntry);

		return _toDigitalSalesRoom(group);
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

			Group group = _groupService.addGroup(
				externalReferenceCode, GroupConstants.DEFAULT_PARENT_GROUP_ID,
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

	private String _getFrontendTokensValues(
		String primaryColor, String secondaryColor) {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

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

	private DigitalSalesRoom _toDigitalSalesRoom(Group group) throws Exception {
		return _digitalSalesRoomDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				true, null, _dtoConverterRegistry, group.getGroupId(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser),
			group);
	}

	private com.liferay.object.rest.dto.v1_0.ObjectEntry _toObjectEntry(
		DigitalSalesRoom digitalSalesRoom, Group group) {

		return new com.liferay.object.rest.dto.v1_0.ObjectEntry() {
			{
				setProperties(
					() -> HashMapBuilder.<String, Object>put(
						"accountId", digitalSalesRoom.getAccountId()
					).put(
						"banner",
						() -> {
							FileEntry fileEntry = digitalSalesRoom.getBanner();

							if (fileEntry == null) {
								return null;
							}

							return HashMapBuilder.put(
								"fileBase64", fileEntry.getFileBase64()
							).put(
								"name",
								GetterUtil.getString(
									fileEntry.getFileName(),
									StringUtil.randomString())
							).build();
						}
					).put(
						"channelId", digitalSalesRoom.getChannelId()
					).put(
						"clientLogo",
						() -> {
							FileEntry fileEntry =
								digitalSalesRoom.getClientLogo();

							if (fileEntry == null) {
								return null;
							}

							return HashMapBuilder.put(
								"fileBase64", fileEntry.getFileBase64()
							).put(
								"name",
								GetterUtil.getString(
									fileEntry.getFileName(),
									StringUtil.randomString())
							).build();
						}
					).put(
						"clientName", digitalSalesRoom.getClientName()
					).put(
						"externalReferenceCode",
						group.getExternalReferenceCode()
					).put(
						"primaryColor", digitalSalesRoom.getPrimaryColor()
					).put(
						"secondaryColor", digitalSalesRoom.getSecondaryColor()
					).build());
			}
		};
	}

	@Reference(
		target = "(component.name=com.liferay.headless.digital.sales.room.internal.dto.v1_0.converter.DigitalSalesRoomDTOConverter)"
	)
	private DTOConverter<Group, DigitalSalesRoom> _digitalSalesRoomDTOConverter;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private GroupService _groupService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private LayoutServiceContextHelper _layoutServiceContextHelper;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryManagerRegistry _objectEntryManagerRegistry;

	@Reference
	private Portal _portal;

	@Reference
	private SiteInitializerRegistry _siteInitializerRegistry;

	@Reference
	private StyleBookEntryLocalService _styleBookEntryLocalService;

}