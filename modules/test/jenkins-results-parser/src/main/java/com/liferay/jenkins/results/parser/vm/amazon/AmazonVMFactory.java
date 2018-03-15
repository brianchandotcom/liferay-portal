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
public class AmazonVMFactory {

	public static AmazonVM getExistingAmazonVM(
		String awsAccessKeyId, String awsSecretAccessKey, String instanceId) {

		return new CentOS7AmazonVM(
			awsAccessKeyId, awsSecretAccessKey, instanceId);
	}

	public static AmazonVM newAmazonVM(
		String awsAccessKeyId, String awsSecretAccessKey, String instanceSize,
		InstanceType instanceType, String keyName) {

		if (instanceType == InstanceType.CENTOS_7) {
			return new CentOS7AmazonVM(
				awsAccessKeyId, awsSecretAccessKey, instanceSize, keyName);
		}

		throw new IllegalArgumentException("Invalid instance type");
	}

	public static enum InstanceType {

		CENTOS_7

	}

}