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
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

/**
 * The utility for the item serializer registry service. This utility wraps
 * {@link ItemSerializerRegistry} and is the primary access point for
 * (de)registering serializers and for (de)serialization of {@link Item}s into
 * {@link InputStream}s.
 *
 * @author Tomas Polesovsky
 */
public class ItemSerializerRegistryUtil {

	/**
	 * Delegates call to the wrapped service to remove the serializer from the
	 * registry.
	 *
	 * @param  serializer the item serializer to be removed
	 * @return <code>true</code> when serializer was found and removed from the
	 *         registry, <code>false</code> otherwise
	 */
	public static boolean deregisterSerializer(ItemSerializer serializer) {
		return getInstance().deregisterSerializer(serializer);
	}

	/**
	 * Returns wrapped instance of {@link ItemSerializerRegistry}
	 *
	 * @return the implementation of {@link ItemSerializerRegistry}
	 */
	public static ItemSerializerRegistry getInstance() {
		PortalRuntimePermission.checkGetBeanProperty(
			ItemSerializerRegistryUtil.class);

		return _itemSerializerRegistry;
	}

	/**
	 * Delegates call to the wrapped service to convert a binary content in the
	 * serializedItem into {@link Item} using one of registred serializers.
	 *
	 * @param  serializedItem serialized form of item to be read
	 * @return deserialized instance of the serializedItem
	 * @throws ItemSerializerException if the deserialization process fails
	 * @throws NoSuchItemSerializerException if no suitable serializer is found
	 *         to parse the serializedItem
	 */
	public static Item read(SerializedItem serializedItem)
		throws ItemSerializerException, NoSuchItemSerializerException {

		return getInstance().read(serializedItem);
	}

	/**
	 * Delegates call to the wrapped service to add the serializer into the
	 * registry.
	 *
	 * @param  serializer the serializer to be registered
	 * @throws DuplicateItemSerializerException if there is already registered a
	 *         serializer with the same {@link
	 *         ItemSerializer#getSerializerUID()} or {@link
	 *         ItemSerializer#getSupportedClass()}}
	 */
	public static void registerSerializer(ItemSerializer serializer)
		throws DuplicateItemSerializerException {

		getInstance().registerSerializer(serializer);
	}

	/**
	 * Delegates call to the wrapped service to convert the item into returned
	 * {@link SerializedItem} using one of registered serializers.
	 *
	 * @param  item the item to be converted into binary form.
	 * @return serialized form of the item
	 * @throws ItemSerializerException if the serialization process fails
	 * @throws NoSuchItemSerializerException if no suitable serializer is found
	 *         to convert the item
	 */
	public static SerializedItem write(Item item)
		throws ItemSerializerException, NoSuchItemSerializerException {

		return getInstance().write(item);
	}

	/**
	 * Setup the wrapped instance of {@link ItemSerializerRegistry} service
	 *
	 * @param itemSerializerRegistry the instance to be used as the wrapped
	 *        service
	 */
	public void setItemSerializerRegistry(
		ItemSerializerRegistry itemSerializerRegistry) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_itemSerializerRegistry = itemSerializerRegistry;
	}

	/**
	 * The wrapped service
	 */
	private static ItemSerializerRegistry _itemSerializerRegistry;

}