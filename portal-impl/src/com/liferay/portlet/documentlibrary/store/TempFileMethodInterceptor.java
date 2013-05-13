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

package com.liferay.portlet.documentlibrary.store;

import com.liferay.portal.kernel.memory.DeleteFileFinalizeAction;
import com.liferay.portal.kernel.memory.FinalizeManager;
import com.liferay.portal.kernel.util.FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Shuyang Zhou
 */
public class TempFileMethodInterceptor implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		Object result = methodInvocation.proceed();

		if (result instanceof InputStream) {
			InputStream inputStream = (InputStream)result;

			File tempFile = FileUtil.createTempFile(inputStream);

			result = new FileInputStream(tempFile);

			FinalizeManager.register(
				result,
				new DeleteFileFinalizeAction(tempFile.getAbsolutePath()));
		}

		return result;
	}

}