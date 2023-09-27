/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.workflow.internal.odata.entity.v1_0;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.DateTimeEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.IntegerEntityField;
import com.liferay.portal.odata.entity.StringEntityField;

import java.util.Map;

/**
 * @author Inácio Nery
 */
public class WorkflowTaskEntityModel implements EntityModel {

	public WorkflowTaskEntityModel() {
		_entityFieldsMap = EntityModel.toEntityFieldsMap(
			new DateTimeEntityField(
				"dateCompletion", locale -> "dateCompletion",
				locale -> "dateCompletion"),
			new DateTimeEntityField(
				"dateCreated", locale -> "dateCreated",
				locale -> "dateCreated"),
			new DateTimeEntityField(
				"dateDue", locale -> "dateDue", locale -> "dateDue"),
			new StringEntityField("name", locale -> "name"),
			new IntegerEntityField(
				"workflowInstanceId", locale -> "workflowInstanceId"));
	}

	@Override
	public Map<String, EntityField> getEntityFieldsMap() {
		return _entityFieldsMap;
	}

	@Override
	public String getName() {
		return StringUtil.replace(
			WorkflowTaskEntityModel.class.getName(), CharPool.PERIOD,
			CharPool.UNDERLINE);
	}

	private final Map<String, EntityField> _entityFieldsMap;

}