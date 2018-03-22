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

import com.liferay.user.associated.data.aggregator.UADEntityAggregator;
import com.liferay.user.associated.data.anonymizer.BaseUADEntityAnonymizer;
import com.liferay.user.associated.data.anonymizer.UADEntityAnonymizer;
import com.liferay.user.associated.data.entity.UADEntity;
import com.liferay.user.associated.data.exception.UADEntityException;
import com.liferay.user.associated.data.util.UADAnonymizerHelper;
import com.liferay.user.associated.data.util.UADDynamicQueryHelper;

import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.service.WikiNodeLocalService;
import com.liferay.wiki.uad.constants.WikiUADConstants;
import com.liferay.wiki.uad.entity.WikiNodeUADEntity;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Arrays;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(immediate = true, property =  {
	"model.class.name=" + WikiUADConstants.CLASS_NAME_WIKI_NODE}, service = UADEntityAnonymizer.class)
public class WikiNodeUADEntityAnonymizer extends BaseUADEntityAnonymizer {
	@Override
	public void autoAnonymize(UADEntity uadEntity) throws PortalException {
		WikiNode wikiNode = _getWikiNode(uadEntity);

		_autoAnonymize(wikiNode, uadEntity.getUserId());
	}

	@Override
	public void autoAnonymizeAll(final long userId) throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery = _getActionableDynamicQuery(userId);

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<WikiNode>() {
				@Override
				public void performAction(WikiNode wikiNode)
					throws PortalException {
					_autoAnonymize(wikiNode, userId);
				}
			});

		actionableDynamicQuery.performActions();
	}

	@Override
	public void delete(UADEntity uadEntity) throws PortalException {
		_wikiNodeLocalService.deleteNode(_getWikiNode(uadEntity));
	}

	@Override
	public void deleteAll(long userId) throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery = _getActionableDynamicQuery(userId);

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<WikiNode>() {
				@Override
				public void performAction(WikiNode wikiNode)
					throws PortalException {
					_wikiNodeLocalService.deleteNode(wikiNode);
				}
			});

		actionableDynamicQuery.performActions();
	}

	@Override
	protected UADEntityAggregator getUADEntityAggregator() {
		return _uadEntityAggregator;
	}

	@Override
	public List<String> getUADEntityNonanonymizableFieldNames() {
		return Arrays.asList("name", "description");
	}

	private void _autoAnonymize(WikiNode wikiNode, long userId)
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

	private ActionableDynamicQuery _getActionableDynamicQuery(long userId) {
		return _uadDynamicQueryHelper.addActionableDynamicQueryCriteria(_wikiNodeLocalService.getActionableDynamicQuery(),
			WikiUADConstants.USER_ID_FIELD_NAMES_WIKI_NODE, userId);
	}

	private WikiNode _getWikiNode(UADEntity uadEntity)
		throws PortalException {
		_validate(uadEntity);

		WikiNodeUADEntity wikiNodeUADEntity = (WikiNodeUADEntity)uadEntity;

		return wikiNodeUADEntity.getWikiNode();
	}

	private void _validate(UADEntity uadEntity) throws PortalException {
		if (!(uadEntity instanceof WikiNodeUADEntity)) {
			throw new UADEntityException();
		}
	}

	@Reference
	private WikiNodeLocalService _wikiNodeLocalService;
	@Reference
	private UADAnonymizerHelper _uadAnonymizerHelper;
	@Reference
	private UADDynamicQueryHelper _uadDynamicQueryHelper;
	@Reference(target = "(model.class.name=" +
	WikiUADConstants.CLASS_NAME_WIKI_NODE + ")")
	private UADEntityAggregator _uadEntityAggregator;
}