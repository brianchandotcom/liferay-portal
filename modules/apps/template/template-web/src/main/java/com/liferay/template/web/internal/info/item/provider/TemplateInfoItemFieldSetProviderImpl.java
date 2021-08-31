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

package com.liferay.template.web.internal.info.item.provider;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.template.info.item.provider.TemplateInfoItemFieldSetProvider;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(immediate = true, service = TemplateInfoItemFieldSetProvider.class)
public class TemplateInfoItemFieldSetProviderImpl
	implements TemplateInfoItemFieldSetProvider {

	@Override
	public InfoFieldSet getInfoFieldSet(
		String itemClassName, String itemVariationKey) {

		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			consumer -> {
				for (DDMTemplate ddmTemplate :
						_getDDMTemplates(itemClassName, itemVariationKey)) {

					InfoLocalizedValue<String> labelInfoLocalizedValue =
						InfoLocalizedValue.<String>builder(
						).value(
							LocaleUtil.getDefault(),
							ddmTemplate.getName(LocaleUtil.getDefault())
						).defaultLocale(
							LocaleUtil.getDefault()
						).build();

					consumer.accept(
						InfoField.builder(
						).infoFieldType(
							TextInfoFieldType.INSTANCE
						).name(
							"informationTemplate_" + ddmTemplate.getTemplateId()
						).labelInfoLocalizedValue(
							labelInfoLocalizedValue
						).build());
				}
			}
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "information-templates")
		).name(
			"information-templates"
		).build();
	}

	private List<DDMTemplate> _getDDMTemplates(
		String itemClassName, String itemVariationKey) {

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			return Collections.emptyList();
		}

		try {
			List<DDMTemplate> allVariationTemplates =
				_ddmTemplateLocalService.getTemplates(
					serviceContext.getScopeGroupId(),
					_portal.getClassNameId(itemClassName),
					GetterUtil.getLong(itemVariationKey), true);

			Stream<DDMTemplate> allVariationTemplatesStream =
				allVariationTemplates.stream();

			return allVariationTemplatesStream.filter(
				ddmTemplate ->
					ddmTemplate.getResourceClassNameId() ==
						_getInfoItemFormProviderClassNameId()
			).collect(
				Collectors.toList()
			);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(
				"Caught unexpected exception", portalException);
		}
	}

	private long _getInfoItemFormProviderClassNameId() {
		if (_infoItemFormProviderClassNameId != null) {
			return _infoItemFormProviderClassNameId;
		}

		_infoItemFormProviderClassNameId = _portal.getClassNameId(
			InfoItemFormProvider.class.getName());

		return _infoItemFormProviderClassNameId;
	}

	@Reference
	private DDMTemplateLocalService _ddmTemplateLocalService;

	private Long _infoItemFormProviderClassNameId;

	@Reference
	private Portal _portal;

}