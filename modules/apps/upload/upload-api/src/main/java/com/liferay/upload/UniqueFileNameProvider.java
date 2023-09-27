/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.upload;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.function.Predicate;

/**
 * @author Alejandro Tardín
 */
public interface UniqueFileNameProvider {

	public String provide(String fileName, Predicate<String> predicate)
		throws PortalException;

}