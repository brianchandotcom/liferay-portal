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

package com.liferay.portal.lar.xstream;

import com.liferay.portal.kernel.lar.xstream.XStreamHierarchicalStreamReader;

import com.thoughtworks.xstream.io.HierarchicalStreamReader;

import java.util.Iterator;

/**
 * @author Daniel Kocsis
 */
public class XStreamHierarchicalStreamReaderAdapter
	implements XStreamHierarchicalStreamReader {

	public XStreamHierarchicalStreamReaderAdapter(
		HierarchicalStreamReader hierarchicalStreamReader) {

		_hierarchicalStreamReader = hierarchicalStreamReader;
	}

	@Override
	public void close() {
		_hierarchicalStreamReader.close();
	}

	@Override
	public String getAttribute(int index) {
		return _hierarchicalStreamReader.getAttribute(index);
	}

	@Override
	public String getAttribute(String name) {
		return _hierarchicalStreamReader.getAttribute(name);
	}

	@Override
	public int getAttributeCount() {
		return _hierarchicalStreamReader.getAttributeCount();
	}

	@Override
	public String getAttributeName(int index) {
		return _hierarchicalStreamReader.getAttributeName(index);
	}

	@Override
	public Iterator<String> getAttributeNames() {
		return _hierarchicalStreamReader.getAttributeNames();
	}

	@Override
	public String getNodeName() {
		return _hierarchicalStreamReader.getNodeName();
	}

	@Override
	public String getValue() {
		return _hierarchicalStreamReader.getValue();
	}

	@Override
	public boolean hasMoreChildren() {
		return _hierarchicalStreamReader.hasMoreChildren();
	}

	@Override
	public void moveDown() {
		_hierarchicalStreamReader.moveDown();
	}

	@Override
	public void moveUp() {
		_hierarchicalStreamReader.moveUp();
	}

	@Override
	public XStreamHierarchicalStreamReader underlyingReader() {
		return new XStreamHierarchicalStreamReaderAdapter(
			_hierarchicalStreamReader.underlyingReader());
	}

	private final HierarchicalStreamReader _hierarchicalStreamReader;

}