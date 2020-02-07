/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.sharepoint.soap.connector;

import com.liferay.sharepoint.soap.connector.schema.query.Query;
import com.liferay.sharepoint.soap.connector.schema.query.QueryOptionsList;

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