/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.rest.internal.resource.v1_0;

import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.search.experiences.rest.dto.v1_0.KeywordQueryContributor;
import com.liferay.search.experiences.rest.internal.resource.v1_0.util.BundleContextUtil;
import com.liferay.search.experiences.rest.resource.v1_0.KeywordQueryContributorResource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/keyword-query-contributor.properties",
	scope = ServiceScope.PROTOTYPE,
	service = KeywordQueryContributorResource.class
)
public class KeywordQueryContributorResourceImpl
	extends BaseKeywordQueryContributorResourceImpl {

	@Override
	public Page<KeywordQueryContributor> getKeywordQueryContributorsPage()
		throws Exception {

		return Page.of(
			transformToList(
				BundleContextUtil.getComponentNames(
					com.liferay.portal.search.spi.model.query.contributor.
						KeywordQueryContributor.class),
				componentName -> new KeywordQueryContributor() {
					{
						className = componentName;
					}
				}));
	}

}