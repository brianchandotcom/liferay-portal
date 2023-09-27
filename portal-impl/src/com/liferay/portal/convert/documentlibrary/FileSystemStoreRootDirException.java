/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.convert.documentlibrary;

import com.liferay.portal.convert.ConvertException;

/**
 * @author László Csontos
 */
public class FileSystemStoreRootDirException extends ConvertException {

	public FileSystemStoreRootDirException() {
	}

	public FileSystemStoreRootDirException(String msg) {
		super(msg);
	}

	public FileSystemStoreRootDirException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public FileSystemStoreRootDirException(Throwable throwable) {
		super(throwable);
	}

}