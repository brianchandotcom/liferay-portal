/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.web.internal.html;

import com.liferay.adaptive.media.image.html.AMImageHTMLTagFactory;
import com.liferay.adaptive.media.image.html.constants.AMImageHTMLConstants;
import com.liferay.adaptive.media.image.media.query.Condition;
import com.liferay.adaptive.media.image.media.query.MediaQuery;
import com.liferay.adaptive.media.image.media.query.MediaQueryProvider;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;

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

	private String _getMediaQueryString(MediaQuery mediaQuery) {
		List<Condition> conditions = mediaQuery.getConditions();

		if (conditions.isEmpty()) {
			return null;
		}

		String[] conditionStrings = new String[conditions.size()];

		for (int i = 0; i < conditionStrings.length; i++) {
			Condition condition = conditions.get(i);

			conditionStrings[i] = StringBundler.concat(
				StringPool.OPEN_PARENTHESIS, condition.getAttribute(),
				StringPool.COLON, condition.getValue(),
				StringPool.CLOSE_PARENTHESIS);
		}

		return StringUtil.merge(conditionStrings, " and ");
	}

	private String _getSourceElement(MediaQuery mediaQuery) {
		String mediaQueryString = _getMediaQueryString(mediaQuery);

		if (mediaQueryString == null) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(5);

		sb.append("<source media=\"");
		sb.append(mediaQueryString);
		sb.append("\" srcset=\"");
		sb.append(mediaQuery.getSrc());
		sb.append("\" />");

		return sb.toString();
	}

	private List<String> _getSourceElements(FileEntry fileEntry)
		throws PortalException {

		return TransformUtil.transform(
			_mediaQueryProvider.getMediaQueries(fileEntry),
			this::_getSourceElement);
	}

	@Reference
	private MediaQueryProvider _mediaQueryProvider;

}