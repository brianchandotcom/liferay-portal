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
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.staging.StagingGroupHelper;
import com.liferay.template.constants.TemplatePortletKeys;
import com.liferay.template.info.item.provider.TemplateInfoItemFieldSetProvider;
import com.liferay.template.web.internal.info.item.field.reader.TemplateInfoItemFieldReader;

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

					TemplateInfoItemFieldReader templateInfoItemFieldReader =
						new TemplateInfoItemFieldReader(
							ddmTemplate,
							InfoItemFieldValues.builder(
							).build());

					consumer.accept(templateInfoItemFieldReader.getInfoField());
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

		TemplateInfoItemFieldReader templateInfoItemFieldReader =
			new TemplateInfoItemFieldReader(
				ddmTemplate,
				InfoItemFieldValues.builder(
				).build());

		return new InfoFieldValue<>(
			templateInfoItemFieldReader.getInfoField(),
			templateInfoItemFieldReader.getValue(itemObject));
	}

	private List<DDMTemplate> _getDDMTemplates(String className, long classPK)
		throws RuntimeException {

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			return Collections.emptyList();
		}

		try {
			long groupId = serviceContext.getScopeGroupId();

			if (!_stagingGroupHelper.isStagedPortlet(
					groupId, TemplatePortletKeys.TEMPLATE)) {

				Group liveGroup = _stagingGroupHelper.fetchLiveGroup(groupId);

				if (liveGroup != null) {
					groupId = liveGroup.getGroupId();
				}
			}

			return _ddmTemplateLocalService.getTemplates(
				serviceContext.getCompanyId(),
				_portal.getCurrentAndAncestorSiteGroupIds(groupId),
				new long[] {_portal.getClassNameId(className)},
				new long[] {classPK},
				_portal.getClassNameId(InfoItemFormProvider.class.getName()),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			return Collections.emptyList();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TemplateInfoItemFieldSetProviderImpl.class);

	@Reference
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private StagingGroupHelper _stagingGroupHelper;

}