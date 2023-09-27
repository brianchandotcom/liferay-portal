/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.persistence;

import com.liferay.object.exception.NoSuchObjectFieldSettingException;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the object field setting service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see ObjectFieldSettingUtil
 * @generated
 */
@ProviderType
public interface ObjectFieldSettingPersistence
	extends BasePersistence<ObjectFieldSetting> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ObjectFieldSettingUtil} to access the object field setting persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the object field settings where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object field settings
	 */
	public java.util.List<ObjectFieldSetting> findByUuid(String uuid);

	/**
	 * Returns a range of all the object field settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object field settings
	 * @param end the upper bound of the range of object field settings (not inclusive)
	 * @return the range of matching object field settings
	 */
	public java.util.List<ObjectFieldSetting> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the object field settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object field settings
	 * @param end the upper bound of the range of object field settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object field settings
	 */
	public java.util.List<ObjectFieldSetting> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectFieldSetting>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object field settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object field settings
	 * @param end the upper bound of the range of object field settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object field settings
	 */
	public java.util.List<ObjectFieldSetting> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectFieldSetting>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object field setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object field setting
	 * @throws NoSuchObjectFieldSettingException if a matching object field setting could not be found
	 */
	public ObjectFieldSetting findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectFieldSetting>
				orderByComparator)
		throws NoSuchObjectFieldSettingException;

	/**
	 * Returns the first object field setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object field setting, or <code>null</code> if a matching object field setting could not be found
	 */
	public ObjectFieldSetting fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectFieldSetting>
			orderByComparator);

	/**
	 * Returns the last object field setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object field setting
	 * @throws NoSuchObjectFieldSettingException if a matching object field setting could not be found
	 */
	public ObjectFieldSetting findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectFieldSetting>
				orderByComparator)
		throws NoSuchObjectFieldSettingException;

	/**
	 * Returns the last object field setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object field setting, or <code>null</code> if a matching object field setting could not be found
	 */
	public ObjectFieldSetting fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectFieldSetting>
			orderByComparator);

	/**
	 * Returns the object field settings before and after the current object field setting in the ordered set where uuid = &#63;.
	 *
	 * @param objectFieldSettingId the primary key of the current object field setting
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object field setting
	 * @throws NoSuchObjectFieldSettingException if a object field setting with the primary key could not be found
	 */
	public ObjectFieldSetting[] findByUuid_PrevAndNext(
			long objectFieldSettingId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectFieldSetting>
				orderByComparator)
		throws NoSuchObjectFieldSettingException;

	/**
	 * Removes all the object field settings where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of object field settings where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object field settings
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the object field settings where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object field settings
	 */
	public java.util.List<ObjectFieldSetting> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the object field settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object field settings
	 * @param end the upper bound of the range of object field settings (not inclusive)
	 * @return the range of matching object field settings
	 */
	public java.util.List<ObjectFieldSetting> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the object field settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object field settings
	 * @param end the upper bound of the range of object field settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object field settings
	 */
	public java.util.List<ObjectFieldSetting> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectFieldSetting>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object field settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object field settings
	 * @param end the upper bound of the range of object field settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object field settings
	 */
	public java.util.List<ObjectFieldSetting> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectFieldSetting>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object field setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object field setting
	 * @throws NoSuchObjectFieldSettingException if a matching object field setting could not be found
	 */
	public ObjectFieldSetting findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectFieldSetting>
				orderByComparator)
		throws NoSuchObjectFieldSettingException;

	/**
	 * Returns the first object field setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object field setting, or <code>null</code> if a matching object field setting could not be found
	 */
	public ObjectFieldSetting fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectFieldSetting>
			orderByComparator);

	/**
	 * Returns the last object field setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object field setting
	 * @throws NoSuchObjectFieldSettingException if a matching object field setting could not be found
	 */
	public ObjectFieldSetting findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectFieldSetting>
				orderByComparator)
		throws NoSuchObjectFieldSettingException;

	/**
	 * Returns the last object field setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object field setting, or <code>null</code> if a matching object field setting could not be found
	 */
	public ObjectFieldSetting fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectFieldSetting>
			orderByComparator);

	/**
	 * Returns the object field settings before and after the current object field setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectFieldSettingId the primary key of the current object field setting
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object field setting
	 * @throws NoSuchObjectFieldSettingException if a object field setting with the primary key could not be found
	 */
	public ObjectFieldSetting[] findByUuid_C_PrevAndNext(
			long objectFieldSettingId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectFieldSetting>
				orderByComparator)
		throws NoSuchObjectFieldSettingException;

	/**
	 * Removes all the object field settings where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of object field settings where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object field settings
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the object field settings where objectFieldId = &#63;.
	 *
	 * @param objectFieldId the object field ID
	 * @return the matching object field settings
	 */
	public java.util.List<ObjectFieldSetting> findByObjectFieldId(
		long objectFieldId);

	/**
	 * Returns a range of all the object field settings where objectFieldId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldSettingModelImpl</code>.
	 * </p>
	 *
	 * @param objectFieldId the object field ID
	 * @param start the lower bound of the range of object field settings
	 * @param end the upper bound of the range of object field settings (not inclusive)
	 * @return the range of matching object field settings
	 */
	public java.util.List<ObjectFieldSetting> findByObjectFieldId(
		long objectFieldId, int start, int end);

	/**
	 * Returns an ordered range of all the object field settings where objectFieldId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldSettingModelImpl</code>.
	 * </p>
	 *
	 * @param objectFieldId the object field ID
	 * @param start the lower bound of the range of object field settings
	 * @param end the upper bound of the range of object field settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object field settings
	 */
	public java.util.List<ObjectFieldSetting> findByObjectFieldId(
		long objectFieldId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectFieldSetting>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object field settings where objectFieldId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldSettingModelImpl</code>.
	 * </p>
	 *
	 * @param objectFieldId the object field ID
	 * @param start the lower bound of the range of object field settings
	 * @param end the upper bound of the range of object field settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object field settings
	 */
	public java.util.List<ObjectFieldSetting> findByObjectFieldId(
		long objectFieldId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectFieldSetting>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first object field setting in the ordered set where objectFieldId = &#63;.
	 *
	 * @param objectFieldId the object field ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object field setting
	 * @throws NoSuchObjectFieldSettingException if a matching object field setting could not be found
	 */
	public ObjectFieldSetting findByObjectFieldId_First(
			long objectFieldId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectFieldSetting>
				orderByComparator)
		throws NoSuchObjectFieldSettingException;

	/**
	 * Returns the first object field setting in the ordered set where objectFieldId = &#63;.
	 *
	 * @param objectFieldId the object field ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object field setting, or <code>null</code> if a matching object field setting could not be found
	 */
	public ObjectFieldSetting fetchByObjectFieldId_First(
		long objectFieldId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectFieldSetting>
			orderByComparator);

	/**
	 * Returns the last object field setting in the ordered set where objectFieldId = &#63;.
	 *
	 * @param objectFieldId the object field ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object field setting
	 * @throws NoSuchObjectFieldSettingException if a matching object field setting could not be found
	 */
	public ObjectFieldSetting findByObjectFieldId_Last(
			long objectFieldId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectFieldSetting>
				orderByComparator)
		throws NoSuchObjectFieldSettingException;

	/**
	 * Returns the last object field setting in the ordered set where objectFieldId = &#63;.
	 *
	 * @param objectFieldId the object field ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object field setting, or <code>null</code> if a matching object field setting could not be found
	 */
	public ObjectFieldSetting fetchByObjectFieldId_Last(
		long objectFieldId,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectFieldSetting>
			orderByComparator);

	/**
	 * Returns the object field settings before and after the current object field setting in the ordered set where objectFieldId = &#63;.
	 *
	 * @param objectFieldSettingId the primary key of the current object field setting
	 * @param objectFieldId the object field ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object field setting
	 * @throws NoSuchObjectFieldSettingException if a object field setting with the primary key could not be found
	 */
	public ObjectFieldSetting[] findByObjectFieldId_PrevAndNext(
			long objectFieldSettingId, long objectFieldId,
			com.liferay.portal.kernel.util.OrderByComparator<ObjectFieldSetting>
				orderByComparator)
		throws NoSuchObjectFieldSettingException;

	/**
	 * Removes all the object field settings where objectFieldId = &#63; from the database.
	 *
	 * @param objectFieldId the object field ID
	 */
	public void removeByObjectFieldId(long objectFieldId);

	/**
	 * Returns the number of object field settings where objectFieldId = &#63;.
	 *
	 * @param objectFieldId the object field ID
	 * @return the number of matching object field settings
	 */
	public int countByObjectFieldId(long objectFieldId);

	/**
	 * Returns the object field setting where objectFieldId = &#63; and name = &#63; or throws a <code>NoSuchObjectFieldSettingException</code> if it could not be found.
	 *
	 * @param objectFieldId the object field ID
	 * @param name the name
	 * @return the matching object field setting
	 * @throws NoSuchObjectFieldSettingException if a matching object field setting could not be found
	 */
	public ObjectFieldSetting findByOFI_N(long objectFieldId, String name)
		throws NoSuchObjectFieldSettingException;

	/**
	 * Returns the object field setting where objectFieldId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param objectFieldId the object field ID
	 * @param name the name
	 * @return the matching object field setting, or <code>null</code> if a matching object field setting could not be found
	 */
	public ObjectFieldSetting fetchByOFI_N(long objectFieldId, String name);

	/**
	 * Returns the object field setting where objectFieldId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param objectFieldId the object field ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching object field setting, or <code>null</code> if a matching object field setting could not be found
	 */
	public ObjectFieldSetting fetchByOFI_N(
		long objectFieldId, String name, boolean useFinderCache);

	/**
	 * Removes the object field setting where objectFieldId = &#63; and name = &#63; from the database.
	 *
	 * @param objectFieldId the object field ID
	 * @param name the name
	 * @return the object field setting that was removed
	 */
	public ObjectFieldSetting removeByOFI_N(long objectFieldId, String name)
		throws NoSuchObjectFieldSettingException;

	/**
	 * Returns the number of object field settings where objectFieldId = &#63; and name = &#63;.
	 *
	 * @param objectFieldId the object field ID
	 * @param name the name
	 * @return the number of matching object field settings
	 */
	public int countByOFI_N(long objectFieldId, String name);

	/**
	 * Caches the object field setting in the entity cache if it is enabled.
	 *
	 * @param objectFieldSetting the object field setting
	 */
	public void cacheResult(ObjectFieldSetting objectFieldSetting);

	/**
	 * Caches the object field settings in the entity cache if it is enabled.
	 *
	 * @param objectFieldSettings the object field settings
	 */
	public void cacheResult(
		java.util.List<ObjectFieldSetting> objectFieldSettings);

	/**
	 * Creates a new object field setting with the primary key. Does not add the object field setting to the database.
	 *
	 * @param objectFieldSettingId the primary key for the new object field setting
	 * @return the new object field setting
	 */
	public ObjectFieldSetting create(long objectFieldSettingId);

	/**
	 * Removes the object field setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectFieldSettingId the primary key of the object field setting
	 * @return the object field setting that was removed
	 * @throws NoSuchObjectFieldSettingException if a object field setting with the primary key could not be found
	 */
	public ObjectFieldSetting remove(long objectFieldSettingId)
		throws NoSuchObjectFieldSettingException;

	public ObjectFieldSetting updateImpl(ObjectFieldSetting objectFieldSetting);

	/**
	 * Returns the object field setting with the primary key or throws a <code>NoSuchObjectFieldSettingException</code> if it could not be found.
	 *
	 * @param objectFieldSettingId the primary key of the object field setting
	 * @return the object field setting
	 * @throws NoSuchObjectFieldSettingException if a object field setting with the primary key could not be found
	 */
	public ObjectFieldSetting findByPrimaryKey(long objectFieldSettingId)
		throws NoSuchObjectFieldSettingException;

	/**
	 * Returns the object field setting with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectFieldSettingId the primary key of the object field setting
	 * @return the object field setting, or <code>null</code> if a object field setting with the primary key could not be found
	 */
	public ObjectFieldSetting fetchByPrimaryKey(long objectFieldSettingId);

	/**
	 * Returns all the object field settings.
	 *
	 * @return the object field settings
	 */
	public java.util.List<ObjectFieldSetting> findAll();

	/**
	 * Returns a range of all the object field settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object field settings
	 * @param end the upper bound of the range of object field settings (not inclusive)
	 * @return the range of object field settings
	 */
	public java.util.List<ObjectFieldSetting> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the object field settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object field settings
	 * @param end the upper bound of the range of object field settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object field settings
	 */
	public java.util.List<ObjectFieldSetting> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectFieldSetting>
			orderByComparator);

	/**
	 * Returns an ordered range of all the object field settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object field settings
	 * @param end the upper bound of the range of object field settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object field settings
	 */
	public java.util.List<ObjectFieldSetting> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ObjectFieldSetting>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the object field settings from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of object field settings.
	 *
	 * @return the number of object field settings
	 */
	public int countAll();

}