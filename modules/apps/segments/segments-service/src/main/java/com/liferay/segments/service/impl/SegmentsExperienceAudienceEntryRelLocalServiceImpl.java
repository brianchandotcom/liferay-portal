/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.segments.service.base.SegmentsExperienceAudienceEntryRelLocalServiceBaseImpl;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eduardo García
 */
@Component(
	property = "model.class.name=com.liferay.segments.model.SegmentsExperienceAudienceEntryRel",
	service = AopService.class
)
public class SegmentsExperienceAudienceEntryRelLocalServiceImpl
	extends SegmentsExperienceAudienceEntryRelLocalServiceBaseImpl {
}