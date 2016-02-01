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

import com.liferay.portlet.documentlibrary.exception.NoSuchFileShortcutException;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;

/**
 * The persistence interface for the document library file shortcut service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.documentlibrary.service.persistence.impl.DLFileShortcutPersistenceImpl
 * @see DLFileShortcutUtil
 * @generated
 */
@ProviderType
public interface DLFileShortcutPersistence extends BasePersistence<DLFileShortcut> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DLFileShortcutUtil} to access the document library file shortcut persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the document library file shortcuts where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching document library file shortcuts
	*/
	public java.util.List<DLFileShortcut> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the document library file shortcuts where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @return the range of matching document library file shortcuts
	*/
	public java.util.List<DLFileShortcut> findByUuid(java.lang.String uuid,
		int start, int end);

	/**
	* Returns an ordered range of all the document library file shortcuts where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file shortcuts
	*/
	public java.util.List<DLFileShortcut> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator);

	/**
	* Returns an ordered range of all the document library file shortcuts where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library file shortcuts
	*/
	public java.util.List<DLFileShortcut> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first document library file shortcut in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file shortcut
	* @throws NoSuchFileShortcutException if a matching document library file shortcut could not be found
	*/
	public DLFileShortcut findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException;

	/**
	* Returns the first document library file shortcut in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	*/
	public DLFileShortcut fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator);

	/**
	* Returns the last document library file shortcut in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file shortcut
	* @throws NoSuchFileShortcutException if a matching document library file shortcut could not be found
	*/
	public DLFileShortcut findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException;

	/**
	* Returns the last document library file shortcut in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	*/
	public DLFileShortcut fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator);

	/**
	* Returns the document library file shortcuts before and after the current document library file shortcut in the ordered set where uuid = &#63;.
	*
	* @param fileShortcutId the primary key of the current document library file shortcut
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file shortcut
	* @throws NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	*/
	public DLFileShortcut[] findByUuid_PrevAndNext(long fileShortcutId,
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException;

	/**
	* Removes all the document library file shortcuts where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of document library file shortcuts where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching document library file shortcuts
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the document library file shortcut where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchFileShortcutException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching document library file shortcut
	* @throws NoSuchFileShortcutException if a matching document library file shortcut could not be found
	*/
	public DLFileShortcut findByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchFileShortcutException;

	/**
	* Returns the document library file shortcut where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	*/
	public DLFileShortcut fetchByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns the document library file shortcut where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	*/
	public DLFileShortcut fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the document library file shortcut where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the document library file shortcut that was removed
	*/
	public DLFileShortcut removeByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchFileShortcutException;

	/**
	* Returns the number of document library file shortcuts where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching document library file shortcuts
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the document library file shortcuts where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching document library file shortcuts
	*/
	public java.util.List<DLFileShortcut> findByUuid_C(java.lang.String uuid,
		long companyId);

	/**
	* Returns a range of all the document library file shortcuts where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @return the range of matching document library file shortcuts
	*/
	public java.util.List<DLFileShortcut> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the document library file shortcuts where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file shortcuts
	*/
	public java.util.List<DLFileShortcut> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator);

	/**
	* Returns an ordered range of all the document library file shortcuts where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library file shortcuts
	*/
	public java.util.List<DLFileShortcut> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first document library file shortcut in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file shortcut
	* @throws NoSuchFileShortcutException if a matching document library file shortcut could not be found
	*/
	public DLFileShortcut findByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException;

	/**
	* Returns the first document library file shortcut in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	*/
	public DLFileShortcut fetchByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator);

	/**
	* Returns the last document library file shortcut in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file shortcut
	* @throws NoSuchFileShortcutException if a matching document library file shortcut could not be found
	*/
	public DLFileShortcut findByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException;

	/**
	* Returns the last document library file shortcut in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	*/
	public DLFileShortcut fetchByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator);

	/**
	* Returns the document library file shortcuts before and after the current document library file shortcut in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param fileShortcutId the primary key of the current document library file shortcut
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file shortcut
	* @throws NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	*/
	public DLFileShortcut[] findByUuid_C_PrevAndNext(long fileShortcutId,
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException;

	/**
	* Removes all the document library file shortcuts where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of document library file shortcuts where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching document library file shortcuts
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the document library file shortcuts where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching document library file shortcuts
	*/
	public java.util.List<DLFileShortcut> findByCompanyId(long companyId);

	/**
	* Returns a range of all the document library file shortcuts where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @return the range of matching document library file shortcuts
	*/
	public java.util.List<DLFileShortcut> findByCompanyId(long companyId,
		int start, int end);

	/**
	* Returns an ordered range of all the document library file shortcuts where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file shortcuts
	*/
	public java.util.List<DLFileShortcut> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator);

	/**
	* Returns an ordered range of all the document library file shortcuts where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library file shortcuts
	*/
	public java.util.List<DLFileShortcut> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first document library file shortcut in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file shortcut
	* @throws NoSuchFileShortcutException if a matching document library file shortcut could not be found
	*/
	public DLFileShortcut findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException;

	/**
	* Returns the first document library file shortcut in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	*/
	public DLFileShortcut fetchByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator);

	/**
	* Returns the last document library file shortcut in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file shortcut
	* @throws NoSuchFileShortcutException if a matching document library file shortcut could not be found
	*/
	public DLFileShortcut findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException;

	/**
	* Returns the last document library file shortcut in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	*/
	public DLFileShortcut fetchByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator);

	/**
	* Returns the document library file shortcuts before and after the current document library file shortcut in the ordered set where companyId = &#63;.
	*
	* @param fileShortcutId the primary key of the current document library file shortcut
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file shortcut
	* @throws NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	*/
	public DLFileShortcut[] findByCompanyId_PrevAndNext(long fileShortcutId,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException;

	/**
	* Removes all the document library file shortcuts where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of document library file shortcuts where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching document library file shortcuts
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns all the document library file shortcuts where toFileEntryId = &#63;.
	*
	* @param toFileEntryId the to file entry ID
	* @return the matching document library file shortcuts
	*/
	public java.util.List<DLFileShortcut> findByToFileEntryId(
		long toFileEntryId);

	/**
	* Returns a range of all the document library file shortcuts where toFileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param toFileEntryId the to file entry ID
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @return the range of matching document library file shortcuts
	*/
	public java.util.List<DLFileShortcut> findByToFileEntryId(
		long toFileEntryId, int start, int end);

	/**
	* Returns an ordered range of all the document library file shortcuts where toFileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param toFileEntryId the to file entry ID
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file shortcuts
	*/
	public java.util.List<DLFileShortcut> findByToFileEntryId(
		long toFileEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator);

	/**
	* Returns an ordered range of all the document library file shortcuts where toFileEntryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param toFileEntryId the to file entry ID
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library file shortcuts
	*/
	public java.util.List<DLFileShortcut> findByToFileEntryId(
		long toFileEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first document library file shortcut in the ordered set where toFileEntryId = &#63;.
	*
	* @param toFileEntryId the to file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file shortcut
	* @throws NoSuchFileShortcutException if a matching document library file shortcut could not be found
	*/
	public DLFileShortcut findByToFileEntryId_First(long toFileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException;

	/**
	* Returns the first document library file shortcut in the ordered set where toFileEntryId = &#63;.
	*
	* @param toFileEntryId the to file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	*/
	public DLFileShortcut fetchByToFileEntryId_First(long toFileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator);

	/**
	* Returns the last document library file shortcut in the ordered set where toFileEntryId = &#63;.
	*
	* @param toFileEntryId the to file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file shortcut
	* @throws NoSuchFileShortcutException if a matching document library file shortcut could not be found
	*/
	public DLFileShortcut findByToFileEntryId_Last(long toFileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException;

	/**
	* Returns the last document library file shortcut in the ordered set where toFileEntryId = &#63;.
	*
	* @param toFileEntryId the to file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	*/
	public DLFileShortcut fetchByToFileEntryId_Last(long toFileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator);

	/**
	* Returns the document library file shortcuts before and after the current document library file shortcut in the ordered set where toFileEntryId = &#63;.
	*
	* @param fileShortcutId the primary key of the current document library file shortcut
	* @param toFileEntryId the to file entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file shortcut
	* @throws NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	*/
	public DLFileShortcut[] findByToFileEntryId_PrevAndNext(
		long fileShortcutId, long toFileEntryId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException;

	/**
	* Removes all the document library file shortcuts where toFileEntryId = &#63; from the database.
	*
	* @param toFileEntryId the to file entry ID
	*/
	public void removeByToFileEntryId(long toFileEntryId);

	/**
	* Returns the number of document library file shortcuts where toFileEntryId = &#63;.
	*
	* @param toFileEntryId the to file entry ID
	* @return the number of matching document library file shortcuts
	*/
	public int countByToFileEntryId(long toFileEntryId);

	/**
	* Returns all the document library file shortcuts where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @return the matching document library file shortcuts
	*/
	public java.util.List<DLFileShortcut> findByG_F(long groupId, long folderId);

	/**
	* Returns a range of all the document library file shortcuts where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @return the range of matching document library file shortcuts
	*/
	public java.util.List<DLFileShortcut> findByG_F(long groupId,
		long folderId, int start, int end);

	/**
	* Returns an ordered range of all the document library file shortcuts where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file shortcuts
	*/
	public java.util.List<DLFileShortcut> findByG_F(long groupId,
		long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator);

	/**
	* Returns an ordered range of all the document library file shortcuts where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library file shortcuts
	*/
	public java.util.List<DLFileShortcut> findByG_F(long groupId,
		long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file shortcut
	* @throws NoSuchFileShortcutException if a matching document library file shortcut could not be found
	*/
	public DLFileShortcut findByG_F_First(long groupId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException;

	/**
	* Returns the first document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	*/
	public DLFileShortcut fetchByG_F_First(long groupId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator);

	/**
	* Returns the last document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file shortcut
	* @throws NoSuchFileShortcutException if a matching document library file shortcut could not be found
	*/
	public DLFileShortcut findByG_F_Last(long groupId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException;

	/**
	* Returns the last document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	*/
	public DLFileShortcut fetchByG_F_Last(long groupId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator);

	/**
	* Returns the document library file shortcuts before and after the current document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63;.
	*
	* @param fileShortcutId the primary key of the current document library file shortcut
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file shortcut
	* @throws NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	*/
	public DLFileShortcut[] findByG_F_PrevAndNext(long fileShortcutId,
		long groupId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException;

	/**
	* Returns all the document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @return the matching document library file shortcuts that the user has permission to view
	*/
	public java.util.List<DLFileShortcut> filterFindByG_F(long groupId,
		long folderId);

	/**
	* Returns a range of all the document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @return the range of matching document library file shortcuts that the user has permission to view
	*/
	public java.util.List<DLFileShortcut> filterFindByG_F(long groupId,
		long folderId, int start, int end);

	/**
	* Returns an ordered range of all the document library file shortcuts that the user has permissions to view where groupId = &#63; and folderId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file shortcuts that the user has permission to view
	*/
	public java.util.List<DLFileShortcut> filterFindByG_F(long groupId,
		long folderId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator);

	/**
	* Returns the document library file shortcuts before and after the current document library file shortcut in the ordered set of document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63;.
	*
	* @param fileShortcutId the primary key of the current document library file shortcut
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file shortcut
	* @throws NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	*/
	public DLFileShortcut[] filterFindByG_F_PrevAndNext(long fileShortcutId,
		long groupId, long folderId,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException;

	/**
	* Removes all the document library file shortcuts where groupId = &#63; and folderId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	*/
	public void removeByG_F(long groupId, long folderId);

	/**
	* Returns the number of document library file shortcuts where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @return the number of matching document library file shortcuts
	*/
	public int countByG_F(long groupId, long folderId);

	/**
	* Returns the number of document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @return the number of matching document library file shortcuts that the user has permission to view
	*/
	public int filterCountByG_F(long groupId, long folderId);

	/**
	* Returns all the document library file shortcuts where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @return the matching document library file shortcuts
	*/
	public java.util.List<DLFileShortcut> findByC_NotS(long companyId,
		int status);

	/**
	* Returns a range of all the document library file shortcuts where companyId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @return the range of matching document library file shortcuts
	*/
	public java.util.List<DLFileShortcut> findByC_NotS(long companyId,
		int status, int start, int end);

	/**
	* Returns an ordered range of all the document library file shortcuts where companyId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file shortcuts
	*/
	public java.util.List<DLFileShortcut> findByC_NotS(long companyId,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator);

	/**
	* Returns an ordered range of all the document library file shortcuts where companyId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param status the status
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library file shortcuts
	*/
	public java.util.List<DLFileShortcut> findByC_NotS(long companyId,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first document library file shortcut in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file shortcut
	* @throws NoSuchFileShortcutException if a matching document library file shortcut could not be found
	*/
	public DLFileShortcut findByC_NotS_First(long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException;

	/**
	* Returns the first document library file shortcut in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	*/
	public DLFileShortcut fetchByC_NotS_First(long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator);

	/**
	* Returns the last document library file shortcut in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file shortcut
	* @throws NoSuchFileShortcutException if a matching document library file shortcut could not be found
	*/
	public DLFileShortcut findByC_NotS_Last(long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException;

	/**
	* Returns the last document library file shortcut in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	*/
	public DLFileShortcut fetchByC_NotS_Last(long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator);

	/**
	* Returns the document library file shortcuts before and after the current document library file shortcut in the ordered set where companyId = &#63; and status &ne; &#63;.
	*
	* @param fileShortcutId the primary key of the current document library file shortcut
	* @param companyId the company ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file shortcut
	* @throws NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	*/
	public DLFileShortcut[] findByC_NotS_PrevAndNext(long fileShortcutId,
		long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException;

	/**
	* Removes all the document library file shortcuts where companyId = &#63; and status &ne; &#63; from the database.
	*
	* @param companyId the company ID
	* @param status the status
	*/
	public void removeByC_NotS(long companyId, int status);

	/**
	* Returns the number of document library file shortcuts where companyId = &#63; and status &ne; &#63;.
	*
	* @param companyId the company ID
	* @param status the status
	* @return the number of matching document library file shortcuts
	*/
	public int countByC_NotS(long companyId, int status);

	/**
	* Returns all the document library file shortcuts where groupId = &#63; and folderId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @return the matching document library file shortcuts
	*/
	public java.util.List<DLFileShortcut> findByG_F_A(long groupId,
		long folderId, boolean active);

	/**
	* Returns a range of all the document library file shortcuts where groupId = &#63; and folderId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @return the range of matching document library file shortcuts
	*/
	public java.util.List<DLFileShortcut> findByG_F_A(long groupId,
		long folderId, boolean active, int start, int end);

	/**
	* Returns an ordered range of all the document library file shortcuts where groupId = &#63; and folderId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file shortcuts
	*/
	public java.util.List<DLFileShortcut> findByG_F_A(long groupId,
		long folderId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator);

	/**
	* Returns an ordered range of all the document library file shortcuts where groupId = &#63; and folderId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library file shortcuts
	*/
	public java.util.List<DLFileShortcut> findByG_F_A(long groupId,
		long folderId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file shortcut
	* @throws NoSuchFileShortcutException if a matching document library file shortcut could not be found
	*/
	public DLFileShortcut findByG_F_A_First(long groupId, long folderId,
		boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException;

	/**
	* Returns the first document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	*/
	public DLFileShortcut fetchByG_F_A_First(long groupId, long folderId,
		boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator);

	/**
	* Returns the last document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file shortcut
	* @throws NoSuchFileShortcutException if a matching document library file shortcut could not be found
	*/
	public DLFileShortcut findByG_F_A_Last(long groupId, long folderId,
		boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException;

	/**
	* Returns the last document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	*/
	public DLFileShortcut fetchByG_F_A_Last(long groupId, long folderId,
		boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator);

	/**
	* Returns the document library file shortcuts before and after the current document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and active = &#63;.
	*
	* @param fileShortcutId the primary key of the current document library file shortcut
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file shortcut
	* @throws NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	*/
	public DLFileShortcut[] findByG_F_A_PrevAndNext(long fileShortcutId,
		long groupId, long folderId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException;

	/**
	* Returns all the document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @return the matching document library file shortcuts that the user has permission to view
	*/
	public java.util.List<DLFileShortcut> filterFindByG_F_A(long groupId,
		long folderId, boolean active);

	/**
	* Returns a range of all the document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @return the range of matching document library file shortcuts that the user has permission to view
	*/
	public java.util.List<DLFileShortcut> filterFindByG_F_A(long groupId,
		long folderId, boolean active, int start, int end);

	/**
	* Returns an ordered range of all the document library file shortcuts that the user has permissions to view where groupId = &#63; and folderId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file shortcuts that the user has permission to view
	*/
	public java.util.List<DLFileShortcut> filterFindByG_F_A(long groupId,
		long folderId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator);

	/**
	* Returns the document library file shortcuts before and after the current document library file shortcut in the ordered set of document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63; and active = &#63;.
	*
	* @param fileShortcutId the primary key of the current document library file shortcut
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file shortcut
	* @throws NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	*/
	public DLFileShortcut[] filterFindByG_F_A_PrevAndNext(long fileShortcutId,
		long groupId, long folderId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException;

	/**
	* Removes all the document library file shortcuts where groupId = &#63; and folderId = &#63; and active = &#63; from the database.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	*/
	public void removeByG_F_A(long groupId, long folderId, boolean active);

	/**
	* Returns the number of document library file shortcuts where groupId = &#63; and folderId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @return the number of matching document library file shortcuts
	*/
	public int countByG_F_A(long groupId, long folderId, boolean active);

	/**
	* Returns the number of document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @return the number of matching document library file shortcuts that the user has permission to view
	*/
	public int filterCountByG_F_A(long groupId, long folderId, boolean active);

	/**
	* Returns all the document library file shortcuts where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param status the status
	* @return the matching document library file shortcuts
	*/
	public java.util.List<DLFileShortcut> findByG_F_A_S(long groupId,
		long folderId, boolean active, int status);

	/**
	* Returns a range of all the document library file shortcuts where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param status the status
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @return the range of matching document library file shortcuts
	*/
	public java.util.List<DLFileShortcut> findByG_F_A_S(long groupId,
		long folderId, boolean active, int status, int start, int end);

	/**
	* Returns an ordered range of all the document library file shortcuts where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param status the status
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file shortcuts
	*/
	public java.util.List<DLFileShortcut> findByG_F_A_S(long groupId,
		long folderId, boolean active, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator);

	/**
	* Returns an ordered range of all the document library file shortcuts where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param status the status
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching document library file shortcuts
	*/
	public java.util.List<DLFileShortcut> findByG_F_A_S(long groupId,
		long folderId, boolean active, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file shortcut
	* @throws NoSuchFileShortcutException if a matching document library file shortcut could not be found
	*/
	public DLFileShortcut findByG_F_A_S_First(long groupId, long folderId,
		boolean active, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException;

	/**
	* Returns the first document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	*/
	public DLFileShortcut fetchByG_F_A_S_First(long groupId, long folderId,
		boolean active, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator);

	/**
	* Returns the last document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file shortcut
	* @throws NoSuchFileShortcutException if a matching document library file shortcut could not be found
	*/
	public DLFileShortcut findByG_F_A_S_Last(long groupId, long folderId,
		boolean active, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException;

	/**
	* Returns the last document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	*/
	public DLFileShortcut fetchByG_F_A_S_Last(long groupId, long folderId,
		boolean active, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator);

	/**
	* Returns the document library file shortcuts before and after the current document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	*
	* @param fileShortcutId the primary key of the current document library file shortcut
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file shortcut
	* @throws NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	*/
	public DLFileShortcut[] findByG_F_A_S_PrevAndNext(long fileShortcutId,
		long groupId, long folderId, boolean active, int status,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException;

	/**
	* Returns all the document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param status the status
	* @return the matching document library file shortcuts that the user has permission to view
	*/
	public java.util.List<DLFileShortcut> filterFindByG_F_A_S(long groupId,
		long folderId, boolean active, int status);

	/**
	* Returns a range of all the document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param status the status
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @return the range of matching document library file shortcuts that the user has permission to view
	*/
	public java.util.List<DLFileShortcut> filterFindByG_F_A_S(long groupId,
		long folderId, boolean active, int status, int start, int end);

	/**
	* Returns an ordered range of all the document library file shortcuts that the user has permissions to view where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param status the status
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching document library file shortcuts that the user has permission to view
	*/
	public java.util.List<DLFileShortcut> filterFindByG_F_A_S(long groupId,
		long folderId, boolean active, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator);

	/**
	* Returns the document library file shortcuts before and after the current document library file shortcut in the ordered set of document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	*
	* @param fileShortcutId the primary key of the current document library file shortcut
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next document library file shortcut
	* @throws NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	*/
	public DLFileShortcut[] filterFindByG_F_A_S_PrevAndNext(
		long fileShortcutId, long groupId, long folderId, boolean active,
		int status,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException;

	/**
	* Removes all the document library file shortcuts where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param status the status
	*/
	public void removeByG_F_A_S(long groupId, long folderId, boolean active,
		int status);

	/**
	* Returns the number of document library file shortcuts where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param status the status
	* @return the number of matching document library file shortcuts
	*/
	public int countByG_F_A_S(long groupId, long folderId, boolean active,
		int status);

	/**
	* Returns the number of document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param folderId the folder ID
	* @param active the active
	* @param status the status
	* @return the number of matching document library file shortcuts that the user has permission to view
	*/
	public int filterCountByG_F_A_S(long groupId, long folderId,
		boolean active, int status);

	/**
	* Caches the document library file shortcut in the entity cache if it is enabled.
	*
	* @param dlFileShortcut the document library file shortcut
	*/
	public void cacheResult(DLFileShortcut dlFileShortcut);

	/**
	* Caches the document library file shortcuts in the entity cache if it is enabled.
	*
	* @param dlFileShortcuts the document library file shortcuts
	*/
	public void cacheResult(java.util.List<DLFileShortcut> dlFileShortcuts);

	/**
	* Creates a new document library file shortcut with the primary key. Does not add the document library file shortcut to the database.
	*
	* @param fileShortcutId the primary key for the new document library file shortcut
	* @return the new document library file shortcut
	*/
	public DLFileShortcut create(long fileShortcutId);

	/**
	* Removes the document library file shortcut with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fileShortcutId the primary key of the document library file shortcut
	* @return the document library file shortcut that was removed
	* @throws NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	*/
	public DLFileShortcut remove(long fileShortcutId)
		throws NoSuchFileShortcutException;

	public DLFileShortcut updateImpl(DLFileShortcut dlFileShortcut);

	/**
	* Returns the document library file shortcut with the primary key or throws a {@link NoSuchFileShortcutException} if it could not be found.
	*
	* @param fileShortcutId the primary key of the document library file shortcut
	* @return the document library file shortcut
	* @throws NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	*/
	public DLFileShortcut findByPrimaryKey(long fileShortcutId)
		throws NoSuchFileShortcutException;

	/**
	* Returns the document library file shortcut with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param fileShortcutId the primary key of the document library file shortcut
	* @return the document library file shortcut, or <code>null</code> if a document library file shortcut with the primary key could not be found
	*/
	public DLFileShortcut fetchByPrimaryKey(long fileShortcutId);

	@Override
	public java.util.Map<java.io.Serializable, DLFileShortcut> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the document library file shortcuts.
	*
	* @return the document library file shortcuts
	*/
	public java.util.List<DLFileShortcut> findAll();

	/**
	* Returns a range of all the document library file shortcuts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @return the range of document library file shortcuts
	*/
	public java.util.List<DLFileShortcut> findAll(int start, int end);

	/**
	* Returns an ordered range of all the document library file shortcuts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of document library file shortcuts
	*/
	public java.util.List<DLFileShortcut> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator);

	/**
	* Returns an ordered range of all the document library file shortcuts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of document library file shortcuts
	*/
	public java.util.List<DLFileShortcut> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileShortcut> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the document library file shortcuts from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of document library file shortcuts.
	*
	* @return the number of document library file shortcuts
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}