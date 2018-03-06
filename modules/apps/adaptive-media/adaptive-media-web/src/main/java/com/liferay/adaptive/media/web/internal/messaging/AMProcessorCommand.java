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

package com.liferay.adaptive.media.web.internal.messaging;

import com.liferay.adaptive.media.processor.AMProcessor;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Adolfo Pérez
 */
public enum AMProcessorCommand {

	CLEAN_UP {

		@Override
		protected <M> void execute(
				AMProcessor<M, ?> amProcessor, M model, String modelId)
			throws PortalException {

			amProcessor.cleanUp(model);
		}

	},
	PROCESS {

		@Override
		protected <M> void execute(
				AMProcessor<M, ?> amProcessor, M model, String modelId)
			throws PortalException {

			amProcessor.process(model);
		}

	};

	protected abstract <M> void execute(
			AMProcessor<M, ?> amProcessor, M model, String modelId)
		throws PortalException;

}