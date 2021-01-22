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
import com.liferay.headless.delivery.internal.odata.entity.v1_0.ContentPageEntityModel;
import com.liferay.headless.delivery.resource.v1_0.ContentPageResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.events.ServicePreAction;
import com.liferay.portal.events.ThemeServicePreAction;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutFriendlyURL;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.service.LayoutFriendlyURLLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.servlet.DummyHttpServletResponse;
import com.liferay.portal.kernel.servlet.DynamicServletRequest;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;
import com.liferay.segments.SegmentsEntryRetriever;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.constants.SegmentsWebKeys;
import com.liferay.segments.context.RequestContextMapper;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.processor.SegmentsExperienceRequestProcessorRegistry;
import com.liferay.segments.service.SegmentsExperienceService;
import com.liferay.taglib.util.ThemeUtil;

import java.util.Collections;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.MultivaluedMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

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
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _entityModel;
	}

	@Override
	public ContentPage getSiteContentPage(Long siteId, String friendlyUrlPath)
		throws Exception {

		return _toContentPage(
			_getLayout(siteId, friendlyUrlPath), "getSiteContentPage", null);
	}

	@Override
	public ContentPage getSiteContentPageExperienceExperienceKey(
			Long siteId, String friendlyUrlPath, String experienceKey)
		throws Exception {

		return _toContentPage(
			_getLayout(siteId, friendlyUrlPath), "getSiteContentPage",
			experienceKey);
	}

	@Override
	public String getSiteContentPageExperienceExperienceKeyRenderedPage(
			Long siteId, String friendlyUrlPath, String experienceKey)
		throws Exception {

		return _toHTML(friendlyUrlPath, siteId, experienceKey);
	}

	@Override
	public String getSiteContentPageRenderedPage(
			Long siteId, String friendlyUrlPath)
		throws Exception {

		return _toHTML(friendlyUrlPath, siteId, null);
	}

	@Override
	public Page<ContentPage> getSiteContentPagesPage(
			Long siteId, String search, Aggregation aggregation, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			Collections.emptyMap(),
			booleanQuery -> {
				BooleanFilter booleanFilter =
					booleanQuery.getPreBooleanFilter();

				booleanFilter.add(
					new TermFilter(Field.GROUP_ID, String.valueOf(siteId)),
					BooleanClauseOccur.MUST);
			},
			filter, Layout.class, search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.addVulcanAggregation(aggregation);
				searchContext.setCompanyId(contextCompany.getCompanyId());

				searchContext.setAttribute(Field.TITLE, search);
				searchContext.setAttribute(
					Field.TYPE,
					new String[] {
						LayoutConstants.TYPE_COLLECTION,
						LayoutConstants.TYPE_CONTENT
					});

				searchContext.setAttribute(
					"privateLayout", Boolean.FALSE.toString());

				Group group = groupLocalService.getGroup(siteId);

				searchContext.setCompanyId(group.getCompanyId());

				searchContext.setGroupIds(new long[] {siteId});
				searchContext.setKeywords(search);
			},
			sorts,
			document -> {
				long plid = GetterUtil.getLong(
					document.get(Field.ENTRY_CLASS_PK));

				return _toContentPage(
					_layoutLocalService.getLayout(plid),
					"getSiteContentPagesPage", null);
			});
	}

	private Layout _getLayout(long groupId, String friendlyUrlPath)
		throws Exception {

		String languageId = LocaleUtil.toLanguageId(
			contextAcceptLanguage.getPreferredLocale());

		LayoutFriendlyURL layoutFriendlyURL =
			_layoutFriendlyURLLocalService.getLayoutFriendlyURL(
				groupId, false, StringPool.FORWARD_SLASH + friendlyUrlPath,
				languageId);

		return _layoutLocalService.getLayout(layoutFriendlyURL.getPlid());
	}

	private long _getSegmentsExperienceId(
			Layout layout, String segmentsExperienceKey)
		throws Exception {

		if (Validator.isNotNull(segmentsExperienceKey)) {
			SegmentsExperience segmentsExperience =
				_segmentsExperienceService.fetchSegmentsExperience(
					layout.getGroupId(), segmentsExperienceKey);

			return segmentsExperience.getSegmentsExperienceId();
		}

		contextHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay(layout));

		long[] segmentsEntryIds = _segmentsEntryRetriever.getSegmentsEntryIds(
			layout.getGroupId(), contextUser.getUserId(),
			_requestContextMapper.map(contextHttpServletRequest));

		long[] segmentsExperienceIds =
			_segmentsExperienceRequestProcessorRegistry.
				getSegmentsExperienceIds(
					contextHttpServletRequest, null, layout.getGroupId(),
					_portal.getClassNameId(Layout.class.getName()),
					layout.getPlid(), segmentsEntryIds);

		if (segmentsExperienceIds.length > 0) {
			return segmentsExperienceIds[0];
		}

		return SegmentsEntryConstants.ID_DEFAULT;
	}

	private ThemeDisplay _getThemeDisplay(Layout layout) throws Exception {
		ServicePreAction servicePreAction = new ServicePreAction();

		HttpServletResponse httpServletResponse =
			new DummyHttpServletResponse();

		servicePreAction.servicePre(
			contextHttpServletRequest, httpServletResponse, false);

		ThemeServicePreAction themeServicePreAction =
			new ThemeServicePreAction();

		themeServicePreAction.run(
			contextHttpServletRequest, httpServletResponse);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)contextHttpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		themeDisplay.setLayout(layout);
		themeDisplay.setScopeGroupId(layout.getGroupId());
		themeDisplay.setSiteGroupId(layout.getGroupId());

		return themeDisplay;
	}

	private ContentPage _toContentPage(
			Layout layout, String methodName, String segmentsExperienceKey)
		throws Exception {

		DefaultDTOConverterContext dtoConverterContext =
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				HashMapBuilder.put(
					"get",
					addAction(
						"VIEW", methodName,
						"com.liferay.portal.kernel.model.Group",
						layout.getGroupId())
				).build(),
				_dtoConverterRegistry, contextHttpServletRequest,
				layout.getPlid(), contextAcceptLanguage.getPreferredLocale(),
				contextUriInfo, contextUser);

		long segmentsExperienceId = _getSegmentsExperienceId(
			layout, segmentsExperienceKey);

		dtoConverterContext.setAttribute(
			"segmentsExperienceId", segmentsExperienceId);

		return _contentPageDTOConverter.toDTO(dtoConverterContext, layout);
	}

	private String _toHTML(
			String friendlyUrlPath, long groupId, String segmentsExperienceKey)
		throws Exception {

		Layout layout = _getLayout(groupId, friendlyUrlPath);

		contextHttpServletRequest = DynamicServletRequest.addQueryString(
			contextHttpServletRequest, "p_l_id=" + layout.getPlid(), false);

		long segmentsExperienceId = _getSegmentsExperienceId(
			layout, segmentsExperienceKey);

		contextHttpServletRequest.setAttribute(
			SegmentsWebKeys.SEGMENTS_EXPERIENCE_IDS,
			new long[] {segmentsExperienceId});

		contextHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay(layout));

		layout.includeLayoutContent(
			contextHttpServletRequest, contextHttpServletResponse);

		StringBundler sb =
			(StringBundler)contextHttpServletRequest.getAttribute(
				WebKeys.LAYOUT_CONTENT);

		LayoutSet layoutSet = layout.getLayoutSet();

		ServletContext servletContext = ServletContextPool.get(
			StringPool.BLANK);

		if (contextHttpServletRequest.getAttribute(WebKeys.CTX) == null) {
			contextHttpServletRequest.setAttribute(WebKeys.CTX, servletContext);
		}

		Document document = Jsoup.parse(
			ThemeUtil.include(
				servletContext, contextHttpServletRequest,
				contextHttpServletResponse, "portal_normal.ftl",
				layoutSet.getTheme(), false));

		Element bodyElement = document.body();

		bodyElement.html(sb.toString());

		return document.html();
	}

	private static final EntityModel _entityModel =
		new ContentPageEntityModel();

	@Reference
	private ContentPageDTOConverter _contentPageDTOConverter;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private LayoutFriendlyURLLocalService _layoutFriendlyURLLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutService _layoutService;

	@Reference
	private Portal _portal;

	@Reference
	private RequestContextMapper _requestContextMapper;

	@Reference
	private SegmentsEntryRetriever _segmentsEntryRetriever;

	@Reference
	private SegmentsExperienceRequestProcessorRegistry
		_segmentsExperienceRequestProcessorRegistry;

	@Reference
	private SegmentsExperienceService _segmentsExperienceService;

}