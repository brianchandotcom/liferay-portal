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

package com.liferay.template.internal.info.item.provider;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portlet.display.template.PortletDisplayTemplate;
import com.liferay.staging.StagingGroupHelper;
import com.liferay.template.constants.TemplatePortletKeys;
import com.liferay.template.info.item.provider.TemplateInfoItemFieldSetProvider;
import com.liferay.template.internal.transformer.TemplateDisplayTemplateTransformer;
import com.liferay.template.internal.transformer.TemplateNodeFactory;
import com.liferay.template.model.TemplateEntry;
import com.liferay.template.service.TemplateEntryLocalService;

import java.util.ArrayList;
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
	public InfoFieldSet getInfoFieldSet(
		String infoItemClassName, String infoItemFormVariationKey) {

		return InfoFieldSet.builder(
		).infoFieldSetEntry(
			consumer -> {
				for (TemplateEntry templateEntry :
						_getTemplateEntries(
							infoItemClassName, infoItemFormVariationKey)) {

					consumer.accept(_getInfoField(templateEntry));
				}
			}
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "templates")
		).name(
			"templates"
		).build();
	}

	@Override
	public List<InfoFieldValue<Object>> getInfoFieldValues(
		String infoItemClassName, String infoItemFormVariationKey,
		Object itemObject) {

		List<InfoFieldValue<Object>> infoFieldValues = new ArrayList<>();

		for (TemplateEntry templateEntry :
				_getTemplateEntries(
					infoItemClassName, infoItemFormVariationKey)) {

			infoFieldValues.add(_getInfoFieldValue(templateEntry, itemObject));
		}

		return infoFieldValues;
	}

	private InfoField<?> _getInfoField(TemplateEntry templateEntry) {
		DDMTemplate ddmTemplate = _ddmTemplateLocalService.fetchDDMTemplate(
			templateEntry.getDDMTemplateId());

		return InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).name(
			PortletDisplayTemplate.DISPLAY_STYLE_PREFIX +
				templateEntry.getTemplateEntryId()
		).labelInfoLocalizedValue(
			InfoLocalizedValue.<String>builder(
			).value(
				LocaleUtil.getDefault(),
				ddmTemplate.getName(LocaleUtil.getDefault())
			).defaultLocale(
				LocaleUtil.getDefault()
			).build()
		).build();
	}

	private InfoFieldValue<Object> _getInfoFieldValue(
		TemplateEntry templateEntry, Object itemObject) {

		if (templateEntry == null) {
			return null;
		}

		return new InfoFieldValue<>(
			_getInfoField(templateEntry),
			() -> {
				InfoItemFieldValues infoItemFieldValues =
					InfoItemFieldValues.builder(
					).build();

				InfoItemFieldValuesProvider<Object>
					infoItemFieldValuesProvider =
						_infoItemServiceTracker.getFirstInfoItemService(
							InfoItemFieldValuesProvider.class,
							templateEntry.getInfoItemClassName());

				if (infoItemFieldValuesProvider != null) {
					infoItemFieldValues =
						infoItemFieldValuesProvider.getInfoItemFieldValues(
							itemObject);
				}

				TemplateDisplayTemplateTransformer
					templateDisplayTemplateTransformer =
						new TemplateDisplayTemplateTransformer(
							templateEntry, infoItemFieldValues,
							_templateNodeFactory);

				try {
					return templateDisplayTemplateTransformer.transform();
				}
				catch (Exception exception) {
					_log.error("Unable to transform template", exception);
				}

				return StringPool.BLANK;
			});
	}

	private List<TemplateEntry> _getTemplateEntries(
			String infoItemClassName, String infoItemFormVariationKey)
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

			return _templateEntryLocalService.getTemplateEntries(
				groupId, infoItemClassName, infoItemFormVariationKey,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return Collections.emptyList();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TemplateInfoItemFieldSetProviderImpl.class);

	@Reference
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private StagingGroupHelper _stagingGroupHelper;

	@Reference
	private TemplateEntryLocalService _templateEntryLocalService;

	@Reference
	private TemplateNodeFactory _templateNodeFactory;

}