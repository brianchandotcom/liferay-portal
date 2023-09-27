/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.rest.internal.resource.v1_0;

import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.search.experiences.rest.dto.v1_0.ModelPrefilterContributor;
import com.liferay.search.experiences.rest.internal.resource.v1_0.util.BundleContextUtil;
import com.liferay.search.experiences.rest.resource.v1_0.ModelPrefilterContributorResource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/model-prefilter-contributor.properties",
	scope = ServiceScope.PROTOTYPE,
	service = ModelPrefilterContributorResource.class
)
public class ModelPrefilterContributorResourceImpl
	extends BaseModelPrefilterContributorResourceImpl {

	@Override
	public Page<ModelPrefilterContributor> getModelPrefilterContributorsPage()
		throws Exception {

		return Page.of(
			transformToList(
				BundleContextUtil.getComponentNames(
					ModelPreFilterContributor.class),
				componentName -> new ModelPrefilterContributor() {
					{
						className = componentName;
					}
				}));
	}

}