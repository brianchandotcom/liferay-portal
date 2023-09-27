/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Diego Hu
 */
public class FragmentCompositionDescriptionException extends PortalException {

	public FragmentCompositionDescriptionException() {
	}

	public FragmentCompositionDescriptionException(String msg) {
		super(msg);
	}

	public FragmentCompositionDescriptionException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public FragmentCompositionDescriptionException(Throwable throwable) {
		super(throwable);
	}

}