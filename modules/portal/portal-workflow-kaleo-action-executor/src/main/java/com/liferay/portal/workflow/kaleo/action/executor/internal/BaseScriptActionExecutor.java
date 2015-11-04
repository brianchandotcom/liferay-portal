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

package com.liferay.portal.workflow.kaleo.action.executor.internal;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.workflow.kaleo.action.executor.ActionExecutor;
import com.liferay.portal.workflow.kaleo.model.KaleoAction;
import com.liferay.portal.workflow.kaleo.runtime.util.ClassLoaderUtil;

/**
 * @author Marcellus Tavares
 */
public abstract class BaseScriptActionExecutor implements ActionExecutor {

	public ClassLoader[] getScriptClassLoaders(KaleoAction kaleoAction) {
		String[] scriptRequiredContexts = StringUtil.split(
			kaleoAction.getScriptRequiredContexts());

		return ClassLoaderUtil.getClassLoaders(scriptRequiredContexts);
	}

}