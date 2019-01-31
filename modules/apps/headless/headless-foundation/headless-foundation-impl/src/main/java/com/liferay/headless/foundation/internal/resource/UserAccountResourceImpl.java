/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.headless.foundation.internal.resource;

import com.liferay.headless.foundation.dto.Category;
import com.liferay.headless.foundation.dto.Email;
import com.liferay.headless.foundation.dto.Keyword;
import com.liferay.headless.foundation.dto.Organization;
import com.liferay.headless.foundation.dto.Phone;
import com.liferay.headless.foundation.dto.PostalAddress;
import com.liferay.headless.foundation.dto.Role;
import com.liferay.headless.foundation.dto.UserAccount;
import com.liferay.headless.foundation.dto.Vocabulary;
import com.liferay.headless.foundation.dto.WebSite;
import com.liferay.headless.foundation.dto.WebUrl;
import com.liferay.headless.foundation.resource.UserAccountResource;
import com.liferay.portal.vulcan.context.Pagination;
import com.liferay.portal.vulcan.dto.Page;

import java.util.Collections;

import javax.annotation.Generated;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

/**
 * @author Javier Gamarra
 * @generated
 */
@Component(
	property = {
		JaxrsWhiteboardConstants.JAX_RS_APPLICATION_SELECT + "=(osgi.jaxrs.name=headless-foundation-application.rest)",
		JaxrsWhiteboardConstants.JAX_RS_RESOURCE + "=true", "api.version=1.0.0"
	},
	scope = ServiceScope.PROTOTYPE, service = UserAccountResource.class
)
@Generated("")
public class UserAccountResourceImpl implements UserAccountResource {

	@Override
	public Page<PostalAddress> getAddressesPage(
			Integer genericparentid, Pagination pagination)
		throws Exception {

		return new Page(Collections.emptyList(), 0);
	}

	@Override
	public Page<Category> getCategoriesCategoriesPage(
			Integer parentId, Pagination pagination)
		throws Exception {

		return new Page(Collections.emptyList(), 0);
	}

	@Override
	public Page<Keyword> getContentSpaceKeywordsPage(
			Integer parentId, Pagination pagination)
		throws Exception {

		return new Page(Collections.emptyList(), 0);
	}

	@Override
	public Page<Vocabulary> getContentSpaceVocabulariesPage(
			Integer parentId, Pagination pagination)
		throws Exception {

		return new Page(Collections.emptyList(), 0);
	}

	@Override
	public Page<Email> getEmailsPage(
			Object genericparentid, Pagination pagination)
		throws Exception {

		return new Page(Collections.emptyList(), 0);
	}

	@Override
	public UserAccount getMyUserAccount(Integer id) throws Exception {
		return new UserAccount();
	}

	@Override
	public Page<Organization> getMyUserAccountOrganizationPage(
			Integer parentId, Pagination pagination)
		throws Exception {

		return new Page(Collections.emptyList(), 0);
	}

	@Override
	public Page<UserAccount> getMyUserAccountPage(Pagination pagination)
		throws Exception {

		return new Page(Collections.emptyList(), 0);
	}

	@Override
	public Page<Role> getMyUserAccountRolesPage(
			Integer parentId, Pagination pagination)
		throws Exception {

		return new Page(Collections.emptyList(), 0);
	}

	@Override
	public Page<WebSite> getMyUserAccountWebSitePage(
			Integer parentId, Pagination pagination)
		throws Exception {

		return new Page(Collections.emptyList(), 0);
	}

	@Override
	public Page<Organization> getOrganizationOrganizationPage(
			Integer parentId, Pagination pagination)
		throws Exception {

		return new Page(Collections.emptyList(), 0);
	}

	@Override
	public Page<Organization> getOrganizationPage(Pagination pagination)
		throws Exception {

		return new Page(Collections.emptyList(), 0);
	}

	@Override
	public Page<UserAccount> getOrganizationUserAccountPage(
			Integer parentId, Pagination pagination)
		throws Exception {

		return new Page(Collections.emptyList(), 0);
	}

	@Override
	public Page<Phone> getPhonesPage(
			Object genericparentid, Pagination pagination)
		throws Exception {

		return new Page(Collections.emptyList(), 0);
	}

	@Override
	public Page<Role> getRolesPage(Pagination pagination) throws Exception {
		return new Page(Collections.emptyList(), 0);
	}

	@Override
	public Page<Organization> getUserAccountOrganizationPage(
			Integer parentId, Pagination pagination)
		throws Exception {

		return new Page(Collections.emptyList(), 0);
	}

	@Override
	public Page<UserAccount> getUserAccountPage(
			String fullnamequery, Pagination pagination)
		throws Exception {

		return new Page(Collections.emptyList(), 0);
	}

	@Override
	public Page<Role> getUserAccountRolesPage(
			Integer parentId, Pagination pagination)
		throws Exception {

		return new Page(Collections.emptyList(), 0);
	}

	@Override
	public Page<WebSite> getUserAccountWebSitePage(
			Integer parentId, Pagination pagination)
		throws Exception {

		return new Page(Collections.emptyList(), 0);
	}

	@Override
	public Page<Category> getVocabulariesCategoriesPage(
			Integer parentId, Pagination pagination)
		throws Exception {

		return new Page(Collections.emptyList(), 0);
	}

	@Override
	public Page<UserAccount> getWebSiteUserAccountPage(
			Integer parentId, Pagination pagination)
		throws Exception {

		return new Page(Collections.emptyList(), 0);
	}

	@Override
	public Page<WebSite> getWebSiteWebSitePage(
			Integer parentId, Pagination pagination)
		throws Exception {

		return new Page(Collections.emptyList(), 0);
	}

	@Override
	public Page<WebUrl> getWebUrlsPage(
			Object genericparentid, Pagination pagination)
		throws Exception {

		return new Page(Collections.emptyList(), 0);
	}

	@Override
	public UserAccount postUserAccountBatchCreate() throws Exception {
		return new UserAccount();
	}

}