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

package com.liferay.fragment.service.persistence;

import com.liferay.fragment.exception.NoSuchEntryContributedException;
import com.liferay.fragment.model.FragmentEntryContributed;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the fragment entry contributed service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FragmentEntryContributedUtil
 * @generated
 */
@ProviderType
public interface FragmentEntryContributedPersistence
	extends BasePersistence<FragmentEntryContributed>,
			CTPersistence<FragmentEntryContributed> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link FragmentEntryContributedUtil} to access the fragment entry contributed persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns the fragment entry contributed where fragmentEntryKey = &#63; or throws a <code>NoSuchEntryContributedException</code> if it could not be found.
	 *
	 * @param fragmentEntryKey the fragment entry key
	 * @return the matching fragment entry contributed
	 * @throws NoSuchEntryContributedException if a matching fragment entry contributed could not be found
	 */
	public FragmentEntryContributed findByFragmentEntryKey(
			String fragmentEntryKey)
		throws NoSuchEntryContributedException;

	/**
	 * Returns the fragment entry contributed where fragmentEntryKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param fragmentEntryKey the fragment entry key
	 * @return the matching fragment entry contributed, or <code>null</code> if a matching fragment entry contributed could not be found
	 */
	public FragmentEntryContributed fetchByFragmentEntryKey(
		String fragmentEntryKey);

	/**
	 * Returns the fragment entry contributed where fragmentEntryKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param fragmentEntryKey the fragment entry key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching fragment entry contributed, or <code>null</code> if a matching fragment entry contributed could not be found
	 */
	public FragmentEntryContributed fetchByFragmentEntryKey(
		String fragmentEntryKey, boolean useFinderCache);

	/**
	 * Removes the fragment entry contributed where fragmentEntryKey = &#63; from the database.
	 *
	 * @param fragmentEntryKey the fragment entry key
	 * @return the fragment entry contributed that was removed
	 */
	public FragmentEntryContributed removeByFragmentEntryKey(
			String fragmentEntryKey)
		throws NoSuchEntryContributedException;

	/**
	 * Returns the number of fragment entry contributeds where fragmentEntryKey = &#63;.
	 *
	 * @param fragmentEntryKey the fragment entry key
	 * @return the number of matching fragment entry contributeds
	 */
	public int countByFragmentEntryKey(String fragmentEntryKey);

	/**
	 * Caches the fragment entry contributed in the entity cache if it is enabled.
	 *
	 * @param fragmentEntryContributed the fragment entry contributed
	 */
	public void cacheResult(FragmentEntryContributed fragmentEntryContributed);

	/**
	 * Caches the fragment entry contributeds in the entity cache if it is enabled.
	 *
	 * @param fragmentEntryContributeds the fragment entry contributeds
	 */
	public void cacheResult(
		java.util.List<FragmentEntryContributed> fragmentEntryContributeds);

	/**
	 * Creates a new fragment entry contributed with the primary key. Does not add the fragment entry contributed to the database.
	 *
	 * @param fragmentEntryContributedId the primary key for the new fragment entry contributed
	 * @return the new fragment entry contributed
	 */
	public FragmentEntryContributed create(long fragmentEntryContributedId);

	/**
	 * Removes the fragment entry contributed with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fragmentEntryContributedId the primary key of the fragment entry contributed
	 * @return the fragment entry contributed that was removed
	 * @throws NoSuchEntryContributedException if a fragment entry contributed with the primary key could not be found
	 */
	public FragmentEntryContributed remove(long fragmentEntryContributedId)
		throws NoSuchEntryContributedException;

	public FragmentEntryContributed updateImpl(
		FragmentEntryContributed fragmentEntryContributed);

	/**
	 * Returns the fragment entry contributed with the primary key or throws a <code>NoSuchEntryContributedException</code> if it could not be found.
	 *
	 * @param fragmentEntryContributedId the primary key of the fragment entry contributed
	 * @return the fragment entry contributed
	 * @throws NoSuchEntryContributedException if a fragment entry contributed with the primary key could not be found
	 */
	public FragmentEntryContributed findByPrimaryKey(
			long fragmentEntryContributedId)
		throws NoSuchEntryContributedException;

	/**
	 * Returns the fragment entry contributed with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fragmentEntryContributedId the primary key of the fragment entry contributed
	 * @return the fragment entry contributed, or <code>null</code> if a fragment entry contributed with the primary key could not be found
	 */
	public FragmentEntryContributed fetchByPrimaryKey(
		long fragmentEntryContributedId);

	/**
	 * Returns all the fragment entry contributeds.
	 *
	 * @return the fragment entry contributeds
	 */
	public java.util.List<FragmentEntryContributed> findAll();

	/**
	 * Returns a range of all the fragment entry contributeds.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryContributedModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment entry contributeds
	 * @param end the upper bound of the range of fragment entry contributeds (not inclusive)
	 * @return the range of fragment entry contributeds
	 */
	public java.util.List<FragmentEntryContributed> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the fragment entry contributeds.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryContributedModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment entry contributeds
	 * @param end the upper bound of the range of fragment entry contributeds (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of fragment entry contributeds
	 */
	public java.util.List<FragmentEntryContributed> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<FragmentEntryContributed> orderByComparator);

	/**
	 * Returns an ordered range of all the fragment entry contributeds.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryContributedModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment entry contributeds
	 * @param end the upper bound of the range of fragment entry contributeds (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of fragment entry contributeds
	 */
	public java.util.List<FragmentEntryContributed> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<FragmentEntryContributed> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the fragment entry contributeds from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of fragment entry contributeds.
	 *
	 * @return the number of fragment entry contributeds
	 */
	public int countAll();

}