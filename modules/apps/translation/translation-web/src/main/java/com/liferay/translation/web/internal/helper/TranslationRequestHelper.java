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

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class TranslationRequestHelper {

	public TranslationRequestHelper(
		InfoItemServiceTracker infoItemServiceTracker,
		HttpServletRequest httpServletRequest) {

		_infoItemServiceTracker = infoItemServiceTracker;
		_httpServletRequest = httpServletRequest;
	}

	public TranslationRequestHelper(
		InfoItemServiceTracker infoItemServiceTracker,
		PortletRequest portletRequest) {

		this(
			infoItemServiceTracker,
			PortalUtil.getHttpServletRequest(portletRequest));
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

	public long getClassNameId() {
		if (_classNameId != null) {
			return _classNameId;
		}

		_classNameId = ParamUtil.getLong(_httpServletRequest, "classNameId");

		return _classNameId;
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

			return getModelClassPKs();
		}

		return segmentsExperienceIds;
	}

	public long getGroupId() {
		if (_groupId != null) {
			return _groupId;
		}

		_groupId = ParamUtil.getLong(_httpServletRequest, "groupId");

		return _groupId;
	}

	public String getModelClassName() {
		if (_modelClassName != null) {
			return _modelClassName;
		}

		_modelClassName = PortalUtil.getClassName(getClassNameId());

		return _modelClassName;
	}

	public long getModelClassPK() throws PortalException {
		long[] modelClassPKs = getModelClassPKs();

		if (modelClassPKs.length > 0) {
			return modelClassPKs[0];
		}

		return 0;
	}

	public long[] getModelClassPKs() throws PortalException {
		if (_modelClassPKs != null) {
			return _modelClassPKs;
		}

		_modelClassPKs = ParamUtil.getLongValues(
			_httpServletRequest, "classPK");

		if (_modelClassPKs.length != 0) {
			return _modelClassPKs;
		}

		InfoItemIdentifierTranslator infoItemIdentifierTranslator =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemIdentifierTranslator.class, getModelClassName());

		String[] keys = ParamUtil.getStringValues(_httpServletRequest, "key");

		long[] modelClassPKs = new long[keys.length];

		for (int i = 0; i < keys.length; i++) {
			ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
				(ClassPKInfoItemIdentifier)
					infoItemIdentifierTranslator.translateInfoItemIdentifier(
						new GroupKeyInfoItemIdentifier(getGroupId(), keys[i]),
						ClassPKInfoItemIdentifier.class);

			modelClassPKs[i] = classPKInfoItemIdentifier.getClassPK();
		}

		_modelClassPKs = modelClassPKs;

		return _modelClassPKs;
	}

	private Long _classNameId;
	private Long _groupId;
	private final HttpServletRequest _httpServletRequest;
	private final InfoItemServiceTracker _infoItemServiceTracker;
	private String _modelClassName;
	private long[] _modelClassPKs;

}