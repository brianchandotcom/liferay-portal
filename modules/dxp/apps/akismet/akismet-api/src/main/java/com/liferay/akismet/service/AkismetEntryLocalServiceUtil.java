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

package com.liferay.akismet.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for AkismetEntry. This utility wraps
 * <code>com.liferay.akismet.service.impl.AkismetEntryLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Jamie Sammons
 * @see AkismetEntryLocalService
 * @generated
 */
public class AkismetEntryLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.akismet.service.impl.AkismetEntryLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the akismet entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param akismetEntry the akismet entry
	 * @return the akismet entry that was added
	 */
	public static com.liferay.akismet.model.AkismetEntry addAkismetEntry(
		com.liferay.akismet.model.AkismetEntry akismetEntry) {

		return getService().addAkismetEntry(akismetEntry);
	}

	/**
	 * Creates a new akismet entry with the primary key. Does not add the akismet entry to the database.
	 *
	 * @param akismetEntryId the primary key for the new akismet entry
	 * @return the new akismet entry
	 */
	public static com.liferay.akismet.model.AkismetEntry createAkismetEntry(
		long akismetEntryId) {

		return getService().createAkismetEntry(akismetEntryId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			createPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the akismet entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param akismetEntry the akismet entry
	 * @return the akismet entry that was removed
	 */
	public static com.liferay.akismet.model.AkismetEntry deleteAkismetEntry(
		com.liferay.akismet.model.AkismetEntry akismetEntry) {

		return getService().deleteAkismetEntry(akismetEntry);
	}

	public static void deleteAkismetEntry(java.util.Date modifiedDate) {
		getService().deleteAkismetEntry(modifiedDate);
	}

	/**
	 * Deletes the akismet entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param akismetEntryId the primary key of the akismet entry
	 * @return the akismet entry that was removed
	 * @throws PortalException if a akismet entry with the primary key could not be found
	 */
	public static com.liferay.akismet.model.AkismetEntry deleteAkismetEntry(
			long akismetEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteAkismetEntry(akismetEntryId);
	}

	public static void deleteAkismetEntry(String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteAkismetEntry(className, classPK);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	public static <T> T dslQuery(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return getService().dslQuery(dslQuery);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.akismet.model.impl.AkismetEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.akismet.model.impl.AkismetEntryModelImpl</code>.
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

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
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

	public static com.liferay.akismet.model.AkismetEntry fetchAkismetEntry(
		long akismetEntryId) {

		return getService().fetchAkismetEntry(akismetEntryId);
	}

	public static com.liferay.akismet.model.AkismetEntry fetchAkismetEntry(
		String className, long classPK) {

		return getService().fetchAkismetEntry(className, classPK);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns a range of all the akismet entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.akismet.model.impl.AkismetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of akismet entries
	 * @param end the upper bound of the range of akismet entries (not inclusive)
	 * @return the range of akismet entries
	 */
	public static java.util.List<com.liferay.akismet.model.AkismetEntry>
		getAkismetEntries(int start, int end) {

		return getService().getAkismetEntries(start, end);
	}

	/**
	 * Returns the number of akismet entries.
	 *
	 * @return the number of akismet entries
	 */
	public static int getAkismetEntriesCount() {
		return getService().getAkismetEntriesCount();
	}

	/**
	 * Returns the akismet entry with the primary key.
	 *
	 * @param akismetEntryId the primary key of the akismet entry
	 * @return the akismet entry
	 * @throws PortalException if a akismet entry with the primary key could not be found
	 */
	public static com.liferay.akismet.model.AkismetEntry getAkismetEntry(
			long akismetEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getAkismetEntry(akismetEntryId);
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
	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the akismet entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param akismetEntry the akismet entry
	 * @return the akismet entry that was updated
	 */
	public static com.liferay.akismet.model.AkismetEntry updateAkismetEntry(
		com.liferay.akismet.model.AkismetEntry akismetEntry) {

		return getService().updateAkismetEntry(akismetEntry);
	}

	public static com.liferay.akismet.model.AkismetEntry updateAkismetEntry(
		String className, long classPK, String type, String permalink,
		String referrer, String userAgent, String userIP, String userURL) {

		return getService().updateAkismetEntry(
			className, classPK, type, permalink, referrer, userAgent, userIP,
			userURL);
	}

	public static AkismetEntryLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<AkismetEntryLocalService, AkismetEntryLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(AkismetEntryLocalService.class);

		ServiceTracker<AkismetEntryLocalService, AkismetEntryLocalService>
			serviceTracker =
				new ServiceTracker
					<AkismetEntryLocalService, AkismetEntryLocalService>(
						bundle.getBundleContext(),
						AkismetEntryLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}