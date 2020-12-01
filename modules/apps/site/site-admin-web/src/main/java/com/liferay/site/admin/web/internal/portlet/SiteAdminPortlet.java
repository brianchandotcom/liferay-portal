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

package com.liferay.site.admin.web.internal.portlet;

import com.liferay.asset.kernel.exception.AssetCategoryException;
import com.liferay.asset.kernel.exception.AssetTagException;
import com.liferay.exportimport.kernel.exception.RemoteExportException;
import com.liferay.exportimport.kernel.staging.Staging;
import com.liferay.layout.seo.service.LayoutSEOSiteLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.change.tracking.CTTransactionException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.DuplicateGroupException;
import com.liferay.portal.kernel.exception.GroupFriendlyURLException;
import com.liferay.portal.kernel.exception.GroupInheritContentException;
import com.liferay.portal.kernel.exception.GroupKeyException;
import com.liferay.portal.kernel.exception.GroupNameException;
import com.liferay.portal.kernel.exception.GroupParentException;
import com.liferay.portal.kernel.exception.LayoutSetVirtualHostException;
import com.liferay.portal.kernel.exception.LocaleException;
import com.liferay.portal.kernel.exception.NoSuchBackgroundTaskException;
import com.liferay.portal.kernel.exception.NoSuchGroupException;
import com.liferay.portal.kernel.exception.NoSuchLayoutSetException;
import com.liferay.portal.kernel.exception.PendingBackgroundTaskException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.RemoteOptionsException;
import com.liferay.portal.kernel.exception.RequiredGroupException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.model.MembershipRequest;
import com.liferay.portal.kernel.model.MembershipRequestConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.auth.AuthException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.RemoteAuthException;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.LayoutSetPrototypeService;
import com.liferay.portal.kernel.service.LayoutSetService;
import com.liferay.portal.kernel.service.MembershipRequestLocalService;
import com.liferay.portal.kernel.service.MembershipRequestService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.TeamLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.servlet.MultiSessionMessages;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
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
import com.liferay.site.admin.web.internal.portlet.action.ActionUtil;
import com.liferay.site.constants.SiteWebKeys;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.site.initializer.SiteInitializerRegistry;
import com.liferay.site.util.GroupSearchProvider;
import com.liferay.sites.kernel.util.Sites;
import com.liferay.sites.kernel.util.SitesUtil;

import java.io.IOException;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-site-admin",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.icon=/icons/site_admin.png",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Sites Admin",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + SiteAdminPortletKeys.SITE_ADMIN,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator"
	},
	service = Portlet.class
)
public class SiteAdminPortlet extends MVCPortlet {

	public void activate(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		updateActive(actionRequest, true);
	}

	public void addGroup(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Throwable {

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

			PortletURL siteAdministrationURL = getSiteAdministrationURL(
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
			PortletURL redirectURL = portal.getControlPanelPortletURL(
				actionRequest, SiteAdminPortletKeys.SITE_ADMIN,
				PortletRequest.RENDER_PHASE);

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse,
				JSONUtil.put("redirectURL", redirectURL.toString()));

			throw ctTransactionException;
		}
		catch (PortalException portalException) {
			hideDefaultSuccessMessage(actionRequest);

			groupExceptionRequestHandler.handlePortalException(
				actionRequest, actionResponse, portalException);
		}
	}

	public void deactivate(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		updateActive(actionRequest, false);
	}

	public void deleteBackgroundTask(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long backgroundTaskId = ParamUtil.getLong(
			actionRequest, BackgroundTaskConstants.BACKGROUND_TASK_ID);

		backgroundTaskManager.deleteBackgroundTask(backgroundTaskId);
	}

