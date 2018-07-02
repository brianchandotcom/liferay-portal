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

package com.liferay.portal.kernel.executor;

import com.liferay.portal.kernel.concurrent.ThreadPoolExecutor;

/**
 * @author     Shuyang Zhou
 * @deprecated As of Judson (7.1.x), replaced by {@link
 *             com.liferay.petra.executor.PortalExecutorManager}
 */
@Deprecated
public interface PortalExecutorManager {

	public ThreadPoolExecutor getPortalExecutor(String name);

	public ThreadPoolExecutor getPortalExecutor(
		String name, boolean createIfAbsent);

	public ThreadPoolExecutor registerPortalExecutor(
		String name, ThreadPoolExecutor threadPoolExecutor);

	public void shutdown();

	public void shutdown(boolean interrupt);

}