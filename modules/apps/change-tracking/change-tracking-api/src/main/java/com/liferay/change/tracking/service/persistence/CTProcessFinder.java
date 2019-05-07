/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.change.tracking.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public interface CTProcessFinder {

	public java.util.List<com.liferay.change.tracking.model.CTProcess>
		findByCompanyId(
			long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator<?>
				orderByComparator);

	public java.util.List<com.liferay.change.tracking.model.CTProcess>
		findByC_S(
			long companyId, int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator<?>
				orderByComparator);

	public java.util.List<com.liferay.change.tracking.model.CTProcess>
		findByC_N_D(
			long companyId, String keywords, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator<?>
				orderByComparator);

	public java.util.List<com.liferay.change.tracking.model.CTProcess>
		findByC_N_D_S(
			long companyId, String keywords, int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator<?>
				orderByComparator);

}