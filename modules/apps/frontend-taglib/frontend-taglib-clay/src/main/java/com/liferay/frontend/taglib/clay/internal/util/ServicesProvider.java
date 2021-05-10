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

package com.liferay.frontend.taglib.clay.internal.util;

import com.liferay.frontend.js.module.launcher.JSModuleResolver;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetDisplayViewSerializer;
import com.liferay.frontend.taglib.clay.data.set.filter.ClayDataSetFilterSerializer;
import com.liferay.frontend.view.state.active.FrontendViewStateActiveSettingsFactory;
import com.liferay.portal.template.react.renderer.ReactRenderer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Chema Balsas
 */
@Component(service = {})
public class ServicesProvider {

	public static ClayDataSetDisplayViewSerializer
		getClayDataSetDisplayViewSerializer() {

		return _clayDataSetDisplayViewSerializer;
	}

	public static ClayDataSetFilterSerializer getClayDataSetFilterSerializer() {
		return _clayDataSetFilterSerializer;
	}

	public static FrontendViewStateActiveSettingsFactory
		getFrontendViewStateActiveSettingsFactory() {

		return _frontendViewStateActiveSettingsFactory;
	}

	public static JSModuleResolver getJSModuleResolver() {
		return _jsModuleResolver;
	}

	public static ReactRenderer getReactRenderer() {
		return _reactRenderer;
	}

	@Reference(unbind = "-")
	public void setFrontendViewStateActiveSettingsFactory(
		FrontendViewStateActiveSettingsFactory
			frontendViewStateActiveSettingsFactory) {

		_frontendViewStateActiveSettingsFactory =
			frontendViewStateActiveSettingsFactory;
	}

	@Reference(unbind = "-")
	public void setJSModuleResolver(JSModuleResolver jsModuleResolver) {
		_jsModuleResolver = jsModuleResolver;
	}

	@Reference(unbind = "-")
	public void setReactRenderer(ReactRenderer reactRenderer) {
		_reactRenderer = reactRenderer;
	}

	@Reference(unbind = "-")
	protected void setClayDataSetDisplayViewSerializer(
		ClayDataSetDisplayViewSerializer clayDataSetDisplayViewSerializer) {

		_clayDataSetDisplayViewSerializer = clayDataSetDisplayViewSerializer;
	}

	@Reference(unbind = "-")
	protected void setClayDataSetFilterSerializer(
		ClayDataSetFilterSerializer clayDataSetFilterSerializer) {

		_clayDataSetFilterSerializer = clayDataSetFilterSerializer;
	}

	private static ClayDataSetDisplayViewSerializer
		_clayDataSetDisplayViewSerializer;
	private static ClayDataSetFilterSerializer _clayDataSetFilterSerializer;
	private static FrontendViewStateActiveSettingsFactory
		_frontendViewStateActiveSettingsFactory;
	private static JSModuleResolver _jsModuleResolver;
	private static ReactRenderer _reactRenderer;

}