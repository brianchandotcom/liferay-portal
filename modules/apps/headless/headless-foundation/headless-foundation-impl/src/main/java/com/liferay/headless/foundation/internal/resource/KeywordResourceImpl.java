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

package com.liferay.headless.foundation.internal.resource;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagService;
import com.liferay.headless.foundation.dto.Keyword;
import com.liferay.headless.foundation.dto.KeywordCollection;
import com.liferay.headless.foundation.resource.KeywordResource;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyService;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.vulcan.context.Pagination;
import com.liferay.portlet.asset.util.comparator.AssetTagNameComparator;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

/**
 * @author Javier Gamarra
 * @generated
 */
@Component(
	property = {
		JaxrsWhiteboardConstants.JAX_RS_APPLICATION_SELECT + "=(osgi.jaxrs.name=headless-foundation-application.rest)",
		JaxrsWhiteboardConstants.JAX_RS_RESOURCE + "=true", "api.version=1.0.0"
	},
	scope = ServiceScope.PROTOTYPE, service = KeywordResource.class
)
@Generated("")
public class KeywordResourceImpl implements KeywordResource {

	@Override
	public KeywordCollection<Keyword> getKeywordCollection(
			Pagination pagination, String size)
		throws Exception {

		Company company = _companyService.getCompanyByWebId(
			PropsUtil.get(PropsKeys.COMPANY_DEFAULT_WEB_ID));

		Group group = company.getGroup();

		List<AssetTag> assetTags = _assetTagService.getGroupTags(
			group.getGroupId(), pagination.getStartPosition(),
			pagination.getEndPosition(), new AssetTagNameComparator());

		Stream<AssetTag> stream = assetTags.stream();

		List<Keyword> keywords = stream.map(
			assetTag -> {
				Keyword keyword = new Keyword();

				keyword.setId(assetTag.getTagId());

				return keyword;
			}
		).collect(
			Collectors.toList()
		);

		int count = _assetTagService.getGroupTagsCount(group.getGroupId());

		return new KeywordCollection(keywords, count);
	}

	@Reference
	private AssetTagService _assetTagService;

	@Reference
	private CompanyService _companyService;

}