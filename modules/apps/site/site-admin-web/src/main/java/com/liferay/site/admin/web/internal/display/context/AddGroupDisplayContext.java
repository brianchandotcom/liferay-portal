/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.admin.web.internal.display.context;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.admin.web.internal.constants.SiteAdminConstants;

import java.util.List;
import java.util.Objects;

import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Lourdes Fernández Besada
 */
public class AddGroupDisplayContext {

	public AddGroupDisplayContext(
		boolean disablePrivateLayouts, HttpServletRequest httpServletRequest,
		RenderResponse renderResponse) {

		_disablePrivateLayouts = disablePrivateLayouts;
		_httpServletRequest = httpServletRequest;
		_renderResponse = renderResponse;
	}

	public String getAddGroupURL() {
		return PortletURLBuilder.createActionURL(
			_renderResponse
		).setActionName(
			"/site_admin/add_group"
		).setBackURL(
			ParamUtil.getString(
				_httpServletRequest, "backURL",
				ParamUtil.getString(_httpServletRequest, "redirect"))
		).setParameter(
			"creationType",
			ParamUtil.getString(_httpServletRequest, "creationType")
		).setParameter(
			"layoutSetPrototypeId",
			ParamUtil.getLong(_httpServletRequest, "layoutSetPrototypeId")
		).setParameter(
			"parentGroupId", _getParentGroupId()
		).setParameter(
			"siteInitializerKey",
			ParamUtil.getString(_httpServletRequest, "siteInitializerKey")
		).buildString();
	}

	public long[] getGroupIds() {
		if (_groupsIds != null) {
			return _groupsIds;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long[] groupsIds = {themeDisplay.getCompanyGroupId()};

		if (_getParentGroupId() > 0) {
			try {
				groupsIds = PortalUtil.getCurrentAndAncestorSiteGroupIds(
					_getParentGroupId());
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}

				return groupsIds;
			}
		}

		_groupsIds = groupsIds;

		return _groupsIds;
	}

	public boolean hasRequiredVocabularies() {
		long classNameId = PortalUtil.getClassNameId(Group.class);

		List<AssetVocabulary> assetVocabularies =
			AssetVocabularyServiceUtil.getGroupVocabularies(getGroupIds());

		for (AssetVocabulary assetVocabulary : assetVocabularies) {
			if (assetVocabulary.isAssociatedToClassNameId(classNameId) &&
				assetVocabulary.isRequired(classNameId, 0)) {

				return true;
			}
		}

		return false;
	}

	public boolean isShowCategorization() {
		long classNameId = PortalUtil.getClassNameId(Group.class);

		List<AssetVocabulary> assetVocabularies =
			AssetVocabularyServiceUtil.getGroupVocabularies(getGroupIds());

		for (AssetVocabulary assetVocabulary : assetVocabularies) {
			if (assetVocabulary.isAssociatedToClassNameId(classNameId) &&
				assetVocabulary.isRequired(classNameId, 0)) {

				int assetVocabularyCategoriesCount =
					AssetCategoryServiceUtil.getVocabularyCategoriesCount(
						assetVocabulary.getGroupId(),
						assetVocabulary.getVocabularyId());

				if (assetVocabularyCategoriesCount > 0) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean isShowLayoutSetVisibilityPrivateCheckbox() {
		if (_disablePrivateLayouts) {
			return false;
		}

		if (Objects.equals(
				ParamUtil.getString(_httpServletRequest, "creationType"),
				SiteAdminConstants.CREATION_TYPE_SITE_TEMPLATE)) {

			return true;
		}

		return false;
	}

	private long _getParentGroupId() {
		if (_parentGroupId != null) {
			return _parentGroupId;
		}

		_parentGroupId = ParamUtil.getLong(
			_httpServletRequest, "parentGroupId");

		return _parentGroupId;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddGroupDisplayContext.class);

	private final boolean _disablePrivateLayouts;
	private long[] _groupsIds;
	private final HttpServletRequest _httpServletRequest;
	private Long _parentGroupId;
	private final RenderResponse _renderResponse;

}