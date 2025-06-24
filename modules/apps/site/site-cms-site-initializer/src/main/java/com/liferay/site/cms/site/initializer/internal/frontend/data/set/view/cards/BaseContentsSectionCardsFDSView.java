/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.frontend.data.set.view.cards;

import com.liferay.petra.string.StringPool;

/**
 * @author Roberto Díaz
 */
public abstract class BaseContentsSectionCardsFDSView
	extends BaseSectionCardsFDSView {

	@Override
	public String getDescription() {
		return StringPool.BLANK;
	}

	@Override
	public String getTitle() {
		return "embedded.title";
	}

}