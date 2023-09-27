/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.view.table.selectable;

import com.liferay.frontend.data.set.constants.FDSConstants;
import com.liferay.frontend.data.set.view.FDSView;
import com.liferay.petra.string.StringPool;

import java.util.Locale;

/**
 * @author Alessio Antonio Rendina
 */
public abstract class BaseSelectableTableFDSView implements FDSView {

	@Override
	public String getContentRenderer() {
		return FDSConstants.SELECTABLE_TABLE;
	}

	public abstract String getFirstColumnLabel(Locale locale);

	public abstract String getFirstColumnName();

	@Override
	public String getLabel() {
		return FDSConstants.SELECTABLE_TABLE;
	}

	@Override
	public String getName() {
		return FDSConstants.SELECTABLE_TABLE;
	}

	@Override
	public String getThumbnail() {
		return StringPool.BLANK;
	}

}