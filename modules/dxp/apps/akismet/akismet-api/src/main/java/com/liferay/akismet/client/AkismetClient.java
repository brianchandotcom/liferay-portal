/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.akismet.client;

import com.liferay.akismet.model.AkismetEntry;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Map;

/**
 * @author Dante Wang
 */
public interface AkismetClient {

	public boolean hasRequiredInfo(String userIP, Map<String, String> headers);

	public boolean isSpam(
			long userId, String content, AkismetEntry akismetEntry)
		throws PortalException;

	public void submitHam(
			long companyId, String ipAddress, String userAgent, String referrer,
			String permalink, String commentType, String userName,
			String emailAddress, String content)
		throws PortalException;

	public void submitHam(MBMessage mbMessage) throws PortalException;

	public void submitSpam(
			long companyId, String ipAddress, String userAgent, String referrer,
			String permalink, String commentType, String userName,
			String emailAddress, String content)
		throws PortalException;

	public void submitSpam(MBMessage mbMessage) throws PortalException;

	public boolean verifyApiKey(long companyId, String apiKey)
		throws PortalException;

}