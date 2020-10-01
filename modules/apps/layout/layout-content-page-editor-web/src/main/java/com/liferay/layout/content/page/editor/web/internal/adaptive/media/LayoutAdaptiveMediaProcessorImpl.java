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

package com.liferay.layout.content.page.editor.web.internal.adaptive.media;

import com.liferay.adaptive.media.content.transformer.ContentTransformerHandler;
import com.liferay.adaptive.media.content.transformer.constants.ContentTransformerContentTypes;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationHelper;
import com.liferay.adaptive.media.image.html.constants.AMImageHTMLConstants;
import com.liferay.adaptive.media.image.url.AMImageURLFactory;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.layout.adaptive.media.LayoutAdaptiveMediaProcessor;
import com.liferay.layout.content.page.editor.web.internal.configuration.FFLayoutContentPageEditorConfiguration;
import com.liferay.layout.responsive.ViewportSize;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.net.URI;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	configurationPid = "com.liferay.layout.content.page.editor.web.internal.configuration.FFLayoutContentPageEditorConfiguration",
	immediate = true, service = LayoutAdaptiveMediaProcessor.class
)
public class LayoutAdaptiveMediaProcessorImpl
	implements LayoutAdaptiveMediaProcessor {

	@Override
	public String processAdaptiveMediaContent(String content) {
		if (!_ffLayoutContentPageEditorConfiguration.adaptiveMediaEnabled()) {
			return content;
		}

		String processedContent = _contentTransformerHandler.transform(
			ContentTransformerContentTypes.HTML, content);

		Document document = Jsoup.parse(processedContent);

		try {
			for (ViewportSize viewportSize : ViewportSize.values()) {
				Elements elements = document.getElementsByAttribute(
					"data-" + viewportSize.getViewportSizeId() +
						"-configuration");

				Iterator<Element> iterator = elements.iterator();

				while (iterator.hasNext()) {
					Element element = iterator.next();

					if (!StringUtil.equalsIgnoreCase(
							element.tagName(), "img")) {

						continue;
					}

					String configuration = element.attr(
						"data-" + viewportSize.getViewportSizeId() +
							"-configuration");

					long fileEntryId = GetterUtil.getLong(
						element.attr(
							AMImageHTMLConstants.ATTRIBUTE_NAME_FILE_ENTRY_ID));

					if (fileEntryId <= 0) {
						continue;
					}

					FileEntry fileEntry = _dlAppService.getFileEntry(
						fileEntryId);

					Optional<AMImageConfigurationEntry>
						amImageConfigurationEntryOptional =
							_amImageConfigurationHelper.
								getAMImageConfigurationEntry(
									fileEntry.getCompanyId(), configuration);

					if (!amImageConfigurationEntryOptional.isPresent()) {
						continue;
					}

					AMImageConfigurationEntry amImageConfigurationEntry =
						amImageConfigurationEntryOptional.get();

					URI uri = _amImageURLFactory.createFileEntryURL(
						fileEntry.getFileVersion(), amImageConfigurationEntry);

					_appendSourceElement(document, element, uri, viewportSize);
				}
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to process adaptive media content", exception);
			}
		}

		Element bodyElement = document.body();

		return bodyElement.html();
	}

	@Modified
	protected void activate(Map<String, Object> properties) {
		_ffLayoutContentPageEditorConfiguration =
			ConfigurableUtil.createConfigurable(
				FFLayoutContentPageEditorConfiguration.class, properties);
	}

	private void _appendSourceElement(
		Document document, Element element, URI uri,
		ViewportSize viewportSize) {

		Element sourceElement = document.createElement("source");

		Element parentElement = element.parent();

		StringBundler sb = new StringBundler(6);

		sb.append("(min-width:");
		sb.append(viewportSize.getMinWidth());
		sb.append("px)");

		if (viewportSize != ViewportSize.DESKTOP) {
			sb.append(" and (max-width:");
			sb.append(viewportSize.getMaxWidth());
			sb.append("px)");
		}

		sourceElement.attr("media", sb.toString());
		sourceElement.attr("srcset", uri.toString());

		parentElement.prependChild(sourceElement);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutAdaptiveMediaProcessorImpl.class);

	@Reference
	private AMImageConfigurationHelper _amImageConfigurationHelper;

	@Reference
	private AMImageURLFactory _amImageURLFactory;

	@Reference
	private ContentTransformerHandler _contentTransformerHandler;

	@Reference
	private DLAppService _dlAppService;

	private volatile FFLayoutContentPageEditorConfiguration
		_ffLayoutContentPageEditorConfiguration;

}