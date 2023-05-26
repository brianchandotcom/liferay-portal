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

package com.liferay.portal.tools.service.builder.test.sequence.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Provides a wrapper for {@link SequenceEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see SequenceEntryLocalService
 * @generated
 */
public class SequenceEntryLocalServiceWrapper
	implements SequenceEntryLocalService,
			   ServiceWrapper<SequenceEntryLocalService> {

	public SequenceEntryLocalServiceWrapper() {
		this(null);
	}

	public SequenceEntryLocalServiceWrapper(
		SequenceEntryLocalService sequenceEntryLocalService) {

		_sequenceEntryLocalService = sequenceEntryLocalService;
	}

	/**
	 * Adds the sequence entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SequenceEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param sequenceEntry the sequence entry
	 * @return the sequence entry that was added
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.sequence.model.
			SequenceEntry addSequenceEntry(
				com.liferay.portal.tools.service.builder.test.sequence.model.
					SequenceEntry sequenceEntry) {

		return _sequenceEntryLocalService.addSequenceEntry(sequenceEntry);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _sequenceEntryLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Creates a new sequence entry with the primary key. Does not add the sequence entry to the database.
	 *
	 * @param sequenceEntryId the primary key for the new sequence entry
	 * @return the new sequence entry
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.sequence.model.
			SequenceEntry createSequenceEntry(long sequenceEntryId) {

		return _sequenceEntryLocalService.createSequenceEntry(sequenceEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _sequenceEntryLocalService.deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the sequence entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SequenceEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param sequenceEntryId the primary key of the sequence entry
	 * @return the sequence entry that was removed
	 * @throws PortalException if a sequence entry with the primary key could not be found
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.sequence.model.
			SequenceEntry deleteSequenceEntry(long sequenceEntryId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _sequenceEntryLocalService.deleteSequenceEntry(sequenceEntryId);
	}

	/**
	 * Deletes the sequence entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SequenceEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param sequenceEntry the sequence entry
	 * @return the sequence entry that was removed
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.sequence.model.
			SequenceEntry deleteSequenceEntry(
				com.liferay.portal.tools.service.builder.test.sequence.model.
					SequenceEntry sequenceEntry) {

		return _sequenceEntryLocalService.deleteSequenceEntry(sequenceEntry);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _sequenceEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _sequenceEntryLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _sequenceEntryLocalService.dynamicQuery();
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

		return _sequenceEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.sequence.model.impl.SequenceEntryModelImpl</code>.
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

		return _sequenceEntryLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.sequence.model.impl.SequenceEntryModelImpl</code>.
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

		return _sequenceEntryLocalService.dynamicQuery(
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

		return _sequenceEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _sequenceEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public
		com.liferay.portal.tools.service.builder.test.sequence.model.
			SequenceEntry fetchSequenceEntry(long sequenceEntryId) {

		return _sequenceEntryLocalService.fetchSequenceEntry(sequenceEntryId);
	}

	/**
	 * Returns the sequence entry with the matching UUID and company.
	 *
	 * @param uuid the sequence entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching sequence entry, or <code>null</code> if a matching sequence entry could not be found
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.sequence.model.
			SequenceEntry fetchSequenceEntryByUuidAndCompanyId(
				String uuid, long companyId) {

		return _sequenceEntryLocalService.fetchSequenceEntryByUuidAndCompanyId(
			uuid, companyId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _sequenceEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _sequenceEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _sequenceEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _sequenceEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns a range of all the sequence entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.sequence.model.impl.SequenceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of sequence entries
	 * @param end the upper bound of the range of sequence entries (not inclusive)
	 * @return the range of sequence entries
	 */
	@Override
	public java.util.List
		<com.liferay.portal.tools.service.builder.test.sequence.model.
			SequenceEntry> getSequenceEntries(int start, int end) {

		return _sequenceEntryLocalService.getSequenceEntries(start, end);
	}

	/**
	 * Returns the number of sequence entries.
	 *
	 * @return the number of sequence entries
	 */
	@Override
	public int getSequenceEntriesCount() {
		return _sequenceEntryLocalService.getSequenceEntriesCount();
	}

	/**
	 * Returns the sequence entry with the primary key.
	 *
	 * @param sequenceEntryId the primary key of the sequence entry
	 * @return the sequence entry
	 * @throws PortalException if a sequence entry with the primary key could not be found
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.sequence.model.
			SequenceEntry getSequenceEntry(long sequenceEntryId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _sequenceEntryLocalService.getSequenceEntry(sequenceEntryId);
	}

	/**
	 * Returns the sequence entry with the matching UUID and company.
	 *
	 * @param uuid the sequence entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching sequence entry
	 * @throws PortalException if a matching sequence entry could not be found
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.sequence.model.
			SequenceEntry getSequenceEntryByUuidAndCompanyId(
					String uuid, long companyId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _sequenceEntryLocalService.getSequenceEntryByUuidAndCompanyId(
			uuid, companyId);
	}

	/**
	 * Updates the sequence entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SequenceEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param sequenceEntry the sequence entry
	 * @return the sequence entry that was updated
	 */
	@Override
	public
		com.liferay.portal.tools.service.builder.test.sequence.model.
			SequenceEntry updateSequenceEntry(
				com.liferay.portal.tools.service.builder.test.sequence.model.
					SequenceEntry sequenceEntry) {

		return _sequenceEntryLocalService.updateSequenceEntry(sequenceEntry);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _sequenceEntryLocalService.getBasePersistence();
	}

	@Override
	public SequenceEntryLocalService getWrappedService() {
		return _sequenceEntryLocalService;
	}

	@Override
	public void setWrappedService(
		SequenceEntryLocalService sequenceEntryLocalService) {

		_sequenceEntryLocalService = sequenceEntryLocalService;
	}

	private SequenceEntryLocalService _sequenceEntryLocalService;

}