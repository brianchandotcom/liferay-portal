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

package com.liferay.translation.web.internal.helper;

import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.GroupKeyInfoItemIdentifier;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.translator.InfoItemIdentifierTranslator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.model.SegmentsExperience;

import javax.portlet.PortletRequest;

/**
 * @author Adolfo Pérez
 */
public class TranslationRequestHelper {

	public TranslationRequestHelper(
		InfoItemServiceTracker infoItemServiceTracker,
		PortletRequest portletRequest) {

		_infoItemServiceTracker = infoItemServiceTracker;
		_portletRequest = portletRequest;
	}

	public String getClassName(long segmentsExperienceId) {
		if (segmentsExperienceId != SegmentsExperienceConstants.ID_DEFAULT) {
			return SegmentsExperience.class.getName();
		}

		return getModelClassName();
	}

	public String getClassName(long[] segmentsExperienceIds) {
		if (ArrayUtil.isEmpty(segmentsExperienceIds) ||
			((segmentsExperienceIds.length == 1) &&
			 (segmentsExperienceIds[0] ==
				 SegmentsExperienceConstants.ID_DEFAULT))) {

			return getModelClassName();
		}

		return SegmentsExperience.class.getName();
	}

	public long getClassPK(long segmentsExperienceId) throws PortalException {
		if (segmentsExperienceId != SegmentsExperienceConstants.ID_DEFAULT) {
			return segmentsExperienceId;
		}

		return getModelClassPK();
	}

	public long[] getClassPKs(long[] segmentsExperienceIds)
		throws PortalException {

		if (ArrayUtil.isEmpty(segmentsExperienceIds) ||
			((segmentsExperienceIds.length == 1) &&
			 (segmentsExperienceIds[0] ==
				 SegmentsExperienceConstants.ID_DEFAULT))) {

			return new long[] {getModelClassPK()};
		}

		return segmentsExperienceIds;
	}

	public String getModelClassName() {
		if (_modelClassName != null) {
			return _modelClassName;
		}

		_modelClassName = PortalUtil.getClassName(
			ParamUtil.getLong(_portletRequest, "classNameId"));

		return _modelClassName;
	}

	public long getModelClassPK() throws PortalException {
		if (_modelClassPK != null) {
			return _modelClassPK;
		}

		_modelClassPK = ParamUtil.getLong(_portletRequest, "classPK");

		if (_modelClassPK != 0) {
			return _modelClassPK;
		}

		InfoItemIdentifierTranslator infoItemIdentifierTranslator =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemIdentifierTranslator.class, getModelClassName());

		long groupId = ParamUtil.getLong(_portletRequest, "groupId");
		String key = ParamUtil.getString(_portletRequest, "key");

		ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
			(ClassPKInfoItemIdentifier)
				infoItemIdentifierTranslator.translateInfoItemIdentifier(
					new GroupKeyInfoItemIdentifier(groupId, key),
					ClassPKInfoItemIdentifier.class);

		_modelClassPK = classPKInfoItemIdentifier.getClassPK();

		return _modelClassPK;
	}

	private final InfoItemServiceTracker _infoItemServiceTracker;
	private String _modelClassName;
	private Long _modelClassPK;
	private final PortletRequest _portletRequest;

}