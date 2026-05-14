/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.patcher.service.persistence;

import com.liferay.osb.patcher.exception.NoSuchPatcherFixComponentException;
import com.liferay.osb.patcher.model.PatcherFixComponent;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the patcher fix component service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PatcherFixComponentUtil
 * @generated
 */
@ProviderType
public interface PatcherFixComponentPersistence
	extends BasePersistence<PatcherFixComponent> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link PatcherFixComponentUtil} to access the patcher fix component persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns the patcher fix component where name = &#63; or throws a <code>NoSuchPatcherFixComponentException</code> if it could not be found.
	 *
	 * @param name the name
	 * @return the matching patcher fix component
	 * @throws NoSuchPatcherFixComponentException if a matching patcher fix component could not be found
	 */
	public PatcherFixComponent findByName(String name)
		throws NoSuchPatcherFixComponentException;

	/**
	 * Returns the patcher fix component where name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching patcher fix component, or <code>null</code> if a matching patcher fix component could not be found
	 */
	public PatcherFixComponent fetchByName(String name, boolean useFinderCache);

	/**
	 * Removes the patcher fix component where name = &#63; from the database.
	 *
	 * @param name the name
	 * @return the patcher fix component that was removed
	 */
	public PatcherFixComponent removeByName(String name)
		throws NoSuchPatcherFixComponentException;

	/**
	 * Returns the number of patcher fix components where name = &#63;.
	 *
	 * @param name the name
	 * @return the number of matching patcher fix components
	 */
	public int countByName(String name);

	/**
	 * Creates a new patcher fix component with the primary key. Does not add the patcher fix component to the database.
	 *
	 * @param patcherFixComponentId the primary key for the new patcher fix component
	 * @return the new patcher fix component
	 */
	public PatcherFixComponent create(long patcherFixComponentId);

	/**
	 * Removes the patcher fix component with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param patcherFixComponentId the primary key of the patcher fix component
	 * @return the patcher fix component that was removed
	 * @throws NoSuchPatcherFixComponentException if a patcher fix component with the primary key could not be found
	 */
	public PatcherFixComponent remove(long patcherFixComponentId)
		throws NoSuchPatcherFixComponentException;

	public PatcherFixComponent updateImpl(
		PatcherFixComponent patcherFixComponent);

	/**
	 * Returns the patcher fix component with the primary key or throws a <code>NoSuchPatcherFixComponentException</code> if it could not be found.
	 *
	 * @param patcherFixComponentId the primary key of the patcher fix component
	 * @return the patcher fix component
	 * @throws NoSuchPatcherFixComponentException if a patcher fix component with the primary key could not be found
	 */
	public PatcherFixComponent findByPrimaryKey(long patcherFixComponentId)
		throws NoSuchPatcherFixComponentException;

	/**
	 * Returns the patcher fix component with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param patcherFixComponentId the primary key of the patcher fix component
	 * @return the patcher fix component, or <code>null</code> if a patcher fix component with the primary key could not be found
	 */
	public PatcherFixComponent fetchByPrimaryKey(long patcherFixComponentId);

	/**
	 * Returns the patcher fix component where name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param name the name
	 * @return the matching patcher fix component, or <code>null</code> if a matching patcher fix component could not be found
	 */
	public default PatcherFixComponent fetchByName(String name) {
		return fetchByName(name, true);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-1780984624