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

package com.liferay.commerce.data.integration.apio.internal.form;

import com.liferay.apio.architect.form.Form;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.List;

/**
 * @author Rodrigo Guedes de Souza
 */
public class UserUpdaterForm {

	public static Form<UserUpdaterForm> buildForm(
		Form.Builder<UserUpdaterForm> formBuilder) {

		return formBuilder.title(
			__ -> "The person updater form"
		).description(
			__ -> "This form can be used to update a user"
		).constructor(
			UserUpdaterForm::new
		).addOptionalString(
			"alternateName", UserUpdaterForm::setAlternateName
		).addRequiredString(
			"email", UserUpdaterForm::setEmail
		).addRequiredString(
			"familyName", UserUpdaterForm::setFamilyName
		).addRequiredString(
			"givenName", UserUpdaterForm::setGivenName
		).addRequiredString(
			"jobTitle", UserUpdaterForm::setJobTitle
		).addRequiredString(
			"password", UserUpdaterForm::setPassword
		).addOptionalLongList(
			"accountIds", UserUpdaterForm::_setAccountIds
		).addOptionalLongList(
			"roleIds", UserUpdaterForm::_setRoleIds
		).build();
	}

	public long[] getAccountIds() {
		return ArrayUtil.toLongArray(_accountIds);
	}

	public String getAlternateName() {
		return _alternateName;
	}

	public String getEmail() {
		return _email;
	}

	public String getFamilyName() {
		return _familyName;
	}

	public String getGivenName() {
		return _givenName;
	}

	public String getJobTitle() {
		return _jobTitle;
	}

	public String getPassword() {
		return _password;
	}

	public long[] getRoleIds() {
		return ArrayUtil.toLongArray(_roleIds);
	}

	public void setAlternateName(String alternateName) {
		_alternateName = alternateName;
	}

	public void setEmail(String emailAddress) {
		_email = emailAddress;
	}

	public void setFamilyName(String lastName) {
		_familyName = lastName;
	}

	public void setGivenName(String givenName) {
		_givenName = givenName;
	}

	public void setJobTitle(String jobTitle) {
		_jobTitle = jobTitle;
	}

	public void setPassword(String password) {
		_password = password;
	}

	private void _setAccountIds(List<Long> accountIds) {
		_accountIds = accountIds;
	}

	private void _setRoleIds(List<Long> roleIds) {
		_roleIds = roleIds;
	}

	private List<Long> _accountIds;
	private String _alternateName;
	private String _email;
	private String _familyName;
	private String _givenName;
	private String _jobTitle;
	private String _password;
	private List<Long> _roleIds;

}