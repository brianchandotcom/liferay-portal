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

package com.liferay.layout.content.page.editor.web.internal.portlet.action;

import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.entry.processor.util.EditableFragmentEntryProcessorUtil;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.DefaultFragmentRendererContext;
import com.liferay.fragment.renderer.FragmentRendererController;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryService;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProviderTracker;
import com.liferay.layout.display.page.constants.LayoutDisplayPageWebKeys;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pablo Molina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/layout_content_page_editor/get_fragment_entry_link"
	},
	service = MVCResourceCommand.class
)
public class GetFragmentEntryLinkMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		long fragmentEntryLinkId = ParamUtil.getLong(
			resourceRequest, "fragmentEntryLinkId");

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
				fragmentEntryLinkId);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (fragmentEntryLink != null) {
			DefaultFragmentRendererContext defaultFragmentRendererContext =
				new DefaultFragmentRendererContext(fragmentEntryLink);

			ThemeDisplay themeDisplay =
				(ThemeDisplay)resourceRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			String languageId = ParamUtil.getString(
				resourceRequest, "languageId", themeDisplay.getLanguageId());

			defaultFragmentRendererContext.setLocale(
				LocaleUtil.fromLanguageId(languageId));

			defaultFragmentRendererContext.setMode(
				FragmentEntryLinkConstants.EDIT);

			long segmentsExperienceId = ParamUtil.getLong(
				resourceRequest, "segmentsExperienceId");

			defaultFragmentRendererContext.setSegmentsExperienceIds(
				new long[] {segmentsExperienceId});

			String collectionItemClassName = ParamUtil.getString(
				resourceRequest, "collectionItemClassName");
			long collectionItemClassPK = ParamUtil.getLong(
				resourceRequest, "collectionItemClassPK");

			HttpServletRequest httpServletRequest =
				_portal.getHttpServletRequest(resourceRequest);

			LayoutDisplayPageProvider<?> currentLayoutDisplayPageProvider =
				(LayoutDisplayPageProvider<?>)httpServletRequest.getAttribute(
					LayoutDisplayPageWebKeys.LAYOUT_DISPLAY_PAGE_PROVIDER);

			if (Validator.isNotNull(collectionItemClassName) &&
				(collectionItemClassPK > 0)) {

				InfoItemIdentifier infoItemIdentifier =
					new ClassPKInfoItemIdentifier(collectionItemClassPK);

				InfoItemObjectProvider<Object> infoItemObjectProvider =
					_infoItemServiceTracker.getFirstInfoItemService(
						InfoItemObjectProvider.class, collectionItemClassName,
						infoItemIdentifier.getInfoItemServiceFilter());

				if (infoItemObjectProvider != null) {
					Object infoItemObject = infoItemObjectProvider.getInfoItem(
						infoItemIdentifier);

					defaultFragmentRendererContext.setDisplayObject(
						infoItemObject);

					httpServletRequest.setAttribute(
						InfoDisplayWebKeys.INFO_LIST_DISPLAY_OBJECT,
						infoItemObject);
				}

				LayoutDisplayPageProvider<?> layoutDisplayPageProvider =
					_layoutDisplayPageProviderTracker.
						getLayoutDisplayPageProviderByClassName(
							collectionItemClassName);

				if (layoutDisplayPageProvider != null) {
					httpServletRequest.setAttribute(
						LayoutDisplayPageWebKeys.LAYOUT_DISPLAY_PAGE_PROVIDER,
						layoutDisplayPageProvider);
				}
			}

			try {
				String content = _fragmentRendererController.render(
					defaultFragmentRendererContext, httpServletRequest,
					_portal.getHttpServletResponse(resourceResponse));

				jsonObject.put(
					"content", content
				).put(
					"editableTypes",
					EditableFragmentEntryProcessorUtil.getEditableTypes(content)
				).put(
					"editableValues",
					JSONFactoryUtil.createJSONObject(
						fragmentEntryLink.getEditableValues())
				);

				FragmentEntry fragmentEntry =
					_fragmentEntryService.fetchFragmentEntry(
						fragmentEntryLink.getFragmentEntryId());

				if (fragmentEntry == null) {
					fragmentEntry =
						_fragmentCollectionContributorTracker.getFragmentEntry(
							fragmentEntryLink.getRendererKey());
				}

				if (fragmentEntry != null) {
					jsonObject.put("icon", fragmentEntry.getIcon());
				}
			}
			finally {
				httpServletRequest.removeAttribute(
					InfoDisplayWebKeys.INFO_LIST_DISPLAY_OBJECT);

				httpServletRequest.setAttribute(
					LayoutDisplayPageWebKeys.LAYOUT_DISPLAY_PAGE_PROVIDER,
					currentLayoutDisplayPageProvider);
			}

			if (SessionErrors.contains(
					httpServletRequest, "fragmentEntryContentInvalid")) {

				jsonObject.put("error", true);

				SessionErrors.clear(httpServletRequest);
			}
		}

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse, jsonObject);
	}

	@Reference
	private FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private FragmentEntryService _fragmentEntryService;

	@Reference
	private FragmentRendererController _fragmentRendererController;

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private LayoutDisplayPageProviderTracker _layoutDisplayPageProviderTracker;

	@Reference
	private Portal _portal;

}