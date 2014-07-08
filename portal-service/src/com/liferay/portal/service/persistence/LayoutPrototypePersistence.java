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

package com.liferay.portal.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.model.LayoutPrototype;

/**
 * The persistence interface for the layout prototype service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPrototypePersistenceImpl
 * @see LayoutPrototypeUtil
 * @generated
 */
@ProviderType
public interface LayoutPrototypePersistence extends BasePersistence<LayoutPrototype> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link LayoutPrototypeUtil} to access the layout prototype persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the layout prototypes where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching layout prototypes
	*/
	public java.util.List<com.liferay.portal.model.LayoutPrototype> findByUuid(
		java.lang.String uuid);

	/**
	* Returns a range of all the layout prototypes where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of layout prototypes
	* @param end the upper bound of the range of layout prototypes (not inclusive)
	* @return the range of matching layout prototypes
	*/
	public java.util.List<com.liferay.portal.model.LayoutPrototype> findByUuid(
		java.lang.String uuid, int start, int end);

	/**
	* Returns an ordered range of all the layout prototypes where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of layout prototypes
	* @param end the upper bound of the range of layout prototypes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout prototypes
	*/
	public java.util.List<com.liferay.portal.model.LayoutPrototype> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first layout prototype in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout prototype
	* @throws com.liferay.portal.NoSuchLayoutPrototypeException if a matching layout prototype could not be found
	*/
	public com.liferay.portal.model.LayoutPrototype findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutPrototypeException;

	/**
	* Returns the first layout prototype in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout prototype, or <code>null</code> if a matching layout prototype could not be found
	*/
	public com.liferay.portal.model.LayoutPrototype fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last layout prototype in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout prototype
	* @throws com.liferay.portal.NoSuchLayoutPrototypeException if a matching layout prototype could not be found
	*/
	public com.liferay.portal.model.LayoutPrototype findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutPrototypeException;

	/**
	* Returns the last layout prototype in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout prototype, or <code>null</code> if a matching layout prototype could not be found
	*/
	public com.liferay.portal.model.LayoutPrototype fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the layout prototypes before and after the current layout prototype in the ordered set where uuid = &#63;.
	*
	* @param layoutPrototypeId the primary key of the current layout prototype
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout prototype
	* @throws com.liferay.portal.NoSuchLayoutPrototypeException if a layout prototype with the primary key could not be found
	*/
	public com.liferay.portal.model.LayoutPrototype[] findByUuid_PrevAndNext(
		long layoutPrototypeId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutPrototypeException;

	/**
	* Returns all the layout prototypes that the user has permission to view where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching layout prototypes that the user has permission to view
	*/
	public java.util.List<com.liferay.portal.model.LayoutPrototype> filterFindByUuid(
		java.lang.String uuid);

	/**
	* Returns a range of all the layout prototypes that the user has permission to view where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of layout prototypes
	* @param end the upper bound of the range of layout prototypes (not inclusive)
	* @return the range of matching layout prototypes that the user has permission to view
	*/
	public java.util.List<com.liferay.portal.model.LayoutPrototype> filterFindByUuid(
		java.lang.String uuid, int start, int end);

	/**
	* Returns an ordered range of all the layout prototypes that the user has permissions to view where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of layout prototypes
	* @param end the upper bound of the range of layout prototypes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout prototypes that the user has permission to view
	*/
	public java.util.List<com.liferay.portal.model.LayoutPrototype> filterFindByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the layout prototypes before and after the current layout prototype in the ordered set of layout prototypes that the user has permission to view where uuid = &#63;.
	*
	* @param layoutPrototypeId the primary key of the current layout prototype
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout prototype
	* @throws com.liferay.portal.NoSuchLayoutPrototypeException if a layout prototype with the primary key could not be found
	*/
	public com.liferay.portal.model.LayoutPrototype[] filterFindByUuid_PrevAndNext(
		long layoutPrototypeId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutPrototypeException;

	/**
	* Removes all the layout prototypes where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of layout prototypes where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching layout prototypes
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the number of layout prototypes that the user has permission to view where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching layout prototypes that the user has permission to view
	*/
	public int filterCountByUuid(java.lang.String uuid);

	/**
	* Returns all the layout prototypes where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching layout prototypes
	*/
	public java.util.List<com.liferay.portal.model.LayoutPrototype> findByUuid_C(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of all the layout prototypes where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of layout prototypes
	* @param end the upper bound of the range of layout prototypes (not inclusive)
	* @return the range of matching layout prototypes
	*/
	public java.util.List<com.liferay.portal.model.LayoutPrototype> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end);

	/**
	* Returns an ordered range of all the layout prototypes where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of layout prototypes
	* @param end the upper bound of the range of layout prototypes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout prototypes
	*/
	public java.util.List<com.liferay.portal.model.LayoutPrototype> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first layout prototype in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout prototype
	* @throws com.liferay.portal.NoSuchLayoutPrototypeException if a matching layout prototype could not be found
	*/
	public com.liferay.portal.model.LayoutPrototype findByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutPrototypeException;

	/**
	* Returns the first layout prototype in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout prototype, or <code>null</code> if a matching layout prototype could not be found
	*/
	public com.liferay.portal.model.LayoutPrototype fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last layout prototype in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout prototype
	* @throws com.liferay.portal.NoSuchLayoutPrototypeException if a matching layout prototype could not be found
	*/
	public com.liferay.portal.model.LayoutPrototype findByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutPrototypeException;

	/**
	* Returns the last layout prototype in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout prototype, or <code>null</code> if a matching layout prototype could not be found
	*/
	public com.liferay.portal.model.LayoutPrototype fetchByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the layout prototypes before and after the current layout prototype in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param layoutPrototypeId the primary key of the current layout prototype
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout prototype
	* @throws com.liferay.portal.NoSuchLayoutPrototypeException if a layout prototype with the primary key could not be found
	*/
	public com.liferay.portal.model.LayoutPrototype[] findByUuid_C_PrevAndNext(
		long layoutPrototypeId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutPrototypeException;

	/**
	* Returns all the layout prototypes that the user has permission to view where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching layout prototypes that the user has permission to view
	*/
	public java.util.List<com.liferay.portal.model.LayoutPrototype> filterFindByUuid_C(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of all the layout prototypes that the user has permission to view where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of layout prototypes
	* @param end the upper bound of the range of layout prototypes (not inclusive)
	* @return the range of matching layout prototypes that the user has permission to view
	*/
	public java.util.List<com.liferay.portal.model.LayoutPrototype> filterFindByUuid_C(
		java.lang.String uuid, long companyId, int start, int end);

	/**
	* Returns an ordered range of all the layout prototypes that the user has permissions to view where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of layout prototypes
	* @param end the upper bound of the range of layout prototypes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout prototypes that the user has permission to view
	*/
	public java.util.List<com.liferay.portal.model.LayoutPrototype> filterFindByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the layout prototypes before and after the current layout prototype in the ordered set of layout prototypes that the user has permission to view where uuid = &#63; and companyId = &#63;.
	*
	* @param layoutPrototypeId the primary key of the current layout prototype
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout prototype
	* @throws com.liferay.portal.NoSuchLayoutPrototypeException if a layout prototype with the primary key could not be found
	*/
	public com.liferay.portal.model.LayoutPrototype[] filterFindByUuid_C_PrevAndNext(
		long layoutPrototypeId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutPrototypeException;

	/**
	* Removes all the layout prototypes where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of layout prototypes where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching layout prototypes
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of layout prototypes that the user has permission to view where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching layout prototypes that the user has permission to view
	*/
	public int filterCountByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the layout prototypes where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching layout prototypes
	*/
	public java.util.List<com.liferay.portal.model.LayoutPrototype> findByCompanyId(
		long companyId);

	/**
	* Returns a range of all the layout prototypes where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of layout prototypes
	* @param end the upper bound of the range of layout prototypes (not inclusive)
	* @return the range of matching layout prototypes
	*/
	public java.util.List<com.liferay.portal.model.LayoutPrototype> findByCompanyId(
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the layout prototypes where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of layout prototypes
	* @param end the upper bound of the range of layout prototypes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout prototypes
	*/
	public java.util.List<com.liferay.portal.model.LayoutPrototype> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first layout prototype in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout prototype
	* @throws com.liferay.portal.NoSuchLayoutPrototypeException if a matching layout prototype could not be found
	*/
	public com.liferay.portal.model.LayoutPrototype findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutPrototypeException;

	/**
	* Returns the first layout prototype in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout prototype, or <code>null</code> if a matching layout prototype could not be found
	*/
	public com.liferay.portal.model.LayoutPrototype fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last layout prototype in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout prototype
	* @throws com.liferay.portal.NoSuchLayoutPrototypeException if a matching layout prototype could not be found
	*/
	public com.liferay.portal.model.LayoutPrototype findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutPrototypeException;

	/**
	* Returns the last layout prototype in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout prototype, or <code>null</code> if a matching layout prototype could not be found
	*/
	public com.liferay.portal.model.LayoutPrototype fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the layout prototypes before and after the current layout prototype in the ordered set where companyId = &#63;.
	*
	* @param layoutPrototypeId the primary key of the current layout prototype
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout prototype
	* @throws com.liferay.portal.NoSuchLayoutPrototypeException if a layout prototype with the primary key could not be found
	*/
	public com.liferay.portal.model.LayoutPrototype[] findByCompanyId_PrevAndNext(
		long layoutPrototypeId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutPrototypeException;

	/**
	* Returns all the layout prototypes that the user has permission to view where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching layout prototypes that the user has permission to view
	*/
	public java.util.List<com.liferay.portal.model.LayoutPrototype> filterFindByCompanyId(
		long companyId);

	/**
	* Returns a range of all the layout prototypes that the user has permission to view where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of layout prototypes
	* @param end the upper bound of the range of layout prototypes (not inclusive)
	* @return the range of matching layout prototypes that the user has permission to view
	*/
	public java.util.List<com.liferay.portal.model.LayoutPrototype> filterFindByCompanyId(
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the layout prototypes that the user has permissions to view where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of layout prototypes
	* @param end the upper bound of the range of layout prototypes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout prototypes that the user has permission to view
	*/
	public java.util.List<com.liferay.portal.model.LayoutPrototype> filterFindByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the layout prototypes before and after the current layout prototype in the ordered set of layout prototypes that the user has permission to view where companyId = &#63;.
	*
	* @param layoutPrototypeId the primary key of the current layout prototype
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout prototype
	* @throws com.liferay.portal.NoSuchLayoutPrototypeException if a layout prototype with the primary key could not be found
	*/
	public com.liferay.portal.model.LayoutPrototype[] filterFindByCompanyId_PrevAndNext(
		long layoutPrototypeId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutPrototypeException;

	/**
	* Removes all the layout prototypes where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of layout prototypes where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching layout prototypes
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns the number of layout prototypes that the user has permission to view where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching layout prototypes that the user has permission to view
	*/
	public int filterCountByCompanyId(long companyId);

	/**
	* Returns all the layout prototypes where companyId = &#63; and active = &#63;.
	*
	* @param companyId the company ID
	* @param active the active
	* @return the matching layout prototypes
	*/
	public java.util.List<com.liferay.portal.model.LayoutPrototype> findByC_A(
		long companyId, boolean active);

	/**
	* Returns a range of all the layout prototypes where companyId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param active the active
	* @param start the lower bound of the range of layout prototypes
	* @param end the upper bound of the range of layout prototypes (not inclusive)
	* @return the range of matching layout prototypes
	*/
	public java.util.List<com.liferay.portal.model.LayoutPrototype> findByC_A(
		long companyId, boolean active, int start, int end);

	/**
	* Returns an ordered range of all the layout prototypes where companyId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param active the active
	* @param start the lower bound of the range of layout prototypes
	* @param end the upper bound of the range of layout prototypes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout prototypes
	*/
	public java.util.List<com.liferay.portal.model.LayoutPrototype> findByC_A(
		long companyId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first layout prototype in the ordered set where companyId = &#63; and active = &#63;.
	*
	* @param companyId the company ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout prototype
	* @throws com.liferay.portal.NoSuchLayoutPrototypeException if a matching layout prototype could not be found
	*/
	public com.liferay.portal.model.LayoutPrototype findByC_A_First(
		long companyId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutPrototypeException;

	/**
	* Returns the first layout prototype in the ordered set where companyId = &#63; and active = &#63;.
	*
	* @param companyId the company ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching layout prototype, or <code>null</code> if a matching layout prototype could not be found
	*/
	public com.liferay.portal.model.LayoutPrototype fetchByC_A_First(
		long companyId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last layout prototype in the ordered set where companyId = &#63; and active = &#63;.
	*
	* @param companyId the company ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout prototype
	* @throws com.liferay.portal.NoSuchLayoutPrototypeException if a matching layout prototype could not be found
	*/
	public com.liferay.portal.model.LayoutPrototype findByC_A_Last(
		long companyId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutPrototypeException;

	/**
	* Returns the last layout prototype in the ordered set where companyId = &#63; and active = &#63;.
	*
	* @param companyId the company ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching layout prototype, or <code>null</code> if a matching layout prototype could not be found
	*/
	public com.liferay.portal.model.LayoutPrototype fetchByC_A_Last(
		long companyId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the layout prototypes before and after the current layout prototype in the ordered set where companyId = &#63; and active = &#63;.
	*
	* @param layoutPrototypeId the primary key of the current layout prototype
	* @param companyId the company ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout prototype
	* @throws com.liferay.portal.NoSuchLayoutPrototypeException if a layout prototype with the primary key could not be found
	*/
	public com.liferay.portal.model.LayoutPrototype[] findByC_A_PrevAndNext(
		long layoutPrototypeId, long companyId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutPrototypeException;

	/**
	* Returns all the layout prototypes that the user has permission to view where companyId = &#63; and active = &#63;.
	*
	* @param companyId the company ID
	* @param active the active
	* @return the matching layout prototypes that the user has permission to view
	*/
	public java.util.List<com.liferay.portal.model.LayoutPrototype> filterFindByC_A(
		long companyId, boolean active);

	/**
	* Returns a range of all the layout prototypes that the user has permission to view where companyId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param active the active
	* @param start the lower bound of the range of layout prototypes
	* @param end the upper bound of the range of layout prototypes (not inclusive)
	* @return the range of matching layout prototypes that the user has permission to view
	*/
	public java.util.List<com.liferay.portal.model.LayoutPrototype> filterFindByC_A(
		long companyId, boolean active, int start, int end);

	/**
	* Returns an ordered range of all the layout prototypes that the user has permissions to view where companyId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param active the active
	* @param start the lower bound of the range of layout prototypes
	* @param end the upper bound of the range of layout prototypes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching layout prototypes that the user has permission to view
	*/
	public java.util.List<com.liferay.portal.model.LayoutPrototype> filterFindByC_A(
		long companyId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the layout prototypes before and after the current layout prototype in the ordered set of layout prototypes that the user has permission to view where companyId = &#63; and active = &#63;.
	*
	* @param layoutPrototypeId the primary key of the current layout prototype
	* @param companyId the company ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next layout prototype
	* @throws com.liferay.portal.NoSuchLayoutPrototypeException if a layout prototype with the primary key could not be found
	*/
	public com.liferay.portal.model.LayoutPrototype[] filterFindByC_A_PrevAndNext(
		long layoutPrototypeId, long companyId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchLayoutPrototypeException;

	/**
	* Removes all the layout prototypes where companyId = &#63; and active = &#63; from the database.
	*
	* @param companyId the company ID
	* @param active the active
	*/
	public void removeByC_A(long companyId, boolean active);

	/**
	* Returns the number of layout prototypes where companyId = &#63; and active = &#63;.
	*
	* @param companyId the company ID
	* @param active the active
	* @return the number of matching layout prototypes
	*/
	public int countByC_A(long companyId, boolean active);

	/**
	* Returns the number of layout prototypes that the user has permission to view where companyId = &#63; and active = &#63;.
	*
	* @param companyId the company ID
	* @param active the active
	* @return the number of matching layout prototypes that the user has permission to view
	*/
	public int filterCountByC_A(long companyId, boolean active);

	/**
	* Caches the layout prototype in the entity cache if it is enabled.
	*
	* @param layoutPrototype the layout prototype
	*/
	public void cacheResult(
		com.liferay.portal.model.LayoutPrototype layoutPrototype);

	/**
	* Caches the layout prototypes in the entity cache if it is enabled.
	*
	* @param layoutPrototypes the layout prototypes
	*/
	public void cacheResult(
		java.util.List<com.liferay.portal.model.LayoutPrototype> layoutPrototypes);

	/**
	* Creates a new layout prototype with the primary key. Does not add the layout prototype to the database.
	*
	* @param layoutPrototypeId the primary key for the new layout prototype
	* @return the new layout prototype
	*/
	public com.liferay.portal.model.LayoutPrototype create(
		long layoutPrototypeId);

	/**
	* Removes the layout prototype with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutPrototypeId the primary key of the layout prototype
	* @return the layout prototype that was removed
	* @throws com.liferay.portal.NoSuchLayoutPrototypeException if a layout prototype with the primary key could not be found
	*/
	public com.liferay.portal.model.LayoutPrototype remove(
		long layoutPrototypeId)
		throws com.liferay.portal.NoSuchLayoutPrototypeException;

	public com.liferay.portal.model.LayoutPrototype updateImpl(
		com.liferay.portal.model.LayoutPrototype layoutPrototype);

	/**
	* Returns the layout prototype with the primary key or throws a {@link com.liferay.portal.NoSuchLayoutPrototypeException} if it could not be found.
	*
	* @param layoutPrototypeId the primary key of the layout prototype
	* @return the layout prototype
	* @throws com.liferay.portal.NoSuchLayoutPrototypeException if a layout prototype with the primary key could not be found
	*/
	public com.liferay.portal.model.LayoutPrototype findByPrimaryKey(
		long layoutPrototypeId)
		throws com.liferay.portal.NoSuchLayoutPrototypeException;

	/**
	* Returns the layout prototype with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param layoutPrototypeId the primary key of the layout prototype
	* @return the layout prototype, or <code>null</code> if a layout prototype with the primary key could not be found
	*/
	public com.liferay.portal.model.LayoutPrototype fetchByPrimaryKey(
		long layoutPrototypeId);

	@Override
	public java.util.Map<java.io.Serializable, com.liferay.portal.model.LayoutPrototype> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the layout prototypes.
	*
	* @return the layout prototypes
	*/
	public java.util.List<com.liferay.portal.model.LayoutPrototype> findAll();

	/**
	* Returns a range of all the layout prototypes.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout prototypes
	* @param end the upper bound of the range of layout prototypes (not inclusive)
	* @return the range of layout prototypes
	*/
	public java.util.List<com.liferay.portal.model.LayoutPrototype> findAll(
		int start, int end);

	/**
	* Returns an ordered range of all the layout prototypes.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.LayoutPrototypeModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of layout prototypes
	* @param end the upper bound of the range of layout prototypes (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of layout prototypes
	*/
	public java.util.List<com.liferay.portal.model.LayoutPrototype> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Removes all the layout prototypes from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of layout prototypes.
	*
	* @return the number of layout prototypes
	*/
	public int countAll();
}