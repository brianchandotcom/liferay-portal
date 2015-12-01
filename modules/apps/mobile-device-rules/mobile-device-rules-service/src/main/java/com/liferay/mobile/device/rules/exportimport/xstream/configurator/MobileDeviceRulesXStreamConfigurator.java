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

package com.liferay.mobile.device.rules.exportimport.xstream.configurator;

import com.liferay.exportimport.xstream.configurator.XStreamConfigurator;
import com.liferay.mobile.device.rules.model.impl.MDRActionImpl;
import com.liferay.mobile.device.rules.model.impl.MDRRuleGroupImpl;
import com.liferay.mobile.device.rules.model.impl.MDRRuleGroupInstanceImpl;
import com.liferay.mobile.device.rules.model.impl.MDRRuleImpl;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portlet.exportimport.xstream.XStreamAlias;
import com.liferay.portlet.exportimport.xstream.XStreamConverter;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Mate Thurzo
 */
@Component(immediate = true, service = XStreamConfigurator.class)
public class MobileDeviceRulesXStreamConfigurator
	implements XStreamConfigurator {

	@Override
	public List<XStreamAlias> getXStreamAliases() {
		return ListUtil.toList(_xStreamAliases);
	}

	@Override
	public List<XStreamConverter> getXStreamConverters() {
		return null;
	}

	protected void activate() {
		_xStreamAliases = new XStreamAlias[] {
			new XStreamAlias(MDRActionImpl.class, "MDRAction"),
			new XStreamAlias(MDRRuleImpl.class, "MDRRule"),
			new XStreamAlias(MDRRuleGroupImpl.class, "MDRRuleGroup"),
			new XStreamAlias(
				MDRRuleGroupInstanceImpl.class, "MDRRuleGroupInstance")
		};
	}

	private XStreamAlias[] _xStreamAliases;

}