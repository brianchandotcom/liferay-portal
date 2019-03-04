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

package com.liferay.portal.kernel.dao.orm;

import com.liferay.petra.executor.PortalExecutorManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class DefaultActionableDynamicQuery implements ActionableDynamicQuery {

	public static final TransactionConfig REQUIRES_NEW_TRANSACTION_CONFIG;

	static {
		TransactionConfig.Builder builder = new TransactionConfig.Builder();

		builder.setPropagation(Propagation.REQUIRES_NEW);
		builder.setRollbackForClasses(
			PortalException.class, SystemException.class);

		REQUIRES_NEW_TRANSACTION_CONFIG = builder.build();
	}

	@Override
	public AddCriteriaMethod getAddCriteriaMethod() {
		return _addCriteriaMethod;
	}

	@Override
	public AddOrderCriteriaMethod getAddOrderCriteriaMethod() {
		return _addOrderCriteriaMethod;
	}

	@Override
	public PerformActionMethod<?> getPerformActionMethod() {
		return _performActionMethod;
	}

	@Override
	public PerformCountMethod getPerformCountMethod() {
		return _performCountMethod;
	}

	@Override
	public boolean isParallel() {
		return _parallel;
	}

	@Override
	public void performActions() throws PortalException {
		try {
			long previousPrimaryKey = -1;

			while (true) {
				long lastPrimaryKey = doPerformActions(previousPrimaryKey);

				if (lastPrimaryKey < 0) {
					return;
				}

				intervalCompleted(previousPrimaryKey, lastPrimaryKey);

				previousPrimaryKey = lastPrimaryKey;
			}
		}
		finally {
			_offset = 0;

			actionsCompleted();
		}
	}

	@Override
	public long performCount() throws PortalException {
		if (_performCountMethod != null) {
			return _performCountMethod.performCount();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			_modelClass, _classLoader);

		addDefaultCriteria(dynamicQuery);

		addCriteria(dynamicQuery);

		return (Long)executeDynamicQuery(
			_dynamicQueryCountMethod, dynamicQuery, getCountProjection());
	}

	@Override
	public void setAddCriteriaMethod(AddCriteriaMethod addCriteriaMethod) {
		_addCriteriaMethod = addCriteriaMethod;
	}

	@Override
	public void setAddOrderCriteriaMethod(
		AddOrderCriteriaMethod addOrderCriteriaMethod) {

		_addOrderCriteriaMethod = addOrderCriteriaMethod;
	}

	@Override
	public void setBaseLocalService(BaseLocalService baseLocalService) {
		_baseLocalService = baseLocalService;

		Class<?> clazz = _baseLocalService.getClass();

		try {
			_dynamicQueryMethod = clazz.getMethod(
				"dynamicQuery", DynamicQuery.class);
			_dynamicQueryCountMethod = clazz.getMethod(
				"dynamicQueryCount", DynamicQuery.class, Projection.class);
		}
		catch (NoSuchMethodException nsme) {
			throw new SystemException(nsme);
		}
	}

	@Override
	public void setClassLoader(ClassLoader classLoader) {
		_classLoader = classLoader;
	}

	@Override
	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	@Override
	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	@Override
	public void setGroupIdPropertyName(String groupIdPropertyName) {
		_groupIdPropertyName = groupIdPropertyName;
	}

	@Override
	public void setInterval(int interval) {
		_interval = interval;
	}

	@Override
	public void setModelClass(Class<?> modelClass) {
		_modelClass = modelClass;
	}

	@Override
	public void setParallel(boolean parallel) {
		_parallel = parallel;
	}

	@Override
	public void setPerformActionMethod(
		PerformActionMethod<?> performActionMethod) {

		_performActionMethod = performActionMethod;
	}

	@Override
	public void setPerformCountMethod(PerformCountMethod performCountMethod) {
		_performCountMethod = performCountMethod;
	}

	@Override
	public void setPrimaryKeyPropertyName(String primaryKeyPropertyName) {
		_primaryKeyPropertyName = primaryKeyPropertyName;
	}

	@Override
	public void setTransactionConfig(TransactionConfig transactionConfig) {
		_transactionConfig = transactionConfig;
	}

	/**
	 * @throws PortalException
	 */
	protected void actionsCompleted() throws PortalException {
	}

	protected void addCriteria(DynamicQuery dynamicQuery) {
		if (_addCriteriaMethod != null) {
			_addCriteriaMethod.addCriteria(dynamicQuery);
		}
	}

	protected void addDefaultCriteria(DynamicQuery dynamicQuery) {
		if (_companyId > 0) {
			Property property = PropertyFactoryUtil.forName("companyId");

			dynamicQuery.add(property.eq(_companyId));
		}

		if (_groupId > 0) {
			Property property = PropertyFactoryUtil.forName(
				_groupIdPropertyName);

			dynamicQuery.add(property.eq(_groupId));
		}
	}

	protected void addOrderCriteria(DynamicQuery dynamicQuery) {
		if (_addOrderCriteriaMethod == null) {
			dynamicQuery.addOrder(
				OrderFactoryUtil.asc(_primaryKeyPropertyName));
		}
		else {
			_addOrderCriteriaMethod.addOrderCriteria(dynamicQuery);
		}
	}

	protected long doPerformActions(long previousPrimaryKey)
		throws PortalException {

		final DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			_modelClass, _classLoader);

		if (_addOrderCriteriaMethod == null) {
			Property property = PropertyFactoryUtil.forName(
				_primaryKeyPropertyName);

			dynamicQuery.add(property.gt(previousPrimaryKey));

			dynamicQuery.setLimit(0, _interval);
		}
		else {
			dynamicQuery.setLimit(_offset, _interval + _offset);
		}

		addDefaultCriteria(dynamicQuery);

		addCriteria(dynamicQuery);

		addOrderCriteria(dynamicQuery);

		Callable<Long> callable = new Callable<Long>() {

			@Override
			public Long call() throws Exception {
				List<Object> objects = (List<Object>)executeDynamicQuery(
					_dynamicQueryMethod, dynamicQuery);

				_offset += objects.size();

				if (objects.isEmpty()) {
					return -1L;
				}

				if (_parallel) {
					List<Future<Void>> futures = new ArrayList<>(
						objects.size());

					for (final Object object : objects) {
						futures.add(
							_executorService.submit(
								new Callable<Void>() {

									@Override
									public Void call() throws PortalException {
										performAction(object);

										return null;
									}

								}));
					}

					for (Future<Void> future : futures) {
						future.get();
					}
				}
				else {
					for (Object object : objects) {
						performAction(object);
					}
				}

				if (objects.size() < _interval) {
					return -1L;
				}

				BaseModel<?> baseModel = (BaseModel<?>)objects.get(
					objects.size() - 1);

				return (Long)baseModel.getPrimaryKeyObj();
			}

		};

		TransactionConfig transactionConfig = getTransactionConfig();

		try {
			if (transactionConfig == null) {
				return callable.call();
			}

			return TransactionInvokerUtil.invoke(transactionConfig, callable);
		}
		catch (Throwable t) {
			if (t instanceof PortalException) {
				throw (PortalException)t;
			}

			if (t instanceof SystemException) {
				throw (SystemException)t;
			}

			throw new SystemException(t);
		}
	}

	protected Object executeDynamicQuery(
			Method dynamicQueryMethod, Object... arguments)
		throws PortalException {

		try {
			return dynamicQueryMethod.invoke(_baseLocalService, arguments);
		}
		catch (InvocationTargetException ite) {
			Throwable throwable = ite.getCause();

			if (throwable instanceof PortalException) {
				throw (PortalException)throwable;
			}
			else if (throwable instanceof SystemException) {
				throw (SystemException)throwable;
			}

			throw new SystemException(ite);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	protected long getCompanyId() {
		return _companyId;
	}

	protected Projection getCountProjection() {
		return ProjectionFactoryUtil.rowCount();
	}

	protected int getInterval() {
		return _interval;
	}

	protected Class<?> getModelClass() {
		return _modelClass;
	}

	protected TransactionConfig getTransactionConfig() {
		return _transactionConfig;
	}

	protected void intervalCompleted(long startPrimaryKey, long endPrimaryKey)
		throws PortalException {
	}

	protected void performAction(Object object) throws PortalException {
		if (_performActionMethod != null) {
			_performActionMethod.performAction(object);
		}
	}

	private static volatile PortalExecutorManager _portalExecutorManager =
		ServiceProxyFactory.newServiceTrackedInstance(
			PortalExecutorManager.class, DefaultActionableDynamicQuery.class,
			"_portalExecutorManager", true);

	private AddCriteriaMethod _addCriteriaMethod;
	private AddOrderCriteriaMethod _addOrderCriteriaMethod;
	private BaseLocalService _baseLocalService;
	private ClassLoader _classLoader;
	private long _companyId;
	private Method _dynamicQueryCountMethod;
	private Method _dynamicQueryMethod;
	private final ExecutorService _executorService =
		_portalExecutorManager.getPortalExecutor(
			DefaultActionableDynamicQuery.class.getName());
	private long _groupId;
	private String _groupIdPropertyName = "groupId";
	private int _interval = Indexer.DEFAULT_INTERVAL;
	private Class<?> _modelClass;
	private int _offset;
	private boolean _parallel;

	@SuppressWarnings("rawtypes")
	private PerformActionMethod _performActionMethod;

	private PerformCountMethod _performCountMethod;
	private String _primaryKeyPropertyName;
	private TransactionConfig _transactionConfig;

}