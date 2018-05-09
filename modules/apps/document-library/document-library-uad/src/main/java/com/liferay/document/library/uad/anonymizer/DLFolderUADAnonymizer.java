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

package com.liferay.document.library.uad.anonymizer;

import com.liferay.document.library.uad.constants.DLUADConstants;
import com.liferay.user.associated.data.anonymizer.UADAnonymizer;

import org.osgi.service.component.annotations.Component;

/**
 * @author William Newbury
 */
@Component(
	immediate = true,
	property = "model.class.name=" + DLUADConstants.CLASS_NAME_DL_FOLDER,
	service = UADAnonymizer.class
)
public class DLFolderUADAnonymizer extends BaseDLFolderUADAnonymizer {
}