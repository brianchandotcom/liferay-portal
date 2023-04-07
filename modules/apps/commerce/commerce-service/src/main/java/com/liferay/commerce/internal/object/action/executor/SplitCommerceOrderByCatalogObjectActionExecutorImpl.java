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

package com.liferay.commerce.internal.object.action.executor;

import com.liferay.commerce.constants.CommerceObjectActionExecutorConstants;
import com.liferay.commerce.util.SplitCommerceOrderHelper;
import com.liferay.object.action.executor.ObjectActionExecutor;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Crescenzo Rega
 */
@Component(service = ObjectActionExecutor.class)
public class SplitCommerceOrderByCatalogObjectActionExecutorImpl
	implements ObjectActionExecutor {

	@Override
	public void execute(
			long companyId, UnicodeProperties parametersUnicodeProperties,
			JSONObject payloadJSONObject, long userId)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				payloadJSONObject.getLong("objectDefinitionId"));

		if (objectDefinition.isSystem() &&
			!FeatureFlagManagerUtil.isEnabled("COMMERCE-11026")) {

			throw new UnsupportedOperationException();
		}

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				_splitCommerceOrderHelper.splitByCatalog(
					payloadJSONObject.getLong("classPK"));

				return null;
			});
	}

	@Override
	public String getKey() {
		return CommerceObjectActionExecutorConstants.
			KEY_SPLIT_COMMERCE_ORDER_BY_CATALOG;
	}

	@Override
	public boolean isAllowedObjectDefinition(String objectDefinitionName) {
		if (!FeatureFlagManagerUtil.isEnabled("COMMERCE-11026")) {
			return false;
		}

		return StringUtil.equals("CommerceOrder", objectDefinitionName);
	}

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private SplitCommerceOrderHelper _splitCommerceOrderHelper;

}