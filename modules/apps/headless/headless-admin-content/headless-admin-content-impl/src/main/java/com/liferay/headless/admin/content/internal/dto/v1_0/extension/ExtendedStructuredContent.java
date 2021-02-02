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

package com.liferay.headless.admin.content.internal.dto.v1_0.extension;

import com.liferay.headless.admin.content.dto.v1_0.Version;
import com.liferay.headless.delivery.dto.v1_0.StructuredContent;

import javax.validation.constraints.NotNull;

/**
 * @author Luis Miguel Barcos
 */
public class ExtendedStructuredContent extends StructuredContent {

	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
		this.version = version;
	}

	@NotNull
	protected Version version;

}