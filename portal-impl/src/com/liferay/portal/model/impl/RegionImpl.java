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