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

	/**
	 * Starts verifying
	 * @throws IOException if the file could not be verified
	 * @throws NoSuchAlgorithmException if the given algorithm is not available
	 */
	@TaskAction
	@Override
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

		// calculate file's checksum

		MessageDigest md = MessageDigest.getInstance(getAlgorithm());
		String calculatedChecksum;

		try (FileInputStream fileInputStream = new FileInputStream(getSrc())) {
			byte[] buf = new byte[1024];
			int read;

			while ((read = fileInputStream.read(buf)) != -1) {
				md.update(buf, 0, read);
			}

			calculatedChecksum = _toHex(md.digest());
		}

		// verify checksum

		if (!calculatedChecksum.equalsIgnoreCase(getChecksum())) {
			throw new GradleException(
				"Invalid checksum for file '" + getSrc().getName() +
					"'. Expected " + getChecksum().toLowerCase() + " but got " +
						calculatedChecksum.toLowerCase() +
							". Please remove file from " +
								getSrc().getAbsolutePath() + ", try again.");
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