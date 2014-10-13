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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class BasePortalLifecycle implements PortalLifecycle {

	@Override
	public void portalDestroy() {
		if (!_calledPortalDestroy) {
			PortalLifecycleUtil.removeDestroy(this);

			try {
				doPortalDestroy();
			}
			catch (Exception e) {
				_log.error(e, e);
			}

			_calledPortalDestroy = true;
		}
	}

	@Override
	public void portalInit() {
		try {
			doPortalInit();
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new IllegalStateException("Unable to initialize portal", e);
		}
	}

	public void registerPortalLifecycle() {
		PortalLifecycleUtil.register(this);
	}

	public void registerPortalLifecycle(int method) {
		PortalLifecycleUtil.register(this, method);
	}

	protected abstract void doPortalDestroy() throws Exception;

	protected abstract void doPortalInit() throws Exception;

	private static final Log _log = LogFactoryUtil.getLog(
		BasePortalLifecycle.class);

	private boolean _calledPortalDestroy;

}