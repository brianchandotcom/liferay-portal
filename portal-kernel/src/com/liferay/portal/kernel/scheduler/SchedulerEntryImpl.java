/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.scheduler;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

/**
 * @author Shuyang Zhou
 */
public class SchedulerEntryImpl implements SchedulerEntry {

	public SchedulerEntryImpl(
		String eventListenerClass, TriggerConfiguration triggerConfiguration) {

		this(eventListenerClass, triggerConfiguration, StringPool.BLANK);
	}

	public SchedulerEntryImpl(
		String eventListenerClass, TriggerConfiguration triggerConfiguration,
		String description) {

		_eventListenerClass = eventListenerClass;
		_triggerConfiguration = triggerConfiguration;
		_description = description;
	}

	@Override
	public String getDescription() {
		return _description;
	}

	@Override
	public String getEventListenerClass() {
		return _eventListenerClass;
	}

	@Override
	public TriggerConfiguration getTriggerConfiguration() {
		return _triggerConfiguration;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{description=", _description, ", eventListenerClass=",
			_eventListenerClass, ", triggerConfiguration=",
			_triggerConfiguration, "}");
	}

	private String _description;
	private String _eventListenerClass;
	private TriggerConfiguration _triggerConfiguration;

}