	public void editGroup(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Throwable {

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

		PortletURL siteAdministrationURL = getSiteAdministrationURL(
			actionRequest, group);

		siteAdministrationURL.setParameter(
			"historyKey",
			ActionUtil.getHistoryKey(actionRequest, actionResponse));
		siteAdministrationURL.setParameter(
			"redirect", siteAdministrationURL.toString());

		actionRequest.setAttribute(
			WebKeys.REDIRECT, siteAdministrationURL.toString());

		sendRedirect(actionRequest, actionResponse);
	}

	/**
	 * Resets the number of failed merge attempts for the site template, which
	 * is accessed by retrieving the layout set prototype ID. Once the counter
	 * is reset, the modified site template is merged back into its linked site,
	 * which is accessed by retrieving the group ID and private layout set.
	 *
	 * <p>
	 * If the number of failed merge attempts is not equal to zero after the
	 * merge, an error key is submitted to {@link SessionErrors}.
	 * </p>
	 *
	 * @param  actionRequest the portlet request used to retrieve parameters
	 * @throws Exception if an exception occurred
	 */
	public void resetMergeFailCountAndMerge(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long layoutSetPrototypeId = ParamUtil.getLong(
			actionRequest, "layoutSetPrototypeId");

		LayoutSetPrototype layoutSetPrototype =
			layoutSetPrototypeService.getLayoutSetPrototype(
				layoutSetPrototypeId);

		SitesUtil.setMergeFailCount(layoutSetPrototype, 0);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		boolean privateLayoutSet = ParamUtil.getBoolean(
			actionRequest, "privateLayoutSet");

		LayoutSet layoutSet = layoutSetLocalService.getLayoutSet(
			groupId, privateLayoutSet);

		SitesUtil.resetPrototype(layoutSet);

		SitesUtil.mergeLayoutSetPrototypeLayouts(
			groupLocalService.getGroup(groupId), layoutSet);

		layoutSetPrototype = layoutSetPrototypeService.getLayoutSetPrototype(
			layoutSetPrototypeId);

		if (SitesUtil.getMergeFailCount(layoutSetPrototype) > 0) {
			SessionErrors.add(actionRequest, "resetMergeFailCountAndMerge");
		}
	}

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		resourceRequest.setAttribute(
			SiteWebKeys.GROUP_SEARCH_PROVIDER, groupSearchProvider);

		super.serveResource(resourceRequest, resourceResponse);
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		renderRequest.setAttribute(
			SiteWebKeys.GROUP_SEARCH_PROVIDER, groupSearchProvider);

		renderRequest.setAttribute(
			SiteWebKeys.SITE_INITIALIZER_REGISTRY, siteInitializerRegistry);

		if (SessionErrors.contains(
				renderRequest, NoSuchBackgroundTaskException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, NoSuchGroupException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, PrincipalException.getNestedClasses())) {

			include("/error.jsp", renderRequest, renderResponse);
		}
		else if (SessionErrors.contains(
					renderRequest, NoSuchLayoutSetException.class.getName())) {

			include("/view.jsp", renderRequest, renderResponse);
		}
		else {
			super.doDispatch(renderRequest, renderResponse);
		}
	}

	protected Group getLiveGroup(PortletRequest portletRequest)
		throws PortalException {

		long liveGroupId = ParamUtil.getLong(portletRequest, "liveGroupId");

		if (liveGroupId > 0) {
			return groupLocalService.getGroup(liveGroupId);
		}

		return null;
	}

	protected PortletURL getSiteAdministrationURL(
		ActionRequest actionRequest, Group group) {

		String portletId = SiteAdminPortletKeys.SITE_ADMIN;

		long liveGroupId = ParamUtil.getLong(actionRequest, "liveGroupId");

		if (liveGroupId <= 0) {
			portletId = SiteAdminPortletKeys.SITE_SETTINGS;
		}

		return portal.getControlPanelPortletURL(
			actionRequest, group, portletId, 0, 0, PortletRequest.RENDER_PHASE);
	}

