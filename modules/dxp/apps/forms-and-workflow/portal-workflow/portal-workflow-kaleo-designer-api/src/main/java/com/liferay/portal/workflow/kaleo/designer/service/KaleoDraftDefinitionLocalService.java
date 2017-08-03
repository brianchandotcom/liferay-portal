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

package com.liferay.portal.workflow.kaleo.designer.service;

import aQute.bnd.annotation.ProviderType;

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
import com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition;

import java.io.Serializable;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Provides the local service interface for KaleoDraftDefinition. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Eduardo Lundgren
 * @see KaleoDraftDefinitionLocalServiceUtil
 * @see com.liferay.portal.workflow.kaleo.designer.service.base.KaleoDraftDefinitionLocalServiceBaseImpl
 * @see com.liferay.portal.workflow.kaleo.designer.service.impl.KaleoDraftDefinitionLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface KaleoDraftDefinitionLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link KaleoDraftDefinitionLocalServiceUtil} to access the kaleo draft definition local service. Add custom service methods to {@link com.liferay.portal.workflow.kaleo.designer.service.impl.KaleoDraftDefinitionLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	public DynamicQuery dynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* @throws PortalException
	*/
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	* Adds the kaleo draft definition to the database. Also notifies the appropriate model listeners.
	*
	* @param kaleoDraftDefinition the kaleo draft definition
	* @return the kaleo draft definition that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public KaleoDraftDefinition addKaleoDraftDefinition(
		KaleoDraftDefinition kaleoDraftDefinition);

	/**
	* Adds a Kaleo draft definition.
	*
	* @param userId the primary key of the Kaleo draft definition's
	creator/owner
	* @param groupId the primary key of the Kaleo draft definition's group
	* @param name the Kaleo draft definition's name
	* @param titleMap the Kaleo draft definition's locales and localized
	titles
	* @param content the content wrapped in XML
	* @param version the Kaleo draft definition's published version
	* @param draftVersion the Kaleo draft definition's draft version
	* @param serviceContext the service context to be applied. This can set
	guest permissions and group permissions for the Kaleo draft
	definition.
	* @return the Kaleo draft definition
	* @throws PortalException if a portal exception occurred
	*/
	public KaleoDraftDefinition addKaleoDraftDefinition(long userId,
		long groupId, java.lang.String name,
		Map<Locale, java.lang.String> titleMap, java.lang.String content,
		int version, int draftVersion, ServiceContext serviceContext)
		throws PortalException;

	/**
	* Creates a new kaleo draft definition with the primary key. Does not add the kaleo draft definition to the database.
	*
	* @param kaleoDraftDefinitionId the primary key for the new kaleo draft definition
	* @return the new kaleo draft definition
	*/
	public KaleoDraftDefinition createKaleoDraftDefinition(
		long kaleoDraftDefinitionId);

	/**
	* Deletes the kaleo draft definition from the database. Also notifies the appropriate model listeners.
	*
	* @param kaleoDraftDefinition the kaleo draft definition
	* @return the kaleo draft definition that was removed
	* @throws PortalException
	*/
	@Indexable(type = IndexableType.DELETE)
	public KaleoDraftDefinition deleteKaleoDraftDefinition(
		KaleoDraftDefinition kaleoDraftDefinition) throws PortalException;

	/**
	* Deletes the Kaleo draft definition and its resources matching the name,
	* published version, and draft version.
	*
	* @param name the Kaleo draft definition's name
	* @param version the Kaleo draft definition's published version
	* @param draftVersion the Kaleo draft definition's draft version
	* @param serviceContext the service context to be applied. This can set
	guest permissions and group permissions for the Kaleo draft
	definition.
	* @return the deleted Kaleo draft definition
	* @throws PortalException if a portal exception occurred
	*/
	public KaleoDraftDefinition deleteKaleoDraftDefinition(
		java.lang.String name, int version, int draftVersion,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Deletes the kaleo draft definition with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param kaleoDraftDefinitionId the primary key of the kaleo draft definition
	* @return the kaleo draft definition that was removed
	* @throws PortalException if a kaleo draft definition with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public KaleoDraftDefinition deleteKaleoDraftDefinition(
		long kaleoDraftDefinitionId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public KaleoDraftDefinition fetchKaleoDraftDefinition(
		long kaleoDraftDefinitionId);

	/**
	* Returns the Kaleo draft definition matching the name, published version,
	* and draft version.
	*
	* @param name the Kaleo draft definition's name
	* @param version the Kaleo draft definition's published version
	* @param draftVersion the Kaleo draft definition's draft version
	* @param serviceContext the service context to be applied. This can set
	guest permissions and group permissions for the Kaleo draft
	definition.
	* @return the matching Kaleo draft definition
	* @throws PortalException if a portal exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public KaleoDraftDefinition getKaleoDraftDefinition(java.lang.String name,
		int version, int draftVersion, ServiceContext serviceContext)
		throws PortalException;

	/**
	* Returns the kaleo draft definition with the primary key.
	*
	* @param kaleoDraftDefinitionId the primary key of the kaleo draft definition
	* @return the kaleo draft definition
	* @throws PortalException if a kaleo draft definition with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public KaleoDraftDefinition getKaleoDraftDefinition(
		long kaleoDraftDefinitionId) throws PortalException;

	/**
	* Returns the latest Kaleo draft definition matching the name and version.
	*
	* @param name the Kaleo draft definition's name
	* @param version the Kaleo draft definition's published version
	* @param serviceContext the service context to be applied. This can set
	guest permissions and group permissions for the Kaleo draft
	definition.
	* @return the latest matching Kaleo draft definition
	* @throws PortalException if a matching Kaleo draft definition could not be
	found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public KaleoDraftDefinition getLatestKaleoDraftDefinition(
		java.lang.String name, int version, ServiceContext serviceContext)
		throws PortalException;

	/**
	* Adds a Kaleo draft definition with a draft version increment.
	*
	* @param userId the primary key of the Kaleo draft definition's
	creator/owner
	* @param name the Kaleo draft definition's name
	* @param version the Kaleo draft definition's published version
	* @param serviceContext the service context to be applied. This can set
	guest permissions and group permissions for the Kaleo draft
	deifnition.
	* @return the Kaleo draft definition
	* @throws PortalException if a portal exception occurred
	*/
	public KaleoDraftDefinition incrementKaleoDraftDefinitionDraftVersion(
		long userId, java.lang.String name, int version,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Publishes the Kaleo draft definition.
	*
	* @param userId the primary key of the Kaleo draft definition's
	creator/owner
	* @param groupId the primary key of the Kaleo draft definition's group
	* @param name the Kaleo draft definition's name
	* @param titleMap the Kaleo draft definition's locales and localized
	titles
	* @param content the content wrapped in XML
	* @param serviceContext the service context to be applied. This can set
	guest permissions and group permissions for the Kaleo draft
	definition.
	* @return the published Kaleo draft definition
	* @throws PortalException if the user did not have the required permissions
	to publish the Kaleo draft definition or if a portal exception
	occurred
	*/
	public KaleoDraftDefinition publishKaleoDraftDefinition(long userId,
		long groupId, java.lang.String name,
		Map<Locale, java.lang.String> titleMap, java.lang.String content,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Updates the kaleo draft definition in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param kaleoDraftDefinition the kaleo draft definition
	* @return the kaleo draft definition that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public KaleoDraftDefinition updateKaleoDraftDefinition(
		KaleoDraftDefinition kaleoDraftDefinition);

	/**
	* Updates the Kaleo draft definition by replacing its content and title and
	* incrementing the draft version.
	*
	* @param userId the primary key of the Kaleo draft definition's
	creator/owner
	* @param name the Kaleo draft definition's name
	* @param titleMap the Kaleo draft definition's locales and localized
	titles
	* @param content the content wrapped in XML
	* @param version the Kaleo draft definition's published version
	* @param serviceContext the service context to be applied. This can set
	guest permissions and group permissions for the Kaleo draft
	definition.
	* @return the updated Kaleo draft definition
	* @throws PortalException if the user did not have the required permissions
	to update the Kaleo draft definition or if a portal exception
	occurred
	*/
	public KaleoDraftDefinition updateKaleoDraftDefinition(long userId,
		java.lang.String name, Map<Locale, java.lang.String> titleMap,
		java.lang.String content, int version, ServiceContext serviceContext)
		throws PortalException;

	/**
	* Returns the number of kaleo draft definitions.
	*
	* @return the number of kaleo draft definitions
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getKaleoDraftDefinitionsCount();

	/**
	* Returns the number of Kaleo draft definition matching the name and
	* version.
	*
	* @param name the Kaleo draft definition's name
	* @param version the Kaleo draft definition's published version
	* @param serviceContext the service context to be applied. This can set
	guest permissions and group permissions for the Kaleo draft
	definition.
	* @return the number of matching Kaleo draft definitions
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getKaleoDraftDefinitionsCount(java.lang.String name,
		int version, ServiceContext serviceContext);

	/**
	* Returns the number of Kaleo draft definitions matching the company and
	* version.
	*
	* @param companyId the primary key of the Kaleo draft definition's company
	* @param version the Kaleo draft definition's published version
	* @return the number of matching Kaleo draft definitions
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getLatestKaleoDraftDefinitionsCount(long companyId, int version);

	/**
	* Returns the number of Kaleo draft definitions matching the company,
	* keywords, and version.
	*
	* @param companyId the primary key of the Kaleo draft definition's company
	* @param keywords the Kaleo draft definition's name or title
	* @param version the Kaleo draft definition's published version
	* @return the number of matching Kaleo draft definitions
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getLatestKaleoDraftDefinitionsCount(long companyId,
		java.lang.String keywords, int version);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.workflow.kaleo.designer.model.impl.KaleoDraftDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.workflow.kaleo.designer.model.impl.KaleoDraftDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Returns a range of all the kaleo draft definitions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.workflow.kaleo.designer.model.impl.KaleoDraftDefinitionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of kaleo draft definitions
	* @param end the upper bound of the range of kaleo draft definitions (not inclusive)
	* @return the range of kaleo draft definitions
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<KaleoDraftDefinition> getKaleoDraftDefinitions(int start,
		int end);

	/**
	* Returns an ordered range of the Kaleo draft definitions matching the name
	* and version.
	*
	* @param name the Kaleo draft definition's name
	* @param version the Kaleo draft definition's published version
	* @param start the lower bound of the range of Kaleo draft definitions to
	return
	* @param end the upper bound of the range of Kaleo draft definitions to
	return (not inclusive)
	* @param orderByComparator the comparator to order the Kaleo draft
	definitions
	* @param serviceContext the service context to be applied. This can set
	guest permissions and group permissions for the Kaleo draft
	definition.
	* @return the range of matching Kaleo draft definitions ordered by the
	comparator
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<KaleoDraftDefinition> getKaleoDraftDefinitions(
		java.lang.String name, int version, int start, int end,
		OrderByComparator orderByComparator, ServiceContext serviceContext);

	/**
	* Returns an ordered range of the latest Kaleo draft definitions matching
	* the company and version.
	*
	* @param companyId the primary key of the Kaleo draft definition's company
	* @param version the Kaleo draft definition's published version
	* @param start the lower bound of the range of Kaleo draft definitions to
	return
	* @param end the upper bound of the range of Kaleo draft definitions to
	return (not inclusive)
	* @param orderByComparator the comparator to order the Kaleo draft
	definitions
	* @return the range of matching Kaleo draft definitions ordered by the
	comparator
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<KaleoDraftDefinition> getLatestKaleoDraftDefinitions(
		long companyId, int version, int start, int end,
		OrderByComparator orderByComparator);

	/**
	* Returns an ordered range of the latest Kaleo draft definitions matching
	* the company, keywords, and version.
	*
	* @param companyId the primary key of the Kaleo draft definition's company
	* @param keywords the Kaleo draft definition's name or title
	* @param version the Kaleo draft definition's published version
	* @param start the lower bound of the range of Kaleo draft definitions to
	return
	* @param end the upper bound of the range of Kaleo draft definitions to
	return (not inclusive)
	* @param orderByComparator the comparator to order the Kaleo draft
	definitions
	* @return the range of matching Kaleo draft definitions ordered by the
	comparator
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<KaleoDraftDefinition> getLatestKaleoDraftDefinitions(
		long companyId, java.lang.String keywords, int version, int start,
		int end, OrderByComparator orderByComparator);

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

	/**
	* Deletes the kaleo draft definition and its resources matching the name
	* and version.
	*
	* @param name the Kaleo draft definition's name
	* @param version the Kaleo draft definition's published version
	* @param serviceContext the service context to be applied. This can set
	guest permissions and group permissions for the Kaleo draft
	definition.
	* @throws PortalException if a portal exception occurred
	*/
	public void deleteKaleoDraftDefinitions(java.lang.String name, int version,
		ServiceContext serviceContext) throws PortalException;
}