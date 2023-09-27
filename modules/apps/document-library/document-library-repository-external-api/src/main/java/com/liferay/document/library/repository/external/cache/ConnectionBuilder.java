/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.repository.external.cache;

import com.liferay.portal.kernel.repository.RepositoryException;

/**
 * @author Iván Zaera
 */
public interface ConnectionBuilder<T> {

	public T buildConnection() throws RepositoryException;

}