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

package com.liferay.portal.monitoring.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Michael C. Han
 */
@ExtendedObjectClassDefinition(category = "foundation")
@Meta.OCD(
	id = "com.liferay.portal.monitoring.configuration.MonitoringConfiguration",
	localization = "content/Language", name = "monitoring-configuration-name"
)
public interface MonitoringConfiguration {

	@Meta.AD(deflt = "true", required = false)
	public boolean monitorEnabled();

	@Meta.AD(deflt = "200", required = false)
	public int monitoringMessageMaxQueueSize();

	@Meta.AD(deflt = "true", required = false)
	public boolean monitorPortalRequest();

	@Meta.AD(deflt = "true", required = false)
	public boolean monitorPortletActionRequest();

	@Meta.AD(deflt = "true", required = false)
	public boolean monitorPortletEventRequest();

	@Meta.AD(deflt = "true", required = false)
	public boolean monitorPortletRenderRequest();

	@Meta.AD(deflt = "true", required = false)
	public boolean monitorPortletResourceRequest();

	@Meta.AD(deflt = "true", required = false)
	public boolean monitorServiceRequest();

	@Meta.AD(deflt = "false", required = false)
	public boolean showPerRequestDataSample();

}