/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.taglib.internal.display.context;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagServiceUtil;
import com.liferay.asset.util.comparator.AssetTagCountComparator;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

/**
 * @author Mikel Lorza
 */
public class AssetTagsNavigationDisplayContext {

	public AssetTagsNavigationDisplayContext() {
	}

	public String buildTagsNavigation(
			long scopeGroupId, String selectedTagName, long classNameId,
			String displayStyle, int maxAssetTags,
			RenderResponse renderResponse, boolean showAssetCount,
			boolean showZeroAssetCount)
		throws Exception {

		List<AssetTag> tags = null;

		if (showAssetCount && (classNameId > 0)) {
			tags = AssetTagServiceUtil.getTags(
				PortalUtil.getSiteGroupId(scopeGroupId), classNameId, null, 0,
				maxAssetTags, new AssetTagCountComparator());
		}
		else {
			tags = AssetTagServiceUtil.getGroupTags(
				PortalUtil.getSiteGroupId(scopeGroupId), 0, maxAssetTags,
				new AssetTagCountComparator());
		}

		if (tags.isEmpty()) {
			return null;
		}

		tags = ListUtil.sort(tags);

		StringBundler sb = new StringBundler();

		sb.append("<ul class=\"tag-items ");

		int maxCount = 1;
		int minCount = 1;

		if (displayStyle.equals("cloud")) {
			sb.append("tag-cloud");

			for (AssetTag tag : tags) {
				String tagName = tag.getName();

				int count = AssetTagServiceUtil.getVisibleAssetsTagsCount(
					scopeGroupId, classNameId, tagName);

				if (!showZeroAssetCount && (count == 0)) {
					continue;
				}

				maxCount = Math.max(maxCount, count);
				minCount = Math.min(minCount, count);
			}
		}
		else {
			sb.append("tag-list");
		}

		sb.append("\">");

		double multiplier = 1;

		if (maxCount != minCount) {
			multiplier = (double)5 / (maxCount - minCount);
		}

		for (AssetTag tag : tags) {
			String tagName = tag.getName();

			int count = AssetTagServiceUtil.getVisibleAssetsTagsCount(
				scopeGroupId, classNameId, tagName);

			int popularity =
				(int)
					(1 +
						((maxCount - (maxCount - (count - minCount))) *
							multiplier));

			if (!showZeroAssetCount && (count == 0)) {
				continue;
			}

			sb.append("<li class=\"tag-popularity-");
			sb.append(popularity);
			sb.append("\"><span>");

			if (tagName.equals(selectedTagName)) {
				sb.append("<a class=\"tag-selected\" href=\"");

				PortletURL portletURL = PortletURLBuilder.createRenderURL(
					renderResponse
				).setParameter(
					"tag", StringPool.BLANK
				).buildPortletURL();

				sb.append(HtmlUtil.escape(portletURL.toString()));
			}
			else {
				sb.append("<a href=\"");

				PortletURL portletURL = PortletURLBuilder.createRenderURL(
					renderResponse
				).setParameter(
					"tag", tagName
				).buildPortletURL();

				sb.append(HtmlUtil.escape(portletURL.toString()));
			}

			sb.append("\">");
			sb.append(tagName);

			if (showAssetCount) {
				sb.append("<span class=\"tag-asset-count\">");
				sb.append(StringPool.SPACE);
				sb.append(StringPool.OPEN_PARENTHESIS);
				sb.append(count);
				sb.append(StringPool.CLOSE_PARENTHESIS);
				sb.append("</span>");
			}

			sb.append("</a></span></li>");
		}

		sb.append("</ul>");

		return sb.toString();
	}

}