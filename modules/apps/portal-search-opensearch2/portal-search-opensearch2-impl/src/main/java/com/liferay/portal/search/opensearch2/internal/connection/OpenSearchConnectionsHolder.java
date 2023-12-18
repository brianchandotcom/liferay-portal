/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.connection;

import java.util.Collection;

/**
 * @author Petteri Karttunen
 */
public interface OpenSearchConnectionsHolder {

	public void addOpenSearchConnection(
		OpenSearchConnection openSearchConnection);

	public OpenSearchConnection getOpenSearchConnection(String connectionId);

	public Collection<OpenSearchConnection> getOpenSearchConnections();

	public void removeOpenSearchConnection(String connectionId);

}