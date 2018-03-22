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

package com.liferay.wiki.uad.aggregator;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;

import com.liferay.user.associated.data.aggregator.BaseUADEntityAggregator;
import com.liferay.user.associated.data.aggregator.UADEntityAggregator;
import com.liferay.user.associated.data.entity.UADEntity;
import com.liferay.user.associated.data.util.UADDynamicQueryHelper;

import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiPageLocalService;
import com.liferay.wiki.uad.constants.WikiUADConstants;
import com.liferay.wiki.uad.entity.WikiPageUADEntity;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(immediate = true, property =  {
	"model.class.name=" + WikiUADConstants.CLASS_NAME_WIKI_PAGE}, service = UADEntityAggregator.class)
public class WikiPageUADEntityAggregator extends BaseUADEntityAggregator {
	@Override
	public int count(long userId) {
		return (int)_wikiPageLocalService.dynamicQueryCount(_getDynamicQuery(
				userId));
	}

	@Override
	public List<UADEntity> getUADEntities(long userId, int start, int end) {
		List<WikiPage> wikiPages = _wikiPageLocalService.dynamicQuery(_getDynamicQuery(
					userId), start, end);

		List<UADEntity> uadEntities = new ArrayList<UADEntity>(wikiPages.size());

		for (WikiPage wikiPage : wikiPages) {
			uadEntities.add(new WikiPageUADEntity(userId,
					_getUADEntityId(userId, wikiPage), wikiPage));
		}

		return uadEntities;
	}

	@Override
	public UADEntity getUADEntity(String uadEntityId) throws PortalException {
		WikiPage wikiPage = _wikiPageLocalService.getWikiPage(_getPageId(
					uadEntityId));

		return new WikiPageUADEntity(_getUserId(uadEntityId), uadEntityId,
			wikiPage);
	}

	@Override
	public String getUADEntitySetName() {
		return WikiUADConstants.UAD_ENTITY_SET_NAME;
	}

	private DynamicQuery _getDynamicQuery(long userId) {
		return _uadDynamicQueryHelper.addDynamicQueryCriteria(_wikiPageLocalService.dynamicQuery(),
			WikiUADConstants.USER_ID_FIELD_NAMES_WIKI_PAGE, userId);
	}

	private long _getPageId(String uadEntityId) {
		String[] uadEntityIdParts = uadEntityId.split("#");

		return Long.parseLong(uadEntityIdParts[0]);
	}

	private String _getUADEntityId(long userId, WikiPage wikiPage) {
		return String.valueOf(wikiPage.getPageId()) + "#" +
		String.valueOf(userId);
	}

	private long _getUserId(String uadEntityId) {
		String[] uadEntityIdParts = uadEntityId.split("#");

		return Long.parseLong(uadEntityIdParts[1]);
	}

	@Reference
	private WikiPageLocalService _wikiPageLocalService;
	@Reference
	private UADDynamicQueryHelper _uadDynamicQueryHelper;
}