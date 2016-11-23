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

package com.liferay.portal.template.soy.internal;

import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateContextContributor;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.template.TemplateContextHelper;
import com.liferay.portal.template.TemplateResourceParser;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.wiring.BundleCapability;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;
import org.osgi.util.tracker.BundleTracker;

/**
 * @author Bruno Basto
 */
@Component(
	immediate = true,
	service = {SoyTemplateContextHelper.class, TemplateContextHelper.class}
)
public class SoyTemplateContextHelper extends TemplateContextHelper {

	public Object deserializeValue(Object value) {
		String json = _jsonSerializer.serializeDeep(value);

		return _jsonDeserializer.deserialize(json);
	}

	@Override
	public Map<String, Object> getHelperUtilities(
		ClassLoader classLoader, boolean restricted) {

		return Collections.emptyMap();
	}

	@Override
	public Set<String> getRestrictedVariables() {
		return SetUtil.fromArray(new String[] {TemplateConstants.NAMESPACE});
	}

	public Bundle getTemplateBundle(String templateId) {
		int pos = templateId.indexOf(TemplateConstants.BUNDLE_SEPARATOR);

		if (pos == -1) {
			throw new IllegalArgumentException(
				String.format(
					"The templateId \"%s\" does not map to a Soy template",
					templateId));
		}

		long bundleId = Long.valueOf(templateId.substring(0, pos));

		Bundle bundle = _bundleProvidersMap.get(bundleId);

		if (bundle == null) {
			throw new IllegalStateException(
				"There are no bundles providing " + bundleId);
		}

		return bundle;
	}

	@Override
	public void prepare(
		Map<String, Object> contextObjects, HttpServletRequest request) {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		contextObjects.put("locale", themeDisplay.getLocale());
		contextObjects.put("themeDisplay", themeDisplay);

		// Custom template context contributors

		for (TemplateContextContributor templateContextContributor :
				_templateContextContributors) {

			templateContextContributor.prepare(contextObjects, request);
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		int stateMask = Bundle.ACTIVE | Bundle.RESOLVED;

		_jsonDeserializer = JSONFactoryUtil.createJSONDeserializer();
		_jsonSerializer = JSONFactoryUtil.createJSONSerializer();

		_bundleTracker = new BundleTracker<>(
			bundleContext, stateMask,
			new SoyCapabilityBundleTrackerCustomizer(
				"soy", _bundleProvidersMap));

		_bundleTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_bundleTracker.close();
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(type=" + TemplateContextContributor.TYPE_GLOBAL + ")",
		unbind = "unregisterTemplateContextContributor"
	)
	protected void registerTemplateContextContributor(
		TemplateContextContributor templateContextContributor) {

		_templateContextContributors.add(templateContextContributor);
	}

	protected void unregisterTemplateContextContributor(
		TemplateContextContributor templateContextContributor) {

		_templateContextContributors.remove(templateContextContributor);
	}

	private final Map<Long, Bundle> _bundleProvidersMap =
		new ConcurrentHashMap<>();
	private BundleTracker<List<BundleCapability>> _bundleTracker;
	private JSONDeserializer<Object> _jsonDeserializer;
	private JSONSerializer _jsonSerializer;
	private final List<TemplateContextContributor>
		_templateContextContributors = new CopyOnWriteArrayList<>();

	@Reference(
		target = "(lang.type=" + TemplateConstants.LANG_TYPE_SOY + ")",
		unbind = "-"
	)
	private TemplateResourceParser _templateResourceParser;

}