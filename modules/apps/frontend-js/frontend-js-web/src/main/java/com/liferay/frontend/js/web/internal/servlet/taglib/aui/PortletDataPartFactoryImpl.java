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

package com.liferay.frontend.js.web.internal.servlet.taglib.aui;

import com.liferay.frontend.js.web.internal.servlet.taglib.aui.part.AMDPortletDataPart;
import com.liferay.frontend.js.web.internal.servlet.taglib.aui.part.AUIPortletDataPart;
import com.liferay.frontend.js.web.internal.servlet.taglib.aui.part.ESMPortletDataPart;
import com.liferay.frontend.js.web.internal.servlet.taglib.aui.part.SimplePortletDataPart;
import com.liferay.portal.kernel.servlet.taglib.aui.ESMImport;
import com.liferay.portal.kernel.servlet.taglib.aui.PortletDataPart;
import com.liferay.portal.kernel.servlet.taglib.aui.PortletDataPartFactory;

import java.util.Collection;

import org.osgi.service.component.annotations.Component;

/**
 * @author Iván Zaera Avellón
 */
@Component(immediate = true, service = PortletDataPartFactory.class)
public class PortletDataPartFactoryImpl implements PortletDataPartFactory {

	@Override
	public PortletDataPart createAMDPortletDataPart(
		String content, String amdRequire) {

		return new AMDPortletDataPart(content, amdRequire);
	}

	@Override
	public PortletDataPart createAUIPortletDataPart(
		String content, String modules) {

		return new AUIPortletDataPart(content, modules);
	}

	@Override
	public PortletDataPart createESMPortletDataPart(
		String content, Collection<ESMImport> esmImports) {

		return new ESMPortletDataPart(content, null, esmImports);
	}

	@Override
	public PortletDataPart createMixedPortletDataPart(
		String content, String amdRequire, Collection<ESMImport> esmImports) {

		return new ESMPortletDataPart(content, amdRequire, esmImports);
	}

	@Override
	public PortletDataPart createSimplePortletDataPart(String content) {
		return new SimplePortletDataPart(content);
	}

}