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

import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.staging.StagingGroupHelper;
import com.liferay.template.constants.TemplatePortletKeys;
import com.liferay.template.info.item.provider.TemplateInfoItemFieldSetProvider;
import com.liferay.template.model.TemplateEntry;
import com.liferay.template.service.TemplateEntryLocalService;
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
				for (TemplateEntry templateEntry :
						_getTemplateEntries(className, classPK)) {

					TemplateInfoItemFieldReader templateInfoItemFieldReader =
						new TemplateInfoItemFieldReader(
							templateEntry,
							InfoItemFieldValues.builder(
							).build());

					consumer.accept(templateInfoItemFieldReader.getInfoField());
				}
			}
		).labelInfoLocalizedValue(
			InfoLocalizedValue.localize(getClass(), "templates")
		).name(
			"templates"
		).build();
	}

	@Override
	public InfoFieldValue<Object> getInfoFieldValue(
		TemplateEntry templateEntry, Object itemObject) {

		if (templateEntry == null) {
			return null;
		}

		InfoItemFieldValues infoItemFieldValues = InfoItemFieldValues.builder(
		).build();

		InfoItemFieldValuesProvider<Object> infoItemFieldValuesProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemFieldValuesProvider.class,
				templateEntry.getInfoItemClassName());

		if (infoItemFieldValuesProvider != null) {
			infoItemFieldValues =
				infoItemFieldValuesProvider.getInfoItemFieldValues(itemObject);
		}

		TemplateInfoItemFieldReader templateInfoItemFieldReader =
			new TemplateInfoItemFieldReader(templateEntry, infoItemFieldValues);

		return new InfoFieldValue<>(
			templateInfoItemFieldReader.getInfoField(),
			templateInfoItemFieldReader.getValue(itemObject));
	}

	private List<TemplateEntry> _getTemplateEntries(
			String className, long classPK)
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
				groupId, className, String.valueOf(classPK), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);
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
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private StagingGroupHelper _stagingGroupHelper;

	@Reference
	private TemplateEntryLocalService _templateEntryLocalService;

}