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

package com.liferay.osb.faro.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link FaroProjectEmailAddressDomainLocalService}.
 *
 * @author Matthew Kong
 * @see FaroProjectEmailAddressDomainLocalService
 * @generated
 */
public class FaroProjectEmailAddressDomainLocalServiceWrapper
	implements FaroProjectEmailAddressDomainLocalService,
			   ServiceWrapper<FaroProjectEmailAddressDomainLocalService> {

	public FaroProjectEmailAddressDomainLocalServiceWrapper(
		FaroProjectEmailAddressDomainLocalService
			faroProjectEmailAddressDomainLocalService) {

		_faroProjectEmailAddressDomainLocalService =
			faroProjectEmailAddressDomainLocalService;
	}

	/**
	 * Adds the faro project email address domain to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FaroProjectEmailAddressDomainLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param faroProjectEmailAddressDomain the faro project email address domain
	 * @return the faro project email address domain that was added
	 */
	@Override
	public com.liferay.osb.faro.model.FaroProjectEmailAddressDomain
		addFaroProjectEmailAddressDomain(
			com.liferay.osb.faro.model.FaroProjectEmailAddressDomain
				faroProjectEmailAddressDomain) {

		return _faroProjectEmailAddressDomainLocalService.
			addFaroProjectEmailAddressDomain(faroProjectEmailAddressDomain);
	}

	@Override
	public com.liferay.osb.faro.model.FaroProjectEmailAddressDomain
		addFaroProjectEmailAddressDomain(
			long groupId, long faroProjectId, String emailDomain) {

		return _faroProjectEmailAddressDomainLocalService.
			addFaroProjectEmailAddressDomain(
				groupId, faroProjectId, emailDomain);
	}

	@Override
	public void addFaroProjectEmailAddressDomains(
		long groupId, long faroProjectId,
		java.util.List<String> emailAddressDomains) {

		_faroProjectEmailAddressDomainLocalService.
			addFaroProjectEmailAddressDomains(
				groupId, faroProjectId, emailAddressDomains);
	}

	/**
	 * Creates a new faro project email address domain with the primary key. Does not add the faro project email address domain to the database.
	 *
	 * @param faroProjectEmailAddressDomainId the primary key for the new faro project email address domain
	 * @return the new faro project email address domain
	 */
	@Override
	public com.liferay.osb.faro.model.FaroProjectEmailAddressDomain
		createFaroProjectEmailAddressDomain(
			long faroProjectEmailAddressDomainId) {

		return _faroProjectEmailAddressDomainLocalService.
			createFaroProjectEmailAddressDomain(
				faroProjectEmailAddressDomainId);
	}

	/**
	 * Deletes the faro project email address domain from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FaroProjectEmailAddressDomainLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param faroProjectEmailAddressDomain the faro project email address domain
	 * @return the faro project email address domain that was removed
	 */
	@Override
	public com.liferay.osb.faro.model.FaroProjectEmailAddressDomain
		deleteFaroProjectEmailAddressDomain(
			com.liferay.osb.faro.model.FaroProjectEmailAddressDomain
				faroProjectEmailAddressDomain) {

		return _faroProjectEmailAddressDomainLocalService.
			deleteFaroProjectEmailAddressDomain(faroProjectEmailAddressDomain);
	}

	/**
	 * Deletes the faro project email address domain with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FaroProjectEmailAddressDomainLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param faroProjectEmailAddressDomainId the primary key of the faro project email address domain
	 * @return the faro project email address domain that was removed
	 * @throws PortalException if a faro project email address domain with the primary key could not be found
	 */
	@Override
	public com.liferay.osb.faro.model.FaroProjectEmailAddressDomain
			deleteFaroProjectEmailAddressDomain(
				long faroProjectEmailAddressDomainId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _faroProjectEmailAddressDomainLocalService.
			deleteFaroProjectEmailAddressDomain(
				faroProjectEmailAddressDomainId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _faroProjectEmailAddressDomainLocalService.deletePersistedModel(
			persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _faroProjectEmailAddressDomainLocalService.dynamicQuery();
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

		return _faroProjectEmailAddressDomainLocalService.dynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.osb.faro.model.impl.FaroProjectEmailAddressDomainModelImpl</code>.
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

		return _faroProjectEmailAddressDomainLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.osb.faro.model.impl.FaroProjectEmailAddressDomainModelImpl</code>.
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

		return _faroProjectEmailAddressDomainLocalService.dynamicQuery(
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

		return _faroProjectEmailAddressDomainLocalService.dynamicQueryCount(
			dynamicQuery);
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

		return _faroProjectEmailAddressDomainLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.osb.faro.model.FaroProjectEmailAddressDomain
		fetchFaroProjectEmailAddressDomain(
			long faroProjectEmailAddressDomainId) {

		return _faroProjectEmailAddressDomainLocalService.
			fetchFaroProjectEmailAddressDomain(faroProjectEmailAddressDomainId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _faroProjectEmailAddressDomainLocalService.
			getActionableDynamicQuery();
	}

	/**
	 * Returns the faro project email address domain with the primary key.
	 *
	 * @param faroProjectEmailAddressDomainId the primary key of the faro project email address domain
	 * @return the faro project email address domain
	 * @throws PortalException if a faro project email address domain with the primary key could not be found
	 */
	@Override
	public com.liferay.osb.faro.model.FaroProjectEmailAddressDomain
			getFaroProjectEmailAddressDomain(
				long faroProjectEmailAddressDomainId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _faroProjectEmailAddressDomainLocalService.
			getFaroProjectEmailAddressDomain(faroProjectEmailAddressDomainId);
	}

	/**
	 * Returns a range of all the faro project email address domains.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.osb.faro.model.impl.FaroProjectEmailAddressDomainModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of faro project email address domains
	 * @param end the upper bound of the range of faro project email address domains (not inclusive)
	 * @return the range of faro project email address domains
	 */
	@Override
	public java.util.List
		<com.liferay.osb.faro.model.FaroProjectEmailAddressDomain>
			getFaroProjectEmailAddressDomains(int start, int end) {

		return _faroProjectEmailAddressDomainLocalService.
			getFaroProjectEmailAddressDomains(start, end);
	}

	@Override
	public java.util.List
		<com.liferay.osb.faro.model.FaroProjectEmailAddressDomain>
			getFaroProjectEmailAddressDomainsByFaroProjectId(
				long faroProjectId) {

		return _faroProjectEmailAddressDomainLocalService.
			getFaroProjectEmailAddressDomainsByFaroProjectId(faroProjectId);
	}

	@Override
	public java.util.List
		<com.liferay.osb.faro.model.FaroProjectEmailAddressDomain>
			getFaroProjectEmailAddressDomainsByGroupId(long groupId) {

		return _faroProjectEmailAddressDomainLocalService.
			getFaroProjectEmailAddressDomainsByGroupId(groupId);
	}

	/**
	 * Returns the number of faro project email address domains.
	 *
	 * @return the number of faro project email address domains
	 */
	@Override
	public int getFaroProjectEmailAddressDomainsCount() {
		return _faroProjectEmailAddressDomainLocalService.
			getFaroProjectEmailAddressDomainsCount();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _faroProjectEmailAddressDomainLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _faroProjectEmailAddressDomainLocalService.
			getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _faroProjectEmailAddressDomainLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Updates the faro project email address domain in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FaroProjectEmailAddressDomainLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param faroProjectEmailAddressDomain the faro project email address domain
	 * @return the faro project email address domain that was updated
	 */
	@Override
	public com.liferay.osb.faro.model.FaroProjectEmailAddressDomain
		updateFaroProjectEmailAddressDomain(
			com.liferay.osb.faro.model.FaroProjectEmailAddressDomain
				faroProjectEmailAddressDomain) {

		return _faroProjectEmailAddressDomainLocalService.
			updateFaroProjectEmailAddressDomain(faroProjectEmailAddressDomain);
	}

	@Override
	public FaroProjectEmailAddressDomainLocalService getWrappedService() {
		return _faroProjectEmailAddressDomainLocalService;
	}

	@Override
	public void setWrappedService(
		FaroProjectEmailAddressDomainLocalService
			faroProjectEmailAddressDomainLocalService) {

		_faroProjectEmailAddressDomainLocalService =
			faroProjectEmailAddressDomainLocalService;
	}

	private FaroProjectEmailAddressDomainLocalService
		_faroProjectEmailAddressDomainLocalService;

}