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

package com.liferay.commerce.headless.user.apio.internal.form;

import com.liferay.apio.architect.form.Form;
import com.liferay.commerce.headless.user.apio.internal.util.UserHelper;
import com.liferay.portal.kernel.util.Validator;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Rodrigo Guedes de Souza
 */
public class UserCreatorForm {

	public static Form<UserCreatorForm> buildForm(
		Form.Builder<UserCreatorForm> formBuilder) {

		return formBuilder.title(
			__ -> "The person creator form"
		).description(
			__ -> "This form can be used to create a person"
		).constructor(
			UserCreatorForm::new
		).addRequiredString(
			"gender", UserCreatorForm::_setGender
		).addOptionalString(
			"alternateName", UserCreatorForm::_setAlternateName
		).addRequiredDate(
			"birthDate", UserCreatorForm::_setBirthDate
		).addRequiredString(
			"email", UserCreatorForm::_setEmail
		).addRequiredString(
			"familyName", UserCreatorForm::_setFamilyName
		).addRequiredString(
			"givenName", UserCreatorForm::_setGivenName
		).addRequiredString(
			"jobTitle", UserCreatorForm::_setJobTitle
		).addRequiredString(
			"password1", UserCreatorForm::_setPassword1
		).addRequiredString(
			"password2", UserCreatorForm::_setPassword2
		).addOptionalLongList(
			"accountIds", UserCreatorForm::_setAccountIds
		).addOptionalLongList(
			"roleIds", UserCreatorForm::_setRoleIds
		).build();
	}

	public long[] getAccountIds() {
		return UserHelper.convertLongListToArray(_accountIds);
	}

	/**
	 * Returns the person's alternate name
	 *
	 * @return the person's alternate name
	 * @review
	 */
	public String getAlternateName() {
		return _alternateName;
	}

	/**
	 * Returns the person's birthday day
	 *
	 * @return the person's birthday day
	 * @review
	 */
	public int getBirthdayDay() {
		return _birthdayDay;
	}

	/**
	 * Returns the person's birthday month
	 *
	 * @return the person's birthday month
	 * @review
	 */
	public int getBirthdayMonth() {
		return _birthdayMonth;
	}

	/**
	 * Returns the person's birthday year
	 *
	 * @return the person's birthday year
	 * @review
	 */
	public int getBirthdayYear() {
		return _birthdayYear;
	}

	/**
	 * Returns the person's email
	 *
	 * @return the person's email
	 * @review
	 */
	public String getEmail() {
		return _email;
	}

	/**
	 * Returns the person's family name
	 *
	 * @return the person's family name
	 * @review
	 */
	public String getFamilyName() {
		return _familyName;
	}

	/**
	 * Returns the person's given name
	 *
	 * @return the person's given name
	 * @review
	 */
	public String getGivenName() {
		return _givenName;
	}

	/**
	 * Returns the person's job title
	 *
	 * @return the person's job title
	 * @review
	 */
	public String getJobTitle() {
		return _jobTitle;
	}

	/**
	 * Returns the person's password (first attempt)
	 *
	 * @return the person's password (first attempt)
	 * @review
	 */
	public String getPassword1() {
		return _password1;
	}

	/**
	 * Returns the person's password (second attempt)
	 *
	 * @return the person's password (second attempt)
	 * @review
	 */
	public String getPassword2() {
		return _password2;
	}

	public long[] getRoleIds() {
		return UserHelper.convertLongListToArray(_roleIds);
	}

	/**
	 * Checks if the person has an alternate name
	 *
	 * @return {@code true} if the person has an alternate name; {@code false}
	 *         otherwise
	 * @review
	 */
	public boolean hasAlternateName() {
		return Validator.isNull(_alternateName);
	}

	/**
	 * Checks if the person is a male
	 *
	 * @return {@code true} if the person is a male; {@code false} otherwise
	 * @review
	 */
	public boolean isMale() {
		return _male;
	}

	private void _setAccountIds(List<Long> accountIds) {
		_accountIds = accountIds;
	}

	private void _setAlternateName(String alternateName) {
		_alternateName = alternateName;
	}

	private void _setBirthDate(Date birthDate) {
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(birthDate);

		_birthdayMonth = calendar.get(Calendar.MONTH);
		_birthdayDay = calendar.get(Calendar.DATE);
		_birthdayYear = calendar.get(Calendar.YEAR);
	}

	private void _setEmail(String emailAddress) {
		_email = emailAddress;
	}

	private void _setFamilyName(String lastName) {
		_familyName = lastName;
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

	private List<Long> _accountIds;
	private String _alternateName;
	private int _birthdayDay;
	private int _birthdayMonth;
	private int _birthdayYear;
	private String _email;
	private String _familyName;
	private String _givenName;
	private String _jobTitle;
	private Boolean _male;
	private String _password1;
	private String _password2;
	private List<Long> _roleIds;

}