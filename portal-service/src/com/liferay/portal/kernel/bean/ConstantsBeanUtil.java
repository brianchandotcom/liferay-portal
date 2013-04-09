/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.bean;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

/**
 * @author Shuyang Zhou
 */
public class ConstantsBeanUtil {

	public static Object toConstantsBean(Class<?> constantsClass) {
		return _constantsBean.toConstantsBean(constantsClass);
	}

	public void setConstantsBean(ConstantsBean constantsBean) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_constantsBean = constantsBean;
	}

	private static ConstantsBean _constantsBean;

}