/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public interface DDLRecordFinder {

	public int countByR_S(long recordSetId, int status);

	public int countByC_S_S(long companyId, int status, int scope);

	public java.util.List<com.liferay.dynamic.data.lists.model.DDLRecord>
		findByR_S(
			long recordSetId, int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.lists.model.DDLRecord>
					orderByComparator);

	public java.util.List<com.liferay.dynamic.data.lists.model.DDLRecord>
		findByC_S_S(
			long companyId, int status, int scope, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.dynamic.data.lists.model.DDLRecord>
					orderByComparator);

	public Long[] findByC_S_S_MinAndMax(long companyId, int status, int scope);

	public java.util.List<com.liferay.dynamic.data.lists.model.DDLRecord>
		findByC_S_S_MinAndMax(
			long companyId, int status, int scope, long minRecordId,
			long maxRecordId);

}