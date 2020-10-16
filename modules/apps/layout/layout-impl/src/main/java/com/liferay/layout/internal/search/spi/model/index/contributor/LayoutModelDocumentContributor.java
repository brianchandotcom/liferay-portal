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

package com.liferay.layout.internal.search.spi.model.index.contributor;

import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.renderer.FragmentRendererController;
import com.liferay.layout.adaptive.media.LayoutAdaptiveMediaProcessor;
import com.liferay.layout.internal.search.util.LayoutPageTemplateStructureRenderUtil;
import com.liferay.layout.internal.servlet.http.DummyHttpServletRequest;
import com.liferay.layout.internal.servlet.http.DummyHttpServletResponse;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.DynamicServletRequest;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;
import com.liferay.portal.util.PropsValues;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.staging.StagingGroupHelper;

import java.util.HashMap;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Vagner B.C
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.portal.kernel.model.Layout",
	service = ModelDocumentContributor.class
)
public class LayoutModelDocumentContributor
	implements ModelDocumentContributor<Layout> {

	public static final String CLASS_NAME = Layout.class.getName();

	@Override
	public void contribute(Document document, Layout layout) {
		if (layout.isSystem() ||
			(layout.getStatus() != WorkflowConstants.STATUS_APPROVED)) {

			return;
		}

		document.addText(
			Field.DEFAULT_LANGUAGE_ID, layout.getDefaultLanguageId());
		document.addLocalizedText(Field.NAME, layout.getNameMap());
		document.addText(
			"privateLayout", String.valueOf(layout.isPrivateLayout()));
		document.addText(Field.TYPE, layout.getType());

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layout.getGroupId(), layout.getPlid());

		for (String languageId : layout.getAvailableLanguageIds()) {
			Locale locale = LocaleUtil.fromLanguageId(languageId);

			document.addText(
				Field.getLocalizedName(locale, Field.TITLE),
				layout.getName(locale));
		}

		if (layoutPageTemplateStructure == null) {
			return;
		}

		PermissionChecker currentPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			HttpServletRequest httpServletRequest =
				new DummyHttpServletRequest();

			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			long userId = layout.getUserId();

			if ((serviceContext != null) && (serviceContext.getUserId() > 0)) {
				userId = serviceContext.getUserId();
			}

			User user = _userLocalService.fetchUser(userId);

			if ((user == null) || user.isDefaultUser()) {
				return;
			}

			httpServletRequest.setAttribute(WebKeys.USER_ID, userId);

			httpServletRequest = DynamicServletRequest.addQueryString(
				httpServletRequest, "p_l_id=" + layout.getPlid(), false);

			HttpServletResponse httpServletResponse =
				new DummyHttpServletResponse();

			try {
				EventsProcessorUtil.process(
					PropsKeys.SERVLET_SERVICE_EVENTS_PRE,
					PropsValues.SERVLET_SERVICE_EVENTS_PRE, httpServletRequest,
					httpServletResponse);
			}
			catch (ActionException actionException) {
				throw new RuntimeException(
					"Unable to initialize dummy HTTP servlet request and " +
						"HTTP servlet response",
					actionException);
			}

			long[] segmentsExperienceIds = {
				SegmentsExperienceConstants.ID_DEFAULT
			};

			Set<Locale> locales = LanguageUtil.getAvailableLocales(
				layout.getGroupId());

			for (Locale locale : locales) {
				try {
					String content =
						LayoutPageTemplateStructureRenderUtil.
							renderLayoutContent(
								_fragmentRendererController, httpServletRequest,
								httpServletResponse,
								_layoutAdaptiveMediaProcessor,
								layoutPageTemplateStructure,
								FragmentEntryLinkConstants.VIEW,
								new HashMap<>(), locale, segmentsExperienceIds);

					if (Validator.isNull(content)) {
						continue;
					}

					document.addText(
						Field.getLocalizedName(locale, Field.CONTENT),
						HtmlUtil.stripHtml(content));
				}
				catch (PortalException portalException) {
					throw new SystemException(portalException);
				}
			}
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
			PermissionThreadLocal.setPermissionChecker(
				currentPermissionChecker);
		}
	}

	@Reference
	private FragmentRendererController _fragmentRendererController;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutAdaptiveMediaProcessor _layoutAdaptiveMediaProcessor;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private StagingGroupHelper _stagingGroupHelper;

	@Reference
	private UserLocalService _userLocalService;

}