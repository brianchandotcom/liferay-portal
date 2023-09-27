/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.internal.xstream.configurator;

import com.liferay.exportimport.kernel.xstream.XStreamAlias;
import com.liferay.exportimport.kernel.xstream.XStreamConverter;
import com.liferay.exportimport.kernel.xstream.XStreamType;
import com.liferay.friendly.url.model.impl.FriendlyURLEntryImpl;
import com.liferay.xstream.configurator.XStreamConfigurator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(service = XStreamConfigurator.class)
public class FriendlyURLEntryXStreamConfigurator
	implements XStreamConfigurator {

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
		return Collections.emptyList();
	}

	private final List<XStreamAlias> _xStreamAliases = Arrays.asList(
		new XStreamAlias(FriendlyURLEntryImpl.class, "FriendlyURLEntry"));
	private final List<XStreamType> _xStreamTypes = Arrays.asList(
		new XStreamType(FriendlyURLEntryImpl.class));

}