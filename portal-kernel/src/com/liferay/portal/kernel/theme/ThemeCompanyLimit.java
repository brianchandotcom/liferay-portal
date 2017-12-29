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

package com.liferay.portal.kernel.theme;

import com.liferay.portal.kernel.util.GetterUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Brian Wing Shun Chan
 */
public class ThemeCompanyLimit implements Serializable {

	public ThemeCompanyLimit() {
		_includes = new ArrayList<>();
		_excludes = new ArrayList<>();
	}

	public List<ThemeCompanyId> getExcludes() {
		return _excludes;
	}

	public List<ThemeCompanyId> getIncludes() {
		return _includes;
	}

	public boolean isExcluded(long companyId) {
		return _matches(_excludes, companyId);
	}

	public boolean isIncluded(long companyId) {
		return _matches(_includes, companyId);
	}

	public void setExcludes(List<? extends ThemeCompanyId> excludes) {
		_excludes = (List<ThemeCompanyId>)excludes;
	}

	public void setIncludes(List<? extends ThemeCompanyId> includes) {
		_includes = (List<ThemeCompanyId>)includes;
	}

	private boolean _matches(
		List<ThemeCompanyId> themeCompanyIds, long companyId) {

		for (ThemeCompanyId themeCompanyId : themeCompanyIds) {
			String themeCompanyIdValue = themeCompanyId.getValue();

			if (themeCompanyId.isPattern()) {
				Pattern pattern = Pattern.compile(themeCompanyIdValue);

				Matcher matcher = pattern.matcher(String.valueOf(companyId));

				if (matcher.matches()) {
					return true;
				}
			}
			else {
				long themeCompanyIdValueLong = GetterUtil.getLong(
					themeCompanyIdValue);

				if (themeCompanyIdValueLong == companyId) {
					return true;
				}
			}
		}

		return false;
	}

	private List<ThemeCompanyId> _excludes;
	private List<ThemeCompanyId> _includes;

}