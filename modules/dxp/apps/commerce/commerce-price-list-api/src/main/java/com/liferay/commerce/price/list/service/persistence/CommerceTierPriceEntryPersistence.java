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

package com.liferay.commerce.price.list.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.price.list.exception.NoSuchTierPriceEntryException;
import com.liferay.commerce.price.list.model.CommerceTierPriceEntry;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the commerce tier price entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.price.list.service.persistence.impl.CommerceTierPriceEntryPersistenceImpl
 * @see CommerceTierPriceEntryUtil
 * @generated
 */
@ProviderType
public interface CommerceTierPriceEntryPersistence extends BasePersistence<CommerceTierPriceEntry> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceTierPriceEntryUtil} to access the commerce tier price entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the commerce tier price entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching commerce tier price entries
	*/
	public java.util.List<CommerceTierPriceEntry> findByUuid(String uuid);

	/**
	* Returns a range of all the commerce tier price entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce tier price entries
	* @param end the upper bound of the range of commerce tier price entries (not inclusive)
	* @return the range of matching commerce tier price entries
	*/
	public java.util.List<CommerceTierPriceEntry> findByUuid(String uuid,
		int start, int end);

	/**
	* Returns an ordered range of all the commerce tier price entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce tier price entries
	* @param end the upper bound of the range of commerce tier price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce tier price entries
	*/
	public java.util.List<CommerceTierPriceEntry> findByUuid(String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTierPriceEntry> orderByComparator);

	/**
	* Returns an ordered range of all the commerce tier price entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce tier price entries
	* @param end the upper bound of the range of commerce tier price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce tier price entries
	*/
	public java.util.List<CommerceTierPriceEntry> findByUuid(String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTierPriceEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce tier price entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tier price entry
	* @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	*/
	public CommerceTierPriceEntry findByUuid_First(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException;

	/**
	* Returns the first commerce tier price entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	*/
	public CommerceTierPriceEntry fetchByUuid_First(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTierPriceEntry> orderByComparator);

	/**
	* Returns the last commerce tier price entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tier price entry
	* @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	*/
	public CommerceTierPriceEntry findByUuid_Last(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException;

	/**
	* Returns the last commerce tier price entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	*/
	public CommerceTierPriceEntry fetchByUuid_Last(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTierPriceEntry> orderByComparator);

	/**
	* Returns the commerce tier price entries before and after the current commerce tier price entry in the ordered set where uuid = &#63;.
	*
	* @param commerceTierPriceEntryId the primary key of the current commerce tier price entry
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce tier price entry
	* @throws NoSuchTierPriceEntryException if a commerce tier price entry with the primary key could not be found
	*/
	public CommerceTierPriceEntry[] findByUuid_PrevAndNext(
		long commerceTierPriceEntryId, String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException;

	/**
	* Removes all the commerce tier price entries where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(String uuid);

	/**
	* Returns the number of commerce tier price entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching commerce tier price entries
	*/
	public int countByUuid(String uuid);

	/**
	* Returns the commerce tier price entry where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchTierPriceEntryException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce tier price entry
	* @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	*/
	public CommerceTierPriceEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchTierPriceEntryException;

	/**
	* Returns the commerce tier price entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	*/
	public CommerceTierPriceEntry fetchByUUID_G(String uuid, long groupId);

	/**
	* Returns the commerce tier price entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	*/
	public CommerceTierPriceEntry fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the commerce tier price entry where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the commerce tier price entry that was removed
	*/
	public CommerceTierPriceEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchTierPriceEntryException;

	/**
	* Returns the number of commerce tier price entries where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching commerce tier price entries
	*/
	public int countByUUID_G(String uuid, long groupId);

	/**
	* Returns all the commerce tier price entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching commerce tier price entries
	*/
	public java.util.List<CommerceTierPriceEntry> findByUuid_C(String uuid,
		long companyId);

	/**
	* Returns a range of all the commerce tier price entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce tier price entries
	* @param end the upper bound of the range of commerce tier price entries (not inclusive)
	* @return the range of matching commerce tier price entries
	*/
	public java.util.List<CommerceTierPriceEntry> findByUuid_C(String uuid,
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the commerce tier price entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce tier price entries
	* @param end the upper bound of the range of commerce tier price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce tier price entries
	*/
	public java.util.List<CommerceTierPriceEntry> findByUuid_C(String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTierPriceEntry> orderByComparator);

	/**
	* Returns an ordered range of all the commerce tier price entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce tier price entries
	* @param end the upper bound of the range of commerce tier price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce tier price entries
	*/
	public java.util.List<CommerceTierPriceEntry> findByUuid_C(String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTierPriceEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce tier price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tier price entry
	* @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	*/
	public CommerceTierPriceEntry findByUuid_C_First(String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException;

	/**
	* Returns the first commerce tier price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	*/
	public CommerceTierPriceEntry fetchByUuid_C_First(String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTierPriceEntry> orderByComparator);

	/**
	* Returns the last commerce tier price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tier price entry
	* @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	*/
	public CommerceTierPriceEntry findByUuid_C_Last(String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException;

	/**
	* Returns the last commerce tier price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	*/
	public CommerceTierPriceEntry fetchByUuid_C_Last(String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTierPriceEntry> orderByComparator);

	/**
	* Returns the commerce tier price entries before and after the current commerce tier price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param commerceTierPriceEntryId the primary key of the current commerce tier price entry
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce tier price entry
	* @throws NoSuchTierPriceEntryException if a commerce tier price entry with the primary key could not be found
	*/
	public CommerceTierPriceEntry[] findByUuid_C_PrevAndNext(
		long commerceTierPriceEntryId, String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException;

	/**
	* Removes all the commerce tier price entries where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(String uuid, long companyId);

	/**
	* Returns the number of commerce tier price entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching commerce tier price entries
	*/
	public int countByUuid_C(String uuid, long companyId);

	/**
	* Returns the commerce tier price entry where externalReferenceCode = &#63; or throws a {@link NoSuchTierPriceEntryException} if it could not be found.
	*
	* @param externalReferenceCode the external reference code
	* @return the matching commerce tier price entry
	* @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	*/
	public CommerceTierPriceEntry findByExternalReferenceCode(
		String externalReferenceCode) throws NoSuchTierPriceEntryException;

	/**
	* Returns the commerce tier price entry where externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param externalReferenceCode the external reference code
	* @return the matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	*/
	public CommerceTierPriceEntry fetchByExternalReferenceCode(
		String externalReferenceCode);

	/**
	* Returns the commerce tier price entry where externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param externalReferenceCode the external reference code
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	*/
	public CommerceTierPriceEntry fetchByExternalReferenceCode(
		String externalReferenceCode, boolean retrieveFromCache);

	/**
	* Removes the commerce tier price entry where externalReferenceCode = &#63; from the database.
	*
	* @param externalReferenceCode the external reference code
	* @return the commerce tier price entry that was removed
	*/
	public CommerceTierPriceEntry removeByExternalReferenceCode(
		String externalReferenceCode) throws NoSuchTierPriceEntryException;

	/**
	* Returns the number of commerce tier price entries where externalReferenceCode = &#63;.
	*
	* @param externalReferenceCode the external reference code
	* @return the number of matching commerce tier price entries
	*/
	public int countByExternalReferenceCode(String externalReferenceCode);

	/**
	* Returns all the commerce tier price entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce tier price entries
	*/
	public java.util.List<CommerceTierPriceEntry> findByGroupId(long groupId);

	/**
	* Returns a range of all the commerce tier price entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce tier price entries
	* @param end the upper bound of the range of commerce tier price entries (not inclusive)
	* @return the range of matching commerce tier price entries
	*/
	public java.util.List<CommerceTierPriceEntry> findByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the commerce tier price entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce tier price entries
	* @param end the upper bound of the range of commerce tier price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce tier price entries
	*/
	public java.util.List<CommerceTierPriceEntry> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTierPriceEntry> orderByComparator);

	/**
	* Returns an ordered range of all the commerce tier price entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce tier price entries
	* @param end the upper bound of the range of commerce tier price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce tier price entries
	*/
	public java.util.List<CommerceTierPriceEntry> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTierPriceEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce tier price entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tier price entry
	* @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	*/
	public CommerceTierPriceEntry findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException;

	/**
	* Returns the first commerce tier price entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	*/
	public CommerceTierPriceEntry fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTierPriceEntry> orderByComparator);

	/**
	* Returns the last commerce tier price entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tier price entry
	* @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	*/
	public CommerceTierPriceEntry findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException;

	/**
	* Returns the last commerce tier price entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	*/
	public CommerceTierPriceEntry fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTierPriceEntry> orderByComparator);

	/**
	* Returns the commerce tier price entries before and after the current commerce tier price entry in the ordered set where groupId = &#63;.
	*
	* @param commerceTierPriceEntryId the primary key of the current commerce tier price entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce tier price entry
	* @throws NoSuchTierPriceEntryException if a commerce tier price entry with the primary key could not be found
	*/
	public CommerceTierPriceEntry[] findByGroupId_PrevAndNext(
		long commerceTierPriceEntryId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException;

	/**
	* Removes all the commerce tier price entries where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of commerce tier price entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce tier price entries
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns all the commerce tier price entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching commerce tier price entries
	*/
	public java.util.List<CommerceTierPriceEntry> findByCompanyId(
		long companyId);

	/**
	* Returns a range of all the commerce tier price entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce tier price entries
	* @param end the upper bound of the range of commerce tier price entries (not inclusive)
	* @return the range of matching commerce tier price entries
	*/
	public java.util.List<CommerceTierPriceEntry> findByCompanyId(
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the commerce tier price entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce tier price entries
	* @param end the upper bound of the range of commerce tier price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce tier price entries
	*/
	public java.util.List<CommerceTierPriceEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTierPriceEntry> orderByComparator);

	/**
	* Returns an ordered range of all the commerce tier price entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce tier price entries
	* @param end the upper bound of the range of commerce tier price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce tier price entries
	*/
	public java.util.List<CommerceTierPriceEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTierPriceEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce tier price entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tier price entry
	* @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	*/
	public CommerceTierPriceEntry findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException;

	/**
	* Returns the first commerce tier price entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	*/
	public CommerceTierPriceEntry fetchByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTierPriceEntry> orderByComparator);

	/**
	* Returns the last commerce tier price entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tier price entry
	* @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	*/
	public CommerceTierPriceEntry findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException;

	/**
	* Returns the last commerce tier price entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	*/
	public CommerceTierPriceEntry fetchByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTierPriceEntry> orderByComparator);

	/**
	* Returns the commerce tier price entries before and after the current commerce tier price entry in the ordered set where companyId = &#63;.
	*
	* @param commerceTierPriceEntryId the primary key of the current commerce tier price entry
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce tier price entry
	* @throws NoSuchTierPriceEntryException if a commerce tier price entry with the primary key could not be found
	*/
	public CommerceTierPriceEntry[] findByCompanyId_PrevAndNext(
		long commerceTierPriceEntryId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException;

	/**
	* Removes all the commerce tier price entries where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of commerce tier price entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching commerce tier price entries
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns all the commerce tier price entries where commercePriceEntryId = &#63;.
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @return the matching commerce tier price entries
	*/
	public java.util.List<CommerceTierPriceEntry> findByCommercePriceEntryId(
		long commercePriceEntryId);

	/**
	* Returns a range of all the commerce tier price entries where commercePriceEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param start the lower bound of the range of commerce tier price entries
	* @param end the upper bound of the range of commerce tier price entries (not inclusive)
	* @return the range of matching commerce tier price entries
	*/
	public java.util.List<CommerceTierPriceEntry> findByCommercePriceEntryId(
		long commercePriceEntryId, int start, int end);

	/**
	* Returns an ordered range of all the commerce tier price entries where commercePriceEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param start the lower bound of the range of commerce tier price entries
	* @param end the upper bound of the range of commerce tier price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce tier price entries
	*/
	public java.util.List<CommerceTierPriceEntry> findByCommercePriceEntryId(
		long commercePriceEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTierPriceEntry> orderByComparator);

	/**
	* Returns an ordered range of all the commerce tier price entries where commercePriceEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param start the lower bound of the range of commerce tier price entries
	* @param end the upper bound of the range of commerce tier price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce tier price entries
	*/
	public java.util.List<CommerceTierPriceEntry> findByCommercePriceEntryId(
		long commercePriceEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTierPriceEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce tier price entry in the ordered set where commercePriceEntryId = &#63;.
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tier price entry
	* @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	*/
	public CommerceTierPriceEntry findByCommercePriceEntryId_First(
		long commercePriceEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException;

	/**
	* Returns the first commerce tier price entry in the ordered set where commercePriceEntryId = &#63;.
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	*/
	public CommerceTierPriceEntry fetchByCommercePriceEntryId_First(
		long commercePriceEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTierPriceEntry> orderByComparator);

	/**
	* Returns the last commerce tier price entry in the ordered set where commercePriceEntryId = &#63;.
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tier price entry
	* @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	*/
	public CommerceTierPriceEntry findByCommercePriceEntryId_Last(
		long commercePriceEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException;

	/**
	* Returns the last commerce tier price entry in the ordered set where commercePriceEntryId = &#63;.
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	*/
	public CommerceTierPriceEntry fetchByCommercePriceEntryId_Last(
		long commercePriceEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTierPriceEntry> orderByComparator);

	/**
	* Returns the commerce tier price entries before and after the current commerce tier price entry in the ordered set where commercePriceEntryId = &#63;.
	*
	* @param commerceTierPriceEntryId the primary key of the current commerce tier price entry
	* @param commercePriceEntryId the commerce price entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce tier price entry
	* @throws NoSuchTierPriceEntryException if a commerce tier price entry with the primary key could not be found
	*/
	public CommerceTierPriceEntry[] findByCommercePriceEntryId_PrevAndNext(
		long commerceTierPriceEntryId, long commercePriceEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException;

	/**
	* Removes all the commerce tier price entries where commercePriceEntryId = &#63; from the database.
	*
	* @param commercePriceEntryId the commerce price entry ID
	*/
	public void removeByCommercePriceEntryId(long commercePriceEntryId);

	/**
	* Returns the number of commerce tier price entries where commercePriceEntryId = &#63;.
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @return the number of matching commerce tier price entries
	*/
	public int countByCommercePriceEntryId(long commercePriceEntryId);

	/**
	* Returns the commerce tier price entry where commercePriceEntryId = &#63; and minQuantity = &#63; or throws a {@link NoSuchTierPriceEntryException} if it could not be found.
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param minQuantity the min quantity
	* @return the matching commerce tier price entry
	* @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	*/
	public CommerceTierPriceEntry findByC_M(long commercePriceEntryId,
		int minQuantity) throws NoSuchTierPriceEntryException;

	/**
	* Returns the commerce tier price entry where commercePriceEntryId = &#63; and minQuantity = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param minQuantity the min quantity
	* @return the matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	*/
	public CommerceTierPriceEntry fetchByC_M(long commercePriceEntryId,
		int minQuantity);

	/**
	* Returns the commerce tier price entry where commercePriceEntryId = &#63; and minQuantity = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param minQuantity the min quantity
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	*/
	public CommerceTierPriceEntry fetchByC_M(long commercePriceEntryId,
		int minQuantity, boolean retrieveFromCache);

	/**
	* Removes the commerce tier price entry where commercePriceEntryId = &#63; and minQuantity = &#63; from the database.
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param minQuantity the min quantity
	* @return the commerce tier price entry that was removed
	*/
	public CommerceTierPriceEntry removeByC_M(long commercePriceEntryId,
		int minQuantity) throws NoSuchTierPriceEntryException;

	/**
	* Returns the number of commerce tier price entries where commercePriceEntryId = &#63; and minQuantity = &#63;.
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param minQuantity the min quantity
	* @return the number of matching commerce tier price entries
	*/
	public int countByC_M(long commercePriceEntryId, int minQuantity);

	/**
	* Returns all the commerce tier price entries where commercePriceEntryId = &#63; and minQuantity &le; &#63;.
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param minQuantity the min quantity
	* @return the matching commerce tier price entries
	*/
	public java.util.List<CommerceTierPriceEntry> findByC_LtM(
		long commercePriceEntryId, int minQuantity);

	/**
	* Returns a range of all the commerce tier price entries where commercePriceEntryId = &#63; and minQuantity &le; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param minQuantity the min quantity
	* @param start the lower bound of the range of commerce tier price entries
	* @param end the upper bound of the range of commerce tier price entries (not inclusive)
	* @return the range of matching commerce tier price entries
	*/
	public java.util.List<CommerceTierPriceEntry> findByC_LtM(
		long commercePriceEntryId, int minQuantity, int start, int end);

	/**
	* Returns an ordered range of all the commerce tier price entries where commercePriceEntryId = &#63; and minQuantity &le; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param minQuantity the min quantity
	* @param start the lower bound of the range of commerce tier price entries
	* @param end the upper bound of the range of commerce tier price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce tier price entries
	*/
	public java.util.List<CommerceTierPriceEntry> findByC_LtM(
		long commercePriceEntryId, int minQuantity, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTierPriceEntry> orderByComparator);

	/**
	* Returns an ordered range of all the commerce tier price entries where commercePriceEntryId = &#63; and minQuantity &le; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param minQuantity the min quantity
	* @param start the lower bound of the range of commerce tier price entries
	* @param end the upper bound of the range of commerce tier price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce tier price entries
	*/
	public java.util.List<CommerceTierPriceEntry> findByC_LtM(
		long commercePriceEntryId, int minQuantity, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTierPriceEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce tier price entry in the ordered set where commercePriceEntryId = &#63; and minQuantity &le; &#63;.
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param minQuantity the min quantity
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tier price entry
	* @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	*/
	public CommerceTierPriceEntry findByC_LtM_First(long commercePriceEntryId,
		int minQuantity,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException;

	/**
	* Returns the first commerce tier price entry in the ordered set where commercePriceEntryId = &#63; and minQuantity &le; &#63;.
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param minQuantity the min quantity
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	*/
	public CommerceTierPriceEntry fetchByC_LtM_First(
		long commercePriceEntryId, int minQuantity,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTierPriceEntry> orderByComparator);

	/**
	* Returns the last commerce tier price entry in the ordered set where commercePriceEntryId = &#63; and minQuantity &le; &#63;.
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param minQuantity the min quantity
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tier price entry
	* @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	*/
	public CommerceTierPriceEntry findByC_LtM_Last(long commercePriceEntryId,
		int minQuantity,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException;

	/**
	* Returns the last commerce tier price entry in the ordered set where commercePriceEntryId = &#63; and minQuantity &le; &#63;.
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param minQuantity the min quantity
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	*/
	public CommerceTierPriceEntry fetchByC_LtM_Last(long commercePriceEntryId,
		int minQuantity,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTierPriceEntry> orderByComparator);

	/**
	* Returns the commerce tier price entries before and after the current commerce tier price entry in the ordered set where commercePriceEntryId = &#63; and minQuantity &le; &#63;.
	*
	* @param commerceTierPriceEntryId the primary key of the current commerce tier price entry
	* @param commercePriceEntryId the commerce price entry ID
	* @param minQuantity the min quantity
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce tier price entry
	* @throws NoSuchTierPriceEntryException if a commerce tier price entry with the primary key could not be found
	*/
	public CommerceTierPriceEntry[] findByC_LtM_PrevAndNext(
		long commerceTierPriceEntryId, long commercePriceEntryId,
		int minQuantity,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException;

	/**
	* Removes all the commerce tier price entries where commercePriceEntryId = &#63; and minQuantity &le; &#63; from the database.
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param minQuantity the min quantity
	*/
	public void removeByC_LtM(long commercePriceEntryId, int minQuantity);

	/**
	* Returns the number of commerce tier price entries where commercePriceEntryId = &#63; and minQuantity &le; &#63;.
	*
	* @param commercePriceEntryId the commerce price entry ID
	* @param minQuantity the min quantity
	* @return the number of matching commerce tier price entries
	*/
	public int countByC_LtM(long commercePriceEntryId, int minQuantity);

	/**
	* Caches the commerce tier price entry in the entity cache if it is enabled.
	*
	* @param commerceTierPriceEntry the commerce tier price entry
	*/
	public void cacheResult(CommerceTierPriceEntry commerceTierPriceEntry);

	/**
	* Caches the commerce tier price entries in the entity cache if it is enabled.
	*
	* @param commerceTierPriceEntries the commerce tier price entries
	*/
	public void cacheResult(
		java.util.List<CommerceTierPriceEntry> commerceTierPriceEntries);

	/**
	* Creates a new commerce tier price entry with the primary key. Does not add the commerce tier price entry to the database.
	*
	* @param commerceTierPriceEntryId the primary key for the new commerce tier price entry
	* @return the new commerce tier price entry
	*/
	public CommerceTierPriceEntry create(long commerceTierPriceEntryId);

	/**
	* Removes the commerce tier price entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceTierPriceEntryId the primary key of the commerce tier price entry
	* @return the commerce tier price entry that was removed
	* @throws NoSuchTierPriceEntryException if a commerce tier price entry with the primary key could not be found
	*/
	public CommerceTierPriceEntry remove(long commerceTierPriceEntryId)
		throws NoSuchTierPriceEntryException;

	public CommerceTierPriceEntry updateImpl(
		CommerceTierPriceEntry commerceTierPriceEntry);

	/**
	* Returns the commerce tier price entry with the primary key or throws a {@link NoSuchTierPriceEntryException} if it could not be found.
	*
	* @param commerceTierPriceEntryId the primary key of the commerce tier price entry
	* @return the commerce tier price entry
	* @throws NoSuchTierPriceEntryException if a commerce tier price entry with the primary key could not be found
	*/
	public CommerceTierPriceEntry findByPrimaryKey(
		long commerceTierPriceEntryId) throws NoSuchTierPriceEntryException;

	/**
	* Returns the commerce tier price entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceTierPriceEntryId the primary key of the commerce tier price entry
	* @return the commerce tier price entry, or <code>null</code> if a commerce tier price entry with the primary key could not be found
	*/
	public CommerceTierPriceEntry fetchByPrimaryKey(
		long commerceTierPriceEntryId);

	@Override
	public java.util.Map<java.io.Serializable, CommerceTierPriceEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce tier price entries.
	*
	* @return the commerce tier price entries
	*/
	public java.util.List<CommerceTierPriceEntry> findAll();

	/**
	* Returns a range of all the commerce tier price entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce tier price entries
	* @param end the upper bound of the range of commerce tier price entries (not inclusive)
	* @return the range of commerce tier price entries
	*/
	public java.util.List<CommerceTierPriceEntry> findAll(int start, int end);

	/**
	* Returns an ordered range of all the commerce tier price entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce tier price entries
	* @param end the upper bound of the range of commerce tier price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce tier price entries
	*/
	public java.util.List<CommerceTierPriceEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTierPriceEntry> orderByComparator);

	/**
	* Returns an ordered range of all the commerce tier price entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce tier price entries
	* @param end the upper bound of the range of commerce tier price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce tier price entries
	*/
	public java.util.List<CommerceTierPriceEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceTierPriceEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce tier price entries from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce tier price entries.
	*
	* @return the number of commerce tier price entries
	*/
	public int countAll();

	@Override
	public java.util.Set<String> getBadColumnNames();
}