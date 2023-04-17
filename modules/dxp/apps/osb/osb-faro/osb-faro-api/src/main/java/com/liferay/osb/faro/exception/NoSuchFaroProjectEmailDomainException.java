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

package com.liferay.osb.faro.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Matthew Kong
 */
public class NoSuchFaroProjectEmailDomainException
	extends NoSuchModelException {

	public NoSuchFaroProjectEmailDomainException() {
	}

	public NoSuchFaroProjectEmailDomainException(String msg) {
		super(msg);
	}

	public NoSuchFaroProjectEmailDomainException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public NoSuchFaroProjectEmailDomainException(Throwable throwable) {
		super(throwable);
	}

}