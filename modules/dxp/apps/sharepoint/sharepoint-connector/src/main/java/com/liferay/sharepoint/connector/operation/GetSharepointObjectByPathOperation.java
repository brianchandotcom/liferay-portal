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
import com.liferay.sharepoint.connector.SharepointException;
import com.liferay.sharepoint.connector.SharepointObject;
import com.liferay.sharepoint.connector.schema.query.Query;
import com.liferay.sharepoint.connector.schema.query.QueryField;
import com.liferay.sharepoint.connector.schema.query.QueryOptionsList;
import com.liferay.sharepoint.connector.schema.query.QueryValue;
import com.liferay.sharepoint.connector.schema.query.operator.EqOperator;
import com.liferay.sharepoint.connector.schema.query.option.FolderQueryOption;
import com.liferay.sharepoint.connector.schema.query.option.ViewAttributesQueryOption;

/**
 * @author Iván Zaera
 */
public class GetSharepointObjectByPathOperation extends BaseOperation {

	@Override
	public void afterPropertiesSet() {
		_getSharepointObjectsByQueryOperation = getOperation(
			GetSharepointObjectsByQueryOperation.class);
	}

	public SharepointObject execute(String path) throws SharepointException {
		String fullPath = toFullPath(path);

		Query query = new Query(
			new EqOperator(
				new QueryField("FileRef"),
				new QueryValue(fullPath.substring(1))));

		QueryOptionsList queryOptionsList = new QueryOptionsList(
			new FolderQueryOption(StringPool.BLANK),
			new ViewAttributesQueryOption(true));

		return getSharepointObject(
			_getSharepointObjectsByQueryOperation.execute(
				query, queryOptionsList));
	}

	private GetSharepointObjectsByQueryOperation
		_getSharepointObjectsByQueryOperation;

}