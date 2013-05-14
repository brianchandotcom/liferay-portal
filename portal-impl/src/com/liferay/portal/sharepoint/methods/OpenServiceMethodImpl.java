/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
public class OpenServiceMethodImpl extends BaseMethodImpl {

	public OpenServiceMethodImpl() {
		Tree metaInfoTree = new Tree();

		metaInfoTree.addChild(new Leaf("vti_casesensitiveurls", "IX|0", false));
		metaInfoTree.addChild(new Leaf("vti_longfilenames", "IX|1", false));
		metaInfoTree.addChild(
			new Leaf("vti_welcomenames", "VX|index.html", false));
		metaInfoTree.addChild(new Leaf("vti_username", "SX|joebloggs", false));
		metaInfoTree.addChild(new Leaf("vti_servertz", "SX|-0700", false));
		metaInfoTree.addChild(
			new Leaf("vti_sourcecontrolsystem", "SR|lw", false));
		metaInfoTree.addChild(
			new Leaf("vti_sourcecontrolversion", "SR|V1", false));
		metaInfoTree.addChild(
			new Leaf("vti_doclibwebviewenabled", "IX|0", false));
		metaInfoTree.addChild(
			new Leaf("vti_sourcecontrolcookie", "SX|fp_internal", false));
		metaInfoTree.addChild(
			new Leaf(
				"vti_sourcecontrolproject", "SX|&#60;STS-based Locking&#62;",
				false));

		Tree serviceTree = new Tree();

		serviceTree.addChild(new Leaf("service_name", "/sharepoint", true));
		serviceTree.addChild(new Leaf("meta_info", metaInfoTree));

		Property serviceProperty = new Property("service", serviceTree);

		_elements.add(serviceProperty);
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

	private static final String _METHOD_NAME = "open service";

	private List<ResponseElement> _elements = new ArrayList<ResponseElement>();

}