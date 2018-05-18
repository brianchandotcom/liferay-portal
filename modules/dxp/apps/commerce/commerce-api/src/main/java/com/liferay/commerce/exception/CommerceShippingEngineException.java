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

package com.liferay.commerce.exception;

import aQute.bnd.annotation.ProviderType;

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
@ProviderType
public class CommerceShippingEngineException extends PortalException {

	public CommerceShippingEngineException() {
	}

	public CommerceShippingEngineException(String msg) {
		super(msg);
	}

	public CommerceShippingEngineException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public CommerceShippingEngineException(Throwable cause) {
		super(cause);
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