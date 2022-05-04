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

package com.liferay.osb.testray.internal.dispatch.executor;

import com.liferay.dispatch.executor.BaseDispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutorOutput;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author José Abelenda
 */
@Component(
	property = {
		"dispatch.task.executor.name=testray",
		"dispatch.task.executor.type=testray"
	},
	service = DispatchTaskExecutor.class
)
public class TestrayDispatchTaskExecutor extends BaseDispatchTaskExecutor {

	@Override
	public void doExecute(
			DispatchTrigger dispatchTrigger,
			DispatchTaskExecutorOutput dispatchTaskExecutorOutput)
		throws Exception {

		if (_log.isInfoEnabled()) {
			_log.info("Invoking doExecute");
		}

		_loadCache(dispatchTrigger.getCompanyId());
	}

	@Override
	public String getName() {
		return "testray";
	}

	private void _loadCache(long companyId) throws Exception {
		List<ObjectDefinition> objectDefinitions =
			_objectDefinitionLocalService.getObjectDefinitions(
				companyId, true, WorkflowConstants.STATUS_APPROVED);

		if (ListUtil.isEmpty(objectDefinitions)) {
			return;
		}

		for (ObjectDefinition objectDefinition : objectDefinitions) {
			_objectDefinitionIds.put(
				objectDefinition.getShortName(),
				objectDefinition.getObjectDefinitionId());
		}

		_loadObjectEntryIds(
			"CaseType", "c_caseTypeId", "CaseType#[$name$]",
			Arrays.asList("name"));
		_loadObjectEntryIds(
			"Project", "c_projectId", "Project#[$name$]",
			Arrays.asList("name"));
		_loadObjectEntryIds(
			"Team", "c_teamId",
			"Team#[$name$]#testrayProjectId#[$r_projectToTeams_c_projectId$]",
			Arrays.asList("name", "r_projectToTeams_c_projectId"));
	}

	private void _loadObjectEntryIds(
			String objectShortName, String pkObjectFieldDBColumnName,
			String key, List<String> placeholders)
		throws Exception {

		List<Map<String, Serializable>> values =
			_objectEntryLocalService.getValuesList(
				_objectDefinitionIds.get(objectShortName), null, 0, 0);

		if (ListUtil.isEmpty(values)) {
			return;
		}

		Map<String, String> placeholderMap = new HashMap<>();

		for (Map<String, Serializable> map : values) {
			Long id = (Long)map.get(pkObjectFieldDBColumnName);

			for (String placeholder : placeholders) {
				placeholderMap.put(
					placeholder, String.valueOf(map.get(placeholder)));
			}

			key = StringUtil.replace(key, "[$", "$]", placeholderMap);

			_objectEntryIds.put(key, id.longValue());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TestrayDispatchTaskExecutor.class);

	private final Map<String, Long> _objectDefinitionIds = new HashMap<>();

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	private final Map<String, Long> _objectEntryIds = new HashMap<>();

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

}