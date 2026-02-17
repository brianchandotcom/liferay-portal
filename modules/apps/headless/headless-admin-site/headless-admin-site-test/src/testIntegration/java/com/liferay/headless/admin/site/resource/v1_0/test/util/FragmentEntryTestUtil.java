/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test.util;

import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentCollectionLocalServiceUtil;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

/**
 * @author Lourdes Fernández Besada
 */
public class FragmentEntryTestUtil {

	public static FragmentEntry addCompanyGroupFragmentEntryWithTextEditable()
		throws PortalException {

		Company company = CompanyLocalServiceUtil.getCompany(
			TestPropsValues.getCompanyId());

		FragmentCollection fragmentCollection =
			FragmentCollectionLocalServiceUtil.addFragmentCollection(
				null, TestPropsValues.getUserId(), company.getGroupId(),
				StringUtil.randomString(), StringPool.BLANK,
				ServiceContextTestUtil.getServiceContext(company.getGroupId()));

		return _addFragmentEntry(
			fragmentCollection.getFragmentCollectionId(), company.getGroupId(),
			"<div data-lfr-editable-id=\"element-text\" " +
				"data-lfr-editable-type=\"text\">Default text</div>");
	}

	public static FragmentEntry addFragmentEntry(
			long fragmentCollectionId, long groupId)
		throws PortalException {

		return _addFragmentEntry(
			fragmentCollectionId, groupId, "Fragment Entry HTML");
	}

	private static FragmentEntry _addFragmentEntry(
			long fragmentCollectionId, long groupId, String html)
		throws PortalException {

		return FragmentEntryLocalServiceUtil.addFragmentEntry(
			null, TestPropsValues.getUserId(), groupId, fragmentCollectionId,
			null, RandomTestUtil.randomString(), StringPool.BLANK, html,
			StringPool.BLANK, false, null, null, 0, false, false,
			FragmentConstants.TYPE_COMPONENT, null,
			WorkflowConstants.STATUS_APPROVED,
			ServiceContextTestUtil.getServiceContext(groupId));
	}

}