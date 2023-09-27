/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dispatch.repository;

import com.liferay.dispatch.repository.exception.DispatchRepositoryException;

/**
 * @author Igor Beslic
 */
public interface DispatchFileValidator {

	public void validateExtension(String fileName)
		throws DispatchRepositoryException;

	public void validateSize(long size) throws DispatchRepositoryException;

}