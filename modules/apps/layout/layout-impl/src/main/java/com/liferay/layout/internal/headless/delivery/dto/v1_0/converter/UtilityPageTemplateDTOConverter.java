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

package com.liferay.layout.internal.headless.delivery.dto.v1_0.converter;

import com.liferay.headless.delivery.dto.v1_0.UtilityPageTemplate;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntry;

/**
 * @author Bárbara Cabrera
 */
public class UtilityPageTemplateDTOConverter {

	public static UtilityPageTemplate toDTO(
		LayoutUtilityPageEntry layoutUtilityPageEntry) {

		return new UtilityPageTemplate() {
			{
				name = layoutUtilityPageEntry.getName();
			}
		};
	}

}