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

package com.liferay.portal.kernel.dao.orm;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.BaseLocalService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class BaseActionableDynamicQuery
	implements ActionableDynamicQuery {

	@Override
	public void performActions() throws PortalException, SystemException {
		long count = doPerformCount();

		if (count > _interval) {
			performActionsInMultipleIntervals();
		}
		else {
			performActionsInSingleInterval();
		}
	}

	public void performActions(long startPrimaryKey, long endPrimaryKey)
		throws PortalException, SystemException {

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			_clazz, _classLoader);

		Property property = PropertyFactoryUtil.forName(
			_primaryKeyPropertyName);

		dynamicQuery.add(property.ge(startPrimaryKey));
		dynamicQuery.add(property.lt(endPrimaryKey));

		addDefaultCriteria(dynamicQuery);

		addCriteria(dynamicQuery);

		List<Object> objects = (List<Object>)executeDynamicQuery(
			_dynamicQueryMethod, dynamicQuery);

		for (Object object : objects) {
			performAction(object);
		}
	}

	@Override
	public long performCount() throws PortalException, SystemException {
		return doPerformCount();
	}

	@Override
	public void setBaseLocalService(BaseLocalService baseLocalService)
		throws SystemException {

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
	public void setPrimaryKeyPropertyName(String primaryKeyPropertyName) {
		_primaryKeyPropertyName = primaryKeyPropertyName;
	}

	@Override
	public void setSearchEngineId(String searchEngineId) {
		_searchEngineId = searchEngineId;
	}

	protected void addCriteria(DynamicQuery dynamicQuery) {
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

	protected void addDocument(Document document) {
		if (_documents == null) {
			_documents = new ArrayList<Document>();
		}

		_documents.add(document);
	}

	protected void addDocuments(Collection<Document> documents) {
		if (_documents == null) {
			_documents = new ArrayList<Document>();
		}

		_documents.addAll(documents);
	}

	protected long doPerformCount() throws PortalException, SystemException {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			_clazz, _classLoader);

		addDefaultCriteria(dynamicQuery);

		addCriteria(dynamicQuery);

		return (Long)executeDynamicQuery(
			_dynamicQueryCountMethod, dynamicQuery, getCountProjection());
	}

	protected Object executeDynamicQuery(
			Method dynamicQueryMethod, Object... arguments)
		throws PortalException, SystemException {

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

	protected void indexInterval() throws PortalException {
		if (Validator.isNull(_searchEngineId) || (_documents == null) ||
			_documents.isEmpty()) {

			return;
		}

		SearchEngineUtil.updateDocuments(
			_searchEngineId, _companyId, new ArrayList<Document>(_documents));

		_documents.clear();
	}

	@SuppressWarnings("unused")
	protected void intervalCompleted(long startPrimaryKey, long endPrimaryKey)
		throws PortalException, SystemException {
	}

	protected abstract void performAction(Object object)
		throws PortalException, SystemException;

	protected void performActionsInMultipleIntervals()
		throws PortalException, SystemException {

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			_clazz, _classLoader);

		Projection minPrimaryKeyProjection = ProjectionFactoryUtil.min(
			_primaryKeyPropertyName);
		Projection maxPrimaryKeyProjection = ProjectionFactoryUtil.max(
			_primaryKeyPropertyName);

		ProjectionList projectionList = ProjectionFactoryUtil.projectionList();

		projectionList.add(minPrimaryKeyProjection);
		projectionList.add(maxPrimaryKeyProjection);

		dynamicQuery.setProjection(projectionList);

		addDefaultCriteria(dynamicQuery);

		addCriteria(dynamicQuery);

		List<Object[]> results = (List<Object[]>)executeDynamicQuery(
			_dynamicQueryMethod, dynamicQuery);

		Object[] minAndMaxPrimaryKeys = results.get(0);

		if ((minAndMaxPrimaryKeys[0] == null) ||
			(minAndMaxPrimaryKeys[1] == null)) {

			return;
		}

		long minPrimaryKey = (Long)minAndMaxPrimaryKeys[0];
		long maxPrimaryKey = (Long)minAndMaxPrimaryKeys[1];

		long startPrimaryKey = minPrimaryKey;
		long endPrimaryKey = startPrimaryKey + _interval;

		while (startPrimaryKey <= maxPrimaryKey) {
			performActions(startPrimaryKey, endPrimaryKey);

			indexInterval();

			intervalCompleted(startPrimaryKey, endPrimaryKey);

			startPrimaryKey = endPrimaryKey;
			endPrimaryKey += _interval;
		}
	}

	protected void performActionsInSingleInterval()
		throws PortalException, SystemException {

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			_clazz, _classLoader);

		addDefaultCriteria(dynamicQuery);

		addCriteria(dynamicQuery);

		List<Object> objects = (List<Object>)executeDynamicQuery(
			_dynamicQueryMethod, dynamicQuery);

		for (Object object : objects) {
			performAction(object);
		}

		indexInterval();
	}

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
	private String _primaryKeyPropertyName;
	private String _searchEngineId;

}