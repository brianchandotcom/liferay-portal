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

package com.liferay.portal.layoutconfiguration.util;

import com.liferay.portal.kernel.util.ClassLoaderUtil;
import com.liferay.portal.kernel.util.DefaultThreadLocalBinder;
import com.liferay.portal.kernel.util.ServerDetector;

/**
 * @author Shuyang Zhou
 */
public class JettyThreadLocalBinder extends DefaultThreadLocalBinder {

	@Override
	public void afterPropertiesSet() throws Exception {
		if (!ServerDetector.isJetty()) {
			return;
		}

		ClassLoader classLoader = ClassLoaderUtil.getContextClassLoader();

		classLoader = classLoader.getParent();

		setClassLoader(classLoader);

		super.afterPropertiesSet();

		ParallelRenderThreadLocalBinderUtil.setThreadLocalBinder(this);
	}

}