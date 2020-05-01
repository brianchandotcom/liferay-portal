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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AkismetEntryLocalService}.
 *
 * @author Jamie Sammons
 * @see AkismetEntryLocalService
 * @generated
 */
public class AkismetEntryLocalServiceWrapper
	implements AkismetEntryLocalService,
			   ServiceWrapper<AkismetEntryLocalService> {

	public AkismetEntryLocalServiceWrapper(
		AkismetEntryLocalService akismetEntryLocalService) {

		_akismetEntryLocalService = akismetEntryLocalService;
	}

	/**
	 * Adds the akismet entry to the database. Also notifies the appropriate model listeners.
	 *
	 * @param akismetEntry the akismet entry
	 * @return the akismet entry that was added
	 */
	@Override
	public com.liferay.akismet.model.AkismetEntry addAkismetEntry(
		com.liferay.akismet.model.AkismetEntry akismetEntry) {

		return _akismetEntryLocalService.addAkismetEntry(akismetEntry);
	}

	/**
	 * Creates a new akismet entry with the primary key. Does not add the akismet entry to the database.
	 *
	 * @param akismetEntryId the primary key for the new akismet entry
	 * @return the new akismet entry
	 */
	@Override
	public com.liferay.akismet.model.AkismetEntry createAkismetEntry(
		long akismetEntryId) {

		return _akismetEntryLocalService.createAkismetEntry(akismetEntryId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel createPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _akismetEntryLocalService.createPersistedModel(primaryKeyObj);
	}

	/**
	 * Deletes the akismet entry from the database. Also notifies the appropriate model listeners.
	 *
	 * @param akismetEntry the akismet entry
	 * @return the akismet entry that was removed
	 */
	@Override
	public com.liferay.akismet.model.AkismetEntry deleteAkismetEntry(
		com.liferay.akismet.model.AkismetEntry akismetEntry) {

		return _akismetEntryLocalService.deleteAkismetEntry(akismetEntry);
	}

	@Override
	public void deleteAkismetEntry(java.util.Date modifiedDate) {
		_akismetEntryLocalService.deleteAkismetEntry(modifiedDate);
	}

	/**
	 * Deletes the akismet entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param akismetEntryId the primary key of the akismet entry
	 * @return the akismet entry that was removed
	 * @throws PortalException if a akismet entry with the primary key could not be found
	 */
	@Override
	public com.liferay.akismet.model.AkismetEntry deleteAkismetEntry(
			long akismetEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _akismetEntryLocalService.deleteAkismetEntry(akismetEntryId);
	}

	@Override
	public void deleteAkismetEntry(String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		_akismetEntryLocalService.deleteAkismetEntry(className, classPK);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _akismetEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public <T> T dslQuery(com.liferay.petra.sql.dsl.query.DSLQuery dslQuery) {
		return _akismetEntryLocalService.dslQuery(dslQuery);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _akismetEntryLocalService.dynamicQuery();
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

		return _akismetEntryLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _akismetEntryLocalService.dynamicQuery(dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _akismetEntryLocalService.dynamicQuery(
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

		return _akismetEntryLocalService.dynamicQueryCount(dynamicQuery);
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

		return _akismetEntryLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.akismet.model.AkismetEntry fetchAkismetEntry(
		long akismetEntryId) {

		return _akismetEntryLocalService.fetchAkismetEntry(akismetEntryId);
	}

	@Override
	public com.liferay.akismet.model.AkismetEntry fetchAkismetEntry(
		String className, long classPK) {

		return _akismetEntryLocalService.fetchAkismetEntry(className, classPK);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _akismetEntryLocalService.getActionableDynamicQuery();
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
	@Override
	public java.util.List<com.liferay.akismet.model.AkismetEntry>
		getAkismetEntries(int start, int end) {

		return _akismetEntryLocalService.getAkismetEntries(start, end);
	}

	/**
	 * Returns the number of akismet entries.
	 *
	 * @return the number of akismet entries
	 */
	@Override
	public int getAkismetEntriesCount() {
		return _akismetEntryLocalService.getAkismetEntriesCount();
	}

	/**
	 * Returns the akismet entry with the primary key.
	 *
	 * @param akismetEntryId the primary key of the akismet entry
	 * @return the akismet entry
	 * @throws PortalException if a akismet entry with the primary key could not be found
	 */
	@Override
	public com.liferay.akismet.model.AkismetEntry getAkismetEntry(
			long akismetEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _akismetEntryLocalService.getAkismetEntry(akismetEntryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _akismetEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _akismetEntryLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _akismetEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Updates the akismet entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param akismetEntry the akismet entry
	 * @return the akismet entry that was updated
	 */
	@Override
	public com.liferay.akismet.model.AkismetEntry updateAkismetEntry(
		com.liferay.akismet.model.AkismetEntry akismetEntry) {

		return _akismetEntryLocalService.updateAkismetEntry(akismetEntry);
	}

	@Override
	public com.liferay.akismet.model.AkismetEntry updateAkismetEntry(
		String className, long classPK, String type, String permalink,
		String referrer, String userAgent, String userIP, String userURL) {

		return _akismetEntryLocalService.updateAkismetEntry(
			className, classPK, type, permalink, referrer, userAgent, userIP,
			userURL);
	}

	@Override
	public AkismetEntryLocalService getWrappedService() {
		return _akismetEntryLocalService;
	}

	@Override
	public void setWrappedService(
		AkismetEntryLocalService akismetEntryLocalService) {

		_akismetEntryLocalService = akismetEntryLocalService;
	}

	private AkismetEntryLocalService _akismetEntryLocalService;

}