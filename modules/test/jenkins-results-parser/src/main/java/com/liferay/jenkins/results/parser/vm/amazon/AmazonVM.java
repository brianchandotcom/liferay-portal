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

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.DeleteVolumeRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.EbsInstanceBlockDevice;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.InstanceBlockDeviceMapping;
import com.amazonaws.services.ec2.model.InstanceState;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.TerminateInstancesRequest;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.vm.VM;

import java.util.List;

/**
 * @author Kiyoshi Lee
 */
public abstract class AmazonVM extends VM {

	public void create() {
		RunInstancesRequest runInstancesRequest = new RunInstancesRequest();

		runInstancesRequest.withImageId(_imageId);
		runInstancesRequest.withInstanceType(_instanceType);
		runInstancesRequest.withKeyName(_keyName);
		runInstancesRequest.withMaxCount(1);
		runInstancesRequest.withMinCount(1);
		runInstancesRequest.withSecurityGroupIds("sg-9ce452fb");

		RunInstancesResult runInstancesResult = _amazonEC2.runInstances(
			runInstancesRequest);

		Reservation instanceReservation = runInstancesResult.getReservation();

		String instanceReservationId = instanceReservation.getReservationId();

		DescribeInstancesResult describeInstancesResult =
			_amazonEC2.describeInstances();

		List<Reservation> reservations =
			describeInstancesResult.getReservations();

		for (Reservation reservation : reservations) {
			String reservationId = reservation.getReservationId();

			if (reservationId.equals(instanceReservationId)) {
				List<Instance> instances = reservation.getInstances();

				Instance instance = instances.get(0);

				_instanceId = instance.getInstanceId();

				break;
			}
		}

		_waitForInstanceState("running");
	}

	public void delete() {
		TerminateInstancesRequest terminateInstancesRequest =
			new TerminateInstancesRequest();

		terminateInstancesRequest.withInstanceIds(_instanceId);

		_amazonEC2.terminateInstances(terminateInstancesRequest);

		_waitForInstanceState("terminated");

		DeleteVolumeRequest deleteVolumeRequest = new DeleteVolumeRequest();

		deleteVolumeRequest.withVolumeId(_volumeId);

		_amazonEC2.deleteVolume(deleteVolumeRequest);
	}

	public String getInstanceId() {
		return _instanceId;
	}

	public String getPublicDnsName() {
		Instance instance = _getInstance();

		return instance.getPublicDnsName();
	}

	protected AmazonVM(
		String awsAccessKeyId, String awsSecretAccessKey, String instanceId) {

		_instanceId = instanceId;

		BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(
			awsAccessKeyId, awsSecretAccessKey);

		AmazonEC2ClientBuilder amazonEC2ClientBuilder =
			AmazonEC2ClientBuilder.standard();

		amazonEC2ClientBuilder.withCredentials(
			new AWSStaticCredentialsProvider(basicAWSCredentials));
		amazonEC2ClientBuilder.withRegion(Regions.US_WEST_1);

		_amazonEC2 = amazonEC2ClientBuilder.build();

		_volumeId = _getVolumeId();
	}

	protected AmazonVM(
		String awsAccessKeyId, String awsSecretAccessKey, String imageId,
		String instanceType, String keyName) {

		_imageId = imageId;
		_instanceType = instanceType;
		_keyName = keyName;

		BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(
			awsAccessKeyId, awsSecretAccessKey);

		AmazonEC2ClientBuilder amazonEC2ClientBuilder =
			AmazonEC2ClientBuilder.standard();

		amazonEC2ClientBuilder.withCredentials(
			new AWSStaticCredentialsProvider(basicAWSCredentials));
		amazonEC2ClientBuilder.withRegion(Regions.US_WEST_1);

		_amazonEC2 = amazonEC2ClientBuilder.build();
	}

	private Instance _getInstance() {
		DescribeInstancesRequest describeInstancesRequest =
			new DescribeInstancesRequest();

		describeInstancesRequest.withInstanceIds(_instanceId);

		DescribeInstancesResult describeInstancesResult =
			_amazonEC2.describeInstances(describeInstancesRequest);

		List<Reservation> reservations =
			describeInstancesResult.getReservations();

		Reservation reservation = reservations.get(0);

		List<Instance> instances = reservation.getInstances();

		return instances.get(0);
	}

	private String _getInstanceState() {
		Instance instance = _getInstance();

		InstanceState instanceState = instance.getState();

		return instanceState.getName();
	}

	private String _getVolumeId() {
		Instance instance = _getInstance();

		List<InstanceBlockDeviceMapping> instanceBlockDeviceMappings =
			instance.getBlockDeviceMappings();

		InstanceBlockDeviceMapping instanceBlockDeviceMapping =
			instanceBlockDeviceMappings.get(0);

		EbsInstanceBlockDevice ebsInstanceBlockDevice =
			instanceBlockDeviceMapping.getEbs();

		return ebsInstanceBlockDevice.getVolumeId();
	}

	private void _waitForInstanceState(String targetState) {
		String instanceState = _getInstanceState();

		System.out.println(
			JenkinsResultsParserUtil.combine(
				"Waiting for the EC2 instance state \"", targetState, "\""));

		long timeout = System.currentTimeMillis() + TIMEOUT_DURATION;

		while (!instanceState.equals(targetState)) {
			if (System.currentTimeMillis() >= timeout) {
				throw new RuntimeException(
					"Timeout occurred while waiting for EC2 instance state \"" +
						targetState + "\"");
			}

			JenkinsResultsParserUtil.sleep(1000 * 30);

			instanceState = _getInstanceState();
		}
	}

	private final AmazonEC2 _amazonEC2;
	private String _imageId;
	private String _instanceId;
	private String _instanceType;
	private String _keyName;
	private String _volumeId;

}