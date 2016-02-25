/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.management.jmx;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.management.ManageAction;

import java.util.concurrent.atomic.AtomicReference;

import javax.management.MBeanServer;

/**
 * @author Shuyang Zhou
 */
public abstract class BaseJMXManageAction<T> implements ManageAction<T> {

	protected MBeanServer getMBeanServer() {
		MBeanServer mBeanServer = _mBeanServerReference.get();

		if (mBeanServer == null) {
			mBeanServer = (MBeanServer)PortalBeanLocatorUtil.locate(
				"mBeanServer");

			_mBeanServerReference.compareAndSet(null, mBeanServer);
		}

		return mBeanServer;
	}

	private static final AtomicReference<MBeanServer> _mBeanServerReference =
		new AtomicReference<>();

}