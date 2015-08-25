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

package com.liferay.portal.store.s3.configuration;

import aQute.bnd.annotation.metatype.Meta;

/**
 * @author Manuel de la Peña
 */
@Meta.OCD(
	id = "com.liferay.portal.store.s3.configuration.S3StoreConfiguration",
	localization = "content/Language", name = "%s3.store.configuration.name"
)
public interface S3StoreConfiguration {

	@Meta.AD(
		description =
			"Optional. Must be provided here," +
				" in the AWS_ACCESS_KEY_ID system environment variable," +
				" the aws.accessKeyId Java system property," +
				" or the EC2 instance must be configured with an IAM Role.",
		required = false
	)
	public String accessKey();

	@Meta.AD(required = true)
	public String bucketName();

	@Meta.AD(deflt = "50", required = false, type = Meta.Type.Integer)
	public String httpClientMaxConnections();

	@Meta.AD(
		deflt = "us-east-1",
		description =
			"AWS region name. See http://docs.aws.amazon.com/" +
				"general/latest/gr/rande.html#s3_region",
		required = false
	)
	public String s3Region();

	@Meta.AD(
		deflt = "STANDARD", description = "AWS S3 storage class",
		required = false
	)
	public String s3StorageClass();

	@Meta.AD(
		description =
			"Optional. Must be provided here," +
				" in the AWS_SECRET_ACCESS_KEY system environment variable," +
				" the aws.secretKey Java system property," +
				" or the EC2 instance must be configured with an IAM Role.",
		required = false
	)
	public String secretKey();

	@Meta.AD(deflt = "7", required = false, type = Meta.Type.Integer)
	public String tempDirCleanUpExpunge();

	@Meta.AD(deflt = "100", required = false, type = Meta.Type.Integer)
	public String tempDirCleanUpFrequency();

}