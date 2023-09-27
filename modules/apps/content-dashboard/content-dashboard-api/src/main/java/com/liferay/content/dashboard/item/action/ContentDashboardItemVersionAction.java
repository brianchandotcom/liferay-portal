/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.item.action;

import java.util.Locale;

/**
 * @author Stefan Tanasie
 */
public interface ContentDashboardItemVersionAction {

	public String getIcon();

	public String getLabel(Locale locale);

	public String getName();

	public default Type getType() {
		return Type.SUBMIT_FORM;
	}

	public String getURL();

	public enum Type {

		BLANK, NAVIGATE, SUBMIT_FORM

	}

}