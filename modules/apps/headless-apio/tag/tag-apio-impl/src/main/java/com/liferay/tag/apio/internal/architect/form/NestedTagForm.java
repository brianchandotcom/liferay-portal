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

package com.liferay.tag.apio.internal.architect.form;

import com.liferay.apio.architect.form.Form;
import com.liferay.apio.architect.form.Form.Builder;

/**
 * Instances of this class represent the values extracted from a nested tag
 * form.
 *
 * @author Eduardo Perez
 */
public class NestedTagForm {

	public static Form<NestedTagForm> buildForm(
		Builder<NestedTagForm> builder) {

		return builder.title(
			__ -> "The tag form"
		).description(
			__ -> "This form can be used to create or update a tag"
		).constructor(
			NestedTagForm::new
		).addRequiredLong(
			"website", NestedTagForm::_setGroupId
		).addRequiredString(
			"name", NestedTagForm::_setName
		).build();
	}

	public long getGroupId() {
		return _groupId;
	}

	public String getName() {
		return _name;
	}

	private void _setGroupId(long groupId) {
		_groupId = groupId;
	}

	private void _setName(String name) {
		_name = name;
	}

	private long _groupId;
	private String _name;

}