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

import aQute.bnd.annotation.ProviderType;

import com.liferay.adaptive.media.internal.messaging.AdaptiveMediaProcessorCommand;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * Generates a particular type of media asynchronously.
 *
 * <p>
 * This processor will typically delegate the generation of the media in {@link
 * AdaptiveMediaProcessor} by invoking it in an asynchronous manner.
 * </p>
 *
 * <p>
 * The type parameter <code>M</code> specifies the model used by the processor
 * to generate the media. The type parameter <code>T</code> restricts the valid
 * {@link com.liferay.adaptive.media.AdaptiveMediaAttribute} set available.
 * </p>
 *
 * @review
 *
 * @author Sergio González
 */
@ProviderType
public interface AdaptiveMediaAsyncProcessor<M, T> {

	/**
	 * Removes the model from the queue of models that are pending on a
	 * particular command.
	 *
	 * @param command the command that is pending to be executed on the model
	 * @param modelId the model ID to be removed from the queue
	 *
	 * @review
	 */
	public void cleanQueue(
		AdaptiveMediaProcessorCommand command, String modelId);

	/**
	 * Asynchronously completely removes any generated media for the model.
	 *
	 * @param  model the model for which all generated media is deleted
	 * @param  modelId the model ID for which all generated media is deleted
	 * @throws PortalException if an error occurred while calling any Liferay
	 *         services
	 *
	 * @review
	 */
	public void triggerCleanUp(M model, String modelId) throws PortalException;

	/**
	 * Asynchronously generates the media for the model
	 *
	 * @param  model the model for which media is generated
	 * @param  modelId the model ID for which media is generated
	 * @throws PortalException if an error occurred while calling any Liferay
	 *         services
	 *
	 * @review
	 */
	public void triggerProcess(M model, String modelId) throws PortalException;

}