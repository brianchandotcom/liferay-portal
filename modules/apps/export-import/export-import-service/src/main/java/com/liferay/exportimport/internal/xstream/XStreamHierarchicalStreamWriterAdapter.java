/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.xstream;

import com.liferay.exportimport.kernel.xstream.XStreamHierarchicalStreamWriter;

import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * @author Daniel Kocsis
 */
public class XStreamHierarchicalStreamWriterAdapter
	implements XStreamHierarchicalStreamWriter {

	public XStreamHierarchicalStreamWriterAdapter(
		HierarchicalStreamWriter hierarchicalStreamWriter) {

		_hierarchicalStreamWriter = hierarchicalStreamWriter;
	}

	@Override
	public void addAttribute(String key, String value) {
		_hierarchicalStreamWriter.addAttribute(key, value);
	}

	@Override
	public void close() {
		_hierarchicalStreamWriter.close();
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
	public void setValue(String value) {
		_hierarchicalStreamWriter.setValue(value);
	}

	@Override
	public void startNode(String name) {
		_hierarchicalStreamWriter.startNode(name);
	}

	@Override
	public XStreamHierarchicalStreamWriterAdapter underlyingWriter() {
		return new XStreamHierarchicalStreamWriterAdapter(
			_hierarchicalStreamWriter.underlyingWriter());
	}

	private final HierarchicalStreamWriter _hierarchicalStreamWriter;

}