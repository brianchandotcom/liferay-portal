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

package com.liferay.portal.vulcan.internal.jaxrs.context.provider;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.sort.InvalidSortException;
import com.liferay.portal.odata.sort.SortField;
import com.liferay.portal.odata.sort.SortParser;
import com.liferay.portal.odata.sort.SortParserProvider;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.internal.accept.language.AcceptLanguageImpl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.ServerErrorException;
import javax.ws.rs.ext.Provider;

import org.apache.cxf.jaxrs.ext.ContextProvider;
import org.apache.cxf.message.Message;

/**
 * @author Brian Wing Shun Chan
 */
@Provider
public class SortContextProvider implements ContextProvider<Sort[]> {

	public SortContextProvider(
		Language language, Portal portal,
		SortParserProvider sortParserProvider) {

		_language = language;
		_portal = portal;
		_sortParserProvider = sortParserProvider;
	}

	public Sort[] createContext(
		AcceptLanguage acceptLanguage, EntityModel entityModel,
		String sortString) {

		if (_log.isDebugEnabled()) {
			_log.debug("Sort parameter value: " + sortString);
		}

		if (Validator.isNull(sortString)) {
			return null;
		}

		if (_log.isDebugEnabled() && (entityModel != null)) {
			_log.debug("OData entity model name: " + entityModel.getName());
		}

		SortParser sortParser = _sortParserProvider.provide(entityModel);

		if (sortParser == null) {
			return null;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("OData sort parser: " + sortParser);
		}

		com.liferay.portal.odata.sort.Sort oDataSort =
			new com.liferay.portal.odata.sort.Sort(
				sortParser.parse(sortString));

		if (_log.isDebugEnabled()) {
			_log.debug("OData sort: " + oDataSort);
		}

		List<SortField> sortFields = oDataSort.getSortFields();

		Sort[] sorts = new Sort[sortFields.size()];

		for (int i = 0; i < sortFields.size(); i++) {
			SortField sortField = sortFields.get(i);

			sorts[i] = new Sort(
				sortField.getSortableFieldName(
					acceptLanguage.getPreferredLocale()),
				!sortField.isAscending());
		}

		return sorts;
	}

	@Override
	public Sort[] createContext(Message message) {
		try {
			HttpServletRequest httpServletRequest =
				ContextProviderUtil.getHttpServletRequest(message);

			return createContext(
				new AcceptLanguageImpl(httpServletRequest, _language, _portal),
				ContextProviderUtil.getEntityModel(message),
				ParamUtil.getString(httpServletRequest, "sort"));
		}
		catch (InvalidSortException ise) {
			throw ise;
		}
		catch (Exception e) {
			throw new ServerErrorException(500, e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SortContextProvider.class);

	private final Language _language;
	private final Portal _portal;
	private final SortParserProvider _sortParserProvider;

}