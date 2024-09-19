/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_4_x.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.EmailAddress;
import com.liferay.portal.kernel.model.ExternalReferenceCodeModel;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.EmailAddressLocalService;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.BaseExternalReferenceCodeUpgradeProcessTestCase;
import com.liferay.portal.upgrade.v7_4_x.EmailAddressExternalReferenceCodeUpgradeProcess;

import java.util.List;

import org.junit.runner.RunWith;

/**
 * @author Matyas Wollner
 */
@RunWith(Arquillian.class)
public class EmailAddressExternalReferenceCodeUpgradeProcessTest
	extends BaseExternalReferenceCodeUpgradeProcessTestCase {

	@Override
	protected ExternalReferenceCodeModel[] addExternalReferenceCodeModels(
			String tableName)
		throws PortalException {

		User user = _userLocalService.getUser(TestPropsValues.getUserId());

		EmailAddress emailAddress = _emailAddressLocalService.addEmailAddress(
			null, user.getUserId(), Contact.class.getName(),
			user.getContactId(), RandomTestUtil.randomString() + "@liferay.com",
			_getListTypeId(ListTypeConstants.CONTACT_EMAIL_ADDRESS), false,
			new ServiceContext());

		return new ExternalReferenceCodeModel[] {emailAddress};
	}

	@Override
	protected ExternalReferenceCodeModel fetchExternalReferenceCodeModel(
			ExternalReferenceCodeModel externalReferenceCodeModel,
			String tableName)
		throws PortalException {

		EmailAddress emailAddress = (EmailAddress)externalReferenceCodeModel;

		return _emailAddressLocalService.fetchEmailAddress(
			emailAddress.getEmailAddressId());
	}

	@Override
	protected String[] getTableNames() {
		return new String[] {"EmailAddress"};
	}

	@Override
	protected EmailAddressExternalReferenceCodeUpgradeProcess
		getUpgradeProcess() {

		return new EmailAddressExternalReferenceCodeUpgradeProcess();
	}

	@Override
	protected UpgradeStepRegistrator getUpgradeStepRegistrator() {
		return null;
	}

	@Override
	protected Version getVersion() {
		return null;
	}

	private long _getListTypeId(String listTypeId) throws PortalException {
		List<ListType> listTypes = _listTypeLocalService.getListTypes(
			TestPropsValues.getCompanyId(), listTypeId);

		ListType listType = listTypes.get(0);

		return listType.getListTypeId();
	}

	@Inject
	private EmailAddressLocalService _emailAddressLocalService;

	@Inject
	private ListTypeLocalService _listTypeLocalService;

	@Inject
	private UserLocalService _userLocalService;

}