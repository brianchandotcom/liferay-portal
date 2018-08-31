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

package com.liferay.portal.fabric.status;

import java.lang.management.OperatingSystemMXBean;

/**
 * @author Shuyang Zhou
 */
public interface AdvancedOperatingSystemMXBean extends OperatingSystemMXBean {

	@JMXProxyUtil.Optional
	public Long getCommittedVirtualMemorySize();

	@JMXProxyUtil.Optional
	public Long getFreePhysicalMemorySize();

	@JMXProxyUtil.Optional
	public Long getFreeSwapSpaceSize();

	@JMXProxyUtil.Optional
	public Long getMaxFileDescriptorCount();

	@JMXProxyUtil.Optional
	public Long getOpenFileDescriptorCount();

	@JMXProxyUtil.Optional
	public Double getProcessCpuLoad();

	@JMXProxyUtil.Optional
	public Long getProcessCpuTime();

	@JMXProxyUtil.Optional
	public Double getSystemCpuLoad();

	@JMXProxyUtil.Optional
	public Long getTotalPhysicalMemorySize();

	@JMXProxyUtil.Optional
	public Long getTotalSwapSpaceSize();

}