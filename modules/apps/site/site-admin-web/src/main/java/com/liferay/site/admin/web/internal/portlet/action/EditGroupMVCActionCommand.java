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

package com.liferay.site.admin.web.internal.portlet.action;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.layout.seo.service.LayoutSEOSiteLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.GroupNameException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.MembershipRequest;
import com.liferay.portal.kernel.model.MembershipRequestConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseTransactionalMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.service.MembershipRequestLocalService;
import com.liferay.portal.kernel.service.MembershipRequestService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.liveusers.LiveUsers;
import com.liferay.sites.kernel.util.SitesUtil;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.SITE_SETTINGS,
		"mvc.command.name=/site_admin/edit_group"
	},
	service = MVCActionCommand.class
)
public class EditGroupMVCActionCommand
	extends BaseTransactionalMVCActionCommand {

	@Override
	protected void doTransactionalCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		_updateGroup(actionRequest, actionResponse);
	}

	private PortletURL _getSiteAdministrationURL(
		ActionRequest actionRequest, Group group) {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Group scopeGroup = themeDisplay.getScopeGroup();

		if (scopeGroup.isStagingGroup()) {
			group = group.getStagingGroup();
		}

		return _portal.getControlPanelPortletURL(
			actionRequest, group, ConfigurationAdminPortletKeys.SITE_SETTINGS,
			0, 0, PortletRequest.RENDER_PHASE);
	}

	private void _updateGroup(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long liveGroupId = ParamUtil.getLong(actionRequest, "liveGroupId");

		long defaultParentGroupId = ParamUtil.getLong(
			actionRequest, "parentGroupId",
			GroupConstants.DEFAULT_PARENT_GROUP_ID);

		long parentGroupId = ParamUtil.getLong(
			actionRequest, "parentGroupSearchContainerPrimaryKeys",
			defaultParentGroupId);

		int membershipRestriction =
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION;

		boolean actionRequestMembershipRestriction = ParamUtil.getBoolean(
			actionRequest, "membershipRestriction");

		if (actionRequestMembershipRestriction &&
			(parentGroupId != GroupConstants.DEFAULT_PARENT_GROUP_ID)) {

			membershipRestriction =
				GroupConstants.MEMBERSHIP_RESTRICTION_TO_PARENT_SITE_MEMBERS;
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			Group.class.getName(), actionRequest);

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		Group liveGroup = _groupLocalService.getGroup(liveGroupId);

		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name", liveGroup.getNameMap());
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(
				actionRequest, "description", liveGroup.getDescriptionMap());
		int type = ParamUtil.getInteger(
			actionRequest, "type", liveGroup.getType());
		boolean manualMembership = ParamUtil.getBoolean(
			actionRequest, "manualMembership", liveGroup.isManualMembership());
		boolean inheritContent = ParamUtil.getBoolean(
			actionRequest, "inheritContent", liveGroup.isInheritContent());
		boolean active = ParamUtil.getBoolean(
			actionRequest, "active", liveGroup.isActive());

		if (!liveGroup.isGuest() && !liveGroup.isOrganization()) {
			UnicodeProperties unicodeProperties =
				PropertiesParamUtil.getProperties(
					actionRequest, "TypeSettingsProperties--");

			Locale defaultLocale = LocaleUtil.fromLanguageId(
				unicodeProperties.getProperty("languageId"));

			_validateDefaultLocaleGroupName(nameMap, defaultLocale);
		}

		boolean redirect = !Objects.equals(
			friendlyURL, liveGroup.getFriendlyURL());

		liveGroup = _groupService.updateGroup(
			liveGroupId, parentGroupId, nameMap, descriptionMap, type,
			manualMembership, membershipRestriction, liveGroup.getFriendlyURL(),
			inheritContent, active, serviceContext);

		if (type == GroupConstants.TYPE_SITE_OPEN) {
			List<MembershipRequest> membershipRequests =
				_membershipRequestLocalService.search(
					liveGroupId, MembershipRequestConstants.STATUS_PENDING,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			for (MembershipRequest membershipRequest : membershipRequests) {
				_membershipRequestService.updateStatus(
					membershipRequest.getMembershipRequestId(),
					themeDisplay.translate("your-membership-has-been-approved"),
					MembershipRequestConstants.STATUS_APPROVED, serviceContext);

				LiveUsers.joinGroup(
					themeDisplay.getCompanyId(), membershipRequest.getGroupId(),
					new long[] {membershipRequest.getUserId()});
			}
		}

		boolean openGraphEnabled = ParamUtil.getBoolean(
			actionRequest, "openGraphEnabled", true);
		Map<Locale, String> openGraphImageAltMap =
			LocalizationUtil.getLocalizationMap(
				actionRequest, "openGraphImageAlt");
		long openGraphImageFileEntryId = ParamUtil.getLong(
			actionRequest, "openGraphImageFileEntryId");

		_layoutSEOSiteLocalService.updateLayoutSEOSite(
			_portal.getUserId(actionRequest), liveGroup.getGroupId(),
			openGraphEnabled, openGraphImageAltMap, openGraphImageFileEntryId,
			serviceContext);

		// Settings

		UnicodeProperties typeSettingsUnicodeProperties =
			liveGroup.getTypeSettingsProperties();

		String customJspServletContextName = ParamUtil.getString(
			actionRequest, "customJspServletContextName",
			typeSettingsUnicodeProperties.getProperty(
				"customJspServletContextName"));

		typeSettingsUnicodeProperties.setProperty(
			"customJspServletContextName", customJspServletContextName);

		UnicodeProperties formTypeSettingsUnicodeProperties =
			PropertiesParamUtil.getProperties(
				actionRequest, "TypeSettingsProperties--");

		typeSettingsUnicodeProperties.putAll(formTypeSettingsUnicodeProperties);

		// Staging

		if (liveGroup.hasStagingGroup()) {
			Group stagingGroup = liveGroup.getStagingGroup();

			UnicodeProperties stagedGroupTypeSettingsUnicodeProperties =
				stagingGroup.getTypeSettingsProperties();

			stagedGroupTypeSettingsUnicodeProperties.putAll(
				formTypeSettingsUnicodeProperties);

			_groupService.updateGroup(
				stagingGroup.getGroupId(),
				stagedGroupTypeSettingsUnicodeProperties.toString());
		}

		liveGroup = _groupService.updateGroup(
			liveGroup.getGroupId(), typeSettingsUnicodeProperties.toString());

		long privateLayoutSetPrototypeId = ParamUtil.getLong(
			actionRequest, "privateLayoutSetPrototypeId");
		long publicLayoutSetPrototypeId = ParamUtil.getLong(
			actionRequest, "publicLayoutSetPrototypeId");

		LayoutSet privateLayoutSet = liveGroup.getPrivateLayoutSet();

		boolean privateLayoutSetPrototypeLinkEnabled = ParamUtil.getBoolean(
			actionRequest, "privateLayoutSetPrototypeLinkEnabled",
			privateLayoutSet.isLayoutSetPrototypeLinkEnabled());

		LayoutSet publicLayoutSet = liveGroup.getPublicLayoutSet();

		boolean publicLayoutSetPrototypeLinkEnabled = ParamUtil.getBoolean(
			actionRequest, "publicLayoutSetPrototypeLinkEnabled",
			publicLayoutSet.isLayoutSetPrototypeLinkEnabled());

		if ((privateLayoutSetPrototypeId == 0) &&
			(publicLayoutSetPrototypeId == 0) &&
			!privateLayoutSetPrototypeLinkEnabled &&
			!publicLayoutSetPrototypeLinkEnabled) {

			long layoutSetPrototypeId = ParamUtil.getLong(
				actionRequest, "layoutSetPrototypeId");
			int layoutSetVisibility = ParamUtil.getInteger(
				actionRequest, "layoutSetVisibility");
			boolean layoutSetPrototypeLinkEnabled = ParamUtil.getBoolean(
				actionRequest, "layoutSetPrototypeLinkEnabled",
				layoutSetPrototypeId > 0);
			boolean layoutSetVisibilityPrivate = ParamUtil.getBoolean(
				actionRequest, "layoutSetVisibilityPrivate");

			if ((layoutSetVisibility == _LAYOUT_SET_VISIBILITY_PRIVATE) ||
				layoutSetVisibilityPrivate) {

				privateLayoutSetPrototypeId = layoutSetPrototypeId;

				privateLayoutSetPrototypeLinkEnabled =
					layoutSetPrototypeLinkEnabled;
			}
			else {
				publicLayoutSetPrototypeId = layoutSetPrototypeId;

				publicLayoutSetPrototypeLinkEnabled =
					layoutSetPrototypeLinkEnabled;
			}
		}

		if (!liveGroup.isStaged() || liveGroup.isStagedRemotely()) {
			SitesUtil.updateLayoutSetPrototypesLinks(
				liveGroup, publicLayoutSetPrototypeId,
				privateLayoutSetPrototypeId,
				publicLayoutSetPrototypeLinkEnabled,
				privateLayoutSetPrototypeLinkEnabled);
		}
		else {
			SitesUtil.updateLayoutSetPrototypesLinks(
				liveGroup.getStagingGroup(), publicLayoutSetPrototypeId,
				privateLayoutSetPrototypeId,
				publicLayoutSetPrototypeLinkEnabled,
				privateLayoutSetPrototypeLinkEnabled);
		}

		themeDisplay.setSiteGroupId(liveGroup.getGroupId());

		if (!redirect) {
			return;
		}

		PortletURL siteAdministrationURL = _getSiteAdministrationURL(
			actionRequest, liveGroup);

		siteAdministrationURL.setParameter(
			"redirect", siteAdministrationURL.toString());
		siteAdministrationURL.setParameter(
			"historyKey",
			ActionUtil.getHistoryKey(actionRequest, actionResponse));

		actionRequest.setAttribute(
			WebKeys.REDIRECT, siteAdministrationURL.toString());
	}

	private void _validateDefaultLocaleGroupName(
			Map<Locale, String> nameMap, Locale defaultLocale)
		throws Exception {

		if ((nameMap == null) || Validator.isNull(nameMap.get(defaultLocale))) {
			throw new GroupNameException();
		}
	}

	private static final int _LAYOUT_SET_VISIBILITY_PRIVATE = 1;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private GroupService _groupService;

	@Reference
	private LayoutSEOSiteLocalService _layoutSEOSiteLocalService;

	@Reference
	private MembershipRequestLocalService _membershipRequestLocalService;

	@Reference
	private MembershipRequestService _membershipRequestService;

	@Reference
	private Portal _portal;

}