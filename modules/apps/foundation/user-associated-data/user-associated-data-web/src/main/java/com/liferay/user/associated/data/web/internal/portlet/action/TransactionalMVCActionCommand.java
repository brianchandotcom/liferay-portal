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

package com.liferay.user.associated.data.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.util.ServiceBeanMethodInvocationFactoryUtil;

import java.lang.reflect.Method;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

/**
 * @author William Newbury
 */
public abstract class TransactionalMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		if (_performProcessActionMethod == null) {
			_performProcessActionMethod = getClass().getDeclaredMethod(
				"performProcessAction",
				new Class<?>[] {ActionRequest.class, ActionResponse.class});
		}

		Object object = ServiceBeanMethodInvocationFactoryUtil.proceed(
			this, getClass(), _performProcessActionMethod,
			new Object[] {actionRequest, actionResponse},
			new String[] {"transactionAdvice"});
	}

	protected abstract void performProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception;

	private Method _performProcessActionMethod;

}