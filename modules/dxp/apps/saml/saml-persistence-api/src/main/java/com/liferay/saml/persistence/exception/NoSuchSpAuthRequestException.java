/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.saml.persistence.exception;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Mika Koivisto
 */
@ProviderType
public class NoSuchSpAuthRequestException extends NoSuchModelException {

	public NoSuchSpAuthRequestException() {
	}

	public NoSuchSpAuthRequestException(String msg) {
		super(msg);
	}

	public NoSuchSpAuthRequestException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public NoSuchSpAuthRequestException(Throwable cause) {
		super(cause);
	}

}