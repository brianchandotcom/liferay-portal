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

package com.liferay.portlet.documentlibrary.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.documentlibrary.exception.NoSuchFileVersionException;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;

/**
 * The persistence interface for the document library file version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.documentlibrary.service.persistence.impl.DLFileVersionPersistenceImpl
 * @see DLFileVersionUtil
 * @generated
 */
@ProviderType
public interface DLFileVersionPersistence extends BasePersistence<DLFileVersion> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DLFileVersionUtil} to access the document library file version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the document library file versions where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching document library file versions
	*/
	public java.util.List<DLFileVersion> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the document library file versions where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @return the range of matching document library file versions
	*/
	public java.util.List<DLFileVersion> findByUuid(java.lang.String uuid,
		int start, int end);

	/**
	* Returns an ordered range of all the document library file versions where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file versions
	*/
	public java.util.List<DLFileVersion> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator);

	/**
	* Returns an ordered range of all the document library file versions where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library file versions
	*/
	public java.util.List<DLFileVersion> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first document library file version in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file version
	* @throws NoSuchFileVersionException if a matching document library file version could not be found
	*/
	public DLFileVersion findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException;

	/**
	* Returns the first document library file version in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	*/
	public DLFileVersion fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator);

	/**
	* Returns the last document library file version in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file version
	* @throws NoSuchFileVersionException if a matching document library file version could not be found
	*/
	public DLFileVersion findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException;

	/**
	* Returns the last document library file version in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file version, or <code>null</code> if a matching document library file version could not be found
	*/
	public DLFileVersion fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator);

	/**
	* Returns the document library file versions before and after the current document library file version in the ordered set where uuid = &#63;.
	*
	* @param fileVersionId the primary key of the current document library file version
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file version
	* @throws NoSuchFileVersionException if a document library file version with the primary key could not be found
	*/
	public DLFileVersion[] findByUuid_PrevAndNext(long fileVersionId,
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException;

	/**
	* Removes all the document library file versions where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of document library file versions where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching document library file versions
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the document library file version where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchFileVersionException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching document library file version
	* @throws NoSuchFileVersionException if a matching document library file version could not be found
	*/
	public DLFileVersion findByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchFileVersionException;

	/**
	* Returns the document library file version where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching document library file version, or <code>null</code> if a matching document library file version could not be found
	*/
	public DLFileVersion fetchByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns the document library file version where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching document library file version, or <code>null</code> if a matching document library file version could not be found
	*/
	public DLFileVersion fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the document library file version where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the document library file version that was removed
	*/
	public DLFileVersion removeByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchFileVersionException;

	/**
	* Returns the number of document library file versions where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching document library file versions
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the document library file versions where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching document library file versions
	*/
	public java.util.List<DLFileVersion> findByUuid_C(java.lang.String uuid,
		long companyId);

	/**
	* Returns a range of all the document library file versions where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @return the range of matching document library file versions
	*/
	public java.util.List<DLFileVersion> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the document library file versions where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file versions
	*/
	public java.util.List<DLFileVersion> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator);

	/**
	* Returns an ordered range of all the document library file versions where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library file versions
	*/
	public java.util.List<DLFileVersion> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first document library file version in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file version
	* @throws NoSuchFileVersionException if a matching document library file version could not be found
	*/
	public DLFileVersion findByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException;

	/**
	* Returns the first document library file version in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	*/
	public DLFileVersion fetchByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator);

	/**
	* Returns the last document library file version in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file version
	* @throws NoSuchFileVersionException if a matching document library file version could not be found
	*/
	public DLFileVersion findByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException;

	/**
	* Returns the last document library file version in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file version, or <code>null</code> if a matching document library file version could not be found
	*/
	public DLFileVersion fetchByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator);

	/**
	* Returns the document library file versions before and after the current document library file version in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param fileVersionId the primary key of the current document library file version
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file version
	* @throws NoSuchFileVersionException if a document library file version with the primary key could not be found
	*/
	public DLFileVersion[] findByUuid_C_PrevAndNext(long fileVersionId,
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException;

	/**
	* Removes all the document library file versions where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of document library file versions where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching document library file versions
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the document library file versions where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching document library file versions
	*/
	public java.util.List<DLFileVersion> findByCompanyId(long companyId);

	/**
	* Returns a range of all the document library file versions where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @return the range of matching document library file versions
	*/
	public java.util.List<DLFileVersion> findByCompanyId(long companyId,
		int start, int end);

	/**
	* Returns an ordered range of all the document library file versions where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file versions
	*/
	public java.util.List<DLFileVersion> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator);

	/**
	* Returns an ordered range of all the document library file versions where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library file versions
	*/
	public java.util.List<DLFileVersion> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first document library file version in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file version
	* @throws NoSuchFileVersionException if a matching document library file version could not be found
	*/
	public DLFileVersion findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException;

	/**
	* Returns the first document library file version in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	*/
	public DLFileVersion fetchByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator);

	/**
	* Returns the last document library file version in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file version
	* @throws NoSuchFileVersionException if a matching document library file version could not be found
	*/
	public DLFileVersion findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException;

	/**
	* Returns the last document library file version in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file version, or <code>null</code> if a matching document library file version could not be found
	*/
	public DLFileVersion fetchByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator);

	/**
	* Returns the document library file versions before and after the current document library file version in the ordered set where companyId = &#63;.
	*
	* @param fileVersionId the primary key of the current document library file version
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file version
	* @throws NoSuchFileVersionException if a document library file version with the primary key could not be found
	*/
	public DLFileVersion[] findByCompanyId_PrevAndNext(long fileVersionId,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException;

	/**
	* Removes all the document library file versions where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of document library file versions where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching document library file versions
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns all the document library file versions where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @return the matching document library file versions
	*/
	public java.util.List<DLFileVersion> findByFileEntryId(long fileEntryId);

	/**
	* Returns a range of all the document library file versions where fileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fileEntryId the file entry ID
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @return the range of matching document library file versions
	*/
	public java.util.List<DLFileVersion> findByFileEntryId(long fileEntryId,
		int start, int end);

	/**
	* Returns an ordered range of all the document library file versions where fileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fileEntryId the file entry ID
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file versions
	*/
	public java.util.List<DLFileVersion> findByFileEntryId(long fileEntryId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator);

	/**
	* Returns an ordered range of all the document library file versions where fileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fileEntryId the file entry ID
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library file versions
	*/
	public java.util.List<DLFileVersion> findByFileEntryId(long fileEntryId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first document library file version in the ordered set where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file version
	* @throws NoSuchFileVersionException if a matching document library file version could not be found
	*/
	public DLFileVersion findByFileEntryId_First(long fileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException;

	/**
	* Returns the first document library file version in the ordered set where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	*/
	public DLFileVersion fetchByFileEntryId_First(long fileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator);

	/**
	* Returns the last document library file version in the ordered set where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file version
	* @throws NoSuchFileVersionException if a matching document library file version could not be found
	*/
	public DLFileVersion findByFileEntryId_Last(long fileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException;

	/**
	* Returns the last document library file version in the ordered set where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file version, or <code>null</code> if a matching document library file version could not be found
	*/
	public DLFileVersion fetchByFileEntryId_Last(long fileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator);

	/**
	* Returns the document library file versions before and after the current document library file version in the ordered set where fileEntryId = &#63;.
	*
	* @param fileVersionId the primary key of the current document library file version
	* @param fileEntryId the file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file version
	* @throws NoSuchFileVersionException if a document library file version with the primary key could not be found
	*/
	public DLFileVersion[] findByFileEntryId_PrevAndNext(long fileVersionId,
		long fileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException;

	/**
	* Removes all the document library file versions where fileEntryId = &#63; from the database.
	*
	* @param fileEntryId the file entry ID
	*/
	public void removeByFileEntryId(long fileEntryId);

	/**
	* Returns the number of document library file versions where fileEntryId = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @return the number of matching document library file versions
	*/
	public int countByFileEntryId(long fileEntryId);

	/**
	* Returns all the document library file versions where mimeType = &#63;.
	*
	* @param mimeType the mime type
	* @return the matching document library file versions
	*/
	public java.util.List<DLFileVersion> findByMimeType(
		java.lang.String mimeType);

	/**
	* Returns a range of all the document library file versions where mimeType = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param mimeType the mime type
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @return the range of matching document library file versions
	*/
	public java.util.List<DLFileVersion> findByMimeType(
		java.lang.String mimeType, int start, int end);

	/**
	* Returns an ordered range of all the document library file versions where mimeType = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param mimeType the mime type
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file versions
	*/
	public java.util.List<DLFileVersion> findByMimeType(
		java.lang.String mimeType, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator);

	/**
	* Returns an ordered range of all the document library file versions where mimeType = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param mimeType the mime type
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library file versions
	*/
	public java.util.List<DLFileVersion> findByMimeType(
		java.lang.String mimeType, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first document library file version in the ordered set where mimeType = &#63;.
	*
	* @param mimeType the mime type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file version
	* @throws NoSuchFileVersionException if a matching document library file version could not be found
	*/
	public DLFileVersion findByMimeType_First(java.lang.String mimeType,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException;

	/**
	* Returns the first document library file version in the ordered set where mimeType = &#63;.
	*
	* @param mimeType the mime type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	*/
	public DLFileVersion fetchByMimeType_First(java.lang.String mimeType,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator);

	/**
	* Returns the last document library file version in the ordered set where mimeType = &#63;.
	*
	* @param mimeType the mime type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file version
	* @throws NoSuchFileVersionException if a matching document library file version could not be found
	*/
	public DLFileVersion findByMimeType_Last(java.lang.String mimeType,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException;

	/**
	* Returns the last document library file version in the ordered set where mimeType = &#63;.
	*
	* @param mimeType the mime type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file version, or <code>null</code> if a matching document library file version could not be found
	*/
	public DLFileVersion fetchByMimeType_Last(java.lang.String mimeType,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator);

	/**
	* Returns the document library file versions before and after the current document library file version in the ordered set where mimeType = &#63;.
	*
	* @param fileVersionId the primary key of the current document library file version
	* @param mimeType the mime type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file version
	* @throws NoSuchFileVersionException if a document library file version with the primary key could not be found
	*/
	public DLFileVersion[] findByMimeType_PrevAndNext(long fileVersionId,
		java.lang.String mimeType,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException;

	/**
	* Removes all the document library file versions where mimeType = &#63; from the database.
	*
	* @param mimeType the mime type
	*/
	public void removeByMimeType(java.lang.String mimeType);

	/**
	* Returns the number of document library file versions where mimeType = &#63;.
	*
	* @param mimeType the mime type
	* @return the number of matching document library file versions
	*/
	public int countByMimeType(java.lang.String mimeType);

	/**
	* Returns all the document library file versions where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @return the matching document library file versions
	*/
	public java.util.List<DLFileVersion> findByC_NotS(long companyId, int status);

	/**
	* Returns a range of all the document library file versions where companyId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @return the range of matching document library file versions
	*/
	public java.util.List<DLFileVersion> findByC_NotS(long companyId,
		int status, int start, int end);

	/**
	* Returns an ordered range of all the document library file versions where companyId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file versions
	*/
	public java.util.List<DLFileVersion> findByC_NotS(long companyId,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator);

	/**
	* Returns an ordered range of all the document library file versions where companyId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library file versions
	*/
	public java.util.List<DLFileVersion> findByC_NotS(long companyId,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first document library file version in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file version
	* @throws NoSuchFileVersionException if a matching document library file version could not be found
	*/
	public DLFileVersion findByC_NotS_First(long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException;

	/**
	* Returns the first document library file version in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	*/
	public DLFileVersion fetchByC_NotS_First(long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator);

	/**
	* Returns the last document library file version in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file version
	* @throws NoSuchFileVersionException if a matching document library file version could not be found
	*/
	public DLFileVersion findByC_NotS_Last(long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException;

	/**
	* Returns the last document library file version in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file version, or <code>null</code> if a matching document library file version could not be found
	*/
	public DLFileVersion fetchByC_NotS_Last(long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator);

	/**
	* Returns the document library file versions before and after the current document library file version in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param fileVersionId the primary key of the current document library file version
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file version
	* @throws NoSuchFileVersionException if a document library file version with the primary key could not be found
	*/
	public DLFileVersion[] findByC_NotS_PrevAndNext(long fileVersionId,
		long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException;

	/**
	* Removes all the document library file versions where companyId = &#63; and status &ne; &#63; from the database.
	*
	* @param companyId the company ID
	* @param status the status
	*/
	public void removeByC_NotS(long companyId, int status);

	/**
	* Returns the number of document library file versions where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @return the number of matching document library file versions
	*/
	public int countByC_NotS(long companyId, int status);

	/**
	* Returns the document library file version where fileEntryId = &#63; and version = &#63; or throws a {@link NoSuchFileVersionException} if it could not be found.
	*
	* @param fileEntryId the file entry ID
	* @param version the version
	* @return the matching document library file version
	* @throws NoSuchFileVersionException if a matching document library file version could not be found
	*/
	public DLFileVersion findByF_V(long fileEntryId, java.lang.String version)
		throws NoSuchFileVersionException;

	/**
	* Returns the document library file version where fileEntryId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param fileEntryId the file entry ID
	* @param version the version
	* @return the matching document library file version, or <code>null</code> if a matching document library file version could not be found
	*/
	public DLFileVersion fetchByF_V(long fileEntryId, java.lang.String version);

	/**
	* Returns the document library file version where fileEntryId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param fileEntryId the file entry ID
	* @param version the version
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching document library file version, or <code>null</code> if a matching document library file version could not be found
	*/
	public DLFileVersion fetchByF_V(long fileEntryId, java.lang.String version,
		boolean retrieveFromCache);

	/**
	* Removes the document library file version where fileEntryId = &#63; and version = &#63; from the database.
	*
	* @param fileEntryId the file entry ID
	* @param version the version
	* @return the document library file version that was removed
	*/
	public DLFileVersion removeByF_V(long fileEntryId, java.lang.String version)
		throws NoSuchFileVersionException;

	/**
	* Returns the number of document library file versions where fileEntryId = &#63; and version = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param version the version
	* @return the number of matching document library file versions
	*/
	public int countByF_V(long fileEntryId, java.lang.String version);

	/**
	* Returns all the document library file versions where fileEntryId = &#63; and status = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param status the status
	* @return the matching document library file versions
	*/
	public java.util.List<DLFileVersion> findByF_S(long fileEntryId, int status);

	/**
	* Returns a range of all the document library file versions where fileEntryId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fileEntryId the file entry ID
	* @param status the status
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @return the range of matching document library file versions
	*/
	public java.util.List<DLFileVersion> findByF_S(long fileEntryId,
		int status, int start, int end);

	/**
	* Returns an ordered range of all the document library file versions where fileEntryId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fileEntryId the file entry ID
	* @param status the status
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file versions
	*/
	public java.util.List<DLFileVersion> findByF_S(long fileEntryId,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator);

	/**
	* Returns an ordered range of all the document library file versions where fileEntryId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fileEntryId the file entry ID
	* @param status the status
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library file versions
	*/
	public java.util.List<DLFileVersion> findByF_S(long fileEntryId,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first document library file version in the ordered set where fileEntryId = &#63; and status = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file version
	* @throws NoSuchFileVersionException if a matching document library file version could not be found
	*/
	public DLFileVersion findByF_S_First(long fileEntryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException;

	/**
	* Returns the first document library file version in the ordered set where fileEntryId = &#63; and status = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	*/
	public DLFileVersion fetchByF_S_First(long fileEntryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator);

	/**
	* Returns the last document library file version in the ordered set where fileEntryId = &#63; and status = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file version
	* @throws NoSuchFileVersionException if a matching document library file version could not be found
	*/
	public DLFileVersion findByF_S_Last(long fileEntryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException;

	/**
	* Returns the last document library file version in the ordered set where fileEntryId = &#63; and status = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file version, or <code>null</code> if a matching document library file version could not be found
	*/
	public DLFileVersion fetchByF_S_Last(long fileEntryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator);

	/**
	* Returns the document library file versions before and after the current document library file version in the ordered set where fileEntryId = &#63; and status = &#63;.
	*
	* @param fileVersionId the primary key of the current document library file version
	* @param fileEntryId the file entry ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file version
	* @throws NoSuchFileVersionException if a document library file version with the primary key could not be found
	*/
	public DLFileVersion[] findByF_S_PrevAndNext(long fileVersionId,
		long fileEntryId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException;

	/**
	* Removes all the document library file versions where fileEntryId = &#63; and status = &#63; from the database.
	*
	* @param fileEntryId the file entry ID
	* @param status the status
	*/
	public void removeByF_S(long fileEntryId, int status);

	/**
	* Returns the number of document library file versions where fileEntryId = &#63; and status = &#63;.
	*
	* @param fileEntryId the file entry ID
	* @param status the status
	* @return the number of matching document library file versions
	*/
	public int countByF_S(long fileEntryId, int status);

	/**
	* Returns all the document library file versions where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @return the matching document library file versions
	*/
	public java.util.List<DLFileVersion> findByG_F_S(long groupId,
		long folderId, int status);

	/**
	* Returns a range of all the document library file versions where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @return the range of matching document library file versions
	*/
	public java.util.List<DLFileVersion> findByG_F_S(long groupId,
		long folderId, int status, int start, int end);

	/**
	* Returns an ordered range of all the document library file versions where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file versions
	*/
	public java.util.List<DLFileVersion> findByG_F_S(long groupId,
		long folderId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator);

	/**
	* Returns an ordered range of all the document library file versions where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library file versions
	*/
	public java.util.List<DLFileVersion> findByG_F_S(long groupId,
		long folderId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first document library file version in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file version
	* @throws NoSuchFileVersionException if a matching document library file version could not be found
	*/
	public DLFileVersion findByG_F_S_First(long groupId, long folderId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException;

	/**
	* Returns the first document library file version in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	*/
	public DLFileVersion fetchByG_F_S_First(long groupId, long folderId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator);

	/**
	* Returns the last document library file version in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file version
	* @throws NoSuchFileVersionException if a matching document library file version could not be found
	*/
	public DLFileVersion findByG_F_S_Last(long groupId, long folderId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException;

	/**
	* Returns the last document library file version in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file version, or <code>null</code> if a matching document library file version could not be found
	*/
	public DLFileVersion fetchByG_F_S_Last(long groupId, long folderId,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator);

	/**
	* Returns the document library file versions before and after the current document library file version in the ordered set where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param fileVersionId the primary key of the current document library file version
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file version
	* @throws NoSuchFileVersionException if a document library file version with the primary key could not be found
	*/
	public DLFileVersion[] findByG_F_S_PrevAndNext(long fileVersionId,
		long groupId, long folderId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException;

	/**
	* Removes all the document library file versions where groupId = &#63; and folderId = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	*/
	public void removeByG_F_S(long groupId, long folderId, int status);

	/**
	* Returns the number of document library file versions where groupId = &#63; and folderId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param status the status
	* @return the number of matching document library file versions
	*/
	public int countByG_F_S(long groupId, long folderId, int status);

	/**
	* Returns all the document library file versions where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param title the title
	* @param version the version
	* @return the matching document library file versions
	*/
	public java.util.List<DLFileVersion> findByG_F_T_V(long groupId,
		long folderId, java.lang.String title, java.lang.String version);

	/**
	* Returns a range of all the document library file versions where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param title the title
	* @param version the version
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @return the range of matching document library file versions
	*/
	public java.util.List<DLFileVersion> findByG_F_T_V(long groupId,
		long folderId, java.lang.String title, java.lang.String version,
		int start, int end);

	/**
	* Returns an ordered range of all the document library file versions where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param title the title
	* @param version the version
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file versions
	*/
	public java.util.List<DLFileVersion> findByG_F_T_V(long groupId,
		long folderId, java.lang.String title, java.lang.String version,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator);

	/**
	* Returns an ordered range of all the document library file versions where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param title the title
	* @param version the version
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library file versions
	*/
	public java.util.List<DLFileVersion> findByG_F_T_V(long groupId,
		long folderId, java.lang.String title, java.lang.String version,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first document library file version in the ordered set where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param title the title
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file version
	* @throws NoSuchFileVersionException if a matching document library file version could not be found
	*/
	public DLFileVersion findByG_F_T_V_First(long groupId, long folderId,
		java.lang.String title, java.lang.String version,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException;

	/**
	* Returns the first document library file version in the ordered set where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param title the title
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file version, or <code>null</code> if a matching document library file version could not be found
	*/
	public DLFileVersion fetchByG_F_T_V_First(long groupId, long folderId,
		java.lang.String title, java.lang.String version,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator);

	/**
	* Returns the last document library file version in the ordered set where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param title the title
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file version
	* @throws NoSuchFileVersionException if a matching document library file version could not be found
	*/
	public DLFileVersion findByG_F_T_V_Last(long groupId, long folderId,
		java.lang.String title, java.lang.String version,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException;

	/**
	* Returns the last document library file version in the ordered set where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param title the title
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file version, or <code>null</code> if a matching document library file version could not be found
	*/
	public DLFileVersion fetchByG_F_T_V_Last(long groupId, long folderId,
		java.lang.String title, java.lang.String version,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator);

	/**
	* Returns the document library file versions before and after the current document library file version in the ordered set where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	*
	* @param fileVersionId the primary key of the current document library file version
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param title the title
	* @param version the version
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file version
	* @throws NoSuchFileVersionException if a document library file version with the primary key could not be found
	*/
	public DLFileVersion[] findByG_F_T_V_PrevAndNext(long fileVersionId,
		long groupId, long folderId, java.lang.String title,
		java.lang.String version,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator)
		throws NoSuchFileVersionException;

	/**
	* Removes all the document library file versions where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63; from the database.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param title the title
	* @param version the version
	*/
	public void removeByG_F_T_V(long groupId, long folderId,
		java.lang.String title, java.lang.String version);

	/**
	* Returns the number of document library file versions where groupId = &#63; and folderId = &#63; and title = &#63; and version = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param title the title
	* @param version the version
	* @return the number of matching document library file versions
	*/
	public int countByG_F_T_V(long groupId, long folderId,
		java.lang.String title, java.lang.String version);

	/**
	* Caches the document library file version in the entity cache if it is enabled.
	*
	* @param dlFileVersion the document library file version
	*/
	public void cacheResult(DLFileVersion dlFileVersion);

	/**
	* Caches the document library file versions in the entity cache if it is enabled.
	*
	* @param dlFileVersions the document library file versions
	*/
	public void cacheResult(java.util.List<DLFileVersion> dlFileVersions);

	/**
	* Creates a new document library file version with the primary key. Does not add the document library file version to the database.
	*
	* @param fileVersionId the primary key for the new document library file version
	* @return the new document library file version
	*/
	public DLFileVersion create(long fileVersionId);

	/**
	* Removes the document library file version with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fileVersionId the primary key of the document library file version
	* @return the document library file version that was removed
	* @throws NoSuchFileVersionException if a document library file version with the primary key could not be found
	*/
	public DLFileVersion remove(long fileVersionId)
		throws NoSuchFileVersionException;

	public DLFileVersion updateImpl(DLFileVersion dlFileVersion);

	/**
	* Returns the document library file version with the primary key or throws a {@link NoSuchFileVersionException} if it could not be found.
	*
	* @param fileVersionId the primary key of the document library file version
	* @return the document library file version
	* @throws NoSuchFileVersionException if a document library file version with the primary key could not be found
	*/
	public DLFileVersion findByPrimaryKey(long fileVersionId)
		throws NoSuchFileVersionException;

	/**
	* Returns the document library file version with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param fileVersionId the primary key of the document library file version
	* @return the document library file version, or <code>null</code> if a document library file version with the primary key could not be found
	*/
	public DLFileVersion fetchByPrimaryKey(long fileVersionId);

	@Override
	public java.util.Map<java.io.Serializable, DLFileVersion> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the document library file versions.
	*
	* @return the document library file versions
	*/
	public java.util.List<DLFileVersion> findAll();

	/**
	* Returns a range of all the document library file versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @return the range of document library file versions
	*/
	public java.util.List<DLFileVersion> findAll(int start, int end);

	/**
	* Returns an ordered range of all the document library file versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of document library file versions
	*/
	public java.util.List<DLFileVersion> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator);

	/**
	* Returns an ordered range of all the document library file versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of document library file versions
	* @param end the upper bound of the range of document library file versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of document library file versions
	*/
	public java.util.List<DLFileVersion> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileVersion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the document library file versions from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of document library file versions.
	*
	* @return the number of document library file versions
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}