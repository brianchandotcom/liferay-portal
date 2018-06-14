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
public class AuroraAmazonVMFactory {

	public static AuroraAmazonVM getExistingAuroraAmazonVM(
		String awsAccessKeyId, String awsSecretAccessKey, String dbInstanceId) {

		return new MySQLAuroraAmazonVM(
			awsAccessKeyId, awsSecretAccessKey, dbInstanceId);
	}

	public static AuroraAmazonVM newAuroraAmazonVM(
		String awsAccessKeyId, String awsSecretAccessKey, String dbClusterId,
		String dbEngineVersion, String dbInstanceClass, String dbInstanceId,
		InstanceType instanceType) {

		if (instanceType == InstanceType.MYSQL) {
			String dbEngine = "aurora-mysql";

			if (dbEngineVersion.equals("5.6.10a")) {
				dbEngine = "aurora";
			}

			return new MySQLAuroraAmazonVM(
				awsAccessKeyId, awsSecretAccessKey, dbClusterId, dbEngine,
				dbEngineVersion, dbInstanceClass, dbInstanceId);
		}

		if (instanceType == InstanceType.POSTGRESQL) {
			return new PostgreSQLAuroraAmazonVM(
				awsAccessKeyId, awsSecretAccessKey, dbClusterId,
				dbEngineVersion, dbInstanceClass, dbInstanceId);
		}

		throw new IllegalArgumentException("Invalid instance type");
	}

	public static enum InstanceType {

		MYSQL, POSTGRESQL

	}

}