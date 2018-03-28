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

package com.liferay.portal.workflow.kaleo.forms.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for KaleoProcess. This utility wraps
 * {@link com.liferay.portal.workflow.kaleo.forms.service.impl.KaleoProcessServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marcellus Tavares
 * @see KaleoProcessService
 * @see com.liferay.portal.workflow.kaleo.forms.service.base.KaleoProcessServiceBaseImpl
 * @see com.liferay.portal.workflow.kaleo.forms.service.impl.KaleoProcessServiceImpl
 * @generated
 */
@ProviderType
public class KaleoProcessServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.workflow.kaleo.forms.service.impl.KaleoProcessServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

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
	public static com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess addKaleoProcess(
		long groupId, long ddmStructureId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		long ddmTemplateId, java.lang.String workflowDefinitionName,
		int workflowDefinitionVersion,
		com.liferay.portal.workflow.kaleo.forms.model.KaleoTaskFormPairs kaleoTaskFormPairs,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addKaleoProcess(groupId, ddmStructureId, nameMap,
			descriptionMap, ddmTemplateId, workflowDefinitionName,
			workflowDefinitionVersion, kaleoTaskFormPairs, serviceContext);
	}

	/**
	* Deletes the Kaleo process and its resources.
	*
	* @param kaleoProcessId the primary key of the kaleo process to delete
	* @return the deleted Kaleo process
	* @throws PortalException if a portal exception occurred
	*/
	public static com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess deleteKaleoProcess(
		long kaleoProcessId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteKaleoProcess(kaleoProcessId);
	}

	/**
	* Returns the Kaleo process with the primary key.
	*
	* @param kaleoProcessId the primary key of the Kaleo process
	* @return the Kaleo process
	* @throws PortalException if a Kaleo process with the primary key could not
	be found
	*/
	public static com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess getKaleoProcess(
		long kaleoProcessId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getKaleoProcess(kaleoProcessId);
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
	public static java.util.List<com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess> search(
		long groupId, java.lang.String keywords, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator) {
		return getService()
				   .search(groupId, keywords, start, end, orderByComparator);
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
	public static int searchCount(long groupId, java.lang.String keywords) {
		return getService().searchCount(groupId, keywords);
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
	public static com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess updateKaleoProcess(
		long kaleoProcessId, long ddmStructureId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		long ddmTemplateId, java.lang.String workflowDefinitionName,
		int workflowDefinitionVersion,
		com.liferay.portal.workflow.kaleo.forms.model.KaleoTaskFormPairs kaleoTaskFormPairs,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateKaleoProcess(kaleoProcessId, ddmStructureId, nameMap,
			descriptionMap, ddmTemplateId, workflowDefinitionName,
			workflowDefinitionVersion, kaleoTaskFormPairs, serviceContext);
	}

	public static KaleoProcessService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<KaleoProcessService, KaleoProcessService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(KaleoProcessService.class);

		ServiceTracker<KaleoProcessService, KaleoProcessService> serviceTracker = new ServiceTracker<KaleoProcessService, KaleoProcessService>(bundle.getBundleContext(),
				KaleoProcessService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}