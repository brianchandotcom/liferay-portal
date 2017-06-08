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

package com.liferay.portal.image.internal.model.adapter.builder;

import com.liferay.portal.image.internal.model.adapter.impl.StagedImageImpl;
import com.liferay.portal.image.model.adapter.StagedImage;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.model.adapter.builder.ModelAdapterBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Akos Thurzo
 */
@Component(immediate = true, service = ModelAdapterBuilder.class)
public class StagedImageModelAdapterBuilder
	implements ModelAdapterBuilder<Image, StagedImage> {

	public StagedImage build(Image image) {
		return new StagedImageImpl(image);
	}

}