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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionAttribute;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.BaseLocalService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public abstract class BaseActionableDynamicQuery
	implements ActionableDynamicQuery {

	public static final TransactionAttribute
		REQUIRES_NEW_TRANSACTION_ATTRIBUTE;

	static {
		TransactionAttribute.Builder builder =
			new TransactionAttribute.Builder();

		builder.propagation(Propagation.REQUIRES_NEW);
		builder.rollbackForClasses(
			PortalException.class, SystemException.class);

		REQUIRES_NEW_TRANSACTION_ATTRIBUTE = builder.build();
	}

	@Override
	public void addDocument(Document document) throws PortalException {
		if (_documents == null) {
			_documents = new ArrayList<Document>();
		}

		_documents.add(document);

		if (_documents.size() >= _interval) {
			indexInterval();
		}
	}

	@Override
	public AddCriteriaMethod getAddCriteriaMethod() {
		return _addCriteriaMethod;
	}

	@Override
	public PerformActionMethod getPerformActionMethod() {
		return _performActionMethod;
	}

	@Override
	public PerformCountMethod getPerformCountMethod() {
		return _performCountMethod;
	}

	@Override
	public void performActions() throws PortalException {
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

	@Override
	public long performCount() throws PortalException {
		if (_performCountMethod != null) {
			return _performCountMethod.performCount();
		}

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			_clazz, _classLoader);

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
	public void setClass(Class<?> clazz) {
		_clazz = clazz;
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
	public void setPerformActionMethod(
		PerformActionMethod performActionMethod) {

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
	public void setSearchEngineId(String searchEngineId) {
		_searchEngineId = searchEngineId;
	}

	@Override
	public void setTransactionAttribute(
		TransactionAttribute transactionAttribute) {

		_transactionAttribute = transactionAttribute;
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

	protected void addDocuments(Collection<Document> documents)
		throws PortalException {

		if (_documents == null) {
			_documents = new ArrayList<Document>();
		}

		_documents.addAll(documents);

		if (_documents.size() >= _interval) {
			indexInterval();
		}
	}

	protected long doPerformActions(long previousPrimaryKey)
		throws PortalException {

		final DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			_clazz, _classLoader);

		Property property = PropertyFactoryUtil.forName(
			_primaryKeyPropertyName);

		dynamicQuery.add(property.gt(previousPrimaryKey));

		dynamicQuery.addOrder(OrderFactoryUtil.asc(_primaryKeyPropertyName));

		dynamicQuery.setLimit(0, _interval);

		addDefaultCriteria(dynamicQuery);

		addCriteria(dynamicQuery);

		Callable<Long> callable = new Callable<Long>() {

			@Override
			public Long call() throws Exception {
				List<Object> objects = (List<Object>)executeDynamicQuery(
					_dynamicQueryMethod, dynamicQuery);

				if (objects.isEmpty()) {
					return -1L;
				}

				for (Object object : objects) {
					performAction(object);
				}

				if (objects.size() < _interval) {
					return -1L;
				}

				BaseModel<?> baseModel = (BaseModel<?>)objects.get(
					objects.size() - 1);

				return (Long)baseModel.getPrimaryKeyObj();
			}

		};

		TransactionAttribute transactionAttribute = getTransactionAttribute();

		try {
			if (transactionAttribute == null) {
				return callable.call();
			}
			else {
				return TransactionInvokerUtil.invoke(
					transactionAttribute, callable);
			}
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
		finally {
			indexInterval();
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

	protected Projection getCountProjection() {
		return ProjectionFactoryUtil.rowCount();
	}

	protected String getSearchEngineId() {
		return _searchEngineId;
	}

	protected TransactionAttribute getTransactionAttribute() {
		return _transactionAttribute;
	}

	protected void indexInterval() throws PortalException {
		if ((_documents == null) || _documents.isEmpty()) {
			return;
		}

		if (Validator.isNull(_searchEngineId)) {
			_searchEngineId = SearchEngineUtil.getSearchEngineId(_documents);
		}

		SearchEngineUtil.updateDocuments(
			_searchEngineId, _companyId, new ArrayList<Document>(_documents));

		_documents.clear();
	}

	@SuppressWarnings("unused")
	protected void intervalCompleted(long startPrimaryKey, long endPrimaryKey)
		throws PortalException {
	}

	protected void performAction(Object object) throws PortalException {
		if (_performActionMethod != null) {
			_performActionMethod.performAction(object);
		}
	}

	private AddCriteriaMethod _addCriteriaMethod;
	private BaseLocalService _baseLocalService;
	private ClassLoader _classLoader;
	private Class<?> _clazz;
	private long _companyId;
	private Collection<Document> _documents;
	private Method _dynamicQueryCountMethod;
	private Method _dynamicQueryMethod;
	private long _groupId;
	private String _groupIdPropertyName = "groupId";
	private int _interval = Indexer.DEFAULT_INTERVAL;
	private PerformActionMethod _performActionMethod;
	private PerformCountMethod _performCountMethod;
	private String _primaryKeyPropertyName;
	private String _searchEngineId;
	private TransactionAttribute _transactionAttribute;

}