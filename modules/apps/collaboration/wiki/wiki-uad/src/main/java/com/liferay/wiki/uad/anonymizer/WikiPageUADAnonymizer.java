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

package com.liferay.wiki.uad.anonymizer;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;

import com.liferay.user.associated.data.anonymizer.DynamicQueryUADAnonymizer;
import com.liferay.user.associated.data.anonymizer.UADAnonymizer;
import com.liferay.user.associated.data.util.UADAnonymizerHelper;

import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiPageLocalService;
import com.liferay.wiki.uad.constants.WikiUADConstants;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Arrays;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(immediate = true, property =  {
	"model.class.name=" + WikiUADConstants.CLASS_NAME_WIKI_PAGE}, service = UADAnonymizer.class)
public class WikiPageUADAnonymizer extends DynamicQueryUADAnonymizer<WikiPage> {
	@Override
	public void autoAnonymize(WikiPage wikiPage, long userId)
		throws PortalException {
		User anonymousUser = _uadAnonymizerHelper.getAnonymousUser();

		if (wikiPage.getUserId() == userId) {
			wikiPage.setUserId(anonymousUser.getUserId());
			wikiPage.setUserName(anonymousUser.getFullName());
		}

		if (wikiPage.getStatusByUserId() == userId) {
			wikiPage.setStatusByUserId(anonymousUser.getUserId());
			wikiPage.setStatusByUserName(anonymousUser.getFullName());
		}

		_wikiPageLocalService.updateWikiPage(wikiPage);
	}

	@Override
	public void delete(WikiPage wikiPage) throws PortalException {
		_wikiPageLocalService.deletePage(wikiPage);
	}

	@Override
	public List<String> getNonanonymizableFieldNames() {
		return Arrays.asList("title", "content", "summary");
	}

	@Override
	protected ActionableDynamicQuery doGetActionableDynamicQuery() {
		return _wikiPageLocalService.getActionableDynamicQuery();
	}

	@Override
	protected String[] doGetUserIdFieldNames() {
		return WikiUADConstants.USER_ID_FIELD_NAMES_WIKI_PAGE;
	}

	@Reference
	private WikiPageLocalService _wikiPageLocalService;
	@Reference
	private UADAnonymizerHelper _uadAnonymizerHelper;
}