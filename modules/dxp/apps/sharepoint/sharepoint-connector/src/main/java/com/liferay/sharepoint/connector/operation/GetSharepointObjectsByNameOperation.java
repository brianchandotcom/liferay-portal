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

package com.liferay.sharepoint.connector.operation;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.sharepoint.connector.SharepointConnection;
import com.liferay.sharepoint.connector.SharepointException;
import com.liferay.sharepoint.connector.SharepointObject;
import com.liferay.sharepoint.connector.schema.query.Query;
import com.liferay.sharepoint.connector.schema.query.QueryField;
import com.liferay.sharepoint.connector.schema.query.QueryOptionsList;
import com.liferay.sharepoint.connector.schema.query.QueryValue;
import com.liferay.sharepoint.connector.schema.query.operator.ContainsOperator;
import com.liferay.sharepoint.connector.schema.query.option.BaseQueryOption;
import com.liferay.sharepoint.connector.schema.query.option.FolderQueryOption;
import com.liferay.sharepoint.connector.schema.query.option.ViewAttributesQueryOption;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Iván Zaera
 */
public class GetSharepointObjectsByNameOperation extends BaseOperation {

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
				baseQueryOptions.toArray(
					new BaseQueryOption[baseQueryOptions.size()])));
	}

	private GetSharepointObjectsByQueryOperation
		_getSharepointObjectsByQueryOperation;

}