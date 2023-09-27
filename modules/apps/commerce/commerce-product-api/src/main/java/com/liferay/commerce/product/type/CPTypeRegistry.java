/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type;

import aQute.bnd.annotation.ProviderType;

import java.util.List;
import java.util.Set;

/**
 * @author Marco Leo
 */
@ProviderType
public interface CPTypeRegistry {

	public CPType getCPType(String name);

	public Set<String> getCPTypeNames();

	public List<CPType> getCPTypes();

}