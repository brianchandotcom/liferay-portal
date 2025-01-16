/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Lourdes Fernández Besada
 */
public class FormContainerParentItemRequiredException extends PortalException {

	public FormContainerParentItemRequiredException() {
	}

	public FormContainerParentItemRequiredException(String msg) {
		super(msg);
	}

	public FormContainerParentItemRequiredException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public FormContainerParentItemRequiredException(Throwable throwable) {
		super(throwable);
	}

}