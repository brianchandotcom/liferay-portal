/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.dynamic.data.mapping.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.mapping.exception.NoSuchStorageLinkException;
import com.liferay.dynamic.data.mapping.model.DDMStorageLink;

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * The persistence interface for the d d m storage link service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.dynamic.data.mapping.service.persistence.impl.DDMStorageLinkPersistenceImpl
 * @see DDMStorageLinkUtil
 * @generated
 */
@ProviderType
public interface DDMStorageLinkPersistence extends BasePersistence<DDMStorageLink> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DDMStorageLinkUtil} to access the d d m storage link persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the d d m storage links where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching d d m storage links
	*/
	public java.util.List<DDMStorageLink> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the d d m storage links where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStorageLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of d d m storage links
	* @param end the upper bound of the range of d d m storage links (not inclusive)
	* @return the range of matching d d m storage links
	*/
	public java.util.List<DDMStorageLink> findByUuid(java.lang.String uuid,
		int start, int end);

	/**
	* Returns an ordered range of all the d d m storage links where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStorageLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of d d m storage links
	* @param end the upper bound of the range of d d m storage links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m storage links
	*/
	public java.util.List<DDMStorageLink> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStorageLink> orderByComparator);

	/**
	* Returns an ordered range of all the d d m storage links where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStorageLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of d d m storage links
	* @param end the upper bound of the range of d d m storage links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m storage links
	*/
	public java.util.List<DDMStorageLink> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStorageLink> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first d d m storage link in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m storage link
	* @throws NoSuchStorageLinkException if a matching d d m storage link could not be found
	*/
	public DDMStorageLink findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStorageLink> orderByComparator)
		throws NoSuchStorageLinkException;

	/**
	* Returns the first d d m storage link in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m storage link, or <code>null</code> if a matching d d m storage link could not be found
	*/
	public DDMStorageLink fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStorageLink> orderByComparator);

	/**
	* Returns the last d d m storage link in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m storage link
	* @throws NoSuchStorageLinkException if a matching d d m storage link could not be found
	*/
	public DDMStorageLink findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStorageLink> orderByComparator)
		throws NoSuchStorageLinkException;

	/**
	* Returns the last d d m storage link in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m storage link, or <code>null</code> if a matching d d m storage link could not be found
	*/
	public DDMStorageLink fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStorageLink> orderByComparator);

	/**
	* Returns the d d m storage links before and after the current d d m storage link in the ordered set where uuid = &#63;.
	*
	* @param storageLinkId the primary key of the current d d m storage link
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m storage link
	* @throws NoSuchStorageLinkException if a d d m storage link with the primary key could not be found
	*/
	public DDMStorageLink[] findByUuid_PrevAndNext(long storageLinkId,
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStorageLink> orderByComparator)
		throws NoSuchStorageLinkException;

	/**
	* Removes all the d d m storage links where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of d d m storage links where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching d d m storage links
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns all the d d m storage links where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching d d m storage links
	*/
	public java.util.List<DDMStorageLink> findByUuid_C(java.lang.String uuid,
		long companyId);

	/**
	* Returns a range of all the d d m storage links where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStorageLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of d d m storage links
	* @param end the upper bound of the range of d d m storage links (not inclusive)
	* @return the range of matching d d m storage links
	*/
	public java.util.List<DDMStorageLink> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the d d m storage links where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStorageLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of d d m storage links
	* @param end the upper bound of the range of d d m storage links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m storage links
	*/
	public java.util.List<DDMStorageLink> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStorageLink> orderByComparator);

	/**
	* Returns an ordered range of all the d d m storage links where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStorageLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of d d m storage links
	* @param end the upper bound of the range of d d m storage links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m storage links
	*/
	public java.util.List<DDMStorageLink> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStorageLink> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first d d m storage link in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m storage link
	* @throws NoSuchStorageLinkException if a matching d d m storage link could not be found
	*/
	public DDMStorageLink findByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStorageLink> orderByComparator)
		throws NoSuchStorageLinkException;

	/**
	* Returns the first d d m storage link in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m storage link, or <code>null</code> if a matching d d m storage link could not be found
	*/
	public DDMStorageLink fetchByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStorageLink> orderByComparator);

	/**
	* Returns the last d d m storage link in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m storage link
	* @throws NoSuchStorageLinkException if a matching d d m storage link could not be found
	*/
	public DDMStorageLink findByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStorageLink> orderByComparator)
		throws NoSuchStorageLinkException;

	/**
	* Returns the last d d m storage link in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m storage link, or <code>null</code> if a matching d d m storage link could not be found
	*/
	public DDMStorageLink fetchByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStorageLink> orderByComparator);

	/**
	* Returns the d d m storage links before and after the current d d m storage link in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param storageLinkId the primary key of the current d d m storage link
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m storage link
	* @throws NoSuchStorageLinkException if a d d m storage link with the primary key could not be found
	*/
	public DDMStorageLink[] findByUuid_C_PrevAndNext(long storageLinkId,
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStorageLink> orderByComparator)
		throws NoSuchStorageLinkException;

	/**
	* Removes all the d d m storage links where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of d d m storage links where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching d d m storage links
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the d d m storage link where classPK = &#63; or throws a {@link NoSuchStorageLinkException} if it could not be found.
	*
	* @param classPK the class p k
	* @return the matching d d m storage link
	* @throws NoSuchStorageLinkException if a matching d d m storage link could not be found
	*/
	public DDMStorageLink findByClassPK(long classPK)
		throws NoSuchStorageLinkException;

	/**
	* Returns the d d m storage link where classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param classPK the class p k
	* @return the matching d d m storage link, or <code>null</code> if a matching d d m storage link could not be found
	*/
	public DDMStorageLink fetchByClassPK(long classPK);

	/**
	* Returns the d d m storage link where classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param classPK the class p k
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching d d m storage link, or <code>null</code> if a matching d d m storage link could not be found
	*/
	public DDMStorageLink fetchByClassPK(long classPK, boolean retrieveFromCache);

	/**
	* Removes the d d m storage link where classPK = &#63; from the database.
	*
	* @param classPK the class p k
	* @return the d d m storage link that was removed
	*/
	public DDMStorageLink removeByClassPK(long classPK)
		throws NoSuchStorageLinkException;

	/**
	* Returns the number of d d m storage links where classPK = &#63;.
	*
	* @param classPK the class p k
	* @return the number of matching d d m storage links
	*/
	public int countByClassPK(long classPK);

	/**
	* Returns all the d d m storage links where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @return the matching d d m storage links
	*/
	public java.util.List<DDMStorageLink> findByStructureId(long structureId);

	/**
	* Returns a range of all the d d m storage links where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStorageLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param structureId the structure ID
	* @param start the lower bound of the range of d d m storage links
	* @param end the upper bound of the range of d d m storage links (not inclusive)
	* @return the range of matching d d m storage links
	*/
	public java.util.List<DDMStorageLink> findByStructureId(long structureId,
		int start, int end);

	/**
	* Returns an ordered range of all the d d m storage links where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStorageLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param structureId the structure ID
	* @param start the lower bound of the range of d d m storage links
	* @param end the upper bound of the range of d d m storage links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m storage links
	*/
	public java.util.List<DDMStorageLink> findByStructureId(long structureId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStorageLink> orderByComparator);

	/**
	* Returns an ordered range of all the d d m storage links where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStorageLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param structureId the structure ID
	* @param start the lower bound of the range of d d m storage links
	* @param end the upper bound of the range of d d m storage links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m storage links
	*/
	public java.util.List<DDMStorageLink> findByStructureId(long structureId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStorageLink> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first d d m storage link in the ordered set where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m storage link
	* @throws NoSuchStorageLinkException if a matching d d m storage link could not be found
	*/
	public DDMStorageLink findByStructureId_First(long structureId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStorageLink> orderByComparator)
		throws NoSuchStorageLinkException;

	/**
	* Returns the first d d m storage link in the ordered set where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m storage link, or <code>null</code> if a matching d d m storage link could not be found
	*/
	public DDMStorageLink fetchByStructureId_First(long structureId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStorageLink> orderByComparator);

	/**
	* Returns the last d d m storage link in the ordered set where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m storage link
	* @throws NoSuchStorageLinkException if a matching d d m storage link could not be found
	*/
	public DDMStorageLink findByStructureId_Last(long structureId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStorageLink> orderByComparator)
		throws NoSuchStorageLinkException;

	/**
	* Returns the last d d m storage link in the ordered set where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m storage link, or <code>null</code> if a matching d d m storage link could not be found
	*/
	public DDMStorageLink fetchByStructureId_Last(long structureId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStorageLink> orderByComparator);

	/**
	* Returns the d d m storage links before and after the current d d m storage link in the ordered set where structureId = &#63;.
	*
	* @param storageLinkId the primary key of the current d d m storage link
	* @param structureId the structure ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m storage link
	* @throws NoSuchStorageLinkException if a d d m storage link with the primary key could not be found
	*/
	public DDMStorageLink[] findByStructureId_PrevAndNext(long storageLinkId,
		long structureId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStorageLink> orderByComparator)
		throws NoSuchStorageLinkException;

	/**
	* Removes all the d d m storage links where structureId = &#63; from the database.
	*
	* @param structureId the structure ID
	*/
	public void removeByStructureId(long structureId);

	/**
	* Returns the number of d d m storage links where structureId = &#63;.
	*
	* @param structureId the structure ID
	* @return the number of matching d d m storage links
	*/
	public int countByStructureId(long structureId);

	/**
	* Caches the d d m storage link in the entity cache if it is enabled.
	*
	* @param ddmStorageLink the d d m storage link
	*/
	public void cacheResult(DDMStorageLink ddmStorageLink);

	/**
	* Caches the d d m storage links in the entity cache if it is enabled.
	*
	* @param ddmStorageLinks the d d m storage links
	*/
	public void cacheResult(java.util.List<DDMStorageLink> ddmStorageLinks);

	/**
	* Creates a new d d m storage link with the primary key. Does not add the d d m storage link to the database.
	*
	* @param storageLinkId the primary key for the new d d m storage link
	* @return the new d d m storage link
	*/
	public DDMStorageLink create(long storageLinkId);

	/**
	* Removes the d d m storage link with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param storageLinkId the primary key of the d d m storage link
	* @return the d d m storage link that was removed
	* @throws NoSuchStorageLinkException if a d d m storage link with the primary key could not be found
	*/
	public DDMStorageLink remove(long storageLinkId)
		throws NoSuchStorageLinkException;

	public DDMStorageLink updateImpl(DDMStorageLink ddmStorageLink);

	/**
	* Returns the d d m storage link with the primary key or throws a {@link NoSuchStorageLinkException} if it could not be found.
	*
	* @param storageLinkId the primary key of the d d m storage link
	* @return the d d m storage link
	* @throws NoSuchStorageLinkException if a d d m storage link with the primary key could not be found
	*/
	public DDMStorageLink findByPrimaryKey(long storageLinkId)
		throws NoSuchStorageLinkException;

	/**
	* Returns the d d m storage link with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param storageLinkId the primary key of the d d m storage link
	* @return the d d m storage link, or <code>null</code> if a d d m storage link with the primary key could not be found
	*/
	public DDMStorageLink fetchByPrimaryKey(long storageLinkId);

	@Override
	public java.util.Map<java.io.Serializable, DDMStorageLink> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the d d m storage links.
	*
	* @return the d d m storage links
	*/
	public java.util.List<DDMStorageLink> findAll();

	/**
	* Returns a range of all the d d m storage links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStorageLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m storage links
	* @param end the upper bound of the range of d d m storage links (not inclusive)
	* @return the range of d d m storage links
	*/
	public java.util.List<DDMStorageLink> findAll(int start, int end);

	/**
	* Returns an ordered range of all the d d m storage links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStorageLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m storage links
	* @param end the upper bound of the range of d d m storage links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of d d m storage links
	*/
	public java.util.List<DDMStorageLink> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStorageLink> orderByComparator);

	/**
	* Returns an ordered range of all the d d m storage links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStorageLinkModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m storage links
	* @param end the upper bound of the range of d d m storage links (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of d d m storage links
	*/
	public java.util.List<DDMStorageLink> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMStorageLink> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the d d m storage links from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of d d m storage links.
	*
	* @return the number of d d m storage links
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}