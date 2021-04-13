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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ElementLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ElementLocalService
 * @generated
 */
public class ElementLocalServiceWrapper
	implements ElementLocalService, ServiceWrapper<ElementLocalService> {

	public ElementLocalServiceWrapper(ElementLocalService elementLocalService) {
		_elementLocalService = elementLocalService;
	}

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
	@Override
	public com.liferay.portal.search.tuning.blueprints.model.Element addElement(
		com.liferay.portal.search.tuning.blueprints.model.Element element) {

		return _elementLocalService.addElement(element);
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Use <code>com.liferay.portal.search.tuning.elements.service.ElementLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>com.liferay.portal.search.tuning.elements.service.ElementLocalServiceUtil</code>.
	 */
	@Override
	public com.liferay.portal.search.tuning.blueprints.model.Element addElement(
			long userId, long groupId,
			java.util.Map<java.util.Locale, String> titleMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			String configuration, int type,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _elementLocalService.addElement(
			userId, groupId, titleMap, descriptionMap, configuration, type,
			serviceContext);
	}

	/**
	 * Creates a new element with the primary key. Does not add the element to the database.
	 *
	 * @param elementId the primary key for the new element
	 * @return the new element
	 */
	@Override
	public com.liferay.portal.search.tuning.blueprints.model.Element
		createElement(long elementId) {

		return _elementLocalService.createElement(elementId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _elementLocalService.createPersistedModel(primaryKeyObj);
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
	@Override
	public com.liferay.portal.search.tuning.blueprints.model.Element
			deleteElement(
				com.liferay.portal.search.tuning.blueprints.model.Element
					element)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _elementLocalService.deleteElement(element);
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
	@Override
	public com.liferay.portal.search.tuning.blueprints.model.Element
			deleteElement(long elementId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _elementLocalService.deleteElement(elementId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _elementLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _elementLocalService.dslQuery(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _elementLocalService.dynamicQuery();
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

		return _elementLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _elementLocalService.dynamicQuery(dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _elementLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
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

		return _elementLocalService.dynamicQueryCount(dynamicQuery);
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

		return _elementLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public com.liferay.portal.search.tuning.blueprints.model.Element
		fetchElement(long elementId) {

		return _elementLocalService.fetchElement(elementId);
	}

	/**
	 * Returns the element matching the UUID and group.
	 *
	 * @param uuid the element's UUID
	 * @param groupId the primary key of the group
	 * @return the matching element, or <code>null</code> if a matching element could not be found
	 */
	@Override
	public com.liferay.portal.search.tuning.blueprints.model.Element
		fetchElementByUuidAndGroupId(String uuid, long groupId) {

		return _elementLocalService.fetchElementByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _elementLocalService.getActionableDynamicQuery();
	}

	@Override
	public int getCompanyElementsCount(long companyId, int type) {
		return _elementLocalService.getCompanyElementsCount(companyId, type);
	}

	/**
	 * Returns the element with the primary key.
	 *
	 * @param elementId the primary key of the element
	 * @return the element
	 * @throws PortalException if a element with the primary key could not be found
	 */
	@Override
	public com.liferay.portal.search.tuning.blueprints.model.Element getElement(
			long elementId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _elementLocalService.getElement(elementId);
	}

	/**
	 * Returns the element matching the UUID and group.
	 *
	 * @param uuid the element's UUID
	 * @param groupId the primary key of the group
	 * @return the matching element
	 * @throws PortalException if a matching element could not be found
	 */
	@Override
	public com.liferay.portal.search.tuning.blueprints.model.Element
			getElementByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _elementLocalService.getElementByUuidAndGroupId(uuid, groupId);
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
	@Override
	public java.util.List
		<com.liferay.portal.search.tuning.blueprints.model.Element> getElements(
			int start, int end) {

		return _elementLocalService.getElements(start, end);
	}

	/**
	 * Returns all the elements matching the UUID and company.
	 *
	 * @param uuid the UUID of the elements
	 * @param companyId the primary key of the company
	 * @return the matching elements, or an empty list if no matches were found
	 */
	@Override
	public java.util.List
		<com.liferay.portal.search.tuning.blueprints.model.Element>
			getElementsByUuidAndCompanyId(String uuid, long companyId) {

		return _elementLocalService.getElementsByUuidAndCompanyId(
			uuid, companyId);
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
	@Override
	public java.util.List
		<com.liferay.portal.search.tuning.blueprints.model.Element>
			getElementsByUuidAndCompanyId(
				String uuid, long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.search.tuning.blueprints.model.Element>
						orderByComparator) {

		return _elementLocalService.getElementsByUuidAndCompanyId(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of elements.
	 *
	 * @return the number of elements
	 */
	@Override
	public int getElementsCount() {
		return _elementLocalService.getElementsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _elementLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public java.util.List
		<com.liferay.portal.search.tuning.blueprints.model.Element>
			getGroupElements(long groupId, int type, int start, int end) {

		return _elementLocalService.getGroupElements(groupId, type, start, end);
	}

	@Override
	public java.util.List
		<com.liferay.portal.search.tuning.blueprints.model.Element>
			getGroupElements(
				long groupId, int status, int type, int start, int end) {

		return _elementLocalService.getGroupElements(
			groupId, status, type, start, end);
	}

	@Override
	public java.util.List
		<com.liferay.portal.search.tuning.blueprints.model.Element>
			getGroupElements(
				long groupId, int status, int type, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.search.tuning.blueprints.model.Element>
						orderByComparator) {

		return _elementLocalService.getGroupElements(
			groupId, status, type, start, end, orderByComparator);
	}

	@Override
	public java.util.List
		<com.liferay.portal.search.tuning.blueprints.model.Element>
			getGroupElements(
				long groupId, int type, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.search.tuning.blueprints.model.Element>
						orderByComparator) {

		return _elementLocalService.getGroupElements(
			groupId, type, start, end, orderByComparator);
	}

	@Override
	public int getGroupElementsCount(long groupId, int type) {
		return _elementLocalService.getGroupElementsCount(groupId, type);
	}

	@Override
	public int getGroupElementsCount(long groupId, int status, int type) {
		return _elementLocalService.getGroupElementsCount(
			groupId, status, type);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _elementLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _elementLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _elementLocalService.getPersistedModel(primaryKeyObj);
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
	@Override
	public com.liferay.portal.search.tuning.blueprints.model.Element
		updateElement(
			com.liferay.portal.search.tuning.blueprints.model.Element element) {

		return _elementLocalService.updateElement(element);
	}

	@Override
	public com.liferay.portal.search.tuning.blueprints.model.Element
			updateElement(
				long userId, long elementId,
				java.util.Map<java.util.Locale, String> titleMap,
				java.util.Map<java.util.Locale, String> descriptionMap,
				String configuration,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _elementLocalService.updateElement(
			userId, elementId, titleMap, descriptionMap, configuration,
			serviceContext);
	}

	@Override
	public com.liferay.portal.search.tuning.blueprints.model.Element
			updateStatus(long userId, long elementId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _elementLocalService.updateStatus(userId, elementId, status);
	}

	@Override
	public ElementLocalService getWrappedService() {
		return _elementLocalService;
	}

	@Override
	public void setWrappedService(ElementLocalService elementLocalService) {
		_elementLocalService = elementLocalService;
	}

	private ElementLocalService _elementLocalService;

}