/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.definition.internal.export;

import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.workflow.kaleo.definition.Node;
import com.liferay.portal.workflow.kaleo.definition.NodeType;
import com.liferay.portal.workflow.kaleo.definition.State;
import com.liferay.portal.workflow.kaleo.definition.export.NodeExporter;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = NodeExporter.class)
public class StateNodeExporter
	extends BaseNodeExporter implements NodeExporter {

	@Override
	public NodeType getNodeType() {
		return NodeType.STATE;
	}

	@Override
	protected Element createNodeElement(Element element, String namespace) {
		return element.addElement("state", namespace);
	}

	@Override
	protected void exportAdditionalNodeElements(
		Node node, Element nodeElement) {

		exportTimersElement(node, nodeElement, "timers", "timer");

		State state = (State)node;

		boolean initial = state.isInitial();

		if (initial) {
			addTextElement(nodeElement, "initial", String.valueOf(initial));
		}
	}

}