	@Override
	protected boolean isSessionErrorException(Throwable throwable) {
		if (throwable instanceof AssetCategoryException ||
			throwable instanceof AssetTagException ||
			throwable instanceof AuthException ||
			throwable instanceof DuplicateGroupException ||
			throwable instanceof GroupFriendlyURLException ||
			throwable instanceof GroupInheritContentException ||
			throwable instanceof GroupKeyException ||
			throwable instanceof GroupNameException ||
			throwable instanceof GroupParentException ||
			throwable instanceof LayoutSetVirtualHostException ||
			throwable instanceof LocaleException ||
			throwable instanceof NoSuchBackgroundTaskException ||
			throwable instanceof NoSuchLayoutSetException ||
			throwable instanceof PendingBackgroundTaskException ||
			throwable instanceof RemoteAuthException ||
			throwable instanceof RemoteExportException ||
			throwable instanceof RemoteOptionsException ||
			throwable instanceof RequiredGroupException ||
			throwable instanceof SystemException ||
			super.isSessionErrorException(throwable)) {

			return true;
		}

		return false;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		this.groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	protected void setGroupSearchProvider(
		GroupSearchProvider groupSearchProvider) {

		this.groupSearchProvider = groupSearchProvider;
	}

	@Reference(unbind = "-")
	protected void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	@Reference(unbind = "-")
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		this.layoutLocalService = layoutLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutSetLocalService(
		LayoutSetLocalService layoutSetLocalService) {

		this.layoutSetLocalService = layoutSetLocalService;
	}

	@Reference(unbind = "-")
	protected void setLayoutSetPrototypeService(
		LayoutSetPrototypeService layoutSetPrototypeService) {

		this.layoutSetPrototypeService = layoutSetPrototypeService;
	}

	@Reference(unbind = "-")
	protected void setLayoutSetService(LayoutSetService layoutSetService) {
		this.layoutSetService = layoutSetService;
	}

	@Reference(unbind = "-")
	protected void setMembershipRequestLocalService(
		MembershipRequestLocalService membershipRequestLocalService) {

		this.membershipRequestLocalService = membershipRequestLocalService;
	}

	@Reference(unbind = "-")
	protected void setMembershipRequestService(
		MembershipRequestService membershipRequestService) {

		this.membershipRequestService = membershipRequestService;
	}

	@Reference(unbind = "-")
	protected void setRoleLocalService(RoleLocalService roleLocalService) {
		this.roleLocalService = roleLocalService;
	}

	@Reference(unbind = "-")
	protected void setTeamLocalService(TeamLocalService teamLocalService) {
		this.teamLocalService = teamLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserService(UserService userService) {
		this.userService = userService;
	}

	protected void updateActive(ActionRequest actionRequest, boolean active)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		if ((groupId == themeDisplay.getDoAsGroupId()) ||
			(groupId == themeDisplay.getScopeGroupId()) ||
			(groupId == ActionUtil.getRefererGroupId(themeDisplay))) {

			throw new RequiredGroupException.MustNotDeleteCurrentGroup(groupId);
		}

		Group group = groupService.getGroup(groupId);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			Group.class.getName(), actionRequest);

		groupService.updateGroup(
			groupId, group.getParentGroupId(), group.getNameMap(),
			group.getDescriptionMap(), group.getType(),
			group.isManualMembership(), group.getMembershipRestriction(),
			group.getFriendlyURL(), group.isInheritContent(), active,
			serviceContext);

		themeDisplay.setScopeGroupId(groupId);
	}

