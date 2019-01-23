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

import com.liferay.portal.kernel.monitoring.ServiceMonitoringControl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Preston Crary
 */
public class AopServiceResolver {

	public AopServiceResolver(
		ServiceMonitoringControl serviceMonitoringControl) {

		_serviceMonitoringControl = serviceMonitoringControl;
	}

	public void addAopServiceRegistrar(
		AopServiceRegistrar aopServiceRegistrar) {

		_aopServiceRegistrars.add(aopServiceRegistrar);

		if (!_transactionExecutorHolders.isEmpty()) {
			TransactionExecutorHolder bestTransactionExecutorHolder =
				_transactionExecutorHolders.get(0);

			aopServiceRegistrar.register(
				bestTransactionExecutorHolder.getTransactionExecutor(),
				_serviceMonitoringControl);
		}
	}

	public void addTransactionExecutorHolder(
		TransactionExecutorHolder transactionExecutorHolder) {

		_transactionExecutorHolders.add(transactionExecutorHolder);

		_transactionExecutorHolders.sort(Comparator.reverseOrder());

		TransactionExecutorHolder bestTransactionExecutorHolder =
			_transactionExecutorHolders.get(0);

		for (AopServiceRegistrar aopServiceRegistrar : _aopServiceRegistrars) {
			aopServiceRegistrar.unregister();

			aopServiceRegistrar.register(
				bestTransactionExecutorHolder.getTransactionExecutor(),
				_serviceMonitoringControl);
		}
	}

	public boolean isEmpty() {
		if (_aopServiceRegistrars.isEmpty() &&
			_transactionExecutorHolders.isEmpty()) {

			return true;
		}

		return false;
	}

	public void removeAopServiceRegistrar(
		AopServiceRegistrar aopServiceRegistrar) {

		_aopServiceRegistrars.remove(aopServiceRegistrar);

		aopServiceRegistrar.unregister();
	}

	public void removeTransactionExecutorHolder(
		TransactionExecutorHolder transactionExecutorHolder) {

		_transactionExecutorHolders.remove(transactionExecutorHolder);

		for (AopServiceRegistrar aopServiceRegistrar : _aopServiceRegistrars) {
			aopServiceRegistrar.unregister();
		}

		if (_transactionExecutorHolders.isEmpty()) {
			return;
		}

		TransactionExecutorHolder bestTransactionExecutorHolder =
			_transactionExecutorHolders.get(0);

		for (AopServiceRegistrar aopServiceRegistrar : _aopServiceRegistrars) {
			aopServiceRegistrar.unregister();

			aopServiceRegistrar.register(
				bestTransactionExecutorHolder.getTransactionExecutor(),
				_serviceMonitoringControl);
		}
	}

	private final Set<AopServiceRegistrar> _aopServiceRegistrars =
		new HashSet<>();
	private final ServiceMonitoringControl _serviceMonitoringControl;
	private final List<TransactionExecutorHolder> _transactionExecutorHolders =
		new ArrayList<>(1);

}