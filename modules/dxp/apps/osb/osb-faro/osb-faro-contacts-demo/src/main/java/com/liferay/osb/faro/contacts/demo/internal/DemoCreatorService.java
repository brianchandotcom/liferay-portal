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

package com.liferay.osb.faro.contacts.demo.internal;

import com.liferay.osb.faro.constants.FaroProjectConstants;
import com.liferay.osb.faro.constants.FaroUserConstants;
import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.engine.client.model.Channel;
import com.liferay.osb.faro.engine.client.model.Individual;
import com.liferay.osb.faro.engine.client.model.LCPProject;
import com.liferay.osb.faro.engine.client.model.Results;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.service.FaroChannelLocalService;
import com.liferay.osb.faro.service.FaroProjectLocalService;
import com.liferay.osb.faro.service.FaroUserLocalService;
import com.liferay.osb.faro.util.FaroPropsValues;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import java.nio.charset.StandardCharsets;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Matthew Kong
 */
public abstract class DemoCreatorService {

	public void createDemo() throws Exception {
		faroProject = faroProjectLocalService.fetchFaroProjectByCorpProjectUuid(
			FaroPropsValues.FARO_PROJECT_ID);

		if (faroProject == null) {
			faroProject = createFaroProject();
		}

		try {
			createUsers(faroProject);
		}
		catch (Exception exception) {
			log.error("Unable to add users", exception);
		}

		if (hasExistingData()) {
			if (log.isInfoEnabled()) {
				log.info("Skipping demo creation because of existing data");
			}

			return;
		}

		createData();

		createFaroChannels();
	}

	protected static String encodeAuthorizationFields(
		String userName, String password) {

		String authorizationString = StringBundler.concat(
			userName, StringPool.COLON, password);

		return new String(
			Base64.encodeBase64(
				authorizationString.getBytes(StandardCharsets.UTF_8)),
			StandardCharsets.UTF_8);
	}

	protected abstract void createData() throws Exception;

	protected void createFaroChannels() throws Exception {
		Results<Channel> results = contactsEngineClient.getChannels(
			faroProject, 0, 10000, null);

		User user = userLocalService.getUserByEmailAddress(
			portal.getDefaultCompanyId(), "test@liferay.com");

		for (Channel channel : results.getItems()) {
			faroChannelLocalService.addFaroChannel(
				user.getUserId(), channel.getName(), channel.getId(),
				faroProject.getGroupId());
		}
	}

	protected FaroProject createFaroProject() throws Exception {
		Http.Options options = new Http.Options();

		options.addPart("corpProjectUuid", FaroPropsValues.FARO_PROJECT_ID);
		options.addPart("name", FaroPropsValues.FARO_PROJECT_ID);
		options.addPart("ownerEmailAddress", "test@liferay.com");
		options.addPart("serverLocation", LCPProject.Cluster.US.toString());
		options.addPart("timeZoneId", "UTC");
		options.setHeaders(headers);
		options.setLocation(
			"http://localhost:8080/o/faro/main/project/provisioned");
		options.setPost(true);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			http.URLtoString(options));

		FaroProject faroProject =
			faroProjectLocalService.getFaroProjectByGroupId(
				jsonObject.getLong("groupId"));

		faroProject.setState(FaroProjectConstants.STATE_NOT_READY);

		return faroProjectLocalService.updateFaroProject(faroProject);
	}

	protected void createUsers(FaroProject faroProject) throws Exception {
		for (String[] userInfo : _USER_INFO) {
			String screenName = userInfo[0];

			String emailAddress = screenName.concat("@faro.io");

			User user = userLocalService.fetchUserByEmailAddress(
				portal.getDefaultCompanyId(), emailAddress);

			if (user != null) {
				continue;
			}

			String[] screenNameParts = StringUtil.split(
				screenName, StringPool.PERIOD);

			String firstName = screenNameParts[0];

			String lastName = null;

			if (screenNameParts.length > 1) {
				lastName = screenNameParts[1];
			}
			else {
				lastName = screenNameParts[0];
			}

			user = userLocalService.addUserWithWorkflow(
				UserConstants.USER_ID_DEFAULT, portal.getDefaultCompanyId(),
				false, "test", "test", true, screenName, emailAddress,
				LocaleUtil.US, firstName, null, lastName, 0, 0, true, 1, 1,
				1970, null, UserConstants.TYPE_GUEST, null, null, null, null,
				false, null);

			user.setPasswordReset(false);
			user.setPasswordModifiedDate(new Date());
			user.setLastLoginDate(new Date());
			user.setAgreedToTermsOfUse(true);

			userLocalService.updateUser(user);

			Role role = roleLocalService.getRole(
				portal.getDefaultCompanyId(), userInfo[1]);

			faroUserLocalService.addFaroUser(
				user.getUserId(), faroProject.getGroupId(), user.getUserId(),
				role.getRoleId(), emailAddress,
				FaroUserConstants.STATUS_APPROVED, false);
		}
	}

	protected boolean hasExistingData() {
		Results<Individual> individuals = contactsEngineClient.getIndividuals(
			faroProject, (String)null, false, 1, 0, null);

		if (individuals.getTotal() > 0) {
			return true;
		}

		return false;
	}

	protected static final Map<String, String> headers =
		Collections.singletonMap(
			"Authorization",
			"Basic " + encodeAuthorizationFields("test@liferay.com", "test"));
	protected static final Log log = LogFactoryUtil.getLog(
		DemoCreatorService.class);

	@Reference
	protected ContactsEngineClient contactsEngineClient;

	@Reference
	protected FaroChannelLocalService faroChannelLocalService;

	protected FaroProject faroProject;

	@Reference
	protected FaroProjectLocalService faroProjectLocalService;

	@Reference
	protected FaroUserLocalService faroUserLocalService;

	@Reference
	protected Http http;

	@Reference
	protected Portal portal;

	@Reference
	protected RoleLocalService roleLocalService;

	@Reference
	protected UserLocalService userLocalService;

	private static final String[][] _USER_INFO = {
		{"bryan.cheung", RoleConstants.SITE_OWNER},
		{"corbin.murakami", RoleConstants.SITE_MEMBER},
		{"michelle.hoshi", RoleConstants.SITE_ADMINISTRATOR},
		{"test", RoleConstants.SITE_OWNER}
	};

}