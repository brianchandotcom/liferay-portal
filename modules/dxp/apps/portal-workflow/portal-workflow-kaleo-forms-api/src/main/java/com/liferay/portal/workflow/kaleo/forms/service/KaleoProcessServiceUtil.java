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

package com.liferay.portal.workflow.kaleo.forms.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.workflow.kaleo.forms.model.KaleoProcess;

import java.util.List;
import java.util.Map;

/**
 * Provides the remote service utility for KaleoProcess. This utility wraps
 * <code>com.liferay.portal.workflow.kaleo.forms.service.impl.KaleoProcessServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marcellus Tavares
 * @see KaleoProcessService
 * @generated
 */
public class KaleoProcessServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.workflow.kaleo.forms.service.impl.KaleoProcessServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
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
	public static KaleoProcess addKaleoProcess(
			long groupId, long ddmStructureId,
			Map<java.util.Locale, String> nameMap,
			Map<java.util.Locale, String> descriptionMap, long ddmTemplateId,
			String workflowDefinitionName, int workflowDefinitionVersion,
			com.liferay.portal.workflow.kaleo.forms.model.KaleoTaskFormPairs
				kaleoTaskFormPairs,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addKaleoProcess(
			groupId, ddmStructureId, nameMap, descriptionMap, ddmTemplateId,
			workflowDefinitionName, workflowDefinitionVersion,
			kaleoTaskFormPairs, serviceContext);
	}

	/**
	 * Deletes the Kaleo process and its resources.
	 *
	 * @param kaleoProcessId the primary key of the kaleo process to delete
	 * @return the deleted Kaleo process
	 * @throws PortalException if a portal exception occurred
	 */
	public static KaleoProcess deleteKaleoProcess(long kaleoProcessId)
		throws PortalException {

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
	public static KaleoProcess getKaleoProcess(long kaleoProcessId)
		throws PortalException {

		return getService().getKaleoProcess(kaleoProcessId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
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
	public static List<KaleoProcess> search(
		long groupId, String keywords, int start, int end,
		OrderByComparator<KaleoProcess> orderByComparator) {

		return getService().search(
			groupId, keywords, start, end, orderByComparator);
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
	public static int searchCount(long groupId, String keywords) {
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
	public static KaleoProcess updateKaleoProcess(
			long kaleoProcessId, long ddmStructureId,
			Map<java.util.Locale, String> nameMap,
			Map<java.util.Locale, String> descriptionMap, long ddmTemplateId,
			String workflowDefinitionName, int workflowDefinitionVersion,
			com.liferay.portal.workflow.kaleo.forms.model.KaleoTaskFormPairs
				kaleoTaskFormPairs,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateKaleoProcess(
			kaleoProcessId, ddmStructureId, nameMap, descriptionMap,
			ddmTemplateId, workflowDefinitionName, workflowDefinitionVersion,
			kaleoTaskFormPairs, serviceContext);
	}

	public static KaleoProcessService getService() {
		return _service;
	}

	public static void setService(KaleoProcessService service) {
		_service = service;
	}

	private static volatile KaleoProcessService _service;

}