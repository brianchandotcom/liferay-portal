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

package com.liferay.fragment.internal.renderer;

import com.liferay.flags.taglib.servlet.taglib.react.FlagsTag;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.info.item.InfoItemReference;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.layout.display.page.constants.LayoutDisplayPageWebKeys;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Tuple;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pavel Savinov
 */
@Component(service = FragmentRenderer.class)
public class ContentFlagsFragmentRenderer
	extends BaseContentFragmentRenderer implements FragmentRenderer {

	@Override
	public String getCollectionKey() {
		return "content-display";
	}

	@Override
	public String getConfiguration(
		FragmentRendererContext fragmentRendererContext) {

		return JSONUtil.put(
			"fieldSets",
			JSONUtil.putAll(
				JSONUtil.put(
					"fields",
					JSONUtil.putAll(
						JSONUtil.put(
							"label", "item"
						).put(
							"name", "itemSelector"
						).put(
							"type", "itemSelector"
						),
						JSONUtil.put(
							"label", "message"
						).put(
							"name", "message"
						).put(
							"type", "text"
						))))
		).toString();
	}

	@Override
	public String getIcon() {
		return "web-content";
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "content-flags");
	}

	@Override
	public void render(
		FragmentRendererContext fragmentRendererContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		FlagsTag flagsTag = new FlagsTag();

		Tuple displayObject = getDisplayObject(
			fragmentRendererContext, httpServletRequest);

		String className = GetterUtil.getString(displayObject.getObject(0));

		flagsTag.setClassName(className);

		long classPK = GetterUtil.getLong(displayObject.getObject(1));

		flagsTag.setClassPK(classPK);

		flagsTag.setReportedUserId(portal.getUserId(httpServletRequest));

		FragmentEntryLink fragmentEntryLink =
			fragmentRendererContext.getFragmentEntryLink();

		try {
			flagsTag.setMessage(
				LanguageUtil.get(
					httpServletRequest,
					GetterUtil.getString(
						fragmentEntryConfigurationParser.getFieldValue(
							getConfiguration(fragmentRendererContext),
							fragmentEntryLink.getEditableValues(),
							"message"))));

			LayoutDisplayPageProvider<?> layoutDisplayPageProvider =
				(LayoutDisplayPageProvider<?>)httpServletRequest.getAttribute(
					LayoutDisplayPageWebKeys.LAYOUT_DISPLAY_PAGE_PROVIDER);

			if (layoutDisplayPageProvider != null) {
				LayoutDisplayPageObjectProvider<?>
					layoutDisplayPageObjectProvider =
						layoutDisplayPageProvider.
							getLayoutDisplayPageObjectProvider(
								new InfoItemReference(className, classPK));

				if (layoutDisplayPageObjectProvider != null) {
					flagsTag.setContentTitle(
						layoutDisplayPageObjectProvider.getTitle(
							fragmentRendererContext.getLocale()));
				}
			}

			flagsTag.doTag(httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {
			_log.error("Unable to render content flags fragment", exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ContentFlagsFragmentRenderer.class);

}