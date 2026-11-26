/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.fips.internal.osgi.commands;

import com.liferay.osgi.util.osgi.commands.OSGiCommands;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.security.fips.FIPSModeValidationException;
import com.liferay.portal.kernel.security.fips.FIPSModeValidator;

import org.apache.felix.service.command.Descriptor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jorge García Jiménez
 */
@Component(
	property = {"osgi.command.function=check", "osgi.command.scope=fips"},
	service = OSGiCommands.class
)
public class FIPSOSGiCommands implements OSGiCommands {

	@Descriptor("List FIPS boot blockers")
	public String check() {
		StringBundler sb = new StringBundler(4);

		try {
			FIPSModeValidator.validatePortalConfiguration();

			sb.append("No FIPS boot blockers were found");
		}
		catch (FIPSModeValidationException fipsModeValidationException) {
			sb.append(fipsModeValidationException.getMessage());
		}

		sb.append("\nOnly the FIPS boot checks the JVM, including the ");
		sb.append("security providers, the SecureRandom provider, and the ");
		sb.append("TLS and certificate revocation properties");

		return sb.toString();
	}

}