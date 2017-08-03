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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link KaleoDraftDefinitionLocalService}.
 *
 * @author Eduardo Lundgren
 * @see KaleoDraftDefinitionLocalService
 * @generated
 */
@ProviderType
public class KaleoDraftDefinitionLocalServiceWrapper
	implements KaleoDraftDefinitionLocalService,
		ServiceWrapper<KaleoDraftDefinitionLocalService> {
	public KaleoDraftDefinitionLocalServiceWrapper(
		KaleoDraftDefinitionLocalService kaleoDraftDefinitionLocalService) {
		_kaleoDraftDefinitionLocalService = kaleoDraftDefinitionLocalService;
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _kaleoDraftDefinitionLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _kaleoDraftDefinitionLocalService.dynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _kaleoDraftDefinitionLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kaleoDraftDefinitionLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kaleoDraftDefinitionLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Adds the kaleo draft definition to the database. Also notifies the appropriate model listeners.
	*
	* @param kaleoDraftDefinition the kaleo draft definition
	* @return the kaleo draft definition that was added
	*/
	@Override
	public com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition addKaleoDraftDefinition(
		com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition kaleoDraftDefinition) {
		return _kaleoDraftDefinitionLocalService.addKaleoDraftDefinition(kaleoDraftDefinition);
	}

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
	@Override
	public com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition addKaleoDraftDefinition(
		long userId, long groupId, java.lang.String name,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.lang.String content, int version, int draftVersion,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kaleoDraftDefinitionLocalService.addKaleoDraftDefinition(userId,
			groupId, name, titleMap, content, version, draftVersion,
			serviceContext);
	}

	/**
	* Creates a new kaleo draft definition with the primary key. Does not add the kaleo draft definition to the database.
	*
	* @param kaleoDraftDefinitionId the primary key for the new kaleo draft definition
	* @return the new kaleo draft definition
	*/
	@Override
	public com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition createKaleoDraftDefinition(
		long kaleoDraftDefinitionId) {
		return _kaleoDraftDefinitionLocalService.createKaleoDraftDefinition(kaleoDraftDefinitionId);
	}

	/**
	* Deletes the kaleo draft definition from the database. Also notifies the appropriate model listeners.
	*
	* @param kaleoDraftDefinition the kaleo draft definition
	* @return the kaleo draft definition that was removed
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition deleteKaleoDraftDefinition(
		com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition kaleoDraftDefinition)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kaleoDraftDefinitionLocalService.deleteKaleoDraftDefinition(kaleoDraftDefinition);
	}

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
	@Override
	public com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition deleteKaleoDraftDefinition(
		java.lang.String name, int version, int draftVersion,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kaleoDraftDefinitionLocalService.deleteKaleoDraftDefinition(name,
			version, draftVersion, serviceContext);
	}

	/**
	* Deletes the kaleo draft definition with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param kaleoDraftDefinitionId the primary key of the kaleo draft definition
	* @return the kaleo draft definition that was removed
	* @throws PortalException if a kaleo draft definition with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition deleteKaleoDraftDefinition(
		long kaleoDraftDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kaleoDraftDefinitionLocalService.deleteKaleoDraftDefinition(kaleoDraftDefinitionId);
	}

	@Override
	public com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition fetchKaleoDraftDefinition(
		long kaleoDraftDefinitionId) {
		return _kaleoDraftDefinitionLocalService.fetchKaleoDraftDefinition(kaleoDraftDefinitionId);
	}

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
	@Override
	public com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition getKaleoDraftDefinition(
		java.lang.String name, int version, int draftVersion,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kaleoDraftDefinitionLocalService.getKaleoDraftDefinition(name,
			version, draftVersion, serviceContext);
	}

	/**
	* Returns the kaleo draft definition with the primary key.
	*
	* @param kaleoDraftDefinitionId the primary key of the kaleo draft definition
	* @return the kaleo draft definition
	* @throws PortalException if a kaleo draft definition with the primary key could not be found
	*/
	@Override
	public com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition getKaleoDraftDefinition(
		long kaleoDraftDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kaleoDraftDefinitionLocalService.getKaleoDraftDefinition(kaleoDraftDefinitionId);
	}

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
	@Override
	public com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition getLatestKaleoDraftDefinition(
		java.lang.String name, int version,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kaleoDraftDefinitionLocalService.getLatestKaleoDraftDefinition(name,
			version, serviceContext);
	}

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
	@Override
	public com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition incrementKaleoDraftDefinitionDraftVersion(
		long userId, java.lang.String name, int version,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kaleoDraftDefinitionLocalService.incrementKaleoDraftDefinitionDraftVersion(userId,
			name, version, serviceContext);
	}

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
	@Override
	public com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition publishKaleoDraftDefinition(
		long userId, long groupId, java.lang.String name,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.lang.String content,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kaleoDraftDefinitionLocalService.publishKaleoDraftDefinition(userId,
			groupId, name, titleMap, content, serviceContext);
	}

	/**
	* Updates the kaleo draft definition in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param kaleoDraftDefinition the kaleo draft definition
	* @return the kaleo draft definition that was updated
	*/
	@Override
	public com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition updateKaleoDraftDefinition(
		com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition kaleoDraftDefinition) {
		return _kaleoDraftDefinitionLocalService.updateKaleoDraftDefinition(kaleoDraftDefinition);
	}

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
	@Override
	public com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition updateKaleoDraftDefinition(
		long userId, java.lang.String name,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.lang.String content, int version,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kaleoDraftDefinitionLocalService.updateKaleoDraftDefinition(userId,
			name, titleMap, content, version, serviceContext);
	}

	/**
	* Returns the number of kaleo draft definitions.
	*
	* @return the number of kaleo draft definitions
	*/
	@Override
	public int getKaleoDraftDefinitionsCount() {
		return _kaleoDraftDefinitionLocalService.getKaleoDraftDefinitionsCount();
	}

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
	@Override
	public int getKaleoDraftDefinitionsCount(java.lang.String name,
		int version,
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {
		return _kaleoDraftDefinitionLocalService.getKaleoDraftDefinitionsCount(name,
			version, serviceContext);
	}

	/**
	* Returns the number of Kaleo draft definitions matching the company and
	* version.
	*
	* @param companyId the primary key of the Kaleo draft definition's company
	* @param version the Kaleo draft definition's published version
	* @return the number of matching Kaleo draft definitions
	*/
	@Override
	public int getLatestKaleoDraftDefinitionsCount(long companyId, int version) {
		return _kaleoDraftDefinitionLocalService.getLatestKaleoDraftDefinitionsCount(companyId,
			version);
	}

	/**
	* Returns the number of Kaleo draft definitions matching the company,
	* keywords, and version.
	*
	* @param companyId the primary key of the Kaleo draft definition's company
	* @param keywords the Kaleo draft definition's name or title
	* @param version the Kaleo draft definition's published version
	* @return the number of matching Kaleo draft definitions
	*/
	@Override
	public int getLatestKaleoDraftDefinitionsCount(long companyId,
		java.lang.String keywords, int version) {
		return _kaleoDraftDefinitionLocalService.getLatestKaleoDraftDefinitionsCount(companyId,
			keywords, version);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _kaleoDraftDefinitionLocalService.getOSGiServiceIdentifier();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _kaleoDraftDefinitionLocalService.dynamicQuery(dynamicQuery);
	}

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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _kaleoDraftDefinitionLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return _kaleoDraftDefinitionLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

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
	@Override
	public java.util.List<com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition> getKaleoDraftDefinitions(
		int start, int end) {
		return _kaleoDraftDefinitionLocalService.getKaleoDraftDefinitions(start,
			end);
	}

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
	@Override
	public java.util.List<com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition> getKaleoDraftDefinitions(
		java.lang.String name, int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator,
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {
		return _kaleoDraftDefinitionLocalService.getKaleoDraftDefinitions(name,
			version, start, end, orderByComparator, serviceContext);
	}

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
	@Override
	public java.util.List<com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition> getLatestKaleoDraftDefinitions(
		long companyId, int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return _kaleoDraftDefinitionLocalService.getLatestKaleoDraftDefinitions(companyId,
			version, start, end, orderByComparator);
	}

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
	@Override
	public java.util.List<com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition> getLatestKaleoDraftDefinitions(
		long companyId, java.lang.String keywords, int version, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return _kaleoDraftDefinitionLocalService.getLatestKaleoDraftDefinitions(companyId,
			keywords, version, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _kaleoDraftDefinitionLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return _kaleoDraftDefinitionLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

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
	@Override
	public void deleteKaleoDraftDefinitions(java.lang.String name, int version,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_kaleoDraftDefinitionLocalService.deleteKaleoDraftDefinitions(name,
			version, serviceContext);
	}

	@Override
	public KaleoDraftDefinitionLocalService getWrappedService() {
		return _kaleoDraftDefinitionLocalService;
	}

	@Override
	public void setWrappedService(
		KaleoDraftDefinitionLocalService kaleoDraftDefinitionLocalService) {
		_kaleoDraftDefinitionLocalService = kaleoDraftDefinitionLocalService;
	}

	private KaleoDraftDefinitionLocalService _kaleoDraftDefinitionLocalService;
}