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

package com.liferay.adaptive.media.image.web.internal.html;

import com.liferay.adaptive.media.image.html.AMImageHTMLTagFactory;
import com.liferay.adaptive.media.image.html.constants.AMImageHTMLConstants;
import com.liferay.adaptive.media.image.media.query.Condition;
import com.liferay.adaptive.media.image.media.query.MediaQuery;
import com.liferay.adaptive.media.image.media.query.MediaQueryProvider;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = AMImageHTMLTagFactory.class)
public class AMImageHTMLTagFactoryImpl implements AMImageHTMLTagFactory {

	@Override
	public String create(String originalImgTag, FileEntry fileEntry)
		throws PortalException {

		List<String> sourceElements = _getSourceElements(fileEntry);

		if (sourceElements.isEmpty()) {
			return originalImgTag;
		}

		StringBundler sb = new StringBundler(5 + sourceElements.size());

		sb.append("<picture ");
		sb.append(AMImageHTMLConstants.ATTRIBUTE_NAME_FILE_ENTRY_ID);
		sb.append("=\"");
		sb.append(fileEntry.getFileEntryId());
		sb.append("\">");

		sourceElements.forEach(sb::append);

		sb.append(originalImgTag);

		sb.append("</picture>");

		return sb.toString();
	}

	private Optional<String> _getMediaQueryString(MediaQuery mediaQuery) {
		List<Condition> conditions = mediaQuery.getConditions();

		if (conditions.isEmpty()) {
			return Optional.empty();
		}

		String[] conditionStrings = new String[conditions.size()];

		for (int i = 0; i < conditionStrings.length; i++) {
			Condition condition = conditions.get(i);

			StringBundler sb = new StringBundler(5);

			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(condition.getAttribute());
			sb.append(StringPool.COLON);
			sb.append(condition.getValue());
			sb.append(StringPool.CLOSE_PARENTHESIS);

			conditionStrings[i] = sb.toString();
		}

		return Optional.of(StringUtil.merge(conditionStrings, " and "));
	}

	private String _getSourceElement(MediaQuery mediaQuery) {
		StringBundler sb = new StringBundler(5);

		Optional<String> mediaQueryStringOptional = _getMediaQueryString(
			mediaQuery);

		mediaQueryStringOptional.ifPresent(
			mediaQueryString -> {
				sb.append("<source media=\"");
				sb.append(mediaQueryString);
				sb.append("\" srcset=\"");
				sb.append(mediaQuery.getSrc());
				sb.append("\" />");
			});

		return sb.toString();
	}

	private List<String> _getSourceElements(FileEntry fileEntry)
		throws PortalException {

		List<MediaQuery> mediaQueries = _mediaQueryProvider.getMediaQueries(
			fileEntry);

		Stream<MediaQuery> mediaQueryStream = mediaQueries.stream();

		return mediaQueryStream.map(
			this::_getSourceElement
		).collect(
			Collectors.toList()
		);
	}

	@Reference
	private MediaQueryProvider _mediaQueryProvider;

}