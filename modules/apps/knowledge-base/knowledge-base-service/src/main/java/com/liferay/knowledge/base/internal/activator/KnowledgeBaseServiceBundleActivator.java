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

package com.liferay.knowledge.base.internal.activator;

import com.liferay.portal.upgrade.release.BaseUpgradeServiceModuleRelease;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * @author Adolfo Pérez
 */
public class KnowledgeBaseServiceBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		BaseUpgradeServiceModuleRelease upgradeServiceModuleRelease =
			new BaseUpgradeServiceModuleRelease() {

				@Override
				protected String getNamespace() {
					return "KB";
				}

				@Override
				protected String getNewBundleSymbolicName() {
					return "com.liferay.knowledge.base.service";
				}

				@Override
				protected String getOldBundleSymbolicName() {
					return "knowledge-base-portlet";
				}

			};

		upgradeServiceModuleRelease.upgrade();
	}

	@Override
	public void stop(BundleContext bundleContext) {
	}

}