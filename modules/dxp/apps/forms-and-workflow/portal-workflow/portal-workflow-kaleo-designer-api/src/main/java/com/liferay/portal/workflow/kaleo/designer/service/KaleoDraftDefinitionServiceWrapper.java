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
 * Provides a wrapper for {@link KaleoDraftDefinitionService}.
 *
 * @author Eduardo Lundgren
 * @see KaleoDraftDefinitionService
 * @generated
 */
@ProviderType
public class KaleoDraftDefinitionServiceWrapper
	implements KaleoDraftDefinitionService,
		ServiceWrapper<KaleoDraftDefinitionService> {
	public KaleoDraftDefinitionServiceWrapper(
		KaleoDraftDefinitionService kaleoDraftDefinitionService) {
		_kaleoDraftDefinitionService = kaleoDraftDefinitionService;
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
	* @throws PortalException if the user did not have the required permissions
	to create the Kaleo draft definition or if a portal exception
	occurred
	*/
	@Override
	public com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition addKaleoDraftDefinition(
		long userId, long groupId, java.lang.String name,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.lang.String content, int version, int draftVersion,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kaleoDraftDefinitionService.addKaleoDraftDefinition(userId,
			groupId, name, titleMap, content, version, draftVersion,
			serviceContext);
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
	* @throws PortalException if the user did not have the required permissions
	to access the Kaleo draft definition or if a portal exception
	occurred
	*/
	@Override
	public com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition getKaleoDraftDefinition(
		java.lang.String name, int version, int draftVersion,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kaleoDraftDefinitionService.getKaleoDraftDefinition(name,
			version, draftVersion, serviceContext);
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
	* @throws PortalException if a matching kaleo draft definition could not be
	found or if the user did not have the required permissions to
	access the Kaleo draft definition
	*/
	@Override
	public com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition getLatestKaleoDraftDefinition(
		java.lang.String name, int version,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kaleoDraftDefinitionService.getLatestKaleoDraftDefinition(name,
			version, serviceContext);
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
	guest permissions and group permissions for the Kkaleo draft
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
		return _kaleoDraftDefinitionService.publishKaleoDraftDefinition(userId,
			groupId, name, titleMap, content, serviceContext);
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
		return _kaleoDraftDefinitionService.updateKaleoDraftDefinition(userId,
			name, titleMap, content, version, serviceContext);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _kaleoDraftDefinitionService.getOSGiServiceIdentifier();
	}

	/**
	* Returns the Kaleo draft definitions.
	*
	* @return the Kaleo draft definitions
	* @throws PortalException if a portal exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition> getKaleoDraftDefinitions()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kaleoDraftDefinitionService.getKaleoDraftDefinitions();
	}

	/**
	* Returns an ordered range of the latest Kaleo draft definitions matching
	* the company and version.
	*
	* @param companyId the primary key of the Kaleo draft definition's company
	* @param version the Kaleo draft definition's published version
	* @param start the lower bound of the range of Kaleo draft definitions to
	return
	* @param end the upper bound of the range of Kkaleo draft definitions to
	return (not inclusive)
	* @param orderByComparator the comparator to order the Kaleo draft
	definitions
	* @return the range of matching Kaleo draft definitions ordered by the
	comparator
	* @throws PortalException if a portal exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition> getLatestKaleoDraftDefinitions(
		long companyId, int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kaleoDraftDefinitionService.getLatestKaleoDraftDefinitions(companyId,
			version, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of the latest Kaleo draft definitions matching
	* the company, version, and keywords.
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
	* @throws PortalException if a portal exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition> getLatestKaleoDraftDefinitions(
		long companyId, java.lang.String keywords, int version, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _kaleoDraftDefinitionService.getLatestKaleoDraftDefinitions(companyId,
			keywords, version, start, end, orderByComparator);
	}

	/**
	* Deletes the Kaleo draft definition and its resources.
	*
	* @param name the Kaleo draft definition's name
	* @param version the Kaleo draft definition's published version
	* @param serviceContext the service context to be applied. This can set
	guest permissions and group permissions for the Kaleo draft
	definition.
	* @throws PortalException if the user did not have the required permissions
	to delete the Kaleo draft definition or if a portal exception
	occurred
	*/
	@Override
	public void deleteKaleoDraftDefinitions(java.lang.String name, int version,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_kaleoDraftDefinitionService.deleteKaleoDraftDefinitions(name, version,
			serviceContext);
	}

	@Override
	public KaleoDraftDefinitionService getWrappedService() {
		return _kaleoDraftDefinitionService;
	}

	@Override
	public void setWrappedService(
		KaleoDraftDefinitionService kaleoDraftDefinitionService) {
		_kaleoDraftDefinitionService = kaleoDraftDefinitionService;
	}

	private KaleoDraftDefinitionService _kaleoDraftDefinitionService;
}