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

package com.liferay.portal.template.velocity.internal;

import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.TemplateResourceLoader;
import com.liferay.portal.template.DefaultTemplateResourceLoader;
import com.liferay.portal.template.TemplateResourceParser;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Igor Spasic
 * @author Peter Fellwock
 */
@Component(
	immediate = true,
	service = {
		TemplateResourceLoader.class, VelocityTemplateResourceLoader.class
	}
)
public class VelocityTemplateResourceLoader implements TemplateResourceLoader {

	@Override
	public void clearCache() {
		_defaultTemplateResourceLoader.clearCache();
	}

	@Override
	public void clearCache(String templateId) {
		_defaultTemplateResourceLoader.clearCache(templateId);
	}

	@Deactivate
	@Override
	public void destroy() {
		_defaultTemplateResourceLoader.destroy();
	}

	@Override
	public String getName() {
		return _defaultTemplateResourceLoader.getName();
	}

	@Override
	public TemplateResource getTemplateResource(String templateId) {
		return _defaultTemplateResourceLoader.getTemplateResource(templateId);
	}

	@Override
	public boolean hasTemplateResource(String templateId) {
		return _defaultTemplateResourceLoader.hasTemplateResource(templateId);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_defaultTemplateResourceLoader = new DefaultTemplateResourceLoader(
			TemplateConstants.LANG_TYPE_VM, _templateResourceParsers,
			_velocityTemplateResourceCache);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(lang.type=" + TemplateConstants.LANG_TYPE_VM + ")"
	)
	protected void setTemplateResourceParser(
		TemplateResourceParser templateResourceParser) {

		_templateResourceParsers.add(templateResourceParser);
	}

	protected void unsetTemplateResourceParser(
		TemplateResourceParser templateResourceParser) {

		_templateResourceParsers.remove(templateResourceParser);
	}

	private static volatile DefaultTemplateResourceLoader
		_defaultTemplateResourceLoader;

	private final Set<TemplateResourceParser> _templateResourceParsers =
		Collections.newSetFromMap(new ConcurrentHashMap<>());

	@Reference
	private VelocityTemplateResourceCache _velocityTemplateResourceCache;

}