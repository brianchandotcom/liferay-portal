/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.safe.storage;

import com.liferay.portal.kernel.safe.model.NoSuchItemException;
import com.liferay.portal.kernel.safe.serializer.SerializedItem;

import java.util.List;

/**
 * Keep it secret! Keep it safe!
 *
 * <p>
 * It's assumed that the implementation of the interface use some kind of
 * underlying storage, e.g. file system or database. Because it's possible that
 * data saved in the storage can leak, the implementation must make sure that:
 * <ul> <li> any written data are encrypted using a strong key </li> <li> the
 * key is strong enough to endure offline attack in case of the data leak </li>
 * <li> the key and the encrypted data are separated, so that an attacker must
 * gain access to multiple locations to be able to decrypt the data </li> </ul>
 * </p>
 *
 * @author Tomas Polesovsky
 */
public interface Storage {

	/**
	 * Returns all items' names that are stored under the companyId and the
	 * groupId.
	 *
	 * @param  companyId ID of the company where the data belongs to
	 * @param  groupId ID of the group where the data belongs to
	 * @return names of items stored inside a storage for {companyId, groupId}
	 * @throws StorageException if an error occurs while loading
	 */
	public List<String> listNames(long companyId, long groupId)
		throws StorageException;

	/**
	 * Loads encrypted binary stream from an underlying storage, decrypts and
	 * returns in {@link SerializedItem#getItemStream()}.
	 *
	 * <p>
	 * Implementation MUST initialize companyId, groupId and itemStream of the
	 * returning {@link SerializedItem}
	 * </p>
	 *
	 * @param  companyId ID of the company where the data belongs to
	 * @param  groupId ID of the group where the data belongs to
	 * @param  name name of the item
	 * @return serializedItem with initialized companyId, groupId and itemStream
	 * @throws NoSuchItemException if no item is found
	 * @throws StorageException if an error occurs while loading
	 */
	public SerializedItem load(long companyId, long groupId, String name)
		throws NoSuchItemException, StorageException;

	/**
	 * Deletes the item defined by the companyId, groupId and the name.
	 *
	 * @param  companyId ID of the company where the data belongs to
	 * @param  groupId ID of the group where the data belongs to
	 * @param  name name of the item to be removed
	 * @throws NoSuchItemException if no item is found
	 * @throws StorageException if an error occurs while loading
	 */
	public void remove(long companyId, long groupId, String name)
		throws NoSuchItemException, StorageException;

	/**
	 * Saves the serializedItem into underlying storage.
	 *
	 * @param  serializedItem binary form of item to be saved
	 * @param  name name for the item
	 * @throws
	 */
	public void save(SerializedItem serializedItem, String name)
		throws StorageException;

}