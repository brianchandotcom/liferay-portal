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

package com.liferay.document.library.asset.auto.tagger.opennlp.internal;

import com.liferay.asset.auto.tagger.AssetAutoTagProvider;
import com.liferay.portal.kernel.repository.model.FileEntry;

import java.util.Collection;
import java.util.Collections;

import org.osgi.service.component.annotations.Component;

/**
 * @author Cristina González
 */
@Component(
	property = "model.class.name=com.liferay.document.library.kernel.model.DLFileEntry",
	service = AssetAutoTagProvider.class
)
public class OpenNLPDocumentAssetAutoTagProvider
	implements AssetAutoTagProvider<FileEntry> {

	@Override
	public Collection<String> getTagNames(FileEntry fileEntry) {
		return Collections.singleton("tag");
	}

}