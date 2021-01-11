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

package com.liferay.headless.delivery.internal.resource.v1_0;

import com.liferay.headless.delivery.dto.v1_0.Experience;
import com.liferay.headless.delivery.internal.dto.v1_0.converter.ExperienceDTOConverter;
import com.liferay.headless.delivery.resource.v1_0.ExperienceResource;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutFriendlyURL;
import com.liferay.portal.kernel.service.LayoutFriendlyURLLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.util.TransformUtil;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceService;

import java.util.HashMap;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/experience.properties",
	scope = ServiceScope.PROTOTYPE, service = ExperienceResource.class
)
public class ExperienceResourceImpl extends BaseExperienceResourceImpl {

	@Override
	public Page<Experience> getSiteContentPageFriendlyUrlPathExperiencesPage(
			Long siteId, String friendlyUrlPath)
		throws Exception {

		List<SegmentsExperience> segmentsExperiences = _getSegmentsExperiences(
			siteId, friendlyUrlPath);

		return Page.of(
			TransformUtil.transform(
				segmentsExperiences,
				segmentsExperience -> _toExperience(segmentsExperience)));
	}

	private List<SegmentsExperience> _getSegmentsExperiences(
			Long groupId, String friendlyUrlPath)
		throws Exception {

		String languageId = LocaleUtil.toLanguageId(
			contextAcceptLanguage.getPreferredLocale());

		LayoutFriendlyURL layoutFriendlyURL =
			_layoutFriendlyURLLocalService.getLayoutFriendlyURL(
				groupId, false, StringPool.FORWARD_SLASH + friendlyUrlPath,
				languageId);

		Layout layout = _layoutLocalService.getLayout(
			layoutFriendlyURL.getPlid());

		return _segmentsExperienceService.getSegmentsExperiences(
			groupId, _portal.getClassNameId(Layout.class.getName()),
			layout.getPlid(), true);
	}

	private Experience _toExperience(SegmentsExperience segmentsExperience) {
		return _experienceDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(), new HashMap<>(),
				_dtoConverterRegistry, contextHttpServletRequest,
				segmentsExperience.getSegmentsExperienceId(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser),
			segmentsExperience);
	}

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private ExperienceDTOConverter _experienceDTOConverter;

	@Reference
	private LayoutFriendlyURLLocalService _layoutFriendlyURLLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsExperienceService _segmentsExperienceService;

}