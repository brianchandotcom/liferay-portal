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

package com.liferay.lcs.util;

import java.math.BigInteger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides helper methods for Liferay patches. This class uses standard Liferay
 * patch naming conventions to detect a patch's human name or file name.
 *
 * @author  Ivica Cardic
 * @version 2.1.1
 * @since   LCS 0.1
 */
public class PatchUtil {

	/**
	 * Returns the patch's file name. The <code>patchName</code> is used as a
	 * base to build the patch's file name.
	 *
	 * @param  patchName the patch's name
	 * @return the patch's file name
	 * @since  LCS 0.1
	 */
	public static String getPatchFileName(String patchName) {
		return _LIFERAY_PREFIX.concat(patchName).concat(".zip");
	}

	/**
	 * Returns the patch's name in human readable form. For example, all dashes
	 * are replaced with spaces, and words are capitalized.
	 *
	 * @param  patchName the patch's name
	 * @return the patch's name in human readable form
	 * @since  LCS 0.1
	 */
	public static String getPatchHumanName(String patchName) {
		StringBuilder sb = new StringBuilder();

		String patchId = getPatchId(patchName);

		patchId = patchId.replaceFirst("-\\d{4}$", "");

		String[] parts = patchId.split("-");

		for (int i = 0; i < parts.length; i++) {
			String part = parts[i];

			int partLength = part.length();

			if (partLength > 0) {
				sb.append(Character.toUpperCase(part.charAt(0)));
			}

			if (partLength > 1) {
				sb.append(part.substring(1, partLength));
			}

			if (i != (parts.length - 1)) {
				sb.append(" ");
			}
		}

		return sb.toString();
	}

	/**
	 * Returns the patch ID, which is extracted from the patch name.
	 *
	 * @param  patchName the patch's name
	 * @return the patch ID
	 * @since  LCS 0.1
	 */
	public static String getPatchId(String patchName) {
		return patchName.replace(_FIX_PACK_PREFIX, "");
	}

	public static String getPatchMD5Sum(byte[] bytes) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");

			messageDigest.reset();

			messageDigest.update(bytes);

			byte[] digest = messageDigest.digest();

			BigInteger bigInteger = new BigInteger(1, digest);

			return String.format("%032x", bigInteger);
		}
		catch (NoSuchAlgorithmException nsae) {
			_log.error(
				"Skipping MD5 sum check because of missing MD5 sum algorithm");

			return null;
		}
	}

	/**
	 * Returns the patch's name, which is extracted from the
	 * <code>patchFileName</code>.
	 *
	 * @param  patchFileName the patch's file name
	 * @return the patch's name
	 * @since  LCS 0.1
	 */
	public static String getPatchName(String patchFileName) {
		String patchName = patchFileName.replace(_LIFERAY_PREFIX, "");

		patchName = patchName.replace(".zip", "");

		return patchName;
	}

	private static final String _FIX_PACK_PREFIX = "fix-pack-";

	private static final String _LIFERAY_PREFIX = "liferay-";

	private static final Logger _log = LoggerFactory.getLogger(PatchUtil.class);

}