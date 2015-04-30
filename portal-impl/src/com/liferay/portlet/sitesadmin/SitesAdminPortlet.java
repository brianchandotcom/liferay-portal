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

package com.liferay.portlet.sitesadmin;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

/**
 * @author Eudaldo Alonso
 */
public class SitesAdminPortlet extends MVCPortlet {

	public void deleteGroups(ActionRequest actionRequest) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long[] deleteGroupIds = null;

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		if (groupId > 0) {
			deleteGroupIds = new long[] {groupId};
		}
		else {
			deleteGroupIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "deleteGroupIds"), 0L);
		}

		for (long deleteGroupId : deleteGroupIds) {
			GroupServiceUtil.deleteGroup(deleteGroupId);

			LiveUsers.deleteGroup(themeDisplay.getCompanyId(), deleteGroupId);
		}
	}

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				Callable<Group> groupCallable = new GroupCallable(
					actionRequest);

				Group group = TransactionHandlerUtil.invoke(
					_transactionAttribute, groupCallable);

				if (cmd.equals(Constants.ADD)) {
					themeDisplay.setScopeGroupId(group.getGroupId());

					PortletURL siteAdministrationURL =
						PortalUtil.getSiteAdministrationURL(
							actionResponse, themeDisplay,
							PortletKeys.SITE_SETTINGS);

					String controlPanelURL = HttpUtil.setParameter(
						themeDisplay.getURLControlPanel(), "p_p_id",
						PortletKeys.SITES_ADMIN);

					controlPanelURL = HttpUtil.setParameter(
						controlPanelURL, "controlPanelCategory",
						themeDisplay.getControlPanelCategory());

					siteAdministrationURL.setParameter(
						"redirect", controlPanelURL);

					redirect = siteAdministrationURL.toString();

					hideDefaultSuccessMessage(actionRequest);

					MultiSessionMessages.add(
						actionRequest,
						PortletKeys.SITE_SETTINGS + "requestProcessed");
				}
				else {
					long newRefererPlid = getRefererPlid(
						group, themeDisplay.getScopeGroupId(), redirect);

					redirect = HttpUtil.setParameter(
						redirect, "doAsGroupId", group.getGroupId());
					redirect = HttpUtil.setParameter(
						redirect, "refererPlid", newRefererPlid);
				}
			}
			else if (cmd.equals(Constants.DEACTIVATE) ||
					 cmd.equals(Constants.RESTORE)) {

				updateActive(actionRequest, cmd);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteGroups(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse, redirect);
		}
		catch (Exception e) {
			if (e instanceof NoSuchGroupException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				setForward(actionRequest, "portlet.sites_admin.error");
			}
			else if (e instanceof AssetCategoryException ||
					 e instanceof AssetTagException ||
					 e instanceof AuthException ||
					 e instanceof DuplicateGroupException ||
					 e instanceof GroupFriendlyURLException ||
					 e instanceof GroupInheritContentException ||
					 e instanceof GroupKeyException ||
					 e instanceof GroupParentException ||
					 e instanceof LayoutSetVirtualHostException ||
					 e instanceof LocaleException ||
					 e instanceof PendingBackgroundTaskException ||
					 e instanceof RemoteAuthException ||
					 e instanceof RemoteExportException ||
					 e instanceof RemoteOptionsException ||
					 e instanceof RequiredGroupException ||
					 e instanceof SystemException) {

				if (e instanceof RemoteAuthException) {
					SessionErrors.add(actionRequest, AuthException.class, e);
				}
				else {
					SessionErrors.add(actionRequest, e.getClass(), e);
				}
			}
			else {
				throw e;
			}
		}
		catch (Throwable t) {
			_log.error(t);

			setForward(actionRequest, "portlet.sites_admin.error");
		}
	}

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getGroup(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchGroupException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return actionMapping.findForward("portlet.sites_admin.error");
			}
			else {
				throw e;
			}
		}

		return actionMapping.findForward(
			getForward(renderRequest, "portlet.sites_admin.edit_site"));
	}

	public void updateActive(ActionRequest actionRequest, String cmd)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		if ((groupId == themeDisplay.getDoAsGroupId()) ||
			(groupId == themeDisplay.getScopeGroupId()) ||
			(groupId == getRefererGroupId(themeDisplay))) {

			throw new RequiredGroupException.MustNotDeleteCurrentGroup(groupId);
		}

		Group group = GroupServiceUtil.getGroup(groupId);

		boolean active = false;

		if (cmd.equals(Constants.RESTORE)) {
			active = true;
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			Group.class.getName(), actionRequest);

		GroupServiceUtil.updateGroup(
			groupId, group.getParentGroupId(), group.getNameMap(),
			group.getDescriptionMap(), group.getType(),
			group.isManualMembership(), group.getMembershipRestriction(),
			group.getFriendlyURL(), group.isInheritContent(), active,
			serviceContext);
	}

	public Group updateGroup(ActionRequest actionRequest) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long userId = PortalUtil.getUserId(actionRequest);

		long liveGroupId = ParamUtil.getLong(actionRequest, "liveGroupId");

		long parentGroupId = ParamUtil.getLong(
			actionRequest, "parentGroupSearchContainerPrimaryKeys",
			GroupConstants.DEFAULT_PARENT_GROUP_ID);
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

			nameMap = LocalizationUtil.getLocalizationMap(
				actionRequest, "name");
			descriptionMap = LocalizationUtil.getLocalizationMap(
				actionRequest, "description");
			type = ParamUtil.getInteger(actionRequest, "type");
			friendlyURL = ParamUtil.getString(actionRequest, "friendlyURL");
			manualMembership = ParamUtil.getBoolean(
				actionRequest, "manualMembership");
			inheritContent = ParamUtil.getBoolean(
				actionRequest, "inheritContent");
			active = ParamUtil.getBoolean(actionRequest, "active");

			liveGroup = GroupServiceUtil.addGroup(
				parentGroupId, GroupConstants.DEFAULT_LIVE_GROUP_ID, nameMap,
				descriptionMap, type, manualMembership, membershipRestriction,
				friendlyURL, true, inheritContent, active, serviceContext);

			LiveUsers.joinGroup(
				themeDisplay.getCompanyId(), liveGroup.getGroupId(), userId);
		}
		else {

			// Update group

			liveGroup = GroupLocalServiceUtil.getGroup(liveGroupId);

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
				actionRequest, "friendlyURL", liveGroup.getFriendlyURL());
			inheritContent = ParamUtil.getBoolean(
				actionRequest, "inheritContent", liveGroup.getInheritContent());
			active = ParamUtil.getBoolean(
				actionRequest, "active", liveGroup.getActive());

			liveGroup = GroupServiceUtil.updateGroup(
				liveGroupId, parentGroupId, nameMap, descriptionMap, type,
				manualMembership, membershipRestriction, friendlyURL,
				inheritContent, active, serviceContext);

			if (type == GroupConstants.TYPE_SITE_OPEN) {
				List<MembershipRequest> membershipRequests =
					MembershipRequestLocalServiceUtil.search(
						liveGroupId, MembershipRequestConstants.STATUS_PENDING,
						QueryUtil.ALL_POS, QueryUtil.ALL_POS);

				for (MembershipRequest membershipRequest : membershipRequests) {
					MembershipRequestServiceUtil.updateStatus(
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
			ListUtil.toString(
				getRoles(actionRequest), Role.ROLE_ID_ACCESSOR,
				StringPool.COMMA));
		typeSettingsProperties.setProperty(
			"defaultTeamIds",
			ListUtil.toString(
				getTeams(actionRequest), Team.TEAM_ID_ACCESSOR,
				StringPool.COMMA));

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

		String publicRobots = ParamUtil.getString(
			actionRequest, "publicRobots",
			liveGroup.getTypeSettingsProperty("false-robots.txt"));
		String privateRobots = ParamUtil.getString(
			actionRequest, "privateRobots",
			liveGroup.getTypeSettingsProperty("true-robots.txt"));

		typeSettingsProperties.setProperty("false-robots.txt", publicRobots);
		typeSettingsProperties.setProperty("true-robots.txt", privateRobots);

		boolean trashEnabled = ParamUtil.getBoolean(
			actionRequest, "trashEnabled",
			GetterUtil.getBoolean(
				typeSettingsProperties.getProperty("trashEnabled"), true));

		typeSettingsProperties.setProperty(
			"trashEnabled", String.valueOf(trashEnabled));

		int trashEntriesMaxAgeCompany = PrefsPropsUtil.getInteger(
			themeDisplay.getCompanyId(), PropsKeys.TRASH_ENTRIES_MAX_AGE);

		double trashEntriesMaxAgeGroup = ParamUtil.getDouble(
			actionRequest, "trashEntriesMaxAge");

		if (trashEntriesMaxAgeGroup > 0) {
			trashEntriesMaxAgeGroup *= 1440;
		}
		else {
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

		typeSettingsProperties.putAll(formTypeSettingsProperties);

		// Virtual hosts

		LayoutSet publicLayoutSet = liveGroup.getPublicLayoutSet();

		String publicVirtualHost = ParamUtil.getString(
			actionRequest, "publicVirtualHost",
			publicLayoutSet.getVirtualHostname());

		LayoutSetServiceUtil.updateVirtualHost(
			liveGroup.getGroupId(), false, publicVirtualHost);

		LayoutSet privateLayoutSet = liveGroup.getPrivateLayoutSet();

		String privateVirtualHost = ParamUtil.getString(
			actionRequest, "privateVirtualHost",
			privateLayoutSet.getVirtualHostname());

		LayoutSetServiceUtil.updateVirtualHost(
			liveGroup.getGroupId(), true, privateVirtualHost);

		// Staging

		if (liveGroup.hasStagingGroup()) {
			Group stagingGroup = liveGroup.getStagingGroup();

			friendlyURL = ParamUtil.getString(
				actionRequest, "stagingFriendlyURL",
				stagingGroup.getFriendlyURL());

			GroupServiceUtil.updateFriendlyURL(
				stagingGroup.getGroupId(), friendlyURL);

			LayoutSet stagingPublicLayoutSet =
				stagingGroup.getPublicLayoutSet();

			publicVirtualHost = ParamUtil.getString(
				actionRequest, "stagingPublicVirtualHost",
				stagingPublicLayoutSet.getVirtualHostname());

			LayoutSetServiceUtil.updateVirtualHost(
				stagingGroup.getGroupId(), false, publicVirtualHost);

			LayoutSet stagingPrivateLayoutSet =
				stagingGroup.getPrivateLayoutSet();

			privateVirtualHost = ParamUtil.getString(
				actionRequest, "stagingPrivateVirtualHost",
				stagingPrivateLayoutSet.getVirtualHostname());

			LayoutSetServiceUtil.updateVirtualHost(
				stagingGroup.getGroupId(), true, privateVirtualHost);

			GroupServiceUtil.updateGroup(
				stagingGroup.getGroupId(), typeSettingsProperties.toString());
		}

		liveGroup = GroupServiceUtil.updateGroup(
			liveGroup.getGroupId(), typeSettingsProperties.toString());

		// Layout set prototypes

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
				(layoutSetPrototypeId > 0));

			if (layoutSetVisibility == _LAYOUT_SET_VISIBILITY_PRIVATE) {
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

		// Staging

		if (!privateLayoutSet.isLayoutSetPrototypeLinkActive() &&
			!publicLayoutSet.isLayoutSetPrototypeLinkActive()) {

			StagingUtil.updateStaging(actionRequest, liveGroup);
		}

		boolean forceDisable = ParamUtil.getBoolean(
			actionRequest, "forceDisable");

		if (forceDisable) {
			GroupLocalServiceUtil.disableStaging(liveGroupId);
		}

		return liveGroup;
	}

	protected String getGroupFriendlyURL(Group liveGroup) {
		if (liveGroup != null) {
			return liveGroup.getFriendlyURL();
		}

		return null;
	}

	protected Group getLiveGroup(PortletRequest portletRequest)
		throws PortalException {

		long liveGroupId = ParamUtil.getLong(portletRequest, "liveGroupId");

		if (liveGroupId > 0) {
			return GroupLocalServiceUtil.getGroup(liveGroupId);
		}

		return null;
	}

	protected long getRefererGroupId(ThemeDisplay themeDisplay)
		throws Exception {

		long refererGroupId = 0;

		try {
			Layout refererLayout = LayoutLocalServiceUtil.getLayout(
				themeDisplay.getRefererPlid());

			refererGroupId = refererLayout.getGroupId();
		}
		catch (NoSuchLayoutException nsle) {
		}

		return refererGroupId;
	}

	protected long getRefererPlid(
		Group liveGroup, long scopeGroupId, String redirect) {

		long refererPlid = GetterUtil.getLong(
			HttpUtil.getParameter(redirect, "refererPlid", false));

		if ((refererPlid > 0) && liveGroup.hasStagingGroup() &&
			(scopeGroupId != liveGroup.getGroupId())) {

			Layout firstLayout = LayoutLocalServiceUtil.fetchFirstLayout(
				liveGroup.getGroupId(), false,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

			if (firstLayout == null) {
				firstLayout = LayoutLocalServiceUtil.fetchFirstLayout(
					liveGroup.getGroupId(), true,
					LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);
			}

			if (firstLayout != null) {
				return firstLayout.getPlid();
			}
		}

		return LayoutConstants.DEFAULT_PLID;
	}

	protected List<Role> getRoles(PortletRequest portletRequest)
		throws Exception {

		List<Role> roles = new ArrayList<>();

		long[] siteRolesRoleIds = StringUtil.split(
			ParamUtil.getString(portletRequest, "siteRolesRoleIds"), 0L);

		for (long siteRolesRoleId : siteRolesRoleIds) {
			if (siteRolesRoleId == 0) {
				continue;
			}

			Role role = RoleLocalServiceUtil.getRole(siteRolesRoleId);

			roles.add(role);
		}

		return roles;
	}

	protected String getStagingGroupFriendlyURL(Group liveGroup) {
		if ((liveGroup != null) && liveGroup.hasStagingGroup()) {
			Group stagingGroup = liveGroup.getStagingGroup();

			return stagingGroup.getFriendlyURL();
		}

		return null;
	}

	protected List<Team> getTeams(PortletRequest portletRequest)
		throws Exception {

		List<Team> teams = new ArrayList<>();

		long[] teamsTeamIds = ArrayUtil.unique(
			StringUtil.split(
				ParamUtil.getString(portletRequest, "teamsTeamIds"), 0L));

		for (long teamsTeamId : teamsTeamIds) {
			if (teamsTeamId == 0) {
				continue;
			}

			Team team = TeamLocalServiceUtil.getTeam(teamsTeamId);

			teams.add(team);
		}

		return teams;
	}

	private static final int _LAYOUT_SET_VISIBILITY_PRIVATE = 1;

	private static final Log _log = LogFactoryUtil.getLog(
		SitesAdminPortlet.class);

	private final TransactionAttribute _transactionAttribute =
		TransactionAttributeBuilder.build(
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