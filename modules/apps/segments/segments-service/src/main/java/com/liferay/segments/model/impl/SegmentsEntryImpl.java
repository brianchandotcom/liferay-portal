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

package com.liferay.segments.model.impl;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.CriteriaSerializer;

/**
 * @author Eduardo García
 */
public class SegmentsEntryImpl extends SegmentsEntryBaseImpl {

	public SegmentsEntryImpl() {
	}

	@Override
	public Criteria getCriteriaObj() {
		if ((_criteria == null) && Validator.isNotNull(getCriteria())) {
			_criteria = CriteriaSerializer.deserialize(getCriteria());
		}

		return _criteria;
	}

	private Criteria _criteria;

}