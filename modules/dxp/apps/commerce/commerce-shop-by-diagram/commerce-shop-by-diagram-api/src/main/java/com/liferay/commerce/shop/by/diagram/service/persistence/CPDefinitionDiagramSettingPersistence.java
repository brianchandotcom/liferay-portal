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

package com.liferay.commerce.shop.by.diagram.service.persistence;

import com.liferay.commerce.shop.by.diagram.exception.NoSuchCPDefinitionDiagramSettingException;
import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSetting;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the cp definition diagram setting service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Andrea Sbarra
 * @see CPDefinitionDiagramSettingUtil
 * @generated
 */
@ProviderType
public interface CPDefinitionDiagramSettingPersistence
	extends BasePersistence<CPDefinitionDiagramSetting> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CPDefinitionDiagramSettingUtil} to access the cp definition diagram setting persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the cp definition diagram settings where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cp definition diagram settings
	 */
	public java.util.List<CPDefinitionDiagramSetting> findByUuid(String uuid);

	/**
	 * Returns a range of all the cp definition diagram settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp definition diagram settings
	 * @param end the upper bound of the range of cp definition diagram settings (not inclusive)
	 * @return the range of matching cp definition diagram settings
	 */
	public java.util.List<CPDefinitionDiagramSetting> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the cp definition diagram settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp definition diagram settings
	 * @param end the upper bound of the range of cp definition diagram settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition diagram settings
	 */
	public java.util.List<CPDefinitionDiagramSetting> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPDefinitionDiagramSetting> orderByComparator);

	/**
	 * Returns an ordered range of all the cp definition diagram settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp definition diagram settings
	 * @param end the upper bound of the range of cp definition diagram settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definition diagram settings
	 */
	public java.util.List<CPDefinitionDiagramSetting> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPDefinitionDiagramSetting> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cp definition diagram setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition diagram setting
	 * @throws NoSuchCPDefinitionDiagramSettingException if a matching cp definition diagram setting could not be found
	 */
	public CPDefinitionDiagramSetting findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPDefinitionDiagramSetting> orderByComparator)
		throws NoSuchCPDefinitionDiagramSettingException;

	/**
	 * Returns the first cp definition diagram setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition diagram setting, or <code>null</code> if a matching cp definition diagram setting could not be found
	 */
	public CPDefinitionDiagramSetting fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPDefinitionDiagramSetting> orderByComparator);

	/**
	 * Returns the last cp definition diagram setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition diagram setting
	 * @throws NoSuchCPDefinitionDiagramSettingException if a matching cp definition diagram setting could not be found
	 */
	public CPDefinitionDiagramSetting findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPDefinitionDiagramSetting> orderByComparator)
		throws NoSuchCPDefinitionDiagramSettingException;

	/**
	 * Returns the last cp definition diagram setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition diagram setting, or <code>null</code> if a matching cp definition diagram setting could not be found
	 */
	public CPDefinitionDiagramSetting fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPDefinitionDiagramSetting> orderByComparator);

	/**
	 * Returns the cp definition diagram settings before and after the current cp definition diagram setting in the ordered set where uuid = &#63;.
	 *
	 * @param CPDefinitionDiagramSettingId the primary key of the current cp definition diagram setting
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition diagram setting
	 * @throws NoSuchCPDefinitionDiagramSettingException if a cp definition diagram setting with the primary key could not be found
	 */
	public CPDefinitionDiagramSetting[] findByUuid_PrevAndNext(
			long CPDefinitionDiagramSettingId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPDefinitionDiagramSetting> orderByComparator)
		throws NoSuchCPDefinitionDiagramSettingException;

	/**
	 * Removes all the cp definition diagram settings where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of cp definition diagram settings where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cp definition diagram settings
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the cp definition diagram settings where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cp definition diagram settings
	 */
	public java.util.List<CPDefinitionDiagramSetting> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the cp definition diagram settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definition diagram settings
	 * @param end the upper bound of the range of cp definition diagram settings (not inclusive)
	 * @return the range of matching cp definition diagram settings
	 */
	public java.util.List<CPDefinitionDiagramSetting> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the cp definition diagram settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definition diagram settings
	 * @param end the upper bound of the range of cp definition diagram settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definition diagram settings
	 */
	public java.util.List<CPDefinitionDiagramSetting> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPDefinitionDiagramSetting> orderByComparator);

	/**
	 * Returns an ordered range of all the cp definition diagram settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definition diagram settings
	 * @param end the upper bound of the range of cp definition diagram settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definition diagram settings
	 */
	public java.util.List<CPDefinitionDiagramSetting> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPDefinitionDiagramSetting> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first cp definition diagram setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition diagram setting
	 * @throws NoSuchCPDefinitionDiagramSettingException if a matching cp definition diagram setting could not be found
	 */
	public CPDefinitionDiagramSetting findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPDefinitionDiagramSetting> orderByComparator)
		throws NoSuchCPDefinitionDiagramSettingException;

	/**
	 * Returns the first cp definition diagram setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition diagram setting, or <code>null</code> if a matching cp definition diagram setting could not be found
	 */
	public CPDefinitionDiagramSetting fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPDefinitionDiagramSetting> orderByComparator);

	/**
	 * Returns the last cp definition diagram setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition diagram setting
	 * @throws NoSuchCPDefinitionDiagramSettingException if a matching cp definition diagram setting could not be found
	 */
	public CPDefinitionDiagramSetting findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPDefinitionDiagramSetting> orderByComparator)
		throws NoSuchCPDefinitionDiagramSettingException;

	/**
	 * Returns the last cp definition diagram setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition diagram setting, or <code>null</code> if a matching cp definition diagram setting could not be found
	 */
	public CPDefinitionDiagramSetting fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPDefinitionDiagramSetting> orderByComparator);

	/**
	 * Returns the cp definition diagram settings before and after the current cp definition diagram setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param CPDefinitionDiagramSettingId the primary key of the current cp definition diagram setting
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition diagram setting
	 * @throws NoSuchCPDefinitionDiagramSettingException if a cp definition diagram setting with the primary key could not be found
	 */
	public CPDefinitionDiagramSetting[] findByUuid_C_PrevAndNext(
			long CPDefinitionDiagramSettingId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CPDefinitionDiagramSetting> orderByComparator)
		throws NoSuchCPDefinitionDiagramSettingException;

	/**
	 * Removes all the cp definition diagram settings where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of cp definition diagram settings where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cp definition diagram settings
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns the cp definition diagram setting where CPDefinitionId = &#63; or throws a <code>NoSuchCPDefinitionDiagramSettingException</code> if it could not be found.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the matching cp definition diagram setting
	 * @throws NoSuchCPDefinitionDiagramSettingException if a matching cp definition diagram setting could not be found
	 */
	public CPDefinitionDiagramSetting findByCPDefinitionId(long CPDefinitionId)
		throws NoSuchCPDefinitionDiagramSettingException;

	/**
	 * Returns the cp definition diagram setting where CPDefinitionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the matching cp definition diagram setting, or <code>null</code> if a matching cp definition diagram setting could not be found
	 */
	public CPDefinitionDiagramSetting fetchByCPDefinitionId(
		long CPDefinitionId);

	/**
	 * Returns the cp definition diagram setting where CPDefinitionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp definition diagram setting, or <code>null</code> if a matching cp definition diagram setting could not be found
	 */
	public CPDefinitionDiagramSetting fetchByCPDefinitionId(
		long CPDefinitionId, boolean useFinderCache);

	/**
	 * Removes the cp definition diagram setting where CPDefinitionId = &#63; from the database.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the cp definition diagram setting that was removed
	 */
	public CPDefinitionDiagramSetting removeByCPDefinitionId(
			long CPDefinitionId)
		throws NoSuchCPDefinitionDiagramSettingException;

	/**
	 * Returns the number of cp definition diagram settings where CPDefinitionId = &#63;.
	 *
	 * @param CPDefinitionId the cp definition ID
	 * @return the number of matching cp definition diagram settings
	 */
	public int countByCPDefinitionId(long CPDefinitionId);

	/**
	 * Caches the cp definition diagram setting in the entity cache if it is enabled.
	 *
	 * @param cpDefinitionDiagramSetting the cp definition diagram setting
	 */
	public void cacheResult(
		CPDefinitionDiagramSetting cpDefinitionDiagramSetting);

	/**
	 * Caches the cp definition diagram settings in the entity cache if it is enabled.
	 *
	 * @param cpDefinitionDiagramSettings the cp definition diagram settings
	 */
	public void cacheResult(
		java.util.List<CPDefinitionDiagramSetting> cpDefinitionDiagramSettings);

	/**
	 * Creates a new cp definition diagram setting with the primary key. Does not add the cp definition diagram setting to the database.
	 *
	 * @param CPDefinitionDiagramSettingId the primary key for the new cp definition diagram setting
	 * @return the new cp definition diagram setting
	 */
	public CPDefinitionDiagramSetting create(long CPDefinitionDiagramSettingId);

	/**
	 * Removes the cp definition diagram setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPDefinitionDiagramSettingId the primary key of the cp definition diagram setting
	 * @return the cp definition diagram setting that was removed
	 * @throws NoSuchCPDefinitionDiagramSettingException if a cp definition diagram setting with the primary key could not be found
	 */
	public CPDefinitionDiagramSetting remove(long CPDefinitionDiagramSettingId)
		throws NoSuchCPDefinitionDiagramSettingException;

	public CPDefinitionDiagramSetting updateImpl(
		CPDefinitionDiagramSetting cpDefinitionDiagramSetting);

	/**
	 * Returns the cp definition diagram setting with the primary key or throws a <code>NoSuchCPDefinitionDiagramSettingException</code> if it could not be found.
	 *
	 * @param CPDefinitionDiagramSettingId the primary key of the cp definition diagram setting
	 * @return the cp definition diagram setting
	 * @throws NoSuchCPDefinitionDiagramSettingException if a cp definition diagram setting with the primary key could not be found
	 */
	public CPDefinitionDiagramSetting findByPrimaryKey(
			long CPDefinitionDiagramSettingId)
		throws NoSuchCPDefinitionDiagramSettingException;

	/**
	 * Returns the cp definition diagram setting with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPDefinitionDiagramSettingId the primary key of the cp definition diagram setting
	 * @return the cp definition diagram setting, or <code>null</code> if a cp definition diagram setting with the primary key could not be found
	 */
	public CPDefinitionDiagramSetting fetchByPrimaryKey(
		long CPDefinitionDiagramSettingId);

	/**
	 * Returns all the cp definition diagram settings.
	 *
	 * @return the cp definition diagram settings
	 */
	public java.util.List<CPDefinitionDiagramSetting> findAll();

	/**
	 * Returns a range of all the cp definition diagram settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition diagram settings
	 * @param end the upper bound of the range of cp definition diagram settings (not inclusive)
	 * @return the range of cp definition diagram settings
	 */
	public java.util.List<CPDefinitionDiagramSetting> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the cp definition diagram settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition diagram settings
	 * @param end the upper bound of the range of cp definition diagram settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp definition diagram settings
	 */
	public java.util.List<CPDefinitionDiagramSetting> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPDefinitionDiagramSetting> orderByComparator);

	/**
	 * Returns an ordered range of all the cp definition diagram settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionDiagramSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definition diagram settings
	 * @param end the upper bound of the range of cp definition diagram settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cp definition diagram settings
	 */
	public java.util.List<CPDefinitionDiagramSetting> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CPDefinitionDiagramSetting> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the cp definition diagram settings from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of cp definition diagram settings.
	 *
	 * @return the number of cp definition diagram settings
	 */
	public int countAll();

}