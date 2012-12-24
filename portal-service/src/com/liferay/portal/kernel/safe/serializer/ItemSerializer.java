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
 * Converts instance of {@link Item} into its binary form - {@link
 * SerializedItem} and vice versa.
 *
 * @author Tomas Polesovsky
 */
public interface ItemSerializer<T extends Item> {

	/**
	 * Returns unique ID of the serializer. UID is used to identify a serializer
	 * for deserialization of {@link SerializedItem} using {@link
	 * #read(SerializedItem)} method.
	 *
	 * @return ID that identifies this class, usually a constant.
	 */
	public long getSerializerUID();

	/**
	 * Returns the class that is able to covert descendant of {@link Item} into
	 * {@link SerializedItem}.
	 *
	 * @return class of the type <code>T</code> that is able to serialize,
	 *         usually a constant.
	 */
	public Class getSupportedClass();

	/**
	 * Convert SerializedItem into new instance of {@link Item} using {@link
	 * SerializedItem#getItemStream()}.
	 *
	 * <p>
	 * Implementation MUST copy companyId and groupId from the item into the
	 * result. Serializer may use {@link SerializedItem#getHeader()} to read
	 * item's metadata that was previously stored by the {@link
	 * #read(SerializedItem)} method.
	 * </p>
	 *
	 * @param  serializedItem the serialized item to be converted
	 * @return new instance of {@link Item} descendant
	 * @throws ItemSerializerException if deserialization fails
	 */
	public T read(SerializedItem serializedItem) throws ItemSerializerException;

	/**
	 * Serializes internal representation of the item into InputStream and
	 * stores in result using {@link
	 * SerializedItem#setItemStream(java.io.InputStream)}.
	 *
	 * <p>
	 * Implementation MUST copy companyId and groupId from the item into the
	 * result. Serializer may use {@link SerializedItem#setHeader(byte[])} to
	 * store any metadata. The header will be then available in the {@link
	 * #read(SerializedItem)} method.
	 * </p>
	 *
	 * @param  item the item to be serialized
	 * @return new instance with
	 * @throws ItemSerializerException if serialization fails
	 */
	public SerializedItem write(T item) throws ItemSerializerException;

}