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

package com.liferay.portal.search.similar.results.web.internal.util.http;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Wade Cao
 * @author André de Oliveira
 */
@Component(service = HttpHelper.class)
public class HttpHelperImpl implements HttpHelper {

	@Override
	public String[] getFriendlyURLParameters(String urlString) {
		try {
			String[] subpath = StringUtil.split(
				_http.getPath(urlString), Portal.FRIENDLY_URL_SEPARATOR);

			return StringUtil.split(
				subpath[subpath.length - 1], CharPool.FORWARD_SLASH);
		}
		catch (RuntimeException runtimeException) {
			return StringPool.EMPTY_ARRAY;
		}
	}

	@Override
	public String getPortletIdParameter(
		String urlString, String parameterName) {

		try {
			Map<String, String[]> parameterMap = _http.parameterMapFromString(
				_http.getQueryString(urlString));

			String[] parameterValues = parameterMap.get(parameterName);

			if (!ArrayUtil.isEmpty(parameterValues)) {
				return parameterValues[0];
			}

			String ppid = parameterMap.get("p_p_id")[0];

			return parameterMap.get(
				StringBundler.concat(
					StringPool.UNDERLINE, ppid, StringPool.UNDERLINE,
					parameterName))[0];
		}
		catch (RuntimeException runtimeException) {
			return null;
		}
	}

	@Override
	public String getPortletIdParameter(
		String urlString, String parameterName, String portletId) {

		try {
			Map<String, String[]> parameterMap = _http.parameterMapFromString(
				_http.getQueryString(urlString));

			return parameterMap.get(
				StringBundler.concat(
					StringPool.UNDERLINE, portletId, StringPool.UNDERLINE,
					parameterName))[0];
		}
		catch (RuntimeException runtimeException) {
			return null;
		}
	}

	@Reference(unbind = "-")
	public void setHttp(Http http) {
		_http = http;
	}

	private Http _http;

}