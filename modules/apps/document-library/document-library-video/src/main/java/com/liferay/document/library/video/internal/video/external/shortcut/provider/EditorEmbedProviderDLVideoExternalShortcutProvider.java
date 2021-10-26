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

package com.liferay.document.library.video.internal.video.external.shortcut.provider;

import com.liferay.document.library.video.external.shortcut.DLVideoExternalShortcut;
import com.liferay.document.library.video.external.shortcut.provider.DLVideoExternalShortcutProvider;
import com.liferay.frontend.editor.embed.EditorEmbedProvider;
import com.liferay.frontend.editor.embed.constants.EditorEmbedProviderTypeConstants;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = DLVideoExternalShortcutProvider.class)
public class EditorEmbedProviderDLVideoExternalShortcutProvider
	implements DLVideoExternalShortcutProvider {

	@Override
	public DLVideoExternalShortcut getDLVideoExternalShortcut(String url) {
		for (EditorEmbedProvider videoEditorEmbedProvider :
				_videoEditorEmbedProviders) {

			for (String urlScheme : videoEditorEmbedProvider.getURLSchemes()) {
				Pattern pattern = Pattern.compile(urlScheme);

				Matcher matcher = pattern.matcher(url);

				if (matcher.matches()) {
					return new DLVideoExternalShortcut() {

						@Override
						public String getURL() {
							return url;
						}

						@Override
						public String renderHTML(
							HttpServletRequest httpServletRequest) {

							return StringUtil.replace(
								videoEditorEmbedProvider.getTpl(), "{embedId}",
								matcher.group(1));
						}

					};
				}
			}
		}

		return null;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_videoEditorEmbedProviders = ServiceTrackerListFactory.open(
			bundleContext, EditorEmbedProvider.class,
			"(type=" + EditorEmbedProviderTypeConstants.VIDEO + ")");
	}

	@Deactivate
	protected void deactivate() {
		_videoEditorEmbedProviders.close();
	}

	@Reference
	private Http _http;

	private ServiceTrackerList<EditorEmbedProvider> _videoEditorEmbedProviders;

}