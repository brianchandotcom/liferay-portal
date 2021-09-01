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
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.template.info.item.provider.TemplateInfoItemFieldSetProvider;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(immediate = true, service = TemplateInfoItemFieldSetProvider.class)
public class TemplateInfoItemFieldSetProviderImpl
	implements TemplateInfoItemFieldSetProvider {

	@Override
	public InfoFieldSet getInfoFieldSet(String className, long classPK) {
		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			consumer -> {
				for (DDMTemplate ddmTemplate :
						_getDDMTemplates(className, classPK)) {

					consumer.accept(
						InfoField.builder(
						).infoFieldType(
							TextInfoFieldType.INSTANCE
						).name(
							"informationTemplate_" + ddmTemplate.getTemplateId()
						).labelInfoLocalizedValue(
							InfoLocalizedValue.<String>builder(
							).value(
								LocaleUtil.getDefault(),
								ddmTemplate.getName(LocaleUtil.getDefault())
							).defaultLocale(
								LocaleUtil.getDefault()
							).build()
						).build());
				}
			}
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "information-templates")
		).name(
			"information-templates"
		).build();
	}

	@Override
	public InfoFieldValue<Object> getInfoFieldValue(
		DDMTemplate ddmTemplate, Object itemObject) {

		if ((ddmTemplate == null) ||
			(ddmTemplate.getResourceClassNameId() != _portal.getClassNameId(
				InfoItemFormProvider.class.getName()))) {

			return null;
		}

		return new InfoFieldValue<>(
			InfoField.builder(
			).infoFieldType(
				TextInfoFieldType.INSTANCE
			).name(
				"informationTemplate_" + ddmTemplate.getTemplateId()
			).labelInfoLocalizedValue(
				InfoLocalizedValue.<String>builder(
				).value(
					LocaleUtil.getDefault(),
					ddmTemplate.getName(LocaleUtil.getDefault())
				).defaultLocale(
					LocaleUtil.getDefault()
				).build()
			).build(),
			StringPool.BLANK);
	}

	private List<DDMTemplate> _getDDMTemplates(String className, long classPK) {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			return Collections.emptyList();
		}

		return _ddmTemplateLocalService.getTemplates(
			serviceContext.getCompanyId(),
			ArrayUtil.append(
				_portal.getAncestorSiteGroupIds(
					serviceContext.getScopeGroupId()),
				new long[] {serviceContext.getScopeGroupId()}),
			new long[] {_portal.getClassNameId(className)},
			new long[] {classPK},
			_portal.getClassNameId(InfoItemFormProvider.class.getName()),
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	@Reference
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@Reference
	private Portal _portal;

}