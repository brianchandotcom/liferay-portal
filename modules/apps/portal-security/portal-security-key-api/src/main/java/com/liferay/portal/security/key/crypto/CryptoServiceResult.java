/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.key.crypto;

import com.liferay.portal.security.key.ServiceIndicator;

/**
 * Pairs the result of a {@link CryptoManager} operation with the {@link
 * ServiceIndicator} the provider reported for it, so the approved or
 * nonapproved nature of the operation travels with its output instead of being
 * discarded at the manager boundary.
 *
 * @author Christopher Kian
 */
public class CryptoServiceResult<T> {

	public CryptoServiceResult(ServiceIndicator serviceIndicator, T value) {
		_serviceIndicator = serviceIndicator;
		_value = value;
	}

	public ServiceIndicator getServiceIndicator() {
		return _serviceIndicator;
	}

	public T getValue() {
		return _value;
	}

	private final ServiceIndicator _serviceIndicator;
	private final T _value;

}