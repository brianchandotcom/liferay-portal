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
import com.liferay.osb.faro.constants.FaroProjectConstants;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.model.FaroUser;
import com.liferay.osb.faro.service.base.FaroProjectLocalServiceBaseImpl;
import com.liferay.osb.faro.util.EmailUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.comparator.GroupNameComparator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.mail.internet.InternetAddress;

/**
 * @author Matthew Kong
 */
public class FaroProjectLocalServiceImpl
	extends FaroProjectLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public FaroProject addFaroProject(
			long userId, String name, String accountKey, String accountName,
			String corpProjectName, String corpProjectUuid,
			List<String> emailAddressDomains, String friendlyURL,
			String incidentReportEmailAddresses, String serverLocation,
			String services, String state, String subscription,
			String timeZoneId, String weDeployKey)
		throws PortalException {

		long faroProjectId = counterLocalService.increment();

		Group group = groupLocalService.addGroup(
			userId, 0, FaroProject.class.getName(), faroProjectId, 0,
			Collections.singletonMap(LocaleUtil.getDefault(), name), null,
			GroupConstants.TYPE_SITE_PRIVATE, true,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, friendlyURL, true,
			true, null);

		// friendlyURL will be derived from the group name if empty, so it has
		// to be removed after creation

		if ((friendlyURL == null) || Validator.isBlank(friendlyURL.trim())) {
			group.setFriendlyURL(null);

			groupLocalService.updateGroup(group);
		}

		long groupId = group.getGroupId();

		FaroProject faroProject = faroProjectPersistence.create(faroProjectId);

		faroProject.setGroupId(group.getGroupId());
		faroProject.setUserId(userId);

		long now = System.currentTimeMillis();

		faroProject.setCreateTime(now);
		faroProject.setModifiedTime(now);

		faroProject.setName(name);
		faroProject.setAccountKey(accountKey);
		faroProject.setAccountName(accountName);
		faroProject.setCorpProjectName(corpProjectName);
		faroProject.setCorpProjectUuid(corpProjectUuid);
		faroProject.setIncidentReportEmailAddresses(
			incidentReportEmailAddresses);
		faroProject.setServerLocation(serverLocation);
		faroProject.setServices(services);
		faroProject.setState(state);
		faroProject.setSubscription(subscription);
		faroProject.setTimeZoneId(timeZoneId);
		faroProject.setWeDeployKey(weDeployKey);

		faroProjectEmailAddressDomainLocalService.
			addFaroProjectEmailAddressDomains(
				groupId, faroProjectId, emailAddressDomains);

		return faroProjectPersistence.update(faroProject);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	public FaroProject deleteFaroProjectByGroupId(long groupId)
		throws PortalException {

		faroChannelLocalService.deleteFaroChannels(groupId);
		faroPreferencesLocalService.deleteFaroPreferencesByGroupId(groupId);
		faroUserLocalService.deleteFaroUsers(groupId);

		groupLocalService.deleteGroup(groupId);

		return faroProjectPersistence.removeByGroupId(groupId);
	}

	@Override
	public FaroProject fetchFaroProjectByCorpProjectUuid(
		String corpProjectUuid) {

		return faroProjectPersistence.fetchByCorpProjectUuid(corpProjectUuid);
	}

	@Override
	public FaroProject fetchFaroProjectByGroupId(long groupId) {
		return faroProjectPersistence.fetchByGroupId(groupId);
	}

	@Override
	public FaroProject fetchFaroProjectByWeDeployKey(String weDeployKey) {
		return faroProjectPersistence.fetchByWeDeployKey(weDeployKey);
	}

	@Override
	public FaroProject getFaroProjectByGroupId(long groupId)
		throws PortalException {

		return faroProjectPersistence.findByGroupId(groupId);
	}

	@Override
	public FaroProject getFaroProjectByWeDeployKey(String weDeployKey)
		throws PortalException {

		return faroProjectPersistence.findByWeDeployKey(weDeployKey);
	}

	@Override
	public List<FaroProject> getFaroProjects(String serverLocation) {
		return faroProjectPersistence.findByServerLocation(serverLocation);
	}

	@Override
	public List<FaroProject> getFaroProjectsByEmailAddressDomain(
		String emailAddressDomains) {

		return faroProjectFinder.findByEmailAddressDomain(emailAddressDomains);
	}

	@Override
	public List<FaroProject> getFaroProjectsByUserId(long userId) {
		return faroProjectPersistence.findByUserId(userId);
	}

	@Override
	public List<FaroProject> getJoinableFaroProjects(User user)
		throws PortalException {

		List<FaroProject> faroProjects =
			faroProjectLocalService.getFaroProjectsByEmailAddressDomain(
				StringUtil.extractLast(user.getEmailAddress(), CharPool.AT));

		List<Group> groups = groupLocalService.getUserGroups(
			user.getUserId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new GroupNameComparator(true));

		Stream<Group> groupsStream = groups.stream();

		Set<Long> groupIds = groupsStream.map(
			Group::getGroupId
		).collect(
			Collectors.toSet()
		);

		Stream<FaroProject> faroProjectsStream = faroProjects.stream();

		return faroProjectsStream.filter(
			faroProject ->
				!groupIds.contains(faroProject.getGroupId()) &&
				StringUtil.equals(
					faroProject.getState(), FaroProjectConstants.STATE_READY)
		).collect(
			Collectors.toList()
		);
	}

	@Override
	public void sendCreatedWorkspaceEmail(String weDeployKey) throws Exception {
		FaroProject faroProject = fetchFaroProjectByWeDeployKey(weDeployKey);

		FaroUser faroUser = faroUserLocalService.fetchOwnerFaroUser(
			faroProject.getGroupId());

		if (faroUser == null) {
			return;
		}

		String body = StringUtil.read(
			getClassLoader(),
			"com/liferay/osb/faro/dependencies/created-workspace.html");

		User user = userLocalService.getUser(faroProject.getUserId());

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", user.getLocale(), getClass());

		body = StringUtil.replace(
			body,
			new String[] {
				"[$BUTTON_TEXT$]", "[$BUTTON_URL$]", "[$HELP_MSG$]",
				"[$LINK_WORKSPACE$]", "[$LOGO_ICON_URL$]",
				"[$NOTIFICATION_MSG_1$]", "[$NOTIFICATION_MSG_2$]",
				"[$NOTIFICATION_MSG_3$]", "[$NOTIFICATION_MSG_4$]",
				"[$NOTIFICATION_MSG_5$]", "[$NOTIFICATION_MSG_6$]",
				"[$NOTIFICATION_MSG_7$]", "[$NOTIFICATION_MSG_8$]",
				"[$TITLE_ICON_URL$]"
			},
			new String[] {
				_language.get(resourceBundle, "go-to-workspace"), _FARO_URL,
				_language.format(
					resourceBundle, "email-need-more-help",
					new String[] {
						"<a class=\"body-link\" href=\"" +
							DocumentationConstants.BASE_URL + "\">",
						"</a>"
					}),
				StringBundler.concat(
					"<a class=\"body-link\" href=\"", _FARO_URL, "\">",
					_FARO_URL, "</a>"),
				EmailUtil.getLogoIconURL(),
				_language.get(resourceBundle, "welcome-to-analytics-cloud"),
				_language.format(
					resourceBundle, "email-your-workspace-x-is-ready",
					faroProject.getName()),
				_language.format(
					resourceBundle, "email-sign-in-or-create-an-account",
					new String[] {
						"<a class=\"body-link\" href=\"" + _FARO_URL + "\">",
						"</a>",
						"<b class=\"link-override\">" +
							faroUser.getEmailAddress() + "</strong>"
					}),
				_language.get(resourceBundle, "getting-started"),
				_language.get(
					resourceBundle, "get-up-and-running-in-three-steps"),
				_language.get(
					resourceBundle,
					"connect-your-liferay-dxp-sites-to-a-property"),
				_language.get(resourceBundle, "connect-and-map-user-data"),
				_language.get(
					resourceBundle, "invite-teammates-to-collaborate"),
				EmailUtil.getTitleIconURL()
			});

		String subject = _language.get(
			resourceBundle, "your-analytics-cloud-workspace-is-ready");

		_mailService.sendEmail(
			new MailMessage(
				new InternetAddress("ac@liferay.com", "Analytics Cloud"),
				new InternetAddress(faroUser.getEmailAddress(), null), subject,
				body, true));

		if (_log.isInfoEnabled()) {
			_log.info(
				"Created workspace email notification sent to " +
					faroUser.getEmailAddress());
		}
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public FaroProject updateState(long faroProjectId, String state) {
		FaroProject faroProject = faroProjectPersistence.fetchByPrimaryKey(
			faroProjectId);

		if (faroProject == null) {
			return null;
		}

		faroProject.setState(state);

		return faroProjectPersistence.update(faroProject);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public FaroProject updateSubscription(
		long faroProjectId, String subscription) {

		FaroProject faroProject = faroProjectPersistence.fetchByPrimaryKey(
			faroProjectId);

		if (faroProject == null) {
			return null;
		}

		faroProject.setModifiedTime(System.currentTimeMillis());
		faroProject.setSubscription(subscription);

		return faroProjectPersistence.update(faroProject);
	}

	private static final String _FARO_URL = System.getenv("FARO_URL");

	private static final Log _log = LogFactoryUtil.getLog(
		FaroProjectLocalServiceImpl.class);

	@ServiceReference(type = Language.class)
	private Language _language;

	@ServiceReference(type = MailService.class)
	private MailService _mailService;

}