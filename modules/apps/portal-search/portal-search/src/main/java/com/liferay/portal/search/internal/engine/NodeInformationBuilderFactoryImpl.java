/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.engine;

import com.liferay.portal.search.engine.NodeInformationBuilder;
import com.liferay.portal.search.engine.NodeInformationBuilderFactory;

import org.osgi.service.component.annotations.Component;

/**
 * @author Bryan Engler
 */
@Component(service = NodeInformationBuilderFactory.class)
public class NodeInformationBuilderFactoryImpl
	implements NodeInformationBuilderFactory {

	@Override
	public NodeInformationBuilder getNodeInformationBuilder() {
		return new NodeInformationImpl.Builder();
	}

}