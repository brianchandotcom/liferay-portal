/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.sharepoint.methods;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.sharepoint.Property;
import com.liferay.portal.sharepoint.ResponseElement;
import com.liferay.portal.sharepoint.SharepointRequest;
import com.liferay.portal.sharepoint.SharepointStorage;
import com.liferay.portal.sharepoint.Tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bruno Farache
 */
public class PutDocumentMethodImpl extends BaseMethodImpl {

	@Override
	public String getMethodName() {
		return _METHOD_NAME;
	}

	@Override
	public String getRootPath(SharepointRequest sharepointRequest) {
		String rootPath = sharepointRequest.getParameterValue("document");

		rootPath = rootPath.split(StringPool.SEMICOLON)[0];

		return rootPath.substring(15);
	}

	@Override
	protected List<ResponseElement> getElements(
			SharepointRequest sharepointRequest)
		throws Exception {

		List<ResponseElement> elements = new ArrayList<ResponseElement>();

		elements.add(new Property("message", StringPool.BLANK));

		SharepointStorage storage = sharepointRequest.getSharepointStorage();

		storage.putDocument(sharepointRequest);

		Tree documentTree = storage.getDocumentTree(sharepointRequest);

		Property documentProperty = new Property("document", documentTree);

		elements.add(documentProperty);

		return elements;
	}

	private static final String _METHOD_NAME = "put document";

}