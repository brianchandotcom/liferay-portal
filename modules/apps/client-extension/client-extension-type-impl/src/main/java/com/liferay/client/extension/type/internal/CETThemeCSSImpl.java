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
import com.liferay.client.extension.type.CETThemeCSS;

/**
 * @author Iván Zaera Avellón
 */
public class CETThemeCSSImpl extends BaseCETImpl implements CETThemeCSS {

	public CETThemeCSSImpl(ClientExtensionEntry clientExtensionEntry) {
		super(clientExtensionEntry);
	}

	@Override
	public String getClayURL() {
		return getString("clayURL");
	}

	@Override
	public String getMainURL() {
		return getString("mainURL");
	}

}