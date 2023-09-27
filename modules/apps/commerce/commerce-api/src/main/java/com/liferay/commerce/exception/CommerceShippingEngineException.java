/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.exception;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceShippingEngineException extends PortalException {

	public CommerceShippingEngineException() {
	}

	public CommerceShippingEngineException(String msg) {
		super(msg);
	}

	public CommerceShippingEngineException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public CommerceShippingEngineException(Throwable throwable) {
		super(throwable);
	}

	public static class MustSetCurrency
		extends CommerceShippingEngineException {

		public MustSetCurrency(String code) {
			super("Unable to get currency with code \"" + code + "\"");

			_code = code;
		}

		public String getCode() {
			return _code;
		}

		private final String _code;

	}

	public static class MustSetMeasurementUnit
		extends CommerceShippingEngineException {

		public MustSetMeasurementUnit(String[] keys) {
			super(
				"At least one measurement unit with the following keys must " +
					"be set: " +
						StringUtil.merge(keys, StringPool.COMMA_AND_SPACE));

			_keys = Collections.unmodifiableList(Arrays.asList(keys));
		}

		public List<String> getKeys() {
			return _keys;
		}

		private final List<String> _keys;

	}

	public static class MustSetPrimaryCurrency
		extends CommerceShippingEngineException {

		public MustSetPrimaryCurrency() {
			super("Unable to get primary currency");
		}

	}

	public static class MustSetShippingAddress
		extends CommerceShippingEngineException {

		public MustSetShippingAddress() {
			super("Unable to get shipping address");
		}

	}

	public static class MustSetShippingOriginLocator
		extends CommerceShippingEngineException {

		public MustSetShippingOriginLocator(
			String commerceShippingOriginLocatorKey) {

			super(
				"Unable to get shipping origin locator \"" +
					commerceShippingOriginLocatorKey + "\"");
		}

	}

	public static class ServerError extends CommerceShippingEngineException {

		public ServerError(List<KeyValuePair> errorKVPs) {
			super("Unable to get reply from server");

			_errorKVPs = errorKVPs;
		}

		public List<KeyValuePair> getErrorKVPs() {
			return _errorKVPs;
		}

		private final List<KeyValuePair> _errorKVPs;

	}

}