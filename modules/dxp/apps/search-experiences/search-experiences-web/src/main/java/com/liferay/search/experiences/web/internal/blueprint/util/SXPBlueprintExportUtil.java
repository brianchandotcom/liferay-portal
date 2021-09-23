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

package com.liferay.search.experiences.web.internal.blueprint.util;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.search.experiences.web.internal.blueprint.constants.SXPBlueprintWebKeys;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = {})
public class SXPBlueprintExportUtil {

	public static JSONObject mapToJSONObject(Map<Locale, String> map)
		throws JSONException {

		String jsonString = _jsonFactory.looseSerialize(map);

		return _jsonFactory.createJSONObject(jsonString);
	}

	public static void writeResponse(
		ResourceRequest resourceRequest, ResourceResponse resourceResponse,
		String title, String responseString) {

		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(resourceResponse);

		try {
			PrintWriter out = httpServletResponse.getWriter();

			httpServletResponse.setContentType("application/json");
			httpServletResponse.setCharacterEncoding("UTF-8");
			httpServletResponse.setHeader(
				"Content-disposition", "attachment; filename=" + title);

			out.print(responseString);
			out.flush();
		}
		catch (IOException ioException) {
			_log.error(ioException.getMessage(), ioException);

			SessionErrors.add(
				resourceRequest, SXPBlueprintWebKeys.ERROR,
				ioException.getMessage());
		}
	}

	@Reference(unbind = "-")
	protected void setJSONFactory(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	@Reference(unbind = "-")
	protected void setPortal(Portal portal) {
		_portal = portal;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SXPBlueprintExportUtil.class);

	private static JSONFactory _jsonFactory;
	private static Portal _portal;

}