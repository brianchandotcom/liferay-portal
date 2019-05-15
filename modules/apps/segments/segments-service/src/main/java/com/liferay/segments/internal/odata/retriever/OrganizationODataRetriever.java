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
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.filter.FilterParser;
import com.liferay.segments.internal.odata.entity.OrganizationEntityModel;
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
	immediate = true,
	property = "model.class.name=com.liferay.portal.kernel.model.Organization",
	service = ODataRetriever.class
)
public class OrganizationODataRetriever
	implements ODataRetriever<Organization> {

	@Override
	public List<Organization> getResults(
			long companyId, String filterString, Locale locale, int start,
			int end)
		throws PortalException {

		Hits hits = _oDataSearchAdapter.search(
			companyId, _filterParser, filterString,
			Organization.class.getName(), _entityModel, locale, start, end);

		return _getOrganizations(hits);
	}

	@Override
	public int getResultsCount(
			long companyId, String filterString, Locale locale)
		throws PortalException {

		return _oDataSearchAdapter.searchCount(
			companyId, _filterParser, filterString,
			Organization.class.getName(), _entityModel, locale);
	}

	private Organization _getOrganization(Document document)
		throws PortalException {

		long organizationId = GetterUtil.getLong(
			document.get(Field.ORGANIZATION_ID));

		return _organizationLocalService.getOrganization(organizationId);
	}

	private List<Organization> _getOrganizations(Hits hits)
		throws PortalException {

		Document[] documents = hits.getDocs();

		List<Organization> organizations = new ArrayList<>(documents.length);

		for (Document document : documents) {
			organizations.add(_getOrganization(document));
		}

		return organizations;
	}

	@Reference(
		target = "(entity.model.name=" + OrganizationEntityModel.NAME + ")"
	)
	private EntityModel _entityModel;

	@Reference(
		target = "(entity.model.name=" + OrganizationEntityModel.NAME + ")"
	)
	private FilterParser _filterParser;

	@Reference
	private ODataSearchAdapter _oDataSearchAdapter;

	@Reference
	private OrganizationLocalService _organizationLocalService;

}