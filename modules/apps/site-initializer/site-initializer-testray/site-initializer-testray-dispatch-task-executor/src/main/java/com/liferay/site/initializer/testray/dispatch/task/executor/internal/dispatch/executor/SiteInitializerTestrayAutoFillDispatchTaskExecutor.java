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

package com.liferay.site.initializer.testray.dispatch.task.executor.internal.dispatch.executor;

import com.liferay.dispatch.executor.BaseDispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutorOutput;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.site.initializer.testray.dispatch.task.executor.internal.dispatch.executor.util.SiteInitializerTestrayAutoFillUtil;
import com.liferay.site.initializer.testray.dispatch.task.executor.internal.dispatch.executor.util.SiteInitializerTestrayObjectUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Nilton Vieira
 */
@Component(
	property = {
		"dispatch.task.executor.feature.flag=LPS-170809",
		"dispatch.task.executor.name=testray-auto-fill",
		"dispatch.task.executor.overlapping=false",
		"dispatch.task.executor.type=testray-auto-fill"
	},
	service = DispatchTaskExecutor.class
)
public class SiteInitializerTestrayAutoFillDispatchTaskExecutor
	extends BaseDispatchTaskExecutor {

	@Override
	public void doExecute(
			DispatchTrigger dispatchTrigger,
			DispatchTaskExecutorOutput dispatchTaskExecutorOutput)
		throws Exception {

		UnicodeProperties unicodeProperties =
			dispatchTrigger.getDispatchTaskSettingsUnicodeProperties();

		if (Validator.isNull(unicodeProperties.getProperty("autoFillType")) ||
			Validator.isNull(unicodeProperties.getProperty("objectEntryId1")) ||
			Validator.isNull(unicodeProperties.getProperty("objectEntryId2"))) {

			_log.error("The required properties are not set");

			return;
		}

		User user = _userLocalService.getUser(dispatchTrigger.getUserId());

		SiteInitializerTestrayObjectUtil.createDefaultDTOConverterContext(user);

		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		String originalName = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(user.getUserId());

		try {
			SiteInitializerTestrayObjectUtil.loadObjectDefinitions(
				dispatchTrigger.getCompanyId(), _objectDefinitionLocalService);

			_process(dispatchTrigger.getCompanyId(), unicodeProperties);
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);

			PrincipalThreadLocal.setName(originalName);
		}
	}

	@Override
	public String getName() {
		return "testray-auto-fill";
	}

	@Override
	public boolean isClusterModeSingle() {
		return true;
	}

	private void _process(long companyId, UnicodeProperties unicodeProperties)
		throws Exception {

		String autoFillType = GetterUtil.getString(
			unicodeProperties.getProperty("autoFillType"));
		long objectEntryId1 = GetterUtil.getLong(
			unicodeProperties.getProperty("objectEntryId1"));
		long objectEntryId2 = GetterUtil.getLong(
			unicodeProperties.getProperty("objectEntryId2"));

		ObjectEntry objectEntry1 =
			SiteInitializerTestrayObjectUtil.getObjectEntry(
				autoFillType, objectEntryId1, _objectEntryManager);
		ObjectEntry objectEntry2 =
			SiteInitializerTestrayObjectUtil.getObjectEntry(
				autoFillType, objectEntryId2, _objectEntryManager);

		if (StringUtil.equals(autoFillType, "Build")) {
			SiteInitializerTestrayAutoFillUtil.testrayAutoFillBuilds(
				companyId, objectEntry1, objectEntry2, _objectEntryManager);
		}
		else if (StringUtil.equals(autoFillType, "Run")) {
			SiteInitializerTestrayAutoFillUtil.testrayAutoFillRuns(
				companyId, objectEntry1, objectEntry2, _objectEntryManager);
		}
		else {
			_log.error("Auto fill type selected is not available");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SiteInitializerTestrayAutoFillDispatchTaskExecutor.class);

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference(target = "(object.entry.manager.storage.type=default)")
	private ObjectEntryManager _objectEntryManager;

	@Reference
	private UserLocalService _userLocalService;

}