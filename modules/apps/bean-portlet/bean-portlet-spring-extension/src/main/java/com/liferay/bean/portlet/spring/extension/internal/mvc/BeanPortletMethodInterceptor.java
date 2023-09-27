/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bean.portlet.spring.extension.internal.mvc;

import com.liferay.bean.portlet.extension.BeanPortletMethod;
import com.liferay.bean.portlet.extension.BeanPortletMethodWrapper;

/**
 * @author Neil Griffin
 */
public class BeanPortletMethodInterceptor extends BeanPortletMethodWrapper {

	public BeanPortletMethodInterceptor(
		BeanPortletMethod beanPortletMethod, boolean controller) {

		super(beanPortletMethod);

		_controller = controller;
	}

	public boolean isController() {
		return _controller;
	}

	private final boolean _controller;

}