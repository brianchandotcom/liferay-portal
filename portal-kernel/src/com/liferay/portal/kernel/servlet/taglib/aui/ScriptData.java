/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.servlet.taglib.aui;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.util.Mergeable;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Pattern;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class ScriptData implements Mergeable<ScriptData>, Serializable {

	public void append(String portletId, JSFragment jsFragment) {
		if (Validator.isNull(portletId)) {
			portletId = StringPool.BLANK;
		}

		PortletData portletData = _portletDataMap.get(portletId);

		if (portletData == null) {
			portletData = new PortletData();

			PortletData oldPortletData = _portletDataMap.putIfAbsent(
				portletId, portletData);

			if (oldPortletData != null) {
				portletData = oldPortletData;
			}
		}

		portletData.add(jsFragment);
	}

	public void append(
		String portletId, String content, String modules,
		ModulesType modulesType) {

		if (Validator.isNull(modules)) {
			append(portletId, new JSFragment(content));
		}
		else {
			if (modulesType == ModulesType.AUI) {
				List<String> auiModules = new ArrayList<>();

				for (String module : StringUtil.split(modules)) {
					auiModules.add(StringUtil.trim(module));
				}

				append(
					portletId, new JSFragment(null, auiModules, content, null));
			}
			else {
				append(
					portletId,
					new JSFragment(
						_parseAMDRequires(modules), null, content, null));
			}
		}
	}

	public void append(
		String portletId, StringBundler contentSB, String modules,
		ModulesType modulesType) {

		append(portletId, contentSB.toString(), modules, modulesType);
	}

	public void mark() {
		for (PortletData portletData : _portletDataMap.values()) {
			portletData.mark();
		}
	}

	@Override
	public ScriptData merge(ScriptData scriptData) {
		if ((scriptData != null) && (scriptData != this)) {
			_portletDataMap.putAll(scriptData._portletDataMap);
		}

		return this;
	}

	public void reset() {
		for (PortletData portletData : _portletDataMap.values()) {
			portletData.reset();
		}
	}

	@Override
	public ScriptData split() {
		return new ScriptData();
	}

	public void writeTo(Writer writer) throws IOException {
		_portletDataRenderer.write(_portletDataMap.values(), writer);
	}

	public void writeTo(Writer writer, String portletId) throws IOException {
		PortletData portletData = _portletDataMap.remove(portletId);

		if (portletData == null) {
			return;
		}

		_portletDataRenderer.write(Collections.singleton(portletData), writer);
	}

	public static enum ModulesType {

		AUI, ES6

	}

	protected ConcurrentMap<String, PortletData> getPortletDataMap() {
		return _portletDataMap;
	}

	private String[] _getNameAndAlias(String requirePart) {
		requirePart = requirePart.trim();

		String[] parts = _whitespacePattern.split(requirePart, 4);

		if ((parts.length == 3) &&
			StringUtil.equalsIgnoreCase(parts[1], "as")) {

			return new String[] {parts[0], parts[2]};
		}

		return new String[] {requirePart, null};
	}

	private Collection<AMDRequire> _parseAMDRequires(String require) {
		List<AMDRequire> amdRequires = new ArrayList<>();

		String[] requireParts = require.split(StringPool.COMMA);

		for (String requirePart : requireParts) {
			String[] nameAndAlias = _getNameAndAlias(requirePart);

			AMDRequire amdRequire = null;

			if (nameAndAlias[1] == null) {
				amdRequire = new AMDRequire(nameAndAlias[0]);
			}
			else {
				amdRequire = new AMDRequire(nameAndAlias[1], nameAndAlias[0]);
			}

			amdRequires.add(amdRequire);
		}

		return amdRequires;
	}

	private static volatile PortletDataRenderer _portletDataRenderer;
	private static final ServiceTracker
		<PortletDataRenderer, PortletDataRenderer> _serviceTracker;
	private static final Pattern _whitespacePattern = Pattern.compile("\\s+");
	private static final long serialVersionUID = 1L;

	static {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		_serviceTracker =
			new ServiceTracker<PortletDataRenderer, PortletDataRenderer>(
				bundleContext, PortletDataRenderer.class,
				new ServiceTrackerCustomizer
					<PortletDataRenderer, PortletDataRenderer>() {

					@Override
					public PortletDataRenderer addingService(
						ServiceReference<PortletDataRenderer>
							serviceReference) {

						_portletDataRenderer = bundleContext.getService(
							serviceReference);

						return _portletDataRenderer;
					}

					@Override
					public void modifiedService(
						ServiceReference<PortletDataRenderer> serviceReference,
						PortletDataRenderer portletDataRenderer) {
					}

					@Override
					public void removedService(
						ServiceReference<PortletDataRenderer> serviceReference,
						PortletDataRenderer portletDataRenderer) {

						bundleContext.ungetService(serviceReference);
					}

				});

		_serviceTracker.open();
	}

	private final ConcurrentMap<String, PortletData> _portletDataMap =
		new ConcurrentHashMap<>();

}