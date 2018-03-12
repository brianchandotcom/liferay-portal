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

package com.liferay.sharepoint.connector;

import com.liferay.sharepoint.connector.schema.query.Query;
import com.liferay.sharepoint.connector.schema.query.QueryOptionsList;

import java.io.InputStream;

import java.util.List;

/**
 * @author Iván Zaera
 */
public interface SharepointConnection {

	public void addFile(
			String folderPath, String fileName, String changeLog,
			InputStream inputStream)
		throws SharepointException;

	public void addFolder(String folderPath, String folderName)
		throws SharepointException;

	public boolean cancelCheckOutFile(String filePath)
		throws SharepointException;

	public boolean checkInFile(
			String filePath, String comment, CheckInType checkInType)
		throws SharepointException;

	public boolean checkOutFile(String filePath) throws SharepointException;

	public void copySharepointObject(String path, String newPath)
		throws SharepointException;

	public void deleteSharepointObject(String path) throws SharepointException;

	public InputStream getInputStream(SharepointObject sharepointObject)
		throws SharepointException;

	public InputStream getInputStream(SharepointVersion sharepointVersion)
		throws SharepointException;

	public SharepointConnectionInfo getSharepointConnectionInfo();

	public SharepointObject getSharepointObject(long sharepointObjectId)
		throws SharepointException;

	public SharepointObject getSharepointObject(String path)
		throws SharepointException;

	public List<SharepointObject> getSharepointObjects(
			Query query, QueryOptionsList queryOptionsList)
		throws SharepointException;

	public List<SharepointObject> getSharepointObjects(String name)
		throws SharepointException;

	public List<SharepointObject> getSharepointObjects(
			String folderPath, ObjectTypeFilter objectTypeFilter)
		throws SharepointException;

	public int getSharepointObjectsCount(
			String folderPath, ObjectTypeFilter objectTypeFilter)
		throws SharepointException;

	public List<SharepointVersion> getSharepointVersions(String filePath)
		throws SharepointException;

	public void moveSharepointObject(String path, String newPath)
		throws SharepointException;

	public void updateFile(String filePath, InputStream inputStream)
		throws SharepointException;

	public enum CheckInType {

		MAJOR(1), MINOR(0), OVERWRITE(2);

		public int getProtocolValue() {
			return _protocolValue;
		}

		private CheckInType(int protocolValue) {
			_protocolValue = protocolValue;
		}

		private final int _protocolValue;

	}

	public enum ObjectTypeFilter {

		ALL, FILES, FOLDERS

	}

	public enum ServerVersion {

		SHAREPOINT_2010("2010"), SHAREPOINT_2013("2013");

		public String getValue() {
			return _value;
		}

		private ServerVersion(String value) {
			_value = value;
		}

		private final String _value;

	}

}