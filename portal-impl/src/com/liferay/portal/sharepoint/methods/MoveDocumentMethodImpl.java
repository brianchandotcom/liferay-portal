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
import com.liferay.portal.sharepoint.SharepointUtil;
import com.liferay.portal.sharepoint.Tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bruno Farache
 */
public class MoveDocumentMethodImpl extends BaseMethodImpl {

	@Override
	public String getMethodName() {
		return _METHOD_NAME;
	}

	@Override
	public String getRootPath(SharepointRequest sharepointRequest) {
		return sharepointRequest.getParameterValue("oldUrl");
	}

	@Override
	protected List<ResponseElement> getElements(
			SharepointRequest sharepointRequest)
		throws Exception {

		List<ResponseElement> elements = new ArrayList<ResponseElement>();

		String oldUrl = sharepointRequest.getParameterValue("oldUrl");

		oldUrl = SharepointUtil.replaceBackSlashes(oldUrl);

		String newUrl = sharepointRequest.getParameterValue("newUrl");

		newUrl = SharepointUtil.replaceBackSlashes(oldUrl);

		SharepointStorage storage = sharepointRequest.getSharepointStorage();

		Tree[] results = storage.moveDocument(sharepointRequest);

		elements.add(new Property("message", StringPool.BLANK));
		elements.add(new Property("oldUrl", oldUrl));
		elements.add(new Property("newUrl", newUrl));

		Property documentListProperty = new Property(
			"document_list", new Tree());

		elements.add(documentListProperty);

		elements.add(new Property("moved_docs", results[0]));
		elements.add(new Property("moved_dirs", results[1]));

		return elements;
	}

	private static final String _METHOD_NAME = "move document";

}