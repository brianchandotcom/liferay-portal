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
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.NoSuchCategoryException;
import com.liferay.portlet.asset.NoSuchTagException;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetCategoryProperty;
import com.liferay.portlet.asset.model.AssetRenderer;
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

import javax.portlet.PortletMode;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Eduardo Garcia
 * @author Jorge Ferrer
 */
public class AssetImpl implements Asset {

	public Set<String> addLayoutTags(
		HttpServletRequest request, List<AssetTag> tags) {

		Set<String> layoutTags = getLayoutTagNames(request);

		for (AssetTag tag : tags) {
			layoutTags.add(tag.getName());
		}

		return layoutTags;
	}

	public void addPortletBreadcrumbEntries(
			long assetCategoryId, HttpServletRequest request,
			PortletURL portletURL)
		throws Exception {

		AssetCategory assetCategory = AssetCategoryLocalServiceUtil.getCategory(
			assetCategoryId);

		List<AssetCategory> ancestorCategories = assetCategory.getAncestors();

		Collections.reverse(ancestorCategories);

		for (AssetCategory ancestorCategory : ancestorCategories) {
			portletURL.setParameter(
				"categoryId", String.valueOf(ancestorCategory.getCategoryId()));

			addPortletBreadcrumbEntry(
				request, ancestorCategory.getTitleCurrentValue(),
				portletURL.toString());
		}

		portletURL.setParameter("categoryId", String.valueOf(assetCategoryId));

		addPortletBreadcrumbEntry(
			request, assetCategory.getTitleCurrentValue(),
			portletURL.toString());
	}

	public void addPortletBreadcrumbEntry(
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

	public String getAssetKeywords(String className, long classPK)
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

	public Set<String> getLayoutTagNames(HttpServletRequest request) {
		Set<String> tagNames = (Set<String>)request.getAttribute(
			WebKeys.ASSET_LAYOUT_TAG_NAMES);

		if (tagNames == null) {
			tagNames = new HashSet<String>();

			request.setAttribute(WebKeys.ASSET_LAYOUT_TAG_NAMES, tagNames);
		}

		return tagNames;
	}

	public PortletURL getURLEdit(
			AssetRenderer assetRenderer,
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			boolean checkPermissions, PortletURL redirectURL,
			WindowState windowState)
		throws Exception {

		LiferayPortletURL editPortletURL =
			(LiferayPortletURL)assetRenderer.getURLEdit(
				liferayPortletRequest, liferayPortletResponse);

		if (editPortletURL == null) {
			return null;
		}

		if (checkPermissions) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)liferayPortletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			Group stageableGroup = themeDisplay.getScopeGroup();

			if (stageableGroup.isLayout()) {
				Layout layout = themeDisplay.getLayout();

				stageableGroup = layout.getGroup();
			}

			if (!assetRenderer.hasEditPermission(
					themeDisplay.getPermissionChecker()) ||
				stageableGroup.hasStagingGroup()) {

				return null;
			}

			editPortletURL.setWindowState(windowState);
			editPortletURL.setPortletMode(PortletMode.VIEW);

			PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

			String portletResource = ParamUtil.getString(
				liferayPortletRequest, "portletResource",
				portletDisplay.getId());

			editPortletURL.setParameter(
				"referringPortletResource", portletResource);
			editPortletURL.setParameter("redirect", redirectURL.toString());
			editPortletURL.setParameter(
				"originalRedirect", redirectURL.toString());
			editPortletURL.setDoAsGroupId(assetRenderer.getGroupId());
			editPortletURL.setRefererPlid(themeDisplay.getPlid());
		}

		return editPortletURL;
	}

	public boolean isValidWord(String word) {
		if (Validator.isNull(word)) {
			return false;
		}
		else {
			char[] wordCharArray = word.toCharArray();

			for (char c : wordCharArray) {
				for (char invalidChar : AssetUtil.INVALID_CHARACTERS) {
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

	public String substituteCategoryPropertyVariables(
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

	public String substituteTagPropertyVariables(
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

	public String toWord(String text) {
		if (Validator.isNull(text)) {
			return text;
		}
		else {
			char[] textCharArray = text.toCharArray();

			for (int i = 0; i < textCharArray.length; i++) {
				char c = textCharArray[i];

				for (char invalidChar : AssetUtil.INVALID_CHARACTERS) {
					if (c == invalidChar) {
						textCharArray[i] = CharPool.SPACE;

						break;
					}
				}
			}

			return new String(textCharArray);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(AssetImpl.class);

}