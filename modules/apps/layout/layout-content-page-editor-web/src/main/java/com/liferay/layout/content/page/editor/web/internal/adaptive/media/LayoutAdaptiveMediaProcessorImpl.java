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
import com.liferay.adaptive.media.image.media.query.Condition;
import com.liferay.adaptive.media.image.media.query.MediaQuery;
import com.liferay.adaptive.media.image.media.query.MediaQueryProvider;
import com.liferay.adaptive.media.image.url.AMImageURLFactory;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.layout.adaptive.media.LayoutAdaptiveMediaProcessor;
import com.liferay.layout.responsive.ViewportSize;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.net.URI;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(service = LayoutAdaptiveMediaProcessor.class)
public class LayoutAdaptiveMediaProcessorImpl
	implements LayoutAdaptiveMediaProcessor {

	@Override
	public String processAdaptiveMediaContent(String content) {
		String processedContent = _contentTransformerHandler.transform(
			ContentTransformerContentTypes.HTML, content);

		Document document = Jsoup.parse(processedContent);

		try {
			for (ViewportSize viewportSize : _viewportSizes) {
				Elements elements = document.getElementsByAttribute(
					"data-" + viewportSize.getViewportSizeId() +
						"-configuration");

				for (Element element : elements) {
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

			_replaceCSSProperties(document);
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

	private String _getMediaQuery(String elementId, long fileEntryId)
		throws PortalException {

		StringBundler sb = new StringBundler();

		List<MediaQuery> mediaQueries = _mediaQueryProvider.getMediaQueries(
			_dlAppService.getFileEntry(fileEntryId));

		for (MediaQuery mediaQuery : mediaQueries) {
			List<Condition> conditions = mediaQuery.getConditions();

			sb.append("@media ");

			for (Condition condition : conditions) {
				sb.append(StringPool.OPEN_PARENTHESIS);
				sb.append(condition.getAttribute());
				sb.append(StringPool.COLON);
				sb.append(condition.getValue());
				sb.append(StringPool.CLOSE_PARENTHESIS);

				if (conditions.indexOf(condition) != (conditions.size() - 1)) {
					sb.append(" and ");
				}
			}

			sb.append(StringPool.OPEN_CURLY_BRACE);
			sb.append(StringPool.POUND);
			sb.append(elementId);
			sb.append("{background-image: url(");
			sb.append(mediaQuery.getSrc());
			sb.append(") !important;}}");
		}

		return sb.toString();
	}

	private void _replaceCSSProperties(Document document)
		throws PortalException {

		Elements styledElements = document.select("*[style]");

		for (Element styledElement : styledElements) {
			String styleText = styledElement.attr("style");

			if (!styleText.contains("--background-image-file-entry-id:")) {
				continue;
			}

			StringBundler sb = new StringBundler();

			String elementId = styledElement.attr("id");

			if (Validator.isNull(elementId)) {
				elementId = StringUtil.randomId();

				styledElement.attr("id", elementId);
			}

			Matcher matcher = _cssPropertyPattern.matcher(styleText);

			while (matcher.find()) {
				sb.append(
					_getMediaQuery(
						elementId, GetterUtil.getLong(matcher.group(1))));
			}

			if (sb.length() > 0) {
				Element newStyleElement = styledElement.prependElement("style");

				newStyleElement.text(sb.toString());
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutAdaptiveMediaProcessorImpl.class);

	private static final Pattern _cssPropertyPattern = Pattern.compile(
		"--background-image-file-entry-id:\\s*(\\d+);");
	private static final ViewportSize[] _viewportSizes = ViewportSize.values();

	@Reference
	private AMImageConfigurationHelper _amImageConfigurationHelper;

	@Reference
	private AMImageURLFactory _amImageURLFactory;

	@Reference
	private ContentTransformerHandler _contentTransformerHandler;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private MediaQueryProvider _mediaQueryProvider;

}