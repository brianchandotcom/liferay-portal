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

package com.liferay.lcs.portal;

/**
 * @author Riccardo Ferrari
 */
public enum SiteType {

	ORGANIZATION("organization-site", 0), SITE("site", 1);

	public static boolean isOrganization(int type) {
		if (ORGANIZATION.getType() == type) {
			return true;
		}

		return false;
	}

	public static boolean isSite(int type) {
		if (SITE.getType() == type) {
			return true;
		}

		return false;
	}

	public static SiteType toSiteType(int type) {
		for (SiteType siteType : values()) {
			if (type == siteType.getType()) {
				return siteType;
			}
		}

		return ORGANIZATION;
	}

	public String getLabel() {
		return _label;
	}

	public int getType() {
		return _type;
	}

	private SiteType(String label, int type) {
		_label = label;
		_type = type;
	}

	private final String _label;
	private final int _type;

}