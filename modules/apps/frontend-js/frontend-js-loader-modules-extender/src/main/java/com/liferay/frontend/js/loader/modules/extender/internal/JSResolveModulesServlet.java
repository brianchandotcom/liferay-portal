package com.liferay.frontend.js.loader.modules.extender.internal;

import com.liferay.frontend.js.loader.modules.extender.internal.resolution.JSModulesResolution;
import com.liferay.frontend.js.loader.modules.extender.internal.resolution.JSModulesResolver;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.Servlet;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Rodolfo Roza Miranda
 */
@Component(
	configurationPid = "com.liferay.frontend.js.loader.modules.extender.internal.Details",
	immediate = true,
	property = {
		"osgi.http.whiteboard.servlet.name=com.liferay.frontend.js.loader.modules.extender.internal.JSResolveModulesServlet",
		"osgi.http.whiteboard.servlet.pattern=/js_resolve_modules",
		"service.ranking:Integer=" + Details.MAX_VALUE_LESS_1K
	},
	service = {JSResolveModulesServlet.class, Servlet.class}
)
public class JSResolveModulesServlet extends HttpServlet {

	@Override
	protected void service(
		HttpServletRequest req, HttpServletResponse resp)
		throws IOException {

		List<String> reqModules = _getRequestModules(req);

		JSModulesResolution context = _jsModulesResolver.resolve(reqModules);

		_writeResponse(resp, _jsonFactory.looseSerializeDeep(context));
	}

	private List<String> _getRequestModules(HttpServletRequest req)
		throws IOException {

		String method = req.getMethod();

		String[] modules;

		if (method.equals("GET")) {
			modules = ParamUtil.getStringValues(req, "modules");
		}
		else {
			String body = StringUtil.read(req.getInputStream());

			body = URLDecoder.decode(body, req.getCharacterEncoding());

			body = body.substring(8);

			modules = body.split(StringPool.COMMA);
		}

		if (modules != null) {
			return Arrays.asList(modules);
		}

		return Collections.emptyList();
	}

	private void _writeResponse(HttpServletResponse response, String content)
		throws IOException {

		response.setContentType(Details.CONTENT_TYPE);

		ServletOutputStream servletOutputStream = response.getOutputStream();

		PrintWriter printWriter = new PrintWriter(servletOutputStream, true);

		printWriter.write(content);

		printWriter.close();
	}

	@Reference
	private JSModulesResolver _jsModulesResolver;

	@Reference
	private JSONFactory _jsonFactory;
}
