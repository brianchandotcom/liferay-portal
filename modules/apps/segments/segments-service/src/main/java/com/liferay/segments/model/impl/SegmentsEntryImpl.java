/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.model.impl;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.CriteriaSerializer;
import com.liferay.segments.model.SegmentsEntryRole;
import com.liferay.segments.service.SegmentsEntryRoleLocalServiceUtil;

/**
 * @author Eduardo García
 */
public class SegmentsEntryImpl extends SegmentsEntryBaseImpl {

	@Override
	public Criteria getCriteriaObj() {
		if ((_criteria == null) && Validator.isNotNull(getCriteria())) {
			_criteria = CriteriaSerializer.deserialize(getCriteria());
		}

		return _criteria;
	}

	@Override
	public long[] getRoleIds() {
		return ListUtil.toLongArray(
			SegmentsEntryRoleLocalServiceUtil.getSegmentsEntryRoles(
				getSegmentsEntryId()),
			SegmentsEntryRole::getRoleId);
	}

	private Criteria _criteria;

}