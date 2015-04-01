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

package com.liferay.journal.content.web.entries;

import com.liferay.journal.content.web.util.UserToolEntry;

import org.osgi.service.component.annotations.Component;
@Component(
	immediate = true, service = UserToolEntry.class
)
public class TXTConvertionSelectableEntry
	extends BaseConvertionSelectableEntry implements UserToolEntry {

	@Override
	public String getExtension() {
		return "txt";
	}

	@Override
	public String getIcon() {
		return "file-alt";
	}

	@Override
	public String getKey() {
		return "enableTXT";
	}

	@Override
	public String getLabel() {
		return "download-as-txt";
	}

	@Override
	public Double getWeight() {
		return 6.0;
	}

}