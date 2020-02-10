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

package com.liferay.frontend.js.aui.web.internal.servlet.taglib;

import com.liferay.frontend.js.aui.web.internal.configuration.AuiConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilderFactory;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(
	configurationPid = "com.liferay.frontend.js.aui.web.internal.configuration.AuiConfiguration",
	immediate = true, service = DynamicInclude.class
)
public class AuiTopHeadDynamicInclude extends BaseDynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (themeDisplay.isThemeJsBarebone()) {
			_writeFileNames(
				httpServletRequest, httpServletResponse, _fileNamesBarebone);
		}
		else {
			_writeFileNames(
				httpServletRequest, httpServletResponse, _fileNames);
		}
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register(
			"/html/common/themes/top_js.jspf#resources");
	}

	@Activate
	@Modified
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_bundleContext = bundleContext;

		_lastModified = System.currentTimeMillis();

		AuiConfiguration auiConfiguration = ConfigurableUtil.createConfigurable(
			AuiConfiguration.class, properties);

		_fileNames.clear();
		_fileNamesBarebone.clear();

		if (auiConfiguration.enableAui()) {
			Collections.addAll(_fileNames, _FILE_NAMES_AUI_CORE);
			Collections.addAll(_fileNamesBarebone, _FILE_NAMES_AUI_CORE);
		}

		if (auiConfiguration.enableAuiPreload()) {
			Collections.addAll(_fileNames, _FILE_NAMES_AUI_PRELOAD_BAREBONE);

			Collections.addAll(_fileNames, _FILE_NAMES_AUI_PRELOAD);

			Collections.addAll(
				_fileNamesBarebone, _FILE_NAMES_AUI_PRELOAD_BAREBONE);
		}
	}

	private void _writeFileNames(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, List<String> fileNames)
		throws IOException {

		PrintWriter printWriter = httpServletResponse.getWriter();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		AbsolutePortalURLBuilder absolutePortalURLBuilder =
			_absolutePortalURLBuilderFactory.getAbsolutePortalURLBuilder(
				httpServletRequest);

		StringBundler sb = new StringBundler();

		if (themeDisplay.isThemeJsFastLoad()) {
			absolutePortalURLBuilder.ignoreCDNHost();

			sb.append("<script data-senna-track=\"permanent\" src=\"");
			sb.append(
				_portal.getStaticResourceURL(
					httpServletRequest, _portal.getPathContext() + "/combo",
					"minifierType=js", _lastModified));

			for (String fileName : fileNames) {
				sb.append("&");
				sb.append(
					absolutePortalURLBuilder.forModule(
						_bundleContext.getBundle(), fileName
					).build());
			}

			sb.append("\" type=\"text/javascript\"></script>");
		}
		else {
			for (String fileName : fileNames) {
				sb.append("<script data-senna-track=\"permanent\" src=\"");
				sb.append(
					absolutePortalURLBuilder.forModule(
						_bundleContext.getBundle(), fileName
					).build());
				sb.append("\" type=\"text/javascript\"></script>");
			}
		}

		printWriter.println(sb.toString());
	}

	private static final String[] _FILE_NAMES_AUI_CORE = {"/aui/aui/aui.js"};

	private static final String[] _FILE_NAMES_AUI_PRELOAD = {
		"/aui/async-queue/async-queue.js", "/aui/base-build/base-build.js",
		"/aui/cookie/cookie.js", "/aui/event-touch/event-touch.js",
		"/aui/overlay/overlay.js",
		"/aui/querystring-stringify/querystring-stringify.js",
		"/aui/widget-child/widget-child.js",
		"/aui/widget-position-align/widget-position-align.js",
		"/aui/widget-position-constrain/widget-position-constrain.js",
		"/aui/widget-position/widget-position.js",
		"/aui/widget-stack/widget-stack.js",
		"/aui/widget-stdmod/widget-stdmod.js", "/aui/aui-aria/aui-aria.js",
		"/aui/aui-io-plugin-deprecated/aui-io-plugin-deprecated.js",
		"/aui/aui-io-request/aui-io-request.js",
		"/aui/aui-loading-mask-deprecated/aui-loading-mask-deprecated.js",
		"/aui/aui-overlay-base-deprecated/aui-overlay-base-deprecated.js",
		"/aui/aui-overlay-context-deprecated/aui-overlay-context-deprecated.js",
		"/aui/aui-overlay-manager-deprecated/aui-overlay-manager-deprecated.js",
		"/aui/aui-overlay-mask-deprecated/aui-overlay-mask-deprecated.js",
		"/aui/aui-parse-content/aui-parse-content.js", "/liferay/session.js",
		"/liferay/deprecated.js"
	};

	private static final String[] _FILE_NAMES_AUI_PRELOAD_BAREBONE = {
		"/aui/aui-base-html5-shiv/aui-base-html5-shiv.js",
		"/liferay/browser_selectors.js", "/liferay/modules.js",
		"/liferay/aui_sandbox.js", "/aui/arraylist-add/arraylist-add.js",
		"/aui/arraylist-filter/arraylist-filter.js",
		"/aui/arraylist/arraylist.js", "/aui/array-extras/array-extras.js",
		"/aui/array-invoke/array-invoke.js",
		"/aui/attribute-base/attribute-base.js",
		"/aui/attribute-complex/attribute-complex.js",
		"/aui/attribute-core/attribute-core.js",
		"/aui/attribute-observable/attribute-observable.js",
		"/aui/attribute-extras/attribute-extras.js",
		"/aui/base-base/base-base.js",
		"/aui/base-pluginhost/base-pluginhost.js",
		"/aui/classnamemanager/classnamemanager.js",
		"/aui/datatype-xml-format/datatype-xml-format.js",
		"/aui/datatype-xml-parse/datatype-xml-parse.js",
		"/aui/dom-base/dom-base.js", "/aui/dom-core/dom-core.js",
		"/aui/dom-screen/dom-screen.js", "/aui/dom-style/dom-style.js",
		"/aui/event-base/event-base.js",
		"/aui/event-custom-base/event-custom-base.js",
		"/aui/event-custom-complex/event-custom-complex.js",
		"/aui/event-delegate/event-delegate.js",
		"/aui/event-focus/event-focus.js", "/aui/event-hover/event-hover.js",
		"/aui/event-key/event-key.js",
		"/aui/event-mouseenter/event-mouseenter.js",
		"/aui/event-mousewheel/event-mousewheel.js",
		"/aui/event-outside/event-outside.js",
		"/aui/event-resize/event-resize.js",
		"/aui/event-simulate/event-simulate.js",
		"/aui/event-synthetic/event-synthetic.js", "/aui/intl/intl.js",
		"/aui/io-base/io-base.js", "/aui/io-form/io-form.js",
		"/aui/io-queue/io-queue.js",
		"/aui/io-upload-iframe/io-upload-iframe.js", "/aui/io-xdr/io-xdr.js",
		"/aui/json-parse/json-parse.js",
		"/aui/json-stringify/json-stringify.js", "/aui/node-base/node-base.js",
		"/aui/node-core/node-core.js",
		"/aui/node-event-delegate/node-event-delegate.js",
		"/aui/node-event-simulate/node-event-simulate.js",
		"/aui/node-focusmanager/node-focusmanager.js",
		"/aui/node-pluginhost/node-pluginhost.js",
		"/aui/node-screen/node-screen.js", "/aui/node-style/node-style.js",
		"/aui/oop/oop.js", "/aui/plugin/plugin.js",
		"/aui/pluginhost-base/pluginhost-base.js",
		"/aui/pluginhost-config/pluginhost-config.js",
		"/aui/querystring-stringify-simple/querystring-stringify-simple.js",
		"/aui/queue-promote/queue-promote.js",
		"/aui/selector-css2/selector-css2.js",
		"/aui/selector-css3/selector-css3.js",
		"/aui/selector-native/selector-native.js", "/aui/selector/selector.js",
		"/aui/widget-base/widget-base.js",
		"/aui/widget-htmlparser/widget-htmlparser.js",
		"/aui/widget-skin/widget-skin.js",
		"/aui/widget-uievents/widget-uievents.js",
		"/aui/yui-throttle/yui-throttle.js",
		"/aui/aui-base-core/aui-base-core.js",
		"/aui/aui-base-lang/aui-base-lang.js",
		"/aui/aui-classnamemanager/aui-classnamemanager.js",
		"/aui/aui-component/aui-component.js",
		"/aui/aui-debounce/aui-debounce.js",
		"/aui/aui-delayed-task-deprecated/aui-delayed-task-deprecated.js",
		"/aui/aui-event-base/aui-event-base.js",
		"/aui/aui-event-input/aui-event-input.js",
		"/aui/aui-form-validator/aui-form-validator.js",
		"/aui/aui-node-base/aui-node-base.js",
		"/aui/aui-node-html5/aui-node-html5.js",
		"/aui/aui-selector/aui-selector.js", "/aui/aui-timer/aui-timer.js",
		"/liferay/dependency.js", "/liferay/language.js", "/liferay/util.js",
		"/liferay/form.js", "/liferay/form_placeholders.js", "/liferay/icon.js",
		"/liferay/menu.js", "/liferay/notice.js", "/liferay/poller.js"
	};

	@Reference
	private AbsolutePortalURLBuilderFactory _absolutePortalURLBuilderFactory;

	private BundleContext _bundleContext;
	private volatile List<String> _fileNames = new ArrayList<>();
	private volatile List<String> _fileNamesBarebone = new ArrayList<>();
	private long _lastModified;

	@Reference
	private Portal _portal;

}