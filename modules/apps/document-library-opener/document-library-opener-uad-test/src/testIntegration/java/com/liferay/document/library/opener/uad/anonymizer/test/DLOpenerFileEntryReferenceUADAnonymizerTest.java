/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.opener.uad.anonymizer.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.opener.model.DLOpenerFileEntryReference;
import com.liferay.document.library.opener.service.DLOpenerFileEntryReferenceLocalService;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.anonymizer.UADAnonymizer;
import com.liferay.user.associated.data.test.util.BaseUADAnonymizerTestCase;

import java.util.ArrayList;
import java.util.List;

import org.junit.AssumptionViolatedException;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Brian Wing Shun Chan
 */
@RunWith(Arquillian.class)
public class DLOpenerFileEntryReferenceUADAnonymizerTest
	extends BaseUADAnonymizerTestCase<DLOpenerFileEntryReference> {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	protected DLOpenerFileEntryReference addBaseModel(long userId)
		throws Exception {

		return addBaseModel(userId, true);
	}

	@Override
	protected DLOpenerFileEntryReference addBaseModel(
			long userId, boolean deleteAfterTestRun)
		throws Exception {

		throw new AssumptionViolatedException("");
	}

	@Override
	protected UADAnonymizer<DLOpenerFileEntryReference> getUADAnonymizer() {
		return _uadAnonymizer;
	}

	@Override
	protected boolean isBaseModelAutoAnonymized(long baseModelPK, User user)
		throws Exception {

		DLOpenerFileEntryReference dlOpenerFileEntryReference =
			_dlOpenerFileEntryReferenceLocalService.
				getDLOpenerFileEntryReference(baseModelPK);

		String userName = dlOpenerFileEntryReference.getUserName();

		if ((dlOpenerFileEntryReference.getUserId() != user.getUserId()) &&
			!userName.equals(user.getFullName())) {

			return true;
		}

		return false;
	}

	@Override
	protected boolean isBaseModelDeleted(long baseModelPK) {
		DLOpenerFileEntryReference dlOpenerFileEntryReference =
			_dlOpenerFileEntryReferenceLocalService.
				fetchDLOpenerFileEntryReference(baseModelPK);

		if (dlOpenerFileEntryReference == null) {
			return true;
		}

		return false;
	}

	@Inject
	private DLOpenerFileEntryReferenceLocalService
		_dlOpenerFileEntryReferenceLocalService;

	@DeleteAfterTestRun
	private final List<DLOpenerFileEntryReference>
		_dlOpenerFileEntryReferences = new ArrayList<>();

	@Inject(
		filter = "component.name=com.liferay.document.library.opener.uad.anonymizer.DLOpenerFileEntryReferenceUADAnonymizer"
	)
	private UADAnonymizer<DLOpenerFileEntryReference> _uadAnonymizer;

}