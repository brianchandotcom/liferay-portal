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
	property = {
		"default=true", "delimiter=" + StringPool.PERIOD, "type=chunks"
	},
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