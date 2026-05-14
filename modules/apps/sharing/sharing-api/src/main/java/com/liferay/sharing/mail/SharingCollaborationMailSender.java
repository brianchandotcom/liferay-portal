/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.mail;

import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.sharing.model.SharingEntry;

/**
 * @author Alicia García
 */
public interface SharingCollaborationMailSender {

	public void sendInvitation(
			ServiceContext serviceContext, SharingEntry sharingEntry)
		throws Exception;

}