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

package com.liferay.fragment.model.impl;

import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.GroupConstants;

import java.util.Date;

/**
 * @author Eudaldo Alonso
 */
public class FragmentEntryLinkImpl extends FragmentEntryLinkBaseImpl {

	@Override
	public boolean isCacheable() {
		if (getFragmentEntryId() == 0) {
			return false;
		}

		FragmentEntry fragmentEntry =
			FragmentEntryLocalServiceUtil.fetchFragmentEntry(
				getFragmentEntryId());

		if (fragmentEntry != null) {
			return fragmentEntry.isCacheable();
		}

		return false;
	}

	@Override
	public boolean isLatestVersion() throws PortalException {
		FragmentEntry fragmentEntry =
			FragmentEntryLocalServiceUtil.getFragmentEntry(
				getFragmentEntryId());

		Date fragmentEntryModifiedDate = fragmentEntry.getModifiedDate();

		return fragmentEntryModifiedDate.before(getLastPropagationDate());
	}

	@Override
	public boolean isSystem() throws PortalException {
		if (getFragmentEntryId() == 0) {
			return false;
		}

		FragmentEntry fragmentEntry =
			FragmentEntryLocalServiceUtil.fetchFragmentEntry(
				getFragmentEntryId());

		if (fragmentEntry == null) {
			return false;
		}

		if (fragmentEntry.getGroupId() ==
				GroupConstants.DEFAULT_PARENT_GROUP_ID) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isTypeComponent() {
		if (getType() == FragmentConstants.TYPE_COMPONENT) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isTypeInput() {
		if (getType() == FragmentConstants.TYPE_INPUT) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isTypePortlet() {
		if (getType() == FragmentConstants.TYPE_PORTLET) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isTypeReact() {
		if (getType() == FragmentConstants.TYPE_REACT) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isTypeSection() {
		if (getType() == FragmentConstants.TYPE_SECTION) {
			return true;
		}

		return false;
	}

}