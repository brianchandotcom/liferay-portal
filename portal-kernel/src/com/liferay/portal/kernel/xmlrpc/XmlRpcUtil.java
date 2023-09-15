/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.xmlrpc;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;

/**
 * @author Brian Wing Shun Chan
 */
public class XmlRpcUtil {

	public static Fault createFault(int code, String description) {
		return new Fault(code, description);
	}

	public static Success createSuccess(String description) {
		return new Success(description);
	}

	public static Response executeMethod(
			String url, String methodName, Object[] arguments)
		throws XmlRpcException {

		try {
			if (_log.isDebugEnabled()) {
				StringBundler sb = new StringBundler();

				sb.append("XML-RPC invoking ");
				sb.append(methodName);
				sb.append(" ");

				if (arguments != null) {
					for (int i = 0; i < arguments.length; i++) {
						sb.append(arguments[i]);

						if (i < (arguments.length - 1)) {
							sb.append(", ");
						}
					}
				}

				_log.debug(sb.toString());
			}

			String requestXML = XmlRpcParser.buildMethod(methodName, arguments);

			Http.Options options = new Http.Options();

			if (_HTTP_HEADER_VERSION_VERBOSITY_DEFAULT) {
			}
			else if (_HTTP_HEADER_VERSION_VERBOSITY_PARTIAL) {
				options.addHeader(
					HttpHeaders.USER_AGENT, ReleaseInfo.getName());
			}
			else {
				options.addHeader(
					HttpHeaders.USER_AGENT, ReleaseInfo.getServerInfo());
			}

			options.setBody(requestXML, ContentTypes.TEXT_XML, StringPool.UTF8);
			options.setLocation(url);
			options.setPost(true);

			return XmlRpcParser.parseResponse(HttpUtil.URLtoString(options));
		}
		catch (Exception exception) {
			throw new XmlRpcException(exception);
		}
	}

	private static final boolean _HTTP_HEADER_VERSION_VERBOSITY_DEFAULT =
		StringUtil.equalsIgnoreCase(
			PropsUtil.get(PropsKeys.HTTP_HEADER_VERSION_VERBOSITY), "off");

	private static final boolean _HTTP_HEADER_VERSION_VERBOSITY_PARTIAL =
		StringUtil.equalsIgnoreCase(
			PropsUtil.get(PropsKeys.HTTP_HEADER_VERSION_VERBOSITY), "partial");

	private static final Log _log = LogFactoryUtil.getLog(XmlRpcUtil.class);

}