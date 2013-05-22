/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.mail.util;

import com.liferay.mail.model.Filter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PropsUtil;

import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * @author Brian Wing Shun Chan
 */
public class FuseMailHook implements Hook {

	public FuseMailHook() {
		_client = new HttpClient();
	}

	public void addForward(
		long companyId, long userId, List<Filter> filters,
		List<String> emailAddresses, boolean leaveCopy) {
	}

	public void addUser(
		long companyId, long userId, String password, String firstName,
		String middleName, String lastName, String emailAddress) {

		try {
			String mailUserId = getMailUserId(companyId, userId);

			PostMethod method = getPostMethod();

			method.addParameter("request", "order");
			method.addParameter("user", mailUserId);
			method.addParameter("password", password);
			method.addParameter("first_name", firstName);
			method.addParameter("last_name", lastName);
			method.addParameter("account_type", _ACCOUNT_TYPE);
			method.addParameter("group_parent", _GROUP_PARENT);
			method.addParameter("alias[0]", emailAddress);

			executeMethod(method);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void addVacationMessage(
		long companyId, long userId, String emailAddress,
		String vacationMessage) {
	}

	public void deleteEmailAddress(long companyId, long userId) {
		try {
			User user = UserLocalServiceUtil.getUserById(userId);

			String mailUserId = getMailUserId(companyId, userId);

			PostMethod method = getPostMethod();

			method.addParameter("request", "removealias");
			method.addParameter("user", mailUserId);
			method.addParameter("alias", user.getEmailAddress());

			executeMethod(method);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void deleteUser(long companyId, long userId) {
		try {
			String mailUserId = getMailUserId(companyId, userId);

			PostMethod method = getPostMethod();

			method.addParameter("request", "terminate");
			method.addParameter("user", mailUserId);

			executeMethod(method);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void updateBlocked(
		long companyId, long userId, List<String> blocked) {
	}

	public void updateEmailAddress(
		long companyId, long userId, String emailAddress) {

		try {
			deleteEmailAddress(companyId, userId);

			String mailUserId = getMailUserId(companyId, userId);

			PostMethod method = getPostMethod();

			method.addParameter("request", "modify");
			method.addParameter("user", mailUserId);
			method.addParameter("alias[0]", emailAddress);

			executeMethod(method);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void updatePassword(long companyId, long userId, String password) {
		try {
			String mailUserId = getMailUserId(companyId, userId);

			PostMethod method = getPostMethod();

			method.addParameter("request", "modify");
			method.addParameter("user", mailUserId);
			method.addParameter("password", password);

			executeMethod(method);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected int executeMethod(PostMethod method) throws Exception {
		HttpClient client = getHttpClient();

		int status = client.executeMethod(method);

		if (!_log.isDebugEnabled()) {
			return status;
		}

		_log.debug("Posting to URI: " + method.getURI());

		NameValuePair[] pairs = method.getParameters();

		if (pairs.length > 0) {
			StringBundler sb = new StringBundler(pairs.length * 3 + 1);

			sb.append("With parameters:\n");

			for (int i = 0; i < pairs.length; i++) {
				sb.append("\t");
				sb.append(pairs[i]);
				sb.append("\n");
			}

			_log.debug(sb.toString());
		}

		_log.debug("Status: " + status);
		_log.debug("Response body: " + method.getResponseBodyAsString());

		return status;
	}

	protected HttpClient getHttpClient() {
		return _client;
	}

	protected String getMailUserId(long companyId, long userId)
		throws Exception {

		Company company = CompanyLocalServiceUtil.getCompanyById(companyId);

		String mailUserId = company.getMx().concat(StringPool.PERIOD).concat(
			String.valueOf(userId));

		if (_log.isDebugEnabled()) {
			_log.debug("Mail user id " + mailUserId + " for user id " + userId);
		}

		return mailUserId;
	}

	protected PostMethod getPostMethod() {
		PostMethod post = new PostMethod(_URL);

		post.addParameter("PlatformUser", _USERNAME);
		post.addParameter("PlatformPassword", _PASSWORD);

		return post;
	}

	private static final String _ACCOUNT_TYPE = PropsUtil.get(
		PropsKeys.MAIL_HOOK_FUSEMAIL_ACCOUNT_TYPE);

	private static final String _GROUP_PARENT = PropsUtil.get(
		PropsKeys.MAIL_HOOK_FUSEMAIL_GROUP_PARENT);

	private static final String _PASSWORD = PropsUtil.get(
		PropsKeys.MAIL_HOOK_FUSEMAIL_PASSWORD);

	private static final String _URL = PropsUtil.get(
		PropsKeys.MAIL_HOOK_FUSEMAIL_URL);

	private static final String _USERNAME = PropsUtil.get(
		PropsKeys.MAIL_HOOK_FUSEMAIL_USERNAME);

	private static Log _log = LogFactoryUtil.getLog(FuseMailHook.class);

	private HttpClient _client;

}