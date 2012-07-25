/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.sites.action;

import com.liferay.portal.DuplicateGroupException;
import com.liferay.portal.GroupFriendlyURLException;
import com.liferay.portal.GroupNameException;
import com.liferay.portal.LayoutSetVirtualHostException;
import com.liferay.portal.LocaleException;
import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.RemoteExportException;
import com.liferay.portal.RemoteOptionsException;
import com.liferay.portal.RequiredGroupException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.liveusers.LiveUsers;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.MembershipRequest;
import com.liferay.portal.model.MembershipRequestConstants;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.GroupServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetServiceUtil;
import com.liferay.portal.service.MembershipRequestLocalServiceUtil;
import com.liferay.portal.service.MembershipRequestServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.AssetCategoryException;
import com.liferay.portlet.asset.AssetTagException;
import com.liferay.portlet.sites.util.SitesUtil;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 * @author Zsolt Berentey
 */
public class EditGroupAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		try {
			String closeRedirect = ParamUtil.getString(
				actionRequest, "closeRedirect");

			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				Object[] returnValue = updateGroup(actionRequest);

				Group group = (Group)returnValue[0];
				String oldFriendlyURL = (String)returnValue[1];
				String oldStagingFriendlyURL = (String)returnValue[2];
				long newRefererPlid = (Long)returnValue[3];

				redirect = HttpUtil.setParameter(
					redirect, "doAsGroupId", group.getGroupId());
				redirect = HttpUtil.setParameter(
					redirect, "refererPlid", newRefererPlid);

