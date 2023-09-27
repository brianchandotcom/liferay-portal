/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.field.type;

/**
 * @author Eudaldo Alonso
 */
public class FileInfoFieldType implements InfoFieldType {

	public static final Attribute<FileInfoFieldType, String>
		ALLOWED_FILE_EXTENSIONS = new Attribute<>();

	public static final Attribute<FileInfoFieldType, FileSourceType>
		FILE_SOURCE = new Attribute<>();

	public static final FileInfoFieldType INSTANCE = new FileInfoFieldType();

	public static final Attribute<FileInfoFieldType, Long> MAX_FILE_SIZE =
		new Attribute<>();

	@Override
	public String getName() {
		return "file";
	}

	public enum FileSourceType {

		DOCUMENTS_AND_MEDIA, USER_COMPUTER

	}

	private FileInfoFieldType() {
	}

}