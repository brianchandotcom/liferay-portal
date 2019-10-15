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

package com.liferay.portal.kernel.service;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.model.VirtualHost;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.TreeMap;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the local service interface for VirtualHost. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see VirtualHostLocalServiceUtil
 * @generated
 */
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface VirtualHostLocalService
	extends BaseLocalService, PersistedModelLocalService {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link VirtualHostLocalServiceUtil} to access the virtual host local service. Add custom service methods to <code>com.liferay.portal.service.impl.VirtualHostLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	 * Adds the virtual host to the database. Also notifies the appropriate model listeners.
	 *
	 * @param virtualHost the virtual host
	 * @return the virtual host that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	public VirtualHost addVirtualHost(VirtualHost virtualHost);

	/**
	 * Creates a new virtual host with the primary key. Does not add the virtual host to the database.
	 *
	 * @param virtualHostId the primary key for the new virtual host
	 * @return the new virtual host
	 */
	@Transactional(enabled = false)
	public VirtualHost createVirtualHost(long virtualHostId);

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	/**
	 * Deletes the virtual host with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param virtualHostId the primary key of the virtual host
	 * @return the virtual host that was removed
	 * @throws PortalException if a virtual host with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	public VirtualHost deleteVirtualHost(long virtualHostId)
		throws PortalException;

	/**
	 * Deletes the virtual host from the database. Also notifies the appropriate model listeners.
	 *
	 * @param virtualHost the virtual host
	 * @return the virtual host that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	public VirtualHost deleteVirtualHost(VirtualHost virtualHost);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DynamicQuery dynamicQuery();

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery);

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.VirtualHostModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end);

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.VirtualHostModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator);

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long dynamicQueryCount(DynamicQuery dynamicQuery);

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long dynamicQueryCount(
		DynamicQuery dynamicQuery, Projection projection);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public VirtualHost fetchVirtualHost(long virtualHostId);

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #getVirtualHosts(long, long)}
	 */
	@Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public VirtualHost fetchVirtualHost(long companyId, long layoutSetId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public VirtualHost fetchVirtualHost(String hostname);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	 * Returns the virtual host with the primary key.
	 *
	 * @param virtualHostId the primary key of the virtual host
	 * @return the virtual host
	 * @throws PortalException if a virtual host with the primary key could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public VirtualHost getVirtualHost(long virtualHostId)
		throws PortalException;

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #getVirtualHosts(long, long)}
	 */
	@Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public VirtualHost getVirtualHost(long companyId, long layoutSetId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public VirtualHost getVirtualHost(String hostname) throws PortalException;

	/**
	 * Returns a range of all the virtual hosts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.VirtualHostModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of virtual hosts
	 * @param end the upper bound of the range of virtual hosts (not inclusive)
	 * @return the range of virtual hosts
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<VirtualHost> getVirtualHosts(int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<VirtualHost> getVirtualHosts(long companyId, long layoutSetId)
		throws PortalException;

	/**
	 * Returns the number of virtual hosts.
	 *
	 * @return the number of virtual hosts
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getVirtualHostsCount();

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link
	 #updateVirtualHosts(long, long, TreeMap)}
	 */
	@Deprecated
	public VirtualHost updateVirtualHost(
		long companyId, long layoutSetId, String hostname);

	/**
	 * Updates the virtual host in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param virtualHost the virtual host
	 * @return the virtual host that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	public VirtualHost updateVirtualHost(VirtualHost virtualHost);

	public List<VirtualHost> updateVirtualHosts(
		long companyId, long layoutSetId, TreeMap<String, String> hostnameMap);

}