/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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