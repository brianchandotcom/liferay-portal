/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.xstream;

import com.liferay.exportimport.kernel.xstream.XStreamHierarchicalStreamReader;

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