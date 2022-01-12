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

package com.liferay.commerce.shop.by.diagram.service.persistence;

import com.liferay.commerce.shop.by.diagram.exception.NoSuchCSDiagramSettingException;
import com.liferay.commerce.shop.by.diagram.model.CSDiagramSetting;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the cs diagram setting service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CSDiagramSettingUtil
 * @generated
 */
@ProviderType
public interface CSDiagramSettingPersistence
	extends BasePersistence<CSDiagramSetting>, CTPersistence<CSDiagramSetting> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CSDiagramSettingUtil} to access the cs diagram setting persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the cs diagram settings where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cs diagram settings
	 */
	public java.util.List<CSDiagramSetting> findByUuid(String uuid);

	/**
	 * Returns a range of all the cs diagram settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CSDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cs diagram settings
	 * @param end the upper bound of the range of cs diagram settings (not inclusive)
	 * @return the range of matching cs diagram settings
	 */
	public java.util.List<CSDiagramSetting> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the cs diagram settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CSDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cs diagram settings
	 * @param end the upper bound of the range of cs diagram settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cs diagram settings
	 */
	public java.util.List<CSDiagramSetting> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CSDiagramSetting>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cs diagram settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CSDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cs diagram settings
	 * @param end the upper bound of the range of cs diagram settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cs diagram settings
	 */
	public java.util.List<CSDiagramSetting> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CSDiagramSetting>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cs diagram setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cs diagram setting
	 * @throws NoSuchCSDiagramSettingException if a matching cs diagram setting could not be found
	 */
	public CSDiagramSetting findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<CSDiagramSetting>
				orderByComparator)
		throws NoSuchCSDiagramSettingException;

	/**
	 * Returns the first cs diagram setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cs diagram setting, or <code>null</code> if a matching cs diagram setting could not be found
	 */
	public CSDiagramSetting fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CSDiagramSetting>
			orderByComparator);

	/**
	 * Returns the last cs diagram setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cs diagram setting
	 * @throws NoSuchCSDiagramSettingException if a matching cs diagram setting could not be found
	 */
	public CSDiagramSetting findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<CSDiagramSetting>
				orderByComparator)
		throws NoSuchCSDiagramSettingException;

	/**
	 * Returns the last cs diagram setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cs diagram setting, or <code>null</code> if a matching cs diagram setting could not be found
	 */
	public CSDiagramSetting fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CSDiagramSetting>
			orderByComparator);

	/**
	 * Returns the cs diagram settings before and after the current cs diagram setting in the ordered set where uuid = &#63;.
	 *
	 * @param CSDiagramSettingId the primary key of the current cs diagram setting
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cs diagram setting
	 * @throws NoSuchCSDiagramSettingException if a cs diagram setting with the primary key could not be found
	 */
	public CSDiagramSetting[] findByUuid_PrevAndNext(
			long CSDiagramSettingId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<CSDiagramSetting>
				orderByComparator)
		throws NoSuchCSDiagramSettingException;

	/**
	 * Removes all the cs diagram settings where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of cs diagram settings where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cs diagram settings
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the cs diagram settings where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cs diagram settings
	 */
	public java.util.List<CSDiagramSetting> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the cs diagram settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CSDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cs diagram settings
	 * @param end the upper bound of the range of cs diagram settings (not inclusive)
	 * @return the range of matching cs diagram settings
	 */
	public java.util.List<CSDiagramSetting> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the cs diagram settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CSDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cs diagram settings
	 * @param end the upper bound of the range of cs diagram settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cs diagram settings
	 */
	public java.util.List<CSDiagramSetting> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CSDiagramSetting>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cs diagram settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CSDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cs diagram settings
	 * @param end the upper bound of the range of cs diagram settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cs diagram settings
	 */
	public java.util.List<CSDiagramSetting> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CSDiagramSetting>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cs diagram setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cs diagram setting
	 * @throws NoSuchCSDiagramSettingException if a matching cs diagram setting could not be found
	 */
	public CSDiagramSetting findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CSDiagramSetting>
				orderByComparator)
		throws NoSuchCSDiagramSettingException;

	/**
	 * Returns the first cs diagram setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cs diagram setting, or <code>null</code> if a matching cs diagram setting could not be found
	 */
	public CSDiagramSetting fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CSDiagramSetting>
			orderByComparator);

	/**
	 * Returns the last cs diagram setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cs diagram setting
	 * @throws NoSuchCSDiagramSettingException if a matching cs diagram setting could not be found
	 */
	public CSDiagramSetting findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CSDiagramSetting>
				orderByComparator)
		throws NoSuchCSDiagramSettingException;

	/**
	 * Returns the last cs diagram setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cs diagram setting, or <code>null</code> if a matching cs diagram setting could not be found
	 */
	public CSDiagramSetting fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CSDiagramSetting>
			orderByComparator);

	/**
	 * Returns the cs diagram settings before and after the current cs diagram setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param CSDiagramSettingId the primary key of the current cs diagram setting
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cs diagram setting
	 * @throws NoSuchCSDiagramSettingException if a cs diagram setting with the primary key could not be found
	 */
	public CSDiagramSetting[] findByUuid_C_PrevAndNext(
			long CSDiagramSettingId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CSDiagramSetting>
				orderByComparator)
		throws NoSuchCSDiagramSettingException;

	/**
	 * Removes all the cs diagram settings where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of cs diagram settings where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cs diagram settings
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns the cs diagram setting where CPDefinitionId = &#63; or throws a <code>NoSuchCSDiagramSettingException</code> if it could not be found.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the matching cs diagram setting
	 * @throws NoSuchCSDiagramSettingException if a matching cs diagram setting could not be found
	 */
	public CSDiagramSetting findByCPDefinitionId(long CPDefinitionId)
		throws NoSuchCSDiagramSettingException;

	/**
	 * Returns the cs diagram setting where CPDefinitionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the matching cs diagram setting, or <code>null</code> if a matching cs diagram setting could not be found
	 */
	public CSDiagramSetting fetchByCPDefinitionId(long CPDefinitionId);

	/**
	 * Returns the cs diagram setting where CPDefinitionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cs diagram setting, or <code>null</code> if a matching cs diagram setting could not be found
	 */
	public CSDiagramSetting fetchByCPDefinitionId(
		long CPDefinitionId, boolean useFinderCache);

	/**
	 * Removes the cs diagram setting where CPDefinitionId = &#63; from the database.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the cs diagram setting that was removed
	 */
	public CSDiagramSetting removeByCPDefinitionId(long CPDefinitionId)
		throws NoSuchCSDiagramSettingException;

	/**
	 * Returns the number of cs diagram settings where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the number of matching cs diagram settings
	 */
	public int countByCPDefinitionId(long CPDefinitionId);

	/**
	 * Caches the cs diagram setting in the entity cache if it is enabled.
	 *
	 * @param csDiagramSetting the cs diagram setting
	 */
	public void cacheResult(CSDiagramSetting csDiagramSetting);

	/**
	 * Caches the cs diagram settings in the entity cache if it is enabled.
	 *
	 * @param csDiagramSettings the cs diagram settings
	 */
	public void cacheResult(java.util.List<CSDiagramSetting> csDiagramSettings);

	/**
	 * Creates a new cs diagram setting with the primary key. Does not add the cs diagram setting to the database.
	 *
	 * @param CSDiagramSettingId the primary key for the new cs diagram setting
	 * @return the new cs diagram setting
	 */
	public CSDiagramSetting create(long CSDiagramSettingId);

	/**
	 * Removes the cs diagram setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CSDiagramSettingId the primary key of the cs diagram setting
	 * @return the cs diagram setting that was removed
	 * @throws NoSuchCSDiagramSettingException if a cs diagram setting with the primary key could not be found
	 */
	public CSDiagramSetting remove(long CSDiagramSettingId)
		throws NoSuchCSDiagramSettingException;

	public CSDiagramSetting updateImpl(CSDiagramSetting csDiagramSetting);

	/**
	 * Returns the cs diagram setting with the primary key or throws a <code>NoSuchCSDiagramSettingException</code> if it could not be found.
	 *
	 * @param CSDiagramSettingId the primary key of the cs diagram setting
	 * @return the cs diagram setting
	 * @throws NoSuchCSDiagramSettingException if a cs diagram setting with the primary key could not be found
	 */
	public CSDiagramSetting findByPrimaryKey(long CSDiagramSettingId)
		throws NoSuchCSDiagramSettingException;

	/**
	 * Returns the cs diagram setting with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CSDiagramSettingId the primary key of the cs diagram setting
	 * @return the cs diagram setting, or <code>null</code> if a cs diagram setting with the primary key could not be found
	 */
	public CSDiagramSetting fetchByPrimaryKey(long CSDiagramSettingId);

	/**
	 * Returns all the cs diagram settings.
	 *
	 * @return the cs diagram settings
	 */
	public java.util.List<CSDiagramSetting> findAll();

	/**
	 * Returns a range of all the cs diagram settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CSDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cs diagram settings
	 * @param end the upper bound of the range of cs diagram settings (not inclusive)
	 * @return the range of cs diagram settings
	 */
	public java.util.List<CSDiagramSetting> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the cs diagram settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CSDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cs diagram settings
	 * @param end the upper bound of the range of cs diagram settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cs diagram settings
	 */
	public java.util.List<CSDiagramSetting> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CSDiagramSetting>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cs diagram settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CSDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cs diagram settings
	 * @param end the upper bound of the range of cs diagram settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cs diagram settings
	 */
	public java.util.List<CSDiagramSetting> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CSDiagramSetting>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the cs diagram settings from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of cs diagram settings.
	 *
	 * @return the number of cs diagram settings
	 */
	public int countAll();

}