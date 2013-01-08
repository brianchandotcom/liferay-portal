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

package com.liferay.portal.safe;

import com.liferay.portal.kernel.safe.model.Item;
import com.liferay.portal.kernel.safe.serializer.DuplicateItemSerializerException;
import com.liferay.portal.kernel.safe.serializer.ItemSerializer;
import com.liferay.portal.kernel.safe.serializer.ItemSerializerException;
import com.liferay.portal.kernel.safe.serializer.ItemSerializerRegistry;
import com.liferay.portal.kernel.safe.serializer.NoSuchItemSerializerException;
import com.liferay.portal.kernel.safe.serializer.SerializedItem;

/**
 * Dummy implementation throwing UnsupportedOperationException
 *
 * @author Tomas Polesovsky
 */
public class ItemSerializerRegistryImpl implements ItemSerializerRegistry {

	public boolean deregisterSerializer(ItemSerializer serializer) {
		throw new UnsupportedOperationException("");
	}

	public Item read(SerializedItem serializedItem)
		throws ItemSerializerException, NoSuchItemSerializerException {

		throw new UnsupportedOperationException("");
	}

	public void registerSerializer(ItemSerializer serializer)
		throws DuplicateItemSerializerException {

		throw new UnsupportedOperationException("");
	}

	public SerializedItem write(Item item)
		throws ItemSerializerException, NoSuchItemSerializerException {

		throw new UnsupportedOperationException("");
	}

}