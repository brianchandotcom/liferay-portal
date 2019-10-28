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

package com.liferay.vldap.server.internal.util;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;

import org.apache.directory.api.ldap.model.exception.LdapInvalidDnException;
import org.apache.directory.api.ldap.model.name.Dn;
import org.apache.directory.api.ldap.model.name.Rdn;

/**
 * @author Jonathan Potter
 * @author Brian Wing Shun Chan
 */
public class LdapUtil {

	public static String buildName(
		String commonName, String top, Company company,
		String... organizationUnits) {

		StringBundler sb = new StringBundler(
			7 + (organizationUnits.length * 3));

		if (commonName != null) {
			sb.append("cn=");
			sb.append(commonName);
			sb.append(",");
		}

		for (int i = organizationUnits.length - 1; i >= 0; --i) {
			String organizationUnit = organizationUnits[i];

			if (!organizationUnit.contains("=")) {
				sb.append("ou=");
			}

			sb.append(escape(organizationUnit));
			sb.append(",");
		}

		sb.append("ou=");
		sb.append(escape(company.getWebId()));
		sb.append(",o=");
		sb.append(escape(top));

		return sb.toString();
	}

	public static String escape(String name) {
		int pos = name.indexOf(CharPool.EQUAL);

		String suffix = name.substring(pos + 1);

		char[] charArray = suffix.toCharArray();

		StringBundler sb = new StringBundler();

		for (char c : charArray) {
			for (char escapeChar : _ESCAPE_CHARS) {
				if (c == escapeChar) {
					sb.append(CharPool.BACK_SLASH);

					break;
				}
			}

			sb.append(c);
		}

		String escapedSuffix = sb.toString();

		return name.substring(0, pos + 1) + escapedSuffix;
	}

	public static String getRdnType(Dn dn, int index) {
		if ((index < dn.size()) && (index >= 0)) {
			int rdnIndex = dn.size() - 1 - index;

			Rdn rdn = dn.getRdn(rdnIndex);

			return rdn.getNormType();
		}

		return null;
	}

	public static String getRdnValue(Dn dn, int index) {
		try {
			if ((index < dn.size()) && (index >= 0)) {
				int rdnIndex = dn.size() - 1 - index;

				Rdn rdn = dn.getRdn(rdnIndex);

				Object valueObj = rdn.getValue(rdn.getNormType());

				return valueObj.toString();
			}
		}
		catch (LdapInvalidDnException ldapInvalidDnException) {
			if (_log.isDebugEnabled()) {
				_log.debug(ldapInvalidDnException, ldapInvalidDnException);
			}
		}

		return null;
	}

	/**
	 * http://www.rlmueller.net/CharactersEscaped.htm
	 */
	private static final char[] _ESCAPE_CHARS = {
		',', '\\', '#', '+', '<', '>', ';', '"', '='
	};

	private static final Log _log = LogFactoryUtil.getLog(LdapUtil.class);

}