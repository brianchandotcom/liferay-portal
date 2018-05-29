/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.resiliency.spi.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.cluster.Clusterable;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.resiliency.spi.model.SPIDefinition;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service interface for SPIDefinition. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Michael C. Han
 * @see SPIDefinitionLocalServiceUtil
 * @see com.liferay.portal.resiliency.spi.service.base.SPIDefinitionLocalServiceBaseImpl
 * @see com.liferay.portal.resiliency.spi.service.impl.SPIDefinitionLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface SPIDefinitionLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SPIDefinitionLocalServiceUtil} to access the spi definition local service. Add custom service methods to {@link com.liferay.portal.resiliency.spi.service.impl.SPIDefinitionLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public SPIDefinition addSPIDefinition(long userId, String name,
		String connectorAddress, int connectorPort, String description,
		String jvmArguments, String portletIds, String servletContextNames,
		String typeSettings, ServiceContext serviceContext)
		throws PortalException;

	/**
	* Adds the spi definition to the database. Also notifies the appropriate model listeners.
	*
	* @param spiDefinition the spi definition
	* @return the spi definition that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public SPIDefinition addSPIDefinition(SPIDefinition spiDefinition);

	/**
	* Creates a new spi definition with the primary key. Does not add the spi definition to the database.
	*
	* @param spiDefinitionId the primary key for the new spi definition
	* @return the new spi definition
	*/
	@Transactional(enabled = false)
	public SPIDefinition createSPIDefinition(long spiDefinitionId);

	/**
	* @throws PortalException
	*/
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	/**
	* Deletes the spi definition with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param spiDefinitionId the primary key of the spi definition
	* @return the spi definition that was removed
	* @throws PortalException if a spi definition with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public SPIDefinition deleteSPIDefinition(long spiDefinitionId)
		throws PortalException;

	/**
	* Deletes the spi definition from the database. Also notifies the appropriate model listeners.
	*
	* @param spiDefinition the spi definition
	* @return the spi definition that was removed
	* @throws PortalException
	*/
	@Indexable(type = IndexableType.DELETE)
	public SPIDefinition deleteSPIDefinition(SPIDefinition spiDefinition)
		throws PortalException;

	public DynamicQuery dynamicQuery();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery);

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.resiliency.spi.model.impl.SPIDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end);

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.resiliency.spi.model.impl.SPIDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SPIDefinition fetchSPIDefinition(long spiDefinitionId);

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

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Tuple getPortletIdsAndServletContextNames();

	/**
	* Returns the spi definition with the primary key.
	*
	* @param spiDefinitionId the primary key of the spi definition
	* @return the spi definition
	* @throws PortalException if a spi definition with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SPIDefinition getSPIDefinition(long spiDefinitionId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SPIDefinition getSPIDefinition(long companyId, String name)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SPIDefinition> getSPIDefinitions();

	/**
	* Returns a range of all the spi definitions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.resiliency.spi.model.impl.SPIDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of spi definitions
	* @param end the upper bound of the range of spi definitions (not inclusive)
	* @return the range of spi definitions
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SPIDefinition> getSPIDefinitions(int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SPIDefinition> getSPIDefinitions(long companyId, int status);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SPIDefinition> getSPIDefinitions(long companyId, int[] statuses);

	/**
	* Returns the number of spi definitions.
	*
	* @return the number of spi definitions
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getSPIDefinitionsCount();

	@Clusterable
	public void startSPI(long spiDefinitionId) throws PortalException;

	public long startSPIinBackground(long userId, long spiDefinitionId)
		throws PortalException;

	public long startSPIinBackground(long userId, long spiDefinitionId,
		boolean automatedRestart) throws PortalException;

	@Clusterable
	public void stopSPI(long spiDefinitionId) throws PortalException;

	public long stopSPIinBackground(long userId, long spiDefinitionId)
		throws PortalException;

	public SPIDefinition updateSPIDefinition(long spiDefinitionId, int status,
		String statusMessage) throws PortalException;

	public SPIDefinition updateSPIDefinition(long userId, long spiDefinitionId,
		String connectorAddress, int connectorPort, String description,
		String jvmArguments, String portletIds, String servletContextNames,
		String typeSettings, ServiceContext serviceContext)
		throws PortalException;

	/**
	* Updates the spi definition in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param spiDefinition the spi definition
	* @return the spi definition that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public SPIDefinition updateSPIDefinition(SPIDefinition spiDefinition);

	public SPIDefinition updateTypeSettings(long userId, long spiDefinitionId,
		String typeSettings, ServiceContext serviceContext)
		throws PortalException;
}