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

import java.io.InputStream;

/**
 * Bean holding serialized binary content of an {@link
 * com.liferay.portal.kernel.safe.model.Item} together with other metadata
 * information.
 *
 * <p>
 * Implementations of {@link ItemSerializer} can use {@link #setHeader(byte[])}
 * and {@link #getHeader()} to store and load optional metadata.
 * </p>
 *
 * @author Tomas Polesovsky
 */
public class SerializedItem {

	public long getCompanyId() {
		return _companyId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public byte[] getHeader() {
		return _header;
	}

	public InputStream getItemStream() {
		return _itemStream;
	}

	public long getSerializerUID() {
		return _serializerUID;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public void setHeader(byte[] header) {
		_header = header;
	}

	public void setItemStream(InputStream itemStream) {
		_itemStream = itemStream;
	}

	public void setSerializerUID(long serializerUID) {
		_serializerUID = serializerUID;
	}

	private long _companyId;
	private long _groupId;
	private byte[] _header = new byte[0];
	private InputStream _itemStream;
	private long _serializerUID;

}