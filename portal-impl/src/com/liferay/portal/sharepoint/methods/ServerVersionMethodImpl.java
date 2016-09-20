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

package com.liferay.portal.sharepoint.methods;

import com.liferay.portal.sharepoint.Leaf;
import com.liferay.portal.sharepoint.Property;
import com.liferay.portal.sharepoint.ResponseElement;
import com.liferay.portal.sharepoint.SharepointRequest;
import com.liferay.portal.sharepoint.Tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bruno Farache
 */
public class ServerVersionMethodImpl extends BaseMethodImpl {

	public ServerVersionMethodImpl() {
		Tree serverVersionTree = new Tree();

		serverVersionTree.addChild(new Leaf("major ver", "6", true));
		serverVersionTree.addChild(new Leaf("minor ver", "0", true));
		serverVersionTree.addChild(new Leaf("phase ver", "2", true));
		serverVersionTree.addChild(new Leaf("ver incr", "8117", true));

		Property serverVersionProperty = new Property(
			getMethodName(), serverVersionTree);

		_elements.add(serverVersionProperty);

		_elements.add(new Property("source control", "1"));
	}

	@Override
	public String getMethodName() {
		return _METHOD_NAME;
	}

	@Override
	protected List<ResponseElement> getElements(
		SharepointRequest sharepointRequest) {

		return _elements;
	}

	private static final String _METHOD_NAME = "server version";

	private final List<ResponseElement> _elements = new ArrayList<>();

}