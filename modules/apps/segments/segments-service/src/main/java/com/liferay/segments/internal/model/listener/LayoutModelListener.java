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

package com.liferay.segments.internal.model.listener;

import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.segments.exception.DefaultSegmentsExperienceException;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(service = ModelListener.class)
public class LayoutModelListener extends BaseModelListener<Layout> {

	@Override
	public void onAfterCreate(Layout layout) throws ModelListenerException {
		try {
			if (!_isPublishedContentLayout(layout)) {
				return;
			}

			_addDefaultSegmentsExperience(layout);
		}
		catch (PortalException pe) {
			throw new ModelListenerException(pe);
		}
	}

	@Override
	public void onAfterRemove(Layout layout) throws ModelListenerException {
		try {
			if (!_isPublishedContentLayout(layout)) {
				return;
			}

			_segmentsExperienceLocalService.deleteSegmentsExperiences(
				layout.getGroupId(),
				_classNameLocalService.getClassNameId(Layout.class),
				layout.getPlid());
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	@Override
	public void onAfterUpdate(Layout layout) throws ModelListenerException {
		try {
			if (!_isPublishedContentLayout(layout)) {
				return;
			}

			_segmentsExperienceLocalService.getDefaultSegmentsExperience(
				layout.getGroupId(),
				_classNameLocalService.getClassNameId(Layout.class),
				layout.getPlid());
		}
		catch (DefaultSegmentsExperienceException dsee) {
			_addDefaultSegmentsExperience(layout);
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	private void _addDefaultSegmentsExperience(Layout layout) {
		try {
			_segmentsExperienceLocalService.addDefaultSegmentsExperience(
				layout.getGroupId(),
				_classNameLocalService.getClassNameId(Layout.class),
				layout.getPlid());
		}
		catch (Exception e) {
			throw new ModelListenerException(e);
		}
	}

	private boolean _isPublishedContentLayout(Layout layout)
		throws PortalException {

		if (!Objects.equals(layout.getType(), LayoutConstants.TYPE_CONTENT)) {
			return false;
		}

		if (layout.getClassNameId() != _classNameLocalService.getClassNameId(
				Layout.class)) {

			return true;
		}

		Layout draftLayout = _layoutLocalService.getLayout(layout.getClassPK());

		if (draftLayout != null) {
			return false;
		}

		return true;
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}