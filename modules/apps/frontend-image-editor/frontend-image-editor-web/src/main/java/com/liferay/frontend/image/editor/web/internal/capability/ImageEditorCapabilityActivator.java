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

package com.liferay.frontend.image.editor.web.internal.capability;

import com.liferay.frontend.image.editor.capability.ImageEditorCapability;
import com.liferay.frontend.image.editor.web.internal.capability.brightness.ImageEditorCapabilityBrightness;
import com.liferay.frontend.image.editor.web.internal.capability.contrast.ImageEditorCapabilityContrast;
import com.liferay.frontend.image.editor.web.internal.capability.crop.ImageEditorCapabilityCrop;
import com.liferay.frontend.image.editor.web.internal.capability.effects.ImageEditorCapabilityEffects;
import com.liferay.frontend.image.editor.web.internal.capability.resize.ImageEditorCapabilityResize;
import com.liferay.frontend.image.editor.web.internal.capability.rotate.ImageEditorCapabilityRotate;
import com.liferay.frontend.image.editor.web.internal.capability.saturation.ImageEditorCapabilitySaturation;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.ServletContext;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = {})
public class ImageEditorCapabilityActivator {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_register(
			bundleContext, new ImageEditorCapabilityBrightness(_servletContext),
			"com.liferay.frontend.image.editor.capability.category", "adjust",
			"com.liferay.frontend.image.editor.capability.controls",
			"brightness", "com.liferay.frontend.image.editor.capability.icon",
			"effects", "com.liferay.frontend.image.editor.capability.name",
			"brightness", "com.liferay.frontend.image.editor.capability.type",
			"tool");
		_register(
			bundleContext, new ImageEditorCapabilityContrast(_servletContext),
			"com.liferay.frontend.image.editor.capability.category", "adjust",
			"com.liferay.frontend.image.editor.capability.controls", "contrast",
			"com.liferay.frontend.image.editor.capability.icon", "effects",
			"com.liferay.frontend.image.editor.capability.name", "contrast",
			"com.liferay.frontend.image.editor.capability.type", "tool");
		_register(
			bundleContext, new ImageEditorCapabilityCrop(_servletContext),
			"com.liferay.frontend.image.editor.capability.category",
			"transform",
			"com.liferay.frontend.image.editor.capability.controls", "crop",
			"com.liferay.frontend.image.editor.capability.icon", "transform",
			"com.liferay.frontend.image.editor.capability.name", "crop",
			"com.liferay.frontend.image.editor.capability.type", "tool");
		_register(
			bundleContext, new ImageEditorCapabilityEffects(_servletContext),
			"com.liferay.frontend.image.editor.capability.category", "effects",
			"com.liferay.frontend.image.editor.capability.controls", "effects",
			"com.liferay.frontend.image.editor.capability.icon", "magic",
			"com.liferay.frontend.image.editor.capability.name", "effects",
			"com.liferay.frontend.image.editor.capability.type", "tool");
		_register(
			bundleContext, new ImageEditorCapabilityResize(_servletContext),
			"com.liferay.frontend.image.editor.capability.category",
			"transform",
			"com.liferay.frontend.image.editor.capability.controls", "resize",
			"com.liferay.frontend.image.editor.capability.icon", "transform",
			"com.liferay.frontend.image.editor.capability.name", "resize",
			"com.liferay.frontend.image.editor.capability.type", "tool");
		_register(
			bundleContext, new ImageEditorCapabilityRotate(_servletContext),
			"com.liferay.frontend.image.editor.capability.category",
			"transform",
			"com.liferay.frontend.image.editor.capability.controls", "rotate",
			"com.liferay.frontend.image.editor.capability.icon", "transform",
			"com.liferay.frontend.image.editor.capability.name", "rotate",
			"com.liferay.frontend.image.editor.capability.type", "tool");
		_register(
			bundleContext, new ImageEditorCapabilitySaturation(_servletContext),
			"com.liferay.frontend.image.editor.capability.category", "adjust",
			"com.liferay.frontend.image.editor.capability.controls",
			"saturation", "com.liferay.frontend.image.editor.capability.icon",
			"effects", "com.liferay.frontend.image.editor.capability.name",
			"saturation", "com.liferay.frontend.image.editor.capability.type",
			"tool");
	}

	@Deactivate
	protected void deactivate() {
		for (ServiceRegistration<ImageEditorCapability> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}

		_serviceRegistrations.clear();
	}

	private void _register(
		BundleContext bundleContext,
		ImageEditorCapability imageEditorCapability, String... properties) {

		_serviceRegistrations.add(
			bundleContext.registerService(
				ImageEditorCapability.class, imageEditorCapability,
				new HashMapDictionary<>(MapUtil.fromArray(properties))));
	}

	private final List<ServiceRegistration<ImageEditorCapability>>
		_serviceRegistrations = new CopyOnWriteArrayList<>();

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.frontend.image.editor.web)"
	)
	private ServletContext _servletContext;

}