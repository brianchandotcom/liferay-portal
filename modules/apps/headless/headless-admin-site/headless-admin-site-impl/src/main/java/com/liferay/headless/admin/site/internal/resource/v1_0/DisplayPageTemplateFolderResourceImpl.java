/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0;

import com.liferay.headless.admin.site.resource.v1_0.DisplayPageTemplateFolderResource;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionService;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Rubén Pulido
 * @author Bárbara Cabrera
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/display-page-template-folder.properties",
	scope = ServiceScope.PROTOTYPE,
	service = DisplayPageTemplateFolderResource.class
)
public class DisplayPageTemplateFolderResourceImpl
	extends BaseDisplayPageTemplateFolderResourceImpl {

	@Override
	public void deleteSiteSiteByExternalReferenceCodeDisplayPageTemplateFolder(
			String siteExternalReferenceCode,
			String displayPageTemplateFolderExternalReferenceCode)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35443")) {
			throw new UnsupportedOperationException();
		}

		Group group = _groupLocalService.getGroupByExternalReferenceCode(
			siteExternalReferenceCode, contextCompany.getCompanyId());

		_layoutPageTemplateCollectionService.deleteLayoutPageTemplateCollection(
			displayPageTemplateFolderExternalReferenceCode, group.getGroupId());
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutPageTemplateCollectionService
		_layoutPageTemplateCollectionService;

}