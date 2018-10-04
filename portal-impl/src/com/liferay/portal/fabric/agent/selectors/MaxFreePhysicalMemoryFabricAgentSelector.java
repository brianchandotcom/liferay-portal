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

package com.liferay.portal.fabric.agent.selectors;

import com.liferay.petra.process.ProcessCallable;
import com.liferay.portal.fabric.agent.FabricAgent;
import com.liferay.portal.fabric.status.AdvancedOperatingSystemMXBean;
import com.liferay.portal.fabric.status.FabricStatus;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author Shuyang Zhou
 */
public class MaxFreePhysicalMemoryFabricAgentSelector
	implements FabricAgentSelector {

	@Override
	public Collection<FabricAgent> select(
		Collection<FabricAgent> fabricAgents,
		ProcessCallable<?> processCallable) {

		if (fabricAgents.isEmpty()) {
			return fabricAgents;
		}

		return Collections.singleton(
			Collections.max(fabricAgents, _COMPARATOR));
	}

	private static final Comparator<FabricAgent> _COMPARATOR =
		new MaxFreePhysicalMemoryComparator();

	private static class MaxFreePhysicalMemoryComparator
		implements Comparator<FabricAgent> {

		@Override
		public int compare(FabricAgent fabricAgent1, FabricAgent fabricAgent2) {
			FabricStatus fabricStatus1 = fabricAgent1.getFabricStatus();

			AdvancedOperatingSystemMXBean advancedOperatingSystemMXBean1 =
				fabricStatus1.getAdvancedOperatingSystemMXBean();

			Long freePhysicalMemorySize1 =
				advancedOperatingSystemMXBean1.getFreePhysicalMemorySize();

			FabricStatus fabricStatus2 = fabricAgent2.getFabricStatus();

			AdvancedOperatingSystemMXBean advancedOperatingSystemMXBean2 =
				fabricStatus2.getAdvancedOperatingSystemMXBean();

			Long freePhysicalMemorySize2 =
				advancedOperatingSystemMXBean2.getFreePhysicalMemorySize();

			if ((freePhysicalMemorySize1 == null) &&
				(freePhysicalMemorySize2 == null)) {

				return 0;
			}

			if (freePhysicalMemorySize1 == null) {
				return -1;
			}

			if (freePhysicalMemorySize2 == null) {
				return 1;
			}

			return freePhysicalMemorySize1.compareTo(freePhysicalMemorySize2);
		}

	}

}