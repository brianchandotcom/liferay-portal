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

import com.liferay.dynamic.data.mapping.exception.NoSuchTemplateException;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * The persistence interface for the d d m template service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.dynamic.data.mapping.service.persistence.impl.DDMTemplatePersistenceImpl
 * @see DDMTemplateUtil
 * @generated
 */
@ProviderType
public interface DDMTemplatePersistence extends BasePersistence<DDMTemplate> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link DDMTemplateUtil} to access the d d m template persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the d d m templates where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the d d m templates where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByUuid(java.lang.String uuid,
		int start, int end);

	/**
	* Returns an ordered range of all the d d m templates where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator);

	/**
	* Returns an ordered range of all the d d m templates where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first d d m template in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template
	* @throws NoSuchTemplateException if a matching d d m template could not be found
	*/
	public DDMTemplate findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException;

	/**
	* Returns the first d d m template in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public DDMTemplate fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator);

	/**
	* Returns the last d d m template in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template
	* @throws NoSuchTemplateException if a matching d d m template could not be found
	*/
	public DDMTemplate findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException;

	/**
	* Returns the last d d m template in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public DDMTemplate fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator);

	/**
	* Returns the d d m templates before and after the current d d m template in the ordered set where uuid = &#63;.
	*
	* @param templateId the primary key of the current d d m template
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template
	* @throws NoSuchTemplateException if a d d m template with the primary key could not be found
	*/
	public DDMTemplate[] findByUuid_PrevAndNext(long templateId,
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException;

	/**
	* Removes all the d d m templates where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of d d m templates where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching d d m templates
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the d d m template where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchTemplateException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching d d m template
	* @throws NoSuchTemplateException if a matching d d m template could not be found
	*/
	public DDMTemplate findByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchTemplateException;

	/**
	* Returns the d d m template where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public DDMTemplate fetchByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns the d d m template where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public DDMTemplate fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the d d m template where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the d d m template that was removed
	*/
	public DDMTemplate removeByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchTemplateException;

	/**
	* Returns the number of d d m templates where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching d d m templates
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the d d m templates where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByUuid_C(java.lang.String uuid,
		long companyId);

	/**
	* Returns a range of all the d d m templates where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the d d m templates where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator);

	/**
	* Returns an ordered range of all the d d m templates where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first d d m template in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template
	* @throws NoSuchTemplateException if a matching d d m template could not be found
	*/
	public DDMTemplate findByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException;

	/**
	* Returns the first d d m template in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public DDMTemplate fetchByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator);

	/**
	* Returns the last d d m template in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template
	* @throws NoSuchTemplateException if a matching d d m template could not be found
	*/
	public DDMTemplate findByUuid_C_Last(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException;

	/**
	* Returns the last d d m template in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public DDMTemplate fetchByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator);

	/**
	* Returns the d d m templates before and after the current d d m template in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param templateId the primary key of the current d d m template
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template
	* @throws NoSuchTemplateException if a d d m template with the primary key could not be found
	*/
	public DDMTemplate[] findByUuid_C_PrevAndNext(long templateId,
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException;

	/**
	* Removes all the d d m templates where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of d d m templates where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching d d m templates
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the d d m templates where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByGroupId(long groupId);

	/**
	* Returns a range of all the d d m templates where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByGroupId(long groupId, int start,
		int end);

	/**
	* Returns an ordered range of all the d d m templates where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator);

	/**
	* Returns an ordered range of all the d d m templates where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first d d m template in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template
	* @throws NoSuchTemplateException if a matching d d m template could not be found
	*/
	public DDMTemplate findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException;

	/**
	* Returns the first d d m template in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public DDMTemplate fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator);

	/**
	* Returns the last d d m template in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template
	* @throws NoSuchTemplateException if a matching d d m template could not be found
	*/
	public DDMTemplate findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException;

	/**
	* Returns the last d d m template in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public DDMTemplate fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator);

	/**
	* Returns the d d m templates before and after the current d d m template in the ordered set where groupId = &#63;.
	*
	* @param templateId the primary key of the current d d m template
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template
	* @throws NoSuchTemplateException if a d d m template with the primary key could not be found
	*/
	public DDMTemplate[] findByGroupId_PrevAndNext(long templateId,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException;

	/**
	* Returns all the d d m templates that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching d d m templates that the user has permission to view
	*/
	public java.util.List<DDMTemplate> filterFindByGroupId(long groupId);

	/**
	* Returns a range of all the d d m templates that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates that the user has permission to view
	*/
	public java.util.List<DDMTemplate> filterFindByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the d d m templates that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates that the user has permission to view
	*/
	public java.util.List<DDMTemplate> filterFindByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator);

	/**
	* Returns the d d m templates before and after the current d d m template in the ordered set of d d m templates that the user has permission to view where groupId = &#63;.
	*
	* @param templateId the primary key of the current d d m template
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template
	* @throws NoSuchTemplateException if a d d m template with the primary key could not be found
	*/
	public DDMTemplate[] filterFindByGroupId_PrevAndNext(long templateId,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException;

	/**
	* Removes all the d d m templates where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of d d m templates where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching d d m templates
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns the number of d d m templates that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching d d m templates that the user has permission to view
	*/
	public int filterCountByGroupId(long groupId);

	/**
	* Returns all the d d m templates where classPK = &#63;.
	*
	* @param classPK the class p k
	* @return the matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByClassPK(long classPK);

	/**
	* Returns a range of all the d d m templates where classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classPK the class p k
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByClassPK(long classPK, int start,
		int end);

	/**
	* Returns an ordered range of all the d d m templates where classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classPK the class p k
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByClassPK(long classPK, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator);

	/**
	* Returns an ordered range of all the d d m templates where classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classPK the class p k
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByClassPK(long classPK, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first d d m template in the ordered set where classPK = &#63;.
	*
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template
	* @throws NoSuchTemplateException if a matching d d m template could not be found
	*/
	public DDMTemplate findByClassPK_First(long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException;

	/**
	* Returns the first d d m template in the ordered set where classPK = &#63;.
	*
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public DDMTemplate fetchByClassPK_First(long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator);

	/**
	* Returns the last d d m template in the ordered set where classPK = &#63;.
	*
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template
	* @throws NoSuchTemplateException if a matching d d m template could not be found
	*/
	public DDMTemplate findByClassPK_Last(long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException;

	/**
	* Returns the last d d m template in the ordered set where classPK = &#63;.
	*
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public DDMTemplate fetchByClassPK_Last(long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator);

	/**
	* Returns the d d m templates before and after the current d d m template in the ordered set where classPK = &#63;.
	*
	* @param templateId the primary key of the current d d m template
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template
	* @throws NoSuchTemplateException if a d d m template with the primary key could not be found
	*/
	public DDMTemplate[] findByClassPK_PrevAndNext(long templateId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException;

	/**
	* Removes all the d d m templates where classPK = &#63; from the database.
	*
	* @param classPK the class p k
	*/
	public void removeByClassPK(long classPK);

	/**
	* Returns the number of d d m templates where classPK = &#63;.
	*
	* @param classPK the class p k
	* @return the number of matching d d m templates
	*/
	public int countByClassPK(long classPK);

	/**
	* Returns all the d d m templates where templateKey = &#63;.
	*
	* @param templateKey the template key
	* @return the matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByTemplateKey(
		java.lang.String templateKey);

	/**
	* Returns a range of all the d d m templates where templateKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param templateKey the template key
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByTemplateKey(
		java.lang.String templateKey, int start, int end);

	/**
	* Returns an ordered range of all the d d m templates where templateKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param templateKey the template key
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByTemplateKey(
		java.lang.String templateKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator);

	/**
	* Returns an ordered range of all the d d m templates where templateKey = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param templateKey the template key
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByTemplateKey(
		java.lang.String templateKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first d d m template in the ordered set where templateKey = &#63;.
	*
	* @param templateKey the template key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template
	* @throws NoSuchTemplateException if a matching d d m template could not be found
	*/
	public DDMTemplate findByTemplateKey_First(java.lang.String templateKey,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException;

	/**
	* Returns the first d d m template in the ordered set where templateKey = &#63;.
	*
	* @param templateKey the template key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public DDMTemplate fetchByTemplateKey_First(java.lang.String templateKey,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator);

	/**
	* Returns the last d d m template in the ordered set where templateKey = &#63;.
	*
	* @param templateKey the template key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template
	* @throws NoSuchTemplateException if a matching d d m template could not be found
	*/
	public DDMTemplate findByTemplateKey_Last(java.lang.String templateKey,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException;

	/**
	* Returns the last d d m template in the ordered set where templateKey = &#63;.
	*
	* @param templateKey the template key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public DDMTemplate fetchByTemplateKey_Last(java.lang.String templateKey,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator);

	/**
	* Returns the d d m templates before and after the current d d m template in the ordered set where templateKey = &#63;.
	*
	* @param templateId the primary key of the current d d m template
	* @param templateKey the template key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template
	* @throws NoSuchTemplateException if a d d m template with the primary key could not be found
	*/
	public DDMTemplate[] findByTemplateKey_PrevAndNext(long templateId,
		java.lang.String templateKey,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException;

	/**
	* Removes all the d d m templates where templateKey = &#63; from the database.
	*
	* @param templateKey the template key
	*/
	public void removeByTemplateKey(java.lang.String templateKey);

	/**
	* Returns the number of d d m templates where templateKey = &#63;.
	*
	* @param templateKey the template key
	* @return the number of matching d d m templates
	*/
	public int countByTemplateKey(java.lang.String templateKey);

	/**
	* Returns all the d d m templates where type = &#63;.
	*
	* @param type the type
	* @return the matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByType(java.lang.String type);

	/**
	* Returns a range of all the d d m templates where type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param type the type
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByType(java.lang.String type,
		int start, int end);

	/**
	* Returns an ordered range of all the d d m templates where type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param type the type
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByType(java.lang.String type,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator);

	/**
	* Returns an ordered range of all the d d m templates where type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param type the type
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByType(java.lang.String type,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first d d m template in the ordered set where type = &#63;.
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template
	* @throws NoSuchTemplateException if a matching d d m template could not be found
	*/
	public DDMTemplate findByType_First(java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException;

	/**
	* Returns the first d d m template in the ordered set where type = &#63;.
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public DDMTemplate fetchByType_First(java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator);

	/**
	* Returns the last d d m template in the ordered set where type = &#63;.
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template
	* @throws NoSuchTemplateException if a matching d d m template could not be found
	*/
	public DDMTemplate findByType_Last(java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException;

	/**
	* Returns the last d d m template in the ordered set where type = &#63;.
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public DDMTemplate fetchByType_Last(java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator);

	/**
	* Returns the d d m templates before and after the current d d m template in the ordered set where type = &#63;.
	*
	* @param templateId the primary key of the current d d m template
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template
	* @throws NoSuchTemplateException if a d d m template with the primary key could not be found
	*/
	public DDMTemplate[] findByType_PrevAndNext(long templateId,
		java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException;

	/**
	* Removes all the d d m templates where type = &#63; from the database.
	*
	* @param type the type
	*/
	public void removeByType(java.lang.String type);

	/**
	* Returns the number of d d m templates where type = &#63;.
	*
	* @param type the type
	* @return the number of matching d d m templates
	*/
	public int countByType(java.lang.String type);

	/**
	* Returns all the d d m templates where language = &#63;.
	*
	* @param language the language
	* @return the matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByLanguage(java.lang.String language);

	/**
	* Returns a range of all the d d m templates where language = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param language the language
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByLanguage(
		java.lang.String language, int start, int end);

	/**
	* Returns an ordered range of all the d d m templates where language = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param language the language
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByLanguage(
		java.lang.String language, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator);

	/**
	* Returns an ordered range of all the d d m templates where language = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param language the language
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByLanguage(
		java.lang.String language, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first d d m template in the ordered set where language = &#63;.
	*
	* @param language the language
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template
	* @throws NoSuchTemplateException if a matching d d m template could not be found
	*/
	public DDMTemplate findByLanguage_First(java.lang.String language,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException;

	/**
	* Returns the first d d m template in the ordered set where language = &#63;.
	*
	* @param language the language
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public DDMTemplate fetchByLanguage_First(java.lang.String language,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator);

	/**
	* Returns the last d d m template in the ordered set where language = &#63;.
	*
	* @param language the language
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template
	* @throws NoSuchTemplateException if a matching d d m template could not be found
	*/
	public DDMTemplate findByLanguage_Last(java.lang.String language,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException;

	/**
	* Returns the last d d m template in the ordered set where language = &#63;.
	*
	* @param language the language
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public DDMTemplate fetchByLanguage_Last(java.lang.String language,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator);

	/**
	* Returns the d d m templates before and after the current d d m template in the ordered set where language = &#63;.
	*
	* @param templateId the primary key of the current d d m template
	* @param language the language
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template
	* @throws NoSuchTemplateException if a d d m template with the primary key could not be found
	*/
	public DDMTemplate[] findByLanguage_PrevAndNext(long templateId,
		java.lang.String language,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException;

	/**
	* Removes all the d d m templates where language = &#63; from the database.
	*
	* @param language the language
	*/
	public void removeByLanguage(java.lang.String language);

	/**
	* Returns the number of d d m templates where language = &#63;.
	*
	* @param language the language
	* @return the number of matching d d m templates
	*/
	public int countByLanguage(java.lang.String language);

	/**
	* Returns the d d m template where smallImageId = &#63; or throws a {@link NoSuchTemplateException} if it could not be found.
	*
	* @param smallImageId the small image ID
	* @return the matching d d m template
	* @throws NoSuchTemplateException if a matching d d m template could not be found
	*/
	public DDMTemplate findBySmallImageId(long smallImageId)
		throws NoSuchTemplateException;

	/**
	* Returns the d d m template where smallImageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param smallImageId the small image ID
	* @return the matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public DDMTemplate fetchBySmallImageId(long smallImageId);

	/**
	* Returns the d d m template where smallImageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param smallImageId the small image ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public DDMTemplate fetchBySmallImageId(long smallImageId,
		boolean retrieveFromCache);

	/**
	* Removes the d d m template where smallImageId = &#63; from the database.
	*
	* @param smallImageId the small image ID
	* @return the d d m template that was removed
	*/
	public DDMTemplate removeBySmallImageId(long smallImageId)
		throws NoSuchTemplateException;

	/**
	* Returns the number of d d m templates where smallImageId = &#63;.
	*
	* @param smallImageId the small image ID
	* @return the number of matching d d m templates
	*/
	public int countBySmallImageId(long smallImageId);

	/**
	* Returns all the d d m templates where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @return the matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByG_C(long groupId, long classNameId);

	/**
	* Returns a range of all the d d m templates where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByG_C(long groupId,
		long classNameId, int start, int end);

	/**
	* Returns an ordered range of all the d d m templates where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByG_C(long groupId,
		long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator);

	/**
	* Returns an ordered range of all the d d m templates where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByG_C(long groupId,
		long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first d d m template in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template
	* @throws NoSuchTemplateException if a matching d d m template could not be found
	*/
	public DDMTemplate findByG_C_First(long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException;

	/**
	* Returns the first d d m template in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public DDMTemplate fetchByG_C_First(long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator);

	/**
	* Returns the last d d m template in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template
	* @throws NoSuchTemplateException if a matching d d m template could not be found
	*/
	public DDMTemplate findByG_C_Last(long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException;

	/**
	* Returns the last d d m template in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public DDMTemplate fetchByG_C_Last(long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator);

	/**
	* Returns the d d m templates before and after the current d d m template in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param templateId the primary key of the current d d m template
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template
	* @throws NoSuchTemplateException if a d d m template with the primary key could not be found
	*/
	public DDMTemplate[] findByG_C_PrevAndNext(long templateId, long groupId,
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException;

	/**
	* Returns all the d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @return the matching d d m templates that the user has permission to view
	*/
	public java.util.List<DDMTemplate> filterFindByG_C(long groupId,
		long classNameId);

	/**
	* Returns a range of all the d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates that the user has permission to view
	*/
	public java.util.List<DDMTemplate> filterFindByG_C(long groupId,
		long classNameId, int start, int end);

	/**
	* Returns an ordered range of all the d d m templates that the user has permissions to view where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates that the user has permission to view
	*/
	public java.util.List<DDMTemplate> filterFindByG_C(long groupId,
		long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator);

	/**
	* Returns the d d m templates before and after the current d d m template in the ordered set of d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63;.
	*
	* @param templateId the primary key of the current d d m template
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template
	* @throws NoSuchTemplateException if a d d m template with the primary key could not be found
	*/
	public DDMTemplate[] filterFindByG_C_PrevAndNext(long templateId,
		long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException;

	/**
	* Removes all the d d m templates where groupId = &#63; and classNameId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	*/
	public void removeByG_C(long groupId, long classNameId);

	/**
	* Returns the number of d d m templates where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @return the number of matching d d m templates
	*/
	public int countByG_C(long groupId, long classNameId);

	/**
	* Returns the number of d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @return the number of matching d d m templates that the user has permission to view
	*/
	public int filterCountByG_C(long groupId, long classNameId);

	/**
	* Returns all the d d m templates where groupId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classPK the class p k
	* @return the matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByG_CPK(long groupId, long classPK);

	/**
	* Returns a range of all the d d m templates where groupId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classPK the class p k
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByG_CPK(long groupId, long classPK,
		int start, int end);

	/**
	* Returns an ordered range of all the d d m templates where groupId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classPK the class p k
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByG_CPK(long groupId, long classPK,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator);

	/**
	* Returns an ordered range of all the d d m templates where groupId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classPK the class p k
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByG_CPK(long groupId, long classPK,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first d d m template in the ordered set where groupId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template
	* @throws NoSuchTemplateException if a matching d d m template could not be found
	*/
	public DDMTemplate findByG_CPK_First(long groupId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException;

	/**
	* Returns the first d d m template in the ordered set where groupId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public DDMTemplate fetchByG_CPK_First(long groupId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator);

	/**
	* Returns the last d d m template in the ordered set where groupId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template
	* @throws NoSuchTemplateException if a matching d d m template could not be found
	*/
	public DDMTemplate findByG_CPK_Last(long groupId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException;

	/**
	* Returns the last d d m template in the ordered set where groupId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public DDMTemplate fetchByG_CPK_Last(long groupId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator);

	/**
	* Returns the d d m templates before and after the current d d m template in the ordered set where groupId = &#63; and classPK = &#63;.
	*
	* @param templateId the primary key of the current d d m template
	* @param groupId the group ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template
	* @throws NoSuchTemplateException if a d d m template with the primary key could not be found
	*/
	public DDMTemplate[] findByG_CPK_PrevAndNext(long templateId, long groupId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException;

	/**
	* Returns all the d d m templates that the user has permission to view where groupId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classPK the class p k
	* @return the matching d d m templates that the user has permission to view
	*/
	public java.util.List<DDMTemplate> filterFindByG_CPK(long groupId,
		long classPK);

	/**
	* Returns a range of all the d d m templates that the user has permission to view where groupId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classPK the class p k
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates that the user has permission to view
	*/
	public java.util.List<DDMTemplate> filterFindByG_CPK(long groupId,
		long classPK, int start, int end);

	/**
	* Returns an ordered range of all the d d m templates that the user has permissions to view where groupId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classPK the class p k
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates that the user has permission to view
	*/
	public java.util.List<DDMTemplate> filterFindByG_CPK(long groupId,
		long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator);

	/**
	* Returns the d d m templates before and after the current d d m template in the ordered set of d d m templates that the user has permission to view where groupId = &#63; and classPK = &#63;.
	*
	* @param templateId the primary key of the current d d m template
	* @param groupId the group ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template
	* @throws NoSuchTemplateException if a d d m template with the primary key could not be found
	*/
	public DDMTemplate[] filterFindByG_CPK_PrevAndNext(long templateId,
		long groupId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException;

	/**
	* Returns all the d d m templates that the user has permission to view where groupId = any &#63; and classPK = &#63;.
	*
	* @param groupIds the group IDs
	* @param classPK the class p k
	* @return the matching d d m templates that the user has permission to view
	*/
	public java.util.List<DDMTemplate> filterFindByG_CPK(long[] groupIds,
		long classPK);

	/**
	* Returns a range of all the d d m templates that the user has permission to view where groupId = any &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param classPK the class p k
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates that the user has permission to view
	*/
	public java.util.List<DDMTemplate> filterFindByG_CPK(long[] groupIds,
		long classPK, int start, int end);

	/**
	* Returns an ordered range of all the d d m templates that the user has permission to view where groupId = any &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param classPK the class p k
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates that the user has permission to view
	*/
	public java.util.List<DDMTemplate> filterFindByG_CPK(long[] groupIds,
		long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator);

	/**
	* Returns all the d d m templates where groupId = any &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param classPK the class p k
	* @return the matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByG_CPK(long[] groupIds, long classPK);

	/**
	* Returns a range of all the d d m templates where groupId = any &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param classPK the class p k
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByG_CPK(long[] groupIds,
		long classPK, int start, int end);

	/**
	* Returns an ordered range of all the d d m templates where groupId = any &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param classPK the class p k
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByG_CPK(long[] groupIds,
		long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator);

	/**
	* Returns an ordered range of all the d d m templates where groupId = &#63; and classPK = &#63;, optionally using the finder cache.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classPK the class p k
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByG_CPK(long[] groupIds,
		long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the d d m templates where groupId = &#63; and classPK = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classPK the class p k
	*/
	public void removeByG_CPK(long groupId, long classPK);

	/**
	* Returns the number of d d m templates where groupId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classPK the class p k
	* @return the number of matching d d m templates
	*/
	public int countByG_CPK(long groupId, long classPK);

	/**
	* Returns the number of d d m templates where groupId = any &#63; and classPK = &#63;.
	*
	* @param groupIds the group IDs
	* @param classPK the class p k
	* @return the number of matching d d m templates
	*/
	public int countByG_CPK(long[] groupIds, long classPK);

	/**
	* Returns the number of d d m templates that the user has permission to view where groupId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classPK the class p k
	* @return the number of matching d d m templates that the user has permission to view
	*/
	public int filterCountByG_CPK(long groupId, long classPK);

	/**
	* Returns the number of d d m templates that the user has permission to view where groupId = any &#63; and classPK = &#63;.
	*
	* @param groupIds the group IDs
	* @param classPK the class p k
	* @return the number of matching d d m templates that the user has permission to view
	*/
	public int filterCountByG_CPK(long[] groupIds, long classPK);

	/**
	* Returns all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByG_C_C(long groupId,
		long classNameId, long classPK);

	/**
	* Returns a range of all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByG_C_C(long groupId,
		long classNameId, long classPK, int start, int end);

	/**
	* Returns an ordered range of all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByG_C_C(long groupId,
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator);

	/**
	* Returns an ordered range of all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByG_C_C(long groupId,
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first d d m template in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template
	* @throws NoSuchTemplateException if a matching d d m template could not be found
	*/
	public DDMTemplate findByG_C_C_First(long groupId, long classNameId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException;

	/**
	* Returns the first d d m template in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public DDMTemplate fetchByG_C_C_First(long groupId, long classNameId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator);

	/**
	* Returns the last d d m template in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template
	* @throws NoSuchTemplateException if a matching d d m template could not be found
	*/
	public DDMTemplate findByG_C_C_Last(long groupId, long classNameId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException;

	/**
	* Returns the last d d m template in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public DDMTemplate fetchByG_C_C_Last(long groupId, long classNameId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator);

	/**
	* Returns the d d m templates before and after the current d d m template in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param templateId the primary key of the current d d m template
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template
	* @throws NoSuchTemplateException if a d d m template with the primary key could not be found
	*/
	public DDMTemplate[] findByG_C_C_PrevAndNext(long templateId, long groupId,
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException;

	/**
	* Returns all the d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching d d m templates that the user has permission to view
	*/
	public java.util.List<DDMTemplate> filterFindByG_C_C(long groupId,
		long classNameId, long classPK);

	/**
	* Returns a range of all the d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates that the user has permission to view
	*/
	public java.util.List<DDMTemplate> filterFindByG_C_C(long groupId,
		long classNameId, long classPK, int start, int end);

	/**
	* Returns an ordered range of all the d d m templates that the user has permissions to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates that the user has permission to view
	*/
	public java.util.List<DDMTemplate> filterFindByG_C_C(long groupId,
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator);

	/**
	* Returns the d d m templates before and after the current d d m template in the ordered set of d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param templateId the primary key of the current d d m template
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template
	* @throws NoSuchTemplateException if a d d m template with the primary key could not be found
	*/
	public DDMTemplate[] filterFindByG_C_C_PrevAndNext(long templateId,
		long groupId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException;

	/**
	* Returns all the d d m templates that the user has permission to view where groupId = any &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupIds the group IDs
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching d d m templates that the user has permission to view
	*/
	public java.util.List<DDMTemplate> filterFindByG_C_C(long[] groupIds,
		long classNameId, long classPK);

	/**
	* Returns a range of all the d d m templates that the user has permission to view where groupId = any &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates that the user has permission to view
	*/
	public java.util.List<DDMTemplate> filterFindByG_C_C(long[] groupIds,
		long classNameId, long classPK, int start, int end);

	/**
	* Returns an ordered range of all the d d m templates that the user has permission to view where groupId = any &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates that the user has permission to view
	*/
	public java.util.List<DDMTemplate> filterFindByG_C_C(long[] groupIds,
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator);

	/**
	* Returns all the d d m templates where groupId = any &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByG_C_C(long[] groupIds,
		long classNameId, long classPK);

	/**
	* Returns a range of all the d d m templates where groupId = any &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByG_C_C(long[] groupIds,
		long classNameId, long classPK, int start, int end);

	/**
	* Returns an ordered range of all the d d m templates where groupId = any &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupIds the group IDs
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByG_C_C(long[] groupIds,
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator);

	/**
	* Returns an ordered range of all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63;, optionally using the finder cache.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByG_C_C(long[] groupIds,
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	*/
	public void removeByG_C_C(long groupId, long classNameId, long classPK);

	/**
	* Returns the number of d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching d d m templates
	*/
	public int countByG_C_C(long groupId, long classNameId, long classPK);

	/**
	* Returns the number of d d m templates where groupId = any &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupIds the group IDs
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching d d m templates
	*/
	public int countByG_C_C(long[] groupIds, long classNameId, long classPK);

	/**
	* Returns the number of d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching d d m templates that the user has permission to view
	*/
	public int filterCountByG_C_C(long groupId, long classNameId, long classPK);

	/**
	* Returns the number of d d m templates that the user has permission to view where groupId = any &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupIds the group IDs
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching d d m templates that the user has permission to view
	*/
	public int filterCountByG_C_C(long[] groupIds, long classNameId,
		long classPK);

	/**
	* Returns the d d m template where groupId = &#63; and classNameId = &#63; and templateKey = &#63; or throws a {@link NoSuchTemplateException} if it could not be found.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param templateKey the template key
	* @return the matching d d m template
	* @throws NoSuchTemplateException if a matching d d m template could not be found
	*/
	public DDMTemplate findByG_C_T(long groupId, long classNameId,
		java.lang.String templateKey) throws NoSuchTemplateException;

	/**
	* Returns the d d m template where groupId = &#63; and classNameId = &#63; and templateKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param templateKey the template key
	* @return the matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public DDMTemplate fetchByG_C_T(long groupId, long classNameId,
		java.lang.String templateKey);

	/**
	* Returns the d d m template where groupId = &#63; and classNameId = &#63; and templateKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param templateKey the template key
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public DDMTemplate fetchByG_C_T(long groupId, long classNameId,
		java.lang.String templateKey, boolean retrieveFromCache);

	/**
	* Removes the d d m template where groupId = &#63; and classNameId = &#63; and templateKey = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param templateKey the template key
	* @return the d d m template that was removed
	*/
	public DDMTemplate removeByG_C_T(long groupId, long classNameId,
		java.lang.String templateKey) throws NoSuchTemplateException;

	/**
	* Returns the number of d d m templates where groupId = &#63; and classNameId = &#63; and templateKey = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param templateKey the template key
	* @return the number of matching d d m templates
	*/
	public int countByG_C_T(long groupId, long classNameId,
		java.lang.String templateKey);

	/**
	* Returns all the d d m templates where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @return the matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByC_C_T(long classNameId,
		long classPK, java.lang.String type);

	/**
	* Returns a range of all the d d m templates where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByC_C_T(long classNameId,
		long classPK, java.lang.String type, int start, int end);

	/**
	* Returns an ordered range of all the d d m templates where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByC_C_T(long classNameId,
		long classPK, java.lang.String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator);

	/**
	* Returns an ordered range of all the d d m templates where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByC_C_T(long classNameId,
		long classPK, java.lang.String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first d d m template in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template
	* @throws NoSuchTemplateException if a matching d d m template could not be found
	*/
	public DDMTemplate findByC_C_T_First(long classNameId, long classPK,
		java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException;

	/**
	* Returns the first d d m template in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public DDMTemplate fetchByC_C_T_First(long classNameId, long classPK,
		java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator);

	/**
	* Returns the last d d m template in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template
	* @throws NoSuchTemplateException if a matching d d m template could not be found
	*/
	public DDMTemplate findByC_C_T_Last(long classNameId, long classPK,
		java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException;

	/**
	* Returns the last d d m template in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public DDMTemplate fetchByC_C_T_Last(long classNameId, long classPK,
		java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator);

	/**
	* Returns the d d m templates before and after the current d d m template in the ordered set where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param templateId the primary key of the current d d m template
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template
	* @throws NoSuchTemplateException if a d d m template with the primary key could not be found
	*/
	public DDMTemplate[] findByC_C_T_PrevAndNext(long templateId,
		long classNameId, long classPK, java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException;

	/**
	* Removes all the d d m templates where classNameId = &#63; and classPK = &#63; and type = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	*/
	public void removeByC_C_T(long classNameId, long classPK,
		java.lang.String type);

	/**
	* Returns the number of d d m templates where classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @return the number of matching d d m templates
	*/
	public int countByC_C_T(long classNameId, long classPK,
		java.lang.String type);

	/**
	* Returns all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @return the matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByG_C_C_T(long groupId,
		long classNameId, long classPK, java.lang.String type);

	/**
	* Returns a range of all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByG_C_C_T(long groupId,
		long classNameId, long classPK, java.lang.String type, int start,
		int end);

	/**
	* Returns an ordered range of all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByG_C_C_T(long groupId,
		long classNameId, long classPK, java.lang.String type, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator);

	/**
	* Returns an ordered range of all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByG_C_C_T(long groupId,
		long classNameId, long classPK, java.lang.String type, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first d d m template in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template
	* @throws NoSuchTemplateException if a matching d d m template could not be found
	*/
	public DDMTemplate findByG_C_C_T_First(long groupId, long classNameId,
		long classPK, java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException;

	/**
	* Returns the first d d m template in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public DDMTemplate fetchByG_C_C_T_First(long groupId, long classNameId,
		long classPK, java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator);

	/**
	* Returns the last d d m template in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template
	* @throws NoSuchTemplateException if a matching d d m template could not be found
	*/
	public DDMTemplate findByG_C_C_T_Last(long groupId, long classNameId,
		long classPK, java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException;

	/**
	* Returns the last d d m template in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public DDMTemplate fetchByG_C_C_T_Last(long groupId, long classNameId,
		long classPK, java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator);

	/**
	* Returns the d d m templates before and after the current d d m template in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param templateId the primary key of the current d d m template
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template
	* @throws NoSuchTemplateException if a d d m template with the primary key could not be found
	*/
	public DDMTemplate[] findByG_C_C_T_PrevAndNext(long templateId,
		long groupId, long classNameId, long classPK, java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException;

	/**
	* Returns all the d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @return the matching d d m templates that the user has permission to view
	*/
	public java.util.List<DDMTemplate> filterFindByG_C_C_T(long groupId,
		long classNameId, long classPK, java.lang.String type);

	/**
	* Returns a range of all the d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates that the user has permission to view
	*/
	public java.util.List<DDMTemplate> filterFindByG_C_C_T(long groupId,
		long classNameId, long classPK, java.lang.String type, int start,
		int end);

	/**
	* Returns an ordered range of all the d d m templates that the user has permissions to view where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates that the user has permission to view
	*/
	public java.util.List<DDMTemplate> filterFindByG_C_C_T(long groupId,
		long classNameId, long classPK, java.lang.String type, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator);

	/**
	* Returns the d d m templates before and after the current d d m template in the ordered set of d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param templateId the primary key of the current d d m template
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template
	* @throws NoSuchTemplateException if a d d m template with the primary key could not be found
	*/
	public DDMTemplate[] filterFindByG_C_C_T_PrevAndNext(long templateId,
		long groupId, long classNameId, long classPK, java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException;

	/**
	* Removes all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	*/
	public void removeByG_C_C_T(long groupId, long classNameId, long classPK,
		java.lang.String type);

	/**
	* Returns the number of d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @return the number of matching d d m templates
	*/
	public int countByG_C_C_T(long groupId, long classNameId, long classPK,
		java.lang.String type);

	/**
	* Returns the number of d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @return the number of matching d d m templates that the user has permission to view
	*/
	public int filterCountByG_C_C_T(long groupId, long classNameId,
		long classPK, java.lang.String type);

	/**
	* Returns all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param mode the mode
	* @return the matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByG_C_C_T_M(long groupId,
		long classNameId, long classPK, java.lang.String type,
		java.lang.String mode);

	/**
	* Returns a range of all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param mode the mode
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByG_C_C_T_M(long groupId,
		long classNameId, long classPK, java.lang.String type,
		java.lang.String mode, int start, int end);

	/**
	* Returns an ordered range of all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param mode the mode
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByG_C_C_T_M(long groupId,
		long classNameId, long classPK, java.lang.String type,
		java.lang.String mode, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator);

	/**
	* Returns an ordered range of all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param mode the mode
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching d d m templates
	*/
	public java.util.List<DDMTemplate> findByG_C_C_T_M(long groupId,
		long classNameId, long classPK, java.lang.String type,
		java.lang.String mode, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first d d m template in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param mode the mode
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template
	* @throws NoSuchTemplateException if a matching d d m template could not be found
	*/
	public DDMTemplate findByG_C_C_T_M_First(long groupId, long classNameId,
		long classPK, java.lang.String type, java.lang.String mode,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException;

	/**
	* Returns the first d d m template in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param mode the mode
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public DDMTemplate fetchByG_C_C_T_M_First(long groupId, long classNameId,
		long classPK, java.lang.String type, java.lang.String mode,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator);

	/**
	* Returns the last d d m template in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param mode the mode
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template
	* @throws NoSuchTemplateException if a matching d d m template could not be found
	*/
	public DDMTemplate findByG_C_C_T_M_Last(long groupId, long classNameId,
		long classPK, java.lang.String type, java.lang.String mode,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException;

	/**
	* Returns the last d d m template in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param mode the mode
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching d d m template, or <code>null</code> if a matching d d m template could not be found
	*/
	public DDMTemplate fetchByG_C_C_T_M_Last(long groupId, long classNameId,
		long classPK, java.lang.String type, java.lang.String mode,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator);

	/**
	* Returns the d d m templates before and after the current d d m template in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	*
	* @param templateId the primary key of the current d d m template
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param mode the mode
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template
	* @throws NoSuchTemplateException if a d d m template with the primary key could not be found
	*/
	public DDMTemplate[] findByG_C_C_T_M_PrevAndNext(long templateId,
		long groupId, long classNameId, long classPK, java.lang.String type,
		java.lang.String mode,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException;

	/**
	* Returns all the d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param mode the mode
	* @return the matching d d m templates that the user has permission to view
	*/
	public java.util.List<DDMTemplate> filterFindByG_C_C_T_M(long groupId,
		long classNameId, long classPK, java.lang.String type,
		java.lang.String mode);

	/**
	* Returns a range of all the d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param mode the mode
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of matching d d m templates that the user has permission to view
	*/
	public java.util.List<DDMTemplate> filterFindByG_C_C_T_M(long groupId,
		long classNameId, long classPK, java.lang.String type,
		java.lang.String mode, int start, int end);

	/**
	* Returns an ordered range of all the d d m templates that the user has permissions to view where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param mode the mode
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching d d m templates that the user has permission to view
	*/
	public java.util.List<DDMTemplate> filterFindByG_C_C_T_M(long groupId,
		long classNameId, long classPK, java.lang.String type,
		java.lang.String mode, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator);

	/**
	* Returns the d d m templates before and after the current d d m template in the ordered set of d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	*
	* @param templateId the primary key of the current d d m template
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param mode the mode
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next d d m template
	* @throws NoSuchTemplateException if a d d m template with the primary key could not be found
	*/
	public DDMTemplate[] filterFindByG_C_C_T_M_PrevAndNext(long templateId,
		long groupId, long classNameId, long classPK, java.lang.String type,
		java.lang.String mode,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator)
		throws NoSuchTemplateException;

	/**
	* Removes all the d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param mode the mode
	*/
	public void removeByG_C_C_T_M(long groupId, long classNameId, long classPK,
		java.lang.String type, java.lang.String mode);

	/**
	* Returns the number of d d m templates where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param mode the mode
	* @return the number of matching d d m templates
	*/
	public int countByG_C_C_T_M(long groupId, long classNameId, long classPK,
		java.lang.String type, java.lang.String mode);

	/**
	* Returns the number of d d m templates that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63; and type = &#63; and mode = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param type the type
	* @param mode the mode
	* @return the number of matching d d m templates that the user has permission to view
	*/
	public int filterCountByG_C_C_T_M(long groupId, long classNameId,
		long classPK, java.lang.String type, java.lang.String mode);

	/**
	* Caches the d d m template in the entity cache if it is enabled.
	*
	* @param ddmTemplate the d d m template
	*/
	public void cacheResult(DDMTemplate ddmTemplate);

	/**
	* Caches the d d m templates in the entity cache if it is enabled.
	*
	* @param ddmTemplates the d d m templates
	*/
	public void cacheResult(java.util.List<DDMTemplate> ddmTemplates);

	/**
	* Creates a new d d m template with the primary key. Does not add the d d m template to the database.
	*
	* @param templateId the primary key for the new d d m template
	* @return the new d d m template
	*/
	public DDMTemplate create(long templateId);

	/**
	* Removes the d d m template with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param templateId the primary key of the d d m template
	* @return the d d m template that was removed
	* @throws NoSuchTemplateException if a d d m template with the primary key could not be found
	*/
	public DDMTemplate remove(long templateId) throws NoSuchTemplateException;

	public DDMTemplate updateImpl(DDMTemplate ddmTemplate);

	/**
	* Returns the d d m template with the primary key or throws a {@link NoSuchTemplateException} if it could not be found.
	*
	* @param templateId the primary key of the d d m template
	* @return the d d m template
	* @throws NoSuchTemplateException if a d d m template with the primary key could not be found
	*/
	public DDMTemplate findByPrimaryKey(long templateId)
		throws NoSuchTemplateException;

	/**
	* Returns the d d m template with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param templateId the primary key of the d d m template
	* @return the d d m template, or <code>null</code> if a d d m template with the primary key could not be found
	*/
	public DDMTemplate fetchByPrimaryKey(long templateId);

	@Override
	public java.util.Map<java.io.Serializable, DDMTemplate> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the d d m templates.
	*
	* @return the d d m templates
	*/
	public java.util.List<DDMTemplate> findAll();

	/**
	* Returns a range of all the d d m templates.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @return the range of d d m templates
	*/
	public java.util.List<DDMTemplate> findAll(int start, int end);

	/**
	* Returns an ordered range of all the d d m templates.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of d d m templates
	*/
	public java.util.List<DDMTemplate> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator);

	/**
	* Returns an ordered range of all the d d m templates.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMTemplateModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m templates
	* @param end the upper bound of the range of d d m templates (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of d d m templates
	*/
	public java.util.List<DDMTemplate> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DDMTemplate> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the d d m templates from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of d d m templates.
	*
	* @return the number of d d m templates
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}