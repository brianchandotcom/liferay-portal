/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine;

import java.util.List;
import java.util.Set;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Bryan Engler
 */
@ProviderType
public interface ConnectionInformationBuilder {

	public ConnectionInformation build();

	public void clusterName(String clusterName);

	public void connectionId(String connectionId);

	public void error(String error);

	public void health(String health);

	public void labels(Set<String> labels);

	public void nodeInformationList(List<NodeInformation> nodeInformationList);

}