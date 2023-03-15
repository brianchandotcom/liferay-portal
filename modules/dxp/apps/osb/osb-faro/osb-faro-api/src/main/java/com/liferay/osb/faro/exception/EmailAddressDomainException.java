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

import java.util.Collection;
import java.util.Collections;

/**
 * @author André Miranda
 */
public class EmailAddressDomainException extends RuntimeException {

	public EmailAddressDomainException(String message) {
		super(message);

		_invalidEmailAddressDomains = Collections.emptyList();
	}

	public EmailAddressDomainException(
		String message, Collection<String> invalidEmailAddressDomains) {

		super(message);

		_invalidEmailAddressDomains = invalidEmailAddressDomains;
	}

	public Collection<String> getInvalidEmailAddressDomains() {
		return _invalidEmailAddressDomains;
	}

	private final Collection<String> _invalidEmailAddressDomains;

}