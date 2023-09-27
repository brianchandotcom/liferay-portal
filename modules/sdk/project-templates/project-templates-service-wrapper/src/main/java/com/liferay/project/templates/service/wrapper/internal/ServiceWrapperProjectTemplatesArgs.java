/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.project.templates.service.wrapper.internal;

import com.beust.jcommander.Parameter;

import com.liferay.project.templates.extensions.ProjectTemplatesArgsExt;

/**
 * @author Gregory Amerson
 */
public class ServiceWrapperProjectTemplatesArgs
	implements ProjectTemplatesArgsExt {

	public String getService() {
		return _service;
	}

	@Override
	public String getTemplateName() {
		return "service-wrapper";
	}

	public void setService(String service) {
		_service = service;
	}

	@Parameter(
		description = "Provide the name of the service to be implemented.",
		names = "--service", required = true
	)
	private String _service;

}