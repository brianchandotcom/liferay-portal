/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.menu.item.asset.vocabulary.internal.portlet.action;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.navigation.admin.constants.SiteNavigationAdminPortletKeys;

import jakarta.portlet.ResourceRequest;
import jakarta.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	property = {
		"jakarta.portlet.name=" + SiteNavigationAdminPortletKeys.SITE_NAVIGATION_ADMIN,
		"mvc.command.name=/navigation_menu/get_asset_vocabulary_details"
	},
	service = MVCResourceCommand.class
)
public class GetAssetVocabularyDetailsMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Group group = null;
		String scopeExternalReferenceCode = ParamUtil.getString(
			resourceRequest, "scopeExternalReferenceCode");

		if (Validator.isNull(scopeExternalReferenceCode)) {
			group = themeDisplay.getScopeGroup();
		}
		else {
			group = _groupLocalService.getGroupByExternalReferenceCode(
				scopeExternalReferenceCode, themeDisplay.getCompanyId());
		}

		long groupId = group.getGroupId();

		String externalReferenceCode = ParamUtil.getString(
			resourceRequest, "externalReferenceCode");

		try {
			AssetVocabulary assetVocabulary =
				_assetVocabularyLocalService.
					getAssetVocabularyByExternalReferenceCode(
						externalReferenceCode, groupId);

			Group finalGroup = group;

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"numberOfCategories", assetVocabulary.getCategoriesCount()
				).put(
					"siteName",
					() -> {
						if (assetVocabulary.getGroupId() ==
								themeDisplay.getCompanyGroupId()) {

							return _language.get(
								_portal.getHttpServletRequest(resourceRequest),
								"global");
						}

						return finalGroup.getDescriptiveName(
							themeDisplay.getLocale());
					}
				));
		}
		catch (PortalException portalException) {
			_log.error("Unable to get asset vocabulary", portalException);

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"error",
					_language.get(
						themeDisplay.getRequest(),
						"an-unexpected-error-occurred")));
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GetAssetVocabularyDetailsMVCResourceCommand.class);

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}