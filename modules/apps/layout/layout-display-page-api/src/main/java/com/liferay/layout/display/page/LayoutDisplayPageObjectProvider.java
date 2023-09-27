/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.display.page;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.Locale;

/**
 * @author Jorge Ferrer
 */
public interface LayoutDisplayPageObjectProvider<T> {

	public default String getClassName() {
		return PortalUtil.getClassName(getClassNameId());
	}

	public long getClassNameId();

	public long getClassPK();

	public long getClassTypeId();

	public String getDescription(Locale locale);

	public T getDisplayObject();

	public default String getExternalReferenceCode() {
		return StringPool.BLANK;
	}

	public long getGroupId();

	public String getKeywords(Locale locale);

	public String getTitle(Locale locale);

	public String getURLTitle(Locale locale);

}