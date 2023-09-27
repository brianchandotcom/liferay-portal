/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Brian Wing Shun Chan
 */
@JSON(strict = true)
public class RegionImpl extends RegionBaseImpl {

	@JSON
	@Override
	public String getTitle() {
		String title = getTitle(
			LocaleUtil.toLanguageId(getLocale(getTitleCurrentLanguageId())),
			true);

		if (Validator.isNotNull(title)) {
			return title;
		}

		return getName();
	}

	@Override
	public String getTitle(String languageId, boolean useDefault) {
		String title = super.getTitle(languageId, useDefault);

		if (Validator.isNotNull(title)) {
			return title;
		}

		return getName();
	}

	@Override
	public String getTitleCurrentLanguageId() {
		return _titleCurrentLanguageId;
	}

	@Override
	public void setTitleCurrentLanguageId(String languageId) {
		_titleCurrentLanguageId = languageId;
	}

	private String _titleCurrentLanguageId;

}