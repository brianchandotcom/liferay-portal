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

package com.liferay.headless.delivery.internal.resource.v1_0;

import com.liferay.headless.delivery.dto.v1_0.ContentPage;
import com.liferay.headless.delivery.internal.dto.v1_0.converter.ContentPageDTOConverter;
import com.liferay.headless.delivery.resource.v1_0.ContentPageResource;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier de Arcos
 * @author Jürgen Kappler
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/content-page.properties",
	scope = ServiceScope.PROTOTYPE, service = ContentPageResource.class
)
public class ContentPageResourceImpl extends BaseContentPageResourceImpl {

	@Override
	public Page<ContentPage> getSiteContentPagesPage(
		@NotNull Long siteId, String search, Aggregation aggregation,
		Filter filter, Pagination pagination, Sort[] sorts) {

		List<Layout> layouts = _layoutService.getLayouts(
			siteId, LayoutConstants.TYPE_CONTENT);

		return Page.of(TransformUtil.transform(layouts, this::_toContentPage));
	}

	private ContentPage _toContentPage(Layout layout) throws Exception {
		return _contentPageDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				HashMapBuilder.put(
					"get",
					addAction(
						"VIEW", "getSiteContentPagesPage",
						"com.liferay.portal.kernel.model.Group",
						layout.getGroupId())
				).build(),
				_dtoConverterRegistry, contextHttpServletRequest,
				layout.getPlid(), contextAcceptLanguage.getPreferredLocale(),
				contextUriInfo, contextUser),
			layout);
	}

	@Reference
	private ContentPageDTOConverter _contentPageDTOConverter;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private LayoutService _layoutService;

}