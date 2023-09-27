/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public interface DDMFormInstanceRecordFinder {

	public int countByF_S(long ddmFormInstanceId, int status);

	public java.util.List
		<com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord>
			findByF_S(
				long ddmFormInstanceId, int status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.dynamic.data.mapping.model.
						DDMFormInstanceRecord> orderByComparator);

}