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

package com.liferay.segments.criteria.contributor;

import aQute.bnd.annotation.ConsumerType;

import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.Field;

import java.util.List;
import java.util.Locale;

/**
 * @author Eduardo Garcia
 */
@ConsumerType
public interface SegmentsCriteriaContributor {

	public void contribute(
		Criteria criteria, String filterString,
		Criteria.Conjunction conjunction);

	public default Criteria.Criterion getCriterion(Criteria criteria) {
		return criteria.getCriterion(getKey());
	}

	public List<Field> getFields(Locale locale);

	public String getKey();

	public String getLabel(Locale locale);

}