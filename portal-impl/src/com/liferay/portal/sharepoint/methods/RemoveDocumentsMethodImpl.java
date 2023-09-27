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
import com.liferay.portal.sharepoint.Tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bruno Farache
 */
public class RemoveDocumentsMethodImpl extends BaseMethodImpl {

	@Override
	public String getMethodName() {
		return _METHOD_NAME;
	}

	@Override
	public String getRootPath(SharepointRequest sharepointRequest) {
		String urlList = sharepointRequest.getParameterValue("url_list");

		urlList = urlList.substring(1, urlList.length() - 1);

		return urlList.split(StringPool.SEMICOLON)[0];
	}

	@Override
	protected List<ResponseElement> getElements(
			SharepointRequest sharepointRequest)
		throws Exception {

		List<ResponseElement> elements = new ArrayList<>();

		SharepointStorage storage = sharepointRequest.getSharepointStorage();

		Tree[] results = storage.removeDocument(sharepointRequest);

		elements.add(new Property("removed_docs", results[0]));
		elements.add(new Property("removed_dirs", results[1]));
		elements.add(new Property("failed_docs", results[2]));
		elements.add(new Property("failed_dirs", results[3]));

		return elements;
	}

	private static final String _METHOD_NAME = "remove documents";

}