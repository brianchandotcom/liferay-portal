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

package com.liferay.layout.internal.content;

import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.renderer.FragmentRendererController;
import com.liferay.layout.content.LayoutContentProvider;
import com.liferay.layout.crawler.LayoutCrawler;
import com.liferay.layout.internal.search.util.LayoutPageTemplateStructureRenderUtil;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.servlet.DynamicServletRequest;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.RenderLayoutContentThreadLocal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;

/**
 * @author Eudaldo Alonso
 */
@Component(service = LayoutContentProvider.class)
public class LayoutContentProviderImpl implements LayoutContentProvider {

	@Override
	public String getLayoutContent(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, Layout layout, Locale locale) {

		if ((httpServletRequest == null) || (httpServletResponse == null)) {
			return StringPool.BLANK;
		}

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layout.getGroupId(), layout.getPlid());

		if (layoutPageTemplateStructure == null) {
			return StringPool.BLANK;
		}

		boolean originalRenderLayoutContent =
			RenderLayoutContentThreadLocal.isRenderLayoutContent();

		try {
			RenderLayoutContentThreadLocal.setRenderLayoutContent(true);

			if (_isUseLayoutCrawler(layout)) {
				String content = StringPool.BLANK;

				try {
					content = _layoutCrawler.getLayoutContent(layout, locale);
				}
				catch (Exception exception) {
					if (_log.isWarnEnabled()) {
						_log.warn("Unable to get layout content", exception);
					}
				}

				content = _getWrapper(content);

				if (Validator.isNotNull(content)) {
					return content;
				}
			}

			httpServletRequest = DynamicServletRequest.addQueryString(
				httpServletRequest, "p_l_id=" + layout.getPlid(), false);

			Layout originalRequestLayout =
				(Layout)httpServletRequest.getAttribute(WebKeys.LAYOUT);

			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			Layout originalThemeDisplayLayout = themeDisplay.getLayout();
			long originalThemeDisplayPlid = themeDisplay.getPlid();

			try {
				httpServletRequest.setAttribute(WebKeys.LAYOUT, layout);

				themeDisplay.setLayout(layout);
				themeDisplay.setPlid(layout.getPlid());

				long segmentsExperienceId =
					_segmentsExperienceLocalService.
						fetchDefaultSegmentsExperienceId(layout.getPlid());

				String content = StringPool.BLANK;

				try {
					content =
						LayoutPageTemplateStructureRenderUtil.
							renderLayoutContent(
								_fragmentRendererController, httpServletRequest,
								httpServletResponse,
								layoutPageTemplateStructure,
								FragmentEntryLinkConstants.VIEW, locale,
								segmentsExperienceId);
				}
				catch (Exception exception) {
					if (_log.isWarnEnabled()) {
						_log.warn("Unable to get layout content", exception);
					}
				}

				return _html.stripHtml(content);
			}
			finally {
				httpServletRequest.setAttribute(
					WebKeys.LAYOUT, originalRequestLayout);

				themeDisplay.setLayout(originalThemeDisplayLayout);
				themeDisplay.setPlid(originalThemeDisplayPlid);
			}
		}
		finally {
			RenderLayoutContentThreadLocal.setRenderLayoutContent(
				originalRenderLayoutContent);
		}
	}

	private String _getWrapper(String layoutContent) {
		int wrapperIndex = layoutContent.indexOf(_WRAPPER_ELEMENT);

		if (wrapperIndex == -1) {
			return layoutContent;
		}

		return _html.stripHtml(
			layoutContent.substring(wrapperIndex + _WRAPPER_ELEMENT.length()));
	}

	private boolean _isUseLayoutCrawler(Layout layout) {
		if ((_layoutCrawler == null) || layout.isPrivateLayout()) {
			return false;
		}

		Role role = _roleLocalService.fetchRole(
			layout.getCompanyId(), RoleConstants.GUEST);

		if (role == null) {
			return false;
		}

		ResourcePermission resourcePermission =
			_resourcePermissionLocalService.fetchResourcePermission(
				role.getCompanyId(), Layout.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(layout.getPlid()), role.getRoleId());

		if ((resourcePermission != null) &&
			resourcePermission.isViewActionId()) {

			return true;
		}

		return false;
	}

	private static final String _WRAPPER_ELEMENT = "id=\"wrapper\">";

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutContentProviderImpl.class);

	@Reference
	private FragmentRendererController _fragmentRendererController;

	@Reference
	private Html _html;

	@Reference(cardinality = ReferenceCardinality.OPTIONAL)
	private LayoutCrawler _layoutCrawler;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}