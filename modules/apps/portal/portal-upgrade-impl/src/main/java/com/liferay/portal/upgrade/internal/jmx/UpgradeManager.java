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

package com.liferay.portal.upgrade.internal.jmx;

import com.liferay.portal.upgrade.internal.recorder.UpgradeRecorder;

import javax.management.DynamicMBean;
import javax.management.NotCompliantMBeanException;
import javax.management.StandardMBean;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luis Ortiz
 */
@Component(
	enabled = false,
	property = {
		"jmx.objectname=com.liferay.portal.monitoring:classification=upgrade_manager,name=UpgradeManager",
		"jmx.objectname.cache.key=UpgradeManager"
	},
	service = DynamicMBean.class
)
public class UpgradeManager
	extends StandardMBean implements UpgradeManagerMBean {

	public UpgradeManager() throws NotCompliantMBeanException {
		super(UpgradeManagerMBean.class);
	}

	@Override
	public String getResult() {
		return _upgradeRecorder.getResult();
	}

	@Override
	public String getType() {
		return _upgradeRecorder.getType();
	}

	@Reference
	private UpgradeRecorder _upgradeRecorder;

}