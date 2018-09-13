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

package com.liferay.forms.apio.internal.model;

/**
 * @author Javier Gamarra
 */
public class FileEntryValue {

	public FileEntryValue(
		long fileEntryId, long groupId, String title, String type, String uuid,
		String version) {

		this.fileEntryId = fileEntryId;
		this.groupId = groupId;
		this.title = title;
		this.type = type;
		this.uuid = uuid;
		this.version = version;
	}

	public final long fileEntryId;
	public final long groupId;
	public final String title;
	public final String type;
	public final String uuid;
	public final String version;

}