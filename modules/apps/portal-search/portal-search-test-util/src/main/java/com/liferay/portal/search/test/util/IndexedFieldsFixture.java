/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.test.util;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchEngine;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.search.document.DocumentBuilderFactory;
import com.liferay.portal.search.model.uid.UIDFactory;

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

		_uidFactory = null;
		_documentBuilderFactory = null;
	}

	public IndexedFieldsFixture(
		ResourcePermissionLocalService resourcePermissionLocalService,
		SearchEngineHelper searchEngineHelper,
		DocumentBuilderFactory documentBuilderFactory) {

		_resourcePermissionLocalService = resourcePermissionLocalService;
		_searchEngineHelper = searchEngineHelper;
		_documentBuilderFactory = documentBuilderFactory;

		_uidFactory = null;
	}

	public IndexedFieldsFixture(
		ResourcePermissionLocalService resourcePermissionLocalService,
		SearchEngineHelper searchEngineHelper, UIDFactory uidFactory,
		DocumentBuilderFactory documentBuilderFactory) {

		_resourcePermissionLocalService = resourcePermissionLocalService;
		_searchEngineHelper = searchEngineHelper;
		_uidFactory = uidFactory;
		_documentBuilderFactory = documentBuilderFactory;
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
		populatePriority(priority, map, true);
	}

	public void populatePriority(
		String priority, Map<String, String> map,
		boolean sourceFilteringEnabled) {

		map.put(Field.PRIORITY, priority);

		if (sourceFilteringEnabled || _isSearchEngineSolr()) {
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
		ClassedModel classedModel, Map<String, String> map) {

		DocumentBuilder documentBuilder = _documentBuilderFactory.builder();

		_uidFactory.setUID(classedModel, documentBuilder);

		Document document = documentBuilder.build();

		map.put(Field.UID, document.getString(Field.UID));

		String uidm = document.getString("uidm");

		if (uidm != null) {
			map.put("uidm", uidm);
		}

		if (classedModel instanceof CTModel<?>) {
			CTModel<?> ctModel = (CTModel<?>)classedModel;

			if (ctModel.getCtCollectionId() !=
					CTConstants.CT_COLLECTION_ID_PRODUCTION) {

				map.put(
					"ctCollectionId",
					String.valueOf(ctModel.getCtCollectionId()));
			}
		}
	}

	public void populateUID(
		String modelClassName, long id, Map<String, String> map) {

		map.put(Field.UID, modelClassName + "_PORTLET_" + id);

		if (_ENFORCE_STANDARD_UID) {
			map.put("uidm", modelClassName + "_PORTLET_" + id);
		}
	}

	public void populateViewCount(
			Class<?> clazz, long classPK, Map<String, String> map)
		throws Exception {

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClass(
				clazz);

		AssetEntry assetEntry = assetRendererFactory.getAssetEntry(
			clazz.getName(), classPK);

		map.put("viewCount", String.valueOf(assetEntry.getViewCount()));
		map.put(
			"viewCount_sortable", String.valueOf(assetEntry.getViewCount()));
	}

	public void postProcessDocument(
		com.liferay.portal.kernel.search.Document document) {

		if (_isSearchEngineSolr()) {
			if (Validator.isNotNull(document.get("roleNames"))) {
				document.add(
					new Field(
						"roleNames",
						StringUtil.toLowerCase(document.get("roleNames"))));
			}

			document.remove("score");
		}

		if (_isSearchEngineElasticsearch()) {
			document.remove("timestamp");
		}
	}

	public Document postProcessDocument(Document document) {
		if (_isSearchEngineSolr()) {
			DocumentBuilder documentBuilder = _documentBuilderFactory.builder(
				document);

			documentBuilder.setString(
				"userGroupRoleNames",
				StringUtil.toLowerCase(
					document.getString("userGroupRoleNames")));
			documentBuilder.unsetValue("score");

			return documentBuilder.build();
		}

		if (_isSearchEngineElasticsearch()) {
			DocumentBuilder documentBuilder = _documentBuilderFactory.builder(
				document);

			documentBuilder.unsetValue("timestamp");

			return documentBuilder.build();
		}

		return document;
	}

	protected void populateRoleIds(
		String field, List<String> values, Map<String, String> map) {

		if (values.size() == 1) {
			map.put(field, values.get(0));
		}
		else if (values.size() > 1) {
			values.sort(null);

			map.put(field, values.toString());
		}
	}

	private boolean _isSearchEngine(String vendor) {
		SearchEngine searchEngine = _searchEngineHelper.getSearchEngine();

		return vendor.equals(searchEngine.getVendor());
	}

	private boolean _isSearchEngineElasticsearch() {
		return _isSearchEngine("Elasticsearch");
	}

	private boolean _isSearchEngineSolr() {
		return _isSearchEngine("Solr");
	}

	private static final boolean _ENFORCE_STANDARD_UID = false;

	private final Format _dateFormat =
		FastDateFormatFactoryUtil.getSimpleDateFormat("yyyyMMddHHmmss");
	private final DocumentBuilderFactory _documentBuilderFactory;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;
	private final SearchEngineHelper _searchEngineHelper;
	private final UIDFactory _uidFactory;

}