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

import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.service.WikiNodeLocalService;
import com.liferay.wiki.uad.constants.WikiUADConstants;
import com.liferay.wiki.uad.entity.WikiNodeUADEntity;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(immediate = true, property =  {
	"model.class.name=" + WikiUADConstants.CLASS_NAME_WIKI_NODE}, service = UADEntityAggregator.class)
public class WikiNodeUADEntityAggregator extends BaseUADEntityAggregator {
	@Override
	public int count(long userId) {
		return (int)_wikiNodeLocalService.dynamicQueryCount(_getDynamicQuery(
				userId));
	}

	@Override
	public List<UADEntity> getUADEntities(long userId, int start, int end) {
		List<WikiNode> wikiNodes = _wikiNodeLocalService.dynamicQuery(_getDynamicQuery(
					userId), start, end);

		List<UADEntity> uadEntities = new ArrayList<UADEntity>(wikiNodes.size());

		for (WikiNode wikiNode : wikiNodes) {
			uadEntities.add(new WikiNodeUADEntity(userId,
					_getUADEntityId(userId, wikiNode), wikiNode));
		}

		return uadEntities;
	}

	@Override
	public UADEntity getUADEntity(String uadEntityId) throws PortalException {
		WikiNode wikiNode = _wikiNodeLocalService.getWikiNode(_getNodeId(
					uadEntityId));

		return new WikiNodeUADEntity(_getUserId(uadEntityId), uadEntityId,
			wikiNode);
	}

	@Override
	public String getUADEntitySetName() {
		return WikiUADConstants.UAD_ENTITY_SET_NAME;
	}

	private DynamicQuery _getDynamicQuery(long userId) {
		return _uadDynamicQueryHelper.addDynamicQueryCriteria(_wikiNodeLocalService.dynamicQuery(),
			WikiUADConstants.USER_ID_FIELD_NAMES_WIKI_NODE, userId);
	}

	private long _getNodeId(String uadEntityId) {
		String[] uadEntityIdParts = uadEntityId.split("#");

		return Long.parseLong(uadEntityIdParts[0]);
	}

	private String _getUADEntityId(long userId, WikiNode wikiNode) {
		return String.valueOf(wikiNode.getNodeId()) + "#" +
		String.valueOf(userId);
	}

	private long _getUserId(String uadEntityId) {
		String[] uadEntityIdParts = uadEntityId.split("#");

		return Long.parseLong(uadEntityIdParts[1]);
	}

	@Reference
	private WikiNodeLocalService _wikiNodeLocalService;
	@Reference
	private UADDynamicQueryHelper _uadDynamicQueryHelper;
}