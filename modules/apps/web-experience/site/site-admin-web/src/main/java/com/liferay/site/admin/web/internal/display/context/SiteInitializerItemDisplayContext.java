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

package com.liferay.site.admin.web.internal.display.context;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.site.admin.web.internal.constants.SiteAdminConstants;
import com.liferay.site.initializer.GroupInitializer;

import java.util.Locale;

/**
 * @author Marco Leo
 */
public class SiteInitializerItemDisplayContext {

	public SiteInitializerItemDisplayContext(
		GroupInitializer groupInitializer, Locale locale) {

		_groupInitializerKey = groupInitializer.getKey();
		_icon = groupInitializer.getThumbnailSrc();
		_layoutSetPrototypeId = 0;
		_name = groupInitializer.getName(locale);
		_type = SiteAdminConstants.CREATION_TYPE_INITIALIZER;
	}

	public SiteInitializerItemDisplayContext(
		LayoutSetPrototype layoutSetPrototype, Locale locale) {

		_groupInitializerKey = StringPool.BLANK;
		_icon = StringPool.BLANK;
		_layoutSetPrototypeId = layoutSetPrototype.getLayoutSetPrototypeId();
		_name = layoutSetPrototype.getName(locale);
		_type = SiteAdminConstants.CREATION_TYPE_SITE_TEMPLATE;
	}

	public String getGroupInitializerKey() {
		return _groupInitializerKey;
	}

	public String getIcon() {
		return _icon;
	}

	public String getKey() {
		if (isCreationTypeInitializer()) {
			return _groupInitializerKey;
		}

		return String.valueOf(_layoutSetPrototypeId);
	}

	public long getLayoutSetPrototypeId() {
		return _layoutSetPrototypeId;
	}

	public String getName() {
		return _name;
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

	private final String _groupInitializerKey;
	private final String _icon;
	private final long _layoutSetPrototypeId;
	private final String _name;
	private final String _type;

}