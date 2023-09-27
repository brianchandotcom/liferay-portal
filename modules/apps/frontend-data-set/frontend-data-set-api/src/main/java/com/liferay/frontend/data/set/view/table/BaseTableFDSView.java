/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.view.table;

import com.liferay.frontend.data.set.constants.FDSConstants;
import com.liferay.frontend.data.set.view.FDSView;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Marco Leo
 */
public abstract class BaseTableFDSView implements FDSView {

	@Override
	public String getContentRenderer() {
		return FDSConstants.TABLE;
	}

	@Override
	public String getLabel() {
		return FDSConstants.TABLE;
	}

	@Override
	public String getName() {
		return FDSConstants.TABLE;
	}

	public ResourceBundle getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());
	}

	@Override
	public String getThumbnail() {
		return FDSConstants.TABLE;
	}

	public boolean isQuickActionsEnabled() {
		return false;
	}

}