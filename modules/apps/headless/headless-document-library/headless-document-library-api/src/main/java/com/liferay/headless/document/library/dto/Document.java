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
@XmlRootElement(name = "Document")
public class Document {

	public Object getAdaptedMedia() {
		return _adaptedMedia;
	}

	public Long[] getCategory() {
		return _category;
	}

	public String getContentUrl() {
		return _contentUrl;
	}

	public Creator getCreator() {
		return _creator;
	}

	public Date getDateCreated() {
		return _dateCreated;
	}

	public Date getDateModified() {
		return _dateModified;
	}

	public String getDescription() {
		return _description;
	}

	public String getEncodingFormat() {
		return _encodingFormat;
	}

	public String getFileExtension() {
		return _fileExtension;
	}

	public Folder getFolder() {
		return _folder;
	}

	public Long getFolderId() {
		return _folderId;
	}

	public Long getId() {
		return _id;
	}

	public String[] getKeywords() {
		return _keywords;
	}

	public String getSelf() {
		return _self;
	}

	public Number getSizeInBytes() {
		return _sizeInBytes;
	}

	public String getTitle() {
		return _title;
	}

	public void setAdaptedMedia(Object adaptedMedia) {
		_adaptedMedia = adaptedMedia;
	}

	public void setCategory(Long[] category) {
		_category = category;
	}

	public void setContentUrl(String contentUrl) {
		_contentUrl = contentUrl;
	}

	public void setCreator(Creator creator) {
		_creator = creator;
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

	public void setEncodingFormat(String encodingFormat) {
		_encodingFormat = encodingFormat;
	}

	public void setFileExtension(String fileExtension) {
		_fileExtension = fileExtension;
	}

	public void setFolder(Folder folder) {
		_folder = folder;
	}

	public void setFolderId(Long folderId) {
		_folderId = folderId;
	}

	public void setId(Long id) {
		_id = id;
	}

	public void setKeywords(String[] keywords) {
		_keywords = keywords;
	}

	public void setSelf(String self) {
		_self = self;
	}

	public void setSizeInBytes(Number sizeInBytes) {
		_sizeInBytes = sizeInBytes;
	}

	public void setTitle(String title) {
		_title = title;
	}

	private Object _adaptedMedia;
	private Long[] _category;
	private String _contentUrl;
	private Creator _creator;
	private Date _dateCreated;
	private Date _dateModified;
	private String _description;
	private String _encodingFormat;
	private String _fileExtension;
	private Folder _folder;
	private Long _folderId;
	private Long _id;
	private String[] _keywords;
	private String _self;
	private Number _sizeInBytes;
	private String _title;

}