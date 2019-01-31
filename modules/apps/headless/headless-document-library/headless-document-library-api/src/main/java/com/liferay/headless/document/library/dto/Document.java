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

	public String getContentUrl() {
		return _contentUrl;
	}

	public String getCreator() {
		return _creator;
	}

	public String getDateCreated() {
		return _dateCreated;
	}

	public String getDateModified() {
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

	public Integer getId() {
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

	public void setContentUrl(String contentUrl) {
		_contentUrl = contentUrl;
	}

	public void setCreator(String creator) {
		_creator = creator;
	}

	public void setDateCreated(String dateCreated) {
		_dateCreated = dateCreated;
	}

	public void setDateModified(String dateModified) {
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

	public void setId(Integer id) {
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
	private String _contentUrl;
	private String _creator;
	private String _dateCreated;
	private String _dateModified;
	private String _description;
	private String _encodingFormat;
	private String _fileExtension;
	private Folder _folder;
	private Integer _id;
	private String[] _keywords;
	private String _self;
	private Number _sizeInBytes;
	private String _title;

}