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

package com.liferay.account.service.persistence;

import com.liferay.account.exception.NoSuchEntryUserRelException;
import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import java.util.Set;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the account entry user rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AccountEntryUserRelUtil
 * @generated
 */
@ProviderType
public interface AccountEntryUserRelPersistence
	extends BasePersistence<AccountEntryUserRel> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AccountEntryUserRelUtil} to access the account entry user rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Caches the account entry user rel in the entity cache if it is enabled.
	 *
	 * @param accountEntryUserRel the account entry user rel
	 */
	public void cacheResult(AccountEntryUserRel accountEntryUserRel);

	/**
	 * Caches the account entry user rels in the entity cache if it is enabled.
	 *
	 * @param accountEntryUserRels the account entry user rels
	 */
	public void cacheResult(
		java.util.List<AccountEntryUserRel> accountEntryUserRels);

	/**
	 * Creates a new account entry user rel with the primary key. Does not add the account entry user rel to the database.
	 *
	 * @param accountEntryUserRelPK the primary key for the new account entry user rel
	 * @return the new account entry user rel
	 */
	public AccountEntryUserRel create(
		AccountEntryUserRelPK accountEntryUserRelPK);

	/**
	 * Removes the account entry user rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param accountEntryUserRelPK the primary key of the account entry user rel
	 * @return the account entry user rel that was removed
	 * @throws NoSuchEntryUserRelException if a account entry user rel with the primary key could not be found
	 */
	public AccountEntryUserRel remove(
			AccountEntryUserRelPK accountEntryUserRelPK)
		throws NoSuchEntryUserRelException;

	public AccountEntryUserRel updateImpl(
		AccountEntryUserRel accountEntryUserRel);

	/**
	 * Returns the account entry user rel with the primary key or throws a <code>NoSuchEntryUserRelException</code> if it could not be found.
	 *
	 * @param accountEntryUserRelPK the primary key of the account entry user rel
	 * @return the account entry user rel
	 * @throws NoSuchEntryUserRelException if a account entry user rel with the primary key could not be found
	 */
	public AccountEntryUserRel findByPrimaryKey(
			AccountEntryUserRelPK accountEntryUserRelPK)
		throws NoSuchEntryUserRelException;

	/**
	 * Returns the account entry user rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param accountEntryUserRelPK the primary key of the account entry user rel
	 * @return the account entry user rel, or <code>null</code> if a account entry user rel with the primary key could not be found
	 */
	public AccountEntryUserRel fetchByPrimaryKey(
		AccountEntryUserRelPK accountEntryUserRelPK);

	/**
	 * Returns all the account entry user rels.
	 *
	 * @return the account entry user rels
	 */
	public java.util.List<AccountEntryUserRel> findAll();

	/**
	 * Returns a range of all the account entry user rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AccountEntryUserRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of account entry user rels
	 * @param end the upper bound of the range of account entry user rels (not inclusive)
	 * @return the range of account entry user rels
	 */
	public java.util.List<AccountEntryUserRel> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the account entry user rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AccountEntryUserRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of account entry user rels
	 * @param end the upper bound of the range of account entry user rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of account entry user rels
	 */
	public java.util.List<AccountEntryUserRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AccountEntryUserRel>
			orderByComparator);

	/**
	 * Returns an ordered range of all the account entry user rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>AccountEntryUserRelModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of account entry user rels
	 * @param end the upper bound of the range of account entry user rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of account entry user rels
	 */
	public java.util.List<AccountEntryUserRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AccountEntryUserRel>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the account entry user rels from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of account entry user rels.
	 *
	 * @return the number of account entry user rels
	 */
	public int countAll();

	public Set<String> getCompoundPKColumnNames();

}