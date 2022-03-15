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

package com.liferay.template.internal.info.item.renderer;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.item.provider.InfoItemObjectVariationProvider;
import com.liferay.info.item.renderer.InfoItemTemplatedRenderer;
import com.liferay.info.item.renderer.template.InfoItemRendererTemplate;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.staging.StagingGroupHelper;
import com.liferay.template.constants.TemplatePortletKeys;
import com.liferay.template.internal.transformer.TemplateDisplayTemplateTransformer;
import com.liferay.template.internal.transformer.TemplateNodeFactory;
import com.liferay.template.model.TemplateEntry;
import com.liferay.template.service.TemplateEntryLocalService;

import java.io.Writer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Lourdes Fernández Besada
 */
public class TemplateInfoItemTemplatedRenderer<T>
	implements InfoItemTemplatedRenderer<T> {

	public TemplateInfoItemTemplatedRenderer(
		String className, DDMTemplateLocalService ddmTemplateLocalService,
		InfoItemServiceTracker infoItemServiceTracker,
		StagingGroupHelper stagingGroupHelper,
		TemplateEntryLocalService templateEntryLocalService,
		TemplateNodeFactory templateNodeFactory) {

		_className = className;
		_ddmTemplateLocalService = ddmTemplateLocalService;
		_infoItemServiceTracker = infoItemServiceTracker;
		_stagingGroupHelper = stagingGroupHelper;
		_templateEntryLocalService = templateEntryLocalService;
		_templateNodeFactory = templateNodeFactory;
	}

	@Override
	public List<InfoItemRendererTemplate> getInfoItemRendererTemplates(
		String className, String classTypeKey, Locale locale) {

		if (!Objects.equals(_className, className)) {
			return Collections.emptyList();
		}

		List<InfoItemRendererTemplate> infoItemRendererTemplates =
			new ArrayList<>();

		for (TemplateEntry templateEntry :
				_getTemplateEntries(_className, classTypeKey)) {

			DDMTemplate ddmTemplate = _ddmTemplateLocalService.fetchTemplate(
				templateEntry.getDDMTemplateId());

			infoItemRendererTemplates.add(
				new InfoItemRendererTemplate(
					ddmTemplate.getName(locale),
					String.valueOf(templateEntry.getTemplateEntryId())));
		}

		return infoItemRendererTemplates;
	}

	@Override
	public List<InfoItemRendererTemplate> getInfoItemRendererTemplates(
		T t, Locale locale) {

		String infoItemFormVariationKey = StringPool.BLANK;

		InfoItemObjectVariationProvider<T> infoItemObjectVariationProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemObjectVariationProvider.class, _className);

		if (infoItemObjectVariationProvider != null) {
			infoItemFormVariationKey =
				infoItemObjectVariationProvider.getInfoItemFormVariationKey(t);
		}

		return getInfoItemRendererTemplates(
			_className, infoItemFormVariationKey, locale);
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "templates");
	}

	@Override
	public void render(
		T t, String templateEntryId, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		try {
			Writer writer = httpServletResponse.getWriter();

			TemplateEntry templateEntry =
				_templateEntryLocalService.fetchTemplateEntry(
					GetterUtil.getLong(templateEntryId));

			InfoItemFieldValues infoItemFieldValues =
				InfoItemFieldValues.builder(
				).build();

			InfoItemFieldValuesProvider<Object> infoItemFieldValuesProvider =
				_infoItemServiceTracker.getFirstInfoItemService(
					InfoItemFieldValuesProvider.class,
					templateEntry.getInfoItemClassName());

			if (infoItemFieldValuesProvider != null) {
				infoItemFieldValues =
					infoItemFieldValuesProvider.getInfoItemFieldValues(t);
			}

			TemplateDisplayTemplateTransformer
				templateDisplayTemplateTransformer =
					new TemplateDisplayTemplateTransformer(
						templateEntry, infoItemFieldValues,
						_templateNodeFactory);

			writer.write(templateDisplayTemplateTransformer.transform());
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
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
				PortalUtil.getCurrentAndAncestorSiteGroupIds(groupId),
				infoItemClassName, infoItemFormVariationKey, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return Collections.emptyList();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TemplateInfoItemTemplatedRenderer.class);

	private final String _className;
	private final DDMTemplateLocalService _ddmTemplateLocalService;
	private final InfoItemServiceTracker _infoItemServiceTracker;
	private final StagingGroupHelper _stagingGroupHelper;
	private final TemplateEntryLocalService _templateEntryLocalService;
	private final TemplateNodeFactory _templateNodeFactory;

}