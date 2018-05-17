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

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Rodrigo Guedes de Souza
 */
public class RoleForm {

	public static Form<RoleForm> buildForm(Form.Builder<RoleForm> formBuilder) {
		return formBuilder.title(
			__ -> "The role creator form"
		).description(
			__ -> "This form can be used to create a role"
		).constructor(
			RoleForm::new
		).addRequiredString(
			"title", RoleForm::_setTitle
		).addOptionalString(
			"description", RoleForm::_setDescription
		).addOptionalString(
			"name", RoleForm::_setName
		).addOptionalLongList(
			"userIds", RoleForm::_setUsersIds
		).build();
	}

	public String getDescription() {
		return _description;
	}

	public Map<Locale, String> getDescriptionMap() {
		return Collections.singletonMap(Locale.getDefault(), _description);
	}

	public String getName() {
		return _name;
	}

	public String getTitle() {
		return _title;
	}

	public Map<Locale, String> getTitleMap() {
		return Collections.singletonMap(Locale.getDefault(), _title);
	}

	public List<Long> getUserIds() {
		return _userIds;
	}

	private void _setDescription(String description) {
		_description = description;
	}

	private void _setName(String name) {
		_name = name;
	}

	private void _setTitle(String title) {
		_title = title;
	}

	private void _setUsersIds(List<Long> userIds) {
		_userIds = userIds;
	}

	private String _description;
	private String _name;
	private String _title;
	private List<Long> _userIds;

}