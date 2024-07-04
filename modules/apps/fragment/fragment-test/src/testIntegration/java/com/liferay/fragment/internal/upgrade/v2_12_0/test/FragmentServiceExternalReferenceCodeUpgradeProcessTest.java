/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.upgrade.v2_12_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentComposition;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryVersion;
import com.liferay.fragment.service.FragmentCompositionService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.fragment.test.util.FragmentEntryTestUtil;
import com.liferay.fragment.test.util.FragmentTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ExternalReferenceCodeModel;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.BaseExternalReferenceCodeUpgradeProcessTestCase;

import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 */
@RunWith(Arquillian.class)
public class FragmentServiceExternalReferenceCodeUpgradeProcessTest
	extends BaseExternalReferenceCodeUpgradeProcessTestCase {

	@Override
	protected ExternalReferenceCodeModel[] addExternalReferenceCodeModels(
			String tableName)
		throws PortalException {

		FragmentCollection fragmentCollection =
			FragmentTestUtil.addFragmentCollection(group.getGroupId());

		if (tableName.equals("FragmentComposition")) {
			return new ExternalReferenceCodeModel[] {
				_fragmentCompositionService.addFragmentComposition(
					null, group.getGroupId(),
					fragmentCollection.getFragmentCollectionId(),
					StringUtil.randomId(), RandomTestUtil.randomString(),
					StringPool.BLANK, StringPool.BLANK, 0,
					WorkflowConstants.STATUS_APPROVED,
					ServiceContextTestUtil.getServiceContext(
						group.getGroupId(), TestPropsValues.getUserId()))
			};
		}
		else if (tableName.equals("FragmentEntry")) {
			return new ExternalReferenceCodeModel[] {
				FragmentEntryTestUtil.addFragmentEntry(
					fragmentCollection.getFragmentCollectionId(),
					RandomTestUtil.randomString())
			};
		}
		else if (tableName.equals("FragmentEntryVersion")) {
			FragmentEntry fragmentEntry =
				FragmentEntryTestUtil.addFragmentEntry(
					fragmentCollection.getFragmentCollectionId(),
					RandomTestUtil.randomString());

			return new ExternalReferenceCodeModel[] {
				_fragmentEntryLocalService.fetchLatestVersion(fragmentEntry)
			};
		}

		return null;
	}

	@Override
	protected ExternalReferenceCodeModel fetchExternalReferenceCodeModel(
			ExternalReferenceCodeModel externalReferenceCodeModel,
			String tableName)
		throws PortalException {

		if (tableName.equals("FragmentComposition")) {
			FragmentComposition fragmentComposition =
				(FragmentComposition)externalReferenceCodeModel;

			return _fragmentCompositionService.fetchFragmentComposition(
				fragmentComposition.getFragmentCompositionId());
		}
		else if (tableName.equals("FragmentEntry")) {
			FragmentEntry fragmentEntry =
				(FragmentEntry)externalReferenceCodeModel;

			return _fragmentEntryLocalService.fetchFragmentEntry(
				fragmentEntry.getFragmentEntryId());
		}
		else if (tableName.equals("FragmentEntryVersion")) {
			FragmentEntryVersion fragmentEntryVersion =
				(FragmentEntryVersion)externalReferenceCodeModel;

			FragmentEntry fragmentEntry =
				_fragmentEntryLocalService.fetchFragmentEntry(
					fragmentEntryVersion.getFragmentEntryId());

			return _fragmentEntryLocalService.fetchLatestVersion(fragmentEntry);
		}

		return null;
	}

	@Override
	protected String getExternalReferenceCode(
		ExternalReferenceCodeModel externalReferenceCodeModel,
		String tableName) {

		if (tableName.equals("FragmentEntryVersion")) {
			FragmentEntryVersion fragmentEntryVersion =
				(FragmentEntryVersion)externalReferenceCodeModel;

			return fragmentEntryVersion.getUuid();
		}

		return super.getExternalReferenceCode(
			externalReferenceCodeModel, tableName);
	}

	@Override
	protected String[] getTableNames() {
		return new String[] {
			"FragmentComposition", "FragmentEntry", "FragmentEntryVersion"
		};
	}

	@Override
	protected UpgradeStepRegistrator getUpgradeStepRegistrator() {
		return _upgradeStepRegistrator;
	}

	@Override
	protected Version getVersion() {
		return new Version(2, 12, 0);
	}

	@Inject(
		filter = "(&(component.name=com.liferay.fragment.internal.upgrade.registry.FragmentServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private FragmentCompositionService _fragmentCompositionService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

}