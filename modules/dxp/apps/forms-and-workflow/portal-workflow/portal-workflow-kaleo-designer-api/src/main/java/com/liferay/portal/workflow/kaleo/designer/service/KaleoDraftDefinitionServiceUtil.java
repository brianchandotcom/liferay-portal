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

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for KaleoDraftDefinition. This utility wraps
 * {@link com.liferay.portal.workflow.kaleo.designer.service.impl.KaleoDraftDefinitionServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Eduardo Lundgren
 * @see KaleoDraftDefinitionService
 * @see com.liferay.portal.workflow.kaleo.designer.service.base.KaleoDraftDefinitionServiceBaseImpl
 * @see com.liferay.portal.workflow.kaleo.designer.service.impl.KaleoDraftDefinitionServiceImpl
 * @generated
 */
@ProviderType
public class KaleoDraftDefinitionServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.workflow.kaleo.designer.service.impl.KaleoDraftDefinitionServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

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
	public static com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition addKaleoDraftDefinition(
		long userId, long groupId, java.lang.String name,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.lang.String content, int version, int draftVersion,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addKaleoDraftDefinition(userId, groupId, name, titleMap,
			content, version, draftVersion, serviceContext);
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
	public static com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition getKaleoDraftDefinition(
		java.lang.String name, int version, int draftVersion,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getKaleoDraftDefinition(name, version, draftVersion,
			serviceContext);
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
	public static com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition getLatestKaleoDraftDefinition(
		java.lang.String name, int version,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getLatestKaleoDraftDefinition(name, version, serviceContext);
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
	public static com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition publishKaleoDraftDefinition(
		long userId, long groupId, java.lang.String name,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.lang.String content,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .publishKaleoDraftDefinition(userId, groupId, name,
			titleMap, content, serviceContext);
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
	public static com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition updateKaleoDraftDefinition(
		long userId, java.lang.String name,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.lang.String content, int version,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateKaleoDraftDefinition(userId, name, titleMap, content,
			version, serviceContext);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	* Returns the Kaleo draft definitions.
	*
	* @return the Kaleo draft definitions
	* @throws PortalException if a portal exception occurred
	*/
	public static java.util.List<com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition> getKaleoDraftDefinitions()
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getKaleoDraftDefinitions();
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
	public static java.util.List<com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition> getLatestKaleoDraftDefinitions(
		long companyId, int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getLatestKaleoDraftDefinitions(companyId, version, start,
			end, orderByComparator);
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
	public static java.util.List<com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition> getLatestKaleoDraftDefinitions(
		long companyId, java.lang.String keywords, int version, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getLatestKaleoDraftDefinitions(companyId, keywords,
			version, start, end, orderByComparator);
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
	public static void deleteKaleoDraftDefinitions(java.lang.String name,
		int version,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteKaleoDraftDefinitions(name, version, serviceContext);
	}

	public static KaleoDraftDefinitionService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<KaleoDraftDefinitionService, KaleoDraftDefinitionService> _serviceTracker =
		ServiceTrackerFactory.open(KaleoDraftDefinitionService.class);
}