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
class XStreamHierarchicalStreamWriterAdaptor
	implements XStreamHierarchicalStreamWriter {

	public XStreamHierarchicalStreamWriterAdaptor(
		HierarchicalStreamWriter hierarchicalStreamWriter) {

		_hierarchicalStreamWriter = hierarchicalStreamWriter;
	}

	public void startNode(String name) {
		_hierarchicalStreamWriter.startNode(name);
	}

	public void addAttribute(String key, String value) {
		_hierarchicalStreamWriter.addAttribute(key, value);
	}

	public void setValue(String value) {
		_hierarchicalStreamWriter.setValue(value);
	}

	public void endNode() {
		_hierarchicalStreamWriter.endNode();
	}

	public void flush() {
		_hierarchicalStreamWriter.flush();
	}

	public void close() {
		_hierarchicalStreamWriter.close();
	}

	public XStreamHierarchicalStreamWriterAdaptor underlyingWriter() {
		return new XStreamHierarchicalStreamWriterAdaptor(
			_hierarchicalStreamWriter.underlyingWriter());
	}

	private HierarchicalStreamWriter _hierarchicalStreamWriter;

}