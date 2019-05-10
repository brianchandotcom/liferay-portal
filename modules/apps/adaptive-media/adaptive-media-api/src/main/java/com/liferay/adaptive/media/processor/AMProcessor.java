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
 * Generates a particular type of media.
 *
 * <p>
 * The type parameter <code>M</code> specifies the model used by the processor
 * to generate the media. The type parameter <code>T</code> restricts the valid
 * {@link com.liferay.adaptive.media.AMAttribute} set available.
 * </p>
 *
 * @author Adolfo Pérez
 */
@ProviderType
public interface AMProcessor<M, T> {

	/**
	 * Completely removes any generated media for the model.
	 *
	 * @param  model the model for which all generated media is deleted
	 * @throws PortalException if an error occurred while calling any Liferay
	 *         services
	 */
	public void cleanUp(M model) throws PortalException;

	/**
	 * Generates the media for the model. Some implementations might not
	 * generate any media for the model.
	 *
	 * @param  model the model for which media is generated
	 * @throws PortalException if an error occurred while calling any Liferay
	 *         services
	 */
	public void process(M model) throws PortalException;

}