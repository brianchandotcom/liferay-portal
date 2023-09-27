/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.service;

import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.segments.model.SegmentsExperimentRel;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for SegmentsExperimentRel. This utility wraps
 * <code>com.liferay.segments.service.impl.SegmentsExperimentRelLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Eduardo Garcia
 * @see SegmentsExperimentRelLocalService
 * @generated
 */
public class SegmentsExperimentRelLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.segments.service.impl.SegmentsExperimentRelLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static SegmentsExperimentRel addSegmentsExperimentRel(
			long segmentsExperimentId, long segmentsExperienceId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addSegmentsExperimentRel(
			segmentsExperimentId, segmentsExperienceId, serviceContext);
	}

	/**
	 * Adds the segments experiment rel to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SegmentsExperimentRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param segmentsExperimentRel the segments experiment rel
	 * @return the segments experiment rel that was added
	 */
	public static SegmentsExperimentRel addSegmentsExperimentRel(
		SegmentsExperimentRel segmentsExperimentRel) {

		return getService().addSegmentsExperimentRel(segmentsExperimentRel);
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
	 * Creates a new segments experiment rel with the primary key. Does not add the segments experiment rel to the database.
	 *
	 * @param segmentsExperimentRelId the primary key for the new segments experiment rel
	 * @return the new segments experiment rel
	 */
	public static SegmentsExperimentRel createSegmentsExperimentRel(
		long segmentsExperimentRelId) {

		return getService().createSegmentsExperimentRel(
			segmentsExperimentRelId);
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
	 * Deletes the segments experiment rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SegmentsExperimentRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param segmentsExperimentRelId the primary key of the segments experiment rel
	 * @return the segments experiment rel that was removed
	 * @throws PortalException if a segments experiment rel with the primary key could not be found
	 */
	public static SegmentsExperimentRel deleteSegmentsExperimentRel(
			long segmentsExperimentRelId)
		throws PortalException {

		return getService().deleteSegmentsExperimentRel(
			segmentsExperimentRelId);
	}

	/**
	 * Deletes the segments experiment rel from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SegmentsExperimentRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param segmentsExperimentRel the segments experiment rel
	 * @return the segments experiment rel that was removed
	 * @throws PortalException
	 */
	public static SegmentsExperimentRel deleteSegmentsExperimentRel(
			SegmentsExperimentRel segmentsExperimentRel)
		throws PortalException {

		return getService().deleteSegmentsExperimentRel(segmentsExperimentRel);
	}

	public static SegmentsExperimentRel deleteSegmentsExperimentRel(
			SegmentsExperimentRel segmentsExperimentRel, boolean force)
		throws PortalException {

		return getService().deleteSegmentsExperimentRel(
			segmentsExperimentRel, force);
	}

	public static void deleteSegmentsExperimentRels(long segmentsExperimentId)
		throws PortalException {

		getService().deleteSegmentsExperimentRels(segmentsExperimentId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.segments.model.impl.SegmentsExperimentRelModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.segments.model.impl.SegmentsExperimentRelModelImpl</code>.
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

	public static SegmentsExperimentRel fetchSegmentsExperimentRel(
		long segmentsExperimentRelId) {

		return getService().fetchSegmentsExperimentRel(segmentsExperimentRelId);
	}

	public static SegmentsExperimentRel fetchSegmentsExperimentRel(
		long segmentsExperimentId, long segmentsExperienceId) {

		return getService().fetchSegmentsExperimentRel(
			segmentsExperimentId, segmentsExperienceId);
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
	 * Returns the segments experiment rel with the primary key.
	 *
	 * @param segmentsExperimentRelId the primary key of the segments experiment rel
	 * @return the segments experiment rel
	 * @throws PortalException if a segments experiment rel with the primary key could not be found
	 */
	public static SegmentsExperimentRel getSegmentsExperimentRel(
			long segmentsExperimentRelId)
		throws PortalException {

		return getService().getSegmentsExperimentRel(segmentsExperimentRelId);
	}

	public static SegmentsExperimentRel getSegmentsExperimentRel(
			long segmentsExperimentId, long segmentsExperienceId)
		throws PortalException {

		return getService().getSegmentsExperimentRel(
			segmentsExperimentId, segmentsExperienceId);
	}

	/**
	 * Returns a range of all the segments experiment rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.segments.model.impl.SegmentsExperimentRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of segments experiment rels
	 * @param end the upper bound of the range of segments experiment rels (not inclusive)
	 * @return the range of segments experiment rels
	 */
	public static List<SegmentsExperimentRel> getSegmentsExperimentRels(
		int start, int end) {

		return getService().getSegmentsExperimentRels(start, end);
	}

	public static List<SegmentsExperimentRel> getSegmentsExperimentRels(
		long segmentsExperimentId) {

		return getService().getSegmentsExperimentRels(segmentsExperimentId);
	}

	public static List<SegmentsExperimentRel>
		getSegmentsExperimentRelsBySegmentsExperienceId(
			long segmentsExperienceId) {

		return getService().getSegmentsExperimentRelsBySegmentsExperienceId(
			segmentsExperienceId);
	}

	/**
	 * Returns the number of segments experiment rels.
	 *
	 * @return the number of segments experiment rels
	 */
	public static int getSegmentsExperimentRelsCount() {
		return getService().getSegmentsExperimentRelsCount();
	}

	public static SegmentsExperimentRel updateSegmentsExperimentRel(
			long segmentsExperimentRelId, double split)
		throws PortalException {

		return getService().updateSegmentsExperimentRel(
			segmentsExperimentRelId, split);
	}

	public static SegmentsExperimentRel updateSegmentsExperimentRel(
			long segmentsExperimentId, long segmentsExperienceId, double split)
		throws PortalException {

		return getService().updateSegmentsExperimentRel(
			segmentsExperimentId, segmentsExperienceId, split);
	}

	public static SegmentsExperimentRel updateSegmentsExperimentRel(
			long segmentsExperimentRelId, String name,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateSegmentsExperimentRel(
			segmentsExperimentRelId, name, serviceContext);
	}

	/**
	 * Updates the segments experiment rel in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SegmentsExperimentRelLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param segmentsExperimentRel the segments experiment rel
	 * @return the segments experiment rel that was updated
	 */
	public static SegmentsExperimentRel updateSegmentsExperimentRel(
		SegmentsExperimentRel segmentsExperimentRel) {

		return getService().updateSegmentsExperimentRel(segmentsExperimentRel);
	}

	public static SegmentsExperimentRelLocalService getService() {
		return _service;
	}

	public static void setService(SegmentsExperimentRelLocalService service) {
		_service = service;
	}

	private static volatile SegmentsExperimentRelLocalService _service;

}