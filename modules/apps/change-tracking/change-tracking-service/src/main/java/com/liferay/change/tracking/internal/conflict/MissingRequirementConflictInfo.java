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

package com.liferay.change.tracking.internal.conflict;

import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Preston Crary
 */
public class MissingRequirementConflictInfo extends BaseConflictInfo {

	public MissingRequirementConflictInfo(
		String className, long modelClassPK,
		CTDisplayRenderer<?> requirementCTDisplayRenderer) {

		_className = className;
		_modelClassPK = modelClassPK;
		_requirementCTDisplayRenderer = requirementCTDisplayRenderer;
	}

	@Override
	public String getConflictDescription(ResourceBundle resourceBundle) {
		return LanguageUtil.get(resourceBundle, "missing-requirement-conflict");
	}

	@Override
	public String getResolutionDescription(ResourceBundle resourceBundle) {
		return LanguageUtil.format(
			resourceBundle,
			"cannot-be-added-because-a-required-x-has-been-deleted",
			_getRequirementTypeName(resourceBundle.getLocale()), false);
	}

	@Override
	public long getSourcePrimaryKey() {
		return _modelClassPK;
	}

	@Override
	public long getTargetPrimaryKey() {
		return 0;
	}

	private String _getRequirementTypeName(Locale locale) {
		if (_requirementCTDisplayRenderer == null) {
			String name = ResourceActionsUtil.getModelResource(
				locale, _className);

			if (name.startsWith("model.resource.")) {
				return _className;
			}

			return name;
		}

		return _requirementCTDisplayRenderer.getTypeName(locale);
	}

	private final String _className;
	private final long _modelClassPK;
	private final CTDisplayRenderer<?> _requirementCTDisplayRenderer;

}