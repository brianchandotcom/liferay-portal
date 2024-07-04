/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.upgrade.v2_13_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.test.util.FragmentEntryTestUtil;
import com.liferay.fragment.test.util.FragmentTestUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ExternalReferenceCodeModel;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.BaseExternalReferenceCodeUpgradeProcessTestCase;

import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
public class FragmentEntryLinkExternalReferenceCodeUpgradeProcessTest
	extends BaseExternalReferenceCodeUpgradeProcessTestCase {

	@Override
	protected ExternalReferenceCodeModel[] addExternalReferenceCodeModels(
			String tableName)
		throws PortalException {

		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(group.getGroupId());

		FragmentEntry fragmentEntry = FragmentEntryTestUtil.addFragmentEntry(
			fragmentCollection.getFragmentCollectionId());

		return new ExternalReferenceCodeModel[] {
			FragmentTestUtil.addFragmentEntryLink(
				group.getGroupId(), fragmentEntry.getFragmentEntryId(), 0)
		};
	}

	@Override
	protected ExternalReferenceCodeModel fetchExternalReferenceCodeModel(
			ExternalReferenceCodeModel externalReferenceCodeModel,
			String tableName)
		throws PortalException {

		FragmentEntryLink fragmentEntryLink =
			(FragmentEntryLink)externalReferenceCodeModel;

		return _fragmentEntryLinkLocalService.fetchFragmentEntryLink(
			fragmentEntryLink.getFragmentEntryLinkId());
	}

	@Override
	protected String[] getTableNames() {
		return new String[] {"FragmentEntryLink"};
	}

	@Override
	protected UpgradeStepRegistrator getUpgradeStepRegistrator() {
		return _upgradeStepRegistrator;
	}

	@Override
	protected Version getVersion() {
		return new Version(2, 13, 0);
	}

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Inject(
		filter = "(&(component.name=com.liferay.fragment.internal.upgrade.registry.FragmentServiceUpgradeStepRegistrator))"
	)
	private UpgradeStepRegistrator _upgradeStepRegistrator;

}