/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.scope.internal.spi.scope.matcher;

import com.liferay.oauth2.provider.scope.spi.scope.matcher.ScopeMatcher;
import com.liferay.oauth2.provider.scope.spi.scope.matcher.ScopeMatcherFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Carlos Sierra Andrés
 */
@Component(
	property = {"default=true", "delimiter=.", "type=chunks"},
	service = ScopeMatcherFactory.class
)
public class ChunkScopeMatcherFactory implements ScopeMatcherFactory {

	@Override
	public ScopeMatcher create(String input) {
		String[] inputParts = StringUtil.split(input, _delimiter);

		if (inputParts.length == 0) {
			return ScopeMatcher.NONE;
		}

		return new ChunkScopeMatcher(inputParts);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_delimiter = MapUtil.getString(
			properties, "delimiter", StringPool.PERIOD);
	}

	private String _delimiter = StringPool.PERIOD;

	private class ChunkScopeMatcher implements ScopeMatcher {

		@Override
		public boolean match(String name) {
			String[] nameParts = StringUtil.split(name, _delimiter);

			if (nameParts.length < _inputParts.length) {
				return false;
			}

			for (int i = 0; i < _inputParts.length; i++) {
				if (_inputParts[i].equals(nameParts[i])) {
					continue;
				}

				return false;
			}

			return true;
		}

		private ChunkScopeMatcher(String[] inputParts) {
			_inputParts = inputParts;
		}

		private final String[] _inputParts;

	}

}