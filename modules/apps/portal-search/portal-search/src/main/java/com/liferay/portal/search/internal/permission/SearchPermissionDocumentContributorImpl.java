/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.permission;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.NoSuchResourceException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.internal.SearchPermissionFieldContributorRegistryUtil;
import com.liferay.portal.search.permission.SearchPermissionDocumentContributor;
import com.liferay.portal.search.spi.model.permission.SearchPermissionFieldContributor;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = SearchPermissionDocumentContributor.class)
public class SearchPermissionDocumentContributorImpl
	implements SearchPermissionDocumentContributor {

	@Override
	public void addPermissionFields(long companyId, Document document) {
		long groupId = GetterUtil.getLong(document.get(Field.GROUP_ID));

		String className = document.get(Field.ENTRY_CLASS_NAME);
		String classPK = document.get(Field.ENTRY_CLASS_PK);

		if (Validator.isNull(className) && Validator.isNull(classPK)) {
			className = document.get(Field.ROOT_ENTRY_CLASS_NAME);
			classPK = document.get(Field.ROOT_ENTRY_CLASS_PK);
		}

		boolean relatedEntry = GetterUtil.getBoolean(
			document.get(Field.RELATED_ENTRY));

		if (relatedEntry) {
			long classNameId = GetterUtil.getLong(
				document.get(Field.CLASS_NAME_ID));

			if (classNameId > 0) {
				className = _portal.getClassName(classNameId);
				classPK = document.get(Field.CLASS_PK);
			}
		}

		addPermissionFields(
			companyId, groupId, className, GetterUtil.getLong(classPK),
			document);
	}

	@Override
	public void addPermissionFields(
		long companyId, long groupId, String className, long classPK,
		Document document) {

		Indexer<?> indexer = _indexerRegistry.nullSafeGetIndexer(className);

		if (!indexer.isPermissionAware()) {
			return;
		}

		String viewActionId = document.get(Field.VIEW_ACTION_ID);

		if (Validator.isNull(viewActionId)) {
			viewActionId = ActionKeys.VIEW;
		}

		_addPermissionFields(
			companyId, groupId, className, classPK, viewActionId, document);
	}

	private void _addPermissionFields(
		long companyId, long groupId, String className, long classPK,
		String viewActionId, Document document) {

		for (SearchPermissionFieldContributor searchPermissionFieldContributor :
				SearchPermissionFieldContributorRegistryUtil.
					getSearchPermissionFieldContributors()) {

			searchPermissionFieldContributor.contribute(
				document, className, classPK);
		}

		String permissionName = _getPermissionName(document, className);

		try {
			List<Role> roles = _resourcePermissionLocalService.getRoles(
				companyId, permissionName, ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(classPK), viewActionId);

			if (roles.isEmpty()) {
				return;
			}

			List<Long> roleIds = new ArrayList<>();
			List<String> groupRoleIds = new ArrayList<>();

			for (Role role : roles) {
				if ((role.getType() == RoleConstants.TYPE_ORGANIZATION) ||
					(role.getType() == RoleConstants.TYPE_SITE)) {

					groupRoleIds.add(
						groupId + StringPool.DASH + role.getRoleId());
				}
				else {
					roleIds.add(role.getRoleId());
				}
			}

			document.addKeyword(Field.ROLE_ID, roleIds.toArray(new Long[0]));
			document.addKeyword(
				Field.GROUP_ROLE_ID, groupRoleIds.toArray(new String[0]));
		}
		catch (NoSuchResourceException noSuchResourceException) {
			if (_log.isDebugEnabled()) {
				_log.debug(noSuchResourceException);
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Unable to get permission fields for class name ",
						permissionName, " and class PK ", classPK),
					exception);
			}
		}
	}

	private String _getPermissionName(Document document, String defaultValue) {
		String resourcePermissionName = document.get("resourcePermissionName");

		if (Validator.isNull(resourcePermissionName)) {
			return defaultValue;
		}

		return resourcePermissionName;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SearchPermissionDocumentContributorImpl.class);

	@Reference
	private IndexerRegistry _indexerRegistry;

	@Reference
	private Portal _portal;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

}