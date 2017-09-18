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

package com.liferay.lcs.rest.internal.client;

import com.liferay.lcs.rest.client.LCSProject;
import com.liferay.lcs.rest.client.LCSProjectService;
import com.liferay.petra.json.web.service.client.JSONWebServiceInvocationException;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Igor Beslic
 */
@Component(immediate = true, service = LCSProjectService.class)
public class LCSProjectServiceImpl
	extends BaseLCSServiceImpl implements LCSProjectService {

	@Override
	public LCSProject addDefaultLCSProject() {
		try {
			return doPostToObject(LCSProject.class, _URL_LCS_PROJECT);
		}
		catch (JSONWebServiceInvocationException jsonwsie) {
			throw new RuntimeException(jsonwsie);
		}
	}

	@Override
	public List<LCSProject> getUserManageableLCSProjects() {
		List<LCSProject> remoteLCSProjects = null;

		try {
			remoteLCSProjects = doGetToList(
				LCSProject.class, _URL_LCS_PROJECT, "manage", "true");
		}
		catch (JSONWebServiceInvocationException jsonwsie) {
			throw new RuntimeException(jsonwsie);
		}

		List<LCSProject> lcsProjects = new ArrayList<LCSProject>();

		for (LCSProject lcsProject : remoteLCSProjects) {
			lcsProjects.add(lcsProject);
		}

		return lcsProjects;
	}

	private static final String _URL_LCS_PROJECT = "/o/osb-lcs-rest/LCSProject";

}