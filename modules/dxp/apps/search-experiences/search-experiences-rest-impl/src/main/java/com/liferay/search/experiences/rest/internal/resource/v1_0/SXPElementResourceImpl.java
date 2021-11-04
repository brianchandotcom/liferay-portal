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

package com.liferay.search.experiences.rest.internal.resource.v1_0;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;
import com.liferay.search.experiences.rest.dto.v1_0.SXPElement;
import com.liferay.search.experiences.rest.dto.v1_0.util.SXPElementUtil;
import com.liferay.search.experiences.rest.resource.v1_0.SXPElementResource;
import com.liferay.search.experiences.service.SXPElementService;

import java.util.Collections;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/sxp-element.properties",
	scope = ServiceScope.PROTOTYPE, service = SXPElementResource.class
)
public class SXPElementResourceImpl extends BaseSXPElementResourceImpl {

	@Override
	public void deleteSXPElement(Long sxpElementId) throws Exception {
		_sxpElementService.deleteSXPElement(sxpElementId);
	}

	@Override
	public SXPElement getSXPElement(Long sxpElementId) throws Exception {
		return SXPElementUtil.toSXPElement(
			_sxpElementService.getSXPElement(sxpElementId),
			contextAcceptLanguage.getPreferredLocale());
	}

	@Override
	public Page<SXPElement> getSXPElementsPage(
			String search, Pagination pagination)
		throws Exception {

		return SearchUtil.search(
			Collections.emptyMap(),
			booleanQuery -> {
			},
			null,
			com.liferay.search.experiences.model.SXPElement.class.getName(),
			search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {

				// TODO Set the relevant search context attributes

				searchContext.setAttribute(Field.DESCRIPTION, search);
				searchContext.setCompanyId(contextCompany.getCompanyId());
			},
			null,
			document -> SXPElementUtil.toSXPElement(
				_sxpElementService.getSXPElement(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK))),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@Override
	public SXPElement postSXPElement(SXPElement sxpElement) throws Exception {
		return SXPElementUtil.toSXPElement(
			_sxpElementService.addSXPElement(
				Collections.singletonMap(
					contextAcceptLanguage.getPreferredLocale(),
					sxpElement.getDescription()),
				String.valueOf(sxpElement.getElementDefinition()), false,
				Collections.singletonMap(
					contextAcceptLanguage.getPreferredLocale(),
					sxpElement.getTitle()),
				0,
				ServiceContextFactory.getInstance(contextHttpServletRequest)),
			contextAcceptLanguage.getPreferredLocale());
	}

	@Reference
	private SXPElementService _sxpElementService;

}