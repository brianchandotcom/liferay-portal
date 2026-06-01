/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
package com.liferay.layout.content.versioning.exception;

import com.liferay.portal.kernel.exception.DuplicateExternalReferenceCodeException;

/**
 * @author Lourdes Fernández Besada
 */
public class DuplicateLayoutContentVersionExternalReferenceCodeException extends DuplicateExternalReferenceCodeException {

	public DuplicateLayoutContentVersionExternalReferenceCodeException() {
	}

	public DuplicateLayoutContentVersionExternalReferenceCodeException(String msg) {
		super(msg);
	}

	public DuplicateLayoutContentVersionExternalReferenceCodeException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public DuplicateLayoutContentVersionExternalReferenceCodeException(Throwable throwable) {
		super(throwable);
	}

}