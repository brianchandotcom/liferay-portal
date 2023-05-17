/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.service.impl;

import com.liferay.mail.kernel.model.MailMessage;
import com.liferay.mail.kernel.service.MailService;
import com.liferay.osb.faro.constants.DocumentationConstants;
import com.liferay.osb.faro.constants.FaroUserConstants;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.model.FaroUser;
import com.liferay.osb.faro.service.FaroPreferencesLocalService;
import com.liferay.osb.faro.service.base.FaroUserLocalServiceBaseImpl;
import com.liferay.osb.faro.service.persistence.FaroProjectPersistence;
import com.liferay.osb.faro.util.EmailUtil;
import com.liferay.osb.faro.util.FaroPropsValues;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.util.ArrayList;
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
	property = "model.class.name=com.liferay.osb.faro.model.FaroUser",
	service = AopService.class
)
public class FaroUserLocalServiceImpl extends FaroUserLocalServiceBaseImpl {

	public List<FaroUser> acceptInvitations(long userId, String key) {
		User user = _userLocalService.fetchUser(userId);

		if (user == null) {
			return Collections.emptyList();
		}

		List<FaroUser> faroUsers = new ArrayList<>(
			faroUserPersistence.findByE_S(
				user.getEmailAddress(), FaroUserConstants.STATUS_PENDING));

		FaroUser faroUser = faroUserPersistence.fetchByKey(key);

		if (faroUser != null) {
			faroUsers.add(faroUser);
		}

		for (FaroUser curFaroUser : faroUsers) {
			if (curFaroUser.getStatus() == FaroUserConstants.STATUS_APPROVED) {
				continue;
			}

			curFaroUser.setModifiedTime(System.currentTimeMillis());
			curFaroUser.setLiveUserId(userId);
			curFaroUser.setStatus(FaroUserConstants.STATUS_APPROVED);

			updateFaroUser(curFaroUser);
		}

		return faroUsers;
	}

	public FaroUser addFaroUser(
			long userId, long groupId, long liveUserId, long roleId,
			String emailAddress, int status, boolean sendEmail)
		throws PortalException {

		FaroUser faroUser = faroUserPersistence.fetchByG_E(
			groupId, emailAddress);

		if (faroUser == null) {
			long faroUserId = counterLocalService.increment();

			faroUser = faroUserPersistence.create(faroUserId);

			faroUser.setGroupId(groupId);
			faroUser.setUserId(userId);

			User user = _userLocalService.getUser(userId);

			faroUser.setUserName(user.getFullName());

			long now = System.currentTimeMillis();

			faroUser.setCreateTime(now);
			faroUser.setModifiedTime(now);

			User liveUser = _userLocalService.fetchUserByEmailAddress(
				user.getCompanyId(), emailAddress);

			if (liveUser != null) {
				liveUserId = liveUser.getUserId();
			}

			faroUser.setLiveUserId(liveUserId);

			faroUser.setRoleId(roleId);
			faroUser.setEmailAddress(emailAddress);
			faroUser.setKey(_portalUUIDUtil.generate());
			faroUser.setStatus(status);

			faroUser = faroUserPersistence.update(faroUser);
		}

		if (sendEmail) {
			try {
				_sendEmail(faroUser, groupId, roleId, userId);
			}
			catch (Exception exception) {
				throw new SystemException(exception);
			}
		}

		return faroUser;
	}

	public FaroUser deleteFaroUser(long faroUserId) throws PortalException {
		FaroUser faroUser = getFaroUser(faroUserId);

		if (faroUser.getLiveUserId() > 0) {
			_faroPreferencesLocalService.deleteFaroPreferences(
				faroUser.getGroupId(), faroUser.getLiveUserId());
			_groupLocalService.deleteUserGroup(
				faroUser.getLiveUserId(), faroUser.getGroupId());
			_userGroupRoleLocalService.deleteUserGroupRoles(
				faroUser.getLiveUserId(), new long[] {faroUser.getGroupId()});
		}

		return super.deleteFaroUser(faroUserId);
	}

	public void deleteFaroUsers(long groupId) {
		faroUserPersistence.removeByGroupId(groupId);
	}

	public FaroUser fetchFaroUser(long groupId, long liveUserId) {
		return faroUserPersistence.fetchByG_L(groupId, liveUserId);
	}

