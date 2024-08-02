/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0;

import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.service.CPSpecificationOptionService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ListTypeDefinition;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.ListTypeDefinitionResource;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.list.type.service.ListTypeDefinitionService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.Collections;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Zoltán Takács
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/list-type-definition.properties",
	scope = ServiceScope.PROTOTYPE, service = ListTypeDefinitionResource.class
)
public class ListTypeDefinitionResourceImpl
	extends BaseListTypeDefinitionResourceImpl {

	@Override
	public Page<ListTypeDefinition> getSpecificationIdListTypeDefinitionsPage(
			Long id)
		throws Exception {

		CPSpecificationOption cpSpecificationOption =
			_cpSpecificationOptionService.getCPSpecificationOption(id);

		long listTypeDefinitionId =
			cpSpecificationOption.getListTypeDefinitionId();

		if (listTypeDefinitionId == 0) {
			return Page.of(Collections.emptyList());
		}

		return Page.of(
			Collections.singletonList(
				_toListTypeDefinition(
					_listTypeDefinitionService.getListTypeDefinition(
						listTypeDefinitionId))));
	}

	@Override
	public ListTypeDefinition postSpecificationIdListTypeDefinition(
			Long id, ListTypeDefinition listTypeDefinition)
		throws Exception {

		com.liferay.list.type.model.ListTypeDefinition
			serviceBuilderListTypeDefinition =
				_listTypeDefinitionService.addListTypeDefinition(
					StringPool.BLANK,
					HashMapBuilder.put(
						_getLocale(), listTypeDefinition.getName()
					).build(),
					false, Collections.emptyList());

		CPSpecificationOption cpSpecificationOption =
			_cpSpecificationOptionService.getCPSpecificationOption(id);

		_cpSpecificationOptionService.updateCPSpecificationOption(
			cpSpecificationOption.getExternalReferenceCode(),
			cpSpecificationOption.getCPSpecificationOptionId(),
			cpSpecificationOption.getCPOptionCategoryId(),
			serviceBuilderListTypeDefinition.getListTypeDefinitionId(),
			cpSpecificationOption.getTitleMap(),
			cpSpecificationOption.getDescriptionMap(),
			cpSpecificationOption.isFacetable(), cpSpecificationOption.getKey(),
			cpSpecificationOption.getPriority(),
			_serviceContextHelper.getServiceContext());

		return _toListTypeDefinition(serviceBuilderListTypeDefinition);
	}

	private Locale _getLocale() {
		if (contextUser != null) {
			return contextUser.getLocale();
		}

		return contextAcceptLanguage.getPreferredLocale();
	}

	private ListTypeDefinition _toListTypeDefinition(
		com.liferay.list.type.model.ListTypeDefinition listTypeDefinition) {

		if (listTypeDefinition == null) {
			return null;
		}

		Locale locale = _getLocale();

		return new ListTypeDefinition() {
			{
				setDateCreated(listTypeDefinition::getCreateDate);
				setDateModified(listTypeDefinition::getModifiedDate);
				setExternalReferenceCode(
					listTypeDefinition::getExternalReferenceCode);
				setId(listTypeDefinition::getListTypeDefinitionId);
				setName(() -> listTypeDefinition.getName(locale));
				setName_i18n(
					() -> LocalizedMapUtil.getI18nMap(
						listTypeDefinition.getNameMap()));
				setSystem(listTypeDefinition::isSystem);
			}
		};
	}

	@Reference
	private CPSpecificationOptionService _cpSpecificationOptionService;

	@Reference
	private ListTypeDefinitionService _listTypeDefinitionService;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}