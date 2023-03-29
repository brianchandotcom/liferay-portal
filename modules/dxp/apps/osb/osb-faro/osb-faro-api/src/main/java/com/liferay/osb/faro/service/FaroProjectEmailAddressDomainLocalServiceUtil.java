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

import com.liferay.osb.faro.model.FaroProjectEmailAddressDomain;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service utility for FaroProjectEmailAddressDomain. This utility wraps
 * <code>com.liferay.osb.faro.service.impl.FaroProjectEmailAddressDomainLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Matthew Kong
 * @see FaroProjectEmailAddressDomainLocalService
 * @generated
 */
public class FaroProjectEmailAddressDomainLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.osb.faro.service.impl.FaroProjectEmailAddressDomainLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

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
	public static FaroProjectEmailAddressDomain
		addFaroProjectEmailAddressDomain(
			FaroProjectEmailAddressDomain faroProjectEmailAddressDomain) {

		return getService().addFaroProjectEmailAddressDomain(
			faroProjectEmailAddressDomain);
	}

	public static FaroProjectEmailAddressDomain
		addFaroProjectEmailAddressDomain(
			long groupId, long faroProjectId, String emailDomain) {

		return getService().addFaroProjectEmailAddressDomain(
			groupId, faroProjectId, emailDomain);
	}

	public static void addFaroProjectEmailAddressDomains(
		long groupId, long faroProjectId, List<String> emailAddressDomains) {

		getService().addFaroProjectEmailAddressDomains(
			groupId, faroProjectId, emailAddressDomains);
	}

	/**
	 * Creates a new faro project email address domain with the primary key. Does not add the faro project email address domain to the database.
	 *
	 * @param faroProjectEmailAddressDomainId the primary key for the new faro project email address domain
	 * @return the new faro project email address domain
	 */
	public static FaroProjectEmailAddressDomain
		createFaroProjectEmailAddressDomain(
			long faroProjectEmailAddressDomainId) {

		return getService().createFaroProjectEmailAddressDomain(
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
	public static FaroProjectEmailAddressDomain
		deleteFaroProjectEmailAddressDomain(
			FaroProjectEmailAddressDomain faroProjectEmailAddressDomain) {

		return getService().deleteFaroProjectEmailAddressDomain(
			faroProjectEmailAddressDomain);
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
	public static FaroProjectEmailAddressDomain
			deleteFaroProjectEmailAddressDomain(
				long faroProjectEmailAddressDomainId)
		throws PortalException {

		return getService().deleteFaroProjectEmailAddressDomain(
			faroProjectEmailAddressDomainId);
	}

	/**
	 * @throws PortalException
	 */
	public static PersistedModel deletePersistedModel(
			PersistedModel persistedModel)
		throws PortalException {

		return getService().deletePersistedModel(persistedModel);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.osb.faro.model.impl.FaroProjectEmailAddressDomainModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.osb.faro.model.impl.FaroProjectEmailAddressDomainModelImpl</code>.
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

	public static FaroProjectEmailAddressDomain
		fetchFaroProjectEmailAddressDomain(
			long faroProjectEmailAddressDomainId) {

		return getService().fetchFaroProjectEmailAddressDomain(
			faroProjectEmailAddressDomainId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	/**
	 * Returns the faro project email address domain with the primary key.
	 *
	 * @param faroProjectEmailAddressDomainId the primary key of the faro project email address domain
	 * @return the faro project email address domain
	 * @throws PortalException if a faro project email address domain with the primary key could not be found
	 */
	public static FaroProjectEmailAddressDomain
			getFaroProjectEmailAddressDomain(
				long faroProjectEmailAddressDomainId)
		throws PortalException {

		return getService().getFaroProjectEmailAddressDomain(
			faroProjectEmailAddressDomainId);
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
	public static List<FaroProjectEmailAddressDomain>
		getFaroProjectEmailAddressDomains(int start, int end) {

		return getService().getFaroProjectEmailAddressDomains(start, end);
	}

	public static List<FaroProjectEmailAddressDomain>
		getFaroProjectEmailAddressDomainsByFaroProjectId(long faroProjectId) {

		return getService().getFaroProjectEmailAddressDomainsByFaroProjectId(
			faroProjectId);
	}

	public static List<FaroProjectEmailAddressDomain>
		getFaroProjectEmailAddressDomainsByGroupId(long groupId) {

		return getService().getFaroProjectEmailAddressDomainsByGroupId(groupId);
	}

	/**
	 * Returns the number of faro project email address domains.
	 *
	 * @return the number of faro project email address domains
	 */
	public static int getFaroProjectEmailAddressDomainsCount() {
		return getService().getFaroProjectEmailAddressDomainsCount();
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
	 * Updates the faro project email address domain in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect FaroProjectEmailAddressDomainLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param faroProjectEmailAddressDomain the faro project email address domain
	 * @return the faro project email address domain that was updated
	 */
	public static FaroProjectEmailAddressDomain
		updateFaroProjectEmailAddressDomain(
			FaroProjectEmailAddressDomain faroProjectEmailAddressDomain) {

		return getService().updateFaroProjectEmailAddressDomain(
			faroProjectEmailAddressDomain);
	}

	public static FaroProjectEmailAddressDomainLocalService getService() {
		return _service;
	}

	private static volatile FaroProjectEmailAddressDomainLocalService _service;

}