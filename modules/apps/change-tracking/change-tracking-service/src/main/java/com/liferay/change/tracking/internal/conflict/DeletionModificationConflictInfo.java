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
public class DeletionModificationConflictInfo implements ConflictInfo {

	public DeletionModificationConflictInfo(long modelClassPK) {
		_modelClassPK = modelClassPK;
	}

	@Override
	public String getConflictDescription(ResourceBundle resourceBundle) {
		return LanguageUtil.get(
			resourceBundle, "deletion-modification-conflict");
	}

	@Override
	public long getCTAutoResolutionInfoId() {
		return 0;
	}

	@Override
	public String getResolutionDescription(ResourceBundle resourceBundle) {
		return LanguageUtil.get(
			resourceBundle, "modification-conflicts-with-a-deletion");
	}

	@Override
	public ResourceBundle getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			locale, DeletionModificationConflictInfo.class);
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

	private final long _modelClassPK;

}