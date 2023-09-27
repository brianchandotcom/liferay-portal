/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.admin.web.internal.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.site.admin.web.internal.constants.SiteAdminConstants;
import com.liferay.site.initializer.SiteInitializer;

import java.util.Locale;

/**
 * @author Marco Leo
 */
public class SiteInitializerItem {

	public SiteInitializerItem(
		LayoutSetPrototype layoutSetPrototype, Locale locale) {

		_siteInitializerKey = StringPool.BLANK;
		_icon = StringPool.BLANK;
		_layoutSetPrototypeId = layoutSetPrototype.getLayoutSetPrototypeId();
		_name = layoutSetPrototype.getName(locale);
		_type = SiteAdminConstants.CREATION_TYPE_SITE_TEMPLATE;
	}

	public SiteInitializerItem(SiteInitializer siteInitializer, Locale locale) {
		_siteInitializerKey = siteInitializer.getKey();
		_icon = siteInitializer.getThumbnailSrc();
		_layoutSetPrototypeId = 0;
		_name = siteInitializer.getName(locale);
		_type = SiteAdminConstants.CREATION_TYPE_INITIALIZER;
	}

	public String getIcon() {
		return _icon;
	}

	public String getKey() {
		if (isCreationTypeInitializer()) {
			return _siteInitializerKey;
		}

		return String.valueOf(_layoutSetPrototypeId);
	}

	public long getLayoutSetPrototypeId() {
		return _layoutSetPrototypeId;
	}

	public String getName() {
		return _name;
	}

	public String getSiteInitializerKey() {
		return _siteInitializerKey;
	}

	public String getType() {
		return _type;
	}

	public boolean isCreationTypeInitializer() {
		if (_type.equals(SiteAdminConstants.CREATION_TYPE_INITIALIZER)) {
			return true;
		}

		return false;
	}

	public boolean isCreationTypeSiteTemplate() {
		return !isCreationTypeInitializer();
	}

	private final String _icon;
	private final long _layoutSetPrototypeId;
	private final String _name;
	private final String _siteInitializerKey;
	private final String _type;

}