	protected Group updateGroup(ActionRequest actionRequest) throws Exception {
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
			long userId = portal.getUserId(actionRequest);

			if (Validator.isNotNull(name)) {
				nameMap.put(LocaleUtil.getDefault(), name);
			}

			liveGroup = groupService.addGroup(
				parentGroupId, GroupConstants.DEFAULT_LIVE_GROUP_ID, nameMap,
				descriptionMap, type, manualMembership, membershipRestriction,
				friendlyURL, true, inheritContent, active, serviceContext);

			LiveUsers.joinGroup(
				themeDisplay.getCompanyId(), liveGroup.getGroupId(), userId);
		}
		else {

			// Update group

			liveGroup = groupLocalService.getGroup(liveGroupId);

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

			liveGroup = groupService.updateGroup(
				liveGroupId, parentGroupId, nameMap, descriptionMap, type,
				manualMembership, membershipRestriction, friendlyURL,
				inheritContent, active, serviceContext);

			if (type == GroupConstants.TYPE_SITE_OPEN) {
				List<MembershipRequest> membershipRequests =
					membershipRequestLocalService.search(
						liveGroupId, MembershipRequestConstants.STATUS_PENDING,
						QueryUtil.ALL_POS, QueryUtil.ALL_POS);

				for (MembershipRequest membershipRequest : membershipRequests) {
					membershipRequestService.updateStatus(
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

		layoutSEOSiteLocalService.updateLayoutSEOSite(
			portal.getUserId(actionRequest), liveGroup.getGroupId(),
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

		layoutSetService.updateVirtualHosts(
			liveGroup.getGroupId(), false,
			ActionUtil.toTreeMap(
				actionRequest, "publicVirtualHost", availableLocales));

		LayoutSet privateLayoutSet = liveGroup.getPrivateLayoutSet();

		layoutSetService.updateVirtualHosts(
			liveGroup.getGroupId(), true,
			ActionUtil.toTreeMap(
				actionRequest, "privateVirtualHost", availableLocales));

		// Staging

		if (liveGroup.hasStagingGroup()) {
			Group stagingGroup = liveGroup.getStagingGroup();

			friendlyURL = ParamUtil.getString(
				actionRequest, "stagingFriendlyURL",
				stagingGroup.getFriendlyURL());

			groupService.updateFriendlyURL(
				stagingGroup.getGroupId(), friendlyURL);

			layoutSetService.updateVirtualHosts(
				stagingGroup.getGroupId(), false,
				ActionUtil.toTreeMap(
					actionRequest, "stagingPublicVirtualHost",
					availableLocales));

			layoutSetService.updateVirtualHosts(
				stagingGroup.getGroupId(), true,
				ActionUtil.toTreeMap(
					actionRequest, "stagingPrivateVirtualHost",
					availableLocales));

			UnicodeProperties stagedGroupTypeSettingsUnicodeProperties =
				stagingGroup.getTypeSettingsProperties();

			stagedGroupTypeSettingsUnicodeProperties.putAll(
				formTypeSettingsUnicodeProperties);

			groupService.updateGroup(
				stagingGroup.getGroupId(),
				stagedGroupTypeSettingsUnicodeProperties.toString());
		}

		liveGroup = groupService.updateGroup(
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
				siteInitializerRegistry.getSiteInitializer(siteInitializerKey);

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

	@Reference
	protected BackgroundTaskManager backgroundTaskManager;

	@Reference
	protected GroupExceptionRequestHandler groupExceptionRequestHandler;

	protected GroupLocalService groupLocalService;
	protected GroupSearchProvider groupSearchProvider;
	protected GroupService groupService;

	@Reference
	protected Http http;

	protected LayoutLocalService layoutLocalService;

	@Reference
	protected LayoutSEOSiteLocalService layoutSEOSiteLocalService;

	protected LayoutSetLocalService layoutSetLocalService;
	protected LayoutSetPrototypeService layoutSetPrototypeService;
	protected LayoutSetService layoutSetService;
	protected MembershipRequestLocalService membershipRequestLocalService;
	protected MembershipRequestService membershipRequestService;

	@Reference
	protected Portal portal;

	protected RoleLocalService roleLocalService;

	@Reference
	protected SiteInitializerRegistry siteInitializerRegistry;

	@Reference
	protected Staging staging;

	protected TeamLocalService teamLocalService;
	protected UserLocalService userLocalService;
	protected UserService userService;

	private static final int _LAYOUT_SET_VISIBILITY_PRIVATE = 1;

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	private class GroupCallable implements Callable<Group> {

		@Override
		public Group call() throws Exception {
			return updateGroup(_actionRequest);
		}

		private GroupCallable(ActionRequest actionRequest) {
			_actionRequest = actionRequest;
		}

		private final ActionRequest _actionRequest;

	}

}