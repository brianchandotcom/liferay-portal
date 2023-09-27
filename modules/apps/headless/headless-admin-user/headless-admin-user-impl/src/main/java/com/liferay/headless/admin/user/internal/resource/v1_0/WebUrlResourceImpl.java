/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.internal.resource.v1_0;

import com.liferay.headless.admin.user.dto.v1_0.WebUrl;
import com.liferay.headless.admin.user.internal.dto.v1_0.converter.constants.DTOConverterConstants;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.WebUrlUtil;
import com.liferay.headless.admin.user.resource.v1_0.WebUrlResource;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.service.WebsiteService;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.pagination.Page;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/web-url.properties",
	scope = ServiceScope.PROTOTYPE, service = WebUrlResource.class
)
public class WebUrlResourceImpl extends BaseWebUrlResourceImpl {

	@Override
	public Page<WebUrl> getOrganizationWebUrlsPage(String organizationId)
		throws Exception {

		Organization organization = _organizationResourceDTOConverter.getObject(
			organizationId);

		return Page.of(
			transform(
				_websiteService.getWebsites(
					organization.getModelClassName(),
					organization.getOrganizationId()),
				WebUrlUtil::toWebUrl));
	}

	@Override
	public Page<WebUrl> getUserAccountWebUrlsPage(Long userAccountId)
		throws Exception {

		User user = _userService.getUserById(userAccountId);

		return Page.of(
			transform(
				_websiteService.getWebsites(
					Contact.class.getName(), user.getContactId()),
				WebUrlUtil::toWebUrl));
	}

	@Override
	public WebUrl getWebUrl(Long webUrlId) throws Exception {
		return WebUrlUtil.toWebUrl(_websiteService.getWebsite(webUrlId));
	}

	@Reference(
		target = DTOConverterConstants.ORGANIZATION_RESOURCE_DTO_CONVERTER
	)
	private DTOConverter
		<Organization, com.liferay.headless.admin.user.dto.v1_0.Organization>
			_organizationResourceDTOConverter;

	@Reference
	private UserService _userService;

	@Reference
	private WebsiteService _websiteService;

}