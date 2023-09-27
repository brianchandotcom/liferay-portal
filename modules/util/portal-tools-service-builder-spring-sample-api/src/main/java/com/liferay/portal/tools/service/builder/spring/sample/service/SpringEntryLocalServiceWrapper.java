/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.spring.sample.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link SpringEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see SpringEntryLocalService
 * @generated
 */
public class SpringEntryLocalServiceWrapper
	implements ServiceWrapper<SpringEntryLocalService>,
			   SpringEntryLocalService {

	public SpringEntryLocalServiceWrapper() {
		this(null);
	}

	public SpringEntryLocalServiceWrapper(
		SpringEntryLocalService springEntryLocalService) {

		_springEntryLocalService = springEntryLocalService;
	}

	/**
	 * Adds the spring entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SpringEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param springEntry the spring entry
	 * @return the spring entry that was added
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.spring.sample.model.SpringEntry
			addSpringEntry(
				com.liferay.portal.tools.service.builder.spring.sample.model.
					SpringEntry springEntry) {

		return _springEntryLocalService.addSpringEntry(springEntry);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _springEntryLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Creates a new spring entry with the primary key. Does not add the spring entry to the database.
	 *
	 * @param springEntryId the primary key for the new spring entry
	 * @return the new spring entry
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.spring.sample.model.SpringEntry
			createSpringEntry(long springEntryId) {

		return _springEntryLocalService.createSpringEntry(springEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _springEntryLocalService.deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the spring entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SpringEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param springEntryId the primary key of the spring entry
	 * @return the spring entry that was removed
	 * @throws PortalException if a spring entry with the primary key could not be found
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.spring.sample.model.SpringEntry
				deleteSpringEntry(long springEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _springEntryLocalService.deleteSpringEntry(springEntryId);
	}

	/**
	 * Deletes the spring entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SpringEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param springEntry the spring entry
	 * @return the spring entry that was removed
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.spring.sample.model.SpringEntry
			deleteSpringEntry(
				com.liferay.portal.tools.service.builder.spring.sample.model.
					SpringEntry springEntry) {

		return _springEntryLocalService.deleteSpringEntry(springEntry);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _springEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _springEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _springEntryLocalService.dynamicQuery();
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

		return _springEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.spring.sample.model.impl.SpringEntryModelImpl</code>.
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

		return _springEntryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.spring.sample.model.impl.SpringEntryModelImpl</code>.
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

		return _springEntryLocalService.dynamicQuery(
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

		return _springEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _springEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public
		com.liferay.portal.tools.service.builder.spring.sample.model.SpringEntry
			fetchSpringEntry(long springEntryId) {

		return _springEntryLocalService.fetchSpringEntry(springEntryId);
	}

	/**
	 * Returns the spring entry with the matching UUID and company.
	 *
	 * @param uuid the spring entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching spring entry, or <code>null</code> if a matching spring entry could not be found
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.spring.sample.model.SpringEntry
			fetchSpringEntryByUuidAndCompanyId(String uuid, long companyId) {

		return _springEntryLocalService.fetchSpringEntryByUuidAndCompanyId(
			uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _springEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _springEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _springEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _springEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns a range of all the spring entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.spring.sample.model.impl.SpringEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of spring entries
	 * @param end the upper bound of the range of spring entries (not inclusive)
	 * @return the range of spring entries
	 */
	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.spring.sample.model.
			SpringEntry> getSpringEntries(int start, int end) {

		return _springEntryLocalService.getSpringEntries(start, end);
	}

	/**
	 * Returns the number of spring entries.
	 *
	 * @return the number of spring entries
	 */
	@Override
	public int getSpringEntriesCount() {
		return _springEntryLocalService.getSpringEntriesCount();
	}

	/**
	 * Returns the spring entry with the primary key.
	 *
	 * @param springEntryId the primary key of the spring entry
	 * @return the spring entry
	 * @throws PortalException if a spring entry with the primary key could not be found
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.spring.sample.model.SpringEntry
				getSpringEntry(long springEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _springEntryLocalService.getSpringEntry(springEntryId);
	}

	/**
	 * Returns the spring entry with the matching UUID and company.
	 *
	 * @param uuid the spring entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching spring entry
	 * @throws PortalException if a matching spring entry could not be found
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.spring.sample.model.SpringEntry
				getSpringEntryByUuidAndCompanyId(String uuid, long companyId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _springEntryLocalService.getSpringEntryByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Updates the spring entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SpringEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param springEntry the spring entry
	 * @return the spring entry that was updated
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.spring.sample.model.SpringEntry
			updateSpringEntry(
				com.liferay.portal.tools.service.builder.spring.sample.model.
					SpringEntry springEntry) {

		return _springEntryLocalService.updateSpringEntry(springEntry);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _springEntryLocalService.getBasePersistence();
	}

	@Override
	public SpringEntryLocalService getWrappedService() {
		return _springEntryLocalService;
	}

	@Override
	public void setWrappedService(
		SpringEntryLocalService springEntryLocalService) {

		_springEntryLocalService = springEntryLocalService;
	}

	private SpringEntryLocalService _springEntryLocalService;

}