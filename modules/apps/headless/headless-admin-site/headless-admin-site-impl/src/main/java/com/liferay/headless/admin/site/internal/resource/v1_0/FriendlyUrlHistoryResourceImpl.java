/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0;

import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.friendly.url.util.comparator.FriendlyURLEntryLocalizationComparator;
import com.liferay.headless.admin.site.dto.v1_0.FriendlyUrlHistory;
import com.liferay.headless.admin.site.dto.v1_0.SitePage;
import com.liferay.headless.admin.site.internal.resource.util.GroupUtil;
import com.liferay.headless.admin.site.resource.v1_0.FriendlyUrlHistoryResource;
import com.liferay.layout.friendly.url.LayoutFriendlyURLEntryHelper;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.vulcan.fields.NestedField;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Rubén Pulido
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/friendly-url-history.properties",
	property = "nested.field.support=true", scope = ServiceScope.PROTOTYPE,
	service = FriendlyUrlHistoryResource.class
)
public class FriendlyUrlHistoryResourceImpl
	extends BaseFriendlyUrlHistoryResourceImpl {

	@NestedField(parentClass = SitePage.class, value = "friendlyUrlHistory")
	@Override
	public FriendlyUrlHistory
			getSiteSiteByExternalReferenceCodeSitePageFriendlyUrlHistory(
				String siteExternalReferenceCode,
				String sitePageExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		return _toFriendlyUrlHistory(
			_layoutLocalService.getLayoutByExternalReferenceCode(
				sitePageExternalReferenceCode,
				GroupUtil.getGroupId(
					true, contextCompany.getCompanyId(),
					siteExternalReferenceCode)));
	}

	private JSONObject _getFriendlyUrlPathJSONObject(Layout layout)
		throws Exception {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		long classNameId = _layoutFriendlyURLEntryHelper.getClassNameId(
			layout.isPrivateLayout());

		for (String languageId : layout.getAvailableLanguageIds()) {
			jsonObject.put(
				LocaleUtil.toBCP47LanguageId(languageId),
				JSONUtil.toJSONArray(
					_friendlyURLEntryLocalService.
						getFriendlyURLEntryLocalizations(
							layout.getGroupId(), classNameId, layout.getPlid(),
							languageId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
							_friendlyURLEntryLocalizationComparator),
					friendlyURLEntryLocalization ->
						friendlyURLEntryLocalization.getUrlTitle()));
		}

		return jsonObject;
	}

	private FriendlyUrlHistory _toFriendlyUrlHistory(Layout layout)
		throws Exception {

		return new FriendlyUrlHistory() {
			{
				setFriendlyUrlPath_i18n(
					() -> _getFriendlyUrlPathJSONObject(layout));
			}
		};
	}

	private final FriendlyURLEntryLocalizationComparator
		_friendlyURLEntryLocalizationComparator =
			FriendlyURLEntryLocalizationComparator.getInstance(false);

	@Reference
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private LayoutFriendlyURLEntryHelper _layoutFriendlyURLEntryHelper;

	@Reference
	private LayoutLocalService _layoutLocalService;

}