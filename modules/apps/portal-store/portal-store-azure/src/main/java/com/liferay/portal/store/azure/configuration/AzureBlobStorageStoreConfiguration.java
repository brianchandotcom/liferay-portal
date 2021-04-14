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

package com.liferay.portal.store.azure.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * The configuration for the DL Store backed by Azure Blob Storage.
 *
 * @author Josef Sustacek
 */
@ExtendedObjectClassDefinition(category = "file-storage")
@Meta.OCD(
	id = AzureBlobStorageStoreConfiguration.ID,
	localization = "content/Language",
	name = "azure-blob-storage-store-configuration-name"
)
public interface AzureBlobStorageStoreConfiguration {

	String ID =
		"com.liferay.portal.store.azure.configuration.AzureBlobStorageStoreConfiguration";

	// Example values:

	//	* via Storage Account -> Access Keys:
	//
	//		DefaultEndpointsProtocol=https;AccountName=liferayportal;AccountKey=iq3R6pupSRXSnNwZkSWEXdghiLiMr7jq3G17RyZrchj9/lOYV1X9JY/1xiu2gh7Y8ElaJfQRA9+52PPqhpckMQ==;EndpointSuffix=core.windows.net
	//
	//	* via Storage Account -> Shared Access Signature:
	//
	//		BlobEndpoint=https://liferayportal.blob.core.windows.net/;QueueEndpoint=https://liferayportal.queue.core.windows.net/;FileEndpoint=https://liferayportal.file.core.windows.net/;TableEndpoint=https://liferayportal.table.core.windows.net/;SharedAccessSignature=sv=2020-02-10&ss=b&srt=sc&sp=rwdlacx&se=2021-03-23T17:44:46Z&st=2021-03-23T09:44:46Z&spr=https&sig=k4fMgC%2Bzkjc7Xu3%2Fv9HBmD6F%2BIjhQHW6qPjR7Xa5AW8%3D
	//
	//	* via Storage Account -> Container -> Shared access signature
	//	(manually build based on "Blob SAS URL" and "Blob SAS token")
	//
	//		BlobEndpoint=https://liferayportal.blob.core.windows.net/;SharedAccessSignature=sp=r&st=2021-03-23T09:53:28Z&se=2021-03-23T17:53:28Z&spr=https&sv=2020-02-10&sr=c&sig=8GdYBOQ0%2BTlEhJlyP1bkr%2BOzdQC6X7Bm0G145MMDMz0%3D

	@Meta.AD(
		description = "connection-string-help", name = "connection-string",
		required = true
	)
	String connectionString();

	/**
	 * Kind of like "S3 bucket" in AWS.
	 * @return
	 */
	@Meta.AD(
		description = "container-name-help", name = "container-name",
		required = true
	)
	String containerName();

	/**
	 * Must be pre-created in Azure in given storage account.
	 * @return
	 */
	@Meta.AD(
		description = "encryption-scope-help", name = "encryption-scope",
		required = false
	)
	String encryptionScope();

	@Meta.AD(
		description = "http-logging-enabled-help",
		name = "http-logging-enabled", required = false
	)
	boolean httpLoggingEnabled();

}