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

package com.liferay.gradle.plugins.workspace.task;

import de.undercouch.gradle.tasks.download.Verify;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.gradle.api.GradleException;
import org.gradle.api.tasks.TaskAction;

/**
 * @author Simon Jiang
 */
public class VerifyBundleTask extends Verify {

	@Override
	@TaskAction
	public void verify() throws IOException, NoSuchAlgorithmException {
		if (getSrc() == null) {
			throw new IllegalArgumentException(
				"Please provide a file to verify");
		}

		if (getAlgorithm() == null) {
			throw new IllegalArgumentException(
				"Please provide the algorithm to use to calculate the " +
					"checksum");
		}

		if (getChecksum() == null) {
			throw new IllegalArgumentException(
				"Please provide a checksum to verify against");
		}

		String calculatedChecksum = null;
		MessageDigest messageDigest = MessageDigest.getInstance(getAlgorithm());

		try (FileInputStream fileInputStream = new FileInputStream(getSrc())) {
			byte[] bytes = new byte[1024];
			int read = 0;

			while ((read = fileInputStream.read(bytes)) != -1) {
				messageDigest.update(bytes, 0, read);
			}

			calculatedChecksum = _toHex(messageDigest.digest());
		}

		if (!calculatedChecksum.equalsIgnoreCase(getChecksum())) {
			File srcFile = getSrc();

			throw new GradleException(
				"Invalid checksum for file '" + srcFile.getName() +
					"'. Expected " + getChecksum().toLowerCase() + " but got " +
						calculatedChecksum.toLowerCase() +
							". Please remove file from " +
								srcFile.getAbsolutePath() + " and try again.");
		}
	}

	private String _toHex(byte[] barr) {
		StringBuilder result = new StringBuilder();

		for (byte b : barr) {
			result.append(String.format("%02X", b));
		}

		return result.toString();
	}

}