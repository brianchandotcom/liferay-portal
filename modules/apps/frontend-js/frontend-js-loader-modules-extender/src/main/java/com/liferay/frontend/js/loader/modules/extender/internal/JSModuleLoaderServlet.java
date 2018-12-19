package com.liferay.frontend.js.loader.modules.extender.internal;

import com.liferay.portal.kernel.util.ParamUtil;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Rodolfo Roza Miranda
 */
@Component(
	configurationPid = "com.liferay.frontend.js.loader.modules.extender.internal.Details",
	immediate = true,
	property = {
		"osgi.http.whiteboard.servlet.name=com.liferay.frontend.js.loader.modules.extender.internal.JSModuleLoaderServlet",
		"osgi.http.whiteboard.servlet.pattern=/js_module_loader",
		"service.ranking:Integer=" + Details.MAX_VALUE_LESS_1K
	},
	service = {JSModuleLoaderServlet.class, Servlet.class}
)
public class JSModuleLoaderServlet extends HttpServlet {

	@Override
	protected void service(
		HttpServletRequest req, HttpServletResponse resp)
		throws IOException {

		List<String> reqModules = _getRequestModules(req);

		List<String> parsedModules = _jsModulesResolver.resolve(reqModules);

		StringWriter stringWriter = new StringWriter();

		PrintWriter printWriter = new PrintWriter(stringWriter);

		String collect = parsedModules.stream().map(m -> "\"" + m + "\"").collect(Collectors.joining(","));

		printWriter.write("[");
		printWriter.write(collect);
		printWriter.write("]");

		printWriter.close();

		String content = stringWriter.toString();

		System.out.println("Resolved modules for " + String.join(",", reqModules) + " : " + parsedModules.size());

		_writeResponse(resp, content);
	}

	@Reference(unbind = "-")
	protected void setJSModulesParser(
		JSModulesResolver jsModulesResolver) {
		_jsModulesResolver = jsModulesResolver;
	}

	private List<String> _getRequestModules(HttpServletRequest req) {
		String[] modulesParam = ParamUtil.getStringValues(req, "modules");

		if (modulesParam != null) {
			return Arrays.asList(modulesParam);
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

	private JSModulesResolver _jsModulesResolver;
}
