/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service.persistence;

import com.liferay.commerce.product.exception.NoSuchCPConfigurationEntrySettingException;
import com.liferay.commerce.product.model.CPConfigurationEntrySetting;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the cp configuration entry setting service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPConfigurationEntrySettingUtil
 * @generated
 */
@ProviderType
public interface CPConfigurationEntrySettingPersistence
	extends BasePersistence<CPConfigurationEntrySetting>,
			CTPersistence<CPConfigurationEntrySetting> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPConfigurationEntrySettingUtil} to access the cp configuration entry setting persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the cp configuration entry settings where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cp configuration entry settings
	 */
	public java.util.List<CPConfigurationEntrySetting> findByUuid(String uuid);

	/**
	 * Returns a range of all the cp configuration entry settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationEntrySettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp configuration entry settings
	 * @param end the upper bound of the range of cp configuration entry settings (not inclusive)
	 * @return the range of matching cp configuration entry settings
	 */
	public java.util.List<CPConfigurationEntrySetting> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the cp configuration entry settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationEntrySettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp configuration entry settings
	 * @param end the upper bound of the range of cp configuration entry settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp configuration entry settings
	 */
	public java.util.List<CPConfigurationEntrySetting> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPConfigurationEntrySetting> orderByComparator);

	/**
	 * Returns an ordered range of all the cp configuration entry settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationEntrySettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp configuration entry settings
	 * @param end the upper bound of the range of cp configuration entry settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp configuration entry settings
	 */
	public java.util.List<CPConfigurationEntrySetting> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPConfigurationEntrySetting> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cp configuration entry setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration entry setting
	 * @throws NoSuchCPConfigurationEntrySettingException if a matching cp configuration entry setting could not be found
	 */
	public CPConfigurationEntrySetting findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPConfigurationEntrySetting> orderByComparator)
		throws NoSuchCPConfigurationEntrySettingException;

	/**
	 * Returns the first cp configuration entry setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration entry setting, or <code>null</code> if a matching cp configuration entry setting could not be found
	 */
	public CPConfigurationEntrySetting fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPConfigurationEntrySetting> orderByComparator);

	/**
	 * Removes all the cp configuration entry settings where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of cp configuration entry settings where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cp configuration entry settings
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the cp configuration entry setting where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchCPConfigurationEntrySettingException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp configuration entry setting
	 * @throws NoSuchCPConfigurationEntrySettingException if a matching cp configuration entry setting could not be found
	 */
	public CPConfigurationEntrySetting findByUUID_G(String uuid, long groupId)
		throws NoSuchCPConfigurationEntrySettingException;

	/**
	 * Returns the cp configuration entry setting where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp configuration entry setting, or <code>null</code> if a matching cp configuration entry setting could not be found
	 */
	public CPConfigurationEntrySetting fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the cp configuration entry setting where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp configuration entry setting, or <code>null</code> if a matching cp configuration entry setting could not be found
	 */
	public CPConfigurationEntrySetting fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the cp configuration entry setting where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the cp configuration entry setting that was removed
	 */
	public CPConfigurationEntrySetting removeByUUID_G(String uuid, long groupId)
		throws NoSuchCPConfigurationEntrySettingException;

	/**
	 * Returns the number of cp configuration entry settings where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching cp configuration entry settings
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the cp configuration entry settings where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cp configuration entry settings
	 */
	public java.util.List<CPConfigurationEntrySetting> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the cp configuration entry settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationEntrySettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp configuration entry settings
	 * @param end the upper bound of the range of cp configuration entry settings (not inclusive)
	 * @return the range of matching cp configuration entry settings
	 */
	public java.util.List<CPConfigurationEntrySetting> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the cp configuration entry settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationEntrySettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp configuration entry settings
	 * @param end the upper bound of the range of cp configuration entry settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp configuration entry settings
	 */
	public java.util.List<CPConfigurationEntrySetting> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPConfigurationEntrySetting> orderByComparator);

	/**
	 * Returns an ordered range of all the cp configuration entry settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationEntrySettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp configuration entry settings
	 * @param end the upper bound of the range of cp configuration entry settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp configuration entry settings
	 */
	public java.util.List<CPConfigurationEntrySetting> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPConfigurationEntrySetting> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cp configuration entry setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration entry setting
	 * @throws NoSuchCPConfigurationEntrySettingException if a matching cp configuration entry setting could not be found
	 */
	public CPConfigurationEntrySetting findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPConfigurationEntrySetting> orderByComparator)
		throws NoSuchCPConfigurationEntrySettingException;

	/**
	 * Returns the first cp configuration entry setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration entry setting, or <code>null</code> if a matching cp configuration entry setting could not be found
	 */
	public CPConfigurationEntrySetting fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPConfigurationEntrySetting> orderByComparator);

	/**
	 * Removes all the cp configuration entry settings where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of cp configuration entry settings where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cp configuration entry settings
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the cp configuration entry settings where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching cp configuration entry settings
	 */
	public java.util.List<CPConfigurationEntrySetting> findByCompanyId(
		long companyId);

	/**
	 * Returns a range of all the cp configuration entry settings where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationEntrySettingModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp configuration entry settings
	 * @param end the upper bound of the range of cp configuration entry settings (not inclusive)
	 * @return the range of matching cp configuration entry settings
	 */
	public java.util.List<CPConfigurationEntrySetting> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the cp configuration entry settings where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationEntrySettingModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp configuration entry settings
	 * @param end the upper bound of the range of cp configuration entry settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp configuration entry settings
	 */
	public java.util.List<CPConfigurationEntrySetting> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPConfigurationEntrySetting> orderByComparator);

	/**
	 * Returns an ordered range of all the cp configuration entry settings where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationEntrySettingModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp configuration entry settings
	 * @param end the upper bound of the range of cp configuration entry settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp configuration entry settings
	 */
	public java.util.List<CPConfigurationEntrySetting> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPConfigurationEntrySetting> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cp configuration entry setting in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration entry setting
	 * @throws NoSuchCPConfigurationEntrySettingException if a matching cp configuration entry setting could not be found
	 */
	public CPConfigurationEntrySetting findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPConfigurationEntrySetting> orderByComparator)
		throws NoSuchCPConfigurationEntrySettingException;

	/**
	 * Returns the first cp configuration entry setting in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp configuration entry setting, or <code>null</code> if a matching cp configuration entry setting could not be found
	 */
	public CPConfigurationEntrySetting fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPConfigurationEntrySetting> orderByComparator);

	/**
	 * Removes all the cp configuration entry settings where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of cp configuration entry settings where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching cp configuration entry settings
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns the cp configuration entry setting where CPConfigurationEntryId = &#63; and type = &#63; or throws a <code>NoSuchCPConfigurationEntrySettingException</code> if it could not be found.
	 *
	 * @param CPConfigurationEntryId the cp configuration entry ID
	 * @param type the type
	 * @return the matching cp configuration entry setting
	 * @throws NoSuchCPConfigurationEntrySettingException if a matching cp configuration entry setting could not be found
	 */
	public CPConfigurationEntrySetting findByC_T(
			long CPConfigurationEntryId, int type)
		throws NoSuchCPConfigurationEntrySettingException;

	/**
	 * Returns the cp configuration entry setting where CPConfigurationEntryId = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param CPConfigurationEntryId the cp configuration entry ID
	 * @param type the type
	 * @return the matching cp configuration entry setting, or <code>null</code> if a matching cp configuration entry setting could not be found
	 */
	public CPConfigurationEntrySetting fetchByC_T(
		long CPConfigurationEntryId, int type);

	/**
	 * Returns the cp configuration entry setting where CPConfigurationEntryId = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param CPConfigurationEntryId the cp configuration entry ID
	 * @param type the type
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp configuration entry setting, or <code>null</code> if a matching cp configuration entry setting could not be found
	 */
	public CPConfigurationEntrySetting fetchByC_T(
		long CPConfigurationEntryId, int type, boolean useFinderCache);

	/**
	 * Removes the cp configuration entry setting where CPConfigurationEntryId = &#63; and type = &#63; from the database.
	 *
	 * @param CPConfigurationEntryId the cp configuration entry ID
	 * @param type the type
	 * @return the cp configuration entry setting that was removed
	 */
	public CPConfigurationEntrySetting removeByC_T(
			long CPConfigurationEntryId, int type)
		throws NoSuchCPConfigurationEntrySettingException;

	/**
	 * Returns the number of cp configuration entry settings where CPConfigurationEntryId = &#63; and type = &#63;.
	 *
	 * @param CPConfigurationEntryId the cp configuration entry ID
	 * @param type the type
	 * @return the number of matching cp configuration entry settings
	 */
	public int countByC_T(long CPConfigurationEntryId, int type);

	/**
	 * Caches the cp configuration entry setting in the entity cache if it is enabled.
	 *
	 * @param cpConfigurationEntrySetting the cp configuration entry setting
	 */
	public void cacheResult(
		CPConfigurationEntrySetting cpConfigurationEntrySetting);

	/**
	 * Caches the cp configuration entry settings in the entity cache if it is enabled.
	 *
	 * @param cpConfigurationEntrySettings the cp configuration entry settings
	 */
	public void cacheResult(
		java.util.List<CPConfigurationEntrySetting>
			cpConfigurationEntrySettings);

	/**
	 * Creates a new cp configuration entry setting with the primary key. Does not add the cp configuration entry setting to the database.
	 *
	 * @param CPConfigurationEntrySettingId the primary key for the new cp configuration entry setting
	 * @return the new cp configuration entry setting
	 */
	public CPConfigurationEntrySetting create(
		long CPConfigurationEntrySettingId);

	/**
	 * Removes the cp configuration entry setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPConfigurationEntrySettingId the primary key of the cp configuration entry setting
	 * @return the cp configuration entry setting that was removed
	 * @throws NoSuchCPConfigurationEntrySettingException if a cp configuration entry setting with the primary key could not be found
	 */
	public CPConfigurationEntrySetting remove(
			long CPConfigurationEntrySettingId)
		throws NoSuchCPConfigurationEntrySettingException;

	public CPConfigurationEntrySetting updateImpl(
		CPConfigurationEntrySetting cpConfigurationEntrySetting);

	/**
	 * Returns the cp configuration entry setting with the primary key or throws a <code>NoSuchCPConfigurationEntrySettingException</code> if it could not be found.
	 *
	 * @param CPConfigurationEntrySettingId the primary key of the cp configuration entry setting
	 * @return the cp configuration entry setting
	 * @throws NoSuchCPConfigurationEntrySettingException if a cp configuration entry setting with the primary key could not be found
	 */
	public CPConfigurationEntrySetting findByPrimaryKey(
			long CPConfigurationEntrySettingId)
		throws NoSuchCPConfigurationEntrySettingException;

	/**
	 * Returns the cp configuration entry setting with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPConfigurationEntrySettingId the primary key of the cp configuration entry setting
	 * @return the cp configuration entry setting, or <code>null</code> if a cp configuration entry setting with the primary key could not be found
	 */
	public CPConfigurationEntrySetting fetchByPrimaryKey(
		long CPConfigurationEntrySettingId);

	/**
	 * Returns all the cp configuration entry settings.
	 *
	 * @return the cp configuration entry settings
	 */
	public java.util.List<CPConfigurationEntrySetting> findAll();

	/**
	 * Returns a range of all the cp configuration entry settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationEntrySettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp configuration entry settings
	 * @param end the upper bound of the range of cp configuration entry settings (not inclusive)
	 * @return the range of cp configuration entry settings
	 */
	public java.util.List<CPConfigurationEntrySetting> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the cp configuration entry settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationEntrySettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp configuration entry settings
	 * @param end the upper bound of the range of cp configuration entry settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp configuration entry settings
	 */
	public java.util.List<CPConfigurationEntrySetting> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPConfigurationEntrySetting> orderByComparator);

	/**
	 * Returns an ordered range of all the cp configuration entry settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPConfigurationEntrySettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp configuration entry settings
	 * @param end the upper bound of the range of cp configuration entry settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cp configuration entry settings
	 */
	public java.util.List<CPConfigurationEntrySetting> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPConfigurationEntrySetting> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the cp configuration entry settings from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of cp configuration entry settings.
	 *
	 * @return the number of cp configuration entry settings
	 */
	public int countAll();

}
// LIFERAY-SERVICE-BUILDER-HASH:1119089545