/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.taglib.internal.util;

import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.layout.item.selector.LayoutItemSelectorReturnType;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.security.auth.AuthTokenUtil;
import com.liferay.portal.kernel.service.LayoutServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class LayoutUtil {

	public static JSONArray getLayoutsJSONArray(
			boolean checkDisplayPage, boolean enableCurrentPage, long groupId,
			HttpServletRequest httpServletRequest,
			String itemSelectorReturnType, boolean privateLayout,
			long parentLayoutId, String[] selectedLayoutUuid, int start,
			int end)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<Layout> layouts = LayoutServiceUtil.getLayouts(
			groupId, privateLayout, parentLayoutId, false, start, end);

		for (Layout layout : layouts) {
			if (_isExcludedLayout(layout)) {
				continue;
			}

			JSONArray childrenJSONArray = getLayoutsJSONArray(
				checkDisplayPage, enableCurrentPage, groupId,
				httpServletRequest, itemSelectorReturnType, privateLayout,
				layout.getLayoutId(), selectedLayoutUuid, 0,
				GetterUtil.getInteger(
					PropsValues.LAYOUT_MANAGE_PAGES_INITIAL_CHILDREN));

			jsonArray.put(
				JSONUtil.put(
					"children",
					() -> {
						if (childrenJSONArray.length() > 0) {
							return childrenJSONArray;
						}

						return null;
					}
				).put(
					"disabled",
					() -> {
						if ((checkDisplayPage &&
							 !layout.isContentDisplayPage()) ||
							(!enableCurrentPage &&
							 (layout.getPlid() == _getSelPlid(
								 httpServletRequest)))) {

							return true;
						}

						return null;
					}
				).put(
					"groupId", layout.getGroupId()
				).put(
					"hasChildren", layout.hasChildren()
				).put(
					"icon", layout.getIcon()
				).put(
					"id", layout.getUuid()
				).put(
					"layoutId", layout.getLayoutId()
				).put(
					"name", layout.getName(themeDisplay.getLocale())
				).put(
					"paginated",
					() -> {
						int layoutsCount = LayoutServiceUtil.getLayoutsCount(
							groupId, layout.isPrivateLayout(),
							layout.getLayoutId());

						if (layoutsCount >
								PropsValues.
									LAYOUT_MANAGE_PAGES_INITIAL_CHILDREN) {

							return true;
						}

						return false;
					}
				).put(
					"payload",
					_getPayload(
						httpServletRequest, itemSelectorReturnType, layout,
						themeDisplay)
				).put(
					"privateLayout", layout.isPrivateLayout()
				).put(
					"returnType", itemSelectorReturnType
				).put(
					"selected",
					() -> {
						if (ArrayUtil.contains(
								selectedLayoutUuid, layout.getUuid())) {

							return true;
						}

						return null;
					}
				).put(
					"url",
					PortalUtil.getLayoutRelativeURL(layout, themeDisplay, false)
				).put(
					"value", layout.getBreadcrumb(themeDisplay.getLocale())
				));
		}

		return jsonArray;
	}

	private static String _getPayload(
			HttpServletRequest httpServletRequest,
			String itemSelectorReturnType, Layout layout,
			ThemeDisplay themeDisplay)
		throws Exception {

		if (Objects.equals(
				LayoutItemSelectorReturnType.class.getName(),
				itemSelectorReturnType)) {

			return JSONUtil.put(
				"layoutId", layout.getLayoutId()
			).put(
				"name", layout.getName(themeDisplay.getLocale())
			).put(
				"plid", layout.getPlid()
			).put(
				"previewURL",
				() -> {
					String layoutURL = HttpComponentsUtil.addParameter(
						PortalUtil.getLayoutFullURL(layout, themeDisplay),
						"p_l_mode", Constants.PREVIEW);

					return HttpComponentsUtil.addParameter(
						layoutURL, "p_p_auth",
						AuthTokenUtil.getToken(httpServletRequest));
				}
			).put(
				"private", layout.isPrivateLayout()
			).put(
				"url", PortalUtil.getLayoutFullURL(layout, themeDisplay)
			).put(
				"uuid", layout.getUuid()
			).toString();
		}
		else if (Objects.equals(
					UUIDItemSelectorReturnType.class.getName(),
					itemSelectorReturnType)) {

			return layout.getUuid();
		}

		return PortalUtil.getLayoutRelativeURL(layout, themeDisplay, false);
	}

	private static long _getSelPlid(HttpServletRequest httpServletRequest) {
		return ParamUtil.getLong(
			httpServletRequest, "selPlid", LayoutConstants.DEFAULT_PLID);
	}

	private static boolean _isExcludedLayout(Layout layout) {
		if (!layout.isTypeContent()) {
			return false;
		}

		if (layout.fetchDraftLayout() != null) {
			return !layout.isPublished();
		}

		if (layout.isApproved() && !layout.isHidden() && !layout.isSystem()) {
			return false;
		}

		return true;
	}

}