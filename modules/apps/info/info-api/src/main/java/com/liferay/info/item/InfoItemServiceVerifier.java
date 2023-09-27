/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.item;

import com.liferay.info.exception.CapabilityVerificationException;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Jorge Ferrer
 */
@ProviderType
public interface InfoItemServiceVerifier {

	public List<Class<?>> getMissingServiceClasses(
			Class<?>[] requiredInfoItemServiceClasses, String itemClassName)
		throws CapabilityVerificationException;

}