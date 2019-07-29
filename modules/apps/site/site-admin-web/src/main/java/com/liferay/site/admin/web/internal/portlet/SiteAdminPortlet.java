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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.AvailableLocaleException;
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
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.exception.NoSuchLayoutSetException;
import com.liferay.portal.kernel.exception.PendingBackgroundTaskException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.RemoteOptionsException;
import com.liferay.portal.kernel.exception.RequiredGroupException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.model.MembershipRequest;
import com.liferay.portal.kernel.model.MembershipRequestConstants;
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
import com.liferay.portal.kernel.util.ArrayUtil;
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
import com.liferay.site.constants.SiteWebKeys;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.site.initializer.SiteInitializerRegistry;
import com.liferay.site.util.GroupSearchProvider;
import com.liferay.sites.kernel.util.Sites;
import com.liferay.sites.kernel.util.SitesUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
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
				"historyKey", getHistoryKey(actionRequest, actionResponse));
			siteAdministrationURL.setParameter(
				"redirect", siteAdministrationURL.toString());

			jsonObject.put("redirectURL", siteAdministrationURL.toString());

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse, jsonObject);
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			hideDefaultSuccessMessage(actionRequest);

			groupExceptionRequestHandler.handlePortalException(
				actionRequest, actionResponse, pe);
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

	public void deleteGroups(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long[] deleteGroupIds = null;

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		if (groupId > 0) {
			deleteGroupIds = new long[] {groupId};
		}
		else {
			deleteGroupIds = ParamUtil.getLongValues(actionRequest, "rowIds");
		}

		for (long deleteGroupId : deleteGroupIds) {
			groupService.deleteGroup(deleteGroupId);

			LiveUsers.deleteGroup(themeDisplay.getCompanyId(), deleteGroupId);
		}
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
			"historyKey", getHistoryKey(actionRequest, actionResponse));
		siteAdministrationURL.setParameter(
			"redirect", siteAdministrationURL.toString());

		actionRequest.setAttribute(
			WebKeys.REDIRECT, siteAdministrationURL.toString());

		sendRedirect(actionRequest, actionResponse);
	}

	public void editGroupAssignments(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		long[] removeUserIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "removeUserIds"), 0L);

		removeUserIds = filterRemoveUserIds(groupId, removeUserIds);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		userService.unsetGroupUsers(groupId, removeUserIds, serviceContext);

		LiveUsers.leaveGroup(
			themeDisplay.getCompanyId(), groupId, removeUserIds);
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

	protected long[] filterRemoveUserIds(long groupId, long[] userIds)
		throws Exception {

		Set<Long> filteredUserIds = new HashSet<>();

		for (long userId : userIds) {
			if (userLocalService.hasGroupUser(groupId, userId)) {
				filteredUserIds.add(userId);
			}
		}

		return ArrayUtil.toArray(filteredUserIds.toArray(new Long[0]));
	}

	protected String getHistoryKey(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		return http.getParameter(
			redirect, actionResponse.getNamespace() + "historyKey", false);
	}

	protected Group getLiveGroup(PortletRequest portletRequest)
		throws PortalException {

		long liveGroupId = ParamUtil.getLong(portletRequest, "liveGroupId");

		if (liveGroupId > 0) {
			return groupLocalService.getGroup(liveGroupId);
		}

		return null;
	}

	protected long getRefererGroupId(ThemeDisplay themeDisplay)
		throws Exception {

		long refererGroupId = 0;

		try {
			Layout refererLayout = layoutLocalService.getLayout(
				themeDisplay.getRefererPlid());

			refererGroupId = refererLayout.getGroupId();
		}
		catch (NoSuchLayoutException nsle) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(nsle, nsle);
			}
		}

		return refererGroupId;
	}

	protected List<Long> getRoleIds(PortletRequest portletRequest)
		throws Exception {

		List<Long> roleIds = new ArrayList<>();

		long[] siteRolesRoleIds = ArrayUtil.unique(
			ParamUtil.getLongValues(
				portletRequest, "siteRolesSearchContainerPrimaryKeys"));

		for (long siteRolesRoleId : siteRolesRoleIds) {
			if (siteRolesRoleId == 0) {
				continue;
			}

			roleIds.add(siteRolesRoleId);
		}

		return roleIds;
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

	protected List<Long> getTeamIds(PortletRequest portletRequest)
		throws Exception {

		List<Long> teamIds = new ArrayList<>();

		long[] teamsTeamIds = ArrayUtil.unique(
			ParamUtil.getLongValues(
				portletRequest, "teamsSearchContainerPrimaryKeys"));

		for (long teamsTeamId : teamsTeamIds) {
			if (teamsTeamId == 0) {
				continue;
			}

			teamIds.add(teamsTeamId);
		}

		return teamIds;
	}

	@Override
	protected boolean isSessionErrorException(Throwable cause) {
		if (cause instanceof AssetCategoryException ||
			cause instanceof AssetTagException ||
			cause instanceof AuthException ||
			cause instanceof DuplicateGroupException ||
			cause instanceof GroupFriendlyURLException ||
			cause instanceof GroupInheritContentException ||
			cause instanceof GroupKeyException ||
			cause instanceof GroupNameException ||
			cause instanceof GroupParentException ||
			cause instanceof LayoutSetVirtualHostException ||
			cause instanceof LocaleException ||
			cause instanceof NoSuchBackgroundTaskException ||
			cause instanceof NoSuchLayoutSetException ||
			cause instanceof PendingBackgroundTaskException ||
			cause instanceof RemoteAuthException ||
			cause instanceof RemoteExportException ||
			cause instanceof RemoteOptionsException ||
			cause instanceof RequiredGroupException ||
			cause instanceof SystemException ||
			super.isSessionErrorException(cause)) {

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

	protected TreeMap<String, String> toTreeMap(
			ActionRequest actionRequest, String parameterPrefix,
			Set<Locale> availableLocales)
		throws AvailableLocaleException {

		TreeMap<String, String> treeMap = new TreeMap<>();

		String[] virtualHostNames = ParamUtil.getStringValues(
			actionRequest, parameterPrefix + "Name[]");
		String[] virtualHostLocales = ParamUtil.getStringValues(
			actionRequest, parameterPrefix + "Locale[]");

		for (int i = 0; i < virtualHostNames.length; i++) {
			String virtualHostName = virtualHostNames[i];

			String virtualHostLocale = virtualHostLocales[i];

			if (Validator.isNotNull(virtualHostLocale)) {
				Locale locale = LocaleUtil.fromLanguageId(virtualHostLocale);

				if (!availableLocales.contains(locale)) {
					throw new AvailableLocaleException(virtualHostLocale);
				}
			}

			treeMap.put(virtualHostName, virtualHostLocale);
		}

		return treeMap;
	}

	protected void updateActive(ActionRequest actionRequest, boolean active)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		if ((groupId == themeDisplay.getDoAsGroupId()) ||
			(groupId == themeDisplay.getScopeGroupId()) ||
			(groupId == getRefererGroupId(themeDisplay))) {

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

			UnicodeProperties unicodeProperties =
				PropertiesParamUtil.getProperties(
					actionRequest, "TypeSettingsProperties--");

			Locale defaultLocale = LocaleUtil.fromLanguageId(
				unicodeProperties.getProperty("languageId"));

			if (!liveGroup.isGuest()) {
				validateDefaultLocaleGroupName(nameMap, defaultLocale);
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

		// Settings

		UnicodeProperties typeSettingsProperties =
			liveGroup.getTypeSettingsProperties();

		String customJspServletContextName = ParamUtil.getString(
			actionRequest, "customJspServletContextName",
			typeSettingsProperties.getProperty("customJspServletContextName"));

		typeSettingsProperties.setProperty(
			"customJspServletContextName", customJspServletContextName);

		typeSettingsProperties.setProperty(
			"defaultSiteRoleIds",
			ListUtil.toString(getRoleIds(actionRequest), StringPool.BLANK));
		typeSettingsProperties.setProperty(
			"defaultTeamIds",
			ListUtil.toString(getTeamIds(actionRequest), StringPool.BLANK));

		String[] analyticsTypes = PrefsPropsUtil.getStringArray(
			themeDisplay.getCompanyId(), PropsKeys.ADMIN_ANALYTICS_TYPES,
			StringPool.NEW_LINE);

		for (String analyticsType : analyticsTypes) {
			if (StringUtil.equalsIgnoreCase(analyticsType, "google")) {
				String googleAnalyticsId = ParamUtil.getString(
					actionRequest, "googleAnalyticsId",
					typeSettingsProperties.getProperty("googleAnalyticsId"));

				typeSettingsProperties.setProperty(
					"googleAnalyticsId", googleAnalyticsId);
			}
			else {
				String analyticsScript = ParamUtil.getString(
					actionRequest, Sites.ANALYTICS_PREFIX + analyticsType,
					typeSettingsProperties.getProperty(analyticsType));

				typeSettingsProperties.setProperty(
					Sites.ANALYTICS_PREFIX + analyticsType, analyticsScript);
			}
		}

		boolean trashEnabled = ParamUtil.getBoolean(
			actionRequest, "trashEnabled",
			GetterUtil.getBoolean(
				typeSettingsProperties.getProperty("trashEnabled"), true));

		typeSettingsProperties.setProperty(
			"trashEnabled", String.valueOf(trashEnabled));

		int trashEntriesMaxAgeCompany = PrefsPropsUtil.getInteger(
			themeDisplay.getCompanyId(), PropsKeys.TRASH_ENTRIES_MAX_AGE);

		int trashEntriesMaxAgeGroup = ParamUtil.getInteger(
			actionRequest, "trashEntriesMaxAge");

		if (trashEntriesMaxAgeGroup <= 0) {
			trashEntriesMaxAgeGroup = GetterUtil.getInteger(
				typeSettingsProperties.getProperty("trashEntriesMaxAge"),
				trashEntriesMaxAgeCompany);
		}

		if (trashEntriesMaxAgeGroup != trashEntriesMaxAgeCompany) {
			typeSettingsProperties.setProperty(
				"trashEntriesMaxAge",
				String.valueOf(GetterUtil.getInteger(trashEntriesMaxAgeGroup)));
		}
		else {
			typeSettingsProperties.remove("trashEntriesMaxAge");
		}

		int contentSharingWithChildrenEnabled = ParamUtil.getInteger(
			actionRequest, "contentSharingWithChildrenEnabled",
			GetterUtil.getInteger(
				typeSettingsProperties.getProperty(
					"contentSharingWithChildrenEnabled"),
				Sites.CONTENT_SHARING_WITH_CHILDREN_DEFAULT_VALUE));

		typeSettingsProperties.setProperty(
			"contentSharingWithChildrenEnabled",
			String.valueOf(contentSharingWithChildrenEnabled));

		UnicodeProperties formTypeSettingsProperties =
			PropertiesParamUtil.getProperties(
				actionRequest, "TypeSettingsProperties--");

		boolean inheritLocales = GetterUtil.getBoolean(
			typeSettingsProperties.getProperty("inheritLocales"));

		if (formTypeSettingsProperties.containsKey("inheritLocales")) {
			inheritLocales = GetterUtil.getBoolean(
				formTypeSettingsProperties.getProperty("inheritLocales"));
		}

		if (inheritLocales) {
			formTypeSettingsProperties.setProperty(
				PropsKeys.LOCALES,
				StringUtil.merge(
					LocaleUtil.toLanguageIds(
						LanguageUtil.getAvailableLocales())));
		}

		if (formTypeSettingsProperties.containsKey(PropsKeys.LOCALES) &&
			Validator.isNull(
				formTypeSettingsProperties.getProperty(PropsKeys.LOCALES))) {

			throw new LocaleException(
				LocaleException.TYPE_DEFAULT,
				"Must have at least one valid locale for site " + liveGroupId);
		}

		typeSettingsProperties.putAll(formTypeSettingsProperties);

		UnicodeProperties ratingsTypeProperties =
			PropertiesParamUtil.getProperties(actionRequest, "RatingsType--");

		for (String propertyKey : ratingsTypeProperties.keySet()) {
			String newRatingsType = ratingsTypeProperties.getProperty(
				propertyKey);

			String oldRatingsType = typeSettingsProperties.getProperty(
				propertyKey);

			if (newRatingsType.equals(oldRatingsType)) {
				continue;
			}

			if (RatingsType.isValid(newRatingsType)) {
				typeSettingsProperties.put(propertyKey, newRatingsType);
			}
			else {
				typeSettingsProperties.remove(propertyKey);
			}
		}

		// Virtual hosts

		LayoutSet publicLayoutSet = liveGroup.getPublicLayoutSet();

		Set<Locale> availableLocales = LanguageUtil.getAvailableLocales(
			liveGroup.getGroupId());

		layoutSetService.updateVirtualHosts(
			liveGroup.getGroupId(), false,
			toTreeMap(actionRequest, "publicVirtualHost", availableLocales));

		LayoutSet privateLayoutSet = liveGroup.getPrivateLayoutSet();

		layoutSetService.updateVirtualHosts(
			liveGroup.getGroupId(), true,
			toTreeMap(actionRequest, "privateVirtualHost", availableLocales));

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
				toTreeMap(
					actionRequest, "stagingPublicVirtualHost",
					availableLocales));

			layoutSetService.updateVirtualHosts(
				stagingGroup.getGroupId(), true,
				toTreeMap(
					actionRequest, "stagingPrivateVirtualHost",
					availableLocales));

			UnicodeProperties stagedGroupTypeSettingsProperties =
				stagingGroup.getTypeSettingsProperties();

			stagedGroupTypeSettingsProperties.putAll(
				formTypeSettingsProperties);

			groupService.updateGroup(
				stagingGroup.getGroupId(),
				stagedGroupTypeSettingsProperties.toString());
		}

		liveGroup = groupService.updateGroup(
			liveGroup.getGroupId(), typeSettingsProperties.toString());

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

	protected void validateDefaultLocaleGroupName(
			Map<Locale, String> nameMap, Locale defaultLocale)
		throws PortalException {

		if ((nameMap == null) || Validator.isNull(nameMap.get(defaultLocale))) {
			throw new GroupNameException();
		}
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

	private static final Log _log = LogFactoryUtil.getLog(
		SiteAdminPortlet.class);

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