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

package com.liferay.structured.content.apio.internal.architect.form;

import com.liferay.apio.architect.form.Form;
import com.liferay.apio.architect.form.Form.Builder;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

/**
 * Instances of this class represent the values extracted from a structured
 * content form.
 *
 * @author Alejandro Hernández
 * @review
 */
public class StructuredContentUpdaterForm {

	/**
	 * Builds a {@code Form} that generates {@code StructuredContentUpdaterForm}
	 * depending on the HTTP body.
	 *
	 * @param  formBuilder the {@code Form} builder
	 * @return a structured content updater form
	 * @review
	 */
	public static Form<StructuredContentUpdaterForm> buildForm(
		Builder<StructuredContentUpdaterForm> formBuilder) {

		return formBuilder.title(
			__ -> "The structured content updater form"
		).description(
			__ -> "This form can be used to update a structured content"
		).constructor(
			StructuredContentUpdaterForm::new
		).addRequiredLong(
			"group", StructuredContentUpdaterForm::setGroup
		).addRequiredLong(
			"user", StructuredContentUpdaterForm::setUser
		).addRequiredLong(
			"version", StructuredContentUpdaterForm::setVersion
		).addRequiredString(
			"description", StructuredContentUpdaterForm::setDescription
		).addRequiredString(
			"text", StructuredContentUpdaterForm::setText
		).addRequiredString(
			"title", StructuredContentUpdaterForm::setTitle
		).build();
	}

	/**
	 * Returns the structured content's description map.
	 *
	 * @return the structured content's description map
	 * @review
	 */
	public Map<Locale, String> getDescriptionMap() {
		return Collections.singletonMap(Locale.getDefault(), _description);
	}

	/**
	 * Returns the structured content group's ID.
	 *
	 * @return the structured content group's ID
	 * @review
	 */
	public long getGroup() {
		return _group;
	}

	/**
	 * Returns the structured content's text.
	 *
	 * @return the structured content's text
	 * @review
	 */
	public String getText() {
		return _text;
	}

	/**
	 * Returns the structured content's title map.
	 *
	 * @return the structured content's title map
	 * @review
	 */
	public Map<Locale, String> getTitleMap() {
		return Collections.singletonMap(Locale.getDefault(), _title);
	}

	/**
	 * Returns the structured content user's ID.
	 *
	 * @return the structured content user's ID
	 * @review
	 */
	public long getUser() {
		return _user;
	}

	/**
	 * Returns the structured content version's ID.
	 *
	 * @return the structured content version's ID
	 * @review
	 */
	public long getVersion() {
		return _version;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setGroup(long group) {
		_group = group;
	}

	public void setText(String text) {
		_text = text;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public void setUser(long user) {
		_user = user;
	}

	public void setVersion(long version) {
		_version = version;
	}

	private String _description;
	private long _group;
	private String _text;
	private String _title;
	private long _user;
	private long _version;

}