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

		List<ResponseElement> elements = new ArrayList<>();

		String oldUrl = sharepointRequest.getParameterValue("oldUrl");

		oldUrl = SharepointUtil.replaceBackSlashes(oldUrl);

		String newUrl = sharepointRequest.getParameterValue("newUrl");

		newUrl = SharepointUtil.replaceBackSlashes(newUrl);

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