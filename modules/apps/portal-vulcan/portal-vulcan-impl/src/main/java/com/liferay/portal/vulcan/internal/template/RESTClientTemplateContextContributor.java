/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.template;

import com.liferay.petra.io.unsync.UnsyncStringWriter;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.servlet.PipingServletResponse;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.template.TemplateContextContributor;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.internal.template.servlet.RESTClientHttpRequest;
import com.liferay.portal.vulcan.internal.template.servlet.RESTClientHttpResponse;

import java.util.Map;
import java.util.Objects;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "type=" + TemplateContextContributor.TYPE_GLOBAL,
	service = TemplateContextContributor.class
)
public class RESTClientTemplateContextContributor
	implements TemplateContextContributor {

	@Override
	public void prepare(
		Map<String, Object> contextObjects,
		HttpServletRequest httpServletRequest) {

		contextObjects.put("restClient", new RESTClient(httpServletRequest));
	}

	public class RESTClient {

		public RESTClient(HttpServletRequest httpServletRequest) {
			_httpServletRequest = httpServletRequest;
		}

		public Object get(String path) throws Exception {
			UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

			ServletContext servletContext = _getServletContext();

			RequestDispatcher requestDispatcher =
				servletContext.getRequestDispatcher(Portal.PATH_MODULE + path);

			HttpServletResponse httpServletResponse = new PipingServletResponse(
				new RESTClientHttpResponse(), unsyncStringWriter);

			requestDispatcher.forward(
				new RESTClientHttpRequest(_httpServletRequest),
				httpServletResponse);

			String responseString = unsyncStringWriter.toString();

			if (Objects.equals(
					httpServletResponse.getContentType(),
					ContentTypes.APPLICATION_JSON)) {

				return _jsonFactory.looseDeserialize(responseString);
			}

			return responseString;
		}

		private final HttpServletRequest _httpServletRequest;

	}

	private ServletContext _getServletContext() {
		if (_servletContext == null) {
			_servletContext = ServletContextPool.get(StringPool.BLANK);
		}

		return _servletContext;
	}

	@Reference
	private JSONFactory _jsonFactory;

	private ServletContext _servletContext;

}