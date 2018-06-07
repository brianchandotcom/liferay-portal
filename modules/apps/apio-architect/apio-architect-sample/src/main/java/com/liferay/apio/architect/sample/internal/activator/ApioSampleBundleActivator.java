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

package com.liferay.apio.architect.sample.internal.activator;

import static java.util.concurrent.CompletableFuture.runAsync;

import com.liferay.apio.architect.sample.internal.model.BlogPostingCommentModel;
import com.liferay.apio.architect.sample.internal.model.BlogPostingModel;
import com.liferay.apio.architect.sample.internal.model.PersonModel;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * Initiates the in-memory databases by calling the different {@code compute}
 * methods in each model class asynchronously.
 *
 * @author Alejandro Hernández
 * @review
 */
public class ApioSampleBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		runAsync(
			() -> {
				PersonModel.compute();

				BlogPostingModel.compute();

				BlogPostingCommentModel.compute();
			});
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
	}

}