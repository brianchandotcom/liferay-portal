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

import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.service.WikiNodeLocalService;
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
	"model.class.name=" + WikiUADConstants.CLASS_NAME_WIKI_NODE}, service = UADAnonymizer.class)
public class WikiNodeUADAnonymizer extends DynamicQueryUADAnonymizer<WikiNode> {
	@Override
	public void autoAnonymize(WikiNode wikiNode, long userId)
		throws PortalException {
		User anonymousUser = _uadAnonymizerHelper.getAnonymousUser();

		if (wikiNode.getUserId() == userId) {
			wikiNode.setUserId(anonymousUser.getUserId());
			wikiNode.setUserName(anonymousUser.getFullName());
		}

		if (wikiNode.getStatusByUserId() == userId) {
			wikiNode.setStatusByUserId(anonymousUser.getUserId());
			wikiNode.setStatusByUserName(anonymousUser.getFullName());
		}

		_wikiNodeLocalService.updateWikiNode(wikiNode);
	}

	@Override
	public void delete(WikiNode wikiNode) throws PortalException {
		_wikiNodeLocalService.deleteNode(wikiNode);
	}

	@Override
	public List<String> getNonanonymizableFieldNames() {
		return Arrays.asList("name", "description");
	}

	@Override
	protected ActionableDynamicQuery doGetActionableDynamicQuery() {
		return _wikiNodeLocalService.getActionableDynamicQuery();
	}

	@Override
	protected String[] doGetUserIdFieldNames() {
		return WikiUADConstants.USER_ID_FIELD_NAMES_WIKI_NODE;
	}

	@Reference
	private WikiNodeLocalService _wikiNodeLocalService;
	@Reference
	private UADAnonymizerHelper _uadAnonymizerHelper;
}