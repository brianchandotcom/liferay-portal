/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
				"Invalid checksum for " + srcFile.getName() + ". Expected " +
					getChecksum().toLowerCase() + ", but got " +
						calculatedChecksum.toLowerCase() + ". Please remove " +
							srcFile.getAbsolutePath() + " and try again.");
		}
	}

	private String _toHex(byte[] bytes) {
		StringBuilder stringBuilder = new StringBuilder();

		for (byte b : bytes) {
			stringBuilder.append(String.format("%02X", b));
		}

		return stringBuilder.toString();
	}

}