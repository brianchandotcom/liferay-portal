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
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Preston Crary
 */
public class AopServiceResolver {

	public synchronized void addAopServiceRegistrar(
		AopServiceRegistrar aopServiceRegistrar) {

		_aopServiceRegistrars.add(aopServiceRegistrar);

		if (!_transactionHandlerHolders.isEmpty()) {
			TransactionHandlerHolder topRankingTransactionHandlerHolder =
				_transactionHandlerHolders.get(0);

			aopServiceRegistrar.register(
				topRankingTransactionHandlerHolder.getTransactionHandler());
		}
	}

	public synchronized void addTransactionHandlerHolder(
		TransactionHandlerHolder transactionHandlerHolder) {

		int index = Collections.binarySearch(
			_transactionHandlerHolders, transactionHandlerHolder,
			Comparator.reverseOrder());

		if (index >= 0) {
			return;
		}

		index = -index - 1;

		_transactionHandlerHolders.add(index, transactionHandlerHolder);

		if (index > 0) {
			return;
		}

		for (AopServiceRegistrar aopServiceRegistrar : _aopServiceRegistrars) {
			aopServiceRegistrar.unregister();
		}

		for (AopServiceRegistrar aopServiceRegistrar : _aopServiceRegistrars) {
			aopServiceRegistrar.register(
				transactionHandlerHolder.getTransactionHandler());
		}
	}

	public synchronized void removeAopServiceRegistrar(
		AopServiceRegistrar aopServiceRegistrar) {

		_aopServiceRegistrars.remove(aopServiceRegistrar);
	}

	public synchronized void removeTransactionHandlerHolder(
		TransactionHandlerHolder transactionHandlerHolder) {

		int index = _transactionHandlerHolders.indexOf(
			transactionHandlerHolder);

		if (index < 0) {
			return;
		}

		_transactionHandlerHolders.remove(index);

		if (index > 0) {
			return;
		}

		for (AopServiceRegistrar aopServiceRegistrar : _aopServiceRegistrars) {
			aopServiceRegistrar.unregister();
		}

		if (_transactionHandlerHolders.isEmpty()) {
			return;
		}

		TransactionHandlerHolder topRankingTransactionHandlerHolder =
			_transactionHandlerHolders.get(0);

		for (AopServiceRegistrar aopServiceRegistrar : _aopServiceRegistrars) {
			aopServiceRegistrar.register(
				topRankingTransactionHandlerHolder.getTransactionHandler());
		}
	}

	private final Set<AopServiceRegistrar> _aopServiceRegistrars =
		Collections.newSetFromMap(new ConcurrentHashMap<>());
	private final List<TransactionHandlerHolder> _transactionHandlerHolders =
		new CopyOnWriteArrayList<>();

}