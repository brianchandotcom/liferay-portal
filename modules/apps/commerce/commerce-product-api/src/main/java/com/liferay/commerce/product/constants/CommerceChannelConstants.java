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

package com.liferay.commerce.product.constants;

/**
 * @author Alec Sloan
 */
public class CommerceChannelConstants {

	public static final String CHANNEL_TYPE_SITE = "site";

	public static final int SITE_TYPE_B2B = 1;

	public static final int SITE_TYPE_B2C = 0;

	public static final int SITE_TYPE_B2X = 2;
	
	public static final int[] SITE_TYPES = {
		SITE_TYPE_B2C, SITE_TYPE_B2B, SITE_TYPE_B2X
	};

	public static String getSiteTypeLabel(int siteType) {
		if (siteType == SITE_TYPE_B2C) {
			return "b2c";
		}
		else if (siteType == SITE_TYPE_B2B) {
			return "b2b";
		}
		else if (siteType == SITE_TYPE_B2X) {
			return "b2x";
		}

		return null;
	}
}