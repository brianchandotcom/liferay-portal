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

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.SingleVMPool;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateManager;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.template.BaseTemplateManager;
import com.liferay.portal.template.TemplateContextHelper;
import com.liferay.portal.template.soy.SoyTemplateResource;
import com.liferay.portal.template.soy.SoyTemplateResourceFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.BundleTracker;

/**
 * @author Bruno Basto
 */
@Component(
	immediate = true,
	property = "language.type=" + TemplateConstants.LANG_TYPE_SOY,
	service = {SoyManager.class, TemplateManager.class}
)
public class SoyManager extends BaseTemplateManager {

	@Override
	public void destroy() {
		templateContextHelper.removeAllHelperUtilities();
	}

	@Override
	public void destroy(ClassLoader classLoader) {
		templateContextHelper.removeHelperUtilities(classLoader);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	public List<TemplateResource> getAllTemplateResources() {
		return _soyCapabilityBundleTrackerCustomizer.getAllTemplateResources();
	}

	@Override
	public String getName() {
		return TemplateConstants.LANG_TYPE_SOY;
	}

	@Override
	public void init() {
	}

	@Reference(unbind = "-")
	public void setSingleVMPool(SingleVMPool singleVMPool) {
		_soyTofuCacheHandler = new SoyTofuCacheHandler(
			(PortalCache<String, SoyTofuCacheBag>)singleVMPool.getPortalCache(
				SoyTemplate.class.getName()));
	}

	@Override
	@Reference(service = SoyTemplateContextHelper.class, unbind = "-")
	public void setTemplateContextHelper(
		TemplateContextHelper templateContextHelper) {

		super.setTemplateContextHelper(templateContextHelper);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		int stateMask = ~Bundle.INSTALLED & ~Bundle.UNINSTALLED;

		_soyCapabilityBundleTrackerCustomizer =
			new SoyTemplateResourceBundleTrackerCustomizer(
				_soyTofuCacheHandler, _soyProviderCapabilityBundleRegister,
				_soyTemplateResourceFactory);

		_bundleTracker = new BundleTracker<>(
			bundleContext, stateMask, _soyCapabilityBundleTrackerCustomizer);

		_bundleTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_bundleTracker.close();
	}

	@Override
	protected Template doGetTemplate(
		TemplateResource templateResource, boolean restricted,
		Map<String, Object> helperUtilities) {

		SoyTemplateResource soyTemplateResource = null;

		if (templateResource == null) {
			soyTemplateResource =
				_soyCapabilityBundleTrackerCustomizer.getSoyTemplateResource();
		}
		else if (templateResource instanceof SoyTemplateResource) {
			soyTemplateResource = (SoyTemplateResource)templateResource;
		}
		else {
			soyTemplateResource =
				_soyTemplateResourceFactory.createSoyTemplateResource(
					Collections.singletonList(templateResource));
		}

		return new SoyTemplate(
			soyTemplateResource, helperUtilities,
			(SoyTemplateContextHelper)templateContextHelper,
			_soyTofuCacheHandler, _soyTemplateResourceFactory, restricted);
	}

	@Reference(unbind = "-")
	protected void setSoyProviderCapabilityBundleRegister(
		SoyProviderCapabilityBundleRegister
			soyProviderCapabilityBundleRegister) {

		_soyProviderCapabilityBundleRegister =
			soyProviderCapabilityBundleRegister;
	}

	@Reference(unbind = "-")
	protected void setSoyTemplateBundleResourceParser(
		SoyTemplateBundleResourceParser soyTemplateBundleResourceParser) {
	}

	private BundleTracker<List<TemplateResource>> _bundleTracker;
	private SoyTemplateResourceBundleTrackerCustomizer
		_soyCapabilityBundleTrackerCustomizer;
	private SoyProviderCapabilityBundleRegister
		_soyProviderCapabilityBundleRegister;

	@Reference
	private SoyTemplateResourceFactory _soyTemplateResourceFactory;

	private SoyTofuCacheHandler _soyTofuCacheHandler;

}