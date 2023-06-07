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

package com.liferay.change.tracking.internal.search.spi.model.index.contributor;

import com.liferay.change.tracking.model.CTRemote;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(
	property = "indexer.class.name=com.liferay.change.tracking.model.CTRemote",
	service = ModelDocumentContributor.class
)
public class CTRemoteModelDocumentContributor
	implements ModelDocumentContributor<CTRemote> {

	@Override
	public void contribute(Document document, CTRemote ctRemote) {
		document.addDate(Field.CREATE_DATE, ctRemote.getCreateDate());
		document.addText(Field.DESCRIPTION, ctRemote.getDescription());
		document.addDate(Field.MODIFIED_DATE, ctRemote.getModifiedDate());
		document.addText(Field.NAME, ctRemote.getName());
		document.addKeyword(Field.URL, ctRemote.getUrl());

		User user = _userLocalService.fetchUser(ctRemote.getUserId());

		if (user != null) {
			document.addKeyword(Field.USER_ID, user.getUserId());
			document.addText(Field.USER_NAME, user.getFullName());
		}
	}

	@Reference
	private UserLocalService _userLocalService;

}