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

package com.liferay.headless.content.space.dto;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public abstract class BaseContentSpaceImpl implements ContentSpace {

	@Override
	public List<String> getAvailableLanguages() {
		return _availableLanguages;
	}

	@Override
	public String getDescription() {
		return _description;
	}

	@Override
	public long getId() {
		return _id;
	}

	public void setAvailableLanguages(List<String> availableLanguages) {
		_availableLanguages = availableLanguages;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setId(long id) {
		_id = id;
	}

	private List<String> _availableLanguages;
	private String _description;
	private long _id;

	@Override
	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	private Long creatorId;

}