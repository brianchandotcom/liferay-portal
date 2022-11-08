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

import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import com.liferay.dispatch.executor.BaseDispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutorOutput;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.xml.SecureXMLFactoryProviderUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import org.rauschig.jarchivelib.Archiver;
import org.rauschig.jarchivelib.ArchiverFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Nilton Vieira
 */
@Component(
	property = {
		"dispatch.task.executor.cluster.mode=single-node",
		"dispatch.task.executor.feature.flag=LPS-166126",
		"dispatch.task.executor.name=testflow-testray",
		"dispatch.task.executor.overlapping=false",
		"dispatch.task.executor.type=testflow-testray"
	},
	service = DispatchTaskExecutor.class
)
public class TaskFlowTestrayDispatchTaskExecutor extends BaseDispatchTaskExecutor{

	@Override
	public void doExecute(
		DispatchTrigger dispatchTrigger,
		DispatchTaskExecutorOutput dispatchTaskExecutorOutput)
		throws Exception {

		UnicodeProperties unicodeProperties =
			dispatchTrigger.getDispatchTaskSettingsUnicodeProperties();

		if (Validator.isNull(unicodeProperties.getProperty("testrayBuildId")) ||
			Validator.isNull(unicodeProperties.getProperty("testrayTaskId")) ||
			Validator.isNull(
				unicodeProperties.getProperty("testrayCaseTypeIds"))) {

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
			_process(dispatchTrigger.getCompanyId(), unicodeProperties);
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);

			PrincipalThreadLocal.setName(originalName);
		}
	}

	private void _process(long companyId, UnicodeProperties unicodeProperties) throws Exception{

		long testrayBuildId = Long.valueOf(unicodeProperties.getProperty("testrayBuildId"));
		long testrayTaskId = Long.valueOf(unicodeProperties.getProperty("testrayTaskId"));
		String testrayCaseTypeIds = unicodeProperties.getProperty("testrayCaseTypeIds");

		List<ObjectDefinition> objectDefinitions =
			_objectDefinitionLocalService.getObjectDefinitions(
				companyId, true, WorkflowConstants.STATUS_APPROVED);

		if (ListUtil.isEmpty(objectDefinitions)) {
			return;
		}

		for (ObjectDefinition objectDefinition : objectDefinitions) {
			_objectDefinitions.put(
				objectDefinition.getShortName(), objectDefinition);
		}

		List<List<ObjectEntry>> testrayCaseResultGroups = new ArrayList<List<ObjectEntry>>();




	}

	private static final Log _log = LogFactoryUtil.getLog(
		TaskFlowTestrayDispatchTaskExecutor.class);

	private final Map<String, ObjectDefinition> _objectDefinitions =
		new HashMap<>();

	private DefaultDTOConverterContext _defaultDTOConverterContext;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference(target = "(object.entry.manager.storage.type=default)")
	private ObjectEntryManager _objectEntryManager;

	@Reference
	private UserLocalService _userLocalService;

}
