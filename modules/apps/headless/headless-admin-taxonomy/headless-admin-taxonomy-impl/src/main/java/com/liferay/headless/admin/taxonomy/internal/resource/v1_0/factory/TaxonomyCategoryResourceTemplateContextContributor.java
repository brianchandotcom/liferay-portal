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

package com.liferay.headless.admin.taxonomy.internal.resource.v1_0.factory;

import com.liferay.headless.admin.taxonomy.resource.v1_0.TaxonomyCategoryResource;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.template.TemplateContextContributor;
import com.liferay.portal.kernel.util.Portal;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jdk.nashorn.internal.ir.annotations.Reference;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = "type=" + TemplateContextContributor.TYPE_GLOBAL,
	service = TemplateContextContributor.class
)
public class TaxonomyCategoryResourceTemplateContextContributor
	implements TemplateContextContributor {

	@Override
	public void prepare(
		Map<String, Object> contextObjects,
		HttpServletRequest httpServletRequest) {

		try {
			User user = _portal.getUser(httpServletRequest);
			if(user == null){
				user = _userLocalService.getDefaultUser(
					_portal.getCompanyId(httpServletRequest));
			}

			TaxonomyCategoryResource.Builder builder =
				_taxonomyCategoryResourceFactory.create();

			TaxonomyCategoryResource taxonomyCategoryResource =
				builder.httpServletRequest(httpServletRequest).user(
					user
			).build();

			// TODO we can pass a lazy function instead of the real resource
			//  so this resource class is initialized just if really used

			contextObjects.put(
				"taxonomyCategoryResource", taxonomyCategoryResource);
		}
		catch (PortalException portalException) {
			portalException.printStackTrace();
		}
	}

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private TaxonomyCategoryResource.Factory _taxonomyCategoryResourceFactory;

}