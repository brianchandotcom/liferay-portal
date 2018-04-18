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

package com.liferay.commerce.user.segment.internal.criterion;

import com.liferay.commerce.user.segment.constants.CommerceUserSegmentsConstants;
import com.liferay.commerce.user.segment.criterion.CommerceUserSegmentCriterionType;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = {
		"commerce.user.segment.type.key=" + CommerceUserSegmentsConstants.CRITERION_TYPE_ORGANIZATION,
		"commerce.user.segment.type.priority:Integer=20"
	},
	service = CommerceUserSegmentCriterionType.class
)
public class OrganizationCommerceUserSegmentCriterionTypeImpl
	extends BaseCommerceUserSegmentCriterionType {

	@Override
	public String getKey() {
		return CommerceUserSegmentsConstants.CRITERION_TYPE_ORGANIZATION;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "organizations");
	}

	@Override
	protected long[] getUserClassPKs(User user) throws PortalException {
		return user.getOrganizationIds();
	}

}