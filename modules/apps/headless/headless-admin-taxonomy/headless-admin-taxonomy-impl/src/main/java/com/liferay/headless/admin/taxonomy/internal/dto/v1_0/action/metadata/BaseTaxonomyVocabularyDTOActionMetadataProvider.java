/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.taxonomy.internal.dto.v1_0.action.metadata;

import com.liferay.headless.admin.taxonomy.internal.resource.v1_0.TaxonomyVocabularyResourceImpl;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.vulcan.dto.action.ActionInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Javier Gamarra
 * @generated
 */
public abstract class BaseTaxonomyVocabularyDTOActionMetadataProvider {

	public BaseTaxonomyVocabularyDTOActionMetadataProvider() {
		_actionInfos.put(
			"delete",
			new ActionInfo(
				getDeleteActionName(), TaxonomyVocabularyResourceImpl.class,
				getDeleteResourceMethodName()));
		_actionInfos.put(
			"get",
			new ActionInfo(
				getGetActionName(), TaxonomyVocabularyResourceImpl.class,
				getGetResourceMethodName()));
		_actionInfos.put(
			"replace",
			new ActionInfo(
				getReplaceActionName(), TaxonomyVocabularyResourceImpl.class,
				getReplaceResourceMethodName()));
		_actionInfos.put(
			"update",
			new ActionInfo(
				getUpdateActionName(), TaxonomyVocabularyResourceImpl.class,
				getUpdateResourceMethodName()));
	}

	public final ActionInfo getActionInfo(String actionName) {
		return _actionInfos.get(actionName);
	}

	public final Set<String> getActionNames() {
		return _actionInfos.keySet();
	}

	public abstract String getPermissionName();

	protected String getDeleteActionName() {
		return ActionKeys.DELETE;
	}

	protected String getDeleteResourceMethodName() {
		return "deleteTaxonomyVocabulary";
	}

	protected String getGetActionName() {
		return ActionKeys.VIEW;
	}

	protected String getGetResourceMethodName() {
		return "getTaxonomyVocabulary";
	}

	protected String getReplaceActionName() {
		return ActionKeys.UPDATE;
	}

	protected String getReplaceResourceMethodName() {
		return "putTaxonomyVocabulary";
	}

	protected String getUpdateActionName() {
		return ActionKeys.UPDATE;
	}

	protected String getUpdateResourceMethodName() {
		return "patchTaxonomyVocabulary";
	}

	protected final void registerActionInfo(
		ActionInfo actionInfo, String actionName) {

		_actionInfos.put(actionName, actionInfo);
	}

	private final Map<String, ActionInfo> _actionInfos = new HashMap<>();

}