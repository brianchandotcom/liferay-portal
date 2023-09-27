/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@Component(service = XStreamConfigurator.class)
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