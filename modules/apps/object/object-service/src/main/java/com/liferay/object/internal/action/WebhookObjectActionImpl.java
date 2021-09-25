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

package com.liferay.object.internal.action;

import com.liferay.object.action.ObjectAction;
import com.liferay.object.action.ObjectActionRequest;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;

import java.io.Serializable;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = ModelListener.class)
public class WebhookObjectActionImpl implements ObjectAction {

	@Override
	public void execute(ObjectActionRequest objectActionRequest)
		throws Exception {

		Map<String, Serializable> properties =
			objectActionRequest.getProperties();

		Http.Options options = new Http.Options();

		options.addHeader(
			HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);
		options.setBody(
			String.valueOf(properties.get("payload")),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);
		options.setLocation((String)properties.get("url"));
		options.setPost(true);

		//TODO Secret and certificates?

		_http.URLtoString(options);
	}

	@Override
	public String getName() {
		return "webhook";
	}

	@Reference
	private Http _http;

}