/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.internal.security.access.control;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InetAddressUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Mariano Álvaro Sáiz
 * @author Carlos Sierra
 */
public final class AllowedIPAddressesValidatorFactory {

	public static AllowedIPAddressesValidator create(
		String ipAddressAndNetmaskString) {

		if (Validator.isNull(ipAddressAndNetmaskString)) {
			return _ALLOWED_IP_ADDRESSES_VALIDATOR;
		}

		String[] ipAddressAndNetmask = StringUtil.split(
			ipAddressAndNetmaskString, StringPool.SLASH);

		try {
			if (Validator.isIPv4Address(ipAddressAndNetmask[0])) {
				return new V4AllowedIPAddressesValidator(
					InetAddressUtil.getInetAddressByName(
						ipAddressAndNetmask[0]),
					ipAddressAndNetmask);
			}
			else if (Validator.isIPv6Address(ipAddressAndNetmask[0])) {
				return new V6AllowedIPAddressesValidator(
					InetAddressUtil.getInetAddressByName(
						ipAddressAndNetmask[0]),
					ipAddressAndNetmask);
			}
			else {
				return _ALLOWED_IP_ADDRESSES_VALIDATOR;
			}
		}
		catch (Exception exception) {
			_log.error("Invalid configured address: ", exception);

			return _ALLOWED_IP_ADDRESSES_VALIDATOR;
		}
	}

	private static final AllowedIPAddressesValidator
		_ALLOWED_IP_ADDRESSES_VALIDATOR = ipAddress -> false;

	private static final int[] _BYTE = {
		0b00000000, 0b10000000, 0b11000000, 0b11100000, 0b11110000, 0b11111000,
		0b11111100, 0b11111110, 0b11111111
	};

	private static final Log _log = LogFactoryUtil.getLog(
		AllowedIPAddressesValidatorFactory.class);

	private abstract static class BaseAllowedIPAddressesValidator
		implements AllowedIPAddressesValidator {

		@Override
		public boolean isAllowedIPAddress(String ipAddress) {
			InetAddress inetAddress = null;

			try {
				inetAddress = InetAddressUtil.getInetAddressByName(ipAddress);
			}
			catch (UnknownHostException unknownHostException) {
				return false;
			}

			byte[] inetAddressBytes = inetAddress.getAddress();

			if (!isSameProtocol(inetAddressBytes)) {
				return false;
			}

			if (_netmask == null) {
				return _allowedIpAddress.equals(inetAddress);
			}

			for (int i = 0; i < _netmask.length; i++) {
				if ((inetAddressBytes[i] & _netmask[i]) !=
						(_allowedIpAddressBytes[i] & _netmask[i])) {

					return false;
				}
			}

			return true;
		}

		protected abstract byte[] getEmptyNetmask();

		protected boolean isSameProtocol(byte[] ipAddressBytes) {
			if (_allowedIpAddressBytes.length == ipAddressBytes.length) {
				return true;
			}

			return false;
		}

		private BaseAllowedIPAddressesValidator(
				InetAddress allowedIpAddress, String[] ipAddressAndNetmask)
			throws UnknownHostException {

			_allowedIpAddress = allowedIpAddress;

			_allowedIpAddressBytes = _allowedIpAddress.getAddress();

			if (_hasNetmask(ipAddressAndNetmask)) {
				String netmask = GetterUtil.getString(ipAddressAndNetmask[1]);

				if (Validator.isNumber(netmask)) {
					_netmask = _getNetmaskFromCIDR(netmask);
				}
				else {
					_netmask = _getNetmaskFromDotNotation(netmask);
				}
			}
		}

		private byte[] _getNetmaskFromCIDR(String netmask) {
			int cidr = GetterUtil.getInteger(netmask);

			int netmaskBytes = cidr / 8;

			byte[] bytesNetmask = getEmptyNetmask();

			for (int i = 0; i < netmaskBytes; i++) {
				bytesNetmask[i] = (byte)_BYTE[8];
			}

			int byteOffset = cidr % 8;

			if (netmaskBytes < bytesNetmask.length) {
				bytesNetmask[netmaskBytes] = (byte)_BYTE[byteOffset];
			}

			return bytesNetmask;
		}

		private byte[] _getNetmaskFromDotNotation(String netmask)
			throws UnknownHostException {

			InetAddress inetAddress = InetAddressUtil.getInetAddressByName(
				netmask);

			return inetAddress.getAddress();
		}

		private boolean _hasNetmask(String[] ipAddressAndNetmask) {
			if (ipAddressAndNetmask.length > 1) {
				return true;
			}

			return false;
		}

		private final InetAddress _allowedIpAddress;
		private final byte[] _allowedIpAddressBytes;
		private byte[] _netmask;

	}

	private static class V4AllowedIPAddressesValidator
		extends BaseAllowedIPAddressesValidator {

		@Override
		public byte[] getEmptyNetmask() {
			return new byte[4];
		}

		private V4AllowedIPAddressesValidator(
				InetAddress inetAddress, String[] ipAddressAndNetmask)
			throws UnknownHostException {

			super(inetAddress, ipAddressAndNetmask);
		}

	}

	private static class V6AllowedIPAddressesValidator
		extends BaseAllowedIPAddressesValidator {

		@Override
		public byte[] getEmptyNetmask() {
			return new byte[16];
		}

		private V6AllowedIPAddressesValidator(
				InetAddress inetAddress, String[] ipAddressAndNetmask)
			throws UnknownHostException {

			super(inetAddress, ipAddressAndNetmask);
		}

	}

}