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

package com.liferay.jenkins.results.parser.k8s;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import io.kubernetes.client.models.V1Pod;

import java.io.File;

/**
 * @author Kenji Heigel
 * @author Michael Hashimoto
 */
public class OraclePod extends Pod {

	@Override
	public void copyFileToPod(
		String sourceFilePath, String sourceHostname, String destFilePath) {

		System.out.println("sourceFilePath=" + sourceFilePath);
		System.out.println("sourceHostname=" + sourceHostname);
		System.out.println("destFilePath=" + destFilePath);

		exec(
			"/bin/bash", "-c",
			JenkinsResultsParserUtil.combine(
				"if [[ ! -d /home/oracle/.ssh ]] ; then ",
				"mkdir -p /home/oracle/.ssh ; ",
				"cp /mnt/ssh-secret-volume/* /home/oracle/.ssh/ ; ",
				"chmod 600 /home/oracle/.ssh/* ; fi"));

		File destFile = new File(destFilePath);

		exec(
			"/bin/bash", "-c",
			JenkinsResultsParserUtil.combine(
				"mkdir -p ", destFile.getParent(),
				" ; scp -o StrictHostKeyChecking=no ", sourceHostname, ":",
				sourceFilePath, " ", destFilePath));
	}

	protected OraclePod(V1Pod v1Pod) {
		super(v1Pod);
	}

}