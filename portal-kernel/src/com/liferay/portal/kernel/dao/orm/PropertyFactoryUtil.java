/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.dao.orm;

/**
 * @author Brian Wing Shun Chan
 */
public class PropertyFactoryUtil {

	public static Property forName(String propertyName) {
		return _projectionFactory.forName(propertyName);
	}

	public static PropertyFactory getPropertyFactory() {
		return _projectionFactory;
	}

	public void setPropertyFactory(PropertyFactory projectionFactory) {
		_projectionFactory = projectionFactory;
	}

	private static PropertyFactory _projectionFactory;

}