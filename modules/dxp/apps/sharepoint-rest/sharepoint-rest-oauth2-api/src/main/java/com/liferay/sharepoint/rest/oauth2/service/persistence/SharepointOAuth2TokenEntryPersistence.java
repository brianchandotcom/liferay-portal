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

package com.liferay.sharepoint.rest.oauth2.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import com.liferay.sharepoint.rest.oauth2.exception.NoSuch2TokenEntryException;
import com.liferay.sharepoint.rest.oauth2.model.SharepointOAuth2TokenEntry;

/**
 * The persistence interface for the sharepoint o auth2 token entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Adolfo Pérez
 * @see com.liferay.sharepoint.rest.oauth2.service.persistence.impl.SharepointOAuth2TokenEntryPersistenceImpl
 * @see SharepointOAuth2TokenEntryUtil
 * @generated
 */
@ProviderType
public interface SharepointOAuth2TokenEntryPersistence extends BasePersistence<SharepointOAuth2TokenEntry> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SharepointOAuth2TokenEntryUtil} to access the sharepoint o auth2 token entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns the sharepoint o auth2 token entry where userId = &#63; and configurationPid = &#63; or throws a {@link NoSuch2TokenEntryException} if it could not be found.
	*
	* @param userId the user ID
	* @param configurationPid the configuration pid
	* @return the matching sharepoint o auth2 token entry
	* @throws NoSuch2TokenEntryException if a matching sharepoint o auth2 token entry could not be found
	*/
	public SharepointOAuth2TokenEntry findByU_C(long userId,
		String configurationPid) throws NoSuch2TokenEntryException;

	/**
	* Returns the sharepoint o auth2 token entry where userId = &#63; and configurationPid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param userId the user ID
	* @param configurationPid the configuration pid
	* @return the matching sharepoint o auth2 token entry, or <code>null</code> if a matching sharepoint o auth2 token entry could not be found
	*/
	public SharepointOAuth2TokenEntry fetchByU_C(long userId,
		String configurationPid);

	/**
	* Returns the sharepoint o auth2 token entry where userId = &#63; and configurationPid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param userId the user ID
	* @param configurationPid the configuration pid
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching sharepoint o auth2 token entry, or <code>null</code> if a matching sharepoint o auth2 token entry could not be found
	*/
	public SharepointOAuth2TokenEntry fetchByU_C(long userId,
		String configurationPid, boolean retrieveFromCache);

	/**
	* Removes the sharepoint o auth2 token entry where userId = &#63; and configurationPid = &#63; from the database.
	*
	* @param userId the user ID
	* @param configurationPid the configuration pid
	* @return the sharepoint o auth2 token entry that was removed
	*/
	public SharepointOAuth2TokenEntry removeByU_C(long userId,
		String configurationPid) throws NoSuch2TokenEntryException;

	/**
	* Returns the number of sharepoint o auth2 token entries where userId = &#63; and configurationPid = &#63;.
	*
	* @param userId the user ID
	* @param configurationPid the configuration pid
	* @return the number of matching sharepoint o auth2 token entries
	*/
	public int countByU_C(long userId, String configurationPid);

	/**
	* Caches the sharepoint o auth2 token entry in the entity cache if it is enabled.
	*
	* @param sharepointOAuth2TokenEntry the sharepoint o auth2 token entry
	*/
	public void cacheResult(
		SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry);

	/**
	* Caches the sharepoint o auth2 token entries in the entity cache if it is enabled.
	*
	* @param sharepointOAuth2TokenEntries the sharepoint o auth2 token entries
	*/
	public void cacheResult(
		java.util.List<SharepointOAuth2TokenEntry> sharepointOAuth2TokenEntries);

	/**
	* Creates a new sharepoint o auth2 token entry with the primary key. Does not add the sharepoint o auth2 token entry to the database.
	*
	* @param sharepointOAuth2TokenEntryId the primary key for the new sharepoint o auth2 token entry
	* @return the new sharepoint o auth2 token entry
	*/
	public SharepointOAuth2TokenEntry create(long sharepointOAuth2TokenEntryId);

	/**
	* Removes the sharepoint o auth2 token entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param sharepointOAuth2TokenEntryId the primary key of the sharepoint o auth2 token entry
	* @return the sharepoint o auth2 token entry that was removed
	* @throws NoSuch2TokenEntryException if a sharepoint o auth2 token entry with the primary key could not be found
	*/
	public SharepointOAuth2TokenEntry remove(long sharepointOAuth2TokenEntryId)
		throws NoSuch2TokenEntryException;

	public SharepointOAuth2TokenEntry updateImpl(
		SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry);

	/**
	* Returns the sharepoint o auth2 token entry with the primary key or throws a {@link NoSuch2TokenEntryException} if it could not be found.
	*
	* @param sharepointOAuth2TokenEntryId the primary key of the sharepoint o auth2 token entry
	* @return the sharepoint o auth2 token entry
	* @throws NoSuch2TokenEntryException if a sharepoint o auth2 token entry with the primary key could not be found
	*/
	public SharepointOAuth2TokenEntry findByPrimaryKey(
		long sharepointOAuth2TokenEntryId) throws NoSuch2TokenEntryException;

	/**
	* Returns the sharepoint o auth2 token entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param sharepointOAuth2TokenEntryId the primary key of the sharepoint o auth2 token entry
	* @return the sharepoint o auth2 token entry, or <code>null</code> if a sharepoint o auth2 token entry with the primary key could not be found
	*/
	public SharepointOAuth2TokenEntry fetchByPrimaryKey(
		long sharepointOAuth2TokenEntryId);

	@Override
	public java.util.Map<java.io.Serializable, SharepointOAuth2TokenEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the sharepoint o auth2 token entries.
	*
	* @return the sharepoint o auth2 token entries
	*/
	public java.util.List<SharepointOAuth2TokenEntry> findAll();

	/**
	* Returns a range of all the sharepoint o auth2 token entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharepointOAuth2TokenEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of sharepoint o auth2 token entries
	* @param end the upper bound of the range of sharepoint o auth2 token entries (not inclusive)
	* @return the range of sharepoint o auth2 token entries
	*/
	public java.util.List<SharepointOAuth2TokenEntry> findAll(int start, int end);

	/**
	* Returns an ordered range of all the sharepoint o auth2 token entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharepointOAuth2TokenEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of sharepoint o auth2 token entries
	* @param end the upper bound of the range of sharepoint o auth2 token entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of sharepoint o auth2 token entries
	*/
	public java.util.List<SharepointOAuth2TokenEntry> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<SharepointOAuth2TokenEntry> orderByComparator);

	/**
	* Returns an ordered range of all the sharepoint o auth2 token entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharepointOAuth2TokenEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of sharepoint o auth2 token entries
	* @param end the upper bound of the range of sharepoint o auth2 token entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of sharepoint o auth2 token entries
	*/
	public java.util.List<SharepointOAuth2TokenEntry> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<SharepointOAuth2TokenEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the sharepoint o auth2 token entries from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of sharepoint o auth2 token entries.
	*
	* @return the number of sharepoint o auth2 token entries
	*/
	public int countAll();
}