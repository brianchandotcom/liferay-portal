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

package com.liferay.sharepoint.connector.schema.batch;

import com.liferay.portal.kernel.xml.simple.Element;
import com.liferay.sharepoint.connector.schema.BaseNode;

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