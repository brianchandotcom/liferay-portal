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

package com.liferay.wiki.uad.entity;

import com.liferay.user.associated.data.entity.BaseUADEntity;

import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.uad.constants.WikiUADConstants;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class WikiPageUADEntity extends BaseUADEntity {
	public WikiPageUADEntity(long userId, String uadEntityId, WikiPage wikiPage) {
		super(userId, uadEntityId, WikiUADConstants.CLASS_NAME_WIKI_PAGE);

		_wikiPage = wikiPage;
	}

	public WikiPage getWikiPage() {
		return _wikiPage;
	}

	private final WikiPage _wikiPage;
}