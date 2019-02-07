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

package com.liferay.headless.document.library.dto;

import java.util.Date;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@XmlRootElement(name = "Folder")
public class Folder {

	public Date getDateCreated() {
		return _dateCreated;
	}

	public Date getDateModified() {
		return _dateModified;
	}

	public String getDescription() {
		return _description;
	}

	public Document[] getDocuments() {
		return _documents;
	}

	public Long[] getDocumentsIds() {
		return _documentsIds;
	}

	public Folder getDocumentsRepository() {
		return _documentsRepository;
	}

	public Long getDocumentsRepositoryId() {
		return _documentsRepositoryId;
	}

	public Long getId() {
		return _id;
	}

	public String getName() {
		return _name;
	}

	public String getSelf() {
		return _self;
	}

	public Folder[] getSubFolders() {
		return _subFolders;
	}

	public void setDateCreated(Date dateCreated) {
		_dateCreated = dateCreated;
	}

	public void setDateModified(Date dateModified) {
		_dateModified = dateModified;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setDocuments(Document[] documents) {
		_documents = documents;
	}

	public void setDocumentsIds(Long[] documentsIds) {
		_documentsIds = documentsIds;
	}

	public void setDocumentsRepository(Folder documentsRepository) {
		_documentsRepository = documentsRepository;
	}

	public void setDocumentsRepositoryId(Long documentsRepositoryId) {
		_documentsRepositoryId = documentsRepositoryId;
	}

	public void setId(Long id) {
		_id = id;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setSelf(String self) {
		_self = self;
	}

	public void setSubFolders(Folder[] subFolders) {
		_subFolders = subFolders;
	}

	private Date _dateCreated;
	private Date _dateModified;
	private String _description;
	private Document[] _documents;
	private Long[] _documentsIds;
	private Folder _documentsRepository;
	private Long _documentsRepositoryId;
	private Long _id;
	private String _name;
	private String _self;
	private Folder[] _subFolders;

}