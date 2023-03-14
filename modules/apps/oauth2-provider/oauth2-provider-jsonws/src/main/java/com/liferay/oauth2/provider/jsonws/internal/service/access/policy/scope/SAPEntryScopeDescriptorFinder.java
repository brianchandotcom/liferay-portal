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

package com.liferay.oauth2.provider.jsonws.internal.service.access.policy.scope;

import com.liferay.oauth2.provider.scope.spi.scope.descriptor.ScopeDescriptor;
import com.liferay.oauth2.provider.scope.spi.scope.finder.ScopeFinder;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.security.service.access.policy.model.SAPEntry;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

/**
 * @author Tomas Polesovsky
 */
public class SAPEntryScopeDescriptorFinder
	implements ScopeDescriptor, ScopeFinder {

	public SAPEntryScopeDescriptorFinder(
		Supplier<List<SAPEntryScope>> sapEntryScopesSupplier,
		ScopeDescriptor defaultScopeDescriptor) {

		_sapEntryScopesSupplier = sapEntryScopesSupplier;
		_defaultScopeDescriptor = defaultScopeDescriptor;
	}

	@Override
	public String describeScope(String scope, Locale locale) {
		_initialize();

		SAPEntryScope sapEntryScope = _sapEntryScopes.get(scope);

		if (sapEntryScope == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to get SAP entry scope " + scope);
			}

			return _defaultScopeDescriptor.describeScope(scope, locale);
		}

		return sapEntryScope.getTitle(locale);
	}

	@Override
	public Collection<String> findScopes() {
		_initialize();

		return new HashSet<>(_scopes);
	}

	public Collection<SAPEntryScope> getSAPEntryScopes() {
		_initialize();

		return _sapEntryScopes.values();
	}

	private void _initialize() {
		if (_initialized) {
			return;
		}

		synchronized (this) {
			if (_initialized) {
				return;
			}

			for (SAPEntryScope sapEntryScope : _sapEntryScopesSupplier.get()) {
				SAPEntry sapEntry = sapEntryScope.getSAPEntry();

				if (sapEntry.isEnabled()) {
					_scopes.add(sapEntryScope.getScope());
				}

				_sapEntryScopes.put(sapEntryScope.getScope(), sapEntryScope);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SAPEntryScopeDescriptorFinder.class);

	private final ScopeDescriptor _defaultScopeDescriptor;
	private volatile boolean _initialized;
	private final Map<String, SAPEntryScope> _sapEntryScopes = new HashMap<>();
	private final Supplier<List<SAPEntryScope>> _sapEntryScopesSupplier;
	private final Set<String> _scopes = new HashSet<>();

}