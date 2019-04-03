/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.metrics.internal.petra.executor;

import com.liferay.petra.concurrent.ThreadPoolHandlerAdapter;
import com.liferay.petra.executor.PortalExecutorConfig;
import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.portal.kernel.util.NamedThreadFactory;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.osgi.service.component.annotations.Component;

/**
 * @author Inácio Nery
 */
@Component(
	immediate = true,
	service = {
		PortalExecutorConfig.class, WorkflowMetricsPortalExecutorConfig.class
	}
)
public class WorkflowMetricsPortalExecutorConfig extends PortalExecutorConfig {

	public WorkflowMetricsPortalExecutorConfig() {
		super(
			WorkflowMetricsPortalExecutor.class.getName(), 1, 1, 60,
			TimeUnit.SECONDS, Integer.MAX_VALUE,
			new NamedThreadFactory(
				WorkflowMetricsPortalExecutor.class.getName(),
				Thread.NORM_PRIORITY, PortalClassLoaderUtil.getClassLoader()),
			new ThreadPoolExecutor.AbortPolicy(),
			new ThreadPoolHandlerAdapter() {

				@Override
				public void afterExecute(
					Runnable runnable, Throwable throwable) {

					CentralizedThreadLocal.clearShortLivedThreadLocals();
				}

			});
	}

}