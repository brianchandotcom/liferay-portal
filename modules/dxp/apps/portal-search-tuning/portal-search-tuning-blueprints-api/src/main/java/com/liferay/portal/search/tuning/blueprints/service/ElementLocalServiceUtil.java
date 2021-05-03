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

package com.liferay.portal.search.tuning.blueprints.service;

import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.search.tuning.blueprints.model.Element;

import java.io.Serializable;

import java.util.List;
import java.util.Map;

/**
 * Provides the local service utility for Element. This utility wraps
 * <code>com.liferay.portal.search.tuning.blueprints.service.impl.ElementLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ElementLocalService
 * @generated
 */
public class ElementLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.search.tuning.blueprints.service.impl.ElementLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the element to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ElementLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param element the element
	 * @return the element that was added
	 */
	public static Element addElement(Element element) {
		return getService().addElement(element);
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Use <code>com.liferay.portal.search.tuning.elements.service.ElementLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>com.liferay.portal.search.tuning.elements.service.ElementLocalServiceUtil</code>.
	 */
	public static Element addElement(
			long userId, long groupId, Map<java.util.Locale, String> titleMap,
			Map<java.util.Locale, String> descriptionMap, String configuration,
			int type,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addElement(
			userId, groupId, titleMap, descriptionMap, configuration, type,
			serviceContext);
	}

	/**
	 * Creates a new element with the primary key. Does not add the element to the database.
	 *
	 * @param elementId the primary key for the new element
	 * @return the new element
	 */
	public static Element createElement(long elementId) {
		return getService().createElement(elementId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel createPersistedModel(
			Serializable primaryKeyObj)
		throws PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the element from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ElementLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param element the element
	 * @return the element that was removed
	 * @throws PortalException
	 */
	public static Element deleteElement(Element element)
		throws PortalException {

		return getService().deleteElement(element);
	}

	/**
	 * Deletes the element with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ElementLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param elementId the primary key of the element
	 * @return the element that was removed
	 * @throws PortalException if a element with the primary key could not be found
	 */
	public static Element deleteElement(long elementId) throws PortalException {
		return getService().deleteElement(elementId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static <T> T dslQuery(DSLQuery dslQuery) {
		return getService().dslQuery(dslQuery);
	}

	public static DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	public static <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.search.tuning.blueprints.model.impl.ElementModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.search.tuning.blueprints.model.impl.ElementModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	public static <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	public static long dynamicQueryCount(DynamicQuery dynamicQuery) {
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
		DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static Element fetchElement(long elementId) {
		return getService().fetchElement(elementId);
	}

	/**
	 * Returns the element matching the UUID and group.
	 *
	 * @param uuid the element's UUID
	 * @param groupId the primary key of the group
	 * @return the matching element, or <code>null</code> if a matching element could not be found
	 */
	public static Element fetchElementByUuidAndGroupId(
		String uuid, long groupId) {

		return getService().fetchElementByUuidAndGroupId(uuid, groupId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static int getCompanyElementsCount(long companyId, int type) {
		return getService().getCompanyElementsCount(companyId, type);
	}

	/**
	 * Returns the element with the primary key.
	 *
	 * @param elementId the primary key of the element
	 * @return the element
	 * @throws PortalException if a element with the primary key could not be found
	 */
	public static Element getElement(long elementId) throws PortalException {
		return getService().getElement(elementId);
	}

	/**
	 * Returns the element matching the UUID and group.
	 *
	 * @param uuid the element's UUID
	 * @param groupId the primary key of the group
	 * @return the matching element
	 * @throws PortalException if a matching element could not be found
	 */
	public static Element getElementByUuidAndGroupId(String uuid, long groupId)
		throws PortalException {

		return getService().getElementByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Returns a range of all the elements.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.search.tuning.blueprints.model.impl.ElementModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @return the range of elements
	 */
	public static List<Element> getElements(int start, int end) {
		return getService().getElements(start, end);
	}

	/**
	 * Returns all the elements matching the UUID and company.
	 *
	 * @param uuid the UUID of the elements
	 * @param companyId the primary key of the company
	 * @return the matching elements, or an empty list if no matches were found
	 */
	public static List<Element> getElementsByUuidAndCompanyId(
		String uuid, long companyId) {

		return getService().getElementsByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of elements matching the UUID and company.
	 *
	 * @param uuid the UUID of the elements
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of elements
	 * @param end the upper bound of the range of elements (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching elements, or an empty list if no matches were found
	 */
	public static List<Element> getElementsByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<Element> orderByComparator) {

		return getService().getElementsByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of elements.
	 *
	 * @return the number of elements
	 */
	public static int getElementsCount() {
		return getService().getElementsCount();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	public static List<Element> getGroupElements(
		long groupId, int type, int start, int end) {

		return getService().getGroupElements(groupId, type, start, end);
	}

	public static List<Element> getGroupElements(
		long groupId, int status, int type, int start, int end) {

		return getService().getGroupElements(groupId, status, type, start, end);
	}

	public static List<Element> getGroupElements(
		long groupId, int status, int type, int start, int end,
		OrderByComparator<Element> orderByComparator) {

		return getService().getGroupElements(
			groupId, status, type, start, end, orderByComparator);
	}

	public static List<Element> getGroupElements(
		long groupId, int type, int start, int end,
		OrderByComparator<Element> orderByComparator) {

		return getService().getGroupElements(
			groupId, type, start, end, orderByComparator);
	}

	public static int getGroupElementsCount(long groupId, int type) {
		return getService().getGroupElementsCount(groupId, type);
	}

	public static int getGroupElementsCount(
		long groupId, int status, int type) {

		return getService().getGroupElementsCount(groupId, status, type);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
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
	 * @throws PortalException
	 */
	public static PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the element in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect ElementLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param element the element
	 * @return the element that was updated
	 */
	public static Element updateElement(Element element) {
		return getService().updateElement(element);
	}

	public static Element updateElement(
			long userId, long elementId, Map<java.util.Locale, String> titleMap,
			Map<java.util.Locale, String> descriptionMap, String configuration,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateElement(
			userId, elementId, titleMap, descriptionMap, configuration,
			serviceContext);
	}

	public static Element updateStatus(long userId, long elementId, int status)
		throws PortalException {

		return getService().updateStatus(userId, elementId, status);
	}

	public static ElementLocalService getService() {
		return _service;
	}

	private static volatile ElementLocalService _service;

}