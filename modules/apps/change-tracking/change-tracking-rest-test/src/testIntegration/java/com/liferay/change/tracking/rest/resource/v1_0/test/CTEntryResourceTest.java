/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.rest.client.dto.v1_0.CTEntry;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.AddressLocalService;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ListTypeLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;

import org.junit.runner.RunWith;

/**
 * @author Pei-Jung Lan
 */
@RunWith(Arquillian.class)
public class CTEntryResourceTest extends BaseCTEntryResourceTestCase {

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"ctCollectionId", "modelClassNameId", "modelClassPK"
		};
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {"changeType", "ownerName", "siteName", "status"};
	}

	@Override
	protected CTEntry testGetCtCollectionCTEntriesPage_addCTEntry(
			Long ctCollectionId, CTEntry ctEntry)
		throws Exception {

		return _addCTEntry(ctCollectionId, ctEntry.getTitle());
	}

	@Override
	protected Long testGetCtCollectionCTEntriesPage_getCtCollectionId()
		throws Exception {

		return _getCTCollectionId();
	}

	@Override
	protected Long
			testGetCtCollectionCTEntriesPage_getIrrelevantCtCollectionId()
		throws Exception {

		CTCollection ctCollection = _ctCollectionLocalService.addCTCollection(
			null, testCompany.getCompanyId(), testCompany.getUserId(), 0,
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		return ctCollection.getCtCollectionId();
	}

	@Override
	protected CTEntry testGetCTEntry_addCTEntry() throws Exception {
		return _addCTEntry(_getCTCollectionId(), RandomTestUtil.randomString());
	}

	@Override
	protected CTEntry testGraphQLCTEntry_addCTEntry() throws Exception {
		return _addCTEntry(_getCTCollectionId(), RandomTestUtil.randomString());
	}

	private CTEntry _addCTEntry(long ctCollectionId, String name)
		throws Exception {

		Address address = null;

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					Long.valueOf(ctCollectionId))) {

			User user = TestPropsValues.getUser();

			ListType listType = _listTypeLocalService.getListType(
				"personal", ListTypeConstants.CONTACT_ADDRESS);

			address = _addressLocalService.addAddress(
				null, user.getUserId(), Contact.class.getName(),
				user.getContactId(), name, RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), null, null,
				RandomTestUtil.randomString(), null, 0, 0,
				listType.getListTypeId(), false, false, null,
				ServiceContextTestUtil.getServiceContext());
		}

		com.liferay.change.tracking.model.CTEntry serviceBuilderCTEntry =
			_ctEntryLocalService.fetchCTEntry(
				ctCollectionId,
				_classNameLocalService.getClassNameId(Address.class),
				address.getAddressId());

		return ctEntryResource.getCTEntry(serviceBuilderCTEntry.getCtEntryId());
	}

	private long _getCTCollectionId() throws Exception {
		CTCollection ctCollection = _ctCollectionLocalService.addCTCollection(
			null, testCompany.getCompanyId(), testCompany.getUserId(), 0,
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		return ctCollection.getCtCollectionId();
	}

	@Inject
	private AddressLocalService _addressLocalService;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private CTEntryLocalService _ctEntryLocalService;

	@Inject
	private ListTypeLocalService _listTypeLocalService;

}