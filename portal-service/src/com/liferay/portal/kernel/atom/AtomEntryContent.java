/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
		this._type = Type.HTML;
	}

	public AtomEntryContent(Type type) {
		this._type = type;
	}

	public AtomEntryContent(String text) {
		this._text = text;
		this._type = Type.HTML;
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
		this._mimeType = mimeType;
	}

	public void setSrcLink(String srcLink) {
		this._srcLink = srcLink;
	}

	public void setText(String text) {
		this._text = text;
	}

	public void setType(Type type) {
		this._type = type;
	}

	public enum Type {
		TEXT,
		HTML,
		XHTML,
		XML,
		MEDIA
	}

	private String _mimeType;
	private String _srcLink;
	private String _text;
	private Type _type;

}
