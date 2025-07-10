/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.action.executor;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.concurrent.Callable;

/**
 * Provides a base implementation for ObjectActionExecutors that need to perform logic
 * after a transaction is committed.
 *
 * Subclasses can register their callback using the provided utility method.
 *
 * This avoids duplicating TransactionCommitCallbackUtil logic in each executor.
 *
 * @author Aquiles Duarte
 */
public abstract class BaseObjectActionExecutor implements ObjectActionExecutor {

	@Override
	public abstract void execute(
			long companyId, long objectActionId,
			UnicodeProperties parametersUnicodeProperties,
			JSONObject payloadJSONObject, long userId)
		throws Exception;

	@Override
	public abstract String getKey();

	protected void registerTransactionCommitCallback(Callable<Void> callback)
		throws Exception {

		TransactionCommitCallbackUtil.registerCallback(callback);
	}

}