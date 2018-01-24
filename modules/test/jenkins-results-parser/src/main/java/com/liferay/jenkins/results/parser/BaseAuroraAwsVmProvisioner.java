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

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rds.AmazonRDS;
import com.amazonaws.services.rds.AmazonRDSClientBuilder;
import com.amazonaws.services.rds.model.CreateDBClusterRequest;
import com.amazonaws.services.rds.model.CreateDBInstanceRequest;
import com.amazonaws.services.rds.model.DBCluster;
import com.amazonaws.services.rds.model.DBInstance;
import com.amazonaws.services.rds.model.DeleteDBClusterRequest;
import com.amazonaws.services.rds.model.DeleteDBInstanceRequest;
import com.amazonaws.services.rds.model.DescribeDBClustersRequest;
import com.amazonaws.services.rds.model.DescribeDBClustersResult;
import com.amazonaws.services.rds.model.DescribeDBInstancesRequest;
import com.amazonaws.services.rds.model.DescribeDBInstancesResult;
import com.amazonaws.services.rds.model.Endpoint;

import java.util.List;

/**
 * @author Kiyoshi Lee
 */
public abstract class BaseAuroraAwsVmProvisioner implements AwsVmProvisioner {

	public void create() {
		CreateDBClusterRequest auroraCreateDBClusterRequest =
			new CreateDBClusterRequest();

		auroraCreateDBClusterRequest.withBackupRetentionPeriod(1);
		auroraCreateDBClusterRequest.withDBClusterIdentifier(_dbClusterId);
		auroraCreateDBClusterRequest.withEngine(_dbEngine);
		auroraCreateDBClusterRequest.withEngineVersion(_dbEngineVersion);
		auroraCreateDBClusterRequest.withMasterUsername(_dbUsername);
		auroraCreateDBClusterRequest.withMasterUserPassword(_dbPassword);
		auroraCreateDBClusterRequest.withVpcSecurityGroupIds(
			_vpcSecurityGroupIds);

		_amazonRDS.createDBCluster(auroraCreateDBClusterRequest);

		String auroraClusterStatus = _getClusterStatus();

		System.out.println("Waiting for the RDS Cluster to start.");

		int i = 0;

		while (!auroraClusterStatus.equals("available")) {
			if (i == 20) {
				throw new RuntimeException(
					JenkinsResultsParserUtil.combine(
						"The Aurora Cluster has not responded after 10 ",
						"minutes."));
			}

			auroraClusterStatus = _getClusterStatus();

			JenkinsResultsParserUtil.sleep(1000 * 30);

			i++;
		}

		CreateDBInstanceRequest auroraCreateDBInstanceRequest =
			new CreateDBInstanceRequest();

		auroraCreateDBInstanceRequest.withDBClusterIdentifier(_dbClusterId);
		auroraCreateDBInstanceRequest.withDBInstanceClass(_dbInstanceClass);
		auroraCreateDBInstanceRequest.withDBInstanceIdentifier(_dbInstanceId);
		auroraCreateDBInstanceRequest.withEngine(_dbEngine);
		auroraCreateDBInstanceRequest.withMultiAZ(false);
		auroraCreateDBInstanceRequest.withPubliclyAccessible(true);

		_amazonRDS.createDBInstance(auroraCreateDBInstanceRequest);

		String auroraStatus = _getStatus();

		System.out.println("Waiting for the RDS Instance to start.");

		i = 0;

		while (!auroraStatus.equals("available")) {
			if (i == 20) {
				throw new RuntimeException(
					JenkinsResultsParserUtil.combine(
						"The Aurora Instance has not responded after 10 ",
						"minutes."));
			}

			auroraStatus = _getStatus();

			JenkinsResultsParserUtil.sleep(1000 * 30);

			i++;
		}
	}

	public void delete() {
		DeleteDBInstanceRequest auroraDeleteDBInstanceRequest =
			new DeleteDBInstanceRequest();

		auroraDeleteDBInstanceRequest.withDBInstanceIdentifier(_dbInstanceId);
		auroraDeleteDBInstanceRequest.withSkipFinalSnapshot(true);

		_amazonRDS.deleteDBInstance(auroraDeleteDBInstanceRequest);

		DeleteDBClusterRequest auroraDeleteDBClusterRequest =
			new DeleteDBClusterRequest();

		auroraDeleteDBClusterRequest.withDBClusterIdentifier(_dbClusterId);

		auroraDeleteDBClusterRequest.withSkipFinalSnapshot(true);

		_amazonRDS.deleteDBCluster(auroraDeleteDBClusterRequest);
	}

