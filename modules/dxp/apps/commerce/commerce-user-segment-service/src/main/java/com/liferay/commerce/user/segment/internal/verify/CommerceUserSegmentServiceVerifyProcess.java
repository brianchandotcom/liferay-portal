/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.user.segment.internal.verify;

import com.liferay.commerce.user.segment.service.CommerceUserSegmentEntryLocalService;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.verify.VerifyProcess;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = "verify.process.name=com.liferay.commerce.user.segment.service",
	service =
		{CommerceUserSegmentServiceVerifyProcess.class, VerifyProcess.class}
)
public class CommerceUserSegmentServiceVerifyProcess extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		verifyCommerceUserSegmentEntries();
	}

	protected void verifyCommerceUserSegmentEntries() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			List<Company> companies = _companyLocalService.getCompanies();

			for (Company company : companies) {
				List<Group> groups = _groupLocalService.getGroups(
					company.getCompanyId(), GroupConstants.ANY_PARENT_GROUP_ID,
					true);

				for (Group group : groups) {
					ServiceContext serviceContext = new ServiceContext();

					serviceContext.setAddGroupPermissions(true);
					serviceContext.setAddGuestPermissions(true);
					serviceContext.setCompanyId(group.getCompanyId());
					serviceContext.setLanguageId(group.getDefaultLanguageId());
					serviceContext.setScopeGroupId(group.getGroupId());
					serviceContext.setUserId(group.getCreatorUserId());

					_commerceUserSegmentEntryLocalService.
						importSystemCommerceUserSegmentEntries(serviceContext);
				}
			}
		}
	}

	@Reference
	private CommerceUserSegmentEntryLocalService
		_commerceUserSegmentEntryLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

}