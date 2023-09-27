/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.servlet;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Alberto Montero
 * @author Julio Camarero
 */
public class NetvibesServlet extends HttpServlet {

	@Override
	public void service(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		try {
			String content = getContent(httpServletRequest);

			if (content == null) {
				PortalUtil.sendError(
					HttpServletResponse.SC_NOT_FOUND,
					new NoSuchLayoutException(), httpServletRequest,
					httpServletResponse);
			}
			else {
				httpServletRequest.setAttribute(WebKeys.NETVIBES, Boolean.TRUE);

				httpServletResponse.setContentType(ContentTypes.TEXT_HTML);

				ServletResponseUtil.write(httpServletResponse, content);
			}
		}
		catch (Exception exception) {
			_log.error(exception);

			PortalUtil.sendError(
				HttpServletResponse.SC_INTERNAL_SERVER_ERROR, exception,
				httpServletRequest, httpServletResponse);
		}
	}

	protected String getContent(HttpServletRequest httpServletRequest)
		throws Exception {

		String path = GetterUtil.getString(httpServletRequest.getPathInfo());

		if (Validator.isNull(path)) {
			return null;
		}

		int pos = path.indexOf(Portal.FRIENDLY_URL_SEPARATOR);

		if (pos == -1) {
			return null;
		}

		String portletId = path.substring(
			pos + Portal.FRIENDLY_URL_SEPARATOR.length());

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			PortalUtil.getCompanyId(httpServletRequest), portletId);

		String title = HtmlUtil.escape(portlet.getDisplayName());

		String portalURL = PortalUtil.getPortalURL(httpServletRequest);

		String iconURL =
			portalURL + PortalUtil.getPathContext() + portlet.getIcon();

		String widgetURL = String.valueOf(httpServletRequest.getRequestURL());

		widgetURL = widgetURL.replaceFirst(
			PropsValues.NETVIBES_SERVLET_MAPPING,
			PropsValues.WIDGET_SERVLET_MAPPING);
		widgetURL = HtmlUtil.escapeJS(widgetURL);

		StringBundler sb = new StringBundler(26);

		sb.append("<!DOCTYPE html>");
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<link href=\"");
		sb.append(_NETVIBES_CSS);
		sb.append("\" rel=\"stylesheet\" type=\"text/css\" ");
		sb.append("/>");
		sb.append("<script src=\"");
		sb.append(_NETVIBES_JS);
		sb.append("\" ");
		sb.append("type=\"text/javascript\"></script>");
		sb.append("<title>");
		sb.append(title);
		sb.append("</title>");
		sb.append("<link href=\"");
		sb.append(iconURL);
		sb.append("\" rel=\"icon\" type=\"image/png\" ");
		sb.append("/>");
		sb.append("</head>");
		sb.append("<body>");
		sb.append("<iframe frameborder=\"0\" height=\"100%\" src=\"");
		sb.append(widgetURL);
		sb.append("\" width=\"100%\">");
		sb.append("</iframe>");
		sb.append("</body>");
		sb.append("</html>");

		return sb.toString();
	}

	private static final String _NETVIBES_CSS =
		"http://www.netvibes.com/themes/uwa/style.css";

	private static final String _NETVIBES_JS =
		"http://www.netvibes.com/js/UWA/load.js.php?env=Standalone";

	private static final Log _log = LogFactoryUtil.getLog(
		NetvibesServlet.class);

}