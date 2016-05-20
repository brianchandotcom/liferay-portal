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

package com.liferay.portal.target.platform.indexer.internal;

import aQute.bnd.build.model.EE;
import aQute.bnd.build.model.OSGI_CORE;
import aQute.bnd.osgi.resource.ResourceBuilder;

import biz.aQute.resolve.ResolverValidator;
import biz.aQute.resolve.ResolverValidator.Resolution;

import com.liferay.portal.target.platform.indexer.Indexer;
import com.liferay.portal.target.platform.indexer.Validator;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.FilenameFilter;

import java.net.URI;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Raymond Augé
 */
public class IndexValidator implements Validator {

	public void setIncludeTargetPlatform(boolean includeTargetPlatform) {
		_includeTargetPlatform = includeTargetPlatform;
	}

	@Override
	public List<String> validate(List<URI> indexURIs) throws Exception {
		if (_includeTargetPlatform) {
			indexURIs = new ArrayList<>(indexURIs);

			File targetPlatformDir = new File(
				PropsValues.MODULE_FRAMEWORK_BASE_DIR,
				Indexer.DIR_NAME_TARGET_PLATFORM);

			if (targetPlatformDir.exists() && targetPlatformDir.canRead()) {
				File[] indexFiles = targetPlatformDir.listFiles(
					new FilenameFilter() {

						@Override
						public boolean accept(File dir, String name) {
							if (name.endsWith(".xml") ||
								name.endsWith(".xml.gz")) {

								return true;
							}

							return false;
						}

					});

				for (File indexFile : indexFiles) {
					indexURIs.add(indexFile.toURI());
				}
			}
		}

		try (ResolverValidator resolverValidator = new ResolverValidator()) {
			List<String> messages = new ArrayList<>();

			ResourceBuilder resourceBuilder = new ResourceBuilder();

			resourceBuilder.addEE(EE.JavaSE_1_7);
			resourceBuilder.addManifest(OSGI_CORE.R6_0_0.getManifest());

			for (URI indexURI : indexURIs) {
				resolverValidator.addRepository(indexURI);
			}

			resolverValidator.setSystem(resourceBuilder.build());

			List<Resolution> resolutions = resolverValidator.validate();

			resolverValidator.check();

			for (Resolution resolution : resolutions) {
				String message = resolution.message;

				if (message == null) {
					continue;
				}

				if (message.startsWith(_MESSAGE_PREFIX)) {
					message = message.substring(_MESSAGE_PREFIX.length());
				}

				messages.add(message);
			}

			return messages;
		}
	}

	private static final String _MESSAGE_PREFIX =
		"Unable to resolve <<INITIAL>> version=null: ";

	private boolean _includeTargetPlatform;

}