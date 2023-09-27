/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.sharepoint.methods;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.sharepoint.Property;
import com.liferay.portal.sharepoint.ResponseElement;
import com.liferay.portal.sharepoint.SharepointRequest;
import com.liferay.portal.sharepoint.SharepointStorage;

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

		List<ResponseElement> elements = new ArrayList<>();

		elements.add(new Property("message", StringPool.BLANK));

		SharepointStorage storage = sharepointRequest.getSharepointStorage();

		storage.putDocument(sharepointRequest);

		Property documentProperty = new Property(
			"document", storage.getDocumentTree(sharepointRequest));

		elements.add(documentProperty);

		return elements;
	}

	private static final String _METHOD_NAME = "put document";

}