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

package com.liferay.commerce.machine.learning.internal.batch.engine.mapper;

import java.util.Map;

/**
 * @author Riccardo Ferrari
 */
public class BatchEngineTaskItemDelegateResourceMapper {

	public BatchEngineTaskItemDelegateResourceMapper(
		String resourceName, Map<String, String> fieldMapping,
		String batchEngineTaskItemDelegate) {

		_resourceName = resourceName;
		_fieldMapping = fieldMapping;
		_batchEngineTaskItemDelegate = batchEngineTaskItemDelegate;
	}

	public String getBatchEngineTaskItemDelegate() {
		return _batchEngineTaskItemDelegate;
	}

	public Map<String, String> getFieldMapping() {
		return _fieldMapping;
	}

	public String getResourceName() {
		return _resourceName;
	}

	private final String _batchEngineTaskItemDelegate;
	private final Map<String, String> _fieldMapping;
	private final String _resourceName;

}