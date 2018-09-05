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

package com.liferay.oauth2.provider.web.internal.display.context;

import com.liferay.oauth2.provider.configuration.OAuth2ProviderConfiguration;
import com.liferay.oauth2.provider.scope.liferay.ApplicationDescriptorLocator;
import com.liferay.oauth2.provider.scope.liferay.ScopeDescriptorLocator;
import com.liferay.oauth2.provider.scope.liferay.ScopeLocator;
import com.liferay.oauth2.provider.scope.spi.application.descriptor.ApplicationDescriptor;
import com.liferay.oauth2.provider.service.OAuth2ApplicationService;
import com.liferay.oauth2.provider.web.internal.AssignableScopes;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletRequest;

/**
 * @author Stian Sigvartsen
 */
public class AssignScopesDisplayContext
	extends OAuth2AdminPortletDisplayContext {

	public AssignScopesDisplayContext(
		OAuth2ApplicationService oAuth2ApplicationService,
		OAuth2ProviderConfiguration oAuth2ProviderConfiguration,
		PortletRequest portletRequest, ThemeDisplay themeDisplay,
		ApplicationDescriptorLocator applicationDescriptorLocator,
		ScopeDescriptorLocator scopeDescriptorLocator,
		ScopeLocator scopeLocator) {

		super(
			oAuth2ApplicationService, oAuth2ProviderConfiguration,
			portletRequest, themeDisplay);

		_applicationDescriptorLocator = applicationDescriptorLocator;
		_locale = themeDisplay.getLocale();

		Set<String> scopeAliases = new HashSet<>(
			scopeLocator.getScopeAliases(themeDisplay.getCompanyId()));

		for (String scopeAlias : scopeAliases) {
			AssignableScopes assignableScopes = new AssignableScopes(
				applicationDescriptorLocator, _locale, scopeDescriptorLocator);

			assignableScopes.addLiferayOAuth2Scopes(
				scopeLocator.getLiferayOAuth2Scopes(
					themeDisplay.getCompanyId(), scopeAlias));

			_assignableScopesRelations.compute(
				assignableScopes,
				(key, existingValue) -> {
					if (existingValue != null) {
						existingValue._scopeAliases.add(scopeAlias);

						return existingValue;
					}

					return new Relations(Collections.singleton(scopeAlias));
				});

			Set<String> applicationNames =
				assignableScopes.getApplicationNames();

			if (applicationNames.size() > 1) {
				for (String applicationName : applicationNames) {
					Set<AssignableScopes> assignableScopesSet =
						_globalAssignableScopesByApplicationName.
							computeIfAbsent(
								applicationName, a -> new HashSet<>());

					assignableScopesSet.add(assignableScopes);
				}
			}
			else if (applicationNames.size() == 1) {
				Iterator<String> iterator = applicationNames.iterator();

				String applicationName = iterator.next();

				Set<AssignableScopes> assignableScopesSet =
					_localAssignableScopesByApplicationName.computeIfAbsent(
						applicationName, a -> new HashSet<>());

				assignableScopesSet.add(assignableScopes);
			}
		}
	}

	public String getApplicationDescription(String applicationName) {
		ApplicationDescriptor applicationDescriptor =
			_applicationDescriptorLocator.getApplicationDescriptor(
				applicationName);

		if (applicationDescriptor == null) {
			return applicationName;
		}

		return applicationDescriptor.describeApplication(_locale);
	}

	public Set<String> getApplicationNames() {
		Set<String> applicationNames = new HashSet<>();

		applicationNames.addAll(
			_globalAssignableScopesByApplicationName.keySet());
		applicationNames.addAll(
			_localAssignableScopesByApplicationName.keySet());

		return applicationNames;
	}

	public Map<String, String> getApplicationNamesDescriptions() {
		Map<String, String> applicationNamesDescriptions = new HashMap<>();

		for (String applicationName : getApplicationNames()) {
			applicationNamesDescriptions.put(
				applicationName, getApplicationDescription(applicationName));
		}

		return applicationNamesDescriptions;
	}

	public String getApplicationScopeDescription(
		String applicationName, AssignableScopes assignableScopes,
		String delimiter) {

		Set<String> applicationScopeDescription =
			assignableScopes.getApplicationScopeDescription(applicationName);

		Stream<String> stream = applicationScopeDescription.stream();

		List<String> scopesList = stream.sorted(
		).map(
			HtmlUtil::escape
		).collect(
			Collectors.toList()
		);

		if (ListUtil.isEmpty(scopesList)) {
			return StringPool.BLANK;
		}

		return StringUtil.merge(scopesList, delimiter);
	}

	public Map<AssignableScopes, Relations>
		getAssignableScopesRelations(String applicationName) {

		Set<AssignableScopes> localAssignableScopes =
			_localAssignableScopesByApplicationName.get(applicationName);

		Map<AssignableScopes, Relations> localRelations = new HashMap<>();

		if (localAssignableScopes != null) {
			localRelations.putAll(
				getAssignableScopesRelations(localAssignableScopes));
		}

		Set<AssignableScopes> globalAssignableScopes =
			_globalAssignableScopesByApplicationName.get(applicationName);

		if (globalAssignableScopes == null) {
			return localRelations;
		}

		Map<AssignableScopes, Relations> assignableScopesRelations =
			new HashMap<>(localRelations);

		for (AssignableScopes assignableScopes : globalAssignableScopes) {
			AssignableScopes applicationAssignableScopes =
				assignableScopes.getApplicationAssignableScopes(
					applicationName);

			for (Map.Entry<AssignableScopes, Relations> entry :
					localRelations.entrySet()) {

				if (assignableScopes.contains(entry.getKey())) {
					Relations relations = entry.getValue();

					relations._globalAssignableScopes.add(assignableScopes);
				}

				applicationAssignableScopes =
					applicationAssignableScopes.subtract(entry.getKey());
			}

			Relations relations = assignableScopesRelations.computeIfAbsent(
				applicationAssignableScopes, a -> new Relations());

			relations._globalAssignableScopes.add(assignableScopes);
		}

		return _normalize(assignableScopesRelations);
	}

	public Map<AssignableScopes, Relations>
		getGlobalAssignableScopesRelations() {

		Collection<Set<AssignableScopes>> assignableScopesCollection =
			_globalAssignableScopesByApplicationName.values();

		Stream<Set<AssignableScopes>> stream =
			assignableScopesCollection.stream();

		return stream.flatMap(
			Set::stream
		).collect(
			Collectors.toSet()
		).stream(
		).filter(
			_assignableScopesRelations::containsKey
		).collect(
			Collectors.toMap(
				Function.identity(), _assignableScopesRelations::get)
		);
	}

	public List<Map.Entry<String, String>>
		getSortedApplicationNamesDescriptions() {

		Map<String, String> applicationNamesDescriptions =
			getApplicationNamesDescriptions();

		List<Map.Entry<String, String>> applicationsNamesDescriptionsList =
			new ArrayList<>(applicationNamesDescriptions.entrySet());

		applicationsNamesDescriptionsList.sort(
			Comparator.comparing(Map.Entry::getValue));

		return applicationsNamesDescriptionsList;
	}

	public class Relations {

		public Relations() {
			_scopeAliases = new HashSet<>();
		}

		public Relations(Set<String> scopeAliases) {
			_scopeAliases = new HashSet<>(scopeAliases);
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}

			if ((o == null) || (getClass() != o.getClass())) {
				return false;
			}

			Relations relations = (Relations)o;

			if (Objects.equals(
					_globalAssignableScopes,
					relations._globalAssignableScopes) &&
				Objects.equals(_scopeAliases, relations._scopeAliases)) {

				return true;
			}

			return false;
		}

		public Set<String> getGlobalScopeAliases() {
			Stream<AssignableScopes> stream = _globalAssignableScopes.stream();

			return stream.map(
				_assignableScopesRelations::get
			).flatMap(
				relations -> {
					Set<String> scopeAliases = relations.getScopeAliases();

					return scopeAliases.stream();
				}
			).collect(
				Collectors.toSet()
			);
		}

		public Set<String> getScopeAliases() {
			return _scopeAliases;
		}

		@Override
		public int hashCode() {
			return Objects.hash(_globalAssignableScopes, _scopeAliases);
		}

		private Set<AssignableScopes> _globalAssignableScopes = new HashSet<>();
		private final Set<String> _scopeAliases;

	}

	protected Map<AssignableScopes, Relations> getAssignableScopesRelations(
		Set<AssignableScopes> assignableScopes) {

		Stream<AssignableScopes> assignableScopesStream =
			assignableScopes.stream();

		return assignableScopesStream.filter(
			_assignableScopesRelations::containsKey
		).collect(
			Collectors.toMap(
				Function.identity(), _assignableScopesRelations::get)
		);
	}

	private static <K, V> Map<V, K> _invertMap(Map<K, V> map) {
		Map<V, K> ret = new HashMap<>(map.size());

		for (Map.Entry<K, V> entry : map.entrySet()) {
			ret.put(entry.getValue(), entry.getKey());
		}

		return ret;
	}

	private Map<AssignableScopes, Relations> _normalize(
		Map<AssignableScopes, Relations> assignableScopesRelations) {

		Map<AssignableScopes, Relations> combinedAssignableScopesRelations =
			new HashMap<>();

		for (Map.Entry<AssignableScopes, Relations>
				assignableScopesRelationsEntry :
					assignableScopesRelations.entrySet()) {

			Relations relations = assignableScopesRelationsEntry.getValue();

			Set<String> scopeAliases = relations.getScopeAliases();

			AssignableScopes assignableScopes =
				assignableScopesRelationsEntry.getKey();

			// Preserve assignable scopes that are assigned an alias

			if ((scopeAliases != null) && !scopeAliases.isEmpty()) {
				combinedAssignableScopesRelations.put(
					assignableScopes, relations);

				continue;
			}

			// Reduce other assignable scopes down to individual
			// application scopes but keep the global assignable scopes
			// relations of each original assignable scopes

			Set<AssignableScopes> applicationScopedAssignableScopesSet =
				assignableScopes.splitByApplicationScopes();

			for (AssignableScopes applicationScopedAssignableScopes :
					applicationScopedAssignableScopesSet) {

				Relations combinedRelations =
					combinedAssignableScopesRelations.computeIfAbsent(
						applicationScopedAssignableScopes,
						__ -> new Relations());

				combinedRelations._globalAssignableScopes.addAll(
					relations._globalAssignableScopes);
			}
		}

		// Merge those with identical global assignable scopes relations

		HashMap<Relations, AssignableScopes> relationsAssignableScopes =
			new HashMap<>();

		for (Map.Entry<AssignableScopes, Relations> entry :
				combinedAssignableScopesRelations.entrySet()) {

			relationsAssignableScopes.compute(
				entry.getValue(),
				(key, existingValue) -> {
					if (existingValue != null) {
						return existingValue.add(entry.getKey());
					}

					return entry.getKey();
				});
		}

		return _invertMap(relationsAssignableScopes);
	}

	private final ApplicationDescriptorLocator _applicationDescriptorLocator;
	private Map<AssignableScopes, Relations> _assignableScopesRelations =
		new HashMap<>();
	private Map<String, Set<AssignableScopes>>
		_globalAssignableScopesByApplicationName = new HashMap<>();
	private Map<String, Set<AssignableScopes>>
		_localAssignableScopesByApplicationName = new HashMap<>();
	private final Locale _locale;

}