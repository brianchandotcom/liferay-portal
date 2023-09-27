/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.soap.repository.connector.operation;

import com.liferay.sharepoint.soap.repository.connector.SharepointConnection;
import com.liferay.sharepoint.soap.repository.connector.SharepointException;
import com.liferay.sharepoint.soap.repository.connector.SharepointObject;
import com.liferay.sharepoint.soap.repository.connector.operation.constants.SharepointConstants;
import com.liferay.sharepoint.soap.repository.connector.schema.query.Query;
import com.liferay.sharepoint.soap.repository.connector.schema.query.QueryField;
import com.liferay.sharepoint.soap.repository.connector.schema.query.QueryOptionsList;
import com.liferay.sharepoint.soap.repository.connector.schema.query.QueryValue;
import com.liferay.sharepoint.soap.repository.connector.schema.query.operator.EqOperator;
import com.liferay.sharepoint.soap.repository.connector.schema.query.option.FolderQueryOption;

import java.util.List;

/**
 * @author Iván Zaera
 */
public final class GetSharepointObjectsByFolderOperation extends BaseOperation {

	@Override
	public void afterPropertiesSet() {
		_getSharepointObjectsByQueryOperation = getOperation(
			GetSharepointObjectsByQueryOperation.class);
	}

	public List<SharepointObject> execute(
			String folderPath,
			SharepointConnection.ObjectTypeFilter objectTypeFilter)
		throws SharepointException {

		return _getSharepointObjectsByQueryOperation.execute(
			_getQuery(objectTypeFilter),
			new QueryOptionsList(
				new FolderQueryOption(toFullPath(folderPath))));
	}

	private Query _getQuery(
		SharepointConnection.ObjectTypeFilter objectTypeFilter) {

		if (objectTypeFilter.equals(
				SharepointConnection.ObjectTypeFilter.ALL)) {

			return new Query(null);
		}

		if (objectTypeFilter.equals(
				SharepointConnection.ObjectTypeFilter.FILES)) {

			return new Query(
				new EqOperator(
					new QueryField("FSObjType"),
					new QueryValue(
						QueryValue.Type.LOOKUP,
						SharepointConstants.FS_OBJ_TYPE_FILE)));
		}

		if (objectTypeFilter.equals(
				SharepointConnection.ObjectTypeFilter.FOLDERS)) {

			return new Query(
				new EqOperator(
					new QueryField("FSObjType"),
					new QueryValue(
						QueryValue.Type.LOOKUP,
						SharepointConstants.FS_OBJ_TYPE_FOLDER)));
		}

		throw new UnsupportedOperationException(
			"Unsupported object type filter " + objectTypeFilter);
	}

	private GetSharepointObjectsByQueryOperation
		_getSharepointObjectsByQueryOperation;

}