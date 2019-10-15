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

package com.liferay.vldap.server.internal.portal.security.samba;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.io.UnsupportedEncodingException;

import java.security.MessageDigest;

import jcifs.util.DES;
import jcifs.util.MD4;

/**
 * @author Minhchau Dang
 */
public class PortalSambaUtil {

	public static void checkAttributes() {
		_checkAttribute("sambaLMPassword");
		_checkAttribute("sambaNTPassword");
	}

	public static String getSambaLMPassword(User user) {
		ExpandoBridge expandoBridge = user.getExpandoBridge();

		return (String)expandoBridge.getAttribute("sambaLMPassword", false);
	}

	public static String getSambaNTPassword(User user) {
		ExpandoBridge expandoBridge = user.getExpandoBridge();

		return (String)expandoBridge.getAttribute("sambaNTPassword", false);
	}

	public static void setSambaLMPassword(User user, String password)
		throws UnsupportedEncodingException {

		ExpandoBridge expandoBridge = user.getExpandoBridge();

		String sambaLMPassword = _encryptSambaLMPassword(password);

		expandoBridge.setAttribute("sambaLMPassword", sambaLMPassword, false);
	}

	public static void setSambaNTPassword(User user, String password)
		throws UnsupportedEncodingException {

		ExpandoBridge expandoBridge = user.getExpandoBridge();

		String sambaNTPassword = _encryptSambaNTPassword(password);

		expandoBridge.setAttribute("sambaNTPassword", sambaNTPassword, false);
	}

	private static void _checkAttribute(String attributeName) {
		long[] companyIds = PortalUtil.getCompanyIds();

		for (long companyId : companyIds) {
			ExpandoBridge expandoBridge =
				ExpandoBridgeFactoryUtil.getExpandoBridge(
					companyId, User.class.getName());

			if (!expandoBridge.hasAttribute(attributeName)) {
				try {
					expandoBridge.addAttribute(attributeName, false);
				}
				catch (PortalException pe) {
					if (_log.isWarnEnabled()) {
						_log.warn(pe, pe);
					}
				}
			}

			UnicodeProperties properties = expandoBridge.getAttributeProperties(
				attributeName);

			properties.put(
				ExpandoColumnConstants.PROPERTY_HIDDEN, StringPool.TRUE);

			expandoBridge.setAttributeProperties(
				attributeName, properties, false);
		}
	}

	private static String _encryptSambaLMPassword(String password)
		throws UnsupportedEncodingException {

		password = StringUtil.toUpperCase(password);

		byte[] passwordBytes = password.getBytes("US-ASCII");

		byte[][] encryptionKeys = new byte[2][7];

		System.arraycopy(
			passwordBytes, 0, encryptionKeys[0], 0,
			Math.min(7, passwordBytes.length));

		if (passwordBytes.length > 7) {
			System.arraycopy(
				passwordBytes, 7, encryptionKeys[1], 0,
				Math.min(7, passwordBytes.length - 7));
		}

		byte[][] encryptedValues = new byte[2][8];

		DES des1 = new DES(encryptionKeys[0]);

		des1.encrypt(_SAMBA_LM_CONSTANT, encryptedValues[0]);

		DES des2 = new DES(encryptionKeys[1]);

		des2.encrypt(_SAMBA_LM_CONSTANT, encryptedValues[1]);

		byte[] sambaLMPasswordBytes = new byte[16];

		System.arraycopy(encryptedValues[0], 0, sambaLMPasswordBytes, 0, 8);
		System.arraycopy(encryptedValues[1], 0, sambaLMPasswordBytes, 8, 8);

		String sambaLMPassword = StringUtil.bytesToHexString(
			sambaLMPasswordBytes);

		sambaLMPassword = StringUtil.toUpperCase(sambaLMPassword);

		return sambaLMPassword;
	}

	private static String _encryptSambaNTPassword(String password)
		throws UnsupportedEncodingException {

		byte[] passwordBytes = password.getBytes("UTF-16LE");

		MessageDigest messageDigest = new MD4();

		byte[] sambaNTPasswordBytes = messageDigest.digest(passwordBytes);

		String sambaNTPassword = StringUtil.bytesToHexString(
			sambaNTPasswordBytes);

		sambaNTPassword = StringUtil.toUpperCase(sambaNTPassword);

		return sambaNTPassword;
	}

	// KGS!@#$%

	private static final byte[] _SAMBA_LM_CONSTANT = {
		(byte)0x4b, (byte)0x47, (byte)0x53, (byte)0x21, (byte)0x40, (byte)0x23,
		(byte)0x24, (byte)0x25
	};

	private static final Log _log = LogFactoryUtil.getLog(
		PortalSambaUtil.class);

}