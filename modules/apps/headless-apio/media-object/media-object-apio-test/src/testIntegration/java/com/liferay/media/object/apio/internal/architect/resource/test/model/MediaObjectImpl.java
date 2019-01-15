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

package com.liferay.media.object.apio.internal.architect.resource.test.model;

import com.liferay.apio.architect.file.BinaryFile;
import com.liferay.media.object.apio.architect.model.MediaObject;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Rubén Pulido
 */
public class MediaObjectImpl implements MediaObject {

	public MediaObjectImpl(
		BinaryFile binaryFile, String title, String description,
		List<String> keywords, List<Long> categories) {

		_binaryFile = binaryFile;
		_title = title;
		_description = description;
		_keywords = Optional.ofNullable(
			keywords
		).map(
			List::stream
		).orElseGet(
			Stream::empty
		).collect(
			Collectors.toList()
		);
		_categories = Optional.ofNullable(
			categories
		).map(
			List::stream
		).orElseGet(
			Stream::empty
		).collect(
			Collectors.toList()
		);
	}

	@Override
	public BinaryFile getBinaryFile() {
		return _binaryFile;
	}

	@Override
	public List<Long> getCategories() {
		return _categories;
	}

	@Override
	public String getDescription() {
		return _description;
	}

	@Override
	public List<String> getKeywords() {
		return _keywords;
	}

	@Override
	public String getTitle() {
		return _title;
	}

	private final BinaryFile _binaryFile;
	private final List<Long> _categories;
	private final String _description;
	private final List<String> _keywords;
	private final String _title;

}