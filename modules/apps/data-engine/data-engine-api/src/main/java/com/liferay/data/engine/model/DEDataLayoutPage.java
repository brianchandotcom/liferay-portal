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

package com.liferay.data.engine.model;

import com.liferay.petra.lang.HashUtil;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;

/**
 * This class represents a layout's page made of a collection of {@link DEDataLayoutRow}.
 * @review
 * @author Jeyvison Nascimento
 */
public class DEDataLayoutPage {

	/**
	 * Overriden equals method
	 * @param obj
	 * @return
	 * @review
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DEDataLayoutPage)) {
			return false;
		}

		DEDataLayoutPage deDataLayoutPage = (DEDataLayoutPage)obj;

		if (Arrays.equals(
				_deDataLayoutRows.toArray(),
				deDataLayoutPage._deDataLayoutRows.toArray()) &&
			Objects.equals(_description, deDataLayoutPage._description) &&
			Objects.equals(_title, deDataLayoutPage._title)) {

			return true;
		}

		return false;
	}

	/**
	 * Returns a {@link Queue} of all the {@link DEDataLayoutRow} that belongs
	 * to the page. Otherwise, returns an empty Queue.
	 * @return {@code Queue<DEDataLayoutRow> }
	 * @review
	 */
	public Queue<DEDataLayoutRow> getDEDataLayoutRows() {
		return _deDataLayoutRows;
	}

	/**
	 * Returns the localized description of the page
	 * @return A Map containing the localized page description
	 * @review
	 */
	public Map<String, String> getDescription() {
		return _description;
	}

	/**
	 * Returns the localized page title
	 * @return A Map containing the localized page title.
	 * @review
	 */
	public Map<String, String> getTitle() {
		return _title;
	}

	/**
	 * Overriden hashCode Method
	 * @return
	 * @review
	 */
	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _title);

		hash = HashUtil.hash(hash, _description);

		return HashUtil.hash(hash, _deDataLayoutRows);
	}

	/**
	 * Sets a Queue of {@link DEDataLayoutRow} to the page. if parameter is null
	 * then a new queue is created.
	 * @param deDataLayoutRows a Queue of {@link DEDataLayoutRow}
	 * @review
	 */
	public void setDEDataLayoutRows(Queue<DEDataLayoutRow> deDataLayoutRows) {
		if (deDataLayoutRows == null) {
			deDataLayoutRows = new ArrayDeque<>();
		}

		_deDataLayoutRows = deDataLayoutRows;
	}

	/**
	 * Sets the localized description of the page. If the description parameter is null,
	 * a new map is defined.
	 * @param description {@link String}
	 * @review
	 */
	public void setDescription(Map<String, String> description) {
		if (description == null) {
			description = new HashMap<>();
		}

		_description = description;
	}

	/**
	 * Sets the localized page title. If the title parameter is null a new map
	 * is defined.
	 * @param title A string containing the title of the page
	 * @review
	 */
	public void setTitle(Map<String, String> title) {
		if (title == null) {
			title = new HashMap<>();
		}

		_title = title;
	}

	private Queue<DEDataLayoutRow> _deDataLayoutRows = new ArrayDeque<>();
	private Map<String, String> _description = new HashMap<>();
	private Map<String, String> _title = new HashMap<>();

}