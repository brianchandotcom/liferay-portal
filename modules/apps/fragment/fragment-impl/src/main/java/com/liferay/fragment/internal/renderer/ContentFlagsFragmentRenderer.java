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
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Tuple;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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
							"label", "content"
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
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = resourceBundleLoader.loadResourceBundle(
			locale);

		return LanguageUtil.get(resourceBundle, "content-flags");
	}

	@Override
	public void render(
		FragmentRendererContext fragmentRendererContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		Tuple displayObject = getDisplayObject(
			fragmentRendererContext, httpServletRequest);

		String className = GetterUtil.getString(displayObject.getObject(0));
		long classPK = GetterUtil.getLong(displayObject.getObject(1));

		InfoDisplayContributor infoDisplayContributor =
			(InfoDisplayContributor)httpServletRequest.getAttribute(
				InfoDisplayWebKeys.INFO_DISPLAY_CONTRIBUTOR);

		FlagsTag flagsTag = new FlagsTag();

		flagsTag.setClassName(className);
		flagsTag.setClassPK(classPK);
		flagsTag.setReportedUserId(_portal.getUserId(httpServletRequest));

		FragmentEntryLink fragmentEntryLink =
			fragmentRendererContext.getFragmentEntryLink();

		try {
			String message = LanguageUtil.get(
				httpServletRequest,
				GetterUtil.getString(
					fragmentEntryConfigurationParser.getFieldValue(
						getConfiguration(fragmentRendererContext),
						fragmentEntryLink.getEditableValues(), "message")));

			flagsTag.setMessage(message);

			if (infoDisplayContributor != null) {
				InfoDisplayObjectProvider infoDisplayObjectProvider =
					infoDisplayContributor.getInfoDisplayObjectProvider(
						classPK);

				if (infoDisplayObjectProvider != null) {
					flagsTag.setContentTitle(
						infoDisplayObjectProvider.getTitle(
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

	@Reference
	private Portal _portal;

}