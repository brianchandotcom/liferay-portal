/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.patcher.service.persistence;

import com.liferay.osb.patcher.exception.NoSuchPatcherTicketHintException;
import com.liferay.osb.patcher.model.PatcherTicketHint;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the patcher ticket hint service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PatcherTicketHintUtil
 * @generated
 */
@ProviderType
public interface PatcherTicketHintPersistence
	extends BasePersistence<PatcherTicketHint> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link PatcherTicketHintUtil} to access the patcher ticket hint persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns the patcher ticket hint where patcherProductVersionId = &#63; or throws a <code>NoSuchPatcherTicketHintException</code> if it could not be found.
	 *
	 * @param patcherProductVersionId the patcher product version ID
	 * @return the matching patcher ticket hint
	 * @throws NoSuchPatcherTicketHintException if a matching patcher ticket hint could not be found
	 */
	public PatcherTicketHint findByPatcherProductVersionId(
			long patcherProductVersionId)
		throws NoSuchPatcherTicketHintException;

	/**
	 * Returns the patcher ticket hint where patcherProductVersionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param patcherProductVersionId the patcher product version ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching patcher ticket hint, or <code>null</code> if a matching patcher ticket hint could not be found
	 */
	public PatcherTicketHint fetchByPatcherProductVersionId(
		long patcherProductVersionId, boolean useFinderCache);

	/**
	 * Removes the patcher ticket hint where patcherProductVersionId = &#63; from the database.
	 *
	 * @param patcherProductVersionId the patcher product version ID
	 * @return the patcher ticket hint that was removed
	 */
	public PatcherTicketHint removeByPatcherProductVersionId(
			long patcherProductVersionId)
		throws NoSuchPatcherTicketHintException;

	/**
	 * Returns the number of patcher ticket hints where patcherProductVersionId = &#63;.
	 *
	 * @param patcherProductVersionId the patcher product version ID
	 * @return the number of matching patcher ticket hints
	 */
	public int countByPatcherProductVersionId(long patcherProductVersionId);

	/**
	 * Creates a new patcher ticket hint with the primary key. Does not add the patcher ticket hint to the database.
	 *
	 * @param patcherTicketHintId the primary key for the new patcher ticket hint
	 * @return the new patcher ticket hint
	 */
	public PatcherTicketHint create(long patcherTicketHintId);

	/**
	 * Removes the patcher ticket hint with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param patcherTicketHintId the primary key of the patcher ticket hint
	 * @return the patcher ticket hint that was removed
	 * @throws NoSuchPatcherTicketHintException if a patcher ticket hint with the primary key could not be found
	 */
	public PatcherTicketHint remove(long patcherTicketHintId)
		throws NoSuchPatcherTicketHintException;

	public PatcherTicketHint updateImpl(PatcherTicketHint patcherTicketHint);

	/**
	 * Returns the patcher ticket hint with the primary key or throws a <code>NoSuchPatcherTicketHintException</code> if it could not be found.
	 *
	 * @param patcherTicketHintId the primary key of the patcher ticket hint
	 * @return the patcher ticket hint
	 * @throws NoSuchPatcherTicketHintException if a patcher ticket hint with the primary key could not be found
	 */
	public PatcherTicketHint findByPrimaryKey(long patcherTicketHintId)
		throws NoSuchPatcherTicketHintException;

	/**
	 * Returns the patcher ticket hint with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param patcherTicketHintId the primary key of the patcher ticket hint
	 * @return the patcher ticket hint, or <code>null</code> if a patcher ticket hint with the primary key could not be found
	 */
	public PatcherTicketHint fetchByPrimaryKey(long patcherTicketHintId);

	/**
	 * Returns the patcher ticket hint where patcherProductVersionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param patcherProductVersionId the patcher product version ID
	 * @return the matching patcher ticket hint, or <code>null</code> if a matching patcher ticket hint could not be found
	 */
	public default PatcherTicketHint fetchByPatcherProductVersionId(
		long patcherProductVersionId) {

		return fetchByPatcherProductVersionId(patcherProductVersionId, true);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:1926778078