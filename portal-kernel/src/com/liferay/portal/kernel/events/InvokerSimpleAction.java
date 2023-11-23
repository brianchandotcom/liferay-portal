/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.events;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.lang.ThreadContextClassLoaderUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class InvokerSimpleAction extends SimpleAction {

	public InvokerSimpleAction(SimpleAction simpleAction) {
		this(simpleAction, Thread.currentThread().getContextClassLoader());
	}

	public InvokerSimpleAction(
		SimpleAction simpleAction, ClassLoader classLoader) {

		_simpleAction = simpleAction;
		_classLoader = classLoader;
	}

	@Override
	public void run(String[] ids) throws ActionException {
		try (SafeCloseable safeCloseable = ThreadContextClassLoaderUtil.swap(
				_classLoader)) {

			_simpleAction.run(ids);
		}
	}

	private final ClassLoader _classLoader;
	private final SimpleAction _simpleAction;

}