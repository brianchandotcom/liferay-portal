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

package com.liferay.knowledge.base.service.persistence;

import com.liferay.knowledge.base.exception.NoSuchTemplateException;
import com.liferay.knowledge.base.model.KBTemplate;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the kb template service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KBTemplateUtil
 * @generated
 */
@ProviderType
public interface KBTemplatePersistence extends BasePersistence<KBTemplate> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link KBTemplateUtil} to access the kb template persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the kb templates where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching kb templates
	 */
	public java.util.List<KBTemplate> findByUuid(String uuid);

	/**
	 * Returns a range of all the kb templates where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KBTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of kb templates
	 * @param end the upper bound of the range of kb templates (not inclusive)
	 * @return the range of matching kb templates
	 */
	public java.util.List<KBTemplate> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the kb templates where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KBTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of kb templates
	 * @param end the upper bound of the range of kb templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kb templates
	 */
	public java.util.List<KBTemplate> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KBTemplate>
			orderByComparator);

	/**
	 * Returns an ordered range of all the kb templates where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KBTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of kb templates
	 * @param end the upper bound of the range of kb templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kb templates
	 */
	public java.util.List<KBTemplate> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KBTemplate>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first kb template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kb template
	 * @throws NoSuchTemplateException if a matching kb template could not be found
	 */
	public KBTemplate findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<KBTemplate>
				orderByComparator)
		throws NoSuchTemplateException;

	/**
	 * Returns the first kb template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kb template, or <code>null</code> if a matching kb template could not be found
	 */
	public KBTemplate fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<KBTemplate>
			orderByComparator);

	/**
	 * Returns the last kb template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kb template
	 * @throws NoSuchTemplateException if a matching kb template could not be found
	 */
	public KBTemplate findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<KBTemplate>
				orderByComparator)
		throws NoSuchTemplateException;

	/**
	 * Returns the last kb template in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kb template, or <code>null</code> if a matching kb template could not be found
	 */
	public KBTemplate fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<KBTemplate>
			orderByComparator);

	/**
	 * Returns the kb templates before and after the current kb template in the ordered set where uuid = &#63;.
	 *
	 * @param kbTemplateId the primary key of the current kb template
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kb template
	 * @throws NoSuchTemplateException if a kb template with the primary key could not be found
	 */
	public KBTemplate[] findByUuid_PrevAndNext(
			long kbTemplateId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<KBTemplate>
				orderByComparator)
		throws NoSuchTemplateException;

	/**
	 * Removes all the kb templates where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of kb templates where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching kb templates
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the kb template where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchTemplateException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching kb template
	 * @throws NoSuchTemplateException if a matching kb template could not be found
	 */
	public KBTemplate findByUUID_G(String uuid, long groupId)
		throws NoSuchTemplateException;

	/**
	 * Returns the kb template where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching kb template, or <code>null</code> if a matching kb template could not be found
	 */
	public KBTemplate fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the kb template where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching kb template, or <code>null</code> if a matching kb template could not be found
	 */
	public KBTemplate fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the kb template where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the kb template that was removed
	 */
	public KBTemplate removeByUUID_G(String uuid, long groupId)
		throws NoSuchTemplateException;

	/**
	 * Returns the number of kb templates where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching kb templates
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the kb templates where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching kb templates
	 */
	public java.util.List<KBTemplate> findByUuid_C(String uuid, long companyId);

	/**
	 * Returns a range of all the kb templates where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KBTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kb templates
	 * @param end the upper bound of the range of kb templates (not inclusive)
	 * @return the range of matching kb templates
	 */
	public java.util.List<KBTemplate> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the kb templates where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KBTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kb templates
	 * @param end the upper bound of the range of kb templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kb templates
	 */
	public java.util.List<KBTemplate> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KBTemplate>
			orderByComparator);

	/**
	 * Returns an ordered range of all the kb templates where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KBTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kb templates
	 * @param end the upper bound of the range of kb templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kb templates
	 */
	public java.util.List<KBTemplate> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KBTemplate>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first kb template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kb template
	 * @throws NoSuchTemplateException if a matching kb template could not be found
	 */
	public KBTemplate findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<KBTemplate>
				orderByComparator)
		throws NoSuchTemplateException;

	/**
	 * Returns the first kb template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kb template, or <code>null</code> if a matching kb template could not be found
	 */
	public KBTemplate fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<KBTemplate>
			orderByComparator);

	/**
	 * Returns the last kb template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kb template
	 * @throws NoSuchTemplateException if a matching kb template could not be found
	 */
	public KBTemplate findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<KBTemplate>
				orderByComparator)
		throws NoSuchTemplateException;

	/**
	 * Returns the last kb template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kb template, or <code>null</code> if a matching kb template could not be found
	 */
	public KBTemplate fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<KBTemplate>
			orderByComparator);

	/**
	 * Returns the kb templates before and after the current kb template in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param kbTemplateId the primary key of the current kb template
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kb template
	 * @throws NoSuchTemplateException if a kb template with the primary key could not be found
	 */
	public KBTemplate[] findByUuid_C_PrevAndNext(
			long kbTemplateId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<KBTemplate>
				orderByComparator)
		throws NoSuchTemplateException;

	/**
	 * Removes all the kb templates where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of kb templates where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching kb templates
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the kb templates where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching kb templates
	 */
	public java.util.List<KBTemplate> findByGroupId(long groupId);

	/**
	 * Returns a range of all the kb templates where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KBTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of kb templates
	 * @param end the upper bound of the range of kb templates (not inclusive)
	 * @return the range of matching kb templates
	 */
	public java.util.List<KBTemplate> findByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the kb templates where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KBTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of kb templates
	 * @param end the upper bound of the range of kb templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kb templates
	 */
	public java.util.List<KBTemplate> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KBTemplate>
			orderByComparator);

	/**
	 * Returns an ordered range of all the kb templates where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KBTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of kb templates
	 * @param end the upper bound of the range of kb templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kb templates
	 */
	public java.util.List<KBTemplate> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KBTemplate>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first kb template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kb template
	 * @throws NoSuchTemplateException if a matching kb template could not be found
	 */
	public KBTemplate findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<KBTemplate>
				orderByComparator)
		throws NoSuchTemplateException;

	/**
	 * Returns the first kb template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kb template, or <code>null</code> if a matching kb template could not be found
	 */
	public KBTemplate fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<KBTemplate>
			orderByComparator);

	/**
	 * Returns the last kb template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kb template
	 * @throws NoSuchTemplateException if a matching kb template could not be found
	 */
	public KBTemplate findByGroupId_Last(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<KBTemplate>
				orderByComparator)
		throws NoSuchTemplateException;

	/**
	 * Returns the last kb template in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kb template, or <code>null</code> if a matching kb template could not be found
	 */
	public KBTemplate fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<KBTemplate>
			orderByComparator);

	/**
	 * Returns the kb templates before and after the current kb template in the ordered set where groupId = &#63;.
	 *
	 * @param kbTemplateId the primary key of the current kb template
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kb template
	 * @throws NoSuchTemplateException if a kb template with the primary key could not be found
	 */
	public KBTemplate[] findByGroupId_PrevAndNext(
			long kbTemplateId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<KBTemplate>
				orderByComparator)
		throws NoSuchTemplateException;

	/**
	 * Returns all the kb templates that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching kb templates that the user has permission to view
	 */
	public java.util.List<KBTemplate> filterFindByGroupId(long groupId);

	/**
	 * Returns a range of all the kb templates that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KBTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of kb templates
	 * @param end the upper bound of the range of kb templates (not inclusive)
	 * @return the range of matching kb templates that the user has permission to view
	 */
	public java.util.List<KBTemplate> filterFindByGroupId(
		long groupId, int start, int end);

	/**
	 * Returns an ordered range of all the kb templates that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KBTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of kb templates
	 * @param end the upper bound of the range of kb templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kb templates that the user has permission to view
	 */
	public java.util.List<KBTemplate> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KBTemplate>
			orderByComparator);

	/**
	 * Returns the kb templates before and after the current kb template in the ordered set of kb templates that the user has permission to view where groupId = &#63;.
	 *
	 * @param kbTemplateId the primary key of the current kb template
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kb template
	 * @throws NoSuchTemplateException if a kb template with the primary key could not be found
	 */
	public KBTemplate[] filterFindByGroupId_PrevAndNext(
			long kbTemplateId, long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<KBTemplate>
				orderByComparator)
		throws NoSuchTemplateException;

	/**
	 * Removes all the kb templates where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of kb templates where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching kb templates
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns the number of kb templates that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching kb templates that the user has permission to view
	 */
	public int filterCountByGroupId(long groupId);

	/**
	 * Caches the kb template in the entity cache if it is enabled.
	 *
	 * @param kbTemplate the kb template
	 */
	public void cacheResult(KBTemplate kbTemplate);

	/**
	 * Caches the kb templates in the entity cache if it is enabled.
	 *
	 * @param kbTemplates the kb templates
	 */
	public void cacheResult(java.util.List<KBTemplate> kbTemplates);

	/**
	 * Creates a new kb template with the primary key. Does not add the kb template to the database.
	 *
	 * @param kbTemplateId the primary key for the new kb template
	 * @return the new kb template
	 */
	public KBTemplate create(long kbTemplateId);

	/**
	 * Removes the kb template with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kbTemplateId the primary key of the kb template
	 * @return the kb template that was removed
	 * @throws NoSuchTemplateException if a kb template with the primary key could not be found
	 */
	public KBTemplate remove(long kbTemplateId) throws NoSuchTemplateException;

	public KBTemplate updateImpl(KBTemplate kbTemplate);

	/**
	 * Returns the kb template with the primary key or throws a <code>NoSuchTemplateException</code> if it could not be found.
	 *
	 * @param kbTemplateId the primary key of the kb template
	 * @return the kb template
	 * @throws NoSuchTemplateException if a kb template with the primary key could not be found
	 */
	public KBTemplate findByPrimaryKey(long kbTemplateId)
		throws NoSuchTemplateException;

	/**
	 * Returns the kb template with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param kbTemplateId the primary key of the kb template
	 * @return the kb template, or <code>null</code> if a kb template with the primary key could not be found
	 */
	public KBTemplate fetchByPrimaryKey(long kbTemplateId);

	/**
	 * Returns all the kb templates.
	 *
	 * @return the kb templates
	 */
	public java.util.List<KBTemplate> findAll();

	/**
	 * Returns a range of all the kb templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KBTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kb templates
	 * @param end the upper bound of the range of kb templates (not inclusive)
	 * @return the range of kb templates
	 */
	public java.util.List<KBTemplate> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the kb templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KBTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kb templates
	 * @param end the upper bound of the range of kb templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of kb templates
	 */
	public java.util.List<KBTemplate> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KBTemplate>
			orderByComparator);

	/**
	 * Returns an ordered range of all the kb templates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KBTemplateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kb templates
	 * @param end the upper bound of the range of kb templates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of kb templates
	 */
	public java.util.List<KBTemplate> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KBTemplate>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the kb templates from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of kb templates.
	 *
	 * @return the number of kb templates
	 */
	public int countAll();

}