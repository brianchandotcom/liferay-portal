/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet;

import com.liferay.portal.kernel.util.ResourceBundleThreadLocal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author Shuyang Zhou
 */
public class NullSafeResourceBundleWrapper extends ResourceBundle {

	public NullSafeResourceBundleWrapper(ResourceBundle resourceBundle) {
		if (resourceBundle == null) {
			throw new NullPointerException();
		}

		setParent(resourceBundle);
	}

	@Override
	public Enumeration<String> getKeys() {
		return parent.getKeys();
	}

	@Override
	protected Object handleGetObject(String key) {
		if (key == null) {
			throw new NullPointerException();
		}

		try {
			return parent.getString(key);
		}
		catch (MissingResourceException mre) {

			if (ResourceBundleThreadLocal.isReplace()) {
				return ResourceBundleUtil.NULL_VALUE;
			}
			else {
				throw mre;
			}
		}
	}

}