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

package com.liferay.message.boards.internal.xstream.configurator;

import com.liferay.exportimport.kernel.xstream.XStreamAlias;
import com.liferay.exportimport.kernel.xstream.XStreamConverter;
import com.liferay.exportimport.kernel.xstream.XStreamType;
import com.liferay.message.boards.model.impl.MBBanImpl;
import com.liferay.message.boards.model.impl.MBCategoryImpl;
import com.liferay.message.boards.model.impl.MBMessageImpl;
import com.liferay.message.boards.model.impl.MBThreadFlagImpl;
import com.liferay.message.boards.model.impl.MBThreadImpl;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.xstream.configurator.XStreamConfigurator;

import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Mate Thurzo
 */
@Component(immediate = true, service = XStreamConfigurator.class)
public class MessageBoardsXStreamConfigurator implements XStreamConfigurator {

	@Override
	public List<XStreamType> getAllowedXStreamTypes() {
		return null;
	}

	@Override
	public List<XStreamAlias> getXStreamAliases() {
		return ListUtil.toList(_xStreamAliases);
	}

	@Override
	public List<XStreamConverter> getXStreamConverters() {
		return null;
	}

	@Activate
	protected void activate() {
		_xStreamAliases = new XStreamAlias[] {
			new XStreamAlias(MBBanImpl.class, "MBBan"),
			new XStreamAlias(MBCategoryImpl.class, "MBCategory"),
			new XStreamAlias(MBMessageImpl.class, "MBMessage"),
			new XStreamAlias(MBThreadImpl.class, "MBThread"),
			new XStreamAlias(MBThreadFlagImpl.class, "MBThreadFlag")
		};
	}

	private XStreamAlias[] _xStreamAliases;

}