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

package com.liferay.jenkins.results.parser;

/**
 * @author Kiyoshi Lee
 */
public class PostgresqlAwsAurora extends BaseAwsAurora {

	public PostgresqlAwsAurora(
		String awsAccessKeyId, String awsSecretAccessKey, String dbInstanceId) {

		super(awsAccessKeyId, awsSecretAccessKey, dbInstanceId);
	}

	public PostgresqlAwsAurora(
		String awsAccessKeyId, String awsSecretAccessKey, String dbClusterId,
		String dbInstanceClass, String dbInstanceId) {

		super(
			awsAccessKeyId, awsSecretAccessKey, dbClusterId,
			"aurora-postgresql", "9.6.1", dbInstanceClass, dbInstanceId,
			"password", "root");
	}

	public PostgresqlAwsAurora(
		String awsAccessKeyId, String awsSecretAccessKey, String dbClusterId,
		String dbEngineVersion, String dbInstanceClass, String dbInstanceId,
		String dbPassword, String dbUsername) {

		super(
			awsAccessKeyId, awsSecretAccessKey, dbClusterId,
			"aurora-postgresql", dbEngineVersion, dbInstanceClass, dbInstanceId,
			dbPassword, dbUsername);
	}

}