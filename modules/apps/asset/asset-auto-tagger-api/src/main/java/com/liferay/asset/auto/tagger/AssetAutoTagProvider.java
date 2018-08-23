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

package com.liferay.asset.auto.tagger;

import java.util.List;

/**
 * Implementations of this interface will be called from
 * {@link AssetAutoTagger#tag(AssetEntry)} to automatically tag assets.
 *
 * Implementations must specify a value for the OSGI property "model.class.name"
 * so that they can be called only for the models they can handle.
 *
 * For example, an AssetAutoTagProvider that can analyze images from the
 * document library and generate tags according to the content of the image
 * would have the following OSGi property:
 *
 * 	model.class.name=com.liferay.document.library.kernel.model.DLFileEntry
 *
 * @author Alejandro Tardín
 */
public interface AssetAutoTagProvider<T> {

	/**
	 * Returns a list of tag names for a given model.
	 *
	 * The model's type must match that from the class specified by the
	 * "model.class.name" OSGi property. Otherwise a runtime error will occur.
	 *
	 * For example an AssetAutoTagProvider with:
	 *
	 *   model.class.name=com.liferay.document.library.kernel.model.DLFileEntry
	 *
	 * would implement this method in the following way:
	 *
	 * <code>public List<String> getTagNames(DLFileEntry dlFileEntry)</code>
	 *
	 * @param model the model from which the tags will be provided
	 * @return a list of tag names for a given model
	 */
	public List<String> getTagNames(T model);

}