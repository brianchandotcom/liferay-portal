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

	public String getDocumentBase64() {
		return documentBase64;
	}

	public String getDSDocumentId() {
		return dsDocumentId;
	}

	public String getName() {
		return name;
	}

	public void setDocumentBase64(String documentBase64) {
		this.documentBase64 = documentBase64;
	}

	public void setDSDocumentId(String dsDocumentId) {
		this.dsDocumentId = dsDocumentId;
	}

	public void setName(String name) {
		this.name = name;
	}

	protected String documentBase64;
	protected String dsDocumentId;
	protected String name;

}