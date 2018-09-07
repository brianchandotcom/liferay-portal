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

package com.liferay.jenkins.results.parser.vm.amazon;

/**
 * @author Kiyoshi Lee
 */
public class AmazonLinux2AmazonVM extends AmazonVM {

	protected AmazonLinux2AmazonVM(
		String awsAccessKeyId, String awsSecretAccessKey, String instanceId) {

		super(awsAccessKeyId, awsSecretAccessKey, instanceId);
	}

	protected AmazonLinux2AmazonVM(
		String awsAccessKeyId, String awsSecretAccessKey, String instanceType,
		String keyName) {

		super(
			awsAccessKeyId, awsSecretAccessKey, "ami-0782017a917e973e7",
			instanceType, keyName);
	}

	protected AmazonLinux2AmazonVM(
		String awsAccessKeyId, String awsSecretAccessKey, String imageId,
		String instanceType, String keyName) {

		super(
			awsAccessKeyId, awsSecretAccessKey, imageId, instanceType, keyName);
	}

}