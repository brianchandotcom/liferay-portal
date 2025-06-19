/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.trash.internal.upgrade.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ExternalReferenceCodeModel;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.BaseExternalReferenceCodeUpgradeProcessTestCase;
import com.liferay.trash.model.TrashEntry;
import com.liferay.trash.service.TrashEntryLocalService;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Manuele Castro
 */
@RunWith(Arquillian.class)
public class TrashEntryExternalReferenceCodeUpgradeProcessTest
	extends BaseExternalReferenceCodeUpgradeProcessTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_journalArticle = JournalTestUtil.addArticle(
			TestPropsValues.getUserId(), group.getGroupId(), 0);
	}

	@Override
	protected ExternalReferenceCodeModel[] addExternalReferenceCodeModels(
			String tableName)
		throws PortalException {

		return new ExternalReferenceCodeModel[] {
			_trashEntryLocalService.addTrashEntry(
				TestPropsValues.getUserId(), _journalArticle.getGroupId(),
				JournalArticle.class.getName(),
				_journalArticle.getResourcePrimKey(), _journalArticle.getUuid(),
				null, _journalArticle.getStatus(), null,
				UnicodePropertiesBuilder.put(
					"title", _journalArticle.getArticleId()
				).build())
		};
	}

	@Override
	protected ExternalReferenceCodeModel fetchExternalReferenceCodeModel(
		ExternalReferenceCodeModel externalReferenceCodeModel,
		String tableName) {

		TrashEntry trashEntry = (TrashEntry)externalReferenceCodeModel;

		return _trashEntryLocalService.fetchTrashEntry(trashEntry.getEntryId());
	}

	@Override
	protected String getExternalReferenceCode(
		ExternalReferenceCodeModel externalReferenceCodeModel,
		String tableName) {

		if (tableName.equals("TrashEntry")) {
			TrashEntry trashEntry = (TrashEntry)externalReferenceCodeModel;

			return trashEntry.getUuid();
		}

		return super.getExternalReferenceCode(
			externalReferenceCodeModel, tableName);
	}

	@Override
	protected String[] getTableNames() {
		return new String[] {"TrashEntry"};
	}

	@Override
	protected UpgradeStepRegistrator getUpgradeStepRegistrator() {
		return _upgradeStepRegistrator;
	}

	@Override
	protected Version getVersion() {
		return new Version(2, 2, 2);
	}

	@Inject(
		filter = "(&(component.name=com.liferay.trash.internal.upgrade.registry.TrashServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	private JournalArticle _journalArticle;

	@Inject
	private TrashEntryLocalService _trashEntryLocalService;

}