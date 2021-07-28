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

package com.liferay.search.tuning.synonyms.service;

import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.search.tuning.synonyms.model.STSynonymsEntry;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for STSynonymsEntry. This utility wraps
 * <code>com.liferay.search.tuning.synonyms.service.impl.STSynonymsEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Bryan Engler
 * @see STSynonymsEntryLocalService
 * @generated
 */
public class STSynonymsEntryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.search.tuning.synonyms.service.impl.STSynonymsEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static STSynonymsEntry addSTSynonymsEntry(
		String indexName, String synonyms) {

		return getService().addSTSynonymsEntry(indexName, synonyms);
	}

	/**
	 * Adds the st synonyms entry to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect STSynonymsEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param stSynonymsEntry the st synonyms entry
	 * @return the st synonyms entry that was added
	 */
	public static STSynonymsEntry addSTSynonymsEntry(
		STSynonymsEntry stSynonymsEntry) {

		return getService().addSTSynonymsEntry(stSynonymsEntry);
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
	 * Creates a new st synonyms entry with the primary key. Does not add the st synonyms entry to the database.
	 *
	 * @param STSynonymsEntryId the primary key for the new st synonyms entry
	 * @return the new st synonyms entry
	 */
	public static STSynonymsEntry createSTSynonymsEntry(
		long STSynonymsEntryId) {

		return getService().createSTSynonymsEntry(STSynonymsEntryId);
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
	 * Deletes the st synonyms entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect STSynonymsEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param STSynonymsEntryId the primary key of the st synonyms entry
	 * @return the st synonyms entry that was removed
	 * @throws PortalException if a st synonyms entry with the primary key could not be found
	 */
	public static STSynonymsEntry deleteSTSynonymsEntry(long STSynonymsEntryId)
		throws PortalException {

		return getService().deleteSTSynonymsEntry(STSynonymsEntryId);
	}

	/**
	 * Deletes the st synonyms entry from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect STSynonymsEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param stSynonymsEntry the st synonyms entry
	 * @return the st synonyms entry that was removed
	 */
	public static STSynonymsEntry deleteSTSynonymsEntry(
		STSynonymsEntry stSynonymsEntry) {

		return getService().deleteSTSynonymsEntry(stSynonymsEntry);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.search.tuning.synonyms.model.impl.STSynonymsEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.search.tuning.synonyms.model.impl.STSynonymsEntryModelImpl</code>.
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

	public static STSynonymsEntry fetchSTSynonymsEntry(long STSynonymsEntryId) {
		return getService().fetchSTSynonymsEntry(STSynonymsEntryId);
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
	 * Returns a range of all the st synonyms entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.search.tuning.synonyms.model.impl.STSynonymsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of st synonyms entries
	 * @param end the upper bound of the range of st synonyms entries (not inclusive)
	 * @return the range of st synonyms entries
	 */
	public static List<STSynonymsEntry> getSTSynonymsEntries(
		int start, int end) {

		return getService().getSTSynonymsEntries(start, end);
	}

	public static List<STSynonymsEntry> getSTSynonymsEntriesByCompanyId(
		long companyId) {

		return getService().getSTSynonymsEntriesByCompanyId(companyId);
	}

	/**
	 * Returns the number of st synonyms entries.
	 *
	 * @return the number of st synonyms entries
	 */
	public static int getSTSynonymsEntriesCount() {
		return getService().getSTSynonymsEntriesCount();
	}

	/**
	 * Returns the st synonyms entry with the primary key.
	 *
	 * @param STSynonymsEntryId the primary key of the st synonyms entry
	 * @return the st synonyms entry
	 * @throws PortalException if a st synonyms entry with the primary key could not be found
	 */
	public static STSynonymsEntry getSTSynonymsEntry(long STSynonymsEntryId)
		throws PortalException {

		return getService().getSTSynonymsEntry(STSynonymsEntryId);
	}

	public static STSynonymsEntry updateSTSynonymsEntry(
			long stSynonymsEntryId, String synonyms)
		throws PortalException {

		return getService().updateSTSynonymsEntry(stSynonymsEntryId, synonyms);
	}

	/**
	 * Updates the st synonyms entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect STSynonymsEntryLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param stSynonymsEntry the st synonyms entry
	 * @return the st synonyms entry that was updated
	 */
	public static STSynonymsEntry updateSTSynonymsEntry(
		STSynonymsEntry stSynonymsEntry) {

		return getService().updateSTSynonymsEntry(stSynonymsEntry);
	}

	public static STSynonymsEntryLocalService getService() {
		return _service;
	}

	private static volatile STSynonymsEntryLocalService _service;

}