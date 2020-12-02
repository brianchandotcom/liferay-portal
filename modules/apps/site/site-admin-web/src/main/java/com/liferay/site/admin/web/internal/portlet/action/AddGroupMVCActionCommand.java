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

import com.liferay.layout.seo.service.LayoutSEOSiteLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.change.tracking.CTTransactionException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.LocaleException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.MembershipRequest;
import com.liferay.portal.kernel.model.MembershipRequestConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.service.LayoutSetService;
import com.liferay.portal.kernel.service.MembershipRequestLocalService;
import com.liferay.portal.kernel.service.MembershipRequestService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.servlet.MultiSessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.liveusers.LiveUsers;
import com.liferay.ratings.kernel.RatingsType;
import com.liferay.site.admin.web.internal.constants.SiteAdminConstants;
import com.liferay.site.admin.web.internal.constants.SiteAdminPortletKeys;
import com.liferay.site.admin.web.internal.handler.GroupExceptionRequestHandler;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.site.initializer.SiteInitializerRegistry;
import com.liferay.sites.kernel.util.Sites;
import com.liferay.sites.kernel.util.SitesUtil;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

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
		"javax.portlet.name=" + SiteAdminPortletKeys.SITE_ADMIN,
		"mvc.command.name=/site_admin/add_group"
	},
	service = MVCActionCommand.class
)
public class AddGroupMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			Callable<Group> groupCallable = new GroupCallable(actionRequest);

			Group group = TransactionInvokerUtil.invoke(
				_transactionConfig, groupCallable);

			long liveGroupId = ParamUtil.getLong(actionRequest, "liveGroupId");

			if (liveGroupId <= 0) {
				hideDefaultSuccessMessage(actionRequest);

				MultiSessionMessages.add(
					actionRequest,
					SiteAdminPortletKeys.SITE_SETTINGS + "requestProcessed");
			}

			PortletURL siteAdministrationURL = _getSiteAdministrationURL(
				actionRequest, group);

			siteAdministrationURL.setParameter(
				"historyKey",
				ActionUtil.getHistoryKey(actionRequest, actionResponse));
			siteAdministrationURL.setParameter(
				"redirect", siteAdministrationURL.toString());

			jsonObject.put("redirectURL", siteAdministrationURL.toString());

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse, jsonObject);
		}
		catch (CTTransactionException ctTransactionException) {
			PortletURL redirectURL = _portal.getControlPanelPortletURL(
				actionRequest, SiteAdminPortletKeys.SITE_ADMIN,
				PortletRequest.RENDER_PHASE);

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse,
				JSONUtil.put("redirectURL", redirectURL.toString()));

			throw ctTransactionException;
		}
		catch (PortalException portalException) {
			hideDefaultSuccessMessage(actionRequest);

			_groupExceptionRequestHandler.handlePortalException(
				actionRequest, actionResponse, portalException);
		}
		catch (Throwable throwable) {
			throw new Exception(throwable);
		}
	}

	private PortletURL _getSiteAdministrationURL(
		ActionRequest actionRequest, Group group) {

		String portletId = SiteAdminPortletKeys.SITE_ADMIN;

		long liveGroupId = ParamUtil.getLong(actionRequest, "liveGroupId");

		if (liveGroupId <= 0) {
			portletId = SiteAdminPortletKeys.SITE_SETTINGS;
		}

		return _portal.getControlPanelPortletURL(
			actionRequest, group, portletId, 0, 0, PortletRequest.RENDER_PHASE);
	}

	private Group _updateGroup(ActionRequest actionRequest) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long liveGroupId = ParamUtil.getLong(actionRequest, "liveGroupId");

		long defaultParentGroupId = ParamUtil.getLong(
			actionRequest, "parentGroupId",
			GroupConstants.DEFAULT_PARENT_GROUP_ID);

		long parentGroupId = ParamUtil.getLong(
			actionRequest, "parentGroupSearchContainerPrimaryKeys",
			defaultParentGroupId);

		Map<Locale, String> nameMap = null;
		Map<Locale, String> descriptionMap = null;
		int type = 0;
		String friendlyURL = null;
		boolean inheritContent = false;
		boolean active = false;
		boolean manualMembership = true;

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

		Group liveGroup = null;

		if (liveGroupId <= 0) {

			// Add group

			String name = ParamUtil.getString(actionRequest, "name");
			nameMap = LocalizationUtil.getLocalizationMap(
				actionRequest, "name");
			descriptionMap = LocalizationUtil.getLocalizationMap(
				actionRequest, "description");
			type = ParamUtil.getInteger(
				actionRequest, "type", GroupConstants.TYPE_SITE_OPEN);
			friendlyURL = ParamUtil.getString(
				actionRequest, "groupFriendlyURL");
			manualMembership = ParamUtil.getBoolean(
				actionRequest, "manualMembership", true);
			inheritContent = ParamUtil.getBoolean(
				actionRequest, "inheritContent");
			active = ParamUtil.getBoolean(actionRequest, "active", true);
			long userId = _portal.getUserId(actionRequest);

			if (Validator.isNotNull(name)) {
				nameMap.put(LocaleUtil.getDefault(), name);
			}

			liveGroup = _groupService.addGroup(
				parentGroupId, GroupConstants.DEFAULT_LIVE_GROUP_ID, nameMap,
				descriptionMap, type, manualMembership, membershipRestriction,
				friendlyURL, true, inheritContent, active, serviceContext);

			LiveUsers.joinGroup(
				themeDisplay.getCompanyId(), liveGroup.getGroupId(), userId);
		}
		else {

			// Update group

			liveGroup = _groupLocalService.getGroup(liveGroupId);

			nameMap = LocalizationUtil.getLocalizationMap(
				actionRequest, "name", liveGroup.getNameMap());
			descriptionMap = LocalizationUtil.getLocalizationMap(
				actionRequest, "description", liveGroup.getDescriptionMap());
			type = ParamUtil.getInteger(
				actionRequest, "type", liveGroup.getType());
			manualMembership = ParamUtil.getBoolean(
				actionRequest, "manualMembership",
				liveGroup.isManualMembership());
			friendlyURL = ParamUtil.getString(
				actionRequest, "groupFriendlyURL", liveGroup.getFriendlyURL());
			inheritContent = ParamUtil.getBoolean(
				actionRequest, "inheritContent", liveGroup.isInheritContent());
			active = ParamUtil.getBoolean(
				actionRequest, "active", liveGroup.isActive());

			if (!liveGroup.isGuest() && !liveGroup.isOrganization()) {
				UnicodeProperties unicodeProperties =
					PropertiesParamUtil.getProperties(
						actionRequest, "TypeSettingsProperties--");

				Locale defaultLocale = LocaleUtil.fromLanguageId(
					unicodeProperties.getProperty("languageId"));

				ActionUtil.validateDefaultLocaleGroupName(
					nameMap, defaultLocale);
			}

			liveGroup = _groupService.updateGroup(
				liveGroupId, parentGroupId, nameMap, descriptionMap, type,
				manualMembership, membershipRestriction, friendlyURL,
				inheritContent, active, serviceContext);

			if (type == GroupConstants.TYPE_SITE_OPEN) {
				List<MembershipRequest> membershipRequests =
					_membershipRequestLocalService.search(
						liveGroupId, MembershipRequestConstants.STATUS_PENDING,
						QueryUtil.ALL_POS, QueryUtil.ALL_POS);

				for (MembershipRequest membershipRequest : membershipRequests) {
					_membershipRequestService.updateStatus(
						membershipRequest.getMembershipRequestId(),
						themeDisplay.translate(
							"your-membership-has-been-approved"),
						MembershipRequestConstants.STATUS_APPROVED,
						serviceContext);

					LiveUsers.joinGroup(
						themeDisplay.getCompanyId(),
						membershipRequest.getGroupId(),
						new long[] {membershipRequest.getUserId()});
				}
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

		typeSettingsUnicodeProperties.setProperty(
			"defaultSiteRoleIds",
			ListUtil.toString(
				ActionUtil.getRoleIds(actionRequest), StringPool.BLANK));
		typeSettingsUnicodeProperties.setProperty(
			"defaultTeamIds",
			ListUtil.toString(
				ActionUtil.getTeamIds(actionRequest), StringPool.BLANK));

		String[] analyticsTypes = PrefsPropsUtil.getStringArray(
			themeDisplay.getCompanyId(), PropsKeys.ADMIN_ANALYTICS_TYPES,
			StringPool.NEW_LINE);

		for (String analyticsType : analyticsTypes) {
			if (StringUtil.equalsIgnoreCase(analyticsType, "google")) {
				String googleAnalyticsCreateCustomConfiguration =
					ParamUtil.getString(
						actionRequest,
						"googleAnalyticsCreateCustomConfiguration",
						typeSettingsUnicodeProperties.getProperty(
							"googleAnalyticsCreateCustomConfiguration"));

				typeSettingsUnicodeProperties.setProperty(
					"googleAnalyticsCreateCustomConfiguration",
					googleAnalyticsCreateCustomConfiguration);

				String googleAnalyticsCustomConfiguration = ParamUtil.getString(
					actionRequest, "googleAnalyticsCustomConfiguration",
					typeSettingsUnicodeProperties.getProperty(
						"googleAnalyticsCustomConfiguration"));

				typeSettingsUnicodeProperties.setProperty(
					"googleAnalyticsCustomConfiguration",
					googleAnalyticsCustomConfiguration);

				String googleAnalyticsId = ParamUtil.getString(
					actionRequest, "googleAnalyticsId",
					typeSettingsUnicodeProperties.getProperty(
						"googleAnalyticsId"));

				typeSettingsUnicodeProperties.setProperty(
					"googleAnalyticsId", googleAnalyticsId);
			}
			else {
				String analyticsScript = ParamUtil.getString(
					actionRequest, Sites.ANALYTICS_PREFIX + analyticsType,
					typeSettingsUnicodeProperties.getProperty(analyticsType));

				typeSettingsUnicodeProperties.setProperty(
					Sites.ANALYTICS_PREFIX + analyticsType, analyticsScript);
			}
		}

		boolean trashEnabled = ParamUtil.getBoolean(
			actionRequest, "trashEnabled",
			GetterUtil.getBoolean(
				typeSettingsUnicodeProperties.getProperty("trashEnabled"),
				true));

		typeSettingsUnicodeProperties.setProperty(
			"trashEnabled", String.valueOf(trashEnabled));

		int trashEntriesMaxAgeCompany = PrefsPropsUtil.getInteger(
			themeDisplay.getCompanyId(), PropsKeys.TRASH_ENTRIES_MAX_AGE);

		int trashEntriesMaxAgeGroup = ParamUtil.getInteger(
			actionRequest, "trashEntriesMaxAge");

		if (trashEntriesMaxAgeGroup <= 0) {
			trashEntriesMaxAgeGroup = GetterUtil.getInteger(
				typeSettingsUnicodeProperties.getProperty("trashEntriesMaxAge"),
				trashEntriesMaxAgeCompany);
		}

		if (trashEntriesMaxAgeGroup != trashEntriesMaxAgeCompany) {
			typeSettingsUnicodeProperties.setProperty(
				"trashEntriesMaxAge",
				String.valueOf(GetterUtil.getInteger(trashEntriesMaxAgeGroup)));
		}
		else {
			typeSettingsUnicodeProperties.remove("trashEntriesMaxAge");
		}

		int contentSharingWithChildrenEnabled = ParamUtil.getInteger(
			actionRequest, "contentSharingWithChildrenEnabled",
			GetterUtil.getInteger(
				typeSettingsUnicodeProperties.getProperty(
					"contentSharingWithChildrenEnabled"),
				Sites.CONTENT_SHARING_WITH_CHILDREN_DEFAULT_VALUE));

		typeSettingsUnicodeProperties.setProperty(
			"contentSharingWithChildrenEnabled",
			String.valueOf(contentSharingWithChildrenEnabled));

		UnicodeProperties formTypeSettingsUnicodeProperties =
			PropertiesParamUtil.getProperties(
				actionRequest, "TypeSettingsProperties--");

		boolean inheritLocales = GetterUtil.getBoolean(
			typeSettingsUnicodeProperties.getProperty("inheritLocales"));

		if (formTypeSettingsUnicodeProperties.containsKey("inheritLocales")) {
			inheritLocales = GetterUtil.getBoolean(
				formTypeSettingsUnicodeProperties.getProperty(
					"inheritLocales"));
		}

		if (inheritLocales) {
			formTypeSettingsUnicodeProperties.setProperty(
				PropsKeys.LOCALES,
				StringUtil.merge(
					LocaleUtil.toLanguageIds(
						LanguageUtil.getAvailableLocales())));

			User user = themeDisplay.getDefaultUser();

			formTypeSettingsUnicodeProperties.setProperty(
				"languageId", user.getLanguageId());
		}

		if (formTypeSettingsUnicodeProperties.containsKey(PropsKeys.LOCALES) &&
			Validator.isNull(
				formTypeSettingsUnicodeProperties.getProperty(
					PropsKeys.LOCALES))) {

			throw new LocaleException(
				LocaleException.TYPE_DEFAULT,
				"Must have at least one valid locale for site " + liveGroupId);
		}

		typeSettingsUnicodeProperties.putAll(formTypeSettingsUnicodeProperties);

		UnicodeProperties ratingsTypeUnicodeProperties =
			PropertiesParamUtil.getProperties(actionRequest, "RatingsType--");

		for (String propertyKey : ratingsTypeUnicodeProperties.keySet()) {
			String newRatingsType = ratingsTypeUnicodeProperties.getProperty(
				propertyKey);

			String oldRatingsType = typeSettingsUnicodeProperties.getProperty(
				propertyKey);

			if (newRatingsType.equals(oldRatingsType)) {
				continue;
			}

			if (RatingsType.isValid(newRatingsType)) {
				typeSettingsUnicodeProperties.put(propertyKey, newRatingsType);
			}
			else {
				typeSettingsUnicodeProperties.remove(propertyKey);
			}
		}

		// Virtual hosts

		LayoutSet publicLayoutSet = liveGroup.getPublicLayoutSet();

		Set<Locale> availableLocales = LanguageUtil.getAvailableLocales(
			liveGroup.getGroupId());

		_layoutSetService.updateVirtualHosts(
			liveGroup.getGroupId(), false,
			ActionUtil.toTreeMap(
				actionRequest, "publicVirtualHost", availableLocales));

		LayoutSet privateLayoutSet = liveGroup.getPrivateLayoutSet();

		_layoutSetService.updateVirtualHosts(
			liveGroup.getGroupId(), true,
			ActionUtil.toTreeMap(
				actionRequest, "privateVirtualHost", availableLocales));

		// Staging

		if (liveGroup.hasStagingGroup()) {
			Group stagingGroup = liveGroup.getStagingGroup();

			friendlyURL = ParamUtil.getString(
				actionRequest, "stagingFriendlyURL",
				stagingGroup.getFriendlyURL());

			_groupService.updateFriendlyURL(
				stagingGroup.getGroupId(), friendlyURL);

			_layoutSetService.updateVirtualHosts(
				stagingGroup.getGroupId(), false,
				ActionUtil.toTreeMap(
					actionRequest, "stagingPublicVirtualHost",
					availableLocales));

			_layoutSetService.updateVirtualHosts(
				stagingGroup.getGroupId(), true,
				ActionUtil.toTreeMap(
					actionRequest, "stagingPrivateVirtualHost",
					availableLocales));

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

		String creationType = ParamUtil.getString(
			actionRequest, "creationType");

		if (Validator.isNull(creationType) ||
			creationType.equals(
				SiteAdminConstants.CREATION_TYPE_SITE_TEMPLATE)) {

			long privateLayoutSetPrototypeId = ParamUtil.getLong(
				actionRequest, "privateLayoutSetPrototypeId");
			long publicLayoutSetPrototypeId = ParamUtil.getLong(
				actionRequest, "publicLayoutSetPrototypeId");

			boolean privateLayoutSetPrototypeLinkEnabled = ParamUtil.getBoolean(
				actionRequest, "privateLayoutSetPrototypeLinkEnabled",
				privateLayoutSet.isLayoutSetPrototypeLinkEnabled());
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
		}
		else if (creationType.equals(
					SiteAdminConstants.CREATION_TYPE_INITIALIZER)) {

			String siteInitializerKey = ParamUtil.getString(
				actionRequest, "siteInitializerKey");

			SiteInitializer siteInitializer =
				_siteInitializerRegistry.getSiteInitializer(siteInitializerKey);

			if (!liveGroup.isStaged() || liveGroup.isStagedRemotely()) {
				siteInitializer.initialize(liveGroup.getGroupId());
			}
			else {
				Group stagingGroup = liveGroup.getStagingGroup();

				siteInitializer.initialize(stagingGroup.getGroupId());
			}
		}

		themeDisplay.setSiteGroupId(liveGroup.getGroupId());

		return liveGroup;
	}

	private static final int _LAYOUT_SET_VISIBILITY_PRIVATE = 1;

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private GroupExceptionRequestHandler _groupExceptionRequestHandler;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private GroupService _groupService;

	@Reference
	private LayoutSEOSiteLocalService _layoutSEOSiteLocalService;

	@Reference
	private LayoutSetService _layoutSetService;

	@Reference
	private MembershipRequestLocalService _membershipRequestLocalService;

	@Reference
	private MembershipRequestService _membershipRequestService;

	@Reference
	private Portal _portal;

	@Reference
	private SiteInitializerRegistry _siteInitializerRegistry;

	private class GroupCallable implements Callable<Group> {

		@Override
		public Group call() throws Exception {
			return _updateGroup(_actionRequest);
		}

		private GroupCallable(ActionRequest actionRequest) {
			_actionRequest = actionRequest;
		}

		private final ActionRequest _actionRequest;

	}

}