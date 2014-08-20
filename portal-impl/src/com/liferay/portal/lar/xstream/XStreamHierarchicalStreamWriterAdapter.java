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

import com.liferay.portal.kernel.lar.xstream.XStreamHierarchicalStreamWriter;

import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * @author Daniel Kocsis
 */
class XStreamHierarchicalStreamWriterAdapter
	implements XStreamHierarchicalStreamWriter {

	public XStreamHierarchicalStreamWriterAdapter(
		HierarchicalStreamWriter hierarchicalStreamWriter) {

		_hierarchicalStreamWriter = hierarchicalStreamWriter;
	}

	@Override
	public void startNode(String name) {
		_hierarchicalStreamWriter.startNode(name);
	}

	@Override
	public void addAttribute(String key, String value) {
		_hierarchicalStreamWriter.addAttribute(key, value);
	}

	@Override
	public void setValue(String value) {
		_hierarchicalStreamWriter.setValue(value);
	}

	@Override
	public void endNode() {
		_hierarchicalStreamWriter.endNode();
	}

	@Override
	public void flush() {
		_hierarchicalStreamWriter.flush();
	}

	@Override
	public void close() {
		_hierarchicalStreamWriter.close();
	}

	@Override
	public XStreamHierarchicalStreamWriterAdapter underlyingWriter() {
		return new XStreamHierarchicalStreamWriterAdapter(
			_hierarchicalStreamWriter.underlyingWriter());
	}

	private final HierarchicalStreamWriter _hierarchicalStreamWriter;

}