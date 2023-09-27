/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.service.persistence;

import com.liferay.change.tracking.exception.NoSuchRemoteException;
import com.liferay.change.tracking.model.CTRemote;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the ct remote service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CTRemoteUtil
 * @generated
 */
@ProviderType
public interface CTRemotePersistence extends BasePersistence<CTRemote> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CTRemoteUtil} to access the ct remote persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the ct remotes where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching ct remotes
	 */
	public java.util.List<CTRemote> findByCompanyId(long companyId);

	/**
	 * Returns a range of all the ct remotes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTRemoteModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct remotes
	 * @param end the upper bound of the range of ct remotes (not inclusive)
	 * @return the range of matching ct remotes
	 */
	public java.util.List<CTRemote> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the ct remotes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTRemoteModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct remotes
	 * @param end the upper bound of the range of ct remotes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct remotes
	 */
	public java.util.List<CTRemote> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTRemote>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ct remotes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTRemoteModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct remotes
	 * @param end the upper bound of the range of ct remotes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ct remotes
	 */
	public java.util.List<CTRemote> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTRemote>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first ct remote in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct remote
	 * @throws NoSuchRemoteException if a matching ct remote could not be found
	 */
	public CTRemote findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CTRemote>
				orderByComparator)
		throws NoSuchRemoteException;

	/**
	 * Returns the first ct remote in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct remote, or <code>null</code> if a matching ct remote could not be found
	 */
	public CTRemote fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CTRemote>
			orderByComparator);

	/**
	 * Returns the last ct remote in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct remote
	 * @throws NoSuchRemoteException if a matching ct remote could not be found
	 */
	public CTRemote findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CTRemote>
				orderByComparator)
		throws NoSuchRemoteException;

	/**
	 * Returns the last ct remote in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct remote, or <code>null</code> if a matching ct remote could not be found
	 */
	public CTRemote fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CTRemote>
			orderByComparator);

	/**
	 * Returns the ct remotes before and after the current ct remote in the ordered set where companyId = &#63;.
	 *
	 * @param ctRemoteId the primary key of the current ct remote
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct remote
	 * @throws NoSuchRemoteException if a ct remote with the primary key could not be found
	 */
	public CTRemote[] findByCompanyId_PrevAndNext(
			long ctRemoteId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CTRemote>
				orderByComparator)
		throws NoSuchRemoteException;

	/**
	 * Returns all the ct remotes that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching ct remotes that the user has permission to view
	 */
	public java.util.List<CTRemote> filterFindByCompanyId(long companyId);

	/**
	 * Returns a range of all the ct remotes that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTRemoteModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct remotes
	 * @param end the upper bound of the range of ct remotes (not inclusive)
	 * @return the range of matching ct remotes that the user has permission to view
	 */
	public java.util.List<CTRemote> filterFindByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the ct remotes that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTRemoteModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ct remotes
	 * @param end the upper bound of the range of ct remotes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct remotes that the user has permission to view
	 */
	public java.util.List<CTRemote> filterFindByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTRemote>
			orderByComparator);

	/**
	 * Returns the ct remotes before and after the current ct remote in the ordered set of ct remotes that the user has permission to view where companyId = &#63;.
	 *
	 * @param ctRemoteId the primary key of the current ct remote
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct remote
	 * @throws NoSuchRemoteException if a ct remote with the primary key could not be found
	 */
	public CTRemote[] filterFindByCompanyId_PrevAndNext(
			long ctRemoteId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CTRemote>
				orderByComparator)
		throws NoSuchRemoteException;

	/**
	 * Removes all the ct remotes where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of ct remotes where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching ct remotes
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns the number of ct remotes that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching ct remotes that the user has permission to view
	 */
	public int filterCountByCompanyId(long companyId);

	/**
	 * Caches the ct remote in the entity cache if it is enabled.
	 *
	 * @param ctRemote the ct remote
	 */
	public void cacheResult(CTRemote ctRemote);

	/**
	 * Caches the ct remotes in the entity cache if it is enabled.
	 *
	 * @param ctRemotes the ct remotes
	 */
	public void cacheResult(java.util.List<CTRemote> ctRemotes);

	/**
	 * Creates a new ct remote with the primary key. Does not add the ct remote to the database.
	 *
	 * @param ctRemoteId the primary key for the new ct remote
	 * @return the new ct remote
	 */
	public CTRemote create(long ctRemoteId);

	/**
	 * Removes the ct remote with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctRemoteId the primary key of the ct remote
	 * @return the ct remote that was removed
	 * @throws NoSuchRemoteException if a ct remote with the primary key could not be found
	 */
	public CTRemote remove(long ctRemoteId) throws NoSuchRemoteException;

	public CTRemote updateImpl(CTRemote ctRemote);

	/**
	 * Returns the ct remote with the primary key or throws a <code>NoSuchRemoteException</code> if it could not be found.
	 *
	 * @param ctRemoteId the primary key of the ct remote
	 * @return the ct remote
	 * @throws NoSuchRemoteException if a ct remote with the primary key could not be found
	 */
	public CTRemote findByPrimaryKey(long ctRemoteId)
		throws NoSuchRemoteException;

	/**
	 * Returns the ct remote with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ctRemoteId the primary key of the ct remote
	 * @return the ct remote, or <code>null</code> if a ct remote with the primary key could not be found
	 */
	public CTRemote fetchByPrimaryKey(long ctRemoteId);

	/**
	 * Returns all the ct remotes.
	 *
	 * @return the ct remotes
	 */
	public java.util.List<CTRemote> findAll();

	/**
	 * Returns a range of all the ct remotes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTRemoteModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct remotes
	 * @param end the upper bound of the range of ct remotes (not inclusive)
	 * @return the range of ct remotes
	 */
	public java.util.List<CTRemote> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the ct remotes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTRemoteModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct remotes
	 * @param end the upper bound of the range of ct remotes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct remotes
	 */
	public java.util.List<CTRemote> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTRemote>
			orderByComparator);

	/**
	 * Returns an ordered range of all the ct remotes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CTRemoteModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct remotes
	 * @param end the upper bound of the range of ct remotes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ct remotes
	 */
	public java.util.List<CTRemote> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTRemote>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the ct remotes from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of ct remotes.
	 *
	 * @return the number of ct remotes
	 */
	public int countAll();

}