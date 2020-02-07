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

package com.liferay.sharepoint.soap.connector.schema.batch;

import com.liferay.portal.kernel.xml.simple.Element;
import com.liferay.sharepoint.soap.connector.schema.BaseNode;

/**
 * @author Iván Zaera
 */
public class Batch extends BaseNode {

	public Batch(
		OnError onError, String folderPath, BatchMethod... batchMethods) {

		_onError = onError;
		_folderPath = folderPath;
		_batchMethods = batchMethods;
	}

	public static enum OnError {

		CONTINUE, RETURN

	}

	@Override
	protected String getNodeName() {
		return "Batch";
	}

	@Override
	protected void populate(Element element) {
		element.addAttribute("OnError", _onError.name());

		if (_folderPath != null) {
			element.addAttribute("RootFolder", _folderPath);
		}

		for (BatchMethod batchMethod : _batchMethods) {
			batchMethod.attach(element);
		}
	}

	private final BatchMethod[] _batchMethods;
	private final String _folderPath;
	private final OnError _onError;

}