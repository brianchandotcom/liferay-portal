/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.persistence;

import com.liferay.portal.kernel.exception.NoSuchPermissionPropagationException;
import com.liferay.portal.kernel.model.PermissionPropagation;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the permission propagation service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PermissionPropagationUtil
 * @generated
 */
@ProviderType
public interface PermissionPropagationPersistence
	extends BasePersistence<PermissionPropagation>,
			CTPersistence<PermissionPropagation> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link PermissionPropagationUtil} to access the permission propagation persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Caches the permission propagation in the entity cache if it is enabled.
	 *
	 * @param permissionPropagation the permission propagation
	 */
	public void cacheResult(PermissionPropagation permissionPropagation);

	/**
	 * Caches the permission propagations in the entity cache if it is enabled.
	 *
	 * @param permissionPropagations the permission propagations
	 */
	public void cacheResult(
		java.util.List<PermissionPropagation> permissionPropagations);

	/**
	 * Creates a new permission propagation with the primary key. Does not add the permission propagation to the database.
	 *
	 * @param permissionPropagationId the primary key for the new permission propagation
	 * @return the new permission propagation
	 */
	public PermissionPropagation create(long permissionPropagationId);

	/**
	 * Removes the permission propagation with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param permissionPropagationId the primary key of the permission propagation
	 * @return the permission propagation that was removed
	 * @throws NoSuchPermissionPropagationException if a permission propagation with the primary key could not be found
	 */
	public PermissionPropagation remove(long permissionPropagationId)
		throws NoSuchPermissionPropagationException;

	public PermissionPropagation updateImpl(
		PermissionPropagation permissionPropagation);

	/**
	 * Returns the permission propagation with the primary key or throws a <code>NoSuchPermissionPropagationException</code> if it could not be found.
	 *
	 * @param permissionPropagationId the primary key of the permission propagation
	 * @return the permission propagation
	 * @throws NoSuchPermissionPropagationException if a permission propagation with the primary key could not be found
	 */
	public PermissionPropagation findByPrimaryKey(long permissionPropagationId)
		throws NoSuchPermissionPropagationException;

	/**
	 * Returns the permission propagation with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param permissionPropagationId the primary key of the permission propagation
	 * @return the permission propagation, or <code>null</code> if a permission propagation with the primary key could not be found
	 */
	public PermissionPropagation fetchByPrimaryKey(
		long permissionPropagationId);

	/**
	 * Returns all the permission propagations.
	 *
	 * @return the permission propagations
	 */
	public java.util.List<PermissionPropagation> findAll();

	/**
	 * Returns a range of all the permission propagations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PermissionPropagationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of permission propagations
	 * @param end the upper bound of the range of permission propagations (not inclusive)
	 * @return the range of permission propagations
	 */
	public java.util.List<PermissionPropagation> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the permission propagations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PermissionPropagationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of permission propagations
	 * @param end the upper bound of the range of permission propagations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of permission propagations
	 */
	public java.util.List<PermissionPropagation> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PermissionPropagation>
			orderByComparator);

	/**
	 * Returns an ordered range of all the permission propagations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PermissionPropagationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of permission propagations
	 * @param end the upper bound of the range of permission propagations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of permission propagations
	 */
	public java.util.List<PermissionPropagation> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<PermissionPropagation>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the permission propagations from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of permission propagations.
	 *
	 * @return the number of permission propagations
	 */
	public int countAll();

}