/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
 * @author Máté Thurzó
 */
@Component(service = XStreamConfigurator.class)
public class MessageBoardsXStreamConfigurator implements XStreamConfigurator {

	@Override
	public List<XStreamType> getAllowedXStreamTypes() {
		return null;
	}

	@Override
	public List<XStreamAlias> getXStreamAliases() {
		return ListUtil.fromArray(_xStreamAliases);
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