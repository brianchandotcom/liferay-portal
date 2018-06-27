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
import com.liferay.portal.kernel.util.CalendarFactoryUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Rodrigo Guedes de Souza
 */
public class CommerceUserUpserterForm {

	public static Form<CommerceUserUpserterForm> buildForm(
		Form.Builder<CommerceUserUpserterForm> formBuilder) {

		return formBuilder.title(
			__ -> "The user creator form"
		).description(
			__ -> "This form can be used to create a user"
		).constructor(
			CommerceUserUpserterForm::new
		).addRequiredString(
			"externalReferenceCode",
			CommerceUserUpserterForm::_setExternalReferenceCode
		).addRequiredString(
			"gender", CommerceUserUpserterForm::_setGender
		).addRequiredString(
			"alternateName", CommerceUserUpserterForm::_setAlternateName
		).addRequiredDate(
			"birthDate", CommerceUserUpserterForm::_setBirthDate
		).addRequiredString(
			"email", CommerceUserUpserterForm::_setEmail
		).addOptionalString(
			"familyName", CommerceUserUpserterForm::_setFamilyName
		).addOptionalString(
			"givenName", CommerceUserUpserterForm::_setGivenName
		).addRequiredString(
			"jobTitle", CommerceUserUpserterForm::_setJobTitle
		).addOptionalString(
			"password1", CommerceUserUpserterForm::_setPassword1
		).addOptionalString(
			"password2", CommerceUserUpserterForm::_setPassword2
		).addOptionalLongList(
			"commerceAccountIds",
			CommerceUserUpserterForm::_setCommerceAccountIds
		).addOptionalLongList(
			"roleIds", CommerceUserUpserterForm::_setRoleIds
		).build();
	}

	public String getAlternateName() {
		return _alternateName;
	}

	public int getBirthdayDay() {
		return _birthdayDay;
	}

	public int getBirthdayMonth() {
		return _birthdayMonth;
	}

	public int getBirthdayYear() {
		return _birthdayYear;
	}

	public long[] getCommerceAccountIds() {
		if (_commerceAccountIds == null) {
			return new long[0];
		}

		return ArrayUtil.toLongArray(_commerceAccountIds);
	}

	public String getEmail() {
		return _email;
	}

	public String getExternalReferenceCode() {
		return _externalReferenceCode;
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

	public String getPassword1() {
		return _password1;
	}

	public String getPassword2() {
		return _password2;
	}

	public long[] getRoleIds() {
		if (_roleIds == null) {
			return new long[0];
		}

		return ArrayUtil.toLongArray(_roleIds);
	}

	public boolean isMale() {
		return _male;
	}

	private void _setAlternateName(String alternateName) {
		_alternateName = alternateName;
	}

	private void _setBirthDate(Date birthDate) {
		Calendar calendar = CalendarFactoryUtil.getCalendar();

		calendar.setTime(birthDate);

		_birthdayMonth = calendar.get(Calendar.MONTH);
		_birthdayDay = calendar.get(Calendar.DATE);
		_birthdayYear = calendar.get(Calendar.YEAR);
	}

	private void _setCommerceAccountIds(List<Long> commerceAccountIds) {
		_commerceAccountIds = commerceAccountIds;
	}

	private void _setEmail(String emailAddress) {
		_email = emailAddress;
	}

	private void _setExternalReferenceCode(String externalReferenceCode) {
		_externalReferenceCode = externalReferenceCode;
	}

	private void _setFamilyName(String familyName) {
		_familyName = familyName;
	}

	private void _setGender(String gender) {
		_male = "male".equals(gender);
	}

	private void _setGivenName(String givenName) {
		_givenName = givenName;
	}

	private void _setJobTitle(String jobTitle) {
		_jobTitle = jobTitle;
	}

	private void _setPassword1(String password1) {
		_password1 = password1;
	}

	private void _setPassword2(String password2) {
		_password2 = password2;
	}

	private void _setRoleIds(List<Long> roleIds) {
		_roleIds = roleIds;
	}

	private String _alternateName;
	private int _birthdayDay;
	private int _birthdayMonth;
	private int _birthdayYear;
	private List<Long> _commerceAccountIds;
	private String _email;
	private String _externalReferenceCode;
	private String _familyName;
	private String _givenName;
	private String _jobTitle;
	private Boolean _male;
	private String _password1;
	private String _password2;
	private List<Long> _roleIds;

}