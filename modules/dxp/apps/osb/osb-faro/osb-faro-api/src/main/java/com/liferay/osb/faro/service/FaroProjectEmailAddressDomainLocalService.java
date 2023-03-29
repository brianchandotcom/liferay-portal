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

import aQute.bnd.annotation.ProviderType;

import com.liferay.osb.faro.model.FaroProjectEmailAddressDomain;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service interface for FaroProjectEmailAddressDomain. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Matthew Kong
 * @see FaroProjectEmailAddressDomainLocalServiceUtil
 * @generated
 */
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface FaroProjectEmailAddressDomainLocalService
	extends BaseLocalService, PersistedModelLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.osb.faro.service.impl.FaroProjectEmailAddressDomainLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the faro project email address domain local service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link FaroProjectEmailAddressDomainLocalServiceUtil} if injection and service tracking are not available.
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
	@Indexable(type = IndexableType.REINDEX)
	public FaroProjectEmailAddressDomain addFaroProjectEmailAddressDomain(
		FaroProjectEmailAddressDomain faroProjectEmailAddressDomain);

	@Indexable(type = IndexableType.REINDEX)
	public FaroProjectEmailAddressDomain addFaroProjectEmailAddressDomain(
		long groupId, long faroProjectId, String emailDomain);

	public void addFaroProjectEmailAddressDomains(
		long groupId, long faroProjectId, List<String> emailAddressDomains);

	/**
	 * Creates a new faro project email address domain with the primary key. Does not add the faro project email address domain to the database.
	 *
	 * @param faroProjectEmailAddressDomainId the primary key for the new faro project email address domain
	 * @return the new faro project email address domain
	 */
	@Transactional(enabled = false)
	public FaroProjectEmailAddressDomain createFaroProjectEmailAddressDomain(
		long faroProjectEmailAddressDomainId);

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
	@Indexable(type = IndexableType.DELETE)
	public FaroProjectEmailAddressDomain deleteFaroProjectEmailAddressDomain(
		FaroProjectEmailAddressDomain faroProjectEmailAddressDomain);

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
	@Indexable(type = IndexableType.DELETE)
	public FaroProjectEmailAddressDomain deleteFaroProjectEmailAddressDomain(
			long faroProjectEmailAddressDomainId)
		throws PortalException;

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DynamicQuery dynamicQuery();

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery);

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end);

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator);

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long dynamicQueryCount(DynamicQuery dynamicQuery);

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long dynamicQueryCount(
		DynamicQuery dynamicQuery, Projection projection);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FaroProjectEmailAddressDomain fetchFaroProjectEmailAddressDomain(
		long faroProjectEmailAddressDomainId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	/**
	 * Returns the faro project email address domain with the primary key.
	 *
	 * @param faroProjectEmailAddressDomainId the primary key of the faro project email address domain
	 * @return the faro project email address domain
	 * @throws PortalException if a faro project email address domain with the primary key could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public FaroProjectEmailAddressDomain getFaroProjectEmailAddressDomain(
			long faroProjectEmailAddressDomainId)
		throws PortalException;

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FaroProjectEmailAddressDomain>
		getFaroProjectEmailAddressDomains(int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FaroProjectEmailAddressDomain>
		getFaroProjectEmailAddressDomainsByFaroProjectId(long faroProjectId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<FaroProjectEmailAddressDomain>
		getFaroProjectEmailAddressDomainsByGroupId(long groupId);

	/**
	 * Returns the number of faro project email address domains.
	 *
	 * @return the number of faro project email address domains
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFaroProjectEmailAddressDomainsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	/**
	 * @throws PortalException
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

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
	@Indexable(type = IndexableType.REINDEX)
	public FaroProjectEmailAddressDomain updateFaroProjectEmailAddressDomain(
		FaroProjectEmailAddressDomain faroProjectEmailAddressDomain);

}