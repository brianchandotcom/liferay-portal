/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.scheduler;

import java.io.Serializable;

/**
 * @author Shuyang Zhou
 */
public interface SchedulerEntry extends Serializable {

	public String getDescription();

	public String getEventListenerClass();

	public TriggerConfiguration getTriggerConfiguration();

}