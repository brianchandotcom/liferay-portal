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

package com.liferay.portal.search.web.internal.layout.prototype;

import com.liferay.layout.page.template.util.LayoutPrototypeHelperUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutPrototypeLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.search.web.layout.prototype.SearchLayoutPrototypeCustomizer;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Adam Brandizzi
 * @author André de Oliveira
 * @author Lino Alves
 */
@Component(immediate = true, service = SearchLayoutFactory.class)
public class SearchLayoutFactoryImpl implements SearchLayoutFactory {

	@Override
	public void createSearchLayout(Group group) {
		if (!_shouldCreateSearchLayout(group)) {
			return;
		}

		Optional<LayoutPrototype> optional = _findSearchLayoutPrototype(
			group.getCompanyId());

		optional.ifPresent(
			layoutPrototype -> createSearchLayout(group, layoutPrototype));
	}

	@Override
	public void createSearchLayoutPrototype(Company company) {
		long companyId = company.getCompanyId();

		try {
			createSearchLayoutPrototype(
				companyId, userLocalService.getDefaultUserId(companyId),
				_getSearchTitleLocalizationMap(),
				_getSearchDescriptionLocalizationMap());
		}
		catch (RuntimeException runtimeException) {
			throw runtimeException;
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	protected void createSearchLayout(
		Group group, LayoutPrototype layoutPrototype) {

		try {
			Layout baseLayout = layoutPrototype.getLayout();

			createSearchLayout(group, layoutPrototype, baseLayout);
		}
		catch (RuntimeException runtimeException) {
			throw runtimeException;
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	protected void createSearchLayout(
			Group group, LayoutPrototype layoutPrototype, Layout baseLayout)
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAttribute(
			"layoutPrototypeLinkEnabled", Boolean.FALSE);

		serviceContext.setAttribute(
			"layoutPrototypeUuid", layoutPrototype.getUuid());

		serviceContext.setUserId(group.getCreatorUserId());

		layoutLocalService.addLayout(
			group.getCreatorUserId(), group.getGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			layoutPrototype.getNameMap(), baseLayout.getTitleMap(),
			layoutPrototype.getDescriptionMap(), baseLayout.getKeywordsMap(),
			baseLayout.getRobotsMap(), LayoutConstants.TYPE_PORTLET,
			baseLayout.getTypeSettings(), baseLayout.isPrivateLayout(),
			_getFriendlyURLMap(), serviceContext);

		UnicodeProperties unicodeProperties = group.getTypeSettingsProperties();

		unicodeProperties.put("searchLayoutCreated", "true");

		group.setTypeSettingsProperties(unicodeProperties);

		groupLocalService.updateGroup(group);

		if (_log.isInfoEnabled()) {
			_log.info("Search Page created");
		}
	}

	protected void createSearchLayoutPrototype(
			long companyId, long defaultUserId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap)
		throws Exception {

		String layoutTemplateId = getLayoutTemplateId();

		List<LayoutPrototype> layoutPrototypes =
			layoutPrototypeLocalService.search(
				companyId, null, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Layout layout = LayoutPrototypeHelperUtil.addLayoutPrototype(
			layoutPrototypeLocalService, companyId, defaultUserId, nameMap,
			descriptionMap, layoutTemplateId, layoutPrototypes);

		if (layout == null) {
			return;
		}

		customize(layout);

		if (_log.isInfoEnabled()) {
			_log.info("Search Page Template created");
		}
	}

	protected void customize(Layout layout) throws Exception {
		if (searchLayoutPrototypeCustomizer != null) {
			searchLayoutPrototypeCustomizer.customize(layout);
		}
		else {
			_defaultSearchLayoutPrototypeCustomizer.customize(layout);
		}
	}

	protected String getLayoutTemplateId() {
		if (searchLayoutPrototypeCustomizer != null) {
			return searchLayoutPrototypeCustomizer.getLayoutTemplateId();
		}

		return _defaultSearchLayoutPrototypeCustomizer.getLayoutTemplateId();
	}

	@Reference
	protected GroupLocalService groupLocalService;

	@Reference
	protected LayoutLocalService layoutLocalService;

	@Reference
	protected LayoutPrototypeLocalService layoutPrototypeLocalService;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected volatile SearchLayoutPrototypeCustomizer
		searchLayoutPrototypeCustomizer;

	@Reference
	protected UserLocalService userLocalService;

	private Optional<LayoutPrototype> _findSearchLayoutPrototype(
		long companyId) {

		Map<Locale, String> searchTitleLocalizationMap =
			_getSearchTitleLocalizationMap();

		List<LayoutPrototype> layoutPrototypes =
			layoutPrototypeLocalService.getLayoutPrototypes(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Stream<LayoutPrototype> stream1 = layoutPrototypes.stream();

		Stream<LayoutPrototype> stream2 = stream1.filter(
			layoutPrototype -> _isSearchLayoutPrototype(
				layoutPrototype, companyId, searchTitleLocalizationMap));

		return stream2.findAny();
	}

	private Map<Locale, String> _getFriendlyURLMap() {
		return LocalizationUtil.getLocalizationMap("/search");
	}

	private Map<Locale, String> _getLocalizationMap(String key) {
		return ResourceBundleUtil.getLocalizationMap(
			LanguageResources.PORTAL_RESOURCE_BUNDLE_LOADER, key);
	}

	private Map<Locale, String> _getLocalizationMap(
		String key, ResourceBundleLoader resourceBundleLoader) {

		return ResourceBundleUtil.getLocalizationMap(resourceBundleLoader, key);
	}

	private Map<Locale, String> _getSearchDescriptionLocalizationMap() {
		return _getLocalizationMap("layout-prototype-search-description");
	}

	private Map<Locale, String> _getSearchTitleLocalizationMap() {
		return _getLocalizationMap("layout-prototype-search-title");
	}

	private boolean _hasSearchLayout(Group group) {
		Layout layout = layoutLocalService.fetchLayoutByFriendlyURL(
			group.getGroupId(), false, "/search");

		if (layout != null) {
			return true;
		}

		return false;
	}

	private boolean _isSearchLayoutPrototype(
		LayoutPrototype layoutPrototype, long companyId,
		Map<Locale, String> searchTitleLocalizationMap) {

		if ((layoutPrototype.getCompanyId() == companyId) &&
			searchTitleLocalizationMap.equals(layoutPrototype.getNameMap())) {

			return true;
		}

		return false;
	}

	private boolean _shouldCreateSearchLayout(Group group) {
		if (_hasSearchLayout(group)) {
			return false;
		}

		UnicodeProperties unicodeProperties = group.getTypeSettingsProperties();

		if (unicodeProperties.get("searchLayoutCreated") != null) {
			return false;
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SearchLayoutFactoryImpl.class);

	private final SearchLayoutPrototypeCustomizer
		_defaultSearchLayoutPrototypeCustomizer =
			new DefaultSearchLayoutPrototypeCustomizer();

}