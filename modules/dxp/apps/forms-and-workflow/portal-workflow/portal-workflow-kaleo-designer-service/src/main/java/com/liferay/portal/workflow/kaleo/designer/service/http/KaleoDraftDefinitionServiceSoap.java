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

package com.liferay.portal.workflow.kaleo.designer.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.workflow.kaleo.designer.service.KaleoDraftDefinitionServiceUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * {@link KaleoDraftDefinitionServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinitionSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition}, that is translated to a
 * {@link com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinitionSoap}. Methods that SOAP cannot
 * safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author Eduardo Lundgren
 * @see KaleoDraftDefinitionServiceHttp
 * @see com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinitionSoap
 * @see KaleoDraftDefinitionServiceUtil
 * @generated
 */
@ProviderType
public class KaleoDraftDefinitionServiceSoap {
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
	public static com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinitionSoap addKaleoDraftDefinition(
		long userId, long groupId, java.lang.String name,
		java.lang.String[] titleMapLanguageIds,
		java.lang.String[] titleMapValues, java.lang.String content,
		int version, int draftVersion,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(titleMapLanguageIds,
					titleMapValues);

			com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition returnValue =
				KaleoDraftDefinitionServiceUtil.addKaleoDraftDefinition(userId,
					groupId, name, titleMap, content, version, draftVersion,
					serviceContext);

			return com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinitionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
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
		throws RemoteException {
		try {
			KaleoDraftDefinitionServiceUtil.deleteKaleoDraftDefinitions(name,
				version, serviceContext);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
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
	public static com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinitionSoap getKaleoDraftDefinition(
		java.lang.String name, int version, int draftVersion,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition returnValue =
				KaleoDraftDefinitionServiceUtil.getKaleoDraftDefinition(name,
					version, draftVersion, serviceContext);

			return com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinitionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns the Kaleo draft definitions.
	*
	* @return the Kaleo draft definitions
	* @throws PortalException if a portal exception occurred
	*/
	public static com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinitionSoap[] getKaleoDraftDefinitions()
		throws RemoteException {
		try {
			java.util.List<com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition> returnValue =
				KaleoDraftDefinitionServiceUtil.getKaleoDraftDefinitions();

			return com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinitionSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
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
	public static com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinitionSoap getLatestKaleoDraftDefinition(
		java.lang.String name, int version,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition returnValue =
				KaleoDraftDefinitionServiceUtil.getLatestKaleoDraftDefinition(name,
					version, serviceContext);

			return com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinitionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
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
	public static com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinitionSoap[] getLatestKaleoDraftDefinitions(
		long companyId, int version, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition> returnValue =
				KaleoDraftDefinitionServiceUtil.getLatestKaleoDraftDefinitions(companyId,
					version, start, end, orderByComparator);

			return com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinitionSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
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
	public static com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinitionSoap[] getLatestKaleoDraftDefinitions(
		long companyId, java.lang.String keywords, int version, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition> returnValue =
				KaleoDraftDefinitionServiceUtil.getLatestKaleoDraftDefinitions(companyId,
					keywords, version, start, end, orderByComparator);

			return com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinitionSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
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
	public static com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinitionSoap publishKaleoDraftDefinition(
		long userId, long groupId, java.lang.String name,
		java.lang.String[] titleMapLanguageIds,
		java.lang.String[] titleMapValues, java.lang.String content,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(titleMapLanguageIds,
					titleMapValues);

			com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition returnValue =
				KaleoDraftDefinitionServiceUtil.publishKaleoDraftDefinition(userId,
					groupId, name, titleMap, content, serviceContext);

			return com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinitionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
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
	public static com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinitionSoap updateKaleoDraftDefinition(
		long userId, java.lang.String name,
		java.lang.String[] titleMapLanguageIds,
		java.lang.String[] titleMapValues, java.lang.String content,
		int version,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(titleMapLanguageIds,
					titleMapValues);

			com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition returnValue =
				KaleoDraftDefinitionServiceUtil.updateKaleoDraftDefinition(userId,
					name, titleMap, content, version, serviceContext);

			return com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinitionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(KaleoDraftDefinitionServiceSoap.class);
}