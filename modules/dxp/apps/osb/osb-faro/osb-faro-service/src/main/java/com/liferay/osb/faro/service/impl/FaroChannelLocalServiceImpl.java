/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.service.impl;

import com.liferay.mail.kernel.model.MailMessage;
import com.liferay.mail.kernel.service.MailService;
import com.liferay.osb.faro.constants.DocumentationConstants;
import com.liferay.osb.faro.constants.FaroChannelConstants;
import com.liferay.osb.faro.model.FaroChannel;
import com.liferay.osb.faro.model.FaroUser;
import com.liferay.osb.faro.service.FaroUserLocalService;
import com.liferay.osb.faro.service.base.FaroChannelLocalServiceBaseImpl;
import com.liferay.osb.faro.util.EmailUtil;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javax.mail.internet.InternetAddress;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matthew Kong
 */
@Component(
	property = "model.class.name=com.liferay.osb.faro.model.FaroChannel",
	service = AopService.class
)
public class FaroChannelLocalServiceImpl
	extends FaroChannelLocalServiceBaseImpl {

	public FaroChannel addFaroChannel(
			long userId, String name, String channelId, long workspaceGroupId)
		throws PortalException {

		long faroChannelId = counterLocalService.increment();

		Group group = _groupLocalService.addGroup(
			userId, workspaceGroupId, FaroChannel.class.getName(),
			faroChannelId, 0,
			Collections.singletonMap(LocaleUtil.getDefault(), name), null,
			GroupConstants.TYPE_SITE_PRIVATE, true,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, null, true, true,
			null);

		// friendlyURL will be derived from the group name if empty, so it has
		// to be removed after creation

		group.setFriendlyURL(null);

		group = _groupLocalService.updateGroup(group);

		FaroChannel faroChannel = faroChannelPersistence.create(faroChannelId);

		faroChannel.setGroupId(group.getGroupId());
		faroChannel.setUserId(userId);

		long now = System.currentTimeMillis();

		faroChannel.setCreateTime(now);
		faroChannel.setModifiedTime(now);

		faroChannel.setChannelId(channelId);
		faroChannel.setName(name);
		faroChannel.setWorkspaceGroupId(workspaceGroupId);

		return faroChannelPersistence.update(faroChannel);
	}

	public void addUsers(
			long companyId, String channelId, List<Long> invitedUserIds,
			long userId, long workspaceGroupId)
		throws PortalException {

		FaroChannel faroChannel = faroChannelPersistence.findByC_W(
			channelId, workspaceGroupId);

		Role role = _roleLocalService.getRole(
			companyId, RoleConstants.SITE_MEMBER);

		for (long invitedUserId : invitedUserIds) {
			_groupLocalService.addUserGroup(
				invitedUserId, faroChannel.getGroupId());

			_userGroupRoleLocalService.deleteUserGroupRoles(
				invitedUserId, new long[] {faroChannel.getGroupId()});

			_userGroupRoleLocalService.addUserGroupRoles(
				invitedUserId, faroChannel.getGroupId(),
				new long[] {role.getRoleId()});

			try {
				_sendEmail(faroChannel, invitedUserId, userId);
			}
			catch (Exception exception) {
				_log.error(exception);
			}
		}
	}

	public FaroChannel deleteFaroChannel(FaroChannel faroChannel)
		throws PortalException {

		_groupLocalService.deleteGroup(faroChannel.getGroupId());

		return faroChannelPersistence.remove(faroChannel);
	}

	public FaroChannel deleteFaroChannel(
			String channelId, long workspaceGroupId)
		throws PortalException {

		return deleteFaroChannel(
			faroChannelPersistence.findByC_W(channelId, workspaceGroupId));
	}

	public void deleteFaroChannels(long workspaceGroupId)
		throws PortalException {

		for (FaroChannel faroChannel :
				faroChannelPersistence.findByWorkspaceGroupId(
					workspaceGroupId)) {

			deleteFaroChannel(faroChannel);
		}
	}

	public FaroChannel getFaroChannel(String channelId, long workspaceGroupId)
		throws PortalException {

		return faroChannelPersistence.findByC_W(channelId, workspaceGroupId);
	}

	public List<FaroUser> getFaroUsers(
			String channelId, boolean available, String query,
			List<Integer> statuses, long workspaceGroupId, int start, int end,
			OrderByComparator<FaroUser> orderByComparator)
		throws PortalException {

		FaroChannel faroChannel = faroChannelPersistence.findByC_W(
			channelId, workspaceGroupId);

		return _faroUserLocalService.getFaroUsers(
			faroChannel.getGroupId(), available, query, statuses,
			faroChannel.getWorkspaceGroupId(), start, end, orderByComparator);
	}

	public int getFaroUsersCount(
			String channelId, boolean available, String query,
			List<Integer> statuses, long workspaceGroupId)
		throws PortalException {

		FaroChannel faroChannel = faroChannelPersistence.findByC_W(
			channelId, workspaceGroupId);

		return _faroUserLocalService.getFaroUsersCount(
			faroChannel.getGroupId(), available, query, statuses,
			faroChannel.getWorkspaceGroupId());
	}

	public void removeUsers(
			String channelId, List<Long> userIds, long workspaceGroupId)
		throws PortalException {

		FaroChannel faroChannel = faroChannelPersistence.findByC_W(
			channelId, workspaceGroupId);

		for (long userId : userIds) {
			_userGroupRoleLocalService.deleteUserGroupRoles(
				userId, new long[] {faroChannel.getGroupId()});

			_groupLocalService.deleteUserGroup(
				userId, faroChannel.getGroupId());
		}
	}

	public List<FaroChannel> search(
		long groupId, String query, int start, int end,
		OrderByComparator<FaroChannel> orderByComparator) {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		return faroChannelFinder.findByKeywords(
			groupId, FaroChannelConstants.PERMISSION_ALL_USERS, query,
			permissionChecker.getUserId(), start, end, orderByComparator);
	}

	public int searchCount(long groupId, String query) {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		return faroChannelFinder.countByKeywords(
			groupId, FaroChannelConstants.PERMISSION_ALL_USERS, query,
			permissionChecker.getUserId());
	}

	private void _sendEmail(
			FaroChannel faroChannel, long invitedUserId, long userId)
		throws Exception {

		User user = _userLocalService.getUser(userId);

		InternetAddress from = new InternetAddress(
			"ac@liferay.com", user.getFullName() + " (Analytics Cloud)");

		User invitedUser = _userLocalService.getUser(invitedUserId);

		InternetAddress to = new InternetAddress(
			invitedUser.getEmailAddress(), invitedUser.getFullName());

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", invitedUser.getLocale(), getClass());

		String subject = _language.get(
			resourceBundle,
			"analytics-cloud-you-have-been-added-to-a-property");

		String body = StringUtil.replace(
			StringUtil.read(
				getClassLoader(),
				"com/liferay/osb/faro/dependencies/property-invite.html"),
			new String[] {
				"[$BUTTON_TEXT$]", "[$BUTTON_URL$]", "[$EMAIL_TITLE$]",
				"[$HELP_MSG$]", "[$LOGO_ICON_URL$]", "[$NOTIFICATION_MSG_1$]",
				"[$NOTIFICATION_MSG_2$]", "[$TITLE_ICON_URL$]", "[$TITLE_MSG$]"
			},
			new String[] {
				_language.get(resourceBundle, "go-to-workspace"),
				EmailUtil.getWorkspaceURL(
					_groupLocalService.fetchGroup(faroChannel.getGroupId())),
				subject,
				_language.format(
					resourceBundle, "email-need-more-help",
					new String[] {
						"<a class=\"body-link\" href=\"" +
							DocumentationConstants.BASE_URL + "\">",
						"</a>"
					}),
				EmailUtil.getLogoIconURL(),
				_language.format(
					resourceBundle, "x-has-been-added-to-your-properties-list",
					faroChannel.getName()),
				_language.get(
					resourceBundle,
					"log-in-to-your-workspace-to-access-this-property"),
				EmailUtil.getTitleIconURL(),
				_language.get(resourceBundle, "you-have-been-added")
			});

		_mailService.sendEmail(new MailMessage(from, to, subject, body, true));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FaroChannelLocalServiceImpl.class);

	@Reference
	private FaroUserLocalService _faroUserLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Language _language;

	@Reference
	private MailService _mailService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}