	public String getAddress() {
		DBInstance auroraInstance = _getInstance();

		Endpoint auroraEndpoint = auroraInstance.getEndpoint();

		return auroraEndpoint.getAddress();
	}

	public String getDBEngine() {
		return _dbEngine;
	}

	public String getDBEngineVersion() {
		return _dbEngineVersion;
	}

	public String getDBPassword() {
		return _dbPassword;
	}

	public String getDBUsername() {
		return _dbUsername;
	}

	protected BaseAuroraAwsVmProvisioner(
		String awsAccessKeyId, String awsSecretAccessKey, String dbInstanceId) {

		BasicAWSCredentials awsCredentials = new BasicAWSCredentials(
			awsAccessKeyId, awsSecretAccessKey);

		AmazonRDSClientBuilder amazonRDSClientBuilder =
			AmazonRDSClientBuilder.standard();

		amazonRDSClientBuilder.withCredentials(
			new AWSStaticCredentialsProvider(awsCredentials));
		amazonRDSClientBuilder.withRegion(Regions.US_WEST_1);

		_amazonRDS = amazonRDSClientBuilder.build();

		_dbInstanceId = dbInstanceId;

		_dbClusterId = _getDbClusterId();
	}

	protected BaseAuroraAwsVmProvisioner(
		String awsAccessKeyId, String awsSecretAccessKey, String dbClusterId,
		String dbEngine, String dbEngineVersion, String dbInstanceClass,
		String dbInstanceId, String dbPassword, String dbUsername) {

		BasicAWSCredentials awsCredentials = new BasicAWSCredentials(
			awsAccessKeyId, awsSecretAccessKey);

		AmazonRDSClientBuilder amazonRDSClientBuilder =
			AmazonRDSClientBuilder.standard();

		amazonRDSClientBuilder.withCredentials(
			new AWSStaticCredentialsProvider(awsCredentials));
		amazonRDSClientBuilder.withRegion(Regions.US_WEST_1);

		_amazonRDS = amazonRDSClientBuilder.build();

		_dbClusterId = dbClusterId;
		_dbEngine = dbEngine;
		_dbEngineVersion = dbEngineVersion;
		_dbInstanceClass = dbInstanceClass;
		_dbInstanceId = dbInstanceId;
		_dbPassword = dbPassword;
		_dbUsername = dbUsername;
	}

	private String _getClusterStatus() {
		DescribeDBClustersRequest auroraClustersRequest =
			new DescribeDBClustersRequest();

		auroraClustersRequest.withDBClusterIdentifier(_dbClusterId);

		DescribeDBClustersResult auroraClustersResult =
			_amazonRDS.describeDBClusters(auroraClustersRequest);

		List<DBCluster> auroraClusters = auroraClustersResult.getDBClusters();

		DBCluster auroraCluster = auroraClusters.get(0);

		return auroraCluster.getStatus();
	}

	private String _getDbClusterId() {
		DBInstance auroraInstance = _getInstance();

		return auroraInstance.getDBClusterIdentifier();
	}

	private DBInstance _getInstance() {
		DescribeDBInstancesRequest auroraInstanceRequest =
			new DescribeDBInstancesRequest();

		auroraInstanceRequest.withDBInstanceIdentifier(_dbInstanceId);

		DescribeDBInstancesResult auroraInstancesResult =
			_amazonRDS.describeDBInstances(auroraInstanceRequest);

		List<DBInstance> auroraInstances =
			auroraInstancesResult.getDBInstances();

		return auroraInstances.get(0);
	}

	private String _getStatus() {
		DBInstance auroraInstance = _getInstance();

		return auroraInstance.getDBInstanceStatus();
	}

	private final AmazonRDS _amazonRDS;
	private final String _dbClusterId;
	private String _dbEngine;
	private String _dbEngineVersion;
	private String _dbInstanceClass;
	private final String _dbInstanceId;
	private String _dbPassword;
	private String _dbUsername;
	private String _vpcSecurityGroupIds = "sg-9ce452fb";

}