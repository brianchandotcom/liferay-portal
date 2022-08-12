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

package com.liferay.commerce.product.internal.xstream.configurator;

import com.liferay.commerce.product.model.impl.CPAttachmentFileEntryImpl;
import com.liferay.exportimport.kernel.xstream.XStreamAlias;
import com.liferay.exportimport.kernel.xstream.XStreamConverter;
import com.liferay.exportimport.kernel.xstream.XStreamType;
import com.liferay.xstream.configurator.XStreamConfigurator;

import java.util.Arrays;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Danny Situ
 */
@Component(
	enabled = false, immediate = true, service = XStreamConfigurator.class
)
public class CPAttachmentXStreamConfigurator implements XStreamConfigurator {

	@Override
	public List<XStreamType> getAllowedXStreamTypes() {
		return _xStreamTypes;
	}

	@Override
	public List<XStreamAlias> getXStreamAliases() {
		return _xStreamAliases;
	}

	@Override
	public List<XStreamConverter> getXStreamConverters() {
		return null;
	}

	private final List<XStreamAlias> _xStreamAliases = Arrays.asList(
		new XStreamAlias(
			CPAttachmentFileEntryImpl.class, "CPAttachmentFileEntry"));
	private final List<XStreamType> _xStreamTypes = Arrays.asList(
		new XStreamType(CPAttachmentFileEntryImpl.class));

}