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

package com.liferay.digital.signature.model;

/**
 * @author Brian Wing Shun Chan
 */
public class DSDocument {

	public String getData() {
		return data;
	}

	public String getDSDocumentId() {
		return dsDocumentId;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public String getName() {
		return name;
	}

	public String getURI() {
		return uri;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setDSDocumentId(String dsDocumentId) {
		this.dsDocumentId = dsDocumentId;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setURI(String uri) {
		this.uri = uri;
	}

	protected String data;
	protected String dsDocumentId;
	protected String fileExtension;
	protected String name;
	protected String uri;

}