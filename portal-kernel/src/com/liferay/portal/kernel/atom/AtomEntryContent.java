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

package com.liferay.portal.kernel.atom;

/**
 * @author Igor Spasic
 */
public class AtomEntryContent {

	public AtomEntryContent() {
	}

	public AtomEntryContent(String text) {
		_text = text;
	}

	public AtomEntryContent(String text, Type type) {
		_text = text;
		_type = type;
	}

	public AtomEntryContent(Type type) {
		_type = type;
	}

	public String getMimeType() {
		return _mimeType;
	}

	public String getSrcLink() {
		return _srcLink;
	}

	public String getText() {
		return _text;
	}

	public Type getType() {
		return _type;
	}

	public void setMimeType(String mimeType) {
		_mimeType = mimeType;
	}

	public void setSrcLink(String srcLink) {
		_srcLink = srcLink;
	}

	public void setText(String text) {
		_text = text;
	}

	public void setType(Type type) {
		_type = type;
	}

	public enum Type {

		HTML, MEDIA, TEXT, XHTML, XML

	}

	private String _mimeType;
	private String _srcLink;
	private String _text;
	private Type _type = Type.HTML;

}