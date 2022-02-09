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

package com.liferay.site.initializer.testray.extra.java.function;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import java.nio.file.Paths;

/**
 * @author José Abelenda
 */
public class ImportResults {

	public static void downloadObject(
		String projectId, String bucketName, String objectName,
		String destFilePath) {

		// The ID of your GCP project
		// String projectId = "your-project-id";

		// The ID of your GCS bucket
		// String bucketName = "testray-results";

		// The ID of your GCS object
		// String objectName = "your-object-name";

		// The path to which the file should be downloaded
		// String destFilePath = "/local/path/to/file.txt";

		Storage storage = StorageOptions.newBuilder(
		).setProjectId(
			projectId
		).build(
		).getService();

		Blob blob = storage.get(BlobId.of(bucketName, objectName));

		blob.downloadTo(Paths.get(destFilePath));

		System.out.println("Downloaded object " + objectName);
	}

	public static void listBuckets(String projectId) throws Exception {
		Storage storage = StorageOptions.newBuilder(
		).setProjectId(
			projectId
		).build(
		).getService();

		Page<Bucket> bucketsPage = storage.list();

		for (Bucket bucket : bucketsPage.iterateAll()) {
			System.out.println(bucket.getName());
		}
	}

	public static void main(String[] args) {
		try {
			System.out.println("Hello World!");

			listBuckets("testray-340800");
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
	}

}