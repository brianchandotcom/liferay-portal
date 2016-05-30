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

package com.liferay.portal.target.platform.indexer;

import com.liferay.portal.target.platform.indexer.internal.LPKGIndexer;
import com.liferay.portal.target.platform.indexer.internal.TargetPlatformIndexer;
import com.liferay.portal.util.PropsValues;

import java.io.File;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Raymond Augé
 */
@Component(immediate = true, service = IndexerFactory.class)
public class IndexerFactory {

	@Activate
	public void activate(BundleContext bundleContext) {
		_systemBundle = bundleContext.getBundle(0);
	}

	public Indexer createLPKGIndexer(File lpkgFile) {
		return new LPKGIndexer(lpkgFile);
	}

	public Indexer createTargetPlatformIndexer() {
		return new TargetPlatformIndexer(
			_systemBundle, PropsValues.MODULE_FRAMEWORK_BASE_DIR + "/static",
			PropsValues.MODULE_FRAMEWORK_MODULES_DIR,
			PropsValues.MODULE_FRAMEWORK_PORTAL_DIR);
	}

	private Bundle _systemBundle;

}