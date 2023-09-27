/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.sharepoint.methods;

import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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
public class GetDocsMetaInfoMethodImpl extends BaseMethodImpl {

	@Override
	public String getMethodName() {
		return _METHOD_NAME;
	}

	@Override
	public String getRootPath(SharepointRequest sharepointRequest) {
		String urlList = sharepointRequest.getParameterValue("url_list");

		return urlList.substring(1, urlList.length() - 1);
	}

	@Override
	protected List<ResponseElement> getElements(
			SharepointRequest sharepointRequest)
		throws Exception {

		List<ResponseElement> elements = new ArrayList<>();

		SharepointStorage storage = sharepointRequest.getSharepointStorage();

		Tree documentListTree = new Tree();

		try {
			documentListTree.addChild(
				storage.getDocumentTree(sharepointRequest));
		}
		catch (Exception exception1) {
			if (exception1 instanceof NoSuchFileEntryException) {
				try {
					documentListTree.addChild(
						storage.getFolderTree(sharepointRequest));
				}
				catch (Exception exception2) {
					if (_log.isDebugEnabled()) {
						_log.debug(exception2);
					}
				}
			}
		}

		Property documentProperty = new Property(
			"document_list", documentListTree);

		elements.add(documentProperty);

		elements.add(new Property("urldirs", new Tree()));

		return elements;
	}

	private static final String _METHOD_NAME = "getDocsMetaInfo";

	private static final Log _log = LogFactoryUtil.getLog(
		GetDocsMetaInfoMethodImpl.class);

}