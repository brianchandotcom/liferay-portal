/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.model.impl;

import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.contributor.FragmentCollectionContributorRegistry;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Date;
import java.util.Map;

/**
 * @author Eudaldo Alonso
 */
public class FragmentEntryLinkImpl extends FragmentEntryLinkBaseImpl {

	@Override
	public boolean isCacheable() {
		FragmentEntry fragmentEntry =
			FragmentEntryLocalServiceUtil.fetchFragmentEntry(
				getFragmentEntryId());

		if (fragmentEntry != null) {
			return fragmentEntry.isCacheable();
		}

		if (Validator.isNull(getRendererKey())) {
			return false;
		}

		FragmentCollectionContributorRegistry
			fragmentCollectionContributorRegistry =
				_fragmentCollectionContributorRegistrySnapshot.get();

		Map<String, FragmentEntry> fragmentEntries =
			fragmentCollectionContributorRegistry.getFragmentEntries();

		FragmentEntry contributedFragmentEntry = fragmentEntries.get(
			getRendererKey());

		if (contributedFragmentEntry != null) {
			return contributedFragmentEntry.isCacheable();
		}

		return false;
	}

	@Override
	public boolean isLatestVersion() throws PortalException {
		FragmentEntry fragmentEntry =
			FragmentEntryLocalServiceUtil.getFragmentEntry(
				getFragmentEntryId());

		Date fragmentEntryModifiedDate = fragmentEntry.getModifiedDate();

		int value = DateUtil.compareTo(
			fragmentEntryModifiedDate, getLastPropagationDate());

		if (value < 0) {
			return true;
		}

		return false;
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

	private static final Snapshot<FragmentCollectionContributorRegistry>
		_fragmentCollectionContributorRegistrySnapshot = new Snapshot<>(
			FragmentEntryLinkImpl.class,
			FragmentCollectionContributorRegistry.class);

}