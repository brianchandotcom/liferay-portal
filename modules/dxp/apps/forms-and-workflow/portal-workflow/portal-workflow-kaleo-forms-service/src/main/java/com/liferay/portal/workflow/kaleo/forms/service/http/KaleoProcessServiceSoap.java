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

package com.liferay.portal.workflow.kaleo.forms.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.workflow.kaleo.forms.service.KaleoProcessServiceUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * {@link KaleoProcessServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess}, that is translated to a
 * {@link com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessSoap}. Methods that SOAP cannot
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
 * @author Marcellus Tavares
 * @see KaleoProcessServiceHttp
 * @see com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessSoap
 * @see KaleoProcessServiceUtil
 * @generated
 */
@ProviderType
public class KaleoProcessServiceSoap {
	/**
	* Adds a kaleo process.
	*
	* @param groupId the primary key of the Kaleo process's group
	* @param ddmStructureId the primary key of the Kaleo process's DDM
	structure
	* @param nameMap the Kaleo process's locales and localized names
	* @param descriptionMap the Kaleo process's locales and localized
	descriptions
	* @param ddmTemplateId the primary key of the Kaleo process's DDM template
	* @param workflowDefinitionName the Kaleo process's workflow definition
	name
	* @param workflowDefinitionVersion the Kaleo process's workflow definition
	version
	* @param kaleoTaskFormPairs the Kaleo task form pairs. For more
	information, see the <code>portal.workflow.kaleo.forms.api</code>
	module's <code>KaleoTaskFormPairs</code> class.
	* @param serviceContext the service context to be applied. This can set
	guest permissions and group permissions for the Kaleo process.
	* @return the Kaleo process
	* @throws PortalException if a portal exception occurred
	*/
	public static com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessSoap addKaleoProcess(
		long groupId, long ddmStructureId, String[] nameMapLanguageIds,
		String[] nameMapValues, String[] descriptionMapLanguageIds,
		String[] descriptionMapValues, long ddmTemplateId,
		String workflowDefinitionName, int workflowDefinitionVersion,
		com.liferay.portal.workflow.kaleo.forms.model.KaleoTaskFormPairs kaleoTaskFormPairs,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(nameMapLanguageIds,
					nameMapValues);
			Map<Locale, String> descriptionMap = LocalizationUtil.getLocalizationMap(descriptionMapLanguageIds,
					descriptionMapValues);

			com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess returnValue =
				KaleoProcessServiceUtil.addKaleoProcess(groupId,
					ddmStructureId, nameMap, descriptionMap, ddmTemplateId,
					workflowDefinitionName, workflowDefinitionVersion,
					kaleoTaskFormPairs, serviceContext);

			return com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Deletes the Kaleo process and its resources.
	*
	* @param kaleoProcessId the primary key of the kaleo process to delete
	* @return the deleted Kaleo process
	* @throws PortalException if a portal exception occurred
	*/
	public static com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessSoap deleteKaleoProcess(
		long kaleoProcessId) throws RemoteException {
		try {
			com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess returnValue =
				KaleoProcessServiceUtil.deleteKaleoProcess(kaleoProcessId);

			return com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns the Kaleo process with the primary key.
	*
	* @param kaleoProcessId the primary key of the Kaleo process
	* @return the Kaleo process
	* @throws PortalException if a Kaleo process with the primary key could not
	be found
	*/
	public static com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessSoap getKaleoProcess(
		long kaleoProcessId) throws RemoteException {
		try {
			com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess returnValue =
				KaleoProcessServiceUtil.getKaleoProcess(kaleoProcessId);

			return com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns an ordered range of all Kaleo processes matching the parameters,
	* including a keywords parameter for matching String values to the Kaleo
	* process's name or description.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to <code>QueryUtil#ALL_POS</code>, which resides in
	* <code>portal-kernel</code>, will return the full result set.
	* </p>
	*
	* @param groupId the primary key of the Kaleo process's group
	* @param keywords the keywords (space separated) to look for and match in
	the Kaleo process name or description (optionally
	<code>null</code>). If the keywords value is not
	<code>null</code>, the search uses the <code>OR</code> operator
	for connecting query criteria; otherwise it uses the
	<code>AND</code> operator.
	* @param start the lower bound of the range of Kaleo processes to return
	* @param end the upper bound of the range of Kaleo processes to return
	(not inclusive)
	* @param orderByComparator the comparator to order the Kaleo processes
	* @return the range of matching Kaleo processes ordered by the comparator
	*/
	public static com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessSoap[] search(
		long groupId, String keywords, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess> returnValue =
				KaleoProcessServiceUtil.search(groupId, keywords, start, end,
					orderByComparator);

			return com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Returns the number of Kaleo processes matching the parameters. The
	* keywords parameter is used for matching String values to the Kaleo
	* process's name or description.
	*
	* @param groupId the primary key of the Kaleo process's group
	* @param keywords the keywords (space separated) to match in the Kaleo
	process name or description (optionally <code>null</code>). If
	the keywords value is not <code>null</code>, the <code>OR</code>
	operator is used for connecting query criteria; otherwise it uses
	the <code>AND</code> operator.
	* @return the number of matching Kaleo processes
	*/
	public static int searchCount(long groupId, String keywords)
		throws RemoteException {
		try {
			int returnValue = KaleoProcessServiceUtil.searchCount(groupId,
					keywords);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	/**
	* Updates the Kaleo process.
	*
	* @param kaleoProcessId the primary key of the Kaleo process
	* @param ddmStructureId the primary key of the Kaleo process's DDM
	structure
	* @param nameMap the Kaleo process's locales and localized names
	* @param descriptionMap the Kaleo process's locales and localized
	descriptions
	* @param ddmTemplateId the primary key of the Kaleo process's DDM template
	* @param workflowDefinitionName the Kaleo process's workflow definition
	name
	* @param workflowDefinitionVersion the Kaleo process's workflow definition
	version
	* @param kaleoTaskFormPairs the Kaleo task form pairs. For more
	information, see the <code>portal.workflow.kaleo.forms.api</code>
	module's <code>KaleoTaskFormPairs</code> class.
	* @param serviceContext the service context to be applied. This can set
	guest permissions and group permissions for the Kaleo process.
	* @return the Kaleo process
	* @throws PortalException if a portal exception occurred
	*/
	public static com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessSoap updateKaleoProcess(
		long kaleoProcessId, long ddmStructureId, String[] nameMapLanguageIds,
		String[] nameMapValues, String[] descriptionMapLanguageIds,
		String[] descriptionMapValues, long ddmTemplateId,
		String workflowDefinitionName, int workflowDefinitionVersion,
		com.liferay.portal.workflow.kaleo.forms.model.KaleoTaskFormPairs kaleoTaskFormPairs,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(nameMapLanguageIds,
					nameMapValues);
			Map<Locale, String> descriptionMap = LocalizationUtil.getLocalizationMap(descriptionMapLanguageIds,
					descriptionMapValues);

			com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess returnValue =
				KaleoProcessServiceUtil.updateKaleoProcess(kaleoProcessId,
					ddmStructureId, nameMap, descriptionMap, ddmTemplateId,
					workflowDefinitionName, workflowDefinitionVersion,
					kaleoTaskFormPairs, serviceContext);

			return com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(KaleoProcessServiceSoap.class);
}