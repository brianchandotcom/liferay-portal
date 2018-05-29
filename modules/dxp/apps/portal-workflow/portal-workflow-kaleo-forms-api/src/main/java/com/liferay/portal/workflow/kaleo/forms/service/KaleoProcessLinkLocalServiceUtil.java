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

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for KaleoProcessLink. This utility wraps
 * {@link com.liferay.portal.workflow.kaleo.forms.service.impl.KaleoProcessLinkLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Marcellus Tavares
 * @see KaleoProcessLinkLocalService
 * @see com.liferay.portal.workflow.kaleo.forms.service.base.KaleoProcessLinkLocalServiceBaseImpl
 * @see com.liferay.portal.workflow.kaleo.forms.service.impl.KaleoProcessLinkLocalServiceImpl
 * @generated
 */
@ProviderType
public class KaleoProcessLinkLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.workflow.kaleo.forms.service.impl.KaleoProcessLinkLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the kaleo process link to the database. Also notifies the appropriate model listeners.
	*
	* @param kaleoProcessLink the kaleo process link
	* @return the kaleo process link that was added
	*/
	public static com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessLink addKaleoProcessLink(
		com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessLink kaleoProcessLink) {
		return getService().addKaleoProcessLink(kaleoProcessLink);
	}

	/**
	* Adds a Kaleo process link referencing the Kaleo process.
	*
	* @param kaleoProcessId the primary key of the Kaleo process
	* @param workflowTaskName the name of the Kaleo process link's workflow
	task
	* @param ddmTemplateId the primary key of the Kaleo process link's DDM
	template
	* @return the Kaleo process link
	*/
	public static com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessLink addKaleoProcessLink(
		long kaleoProcessId, String workflowTaskName, long ddmTemplateId) {
		return getService()
				   .addKaleoProcessLink(kaleoProcessId, workflowTaskName,
			ddmTemplateId);
	}

	/**
	* Creates a new kaleo process link with the primary key. Does not add the kaleo process link to the database.
	*
	* @param kaleoProcessLinkId the primary key for the new kaleo process link
	* @return the new kaleo process link
	*/
	public static com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessLink createKaleoProcessLink(
		long kaleoProcessLinkId) {
		return getService().createKaleoProcessLink(kaleoProcessLinkId);
	}

	/**
	* Deletes the kaleo process link from the database. Also notifies the appropriate model listeners.
	*
	* @param kaleoProcessLink the kaleo process link
	* @return the kaleo process link that was removed
	*/
	public static com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessLink deleteKaleoProcessLink(
		com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessLink kaleoProcessLink) {
		return getService().deleteKaleoProcessLink(kaleoProcessLink);
	}

	/**
	* Deletes the kaleo process link with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param kaleoProcessLinkId the primary key of the kaleo process link
	* @return the kaleo process link that was removed
	* @throws PortalException if a kaleo process link with the primary key could not be found
	*/
	public static com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessLink deleteKaleoProcessLink(
		long kaleoProcessLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteKaleoProcessLink(kaleoProcessLinkId);
	}

	/**
	* Deletes the Kaleo process links associated with the Kaleo process and
	* their resources.
	*
	* @param kaleoProcessId the primary key of the Kaleo process from which to
	delete Kaleo process links
	*/
	public static void deleteKaleoProcessLinks(long kaleoProcessId) {
		getService().deleteKaleoProcessLinks(kaleoProcessId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.workflow.kaleo.forms.model.impl.KaleoProcessLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.workflow.kaleo.forms.model.impl.KaleoProcessLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessLink fetchKaleoProcessLink(
		long kaleoProcessLinkId) {
		return getService().fetchKaleoProcessLink(kaleoProcessLinkId);
	}

	/**
	* Returns the Kaleo process link matching the Kaleo process and workflow
	* task name.
	*
	* @param kaleoProcessId the primary key of the Kaleo process link's Kaleo
	process
	* @param workflowTaskName the name of the Kaleo process link's workflow
	task
	* @return the Kaleo process link matching the Kaleo process and workflow
	task name, or <code>null</code> if a matching Kaleo process link
	could not be found
	*/
	public static com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessLink fetchKaleoProcessLink(
		long kaleoProcessId, String workflowTaskName) {
		return getService()
				   .fetchKaleoProcessLink(kaleoProcessId, workflowTaskName);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the kaleo process link with the primary key.
	*
	* @param kaleoProcessLinkId the primary key of the kaleo process link
	* @return the kaleo process link
	* @throws PortalException if a kaleo process link with the primary key could not be found
	*/
	public static com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessLink getKaleoProcessLink(
		long kaleoProcessLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getKaleoProcessLink(kaleoProcessLinkId);
	}

	/**
	* Returns a range of all the kaleo process links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.workflow.kaleo.forms.model.impl.KaleoProcessLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of kaleo process links
	* @param end the upper bound of the range of kaleo process links (not inclusive)
	* @return the range of kaleo process links
	*/
	public static java.util.List<com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessLink> getKaleoProcessLinks(
		int start, int end) {
		return getService().getKaleoProcessLinks(start, end);
	}

	/**
	* Returns the Kaleo process links matching the Kaleo process.
	*
	* @param kaleoProcessId the primary key of the Kaleo process link's Kaleo
	process
	* @return the Kaleo process links matching the Kaleo process, or
	<code>null</code> if a matching Kaleo process link could not be
	found
	*/
	public static java.util.List<com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessLink> getKaleoProcessLinks(
		long kaleoProcessId) {
		return getService().getKaleoProcessLinks(kaleoProcessId);
	}

	/**
	* Returns the number of kaleo process links.
	*
	* @return the number of kaleo process links
	*/
	public static int getKaleoProcessLinksCount() {
		return getService().getKaleoProcessLinksCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Updates the kaleo process link in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param kaleoProcessLink the kaleo process link
	* @return the kaleo process link that was updated
	*/
	public static com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessLink updateKaleoProcessLink(
		com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessLink kaleoProcessLink) {
		return getService().updateKaleoProcessLink(kaleoProcessLink);
	}

	/**
	* Updates the Kaleo process link, setting the primary key of the associated
	* Kaleo process.
	*
	* @param kaleoProcessLinkId the primary key of the Kaleo process link
	* @param kaleoProcessId the primary key of the Kaleo process link's Kaleo
	process
	* @return the Kaleo process link
	* @throws PortalException if a portal exception occurred
	*/
	public static com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessLink updateKaleoProcessLink(
		long kaleoProcessLinkId, long kaleoProcessId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateKaleoProcessLink(kaleoProcessLinkId, kaleoProcessId);
	}

	/**
	* Updates the Kaleo process link, replacing its values with new ones. New
	* values are set for the primary key of the associated Kaleo process, the
	* name of the associated workflow task, and the primary key of the
	* associated DDM Template.
	*
	* @param kaleoProcessLinkId the primary key of the Kaleo process link
	* @param kaleoProcessId the primary key of the Kaleo process link's Kaleo
	process
	* @param workflowTaskName the name of the Kaleo process link's workflow
	task
	* @param ddmTemplateId the primary key of the Kaleo process link's DDM
	template
	* @return the Kaleo process link
	* @throws PortalException if a portal exception occurred
	*/
	public static com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessLink updateKaleoProcessLink(
		long kaleoProcessLinkId, long kaleoProcessId, String workflowTaskName,
		long ddmTemplateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateKaleoProcessLink(kaleoProcessLinkId, kaleoProcessId,
			workflowTaskName, ddmTemplateId);
	}

	/**
	* Updates the Kaleo process link. If no Kaleo process link is found
	* matching the primary key of the Kaleo process and the workflow task name,
	* a new link is created.
	*
	* @param kaleoProcessId the primary key of the Kaleo process link's Kaleo
	process
	* @param workflowTaskName the name of the Kaleo process link's workflow
	task
	* @param ddmTemplateId the primary key of the Kaleo process link's DDM
	template
	* @return the Kaleo process link
	*/
	public static com.liferay.portal.workflow.kaleo.forms.model.KaleoProcessLink updateKaleoProcessLink(
		long kaleoProcessId, String workflowTaskName, long ddmTemplateId) {
		return getService()
				   .updateKaleoProcessLink(kaleoProcessId, workflowTaskName,
			ddmTemplateId);
	}

	public static KaleoProcessLinkLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<KaleoProcessLinkLocalService, KaleoProcessLinkLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(KaleoProcessLinkLocalService.class);

		ServiceTracker<KaleoProcessLinkLocalService, KaleoProcessLinkLocalService> serviceTracker =
			new ServiceTracker<KaleoProcessLinkLocalService, KaleoProcessLinkLocalService>(bundle.getBundleContext(),
				KaleoProcessLinkLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}