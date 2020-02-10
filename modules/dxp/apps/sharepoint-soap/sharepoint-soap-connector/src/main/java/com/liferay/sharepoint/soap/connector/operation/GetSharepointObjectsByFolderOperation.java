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

package com.liferay.sharepoint.soap.connector.operation;

import com.liferay.sharepoint.soap.connector.SharepointConnection;
import com.liferay.sharepoint.soap.connector.SharepointException;
import com.liferay.sharepoint.soap.connector.SharepointObject;
import com.liferay.sharepoint.soap.connector.schema.query.Query;
import com.liferay.sharepoint.soap.connector.schema.query.QueryField;
import com.liferay.sharepoint.soap.connector.schema.query.QueryOptionsList;
import com.liferay.sharepoint.soap.connector.schema.query.QueryValue;
import com.liferay.sharepoint.soap.connector.schema.query.operator.EqOperator;
import com.liferay.sharepoint.soap.connector.schema.query.option.FolderQueryOption;

import java.util.List;

/**
 * @author Iván Zaera
 */
public class GetSharepointObjectsByFolderOperation extends BaseOperation {

	@Override
	public void afterPropertiesSet() {
		_getSharepointObjectsByQueryOperation = getOperation(
			GetSharepointObjectsByQueryOperation.class);
	}

	public List<SharepointObject> execute(
			String folderPath,
			SharepointConnection.ObjectTypeFilter objectTypeFilter)
		throws SharepointException {

		Query query = null;

		if (objectTypeFilter.equals(
				SharepointConnection.ObjectTypeFilter.ALL)) {

			query = new Query(null);
		}
		else if (objectTypeFilter.equals(
					SharepointConnection.ObjectTypeFilter.FILES)) {

			query = new Query(
				new EqOperator(
					new QueryField("FSObjType"),
					new QueryValue(
						QueryValue.Type.LOOKUP,
						SharepointConstants.FS_OBJ_TYPE_FILE)));
		}
		else if (objectTypeFilter.equals(
					SharepointConnection.ObjectTypeFilter.FOLDERS)) {

			query = new Query(
				new EqOperator(
					new QueryField("FSObjType"),
					new QueryValue(
						QueryValue.Type.LOOKUP,
						SharepointConstants.FS_OBJ_TYPE_FOLDER)));
		}
		else {
			throw new UnsupportedOperationException(
				"Unsupported object type filter " + objectTypeFilter);
		}

		return _getSharepointObjectsByQueryOperation.execute(
			query,
			new QueryOptionsList(
				new FolderQueryOption(toFullPath(folderPath))));
	}

	private GetSharepointObjectsByQueryOperation
		_getSharepointObjectsByQueryOperation;

}