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

package com.liferay.portal.kernel.portlet.bridges.mvc;

import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;

import java.util.concurrent.Callable;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

/**
 * @author Bruno Basto
 */
public abstract class BaseTransactionalMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		try {
			Callable<Void> callable = new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					doTransactionalCommand(actionRequest, actionResponse);

					return null;
				}

			};

			TransactionInvokerUtil.invoke(getTransactionConfig(), callable);
		}
		catch (Throwable throwable) {
			if (throwable instanceof PortletException) {
				throw (PortletException)throwable;
			}

			throw new PortletException(throwable);
		}
	}

	protected abstract void doTransactionalCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception;

	protected TransactionConfig getTransactionConfig() {
		return _transactionConfig;
	}

	private static final TransactionConfig _transactionConfig;

	static {
		TransactionConfig.Builder builder = new TransactionConfig.Builder();

		builder.setRollbackForClasses(Exception.class);

		_transactionConfig = builder.build();
	}

}