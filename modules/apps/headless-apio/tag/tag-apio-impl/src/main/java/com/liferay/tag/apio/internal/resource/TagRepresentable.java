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

package com.liferay.tag.apio.internal.resource;

import com.liferay.apio.architect.representor.Representable;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.model.AssetTagModel;
import com.liferay.tag.apio.identifier.TagIdentifier;

import org.osgi.service.component.annotations.Component;

/**
 * Provides the information necessary to expose {@link AssetTag}
 * resources through a web API.
 *
 * @author Alejandro Hernández
 */
@Component(immediate = true)
public class TagRepresentable
	implements Representable<AssetTag, Long, TagIdentifier> {

	@Override
	public String getName() {
		return "aggregate-ratings";
	}

	@Override
	public Representor<AssetTag, Long> representor(
		Representor.Builder<AssetTag, Long> builder) {

		return builder.types(
			"Tag"
		).identifier(
			AssetTagModel::getTagId
		).addString(
			"name", AssetTagModel::getName
		).addNumber(
			"usages",
			assetTag -> {
				//TODO

				return null;
			}
		).build();
	}

}