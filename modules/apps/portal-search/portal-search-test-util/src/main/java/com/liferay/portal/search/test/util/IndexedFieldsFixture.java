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

package com.liferay.portal.search.test.util;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.text.Format;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Eric Yan
 * @author Wade Cao
 * @author André de Oliveira
 */
public class IndexedFieldsFixture {

	public IndexedFieldsFixture(
		ResourcePermissionLocalService resourcePermissionLocalService,
		SearchEngineHelper searchEngineHelper) {

		_resourcePermissionLocalService = resourcePermissionLocalService;
		_searchEngineHelper = searchEngineHelper;
	}

	public void populateDate(
		String field, Date value, Map<String, String> map) {

		map.put(field, _dateFormat.format(value));

		map.put(field.concat("_sortable"), String.valueOf(value.getTime()));
	}

	public void populateExpirationDateWithForever(Map<String, String> map) {
		populateDate(Field.EXPIRATION_DATE, new Date(Long.MAX_VALUE), map);

		if (_isSearchEngineElasticsearch()) {
			map.put(Field.EXPIRATION_DATE, "99950812133000");
		}
	}

	public void populatePriority(String priority, Map<String, String> map) {
		map.put(Field.PRIORITY, priority);

		if (_isSearchEngineSolr()) {
			map.put(Field.PRIORITY.concat("_sortable"), priority);
		}
	}

	public void populateRoleIdFields(
			long companyId, String className, long classPK, long groupId,
			String viewActionId, Map<String, String> map)
		throws Exception {

		if (Validator.isNull(viewActionId)) {
			viewActionId = ActionKeys.VIEW;
		}

		List<Role> roles = _resourcePermissionLocalService.getRoles(
			companyId, className, ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(classPK), viewActionId);

		List<String> groupRoleIds = new ArrayList<>();
		List<String> roleIds = new ArrayList<>();

		for (Role role : roles) {
			if ((role.getType() == RoleConstants.TYPE_ORGANIZATION) ||
				(role.getType() == RoleConstants.TYPE_SITE)) {

				groupRoleIds.add(groupId + StringPool.DASH + role.getRoleId());
			}
			else {
				roleIds.add(String.valueOf(role.getRoleId()));
			}
		}

		populateRoleIds(Field.GROUP_ROLE_ID, groupRoleIds, map);
		populateRoleIds(Field.ROLE_ID, roleIds, map);
	}

	public void populateUID(
		String modelClassName, long id, Map<String, String> map) {

		map.put(Field.UID, modelClassName + "_PORTLET_" + id);
	}

	public void populateViewCount(
			Class<?> clazz, long classPK, Map<String, String> map)
		throws Exception {

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClass(
				clazz);

		AssetEntry assetEntry = assetRendererFactory.getAssetEntry(
			clazz.getName(), classPK);

		map.put("viewCount", String.valueOf(assetEntry.getViewCount()));
		map.put(
			"viewCount_sortable", String.valueOf(assetEntry.getViewCount()));
	}

	public void postProcessDocument(Document document) {
		if (_isSearchEngineSolr()) {
			document.remove("score");
		}
	}

	protected void populateRoleIds(
		String field, List<String> values, Map<String, String> map) {

		if (values.size() == 1) {
			map.put(field, values.get(0));
		}
		else if (values.size() > 1) {
			map.put(field, values.toString());
		}
	}

	private boolean _isSearchEngine(String vendor) {
		SearchEngine searchEngine = _searchEngineHelper.getSearchEngine(
			_searchEngineHelper.getDefaultSearchEngineId());

		return vendor.equals(searchEngine.getVendor());
	}

	private boolean _isSearchEngineElasticsearch() {
		return _isSearchEngine("Elasticsearch");
	}

	private boolean _isSearchEngineSolr() {
		return _isSearchEngine("Solr");
	}

	private final Format _dateFormat =
		FastDateFormatFactoryUtil.getSimpleDateFormat("yyyyMMddHHmmss");
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;
	private final SearchEngineHelper _searchEngineHelper;

}