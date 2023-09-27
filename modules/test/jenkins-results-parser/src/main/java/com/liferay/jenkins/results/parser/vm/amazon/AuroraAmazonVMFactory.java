/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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