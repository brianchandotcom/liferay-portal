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

package com.liferay.style.book.web.internal.portlet.action;

import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.DefaultFragmentRendererContext;
import com.liferay.fragment.renderer.FragmentRendererController;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.uuid.PortalUUID;
import com.liferay.style.book.constants.StyleBookPortletKeys;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + StyleBookPortletKeys.STYLE_BOOK,
		"mvc.command.name=/style_book/render_fragment_entry_link"
	},
	service = MVCResourceCommand.class
)
public class RenderFragmentEntryLinkMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		FragmentEntry fragmentEntry = _getFragmentEntry(resourceRequest);

		if (fragmentEntry == null) {
			return;
		}

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.createFragmentEntryLink(0);

		fragmentEntryLink.setFragmentEntryId(
			fragmentEntry.getFragmentEntryId());
		fragmentEntryLink.setCss(fragmentEntry.getCss());
		fragmentEntryLink.setHtml(fragmentEntry.getHtml());
		fragmentEntryLink.setJs(fragmentEntry.getJs());
		fragmentEntryLink.setConfiguration(fragmentEntry.getConfiguration());
		fragmentEntryLink.setNamespace(_portalUUID.generate());

		String configurationValues = ParamUtil.get(
			resourceRequest, "configurationValues", StringPool.BLANK);

		if (Validator.isNotNull(configurationValues)) {
			JSONObject configurationValuesJSONObject =
				JSONFactoryUtil.createJSONObject(configurationValues);

			JSONObject editableValuesJSONObject = JSONUtil.put(
				_KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR,
				configurationValuesJSONObject);

			fragmentEntryLink.setEditableValues(
				editableValuesJSONObject.toString());
		}

		DefaultFragmentRendererContext defaultFragmentRendererContext =
			new DefaultFragmentRendererContext(fragmentEntryLink);

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String languageId = ParamUtil.getString(
			resourceRequest, "languageId", themeDisplay.getLanguageId());

		defaultFragmentRendererContext.setLocale(
			LocaleUtil.fromLanguageId(languageId));

		defaultFragmentRendererContext.setMode(FragmentEntryLinkConstants.VIEW);
		defaultFragmentRendererContext.setUseCachedContent(false);

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			resourceRequest);

		boolean isolated = themeDisplay.isIsolated();

		themeDisplay.setIsolated(true);

		String content = StringPool.BLANK;

		try {
			content = _fragmentRendererController.render(
				defaultFragmentRendererContext, httpServletRequest,
				_portal.getHttpServletResponse(resourceResponse));
		}
		finally {
			themeDisplay.setIsolated(isolated);
		}

		InputStream inputStream = new ByteArrayInputStream(content.getBytes());

		PortletResponseUtil.write(resourceResponse, inputStream);
	}

	private FragmentEntry _getFragmentEntry(ResourceRequest resourceRequest) {
		long groupId = ParamUtil.getLong(resourceRequest, "groupId");

		String fragmentEntryKey = ParamUtil.getString(
			resourceRequest, "fragmentEntryKey");

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.fetchFragmentEntry(
				groupId, fragmentEntryKey);

		if (fragmentEntry == null) {
			fragmentEntry =
				_fragmentCollectionContributorTracker.getFragmentEntry(
					fragmentEntryKey);
		}

		return fragmentEntry;
	}

	private static final String _KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR =
		"com.liferay.fragment.entry.processor.freemarker." +
			"FreeMarkerFragmentEntryProcessor";

	@Reference
	private FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Reference
	private FragmentRendererController _fragmentRendererController;

	@Reference
	private Portal _portal;

	@Reference
	private PortalUUID _portalUUID;

}