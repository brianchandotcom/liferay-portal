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

package com.liferay.adaptive.media.processor;

import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Generates a specific type of media asynchronously.
 *
 * <p>
 * This processor delegates the generation of the media in {@link AMProcessor}
 * by invoking it in an asynchronous manner.
 * </p>
 *
 * <p>
 * The type parameter <code>M</code> specifies the model used by the processor
 * to generate the media. The type parameter <code>T</code> restricts the valid
 * {@link com.liferay.adaptive.media.AMAttribute} set available.
 * </p>
 *
 * @author Sergio González
 */
@ProviderType
public interface AMAsyncProcessor<M, T> {

	/**
	 * Asynchronously removes any generated media from the model.
	 *
	 * @param  model the model from which to remove all generated media
	 * @param  modelId the model's ID
	 * @throws PortalException if an error occurred while calling any Liferay
	 *         services
	 */
	public void triggerCleanUp(M model, String modelId) throws PortalException;

	/**
	 * Asynchronously generates the media for the model.
	 *
	 * @param  model the model for which media is generated
	 * @param  modelId the model's ID
	 * @throws PortalException if an error occurred while calling any Liferay
	 *         services
	 */
	public void triggerProcess(M model, String modelId) throws PortalException;

}