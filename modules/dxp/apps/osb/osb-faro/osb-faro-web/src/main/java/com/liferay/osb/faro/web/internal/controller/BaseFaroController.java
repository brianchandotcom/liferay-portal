/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.web.internal.controller;

import com.liferay.osb.faro.engine.client.CerebroEngineClient;
import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.engine.client.WorkspaceEngineClient;
import com.liferay.osb.faro.service.FaroProjectLocalService;
import com.liferay.osb.faro.web.internal.model.display.FaroResultsDisplay;
import com.liferay.osb.faro.web.internal.search.FaroSearchContext;
import com.liferay.osb.faro.web.internal.util.ContactsHelper;
import com.liferay.osb.faro.web.internal.util.ProjectHelper;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Shinn Lok
 */
public abstract class BaseFaroController implements FaroController {

	@Override
	public int[] getEntityTypes() {
		return new int[0];
	}

	@Override
	public FaroResultsDisplay search(
			long groupId, FaroSearchContext faroSearchContext)
		throws Exception {

		return new FaroResultsDisplay();
	}

	protected long getCompanyId() {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		return permissionChecker.getCompanyId();
	}

	protected String getLocalizedMessage(String key) {
		User user = getUser();

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", user.getLocale(), getClass());

		return language.get(resourceBundle, key);
	}

	protected User getUser() {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		return permissionChecker.getUser();
	}

	protected long getUserId() {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		return permissionChecker.getUserId();
	}

	protected boolean isOmniadmin() {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		return permissionChecker.isOmniadmin();
	}

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected volatile CerebroEngineClient cerebroEngineClient;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected volatile ContactsEngineClient contactsEngineClient;

	@Reference
	protected ContactsHelper contactsHelper;

	@Reference
	protected FaroProjectLocalService faroProjectLocalService;

	@Reference
	protected Language language;

	@Reference
	protected ProjectHelper projectHelper;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected volatile WorkspaceEngineClient workspaceEngineClient;

}