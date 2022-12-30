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

package com.liferay.commerce.avalara.dispatch.web.internal.executor;

import com.liferay.commerce.avalara.connector.engine.CommerceAvalaraConnectorEngine;
import com.liferay.dispatch.executor.BaseDispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutorOutput;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Katie Nesterovich
 */
@Component(
	property = {
		"dispatch.task.executor.name=" + CommerceAvalaraDispatchTaskExecutor.AVALARA,
		"dispatch.task.executor.type=" + CommerceAvalaraDispatchTaskExecutor.AVALARA
	},
	service = DispatchTaskExecutor.class
)
public class CommerceAvalaraDispatchTaskExecutor
	extends BaseDispatchTaskExecutor {

	public static final String AVALARA = "avalara";

	@Override
	public void doExecute(
			DispatchTrigger dispatchTrigger,
			DispatchTaskExecutorOutput dispatchTaskExecutorOutput)
		throws PortalException {

		UnicodeProperties unicodeProperties =
			dispatchTrigger.getDispatchTaskSettingsUnicodeProperties();

		String groupId = GetterUtil.getString(
			unicodeProperties.getProperty("groupId"));

		try {
			_commerceAvalaraConnectorEngine.updateByAddressEntries(
				Long.parseLong(groupId));
		}
		catch (Exception exception) {
			dispatchTaskExecutorOutput.setError(exception.getMessage());

			throw new PortalException(exception);
		}
	}

	@Override
	public String getName() {
		return AVALARA;
	}

	@Reference
	private CommerceAvalaraConnectorEngine _commerceAvalaraConnectorEngine;

}