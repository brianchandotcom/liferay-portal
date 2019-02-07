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

package com.liferay.headless.collaboration.dto;

import java.util.Date;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@XmlRootElement(name = "ImageObjectRepository")
public class ImageObjectRepository {

	public Date getDateCreated() {
		return _dateCreated;
	}

	public Date getDateModified() {
		return _dateModified;
	}

	public Long getId() {
		return _id;
	}

	public ImageObject[] getImages() {
		return _images;
	}

	public Long[] getImagesIds() {
		return _imagesIds;
	}

	public String getName() {
		return _name;
	}

	public String getSelf() {
		return _self;
	}

	public void setDateCreated(Date dateCreated) {
		_dateCreated = dateCreated;
	}

	public void setDateModified(Date dateModified) {
		_dateModified = dateModified;
	}

	public void setId(Long id) {
		_id = id;
	}

	public void setImages(ImageObject[] images) {
		_images = images;
	}

	public void setImagesIds(Long[] imagesIds) {
		_imagesIds = imagesIds;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setSelf(String self) {
		_self = self;
	}

	private Date _dateCreated;
	private Date _dateModified;
	private Long _id;
	private ImageObject[] _images;
	private Long[] _imagesIds;
	private String _name;
	private String _self;

}