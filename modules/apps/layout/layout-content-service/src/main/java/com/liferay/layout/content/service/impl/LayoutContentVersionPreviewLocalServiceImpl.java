/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.service.impl;

import com.liferay.layout.content.model.LayoutContentVersion;
import com.liferay.layout.content.model.LayoutContentVersionPreview;
import com.liferay.layout.content.service.base.LayoutContentVersionPreviewLocalServiceBaseImpl;
import com.liferay.layout.content.service.persistence.LayoutContentVersionPersistence;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	property = "model.class.name=com.liferay.layout.content.model.LayoutContentVersionPreview",
	service = AopService.class
)
public class LayoutContentVersionPreviewLocalServiceImpl
	extends LayoutContentVersionPreviewLocalServiceBaseImpl {

	@Override
	public LayoutContentVersionPreview addLayoutContentVersionPreview(
			long userId, long layoutContentVersionId,
			String segmentsExperienceERC, String html, String languageId)
		throws PortalException {

		LayoutContentVersion layoutContentVersion = _checkFeatureFlagEnabled(
			layoutContentVersionId);

		LayoutContentVersionPreview layoutContentVersionPreview =
			layoutContentVersionPreviewPersistence.create(
				counterLocalService.increment(
					LayoutContentVersionPreview.class.getName()));

		layoutContentVersionPreview.setGroupId(
			layoutContentVersion.getGroupId());
		layoutContentVersionPreview.setCompanyId(
			layoutContentVersion.getCompanyId());
		layoutContentVersionPreview.setUserId(userId);

		User user = _userLocalService.getUser(userId);

		layoutContentVersionPreview.setUserName(user.getFullName());

		layoutContentVersionPreview.setLayoutContentVersionId(
			layoutContentVersionId);
		layoutContentVersionPreview.setSegmentsExperienceERC(
			segmentsExperienceERC);
		layoutContentVersionPreview.setHtml(html);
		layoutContentVersionPreview.setLanguageId(languageId);

		return layoutContentVersionPreviewPersistence.update(
			layoutContentVersionPreview);
	}

	@Override
	public void deleteLayoutContentVersionPreviews(
		long layoutContentVersionId) {

		layoutContentVersionPreviewPersistence.removeByLayoutContentVersionId(
			layoutContentVersionId);
	}

	@Override
	public LayoutContentVersionPreview fetchLayoutContentVersionPreview(
		long layoutContentVersionId, String segmentsExperienceERC,
		String languageId) {

		return layoutContentVersionPreviewPersistence.fetchByLCVI_SEERC_L(
			layoutContentVersionId, segmentsExperienceERC, languageId);
	}

	@Override
	public List<LayoutContentVersionPreview> getLayoutContentVersionPreviews(
		long layoutContentVersionId) {

		return layoutContentVersionPreviewPersistence.
			findByLayoutContentVersionId(layoutContentVersionId);
	}

	@Reference
	private LayoutContentVersionPersistence _layoutContentVersionPersistence;

	@Reference
	private UserLocalService _userLocalService;

}