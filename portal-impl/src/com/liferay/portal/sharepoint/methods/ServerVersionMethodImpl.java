/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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