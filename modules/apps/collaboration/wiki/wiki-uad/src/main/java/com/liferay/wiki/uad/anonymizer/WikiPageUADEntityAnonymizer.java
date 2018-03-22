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

import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiPageLocalService;
import com.liferay.wiki.uad.constants.WikiUADConstants;
import com.liferay.wiki.uad.entity.WikiPageUADEntity;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Arrays;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(immediate = true, property =  {
	"model.class.name=" + WikiUADConstants.CLASS_NAME_WIKI_PAGE}, service = UADEntityAnonymizer.class)
public class WikiPageUADEntityAnonymizer extends BaseUADEntityAnonymizer {
	@Override
	public void autoAnonymize(UADEntity uadEntity) throws PortalException {
		WikiPage wikiPage = _getWikiPage(uadEntity);

		_autoAnonymize(wikiPage, uadEntity.getUserId());
	}

	@Override
	public void autoAnonymizeAll(final long userId) throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery = _getActionableDynamicQuery(userId);

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<WikiPage>() {
				@Override
				public void performAction(WikiPage wikiPage)
					throws PortalException {
					_autoAnonymize(wikiPage, userId);
				}
			});

		actionableDynamicQuery.performActions();
	}

	@Override
	public void delete(UADEntity uadEntity) throws PortalException {
		_wikiPageLocalService.deletePage(_getWikiPage(uadEntity));
	}

	@Override
	public void deleteAll(long userId) throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery = _getActionableDynamicQuery(userId);

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<WikiPage>() {
				@Override
				public void performAction(WikiPage wikiPage)
					throws PortalException {
					_wikiPageLocalService.deletePage(wikiPage);
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
		return Arrays.asList("title", "content", "summary");
	}

	private void _autoAnonymize(WikiPage wikiPage, long userId)
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

	private ActionableDynamicQuery _getActionableDynamicQuery(long userId) {
		return _uadDynamicQueryHelper.addActionableDynamicQueryCriteria(_wikiPageLocalService.getActionableDynamicQuery(),
			WikiUADConstants.USER_ID_FIELD_NAMES_WIKI_PAGE, userId);
	}

	private WikiPage _getWikiPage(UADEntity uadEntity)
		throws PortalException {
		_validate(uadEntity);

		WikiPageUADEntity wikiPageUADEntity = (WikiPageUADEntity)uadEntity;

		return wikiPageUADEntity.getWikiPage();
	}

	private void _validate(UADEntity uadEntity) throws PortalException {
		if (!(uadEntity instanceof WikiPageUADEntity)) {
			throw new UADEntityException();
		}
	}

	@Reference
	private WikiPageLocalService _wikiPageLocalService;
	@Reference
	private UADAnonymizerHelper _uadAnonymizerHelper;
	@Reference
	private UADDynamicQueryHelper _uadDynamicQueryHelper;
	@Reference(target = "(model.class.name=" +
	WikiUADConstants.CLASS_NAME_WIKI_PAGE + ")")
	private UADEntityAggregator _uadEntityAggregator;
}