				closeRedirect = updateCloseRedirect(
					closeRedirect, group, themeDisplay, oldFriendlyURL,
					oldStagingFriendlyURL);
			}
			else if (cmd.equals(Constants.DEACTIVATE) ||
					 cmd.equals(Constants.RESTORE)) {

				updateActive(actionRequest, cmd);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteGroup(actionRequest);
			}

			if (Validator.isNotNull(closeRedirect)) {
				LiferayPortletConfig liferayPortletConfig =
					(LiferayPortletConfig)portletConfig;

				SessionMessages.add(
					actionRequest,
					liferayPortletConfig.getPortletId() +
						SessionMessages.KEY_SUFFIX_CLOSE_REDIRECT,
					closeRedirect);
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
					 e instanceof DuplicateGroupException ||
					 e instanceof GroupFriendlyURLException ||
					 e instanceof GroupNameException ||
					 e instanceof LayoutSetVirtualHostException ||
					 e instanceof LocaleException ||
					 e instanceof RemoteExportException ||
					 e instanceof RemoteOptionsException ||
					 e instanceof RequiredGroupException ||
					 e instanceof SystemException) {

				SessionErrors.add(actionRequest, e.getClass(), e);

				if (cmd.equals(Constants.DEACTIVATE) ||
					cmd.equals(Constants.DELETE) ||
					cmd.equals(Constants.RESTORE)) {

					if (Validator.isNotNull(redirect)) {
						actionResponse.sendRedirect(redirect);
					}
				}
			}
			else {
				throw e;
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getGroup(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchGroupException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return mapping.findForward("portlet.sites_admin.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(renderRequest, "portlet.sites_admin.edit_site"));
	}

	protected void deleteGroup(ActionRequest actionRequest) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		GroupServiceUtil.deleteGroup(groupId);

		LiveUsers.deleteGroup(themeDisplay.getCompanyId(), groupId);
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

	protected void updateActive(ActionRequest actionRequest, String cmd)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		if ((groupId == themeDisplay.getDoAsGroupId()) ||
			(groupId == themeDisplay.getScopeGroupId()) ||
			(groupId == getRefererGroupId(themeDisplay))) {

			throw new RequiredGroupException(
				String.valueOf(groupId), RequiredGroupException.CURRENT_GROUP);
		}

		Group group = GroupServiceUtil.getGroup(groupId);

		boolean active = false;

		if (cmd.equals(Constants.RESTORE)) {
			active = true;
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			Group.class.getName(), actionRequest);

		GroupServiceUtil.updateGroup(
			groupId, group.getParentGroupId(), group.getName(),
			group.getDescription(), group.getType(), group.getFriendlyURL(),
			active, serviceContext);
	}

	protected String updateCloseRedirect(
			String closeRedirect, Group group, ThemeDisplay themeDisplay,
			String oldFriendlyURL, String oldStagingFriendlyURL)
		throws PortalException, SystemException {

		if (Validator.isNull(closeRedirect) || (group == null)) {
			return closeRedirect;
		}

		String oldPath = null;
		String newPath = null;

		if (Validator.isNotNull(oldFriendlyURL)) {
			oldPath = oldFriendlyURL;
			newPath = group.getFriendlyURL();

			if (closeRedirect.indexOf(oldPath) != -1) {
				closeRedirect = PortalUtil.updateRedirect(
					closeRedirect, oldPath, newPath);
			}
			else {
				closeRedirect = PortalUtil.getGroupFriendlyURL(
					group, false, themeDisplay);
			}
		}

		if (Validator.isNotNull(oldStagingFriendlyURL)) {
			Group stagingGroup = group.getStagingGroup();

			if (GroupLocalServiceUtil.fetchGroup(
					stagingGroup.getGroupId()) == null) {

				oldPath = oldStagingFriendlyURL;
				newPath = group.getFriendlyURL();
			}
			else {
				oldPath = oldStagingFriendlyURL;
				newPath = stagingGroup.getFriendlyURL();
			}

			if (closeRedirect.contains(oldPath)) {
				closeRedirect = PortalUtil.updateRedirect(
					closeRedirect, oldPath, newPath);
			}
			else {
				closeRedirect = PortalUtil.getGroupFriendlyURL(
					group, false, themeDisplay);
			}
		}

		return closeRedirect;
	}

	protected Object[] updateGroup(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long userId = PortalUtil.getUserId(actionRequest);

		long liveGroupId = ParamUtil.getLong(actionRequest, "liveGroupId");

		long parentGroupId = ParamUtil.getLong(
			actionRequest, "parentGroupSearchContainerPrimaryKeys",
			GroupConstants.DEFAULT_PARENT_GROUP_ID);
		String name = null;
		String description = null;
		int type = 0;
		String friendlyURL = null;
		boolean active = false;

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			Group.class.getName(), actionRequest);

		Group liveGroup = null;
		String oldFriendlyURL = null;
		String oldStagingFriendlyURL = null;

		if (liveGroupId <= 0) {

			// Add group

			name = ParamUtil.getString(actionRequest, "name");
			description = ParamUtil.getString(actionRequest, "description");
			type = ParamUtil.getInteger(actionRequest, "type");
			friendlyURL = ParamUtil.getString(actionRequest, "friendlyURL");
			active = ParamUtil.getBoolean(actionRequest, "active");

			liveGroup = GroupServiceUtil.addGroup(
				parentGroupId, name, description, type, friendlyURL, true,
				active, serviceContext);

			LiveUsers.joinGroup(
				themeDisplay.getCompanyId(), liveGroup.getGroupId(), userId);
		}
		else {

			// Update group

			liveGroup = GroupLocalServiceUtil.getGroup(liveGroupId);

			oldFriendlyURL = liveGroup.getFriendlyURL();

			name = ParamUtil.getString(
				actionRequest, "name", liveGroup.getName());
			description = ParamUtil.getString(
				actionRequest, "description", liveGroup.getDescription());
			type = ParamUtil.getInteger(
				actionRequest, "type", liveGroup.getType());
			friendlyURL = ParamUtil.getString(
				actionRequest, "friendlyURL", liveGroup.getFriendlyURL());
			active = ParamUtil.getBoolean(
				actionRequest, "active", liveGroup.getActive());

			liveGroup = GroupServiceUtil.updateGroup(
				liveGroupId, parentGroupId, name, description, type,
				friendlyURL, active, serviceContext);

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

		String googleAnalyticsId = ParamUtil.getString(
			actionRequest, "googleAnalyticsId",
			typeSettingsProperties.getProperty("googleAnalyticsId"));

		typeSettingsProperties.setProperty(
			"googleAnalyticsId", googleAnalyticsId);

		String publicRobots = ParamUtil.getString(
			actionRequest, "publicRobots",
			liveGroup.getTypeSettingsProperty("false-robots.txt"));
		String privateRobots = ParamUtil.getString(
			actionRequest, "privateRobots",
			liveGroup.getTypeSettingsProperty("true-robots.txt"));

		typeSettingsProperties.setProperty("false-robots.txt", publicRobots);
		typeSettingsProperties.setProperty("true-robots.txt", privateRobots);

		int trashEnabled = ParamUtil.getInteger(
			actionRequest, "trashEnabled",
			GetterUtil.getInteger(
				typeSettingsProperties.getProperty("trashEnabled")));

		typeSettingsProperties.setProperty(
			"trashEnabled", String.valueOf(trashEnabled));

		int trashEntriesMaxAgeGroup = ParamUtil.getInteger(
			actionRequest, "trashEntriesMaxAge",
			GetterUtil.getInteger(
				typeSettingsProperties.getProperty("trashEntriesMaxAge")));

		int trashEntriesMaxAgeCompany = PrefsPropsUtil.getInteger(
			themeDisplay.getCompanyId(), PropsKeys.TRASH_ENTRIES_MAX_AGE);

		if (trashEntriesMaxAgeGroup != trashEntriesMaxAgeCompany) {
			typeSettingsProperties.setProperty(
				"trashEntriesMaxAge", String.valueOf(trashEntriesMaxAgeGroup));
		}
		else {
			typeSettingsProperties.remove("trashEntriesMaxAge");
		}

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

			oldStagingFriendlyURL = stagingGroup.getFriendlyURL();

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
		}

		liveGroup = GroupServiceUtil.updateGroup(
			liveGroup.getGroupId(), typeSettingsProperties.toString());

		// Layout set prototypes

		if (!liveGroup.isStaged()) {
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

			SitesUtil.updateLayoutSetPrototypesLinks(
				liveGroup, publicLayoutSetPrototypeId,
				privateLayoutSetPrototypeId,
				publicLayoutSetPrototypeLinkEnabled,
				privateLayoutSetPrototypeLinkEnabled);
		}

		// Staging

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		long refererPlid = GetterUtil.getLong(
			HttpUtil.getParameter(redirect, "refererPlid", false));

		if (!privateLayoutSet.isLayoutSetPrototypeLinkActive() &&
			!publicLayoutSet.isLayoutSetPrototypeLinkActive()) {

			if ((refererPlid > 0) && liveGroup.hasStagingGroup() &&
				(themeDisplay.getScopeGroupId() != liveGroup.getGroupId())) {

				Layout firstLayout = LayoutLocalServiceUtil.fetchFirstLayout(
					liveGroup.getGroupId(), false,
					LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

				if (firstLayout == null) {
					firstLayout = LayoutLocalServiceUtil.fetchFirstLayout(
						liveGroup.getGroupId(), true,
						LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);
				}

				if (firstLayout != null) {
					refererPlid = firstLayout.getPlid();
				}
				else {
					refererPlid = 0;
				}
			}

			StagingUtil.updateStaging(actionRequest, liveGroup);
		}

		return new Object[] {
			liveGroup, oldFriendlyURL, oldStagingFriendlyURL, refererPlid};
	}

	private static final int _LAYOUT_SET_VISIBILITY_PRIVATE = 1;

}