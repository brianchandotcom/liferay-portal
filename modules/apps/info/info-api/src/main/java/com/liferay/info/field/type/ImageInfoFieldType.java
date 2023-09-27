/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.field.type;

/**
 * @author Jorge Ferrer
 */
public class ImageInfoFieldType implements InfoFieldType {

	public static final Attribute<ImageInfoFieldType, String>
		ALLOWED_FILE_EXTENSIONS = new Attribute<>();

	public static final ImageInfoFieldType INSTANCE = new ImageInfoFieldType();

	public static final Attribute<ImageInfoFieldType, Long> MAX_FILE_SIZE =
		new Attribute<>();

	@Override
	public String getName() {
		return "image";
	}

	private ImageInfoFieldType() {
	}

}