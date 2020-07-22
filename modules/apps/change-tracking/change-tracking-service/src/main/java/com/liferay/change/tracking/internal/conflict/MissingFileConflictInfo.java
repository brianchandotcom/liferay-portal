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

import com.liferay.change.tracking.conflict.ConflictInfo;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Lianne Louie
 */
public class MissingFileConflictInfo implements ConflictInfo {

	public MissingFileConflictInfo(long modelClassPK) {
		_modelClassPK = modelClassPK;
	}

	@Override
	public String getConflictDescription(ResourceBundle resourceBundle) {
		return LanguageUtil.get(resourceBundle, "missing-file-conflict");
	}

	@Override
	public long getCTAutoResolutionInfoId() {
		return _ctAutoResolutionInfoId;
	}

	@Override
	public String getResolutionDescription(ResourceBundle resourceBundle) {
		return LanguageUtil.get(
			resourceBundle, "the-modification-cannot-be-completed-due-to-a-missing-file");
	}

	@Override
	public ResourceBundle getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			locale, MissingFileConflictInfo.class);
	}

	@Override
	public long getSourcePrimaryKey() {
		return _modelClassPK;
	}

	@Override
	public long getTargetPrimaryKey() {
		return _modelClassPK;
	}

	@Override
	public boolean isResolved() {
		return false;
	}

	public void setCtAutoResolutionInfoId(long ctAutoResolutionInfoId) {
		_ctAutoResolutionInfoId = ctAutoResolutionInfoId;
	}

	private long _ctAutoResolutionInfoId;
	private final long _modelClassPK;
}
