/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	private List<ThemeCompanyId> _excludes = new ArrayList<>();
	private List<ThemeCompanyId> _includes = new ArrayList<>();

}