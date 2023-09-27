/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.template.freemarker.internal;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

import freemarker.ext.beans.BeansWrapper;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.util.List;

/**
 * @author Raymond Augé
 */
public class LiferayObjectConstructor implements TemplateMethodModelEx {

	public LiferayObjectConstructor(BeansWrapper beansWrapper) {
		_beansWrapper = beansWrapper;
	}

	@Override
	public Object exec(@SuppressWarnings("rawtypes") List arguments)
		throws TemplateModelException {

		if (arguments.isEmpty()) {
			throw new TemplateModelException(
				"This method must have at least one argument as the name of " +
					"the class to instantiate");
		}

		String className = String.valueOf(arguments.get(0));

		Thread currentThread = Thread.currentThread();

		Class<?> clazz = null;

		try {
			clazz = Class.forName(
				className, true, currentThread.getContextClassLoader());
		}
		catch (Exception exception1) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception1);
			}

			try {
				clazz = Class.forName(
					className, true, PortalClassLoaderUtil.getClassLoader());
			}
			catch (Exception exception2) {
				throw new TemplateModelException(exception2.getMessage());
			}
		}

		Object object = _beansWrapper.newInstance(
			clazz, arguments.subList(1, arguments.size()));

		return _beansWrapper.wrap(object);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LiferayObjectConstructor.class);

	private final BeansWrapper _beansWrapper;

}