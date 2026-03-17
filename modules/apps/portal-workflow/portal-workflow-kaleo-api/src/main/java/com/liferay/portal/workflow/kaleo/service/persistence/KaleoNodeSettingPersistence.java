/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;
import com.liferay.portal.workflow.kaleo.exception.NoSuchNodeSettingException;
import com.liferay.portal.workflow.kaleo.model.KaleoNodeSetting;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the kaleo node setting service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KaleoNodeSettingUtil
 * @generated
 */
@ProviderType
public interface KaleoNodeSettingPersistence
	extends BasePersistence<KaleoNodeSetting>, CTPersistence<KaleoNodeSetting> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link KaleoNodeSettingUtil} to access the kaleo node setting persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the kaleo node settings where kaleoNodeId = &#63;.
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @return the matching kaleo node settings
	 */
	public java.util.List<KaleoNodeSetting> findByKaleoNodeId(long kaleoNodeId);

	/**
	 * Returns a range of all the kaleo node settings where kaleoNodeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNodeSettingModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @param start the lower bound of the range of kaleo node settings
	 * @param end the upper bound of the range of kaleo node settings (not inclusive)
	 * @return the range of matching kaleo node settings
	 */
	public java.util.List<KaleoNodeSetting> findByKaleoNodeId(
		long kaleoNodeId, int start, int end);

	/**
	 * Returns an ordered range of all the kaleo node settings where kaleoNodeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNodeSettingModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @param start the lower bound of the range of kaleo node settings
	 * @param end the upper bound of the range of kaleo node settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo node settings
	 */
	public java.util.List<KaleoNodeSetting> findByKaleoNodeId(
		long kaleoNodeId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoNodeSetting>
			orderByComparator);

	/**
	 * Returns an ordered range of all the kaleo node settings where kaleoNodeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNodeSettingModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @param start the lower bound of the range of kaleo node settings
	 * @param end the upper bound of the range of kaleo node settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo node settings
	 */
	public java.util.List<KaleoNodeSetting> findByKaleoNodeId(
		long kaleoNodeId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoNodeSetting>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first kaleo node setting in the ordered set where kaleoNodeId = &#63;.
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo node setting
	 * @throws NoSuchNodeSettingException if a matching kaleo node setting could not be found
	 */
	public KaleoNodeSetting findByKaleoNodeId_First(
			long kaleoNodeId,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoNodeSetting>
				orderByComparator)
		throws NoSuchNodeSettingException;

	/**
	 * Returns the first kaleo node setting in the ordered set where kaleoNodeId = &#63;.
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo node setting, or <code>null</code> if a matching kaleo node setting could not be found
	 */
	public KaleoNodeSetting fetchByKaleoNodeId_First(
		long kaleoNodeId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoNodeSetting>
			orderByComparator);

	/**
	 * Returns the last kaleo node setting in the ordered set where kaleoNodeId = &#63;.
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo node setting
	 * @throws NoSuchNodeSettingException if a matching kaleo node setting could not be found
	 */
	public KaleoNodeSetting findByKaleoNodeId_Last(
			long kaleoNodeId,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoNodeSetting>
				orderByComparator)
		throws NoSuchNodeSettingException;

	/**
	 * Returns the last kaleo node setting in the ordered set where kaleoNodeId = &#63;.
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo node setting, or <code>null</code> if a matching kaleo node setting could not be found
	 */
	public KaleoNodeSetting fetchByKaleoNodeId_Last(
		long kaleoNodeId,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoNodeSetting>
			orderByComparator);

	/**
	 * Returns the kaleo node settings before and after the current kaleo node setting in the ordered set where kaleoNodeId = &#63;.
	 *
	 * @param kaleoNodeSettingId the primary key of the current kaleo node setting
	 * @param kaleoNodeId the kaleo node ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo node setting
	 * @throws NoSuchNodeSettingException if a kaleo node setting with the primary key could not be found
	 */
	public KaleoNodeSetting[] findByKaleoNodeId_PrevAndNext(
			long kaleoNodeSettingId, long kaleoNodeId,
			com.liferay.portal.kernel.util.OrderByComparator<KaleoNodeSetting>
				orderByComparator)
		throws NoSuchNodeSettingException;

	/**
	 * Removes all the kaleo node settings where kaleoNodeId = &#63; from the database.
	 *
	 * @param kaleoNodeId the kaleo node ID
	 */
	public void removeByKaleoNodeId(long kaleoNodeId);

	/**
	 * Returns the number of kaleo node settings where kaleoNodeId = &#63;.
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @return the number of matching kaleo node settings
	 */
	public int countByKaleoNodeId(long kaleoNodeId);

	/**
	 * Returns the kaleo node setting where kaleoNodeId = &#63; and name = &#63; or throws a <code>NoSuchNodeSettingException</code> if it could not be found.
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @param name the name
	 * @return the matching kaleo node setting
	 * @throws NoSuchNodeSettingException if a matching kaleo node setting could not be found
	 */
	public KaleoNodeSetting findByKNI_N(long kaleoNodeId, String name)
		throws NoSuchNodeSettingException;

	/**
	 * Returns the kaleo node setting where kaleoNodeId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @param name the name
	 * @return the matching kaleo node setting, or <code>null</code> if a matching kaleo node setting could not be found
	 */
	public KaleoNodeSetting fetchByKNI_N(long kaleoNodeId, String name);

	/**
	 * Returns the kaleo node setting where kaleoNodeId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching kaleo node setting, or <code>null</code> if a matching kaleo node setting could not be found
	 */
	public KaleoNodeSetting fetchByKNI_N(
		long kaleoNodeId, String name, boolean useFinderCache);

	/**
	 * Removes the kaleo node setting where kaleoNodeId = &#63; and name = &#63; from the database.
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @param name the name
	 * @return the kaleo node setting that was removed
	 */
	public KaleoNodeSetting removeByKNI_N(long kaleoNodeId, String name)
		throws NoSuchNodeSettingException;

	/**
	 * Returns the number of kaleo node settings where kaleoNodeId = &#63; and name = &#63;.
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @param name the name
	 * @return the number of matching kaleo node settings
	 */
	public int countByKNI_N(long kaleoNodeId, String name);

	/**
	 * Caches the kaleo node setting in the entity cache if it is enabled.
	 *
	 * @param kaleoNodeSetting the kaleo node setting
	 */
	public void cacheResult(KaleoNodeSetting kaleoNodeSetting);

	/**
	 * Caches the kaleo node settings in the entity cache if it is enabled.
	 *
	 * @param kaleoNodeSettings the kaleo node settings
	 */
	public void cacheResult(java.util.List<KaleoNodeSetting> kaleoNodeSettings);

	/**
	 * Creates a new kaleo node setting with the primary key. Does not add the kaleo node setting to the database.
	 *
	 * @param kaleoNodeSettingId the primary key for the new kaleo node setting
	 * @return the new kaleo node setting
	 */
	public KaleoNodeSetting create(long kaleoNodeSettingId);

	/**
	 * Removes the kaleo node setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoNodeSettingId the primary key of the kaleo node setting
	 * @return the kaleo node setting that was removed
	 * @throws NoSuchNodeSettingException if a kaleo node setting with the primary key could not be found
	 */
	public KaleoNodeSetting remove(long kaleoNodeSettingId)
		throws NoSuchNodeSettingException;

	public KaleoNodeSetting updateImpl(KaleoNodeSetting kaleoNodeSetting);

	/**
	 * Returns the kaleo node setting with the primary key or throws a <code>NoSuchNodeSettingException</code> if it could not be found.
	 *
	 * @param kaleoNodeSettingId the primary key of the kaleo node setting
	 * @return the kaleo node setting
	 * @throws NoSuchNodeSettingException if a kaleo node setting with the primary key could not be found
	 */
	public KaleoNodeSetting findByPrimaryKey(long kaleoNodeSettingId)
		throws NoSuchNodeSettingException;

	/**
	 * Returns the kaleo node setting with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param kaleoNodeSettingId the primary key of the kaleo node setting
	 * @return the kaleo node setting, or <code>null</code> if a kaleo node setting with the primary key could not be found
	 */
	public KaleoNodeSetting fetchByPrimaryKey(long kaleoNodeSettingId);

	/**
	 * Returns all the kaleo node settings.
	 *
	 * @return the kaleo node settings
	 */
	public java.util.List<KaleoNodeSetting> findAll();

	/**
	 * Returns a range of all the kaleo node settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNodeSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo node settings
	 * @param end the upper bound of the range of kaleo node settings (not inclusive)
	 * @return the range of kaleo node settings
	 */
	public java.util.List<KaleoNodeSetting> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the kaleo node settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNodeSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo node settings
	 * @param end the upper bound of the range of kaleo node settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of kaleo node settings
	 */
	public java.util.List<KaleoNodeSetting> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoNodeSetting>
			orderByComparator);

	/**
	 * Returns an ordered range of all the kaleo node settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoNodeSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo node settings
	 * @param end the upper bound of the range of kaleo node settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of kaleo node settings
	 */
	public java.util.List<KaleoNodeSetting> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<KaleoNodeSetting>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the kaleo node settings from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of kaleo node settings.
	 *
	 * @return the number of kaleo node settings
	 */
	public int countAll();

}
// SB-Hash:552981433