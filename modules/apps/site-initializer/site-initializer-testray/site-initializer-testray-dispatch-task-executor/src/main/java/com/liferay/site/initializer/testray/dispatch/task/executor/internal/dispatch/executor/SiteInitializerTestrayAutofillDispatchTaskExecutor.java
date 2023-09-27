/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.site.initializer.testray.dispatch.task.executor.internal.dispatch.executor.util.ObjectEntryUtil;
import com.liferay.site.initializer.testray.dispatch.task.executor.internal.dispatch.executor.util.TestrayUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Nilton Vieira
 */
@Component(
	property = {
		"dispatch.task.executor.feature.flag=LPS-170809",
		"dispatch.task.executor.name=testray-autofill",
		"dispatch.task.executor.overlapping=false",
		"dispatch.task.executor.type=testray-autofill"
	},
	service = DispatchTaskExecutor.class
)
public class SiteInitializerTestrayAutofillDispatchTaskExecutor
	extends BaseDispatchTaskExecutor {

	@Override
	public void doExecute(
			DispatchTrigger dispatchTrigger,
			DispatchTaskExecutorOutput dispatchTaskExecutorOutput)
		throws Exception {

		UnicodeProperties unicodeProperties =
			dispatchTrigger.getDispatchTaskSettingsUnicodeProperties();

		if (Validator.isNull(unicodeProperties.getProperty("autofillType")) ||
			Validator.isNull(unicodeProperties.getProperty("objectEntryId1")) ||
			Validator.isNull(unicodeProperties.getProperty("objectEntryId2"))) {

			_log.error("The required properties are not set");

			return;
		}

		User user = _userLocalService.getUser(dispatchTrigger.getUserId());

		_defaultDTOConverterContext = new DefaultDTOConverterContext(
			false, null, null, null, null, LocaleUtil.getSiteDefault(), null,
			user);

		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));

		String originalName = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(user.getUserId());

		try {
			ObjectEntryUtil.loadObjectDefinitions(
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
		return "testray-autofill";
	}

	@Override
	public boolean isClusterModeSingle() {
		return true;
	}

	private void _process(long companyId, UnicodeProperties unicodeProperties)
		throws Exception {

		String autofillType = GetterUtil.getString(
			unicodeProperties.getProperty("autofillType"));
		long objectEntryId1 = GetterUtil.getLong(
			unicodeProperties.getProperty("objectEntryId1"));
		long objectEntryId2 = GetterUtil.getLong(
			unicodeProperties.getProperty("objectEntryId2"));

		ObjectEntry objectEntry1 = ObjectEntryUtil.getObjectEntry(
			_defaultDTOConverterContext, autofillType, objectEntryId1,
			_objectEntryManager);
		ObjectEntry objectEntry2 = ObjectEntryUtil.getObjectEntry(
			_defaultDTOConverterContext, autofillType, objectEntryId2,
			_objectEntryManager);

		if (StringUtil.equals(autofillType, "Build")) {
			TestrayUtil.autofillTestrayBuilds(
				companyId, _defaultDTOConverterContext, _objectEntryManager,
				objectEntry1, objectEntry2);
		}
		else if (StringUtil.equals(autofillType, "Run")) {
			TestrayUtil.autofillTestrayRuns(
				companyId, _defaultDTOConverterContext, _objectEntryManager,
				objectEntry1, objectEntry2);
		}
		else {
			_log.error("Unknown autofill type: " + autofillType);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SiteInitializerTestrayAutofillDispatchTaskExecutor.class);

	private DefaultDTOConverterContext _defaultDTOConverterContext;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference(target = "(object.entry.manager.storage.type=default)")
	private ObjectEntryManager _objectEntryManager;

	@Reference
	private UserLocalService _userLocalService;

}