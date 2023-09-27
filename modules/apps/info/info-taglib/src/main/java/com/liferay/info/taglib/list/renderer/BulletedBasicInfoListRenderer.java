/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.taglib.list.renderer;

import com.liferay.info.taglib.internal.list.renderer.BasicListInfoListStyle;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.Locale;

/**
 * @author Pavel Savinov
 */
public interface BulletedBasicInfoListRenderer<T>
	extends BasicInfoListRenderer<T> {

	@Override
	public default String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "bulleted-list");
	}

	@Override
	public default String getListStyle() {
		return BasicListInfoListStyle.BULLETED.getKey();
	}

}