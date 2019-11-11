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

package com.liferay.knowledge.base.util;

import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.constants.KBPortletKeys;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.service.KBArticleLocalServiceUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.IOException;
import java.io.InputStream;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Peter Shin
 * @author Brian Wing Shun Chan
 */
public class KnowledgeBaseUtil {

	public static String getKBArticleURL(
		long plid, long resourcePrimKey, int status, String portalURL,
		boolean maximized) {

		StringBundler sb = new StringBundler(19);

		sb.append(portalURL);
		sb.append(PortalUtil.getPathMain());
		sb.append("/knowledge_base/find_kb_article");
		sb.append(StringPool.QUESTION);
		sb.append("plid");
		sb.append(StringPool.EQUAL);
		sb.append(plid);
		sb.append(StringPool.AMPERSAND);
		sb.append("resourcePrimKey");
		sb.append(StringPool.EQUAL);
		sb.append(resourcePrimKey);

		if (status != WorkflowConstants.STATUS_APPROVED) {
			sb.append(StringPool.AMPERSAND);
			sb.append("status");
			sb.append(StringPool.EQUAL);
			sb.append(status);
		}

		if (maximized) {
			sb.append(StringPool.AMPERSAND);
			sb.append("maximized");
			sb.append(StringPool.EQUAL);
			sb.append(maximized);
		}

		return sb.toString();
	}

	public static long getKBFolderId(
			long parentResourceClassNameId, long parentResourcePrimKey)
		throws PortalException {

		long kbFolderClassNameId = PortalUtil.getClassNameId(
			KBFolderConstants.getClassName());

		if (parentResourceClassNameId == kbFolderClassNameId) {
			return parentResourcePrimKey;
		}

		KBArticle kbArticle = KBArticleLocalServiceUtil.getLatestKBArticle(
			parentResourcePrimKey, WorkflowConstants.STATUS_ANY);

		return kbArticle.getKbFolderId();
	}

	public static String getMimeType(byte[] bytes, String fileName) {
		try (InputStream inputStream = new UnsyncByteArrayInputStream(bytes)) {
			return MimeTypesUtil.getContentType(inputStream, fileName);
		}
		catch (IOException ioe) {
			if (_log.isWarnEnabled()) {
				_log.warn(ioe, ioe);
			}
		}

		return null;
	}

	public static Long[][] getParams(Long[] params) {
		if (ArrayUtil.isEmpty(params)) {
			return null;
		}

		if (params.length <= _SQL_DATA_MAX_PARAMETERS) {
			return new Long[][] {new Long[0], params};
		}

		return new Long[][] {
			ArrayUtil.subset(params, _SQL_DATA_MAX_PARAMETERS, params.length),
			ArrayUtil.subset(params, 0, _SQL_DATA_MAX_PARAMETERS)
		};
	}

	public static String getUrlTitle(long id, String title) {
		if (title == null) {
			return String.valueOf(id);
		}

		title = StringUtil.toLowerCase(title.trim());

		if (Validator.isNull(title) || Validator.isNumber(title) ||
			title.equals("rss")) {

			title = String.valueOf(id);
		}
		else {
			title = FriendlyURLNormalizerUtil.normalizeWithPeriodsAndSlashes(
				title);
		}

		return ModelHintsUtil.trimString(
			KBArticle.class.getName(), "urlTitle", title);
	}

	public static boolean isValidUrlTitle(String urlTitle) {
		Matcher matcher = _validFriendlyUrlPattern.matcher(urlTitle);

		return matcher.matches();
	}

	public static void setPreferredKBFolderURLTitle(
			PortalPreferences portalPreferences, String contentRootPrefix,
			String value)
		throws JSONException {

		String preferredKBFolderURLTitle = portalPreferences.getValue(
			KBPortletKeys.KNOWLEDGE_BASE_DISPLAY, "preferredKBFolderURLTitle",
			"{}");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			preferredKBFolderURLTitle);

		jsonObject.put(contentRootPrefix, value);

		portalPreferences.setValue(
			KBPortletKeys.KNOWLEDGE_BASE_DISPLAY, "preferredKBFolderURLTitle",
			jsonObject.toString());
	}

	public static List<KBArticle> sort(
		long[] resourcePrimKeys, List<KBArticle> kbArticles) {

		Map<Long, KBArticle> map = new HashMap<>();

		for (KBArticle kbArticle : kbArticles) {
			map.put(kbArticle.getResourcePrimKey(), kbArticle);
		}

		kbArticles.clear();

		for (long resourcePrimKey : resourcePrimKeys) {
			if (map.containsKey(resourcePrimKey)) {
				kbArticles.add(map.get(resourcePrimKey));
			}
		}

		return kbArticles;
	}

	public static String[] splitKeywords(String keywords) {
		Set<String> keywordsSet = new LinkedHashSet<>();

		StringBundler sb = new StringBundler();

		for (char c : keywords.toCharArray()) {
			if (Character.isWhitespace(c)) {
				if (sb.length() > 0) {
					keywordsSet.add(sb.toString());

					sb = new StringBundler();
				}
			}
			else if (Character.isLetterOrDigit(c)) {
				sb.append(c);
			}
			else {
				return new String[] {keywords};
			}
		}

		if (sb.length() > 0) {
			keywordsSet.add(sb.toString());
		}

		return StringUtil.split(StringUtil.merge(keywordsSet));
	}

	public static String trimLeadingSlash(String s) {
		if (Validator.isNull(s)) {
			return s;
		}

		int x = 0;

		for (char c : s.toCharArray()) {
			if ((c != CharPool.BACK_SLASH) && (c != CharPool.FORWARD_SLASH)) {
				break;
			}

			x = x + 1;
		}

		return s.substring(x);
	}

	private static final int _SQL_DATA_MAX_PARAMETERS = GetterUtil.getInteger(
		PropsUtil.get(PropsKeys.SQL_DATA_MAX_PARAMETERS));

	private static final Log _log = LogFactoryUtil.getLog(
		KnowledgeBaseUtil.class);

	private static final Pattern _validFriendlyUrlPattern = Pattern.compile(
		"/[a-z0-9_-]+");

}