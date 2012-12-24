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

package com.liferay.portal.kernel.safe.serializer;

import com.liferay.portal.kernel.safe.model.Item;

/**
 * Stores {@link ItemSerializer}s and use them for storing and loading {@link
 * Item}s in their serialized form {@link SerializedItem}.
 *
 * @author Tomas Polesovsky
 */
public interface ItemSerializerRegistry {

	/**
	 * Removes the serializer from the registry.
	 *
	 * @param  serializer the serializer to be removed.
	 * @return <code>true</code> if serializer was found in the registry and
	 *         successfully removed, <code>false</code> otherwise
	 */
	public boolean deregisterSerializer(ItemSerializer serializer);

	/**
	 * Tries to parse the serializedItem and create a new {@link Item}
	 * descendant using one of registered serializers.
	 *
	 * <p>
	 * Implementation MUST make sure that returning {@link Item}'s common
	 * attributes like companyId and groupId are initialized from the
	 * serializedItem.
	 * </p>
	 *
	 * @param  serializedItem serialized form of item to be read
	 * @return deserialized instance of the serializedItem
	 * @throws ItemSerializerException if the deserialization process fails
	 * @throws NoSuchItemSerializerException if no suitable serializer is found
	 *         to parse the serializedItem
	 */
	public Item read(SerializedItem serializedItem)
		throws ItemSerializerException, NoSuchItemSerializerException;

	/**
	 * Adds the serializer into the registry.
	 *
	 * @param  serializer the serializer to be registered
	 * @throws
	 */
	public void registerSerializer(ItemSerializer serializer)
		throws DuplicateItemSerializerException;

	/**
	 * Tries to convert the item into returned {@link SerializedItem} using one
	 * of registered serializers.
	 *
	 * <p>
	 * Implementation MUST make sure that returning {@link SerializedItem}'s
	 * common attributes like companyId and groupId are initialized from the
	 * item.
	 * </p>
	 *
	 * @param  item the item to be converted into binary form.
	 * @return serialized form of the item
	 * @throws ItemSerializerException if the serialization process fails
	 * @throws NoSuchItemSerializerException if no suitable serializer is found
	 *         to convert the item
	 */
	public SerializedItem write(Item item)
		throws ItemSerializerException, NoSuchItemSerializerException;

}