/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.persistence;

import com.liferay.portal.kernel.exception.NoSuchPreferencesException;
import com.liferay.portal.kernel.model.PortalPreferences;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the portal preferences service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PortalPreferencesUtil
 * @generated
 */
@ProviderType
public interface PortalPreferencesPersistence
	extends BasePersistence<PortalPreferences> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link PortalPreferencesUtil} to access the portal preferences persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns an ordered range of all the portal preferenceses where ownerType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.PortalPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param ownerType the owner type
	 * @param start the lower bound of the range of portal preferenceses
	 * @param end the upper bound of the range of portal preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching portal preferenceses
	 */
	public java.util.List<PortalPreferences> findByOwnerType(
		int ownerType, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PortalPreferences>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first portal preferences in the ordered set where ownerType = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portal preferences
	 * @throws NoSuchPreferencesException if a matching portal preferences could not be found
	 */
	public PortalPreferences findByOwnerType_First(
			int ownerType,
			com.liferay.portal.kernel.util.OrderByComparator<PortalPreferences>
				orderByComparator)
		throws NoSuchPreferencesException;

	/**
	 * Returns the first portal preferences in the ordered set where ownerType = &#63;.
	 *
	 * @param ownerType the owner type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portal preferences, or <code>null</code> if a matching portal preferences could not be found
	 */
	public PortalPreferences fetchByOwnerType_First(
		int ownerType,
		com.liferay.portal.kernel.util.OrderByComparator<PortalPreferences>
			orderByComparator);

	/**
	 * Removes all the portal preferenceses where ownerType = &#63; from the database.
	 *
	 * @param ownerType the owner type
	 */
	public void removeByOwnerType(int ownerType);

	/**
	 * Returns the number of portal preferenceses where ownerType = &#63;.
	 *
	 * @param ownerType the owner type
	 * @return the number of matching portal preferenceses
	 */
	public int countByOwnerType(int ownerType);

	/**
	 * Returns the portal preferences where ownerId = &#63; and ownerType = &#63; or throws a <code>NoSuchPreferencesException</code> if it could not be found.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @return the matching portal preferences
	 * @throws NoSuchPreferencesException if a matching portal preferences could not be found
	 */
	public PortalPreferences findByO_O(long ownerId, int ownerType)
		throws NoSuchPreferencesException;

	/**
	 * Returns the portal preferences where ownerId = &#63; and ownerType = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching portal preferences, or <code>null</code> if a matching portal preferences could not be found
	 */
	public PortalPreferences fetchByO_O(
		long ownerId, int ownerType, boolean useFinderCache);

	/**
	 * Removes the portal preferences where ownerId = &#63; and ownerType = &#63; from the database.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @return the portal preferences that was removed
	 */
	public PortalPreferences removeByO_O(long ownerId, int ownerType)
		throws NoSuchPreferencesException;

	/**
	 * Returns the number of portal preferenceses where ownerId = &#63; and ownerType = &#63;.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @return the number of matching portal preferenceses
	 */
	public int countByO_O(long ownerId, int ownerType);

	/**
	 * Creates a new portal preferences with the primary key. Does not add the portal preferences to the database.
	 *
	 * @param portalPreferencesId the primary key for the new portal preferences
	 * @return the new portal preferences
	 */
	public PortalPreferences create(long portalPreferencesId);

	/**
	 * Removes the portal preferences with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param portalPreferencesId the primary key of the portal preferences
	 * @return the portal preferences that was removed
	 * @throws NoSuchPreferencesException if a portal preferences with the primary key could not be found
	 */
	public PortalPreferences remove(long portalPreferencesId)
		throws NoSuchPreferencesException;

	public PortalPreferences updateImpl(PortalPreferences portalPreferences);

	/**
	 * Returns the portal preferences with the primary key or throws a <code>NoSuchPreferencesException</code> if it could not be found.
	 *
	 * @param portalPreferencesId the primary key of the portal preferences
	 * @return the portal preferences
	 * @throws NoSuchPreferencesException if a portal preferences with the primary key could not be found
	 */
	public PortalPreferences findByPrimaryKey(long portalPreferencesId)
		throws NoSuchPreferencesException;

	/**
	 * Returns the portal preferences with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param portalPreferencesId the primary key of the portal preferences
	 * @return the portal preferences, or <code>null</code> if a portal preferences with the primary key could not be found
	 */
	public PortalPreferences fetchByPrimaryKey(long portalPreferencesId);

	/**
	 * Returns the portal preferences where ownerId = &#63; and ownerType = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param ownerId the owner ID
	 * @param ownerType the owner type
	 * @return the matching portal preferences, or <code>null</code> if a matching portal preferences could not be found
	 */
	public default PortalPreferences fetchByO_O(long ownerId, int ownerType) {
		return fetchByO_O(ownerId, ownerType, true);
	}

	/**
	 * Returns all the portal preferenceses where ownerType = &#63;.
	 *
	 * @param ownerType the owner type
	 * @return the matching portal preferenceses
	 */
	public default java.util.List<PortalPreferences> findByOwnerType(
		int ownerType) {

		return findByOwnerType(
			ownerType, com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the portal preferenceses where ownerType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.PortalPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param ownerType the owner type
	 * @param start the lower bound of the range of portal preferenceses
	 * @param end the upper bound of the range of portal preferenceses (not inclusive)
	 * @return the range of matching portal preferenceses
	 */
	public default java.util.List<PortalPreferences> findByOwnerType(
		int ownerType, int start, int end) {

		return findByOwnerType(ownerType, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the portal preferenceses where ownerType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.PortalPreferencesModelImpl</code>.
	 * </p>
	 *
	 * @param ownerType the owner type
	 * @param start the lower bound of the range of portal preferenceses
	 * @param end the upper bound of the range of portal preferenceses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching portal preferenceses
	 */
	public default java.util.List<PortalPreferences> findByOwnerType(
		int ownerType, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PortalPreferences>
			orderByComparator) {

		return findByOwnerType(ownerType, start, end, orderByComparator, true);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-1085689879