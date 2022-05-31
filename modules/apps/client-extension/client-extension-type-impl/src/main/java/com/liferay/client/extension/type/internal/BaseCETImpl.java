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

package com.liferay.client.extension.type.internal;

import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class BaseCETImpl {

	public BaseCETImpl(ClientExtensionEntry clientExtensionEntry) {
		if (clientExtensionEntry == null) {
			_unicodeProperties = new UnicodeProperties();
		}
		else {
			_unicodeProperties = UnicodePropertiesBuilder.load(
				clientExtensionEntry.getTypeSettings()
			).build();
		}
	}

	protected boolean getBoolean(String key) {
		return GetterUtil.getBoolean(_unicodeProperties.getProperty(key));
	}

	protected String getString(String key) {
		return GetterUtil.getString(_unicodeProperties.getProperty(key));
	}

	private final UnicodeProperties _unicodeProperties;

}