/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.sharepoint.methods;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.sharepoint.Property;
import com.liferay.portal.sharepoint.ResponseElement;
import com.liferay.portal.sharepoint.SharepointRequest;
import com.liferay.portal.sharepoint.SharepointStorage;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bruno Farache
 */
public class GetDocumentMethodImpl extends BaseMethodImpl {

	@Override
	public String getMethodName() {
		return _METHOD_NAME;
	}

	@Override
	public String getRootPath(SharepointRequest sharepointRequest) {
		return sharepointRequest.getParameterValue("document_name");
	}

	@Override
	protected void doProcess(SharepointRequest sharepointRequest)
		throws Exception {

		SharepointStorage storage = sharepointRequest.getSharepointStorage();

		String html = getResponse(sharepointRequest, true);

		InputStream inputStream = storage.getDocumentInputStream(
			sharepointRequest);

		byte[] bytes = ArrayUtil.append(
			html.getBytes(), FileUtil.getBytes(inputStream));

		ServletResponseUtil.write(
			sharepointRequest.getHttpServletResponse(), bytes);
	}

	@Override
	protected List<ResponseElement> getElements(
			SharepointRequest sharepointRequest)
		throws Exception {

		List<ResponseElement> elements = new ArrayList<>();

		SharepointStorage storage = sharepointRequest.getSharepointStorage();

		elements.add(new Property("message", StringPool.BLANK));

		Property documentProperty = new Property(
			"document", storage.getDocumentTree(sharepointRequest));

		elements.add(documentProperty);

		return elements;
	}

	private static final String _METHOD_NAME = "get document";

}