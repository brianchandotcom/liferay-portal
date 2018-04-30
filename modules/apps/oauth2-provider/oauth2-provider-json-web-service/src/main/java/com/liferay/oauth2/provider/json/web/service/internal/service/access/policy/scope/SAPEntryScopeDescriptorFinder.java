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

package com.liferay.oauth2.provider.json.web.service.internal.service.access.policy.scope;

import com.liferay.oauth2.provider.scope.spi.scope.descriptor.ScopeDescriptor;
import com.liferay.oauth2.provider.scope.spi.scope.finder.ScopeFinder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Tomas Polesovsky
 */
public class SAPEntryScopeDescriptorFinder
	implements ScopeDescriptor, ScopeFinder {

	public SAPEntryScopeDescriptorFinder(List<SAPEntryScope> sapEntryScopes) {
		for (SAPEntryScope sapEntryScope : sapEntryScopes) {
			_sapEntryScopesMap.put(sapEntryScope.getScope(), sapEntryScope);
		}
	}

	@Override
	public String describeScope(String scope, Locale locale) {
		SAPEntryScope sapEntryScope = _sapEntryScopesMap.get(scope);

		if (sapEntryScope == null) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to locate SAPEntry scope " + scope);
			}

			return StringPool.BLANK;
		}

		return sapEntryScope.getTitle(locale);
	}

	@Override
	public Collection<String> findScopes() {
		return new HashSet<>(_sapEntryScopesMap.keySet());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SAPEntryScopeDescriptorFinder.class);

	private final Map<String, SAPEntryScope> _sapEntryScopesMap =
		new HashMap<>();

}