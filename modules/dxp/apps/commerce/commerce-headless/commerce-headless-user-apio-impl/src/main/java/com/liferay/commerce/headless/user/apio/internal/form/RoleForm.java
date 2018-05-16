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