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

package com.liferay.commerce.frontend.model;

/**
 * @author Alessio Antonio Rendina
 */
public class TimelineModel {

	public TimelineModel(
		long id, String date, String description, String title) {

		_id = id;
		_date = date;
		_description = description;
		_title = title;
	}

	public String getDate() {
		return _date;
	}

	public String getDescription() {
		return _description;
	}

	public long getId() {
		return _id;
	}

	public String getTitle() {
		return _title;
	}

	private final String _date;
	private final String _description;
	private final long _id;
	private final String _title;

}