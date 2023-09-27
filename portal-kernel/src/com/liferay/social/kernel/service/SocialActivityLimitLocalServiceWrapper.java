/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.social.kernel.service;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;
import com.liferay.social.kernel.model.SocialActivityLimit;

/**
 * Provides a wrapper for {@link SocialActivityLimitLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivityLimitLocalService
 * @generated
 */
public class SocialActivityLimitLocalServiceWrapper
	implements ServiceWrapper<SocialActivityLimitLocalService>,
			   SocialActivityLimitLocalService {

	public SocialActivityLimitLocalServiceWrapper() {
		this(null);
	}

	public SocialActivityLimitLocalServiceWrapper(
		SocialActivityLimitLocalService socialActivityLimitLocalService) {

		_socialActivityLimitLocalService = socialActivityLimitLocalService;
	}

	@Override
	public SocialActivityLimit addActivityLimit(
			long userId, long groupId, long classNameId, long classPK,
			int activityType, String activityCounterName, int limitPeriod)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _socialActivityLimitLocalService.addActivityLimit(
			userId, groupId, classNameId, classPK, activityType,
			activityCounterName, limitPeriod);
	}

	/**
	 * Adds the social activity limit to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SocialActivityLimitLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param socialActivityLimit the social activity limit
	 * @return the social activity limit that was added
	 */
	@Override
	public SocialActivityLimit addSocialActivityLimit(
		SocialActivityLimit socialActivityLimit) {

		return _socialActivityLimitLocalService.addSocialActivityLimit(
			socialActivityLimit);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _socialActivityLimitLocalService.createPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Creates a new social activity limit with the primary key. Does not add the social activity limit to the database.
	 *
	 * @param activityLimitId the primary key for the new social activity limit
	 * @return the new social activity limit
	 */
	@Override
	public SocialActivityLimit createSocialActivityLimit(long activityLimitId) {
		return _socialActivityLimitLocalService.createSocialActivityLimit(
			activityLimitId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _socialActivityLimitLocalService.deletePersistedModel(
			persistedModel);
	}

	/**
	 * Deletes the social activity limit with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SocialActivityLimitLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param activityLimitId the primary key of the social activity limit
	 * @return the social activity limit that was removed
	 * @throws PortalException if a social activity limit with the primary key could not be found
	 */
	@Override
	public SocialActivityLimit deleteSocialActivityLimit(long activityLimitId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _socialActivityLimitLocalService.deleteSocialActivityLimit(
			activityLimitId);
	}

	/**
	 * Deletes the social activity limit from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SocialActivityLimitLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param socialActivityLimit the social activity limit
	 * @return the social activity limit that was removed
	 */
	@Override
	public SocialActivityLimit deleteSocialActivityLimit(
		SocialActivityLimit socialActivityLimit) {

		return _socialActivityLimitLocalService.deleteSocialActivityLimit(
			socialActivityLimit);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _socialActivityLimitLocalService.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(
		com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {

		return _socialActivityLimitLocalService.dslQueryCount(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _socialActivityLimitLocalService.dynamicQuery();
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

		return _socialActivityLimitLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portlet.social.model.impl.SocialActivityLimitModelImpl</code>.
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

		return _socialActivityLimitLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portlet.social.model.impl.SocialActivityLimitModelImpl</code>.
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

		return _socialActivityLimitLocalService.dynamicQuery(
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

		return _socialActivityLimitLocalService.dynamicQueryCount(dynamicQuery);
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

		return _socialActivityLimitLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public SocialActivityLimit fetchActivityLimit(
		long groupId, long userId, long classNameId, long classPK,
		int activityType, String activityCounterName) {

		return _socialActivityLimitLocalService.fetchActivityLimit(
			groupId, userId, classNameId, classPK, activityType,
			activityCounterName);
	}

	@Override
	public SocialActivityLimit fetchSocialActivityLimit(long activityLimitId) {
		return _socialActivityLimitLocalService.fetchSocialActivityLimit(
			activityLimitId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _socialActivityLimitLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _socialActivityLimitLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _socialActivityLimitLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _socialActivityLimitLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Returns the social activity limit with the primary key.
	 *
	 * @param activityLimitId the primary key of the social activity limit
	 * @return the social activity limit
	 * @throws PortalException if a social activity limit with the primary key could not be found
	 */
	@Override
	public SocialActivityLimit getSocialActivityLimit(long activityLimitId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _socialActivityLimitLocalService.getSocialActivityLimit(
			activityLimitId);
	}

	/**
	 * Returns a range of all the social activity limits.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portlet.social.model.impl.SocialActivityLimitModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of social activity limits
	 * @param end the upper bound of the range of social activity limits (not inclusive)
	 * @return the range of social activity limits
	 */
	@Override
	public java.util.List<SocialActivityLimit> getSocialActivityLimits(
		int start, int end) {

		return _socialActivityLimitLocalService.getSocialActivityLimits(
			start, end);
	}

	/**
	 * Returns the number of social activity limits.
	 *
	 * @return the number of social activity limits
	 */
	@Override
	public int getSocialActivityLimitsCount() {
		return _socialActivityLimitLocalService.getSocialActivityLimitsCount();
	}

	/**
	 * Updates the social activity limit in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SocialActivityLimitLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param socialActivityLimit the social activity limit
	 * @return the social activity limit that was updated
	 */
	@Override
	public SocialActivityLimit updateSocialActivityLimit(
		SocialActivityLimit socialActivityLimit) {

		return _socialActivityLimitLocalService.updateSocialActivityLimit(
			socialActivityLimit);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _socialActivityLimitLocalService.getBasePersistence();
	}

	@Override
	public CTPersistence<SocialActivityLimit> getCTPersistence() {
		return _socialActivityLimitLocalService.getCTPersistence();
	}

	@Override
	public Class<SocialActivityLimit> getModelClass() {
		return _socialActivityLimitLocalService.getModelClass();
	}

	@Override
	public <R, E extends Throwable> R updateWithUnsafeFunction(
			UnsafeFunction<CTPersistence<SocialActivityLimit>, R, E>
				updateUnsafeFunction)
		throws E {

		return _socialActivityLimitLocalService.updateWithUnsafeFunction(
			updateUnsafeFunction);
	}

	@Override
	public SocialActivityLimitLocalService getWrappedService() {
		return _socialActivityLimitLocalService;
	}

	@Override
	public void setWrappedService(
		SocialActivityLimitLocalService socialActivityLimitLocalService) {

		_socialActivityLimitLocalService = socialActivityLimitLocalService;
	}

	private SocialActivityLimitLocalService _socialActivityLimitLocalService;

}