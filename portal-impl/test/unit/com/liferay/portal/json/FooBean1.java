/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.json;

import com.liferay.portal.kernel.json.JSON;

import java.util.Collection;

/**
 * @author Igor Spasic
 */
public class FooBean1 extends FooBean {

	@JSON
	@Override
	public Collection<Object> getCollection() {
		return super.getCollection();
	}

	@JSON(include = false)
	@Override
	public String getName() {
		return super.getName();
	}

}