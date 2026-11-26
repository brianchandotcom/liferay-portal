/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.fips.internal.osgi.commands;

import com.liferay.osgi.util.osgi.commands.OSGiCommands;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.security.fips.FIPSModeValidationException;
import com.liferay.portal.kernel.security.fips.FIPSModeValidationException.ValidationError;
import com.liferay.portal.kernel.security.fips.FIPSModeValidator;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

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

	@Descriptor("List the FIPS boot blockers before switching to FIPS mode.")
	public String check() {
		List<String> lines = new ArrayList<>();

		List<ValidationError> validationErrors = new ArrayList<>(
			FIPSModeValidator.getPortalPropertiesValidationErrors());

		validationErrors.addAll(
			FIPSModeValidator.getKeyEncryptionKeyValidationErrors());

		if (validationErrors.isEmpty()) {
			lines.add("No FIPS boot blockers were found.");
		}
		else {
			FIPSModeValidationException fipsModeValidationException =
				new FIPSModeValidationException();

			fipsModeValidationException.setValidationErrors(validationErrors);

			lines.add(fipsModeValidationException.getMessage());
		}

		lines.add(StringPool.BLANK);
		lines.add(
			"Only the FIPS boot checks the JVM. This includes the security " +
				"providers, the SecureRandom provider, and the TLS and " +
					"certificate revocation properties.");

		return StringUtil.merge(lines, StringPool.NEW_LINE);
	}

}