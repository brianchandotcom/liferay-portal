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

import com.liferay.petra.string.StringPool;
import com.liferay.sharepoint.soap.connector.SharepointConnection;
import com.liferay.sharepoint.soap.connector.SharepointException;
import com.liferay.sharepoint.soap.connector.SharepointObject;
import com.liferay.sharepoint.soap.connector.schema.query.Query;
import com.liferay.sharepoint.soap.connector.schema.query.QueryField;
import com.liferay.sharepoint.soap.connector.schema.query.QueryOptionsList;
import com.liferay.sharepoint.soap.connector.schema.query.QueryValue;
import com.liferay.sharepoint.soap.connector.schema.query.operator.ContainsOperator;
import com.liferay.sharepoint.soap.connector.schema.query.option.BaseQueryOption;
import com.liferay.sharepoint.soap.connector.schema.query.option.FolderQueryOption;
import com.liferay.sharepoint.soap.connector.schema.query.option.ViewAttributesQueryOption;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Iván Zaera
 */
public final class GetSharepointObjectsByNameOperation extends BaseOperation {

	@Override
	public void afterPropertiesSet() {
		_getSharepointObjectsByQueryOperation = getOperation(
			GetSharepointObjectsByQueryOperation.class);
	}

	public List<SharepointObject> execute(String name)
		throws SharepointException {

		Query query = new Query(
			new ContainsOperator(
				new QueryField("FileRef"), new QueryValue(name)));

		List<BaseQueryOption> baseQueryOptions = new ArrayList<>();

		baseQueryOptions.add(new FolderQueryOption(StringPool.BLANK));

		if (sharepointConnectionInfo.getServerVersion() ==
				SharepointConnection.ServerVersion.SHAREPOINT_2013) {

			baseQueryOptions.add(new ViewAttributesQueryOption(true));
		}

		return _getSharepointObjectsByQueryOperation.execute(
			query,
			new QueryOptionsList(
				baseQueryOptions.toArray(new BaseQueryOption[0])));
	}

	private GetSharepointObjectsByQueryOperation
		_getSharepointObjectsByQueryOperation;

}