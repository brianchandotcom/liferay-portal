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

import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.tools.service.builder.test.sequence.model.SequenceEntry;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for SequenceEntry. This utility wraps
 * <code>com.liferay.portal.tools.service.builder.test.sequence.service.impl.SequenceEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see SequenceEntryLocalService
 * @generated
 */
public class SequenceEntryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.tools.service.builder.test.sequence.service.impl.SequenceEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

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
	public static SequenceEntry addSequenceEntry(SequenceEntry sequenceEntry) {
		return getService().addSequenceEntry(sequenceEntry);
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
	 * Creates a new sequence entry with the primary key. Does not add the sequence entry to the database.
	 *
	 * @param sequenceEntryId the primary key for the new sequence entry
	 * @return the new sequence entry
	 */
	public static SequenceEntry createSequenceEntry(long sequenceEntryId) {
		return getService().createSequenceEntry(sequenceEntryId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
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
	public static SequenceEntry deleteSequenceEntry(long sequenceEntryId)
		throws PortalException {

		return getService().deleteSequenceEntry(sequenceEntryId);
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
	public static SequenceEntry deleteSequenceEntry(
		SequenceEntry sequenceEntry) {

		return getService().deleteSequenceEntry(sequenceEntry);
	}

	public static <T> T dslQuery(DSLQuery dslQuery) {
		return getService().dslQuery(dslQuery);
	}

	public static int dslQueryCount(DSLQuery dslQuery) {
		return getService().dslQueryCount(dslQuery);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.sequence.model.impl.SequenceEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.tools.service.builder.test.sequence.model.impl.SequenceEntryModelImpl</code>.
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

	public static SequenceEntry fetchSequenceEntry(long sequenceEntryId) {
		return getService().fetchSequenceEntry(sequenceEntryId);
	}

	/**
	 * Returns the sequence entry with the matching UUID and company.
	 *
	 * @param uuid the sequence entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching sequence entry, or <code>null</code> if a matching sequence entry could not be found
	 */
	public static SequenceEntry fetchSequenceEntryByUuidAndCompanyId(
		String uuid, long companyId) {

		return getService().fetchSequenceEntryByUuidAndCompanyId(
			uuid, companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
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
	public static List<SequenceEntry> getSequenceEntries(int start, int end) {
		return getService().getSequenceEntries(start, end);
	}

	/**
	 * Returns the number of sequence entries.
	 *
	 * @return the number of sequence entries
	 */
	public static int getSequenceEntriesCount() {
		return getService().getSequenceEntriesCount();
	}

	/**
	 * Returns the sequence entry with the primary key.
	 *
	 * @param sequenceEntryId the primary key of the sequence entry
	 * @return the sequence entry
	 * @throws PortalException if a sequence entry with the primary key could not be found
	 */
	public static SequenceEntry getSequenceEntry(long sequenceEntryId)
		throws PortalException {

		return getService().getSequenceEntry(sequenceEntryId);
	}

	/**
	 * Returns the sequence entry with the matching UUID and company.
	 *
	 * @param uuid the sequence entry's UUID
	 * @param companyId the primary key of the company
	 * @return the matching sequence entry
	 * @throws PortalException if a matching sequence entry could not be found
	 */
	public static SequenceEntry getSequenceEntryByUuidAndCompanyId(
			String uuid, long companyId)
		throws PortalException {

		return getService().getSequenceEntryByUuidAndCompanyId(uuid, companyId);
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
	public static SequenceEntry updateSequenceEntry(
		SequenceEntry sequenceEntry) {

		return getService().updateSequenceEntry(sequenceEntry);
	}

	public static SequenceEntryLocalService getService() {
		return _service;
	}

	public static void setService(SequenceEntryLocalService service) {
		_service = service;
	}

	private static volatile SequenceEntryLocalService _service;

}