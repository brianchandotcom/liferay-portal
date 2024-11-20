/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.converter;

import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.friendly.url.util.comparator.FriendlyURLEntryLocalizationComparator;
import com.liferay.headless.admin.site.dto.v1_0.FriendlyUrlHistory;
import com.liferay.layout.friendly.url.LayoutFriendlyURLEntryHelper;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "dto.class.name=com.liferay.portal.kernel.model.Layout",
	service = DTOConverter.class
)
public class FriendlyURLHistoryDTOConverter
	implements DTOConverter<Layout, FriendlyUrlHistory> {

	@Override
	public String getContentType() {
		return FriendlyUrlHistory.class.getSimpleName();
	}

	@Override
	public FriendlyUrlHistory toDTO(
			DTOConverterContext dtoConverterContext, Layout layout)
		throws Exception {

		return new FriendlyUrlHistory() {
			{
				setFriendlyUrlPath_i18n(
					() -> _getFriendlyUrlPathJSONObject(layout));
			}
		};
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

	private final FriendlyURLEntryLocalizationComparator
		_friendlyURLEntryLocalizationComparator =
			FriendlyURLEntryLocalizationComparator.getInstance(false);

	@Reference
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private LayoutFriendlyURLEntryHelper _layoutFriendlyURLEntryHelper;

}