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

package com.liferay.commerce.product.definitions.web.internal.model;

import com.liferay.commerce.frontend.model.ImageField;
import com.liferay.commerce.frontend.model.LabelField;

/**
 * @author Alessio Antonio Rendina
 */
public class ProductMedia {

	public ProductMedia(
		long cpAttachmentFileEntryId, ImageField image, String title,
		String extension, double order, String modifiedDate,
		LabelField status) {

		_cpAttachmentFileEntryId = cpAttachmentFileEntryId;
		_image = image;
		_title = title;
		_extension = extension;
		_order = order;
		_modifiedDate = modifiedDate;
		_status = status;
	}

	public long getCPAttachmentFileEntryId() {
		return _cpAttachmentFileEntryId;
	}

	public String getExtension() {
		return _extension;
	}

	public ImageField getImage() {
		return _image;
	}

	public String getModifiedDate() {
		return _modifiedDate;
	}

	public double getOrder() {
		return _order;
	}

	public LabelField getStatus() {
		return _status;
	}

	public String getTitle() {
		return _title;
	}

	private final long _cpAttachmentFileEntryId;
	private final String _extension;
	private final ImageField _image;
	private final String _modifiedDate;
	private final double _order;
	private final LabelField _status;
	private final String _title;

}