/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.contacts.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public interface EntryFinder {

	public int countByKeywords(long companyId, long userId, String keywords);

	public int countByKeywords(long userId, String keywords);

	public java.util.List<com.liferay.portal.kernel.model.BaseModel<?>>
		findByKeywords(
			long companyId, long userId, String keywords, int start, int end);

	public java.util.List<com.liferay.contacts.model.Entry> findByKeywords(
		long userId, String keywords, int start, int end);

}