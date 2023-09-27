/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.messaging;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
@ProviderType
public interface Destination {

	public void close();

	public void close(boolean force);

	public void destroy();

	public DestinationStatistics getDestinationStatistics();

	public String getDestinationType();

	public String getName();

	public void open();

	public void send(Message message);

}