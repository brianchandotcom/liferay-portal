/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.crud;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Marco Leo
 * @author Carlos Correa
 */
@ProviderType
public interface VulcanCRUDItemDelegate<T> {

	public T getItem(Long id) throws Exception;

}