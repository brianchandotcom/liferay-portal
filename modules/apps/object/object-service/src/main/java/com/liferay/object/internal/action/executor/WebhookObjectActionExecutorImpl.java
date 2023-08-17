/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.action.executor;

import com.liferay.object.action.executor.ObjectActionExecutor;
import com.liferay.object.constants.ObjectActionExecutorConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.UnicodeProperties;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(service = ObjectActionExecutor.class)
public class WebhookObjectActionExecutorImpl implements ObjectActionExecutor {

	@Override
	public void execute(
			long companyId, long objectActionId,
			UnicodeProperties parametersUnicodeProperties,
			JSONObject payloadJSONObject, long userId)
		throws Exception {

		Http.Options options = new Http.Options();

		options.addHeader(
			HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);
		options.addHeader(
			"x-api-key", parametersUnicodeProperties.get("secret"));
		options.setBody(
			payloadJSONObject.toString(), ContentTypes.APPLICATION_JSON,
			StringPool.UTF8);
		options.setLocation(parametersUnicodeProperties.get("url"));
		options.setPost(true);

		_http.URLtoString(options);
	}

	@Override
	public String getKey() {
		return ObjectActionExecutorConstants.KEY_WEBHOOK;
	}

	@Reference
	private Http _http;

}