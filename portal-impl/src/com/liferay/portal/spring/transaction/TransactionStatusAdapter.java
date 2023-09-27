/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.spring.transaction;

import com.liferay.petra.reflect.ReflectionUtil;

import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionStatus;

/**
 * @author Shuyang Zhou
 */
public class TransactionStatusAdapter
	extends DefaultTransactionStatus
	implements com.liferay.portal.kernel.transaction.TransactionStatus {

	public TransactionStatusAdapter(TransactionStatus transactionStatus) {
		super(null, false, false, false, false, null);

		_transactionStatus = transactionStatus;
	}

	@Override
	public Object createSavepoint() throws TransactionException {
		return _transactionStatus.createSavepoint();
	}

	@Override
	public void flush() {
		_transactionStatus.flush();
	}

	public TransactionStatus getTransactionStatus() {
		return _transactionStatus;
	}

	@Override
	public boolean hasSavepoint() {
		return _transactionStatus.hasSavepoint();
	}

	@Override
	public boolean isCompleted() {
		return _transactionStatus.isCompleted();
	}

	@Override
	public boolean isNewTransaction() {
		return _transactionStatus.isNewTransaction();
	}

	@Override
	public boolean isRollbackOnly() {
		return _transactionStatus.isRollbackOnly();
	}

	@Override
	public void releaseSavepoint(Object savepoint) throws TransactionException {
		_transactionStatus.releaseSavepoint(savepoint);
	}

	void reportLifecycleListenerThrowables(Throwable throwable) {
		if (_lifecycleListenerThrowable != null) {
			if (throwable == null) {
				ReflectionUtil.throwException(_lifecycleListenerThrowable);
			}
			else {
				throwable.addSuppressed(_lifecycleListenerThrowable);
			}
		}
	}

	@Override
	public void rollbackToSavepoint(Object savepoint)
		throws TransactionException {

		_transactionStatus.rollbackToSavepoint(savepoint);
	}

	@Override
	public void setRollbackOnly() {
		_transactionStatus.setRollbackOnly();
	}

	@Override
	public void suppressLifecycleListenerThrowable(Throwable throwable) {
		if (_lifecycleListenerThrowable == null) {
			_lifecycleListenerThrowable = throwable;
		}
		else {
			_lifecycleListenerThrowable.addSuppressed(throwable);
		}
	}

	private Throwable _lifecycleListenerThrowable;
	private final TransactionStatus _transactionStatus;

}