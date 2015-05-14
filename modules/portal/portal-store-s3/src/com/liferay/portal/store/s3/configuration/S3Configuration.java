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
	id = "com.liferay.portal.store.s3.configuration.S3Configuration"
)
public interface S3Configuration {

	@Meta.AD(required = true)
	public String accessKey();

	@Meta.AD(required = true)
	public String bucketName();

	@Meta.AD(deflt = "50", required = false, type = Meta.Type.Integer)
	public String httpClientMaxConnections();

	@Meta.AD(deflt = "US", required = false)
	public String s3serviceDefaultBucketLocation();

	@Meta.AD(deflt = "STANDARD", required = false)
	public String s3serviceDefaultStorageClass();

	@Meta.AD(deflt = "s3.amazonws.com", required = false)
	public String s3serviceS3Endpoint();

	@Meta.AD(required = true)
	public String secretKey();

	@Meta.AD(deflt = "7", required = false, type = Meta.Type.Integer)
	public String tempDirCleanUpExpunge();

	@Meta.AD(deflt = "100", required = false, type = Meta.Type.Integer)
	public String tempDirCleanUpFrequency();

}