	public FaroUser fetchFaroUser(long groupId, String emailAddress) {
		return faroUserPersistence.fetchByG_E(groupId, emailAddress);
	}

	public FaroUser fetchOwnerFaroUser(long groupId) {
		Role role = _roleLocalService.fetchRole(
			_portal.getDefaultCompanyId(), RoleConstants.SITE_OWNER);

		if (role == null) {
			return null;
		}

		return faroUserPersistence.fetchByG_R_First(
			groupId, role.getRoleId(), null);
	}

	public FaroUser getFaroUser(long groupId, long liveUserId)
		throws PortalException {

		return faroUserPersistence.findByG_L(groupId, liveUserId);
	}

	public List<FaroUser> getFaroUsersByLiveUserId(
		long liveUserId, int status) {

		return faroUserPersistence.findByL_S(liveUserId, status);
	}

	public List<FaroUser> getFaroUsersByRoleId(long groupId, long roleId) {
		return faroUserPersistence.findByG_R(groupId, roleId);
	}

	public List<FaroUser> getFaroUsersByStatus(long groupId, int status) {
		return faroUserPersistence.findByG_S(groupId, status);
	}

	public FaroUser getOwnerFaroUser(long groupId) throws PortalException {
		Role role = _roleLocalService.getRole(
			_portal.getDefaultCompanyId(), RoleConstants.SITE_OWNER);

		return faroUserPersistence.findByG_R_First(
			groupId, role.getRoleId(), null);
	}

	public List<FaroUser> search(
		long groupId, String query, List<Integer> statuses, int start, int end,
		OrderByComparator<FaroUser> orderByComparator) {

		return faroUserFinder.findByKeywords(
			groupId, query, statuses, start, end, orderByComparator);
	}

	public int searchCount(long groupId, String query, List<Integer> statuses) {
		return faroUserFinder.countByKeywords(groupId, query, statuses);
	}

	private String _getNotificationMessage(
			long roleId, long groupId, ResourceBundle resourceBundle)
		throws Exception {

		String roleName = null;

		Role role = _roleLocalService.getRole(roleId);

		if (StringUtil.equals(
				role.getName(), RoleConstants.SITE_ADMINISTRATOR)) {

			roleName = "administrator-fragment";
		}
		else {
			roleName = "member-fragment";
		}

		FaroProject faroProject = _faroProjectPersistence.fetchByGroupId(
			groupId);

		return _language.format(
			resourceBundle, "you-have-been-added-as-a-team-x-on-workspace-x",
			new String[] {
				roleName, "<strong>" + faroProject.getName() + "</strong>"
			});
	}

	private void _sendEmail(
			FaroUser faroUser, long groupId, long roleId, long userId)
		throws Exception {

		if (faroUser.getStatus() == FaroUserConstants.STATUS_REQUESTED) {
			_sendEmailRequest(userId, groupId);
		}
		else {
			_sendEmailNewUser(faroUser, groupId, roleId);
		}
	}

