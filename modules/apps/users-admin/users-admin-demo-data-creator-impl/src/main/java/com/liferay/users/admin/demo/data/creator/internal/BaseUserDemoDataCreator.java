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

package com.liferay.users.admin.demo.data.creator.internal;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.security.RandomUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.users.admin.demo.data.creator.UserDemoDataCreator;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.text.DateFormat;
import java.text.ParseException;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
public abstract class BaseUserDemoDataCreator implements UserDemoDataCreator {

	public User createUser(long companyId, String emailAddress)
		throws PortalException {

		return createUser(
			companyId, StringPool.BLANK, emailAddress, StringPool.BLANK,
			StringPool.BLANK);
	}

	public User createUser(
			long companyId, String screenName, String emailAddress,
			String firstName, String lastName)
		throws PortalException {

		boolean male = true;
		Date birthDate = new Date();
		byte[] portraitBytes = null;

		try {
			URL url = new URL(_RANDOM_USER_API);

			try (InputStream is = url.openStream()) {
				String json = StringUtil.read(is);

				JSONObject rootJSONObject = JSONFactoryUtil.createJSONObject(
					json);

				JSONArray jsonArray = rootJSONObject.getJSONArray("results");

				JSONObject userJSONObject = jsonArray.getJSONObject(0);

				emailAddress = _getEmailAddress(emailAddress, userJSONObject);
				male = StringUtil.equalsIgnoreCase(
					userJSONObject.getString("gender"), "male");
				birthDate = _getBirthDate(birthDate, userJSONObject);

				JSONObject pictureJSONObject = userJSONObject.getJSONObject(
					"picture");

				String portraitURL = pictureJSONObject.getString("large");

				portraitBytes = _getBytes(new URL(portraitURL));
			}
		}
		catch (IOException ioException) {
			if (_log.isWarnEnabled()) {
				_log.warn(ioException, ioException);
			}

			if (Validator.isNull(emailAddress)) {
				String randomString = StringUtil.randomString();

				emailAddress = randomString.concat("@liferay.com");
			}
		}

		User user = userLocalService.fetchUserByEmailAddress(
			companyId, emailAddress);

		if (user != null) {
			return user;
		}

		user = _createBasicUser(
			companyId, screenName, emailAddress, firstName, lastName, male,
			birthDate);

		_userIds.add(user.getUserId());

		if (portraitBytes != null) {
			userLocalService.updatePortrait(user.getUserId(), portraitBytes);
		}

		return user;
	}

	@Override
	public void delete() throws PortalException {
		try {
			for (long userId : _userIds) {
				_userIds.remove(userId);

				userLocalService.deleteUser(userId);
			}
		}
		catch (NoSuchUserException noSuchUserException) {
			if (_log.isWarnEnabled()) {
				_log.warn(noSuchUserException, noSuchUserException);
			}
		}
	}

	protected String[] getFullNameArray(String emailAddress) {
		String emailAccountName = emailAddress.substring(
			0, emailAddress.indexOf(StringPool.AT));

		String[] fullNameArray = StringUtil.split(
			emailAccountName, StringPool.PERIOD);

		String firstName = StringUtil.randomString();
		String lastName = StringUtil.randomString();

		if (fullNameArray.length > 0) {
			firstName = StringUtil.upperCaseFirstLetter(fullNameArray[0]);
		}

		if (fullNameArray.length > 1) {
			lastName = StringUtil.upperCaseFirstLetter(fullNameArray[1]);
		}

		return new String[] {firstName, lastName};
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	protected UserLocalService userLocalService;

	private static List<String> _read(String fileName) {
		return Arrays.asList(
			StringUtil.split(
				StringUtil.read(
					BaseUserDemoDataCreator.class,
					"dependencies/" + fileName + ".txt"),
				CharPool.NEW_LINE));
	}

	private User _createBasicUser(
			long companyId, String screenName, String emailAddress,
			String firstName, String lastName, boolean male, Date birthDate)
		throws PortalException {

		String[] fullNameArray = getFullNameArray(emailAddress);

		if (Validator.isNull(firstName)) {
			firstName = fullNameArray[0];
		}

		if (Validator.isNull(lastName)) {
			lastName = fullNameArray[1];
		}

		boolean autoPassword = false;
		String password1 = "test";
		String password2 = "test";
		boolean autoScreenName = Validator.isNull(screenName);
		Locale locale = LocaleUtil.getDefault();
		String middleName = StringPool.BLANK;
		long prefixId = 0;
		long suffixId = 0;

		Calendar calendar = Calendar.getInstance();

		calendar.setTime(birthDate);

		int birthdayMonth = calendar.get(Calendar.MONTH);
		int birthdayDay = calendar.get(Calendar.DATE);
		int birthdayYear = calendar.get(Calendar.YEAR);

		String jobTitle = _getRandomElement(_jobTitles);
		long[] groupIds = null;
		long[] organizationIds = null;
		long[] roleIds = null;
		long[] userGroupIds = null;
		boolean sendMail = false;

		return userLocalService.addUser(
			UserConstants.USER_ID_DEFAULT, companyId, autoPassword, password1,
			password2, autoScreenName, screenName, emailAddress, locale,
			firstName, middleName, lastName, prefixId, suffixId, male,
			birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds,
			organizationIds, roleIds, userGroupIds, sendMail,
			new ServiceContext());
	}

	private Date _getBirthDate(Date birthDate, JSONObject userJSONObject) {
		String dob = userJSONObject.getString("dob");

		try {
			DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");

			birthDate = dateFormat.parse(dob);
		}
		catch (ParseException parseException) {
			if (_log.isWarnEnabled()) {
				_log.warn(parseException, parseException);
			}
		}

		return birthDate;
	}

	private byte[] _getBytes(URL url) throws IOException {
		try (InputStream is = url.openStream()) {
			return FileUtil.getBytes(is);
		}
	}

	private String _getEmailAddress(
		String emailAddress, JSONObject userJSONObject) {

		if (Validator.isNull(emailAddress)) {
			emailAddress = userJSONObject.getString("email");
		}

		if (!Validator.isEmailAddress(emailAddress)) {
			String[] emailAddressParts = StringUtil.split(
				emailAddress, CharPool.AT);

			String normalizedEmailAddress = FriendlyURLNormalizerUtil.normalize(
				emailAddressParts[0]);

			if (StringUtil.endsWith(normalizedEmailAddress, CharPool.DASH)) {
				normalizedEmailAddress = StringUtil.randomString();
			}

			emailAddress = String.format(
				"%s@%s", normalizedEmailAddress, emailAddressParts[1]);
		}

		return emailAddress;
	}

	private String _getRandomElement(List<String> list) {
		return list.get(RandomUtil.nextInt(list.size()));
	}

	private static final String _RANDOM_USER_API =
		"https://randomuser.me/api?inc=email,gender,dob,picture&noinfo";

	private static final Log _log = LogFactoryUtil.getLog(
		BaseUserDemoDataCreator.class);

	private static final List<String> _jobTitles = _read("job_titles");

	private final List<Long> _userIds = new CopyOnWriteArrayList<>();

}