/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.soap.repository.connector.schema.batch;

import com.liferay.portal.kernel.xml.simple.Element;
import com.liferay.sharepoint.soap.repository.connector.schema.BaseNode;

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

	public enum OnError {

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