	private void _sendEmailNewUser(FaroUser faroUser, long groupId, long roleId)
		throws Exception {

		User user = _userLocalService.getUser(faroUser.getUserId());

		InternetAddress from = new InternetAddress(
			"ac@liferay.com", user.getFullName() + " (Analytics Cloud)");

		String toName = StringPool.BLANK;

		if (faroUser.getLiveUserId() > 0) {
			User receiverUser = _userLocalService.getUser(
				faroUser.getLiveUserId());

			toName = receiverUser.getFullName();
		}

		InternetAddress to = new InternetAddress(
			faroUser.getEmailAddress(), toName);

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", user.getLocale(), getClass());

		String body = null;
		String subject = null;

		if (faroUser.getLiveUserId() > 0) {
			body = StringUtil.read(
				getClassLoader(),
				"com/liferay/osb/faro/dependencies/invite-existing-user.html");
			subject = _language.format(
				resourceBundle,
				"x-has-added-you-to-a-workspace-on-analytics-cloud",
				user.getFullName());
		}
		else {
			body = StringUtil.read(
				getClassLoader(),
				"com/liferay/osb/faro/dependencies/invite-new-user.html");
			subject = _language.get(
				resourceBundle, "welcome-to-analytics-cloud");
		}

		body = StringUtil.replace(
			body,
			new String[] {
				"[$BUTTON_TEXT$]", "[$BUTTON_URL$]", "[$EMAIL_TITLE$]",
				"[$HELP_MSG$]", "[$INSTRUCTION_MSG$]", "[$LOGO_ICON_URL$]",
				"[$NOTIFICATION_MSG$]", "[$TITLE_ICON_URL$]", "[$WELCOME_MSG$]"
			},
			new String[] {
				_language.get(resourceBundle, "sign-in"),
				EmailUtil.getWorkspaceURL(
					_groupLocalService.getGroup(faroUser.getGroupId())),
				subject,
				_language.format(
					resourceBundle, "email-need-more-help",
					new String[] {
						"<a class=\"body-link\" href=\"" +
							DocumentationConstants.BASE_URL + "\">",
						"</a>"
					}),
				_language.format(
					resourceBundle, "email-sign-in-or-create-an-account",
					new String[] {
						"<a class=\"body-link\" href=\"" +
							FaroPropsValues.FARO_URL + "\">",
						"</a>",
						"<b class=\"link-override\">" +
							faroUser.getEmailAddress() + "</strong>"
					}),
				EmailUtil.getLogoIconURL(),
				_getNotificationMessage(roleId, groupId, resourceBundle),
				EmailUtil.getTitleIconURL(),
				_language.get(resourceBundle, "welcome-to-analytics-cloud")
			});

		_mailService.sendEmail(new MailMessage(from, to, subject, body, true));

		if (_log.isInfoEnabled()) {
			_log.info(
				"New user invite email notification sent to " +
					to.getAddress());
		}
	}

	private void _sendEmailRequest(long userId, long groupId) throws Exception {
		User senderUser = _userLocalService.getUser(userId);

		InternetAddress from = new InternetAddress(
			"ac@liferay.com", senderUser.getFullName() + " (Analytics Cloud)");

		FaroProject faroProject = _faroProjectPersistence.findByGroupId(
			groupId);

		User receiverUser = _userLocalService.getUser(faroProject.getUserId());

		InternetAddress to = new InternetAddress(
			receiverUser.getEmailAddress(), receiverUser.getFullName());

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", receiverUser.getLocale(), getClass());

		String workspaceURL = EmailUtil.getWorkspaceURL(
			_groupLocalService.getGroup(faroProject.getGroupId()));

		String body = StringUtil.replace(
			StringUtil.read(
				getClassLoader(),
				"com/liferay/osb/faro/dependencies/join-request.html"),
			new String[] {
				"[$BUTTON_TEXT$]", "[$BUTTON_URL$]", "[$HELP_MSG$]",
				"[$INSTRUCTION_MSG$]", "[$LOGO_ICON_URL$]",
				"[$NOTIFICATION_MSG_1$]", "[$NOTIFICATION_MSG_2$]",
				"[$TITLE_ICON_URL$]"
			},
			new String[] {
				_language.get(resourceBundle, "sign-in"),
				workspaceURL + "/settings/users",
				_language.format(
					resourceBundle, "email-need-more-help",
					new String[] {
						"<a class=\"body-link\" href=\"" +
							DocumentationConstants.BASE_URL + "\">",
						"</a>"
					}),
				_language.get(
					resourceBundle, "email-sign-in-to-approve-or-deny"),
				EmailUtil.getLogoIconURL(),
				_language.get(resourceBundle, "new-request-to-join-workspace"),
				_language.format(
					resourceBundle, "x-has-requested-to-join-the-x-workspace",
					new String[] {
						StringBundler.concat(
							"<strong>", senderUser.getFullName(), "</strong> (",
							senderUser.getEmailAddress(), ")"),
						faroProject.getName()
					}),
				EmailUtil.getTitleIconURL()
			});

		String subject = _language.format(
			resourceBundle, "new-request-to-join-x", faroProject.getName());

		_mailService.sendEmail(new MailMessage(from, to, subject, body, true));

		if (_log.isInfoEnabled()) {
			_log.info(
				"Request to join workspace email notification sent to " +
					to.getAddress());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FaroUserLocalServiceImpl.class);

	@Reference
	private FaroPreferencesLocalService _faroPreferencesLocalService;

	@Reference
	private FaroProjectPersistence _faroProjectPersistence;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Http _http;

	@Reference
	private Language _language;

	@Reference
	private MailService _mailService;

	@Reference
	private Portal _portal;

	@Reference
	private PortalUUIDUtil _portalUUIDUtil;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}