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

package com.liferay.segments.internal.criteria.contributor;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributor;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eduardo Garcia
 */
@Component(
	immediate = true,
	property = {
		"segments.criteria.contributor.key=" + EntityModelSegmentsCriteriaContributor.KEY,
		"segments.criteria.contributor.model.class.name=*",
		"segments.criteria.contributor.priority:Integer=50"
	},
	service = SegmentsCriteriaContributor.class
)
public class EntityModelSegmentsCriteriaContributor
	implements SegmentsCriteriaContributor {

	public static final String KEY = "entity-model";

	@Override
	public void contribute(
		Criteria criteria, String filter, Criteria.Conjunction conjunction) {

		criteria.addCriterion(getKey(), filter, conjunction);
		criteria.addFilter(filter, conjunction);
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, getClass());

		return LanguageUtil.get(resourceBundle, getKey());
	}

}