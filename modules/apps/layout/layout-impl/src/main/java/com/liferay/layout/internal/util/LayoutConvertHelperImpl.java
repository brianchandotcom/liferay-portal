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

package com.liferay.layout.internal.util;

import com.liferay.layout.exception.LayoutConvertException;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.LayoutConvertHelper;
import com.liferay.layout.util.LayoutCopyHelper;
import com.liferay.layout.util.template.LayoutConverter;
import com.liferay.layout.util.template.LayoutConverterRegistry;
import com.liferay.layout.util.template.LayoutData;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypePortletConstants;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.model.PortletDecorator;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.ReadOnlyException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rubén Pulido
 */
@Component(immediate = true, service = LayoutConvertHelper.class)
public class LayoutConvertHelperImpl implements LayoutConvertHelper {

	@Override
	public void convertLayout(long plid) throws Exception {
		_convertLayout(plid);
	}

	@Override
	public long[] convertLayouts(long groupId) throws PortalException {
		return convertLayouts(getConvertibleLayoutsPlids(groupId));
	}

	@Override
	public long[] convertLayouts(long[] plids) {
		List<Long> convertedLayoutsPlids = new ArrayList<>();

		for (long plid : plids) {
			try {
				Callable<Layout> callable = new ConvertLayoutCallable(plid);

				Layout layout = TransactionInvokerUtil.invoke(
					_transactionConfig, callable);

				convertedLayoutsPlids.add(layout.getPlid());
			}
			catch (Throwable t) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						String.format(
							"Layout with plid %s cannot be converted", plid),
						t);
				}
			}
		}

		return ArrayUtil.toLongArray(convertedLayoutsPlids);
	}

	@Override
	public long[] getConvertibleLayoutsPlids(long groupId)
		throws PortalException {

		List<Long> convertibleLayoutsPlids = new ArrayList<>();

		ActionableDynamicQuery actionableDynamicQuery =
			_layoutLocalService.getActionableDynamicQuery();

		if (groupId > 0) {
			actionableDynamicQuery.setAddCriteriaMethod(
				dynamicQuery -> {
					dynamicQuery.add(
						RestrictionsFactoryUtil.eq("groupId", groupId));
					dynamicQuery.add(
						RestrictionsFactoryUtil.eq(
							"type", LayoutConstants.TYPE_PORTLET));
				});

			actionableDynamicQuery.setPerformActionMethod(
				(Layout layout) -> {
					UnicodeProperties typeSettingsProperties =
						layout.getTypeSettingsProperties();

					String layoutTemplateId =
						typeSettingsProperties.getProperty(
							LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID);

					if (layoutTemplateId != null) {
						LayoutConverter layoutConverter =
							_layoutConverterRegistry.getLayoutConverter(
								layoutTemplateId);

						if (layoutConverter.isConvertible(layout)) {
							convertibleLayoutsPlids.add(layout.getPlid());
						}
					}
				});
		}

		actionableDynamicQuery.performActions();

		return ArrayUtil.toLongArray(convertibleLayoutsPlids);
	}

	private Layout _convertLayout(long plid) throws Exception {
		Layout layout = _layoutLocalService.getLayout(plid);

		UnicodeProperties typeSettingsProperties =
			layout.getTypeSettingsProperties();

		String layoutTemplateId = typeSettingsProperties.getProperty(
			LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID);

		if (Validator.isNull(layoutTemplateId)) {
			throw new LayoutConvertException(
				"Layout template id cannot be null");
		}

		LayoutConverter layoutConverter =
			_layoutConverterRegistry.getLayoutConverter(layoutTemplateId);

		if (layoutConverter == null) {
			throw new LayoutConvertException(
				"No layout converter exists for layout template id " +
					layoutTemplateId);
		}

		if (!layoutConverter.isConvertible(layout)) {
			throw new LayoutConvertException(
				"Layout with plid " + layout.getPlid() + " is not convertible");
		}

		layout = _layoutLocalService.updateType(
			plid, LayoutConstants.TYPE_CONTENT);

		_updatePortletDecorator(layout);

		LayoutData layoutData = layoutConverter.convert(layout);

		JSONObject layoutDataJSONObject = layoutData.getLayoutDataJSONObject();

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					layout.getGroupId(), _portal.getClassNameId(Layout.class),
					layout.getPlid());

		ServiceContext serviceContext = Optional.ofNullable(
			ServiceContextThreadLocal.getServiceContext()
		).orElse(
			new ServiceContext()
		);

		if (layoutPageTemplateStructure == null) {
			_layoutPageTemplateStructureLocalService.
				addLayoutPageTemplateStructure(
					serviceContext.getUserId(), layout.getGroupId(),
					_portal.getClassNameId(Layout.class), layout.getPlid(),
					layoutDataJSONObject.toString(), serviceContext);
		}
		else {
			_layoutPageTemplateStructureLocalService.
				updateLayoutPageTemplateStructure(
					layout.getGroupId(), _portal.getClassNameId(Layout.class),
					layout.getPlid(), layoutDataJSONObject.toString());
		}

		Layout draftLayout = _layoutLocalService.addLayout(
			layout.getUserId(), layout.getGroupId(), layout.isPrivateLayout(),
			layout.getParentLayoutId(),
			_classNameLocalService.getClassNameId(Layout.class),
			layout.getPlid(), layout.getNameMap(), layout.getTitleMap(),
			layout.getDescriptionMap(), layout.getKeywordsMap(),
			layout.getRobotsMap(), layout.getType(), layout.getTypeSettings(),
			true, true, Collections.emptyMap(), serviceContext);

		_layoutCopyHelper.copyLayout(layout, draftLayout);

		return _layoutLocalService.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			new Date());
	}

	private String _getDefaultPortletDecoratorId(Layout layout)
		throws PortalException {

		Theme theme = layout.getTheme();

		List<PortletDecorator> portletDecorators = theme.getPortletDecorators();

		Stream<PortletDecorator> portletDecoratorsStream =
			portletDecorators.stream();

		List<PortletDecorator> filteredPortletDecorators =
			portletDecoratorsStream.filter(
				PortletDecorator::isDefaultPortletDecorator
			).collect(
				Collectors.toList()
			);

		if (ListUtil.isEmpty(filteredPortletDecorators)) {
			return StringPool.BLANK;
		}

		PortletDecorator defaultPortletDecorator =
			filteredPortletDecorators.get(0);

		return defaultPortletDecorator.getPortletDecoratorId();
	}

	private void _updatePortletDecorator(Layout layout) throws PortalException {
		String defaultPortletDecoratorId = _getDefaultPortletDecoratorId(
			layout);

		List<PortletPreferences> portletPreferencesList =
			_portletPreferencesLocalService.getPortletPreferences(
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid());

		for (PortletPreferences portletPreferences : portletPreferencesList) {
			String preferencesXML = portletPreferences.getPreferences();

			if (Validator.isNull(preferencesXML)) {
				preferencesXML = PortletConstants.DEFAULT_PREFERENCES;
			}

			javax.portlet.PortletPreferences jxPortletPreferences =
				PortletPreferencesFactoryUtil.fromDefaultXML(preferencesXML);

			String portletSetupPortletDecoratorId =
				jxPortletPreferences.getValue(
					"portletSetupPortletDecoratorId", StringPool.BLANK);

			if (Validator.isNotNull(portletSetupPortletDecoratorId)) {
				continue;
			}

			try {
				jxPortletPreferences.setValue(
					"portletSetupPortletDecoratorId",
					defaultPortletDecoratorId);
			}
			catch (ReadOnlyException roe) {
				throw new PortalException(roe);
			}

			portletPreferences.setPreferences(
				PortletPreferencesFactoryUtil.toXML(jxPortletPreferences));

			_portletPreferencesLocalService.updatePortletPreferences(
				portletPreferences);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutConvertHelperImpl.class);

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private LayoutConverterRegistry _layoutConverterRegistry;

	@Reference
	private LayoutCopyHelper _layoutCopyHelper;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	private class ConvertLayoutCallable implements Callable<Layout> {

		@Override
		public Layout call() throws Exception {
			return _convertLayout(_layoutPlid);
		}

		private ConvertLayoutCallable(Long layoutPlid) {
			_layoutPlid = layoutPlid;
		}

		private final long _layoutPlid;

	}

}