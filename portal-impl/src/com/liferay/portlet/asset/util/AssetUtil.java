/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.NoSuchCategoryException;
import com.liferay.portlet.asset.NoSuchTagException;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetCategoryProperty;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.model.AssetTagProperty;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetCategoryPropertyLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagPropertyLocalServiceUtil;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class AssetUtil {

	public static final char[] INVALID_CHARACTERS = new char[] {
		CharPool.AMPERSAND, CharPool.APOSTROPHE, CharPool.AT,
		CharPool.BACK_SLASH, CharPool.CLOSE_BRACKET, CharPool.CLOSE_CURLY_BRACE,
		CharPool.COLON, CharPool.COMMA, CharPool.EQUAL, CharPool.GREATER_THAN,
		CharPool.FORWARD_SLASH, CharPool.LESS_THAN, CharPool.NEW_LINE,
		CharPool.OPEN_BRACKET, CharPool.OPEN_CURLY_BRACE, CharPool.PERCENT,
		CharPool.PIPE, CharPool.PLUS, CharPool.POUND, CharPool.QUESTION,
		CharPool.QUOTE, CharPool.RETURN, CharPool.SEMICOLON, CharPool.SLASH,
		CharPool.STAR, CharPool.TILDE
	};

	public static Set<String> addLayoutTags(
		HttpServletRequest request, List<AssetTag> tags) {

		Set<String> layoutTags = getLayoutTagNames(request);

		for (AssetTag tag : tags) {
			layoutTags.add(tag.getName());
		}

		return layoutTags;
	}

	public static void addPortletBreadcrumbEntries(
			long assetCategoryId, HttpServletRequest request,
			PortletURL portletURL)
		throws Exception {

		AssetCategory assetCategory = AssetCategoryLocalServiceUtil.getCategory(
			assetCategoryId);

		List<AssetCategory> ancestorCategories = assetCategory.getAncestors();

		Collections.reverse(ancestorCategories);

		for (AssetCategory ancestorCategory : ancestorCategories) {
			portletURL.setParameter("categoryId", String.valueOf(
				ancestorCategory.getCategoryId()));

			addPortletBreadcrumbEntry(
				request, ancestorCategory.getTitleCurrentValue(),
				portletURL.toString());
		}

		portletURL.setParameter("categoryId", String.valueOf(assetCategoryId));

		addPortletBreadcrumbEntry(
			request, assetCategory.getTitleCurrentValue(),
			portletURL.toString());
	}

	public static void addPortletBreadcrumbEntry(
			HttpServletRequest request, String title, String url)
		throws Exception {

		List<BreadcrumbEntry> breadcrumbEntries =
			(List<BreadcrumbEntry>)request.getAttribute(
				WebKeys.PORTLET_BREADCRUMBS);

		if (breadcrumbEntries != null) {
			for (BreadcrumbEntry breadcrumbEntry : breadcrumbEntries) {
				if (title.equals(breadcrumbEntry.getTitle())) {
					return;
				}
			}
		}

		PortalUtil.addPortletBreadcrumbEntry(request, title, url);
	}

	public static String getAssetKeywords(String className, long classPK)
		throws SystemException {

		List<AssetTag> tags = AssetTagLocalServiceUtil.getTags(
			className, classPK);
		List<AssetCategory> categories =
			AssetCategoryLocalServiceUtil.getCategories(className, classPK);

		StringBuffer sb = new StringBuffer();

		sb.append(ListUtil.toString(tags, AssetTag.NAME_ACCESSOR));

		if (!tags.isEmpty()) {
			sb.append(StringPool.COMMA);
		}

		sb.append(ListUtil.toString(categories, AssetCategory.NAME_ACCESSOR));

		return sb.toString();
	}

	public static Set<String> getLayoutTagNames(HttpServletRequest request) {
		Set<String> tagNames = (Set<String>)request.getAttribute(
			WebKeys.ASSET_LAYOUT_TAG_NAMES);

		if (tagNames == null) {
			tagNames = new HashSet<String>();

			request.setAttribute(WebKeys.ASSET_LAYOUT_TAG_NAMES, tagNames);
		}

		return tagNames;
	}

	public static boolean isValidWord(String word) {
		if (Validator.isNull(word)) {
			return false;
		}
		else {
			char[] wordCharArray = word.toCharArray();

			for (char c : wordCharArray) {
				for (char invalidChar : INVALID_CHARACTERS) {
					if (c == invalidChar) {
						if (_log.isDebugEnabled()) {
							_log.debug(
								"Word " + word + " is not valid because " + c +
									" is not allowed");
						}

						return false;
					}
				}
			}
		}

		return true;
	}

	public static String substituteCategoryPropertyVariables(
			long groupId, long categoryId, String s)
		throws PortalException, SystemException {

		String result = s;

		AssetCategory category = null;

		if (categoryId > 0) {
			try {
				category = AssetCategoryLocalServiceUtil.getCategory(
					categoryId);
			}
			catch (NoSuchCategoryException nsce) {
			}
		}

		if (category != null) {
			List<AssetCategoryProperty> categoryProperties =
				AssetCategoryPropertyLocalServiceUtil.getCategoryProperties(
					categoryId);

			for (AssetCategoryProperty categoryProperty : categoryProperties) {
				result = StringUtil.replace(
					result, "[$" + categoryProperty.getKey() + "$]",
					categoryProperty.getValue());
			}
		}

		return StringUtil.stripBetween(result, "[$", "$]");
	}

	public static String substituteTagPropertyVariables(
			long groupId, String tagName, String s)
		throws PortalException, SystemException {

		String result = s;

		AssetTag tag = null;

		if (tagName != null) {
			try {
				tag = AssetTagLocalServiceUtil.getTag(groupId, tagName);
			}
			catch (NoSuchTagException nste) {
			}
		}

		if (tag != null) {
			List<AssetTagProperty> tagProperties =
				AssetTagPropertyLocalServiceUtil.getTagProperties(
					tag.getTagId());

			for (AssetTagProperty tagProperty : tagProperties) {
				result = StringUtil.replace(
					result, "[$" + tagProperty.getKey() + "$]",
					tagProperty.getValue());
			}
		}

		return StringUtil.stripBetween(result, "[$", "$]");
	}

	public static String toWord(String text) {
		if (Validator.isNull(text)) {
			return text;
		}
		else {
			char[] textCharArray = text.toCharArray();

			for (int i = 0; i < textCharArray.length; i++) {
				char c = textCharArray[i];

				for (char invalidChar : INVALID_CHARACTERS) {
					if (c == invalidChar) {
						textCharArray[i] = CharPool.SPACE;

						break;
					}
				}
			}

			return new String(textCharArray);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(AssetUtil.class);

}