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

	private final List<ResponseElement> _elements = new ArrayList<>();

}