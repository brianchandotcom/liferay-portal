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

package com.liferay.segments.internal.odata.retriever;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.filter.FilterParser;
import com.liferay.portal.odata.filter.FilterParserProvider;
import com.liferay.segments.internal.odata.entity.UserEntityModel;
import com.liferay.segments.odata.retriever.ODataRetriever;
import com.liferay.segments.odata.search.ODataSearchAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(
	property = "model.class.name=com.liferay.portal.kernel.model.User",
	service = ODataRetriever.class
)
public class UserODataRetriever implements ODataRetriever<User> {

	@Override
	public List<User> getResults(
			long companyId, String filterString, Locale locale, int start,
			int end)
		throws PortalException {

		FilterParser filterParser = _filterParserProvider.provide(_entityModel);

		Hits hits = _oDataSearchAdapter.search(
			companyId, filterParser, filterString, User.class.getName(),
			_entityModel, locale, start, end);

		return _getUsers(hits);
	}

	@Override
	public int getResultsCount(
			long companyId, String filterString, Locale locale)
		throws PortalException {

		FilterParser filterParser = _filterParserProvider.provide(_entityModel);

		return _oDataSearchAdapter.searchCount(
			companyId, filterParser, filterString, User.class.getName(),
			_entityModel, locale);
	}

	private User _getUser(Document document) throws PortalException {
		long userId = GetterUtil.getLong(document.get(Field.USER_ID));

		return _userLocalService.getUser(userId);
	}

	private List<User> _getUsers(Hits hits) throws PortalException {
		Document[] documents = hits.getDocs();

		List<User> users = new ArrayList<>(documents.length);

		for (Document document : documents) {
			users.add(_getUser(document));
		}

		return users;
	}

	@Reference(target = "(entity.model.name=" + UserEntityModel.NAME + ")")
	private EntityModel _entityModel;

	@Reference
	private FilterParserProvider _filterParserProvider;

	@Reference
	private ODataSearchAdapter _oDataSearchAdapter;

	@Reference
	private UserLocalService _userLocalService;

}