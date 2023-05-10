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

package com.liferay.portal.aop.internal;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Preston Crary
 */
public class AopServiceResolver {

	public synchronized void addAopServiceRegistrar(
		AopServiceRegistrar aopServiceRegistrar) {

		_aopServiceRegistrars.add(aopServiceRegistrar);

		if (!_transactionExecutorHolders.isEmpty()) {
			TransactionExecutorHolder topRankingTransactionExecutorHolder =
				_transactionExecutorHolders.get(0);

			aopServiceRegistrar.register(
				topRankingTransactionExecutorHolder.getTransactionExecutor());
		}
	}

	public synchronized void addTransactionExecutorHolder(
		TransactionExecutorHolder transactionExecutorHolder) {

		int index = Collections.binarySearch(
			_transactionExecutorHolders, transactionExecutorHolder,
			Comparator.reverseOrder());

		if (index >= 0) {
			return;
		}

		index = -index - 1;

		_transactionExecutorHolders.add(index, transactionExecutorHolder);

		if (index > 0) {
			return;
		}

		for (AopServiceRegistrar aopServiceRegistrar : _aopServiceRegistrars) {
			aopServiceRegistrar.unregister();
		}

		for (AopServiceRegistrar aopServiceRegistrar : _aopServiceRegistrars) {
			aopServiceRegistrar.register(
				transactionExecutorHolder.getTransactionExecutor());
		}
	}

	public synchronized void removeAopServiceRegistrar(
		AopServiceRegistrar aopServiceRegistrar) {

		_aopServiceRegistrars.remove(aopServiceRegistrar);
	}

	public synchronized void removeTransactionExecutorHolder(
		TransactionExecutorHolder transactionExecutorHolder) {

		int index = _transactionExecutorHolders.indexOf(
			transactionExecutorHolder);

		if (index < 0) {
			return;
		}

		_transactionExecutorHolders.remove(index);

		if (index > 0) {
			return;
		}

		for (AopServiceRegistrar aopServiceRegistrar : _aopServiceRegistrars) {
			aopServiceRegistrar.unregister();
		}

		if (_transactionExecutorHolders.isEmpty()) {
			return;
		}

		TransactionExecutorHolder topRankingTransactionExecutorHolder =
			_transactionExecutorHolders.get(0);

		for (AopServiceRegistrar aopServiceRegistrar : _aopServiceRegistrars) {
			aopServiceRegistrar.register(
				topRankingTransactionExecutorHolder.getTransactionExecutor());
		}
	}

	private final Queue<AopServiceRegistrar> _aopServiceRegistrars =
		new ConcurrentLinkedQueue<>();
	private final List<TransactionExecutorHolder> _transactionExecutorHolders =
		new CopyOnWriteArrayList<>();

}