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

package com.liferay.osb.faro.service.persistence;

import com.liferay.osb.faro.exception.NoSuchFaroProjectEmailAddressDomainException;
import com.liferay.osb.faro.model.FaroProjectEmailAddressDomain;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the faro project email address domain service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Matthew Kong
 * @see FaroProjectEmailAddressDomainUtil
 * @generated
 */
@ProviderType
public interface FaroProjectEmailAddressDomainPersistence
	extends BasePersistence<FaroProjectEmailAddressDomain> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link FaroProjectEmailAddressDomainUtil} to access the faro project email address domain persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the faro project email address domains where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching faro project email address domains
	 */
	public java.util.List<FaroProjectEmailAddressDomain> findByGroupId(
		long groupId);

	/**
	 * Returns a range of all the faro project email address domains where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectEmailAddressDomainModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of faro project email address domains
	 * @param end the upper bound of the range of faro project email address domains (not inclusive)
	 * @return the range of matching faro project email address domains
	 */
	public java.util.List<FaroProjectEmailAddressDomain> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the faro project email address domains where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectEmailAddressDomainModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of faro project email address domains
	 * @param end the upper bound of the range of faro project email address domains (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching faro project email address domains
	 */
	public java.util.List<FaroProjectEmailAddressDomain> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<FaroProjectEmailAddressDomain> orderByComparator);

	/**
	 * Returns an ordered range of all the faro project email address domains where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectEmailAddressDomainModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of faro project email address domains
	 * @param end the upper bound of the range of faro project email address domains (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching faro project email address domains
	 */
	public java.util.List<FaroProjectEmailAddressDomain> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<FaroProjectEmailAddressDomain> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first faro project email address domain in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro project email address domain
	 * @throws NoSuchFaroProjectEmailAddressDomainException if a matching faro project email address domain could not be found
	 */
	public FaroProjectEmailAddressDomain findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<FaroProjectEmailAddressDomain> orderByComparator)
		throws NoSuchFaroProjectEmailAddressDomainException;

	/**
	 * Returns the first faro project email address domain in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro project email address domain, or <code>null</code> if a matching faro project email address domain could not be found
	 */
	public FaroProjectEmailAddressDomain fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator
			<FaroProjectEmailAddressDomain> orderByComparator);

	/**
	 * Returns the last faro project email address domain in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro project email address domain
	 * @throws NoSuchFaroProjectEmailAddressDomainException if a matching faro project email address domain could not be found
	 */
	public FaroProjectEmailAddressDomain findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<FaroProjectEmailAddressDomain> orderByComparator)
		throws NoSuchFaroProjectEmailAddressDomainException;

	/**
	 * Returns the last faro project email address domain in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro project email address domain, or <code>null</code> if a matching faro project email address domain could not be found
	 */
	public FaroProjectEmailAddressDomain fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator
			<FaroProjectEmailAddressDomain> orderByComparator);

	/**
	 * Returns the faro project email address domains before and after the current faro project email address domain in the ordered set where groupId = &#63;.
	 *
	 * @param faroProjectEmailAddressDomainId the primary key of the current faro project email address domain
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next faro project email address domain
	 * @throws NoSuchFaroProjectEmailAddressDomainException if a faro project email address domain with the primary key could not be found
	 */
	public FaroProjectEmailAddressDomain[] findByGroupId_PrevAndNext(
			long faroProjectEmailAddressDomainId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator
				<FaroProjectEmailAddressDomain> orderByComparator)
		throws NoSuchFaroProjectEmailAddressDomainException;

	/**
	 * Removes all the faro project email address domains where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of faro project email address domains where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching faro project email address domains
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns all the faro project email address domains where faroProjectId = &#63;.
	 *
	 * @param faroProjectId the faro project ID
	 * @return the matching faro project email address domains
	 */
	public java.util.List<FaroProjectEmailAddressDomain> findByFaroProjectId(
		long faroProjectId);

	/**
	 * Returns a range of all the faro project email address domains where faroProjectId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectEmailAddressDomainModelImpl</code>.
	 * </p>
	 *
	 * @param faroProjectId the faro project ID
	 * @param start the lower bound of the range of faro project email address domains
	 * @param end the upper bound of the range of faro project email address domains (not inclusive)
	 * @return the range of matching faro project email address domains
	 */
	public java.util.List<FaroProjectEmailAddressDomain> findByFaroProjectId(
		long faroProjectId, int start, int end);

	/**
	 * Returns an ordered range of all the faro project email address domains where faroProjectId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectEmailAddressDomainModelImpl</code>.
	 * </p>
	 *
	 * @param faroProjectId the faro project ID
	 * @param start the lower bound of the range of faro project email address domains
	 * @param end the upper bound of the range of faro project email address domains (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching faro project email address domains
	 */
	public java.util.List<FaroProjectEmailAddressDomain> findByFaroProjectId(
		long faroProjectId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<FaroProjectEmailAddressDomain> orderByComparator);

	/**
	 * Returns an ordered range of all the faro project email address domains where faroProjectId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectEmailAddressDomainModelImpl</code>.
	 * </p>
	 *
	 * @param faroProjectId the faro project ID
	 * @param start the lower bound of the range of faro project email address domains
	 * @param end the upper bound of the range of faro project email address domains (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching faro project email address domains
	 */
	public java.util.List<FaroProjectEmailAddressDomain> findByFaroProjectId(
		long faroProjectId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<FaroProjectEmailAddressDomain> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first faro project email address domain in the ordered set where faroProjectId = &#63;.
	 *
	 * @param faroProjectId the faro project ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro project email address domain
	 * @throws NoSuchFaroProjectEmailAddressDomainException if a matching faro project email address domain could not be found
	 */
	public FaroProjectEmailAddressDomain findByFaroProjectId_First(
			long faroProjectId,
			com.liferay.portal.kernel.util.OrderByComparator
				<FaroProjectEmailAddressDomain> orderByComparator)
		throws NoSuchFaroProjectEmailAddressDomainException;

	/**
	 * Returns the first faro project email address domain in the ordered set where faroProjectId = &#63;.
	 *
	 * @param faroProjectId the faro project ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching faro project email address domain, or <code>null</code> if a matching faro project email address domain could not be found
	 */
	public FaroProjectEmailAddressDomain fetchByFaroProjectId_First(
		long faroProjectId,
		com.liferay.portal.kernel.util.OrderByComparator
			<FaroProjectEmailAddressDomain> orderByComparator);

	/**
	 * Returns the last faro project email address domain in the ordered set where faroProjectId = &#63;.
	 *
	 * @param faroProjectId the faro project ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro project email address domain
	 * @throws NoSuchFaroProjectEmailAddressDomainException if a matching faro project email address domain could not be found
	 */
	public FaroProjectEmailAddressDomain findByFaroProjectId_Last(
			long faroProjectId,
			com.liferay.portal.kernel.util.OrderByComparator
				<FaroProjectEmailAddressDomain> orderByComparator)
		throws NoSuchFaroProjectEmailAddressDomainException;

	/**
	 * Returns the last faro project email address domain in the ordered set where faroProjectId = &#63;.
	 *
	 * @param faroProjectId the faro project ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching faro project email address domain, or <code>null</code> if a matching faro project email address domain could not be found
	 */
	public FaroProjectEmailAddressDomain fetchByFaroProjectId_Last(
		long faroProjectId,
		com.liferay.portal.kernel.util.OrderByComparator
			<FaroProjectEmailAddressDomain> orderByComparator);

	/**
	 * Returns the faro project email address domains before and after the current faro project email address domain in the ordered set where faroProjectId = &#63;.
	 *
	 * @param faroProjectEmailAddressDomainId the primary key of the current faro project email address domain
	 * @param faroProjectId the faro project ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next faro project email address domain
	 * @throws NoSuchFaroProjectEmailAddressDomainException if a faro project email address domain with the primary key could not be found
	 */
	public FaroProjectEmailAddressDomain[] findByFaroProjectId_PrevAndNext(
			long faroProjectEmailAddressDomainId, long faroProjectId,
			com.liferay.portal.kernel.util.OrderByComparator
				<FaroProjectEmailAddressDomain> orderByComparator)
		throws NoSuchFaroProjectEmailAddressDomainException;

	/**
	 * Removes all the faro project email address domains where faroProjectId = &#63; from the database.
	 *
	 * @param faroProjectId the faro project ID
	 */
	public void removeByFaroProjectId(long faroProjectId);

	/**
	 * Returns the number of faro project email address domains where faroProjectId = &#63;.
	 *
	 * @param faroProjectId the faro project ID
	 * @return the number of matching faro project email address domains
	 */
	public int countByFaroProjectId(long faroProjectId);

	/**
	 * Caches the faro project email address domain in the entity cache if it is enabled.
	 *
	 * @param faroProjectEmailAddressDomain the faro project email address domain
	 */
	public void cacheResult(
		FaroProjectEmailAddressDomain faroProjectEmailAddressDomain);

	/**
	 * Caches the faro project email address domains in the entity cache if it is enabled.
	 *
	 * @param faroProjectEmailAddressDomains the faro project email address domains
	 */
	public void cacheResult(
		java.util.List<FaroProjectEmailAddressDomain>
			faroProjectEmailAddressDomains);

	/**
	 * Creates a new faro project email address domain with the primary key. Does not add the faro project email address domain to the database.
	 *
	 * @param faroProjectEmailAddressDomainId the primary key for the new faro project email address domain
	 * @return the new faro project email address domain
	 */
	public FaroProjectEmailAddressDomain create(
		long faroProjectEmailAddressDomainId);

	/**
	 * Removes the faro project email address domain with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param faroProjectEmailAddressDomainId the primary key of the faro project email address domain
	 * @return the faro project email address domain that was removed
	 * @throws NoSuchFaroProjectEmailAddressDomainException if a faro project email address domain with the primary key could not be found
	 */
	public FaroProjectEmailAddressDomain remove(
			long faroProjectEmailAddressDomainId)
		throws NoSuchFaroProjectEmailAddressDomainException;

	public FaroProjectEmailAddressDomain updateImpl(
		FaroProjectEmailAddressDomain faroProjectEmailAddressDomain);

	/**
	 * Returns the faro project email address domain with the primary key or throws a <code>NoSuchFaroProjectEmailAddressDomainException</code> if it could not be found.
	 *
	 * @param faroProjectEmailAddressDomainId the primary key of the faro project email address domain
	 * @return the faro project email address domain
	 * @throws NoSuchFaroProjectEmailAddressDomainException if a faro project email address domain with the primary key could not be found
	 */
	public FaroProjectEmailAddressDomain findByPrimaryKey(
			long faroProjectEmailAddressDomainId)
		throws NoSuchFaroProjectEmailAddressDomainException;

	/**
	 * Returns the faro project email address domain with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param faroProjectEmailAddressDomainId the primary key of the faro project email address domain
	 * @return the faro project email address domain, or <code>null</code> if a faro project email address domain with the primary key could not be found
	 */
	public FaroProjectEmailAddressDomain fetchByPrimaryKey(
		long faroProjectEmailAddressDomainId);

	/**
	 * Returns all the faro project email address domains.
	 *
	 * @return the faro project email address domains
	 */
	public java.util.List<FaroProjectEmailAddressDomain> findAll();

	/**
	 * Returns a range of all the faro project email address domains.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectEmailAddressDomainModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of faro project email address domains
	 * @param end the upper bound of the range of faro project email address domains (not inclusive)
	 * @return the range of faro project email address domains
	 */
	public java.util.List<FaroProjectEmailAddressDomain> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the faro project email address domains.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectEmailAddressDomainModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of faro project email address domains
	 * @param end the upper bound of the range of faro project email address domains (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of faro project email address domains
	 */
	public java.util.List<FaroProjectEmailAddressDomain> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<FaroProjectEmailAddressDomain> orderByComparator);

	/**
	 * Returns an ordered range of all the faro project email address domains.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FaroProjectEmailAddressDomainModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of faro project email address domains
	 * @param end the upper bound of the range of faro project email address domains (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of faro project email address domains
	 */
	public java.util.List<FaroProjectEmailAddressDomain> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<FaroProjectEmailAddressDomain> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the faro project email address domains from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of faro project email address domains.
	 *
	 * @return the number of faro project email address domains
	 */
	public int countAll();

}