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

package com.liferay.portal.search.web.internal.portlet.shared.task;

import com.liferay.portal.search.web.internal.portlet.shared.task.helper.PortletSharedRequestHelper;
import com.liferay.portal.search.web.portlet.shared.task.PortletSharedTask;
import com.liferay.portal.search.web.portlet.shared.task.PortletSharedTaskExecutor;

import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import javax.portlet.RenderRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 */
@Component(immediate = true, service = PortletSharedTaskExecutor.class)
public class PortletSharedTaskExecutorImpl
	implements PortletSharedTaskExecutor {

	@Override
	public <T> T executeOnlyOnce(
		PortletSharedTask<T> portletSharedTask, String attributeSuffix,
		RenderRequest renderRequest) {

		String attributeName = "LIFERAY_SHARED_" + attributeSuffix;

		Optional<FutureTask<T>> oldFutureTaskOptional;
		FutureTask<T> futureTask;

		synchronized (renderRequest) {
			oldFutureTaskOptional = portletSharedRequestHelper.getAttribute(
				attributeName, renderRequest);

			futureTask = oldFutureTaskOptional.orElseGet(
				() -> {
					FutureTask<T> newFutureTask = new FutureTask<>(
						portletSharedTask::execute);

					portletSharedRequestHelper.setAttribute(
						attributeName, newFutureTask, renderRequest);

					return newFutureTask;
				});
		}

		if (!oldFutureTaskOptional.isPresent()) {
			futureTask.run();
		}

		try {
			return futureTask.get();
		}
		catch (ExecutionException | InterruptedException exception) {
			throw new RuntimeException(exception);
		}
	}

	@Reference
	protected PortletSharedRequestHelper portletSharedRequestHelper;

}