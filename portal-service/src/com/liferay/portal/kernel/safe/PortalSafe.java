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

package com.liferay.portal.kernel.safe;

import com.liferay.portal.kernel.safe.model.Item;

import java.util.List;

/**
 * The interface for Portal Safe implementations.
 *
 * <p>
 * Implementations should use {@link
 * com.liferay.portal.kernel.safe.serializer.ItemSerializerRegistryUtil} to
 * convert {@link Item} into {@link
 * com.liferay.portal.kernel.safe.serializer.SerializedItem} and {@link
 * com.liferay.portal.kernel.safe.storage.Storage} to load or store the
 * serialized item into underlying safe storage.
 * </p>
 *
 * @author Tomas Polesovsky
 */
public interface PortalSafe {

	/**
	 * Returns names of all {@link Item}s in the safe, stored under the
	 * companyId and the groupId.
	 *
	 * <p>
	 * May throw {@link com.liferay.portal.kernel.safe.storage.StorageException}
	 * if there is a problem with loading the item from the underlying storage
	 * </p>
	 *
	 * @param  companyId ID of company to load items from
	 * @param  groupId ID of group inside the company to load items from
	 * @return not <code>null</code> list of accessible items names. These can
	 *         be later used for loading or removing items from the safe.
	 * @throws PortalSafeException if an error occurs
	 */
	public List<String> listItemsNames(long companyId, long groupId)
		throws PortalSafeException;

	/**
	 * Loads and returns an item described by the companyId, the groupId and the
	 * item's name.
	 *
	 * <p>
	 * May throw <ul> <li> {@link
	 * com.liferay.portal.kernel.safe.model.NoSuchItemException} if there is no
	 * item found with the name </li> <li> {@link
	 * com.liferay.portal.kernel.safe.serializer.NoSuchItemSerializerException}
	 * if there is no serializer registered that is able to read the item </li>
	 * <li> {@link com.liferay.portal.kernel.safe.storage.StorageException} if
	 * there is a problem with loading the item from the underlying storage
	 * </li> </ul>
	 * </p>
	 *
	 * @param  companyId ID of company to load items from
	 * @param  groupId ID of group inside the company to load items from
	 * @param  name name of the item to be loaded
	 * @return descendant of {@link Item} interface
	 * @throws PortalSafeException if an other error occurs
	 */
	public Item loadItem(long companyId, long groupId, String name)
		throws PortalSafeException;

	/**
	 * Removes the item from the safe. Item is described by the companyId, the
	 * groupId and the item's name.
	 *
	 * <p>
	 * May throw <ul> <li> {@link
	 * com.liferay.portal.kernel.safe.model.NoSuchItemException} if there is no
	 * item found with the name </li> <li> {@link
	 * com.liferay.portal.kernel.safe.storage.StorageException} if there is a
	 * problem with accessing the item int the underlying storage </li> </ul>
	 * </p>
	 *
	 * @param  companyId ID of company to remove the item from
	 * @param  groupId ID of group inside the company to remove the item from
	 * @param  name name of the item to be removed
	 * @throws PortalSafeException if some error occurs during deleting
	 */
	public void removeItem(long companyId, long groupId, String name)
		throws PortalSafeException;

	/**
	 * Stores the item into safe.
	 *
	 * <p>
	 * May throw <ul> <li> {@link
	 * com.liferay.portal.kernel.safe.serializer.NoSuchItemSerializerException}
	 * if there is no serializer registered that is able to convert the item
	 * into its binary form </li> <li> {@link
	 * com.liferay.portal.kernel.safe.storage.StorageException} if there is a
	 * problem with saving the item into the underlying storage </li> </ul>
	 * </p>
	 *
	 * @param  companyId ID of company to save the item to
	 * @param  groupId ID of group inside the company to save the item to
	 * @param  item the item to be saved
	 * @throws PortalSafeException if an other error occurs
	 */
	public void saveItem(long companyId, long groupId, Item item)
		throws PortalSafeException;

}