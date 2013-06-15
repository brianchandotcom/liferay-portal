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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.ServiceContext;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * The base interface for all ServiceBuilder persistence classes. This interface
 * should never need to be used directly.
 *
 * <p>
 * Caching information and settings can be found in
 * <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see    com.liferay.portal.service.persistence.impl.BasePersistenceImpl
 */
public interface BasePersistence<T extends BaseModel<T>> {

	/**
	 * Clears the cache for all instances of this model.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link
	 * com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this
	 * method.
	 * </p>
	 */
	public void clearCache();

	/**
	 * Clears the cache for a List instances of this model.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link
	 * com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this
	 * method.
	 * </p>
	 *
	 * @param modelList the List instances of this model to clear the cache for
	 */
	public void clearCache(List<T> modelList);

	/**
	 * Clears the cache for one instance of this model.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link
	 * com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this
	 * method.
	 * </p>
	 *
	 * @param model the instance of this model to clear the cache for
	 */
	public void clearCache(T model);

	public void closeSession(Session session);

	/**
	 * Returns the number of rows that match the dynamic query.
	 *
	 * @param  dynamicQuery the dynamic query
	 * @return the number of rows that match the dynamic query
	 * @throws SystemException if a system exception occurred
	 */
	public long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException;

	/**
	 * Returns the number of rows that match the dynamic query.
	 *
	 * @param  dynamicQuery the dynamic query
	 * @param  projection the projection to apply to the query
	 * @return the number of rows that match the dynamic query
	 * @throws SystemException if a system exception occurred
	 */
	public long countWithDynamicQuery(
			DynamicQuery dynamicQuery, Projection projection)
		throws SystemException;

	/**
	 * Returns the model instance with the primary key or returns
	 * <code>null</code> if it could not be found.
	 *
	 * @param  primaryKey the primary key of the model instance
	 * @return the model instance, or <code>null</code> if an instance of this
	 *         model with the primary key could not be found
	 * @throws SystemException if the primary key is <code>null</code>, or if a
	 *         system exception occurred
	 */
	public T fetchByPrimaryKey(Serializable primaryKey) throws SystemException;

	/**
	 * Returns the model instance with the primary key or throws a {@link
	 * NoSuchModelException} if it could not be found.
	 *
	 * @param  primaryKey the primary key of the model instance
	 * @return the model instance
	 * @throws NoSuchModelException if an instance of this model with the
	 *         primary key could not be found
	 * @throws SystemException if the primary key is <code>null</code>, or if a
	 *         system exception occurred
	 */
	public T findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException;

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param  dynamicQuery the dynamic query
	 * @return the matching rows
	 * @throws SystemException if a system exception occurred
	 */
	@SuppressWarnings("rawtypes")
	public List findWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException;

	/**
	 * Performs a dynamic query on the database and returns a range of the
	 * matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  dynamicQuery the dynamic query
	 * @param  start the lower bound of the range of matching rows
	 * @param  end the upper bound of the range of matching rows (not inclusive)
	 * @return the range of matching rows
	 * @throws SystemException if a system exception occurred
	 * @see    com.liferay.portal.kernel.dao.orm.QueryUtil#list(
	 *         com.liferay.portal.kernel.dao.orm.Query,
	 *         com.liferay.portal.kernel.dao.orm.Dialect, int, int)
	 */
	@SuppressWarnings("rawtypes")
	public List findWithDynamicQuery(
			DynamicQuery dynamicQuery, int start, int end)
		throws SystemException;

	/**
	 * Performs a dynamic query on the database and returns an ordered range of
	 * the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link
	 * com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  dynamicQuery the dynamic query
	 * @param  start the lower bound of the range of matching rows
	 * @param  end the upper bound of the range of matching rows (not inclusive)
	 * @param  orderByComparator the comparator to order the results by
	 *         (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 * @throws SystemException if a system exception occurred
	 */
	@SuppressWarnings("rawtypes")
	public List findWithDynamicQuery(
			DynamicQuery dynamicQuery, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException;

	public void flush() throws SystemException;

	public Session getCurrentSession() throws ORMException;

	/**
	 * Returns the data source for this model.
	 *
	 * @return the data source for this model
	 * @see    #setDataSource(DataSource)
	 */
	public DataSource getDataSource();

	/**
	 * Returns the listeners registered for this model.
	 *
	 * @return the listeners registered for this model
	 * @see    #registerListener(ModelListener)
	 */
	public ModelListener<T>[] getListeners();

	public Session openSession() throws ORMException;

	public SystemException processException(Exception e);

	/**
	 * Registers a new listener for this model.
	 *
	 * <p>
	 * A model listener is notified whenever a change is made to an instance of
	 * this model, such as when one is added, updated, or removed.
	 * </p>
	 *
	 * @param listener the model listener to register
	 */
	public void registerListener(ModelListener<T> listener);

	/**
	 * Removes the model instance with the primary key from the database. Also
	 * notifies the appropriate model listeners.
	 *
	 * @param  primaryKey the primary key of the model instance to remove
	 * @return the model instance that was removed
	 * @throws NoSuchModelException if an instance of this model with the
	 *         primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public T remove(Serializable primaryKey)
		throws NoSuchModelException, SystemException;

	/**
	 * Removes the model instance from the database. Also notifies the
	 * appropriate model listeners.
	 *
	 * @param  model the model instance to remove
	 * @return the model instance that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public T remove(T model) throws SystemException;

	/**
	 * Sets the data source for this model.
	 *
	 * @param dataSource the data source to use for this model
	 */
	public void setDataSource(DataSource dataSource);

	/**
	 * Unregisters the model listener.
	 *
	 * @param listener the model listener to unregister
	 * @see   #registerListener(ModelListener)
	 */
	public void unregisterListener(ModelListener<T> listener);

	/**
	 * Updates the model instance in the database or adds it if it does not yet
	 * exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * Typically not called directly, use local service update model methods
	 * instead. For example, {@link
	 * com.liferay.portal.service.UserLocalServiceUtil#updateUser(
	 * com.liferay.portal.model.User)}.
	 * </p>
	 *
	 * @param  model the model instance to update
	 * @return the model instance that was updated
	 * @throws SystemException if a system exception occurred
	 */
	public T update(T model) throws SystemException;

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #update(BaseModel)}}
	 */
	public T update(T model, boolean merge) throws SystemException;

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #update(BaseModel,
	 *             ServiceContext)}}
	 */
	public T update(T model, boolean merge, ServiceContext serviceContext)
		throws SystemException;

	/**
	 * Updates the model instance in the database or adds it if it does not yet
	 * exist, within a different service context. Also notifies the appropriate
	 * model listeners.
	 *
	 * @param  model the model instance to update
	 * @param  serviceContext the service context to be applied
	 * @return the model instance that was updated
	 * @throws SystemException if a system exception occurred
	 */
	public T update(T model, ServiceContext serviceContext)
		throws SystemException;

}