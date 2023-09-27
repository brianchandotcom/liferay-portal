/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.exception;

/**
 * @author Lourdes Fernández Besada
 */
public class FragmentEntryFieldTypesException
	extends FragmentEntryTypeOptionsException {

	public FragmentEntryFieldTypesException() {
	}

	public FragmentEntryFieldTypesException(String msg) {
		super(msg);
	}

	public FragmentEntryFieldTypesException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public FragmentEntryFieldTypesException(Throwable throwable) {
		super(throwable);
	}

}