/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.users.admin.web.internal.util;

import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author Drew Brokke
 */
public class CSSClasses {

	public static Builder builder(String... cssClasses) {
		Builder builder = new Builder();

		builder.add(cssClasses);

		return builder;
	}

	public static class Builder {

		public Builder add(String cssClass) {
			return _add(cssClass, true);
		}

		public Builder add(String... cssClasses) {
			for (String cssClass : cssClasses) {
				_add(cssClass, true);
			}

			return this;
		}

		public Builder add(String cssClass, boolean condition) {
			return _add(cssClass, condition);
		}

		public String build() {
			return _build();
		}

		private Builder() {
		}

		private Builder _add(String cssClass, boolean condition) {
			if (condition) {
				_list.add(cssClass);
			}

			return this;
		}

		private String _build() {
			ListUtil.distinct(_list, Comparator.naturalOrder());

			return StringUtil.merge(_list, StringPool.SPACE);
		}

		private final List<String> _list = new ArrayList<>();

	}

	private CSSClasses() {
	}

}