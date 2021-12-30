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

package com.liferay.exportimport.internal.exportimport.content.processor;

import com.liferay.exportimport.configuration.ExportImportServiceConfiguration;
import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.kernel.exception.ExportImportContentProcessorException;
import com.liferay.exportimport.kernel.exception.ExportImportContentValidationException;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	immediate = true, property = "content.processor.type=LinksToLayouts",
	service = ExportImportContentProcessor.class
)
public class LinksToLayoutsExportImportContentProcessor
	implements ExportImportContentProcessor<String> {

	@Override
	public String replaceExportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content, boolean exportReferencedContent,
			boolean escapeContent)
		throws Exception {

		return _replaceExportLinksToLayouts(
			portletDataContext, stagedModel, content);
	}

	@Override
	public String replaceImportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content)
		throws Exception {

		return _replaceImportLinksToLayouts(portletDataContext, content);
	}

	@Override
	public void validateContentReferences(long groupId, String content)
		throws PortalException {

		if (_isValidateLinksToLayoutsReferences()) {
			_validateLinksToLayoutsReferences(content);
		}
	}

	@Reference(unbind = "-")
	protected void setConfigurationProvider(
		ConfigurationProvider configurationProvider) {

		_configurationProvider = configurationProvider;
	}

	private boolean _isValidateLinksToLayoutsReferences() {
		try {
			ExportImportServiceConfiguration configuration =
				_configurationProvider.getCompanyConfiguration(
					ExportImportServiceConfiguration.class,
					CompanyThreadLocal.getCompanyId());

			return configuration.validateLayoutReferences();
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		return true;
	}

	private String _replaceExportLinksToLayouts(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content)
		throws Exception {

		List<String> oldLinksToLayout = new ArrayList<>();
		List<String> newLinksToLayout = new ArrayList<>();

		Matcher matcher = _exportLinksToLayoutPattern.matcher(content);

		while (matcher.find()) {
			long layoutId = GetterUtil.getLong(matcher.group(1));

			String type = matcher.group(2);

			boolean privateLayout = type.startsWith("private");

			try {
				Layout layout = _layoutLocalService.getLayout(
					portletDataContext.getScopeGroupId(), privateLayout,
					layoutId);

				String oldLinkToLayout = matcher.group(0);

				String newLinkToLayout = StringUtil.replace(
					oldLinkToLayout, type,
					StringBundler.concat(
						type, StringPool.AT, layout.getPlid()));

				oldLinksToLayout.add(oldLinkToLayout);

				newLinksToLayout.add(newLinkToLayout);

				Element entityElement = portletDataContext.getExportDataElement(
					stagedModel);

				portletDataContext.addReferenceElement(
					stagedModel, entityElement, layout,
					PortletDataContext.REFERENCE_TYPE_DEPENDENCY, true);
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled() || _log.isWarnEnabled()) {
					String message = StringBundler.concat(
						"Unable to get layout with ID ", layoutId, " in group ",
						portletDataContext.getScopeGroupId());

					if (_log.isDebugEnabled()) {
						ExportImportContentProcessorException
							exportImportContentProcessorException =
								new ExportImportContentProcessorException(
									message, exception);

						_log.debug(
							message, exportImportContentProcessorException);
					}
					else {
						_log.warn(message);
					}
				}
			}
		}

		return StringUtil.replace(
			content, ArrayUtil.toStringArray(oldLinksToLayout.toArray()),
			ArrayUtil.toStringArray(newLinksToLayout.toArray()));
	}

	private String _replaceImportLinksToLayouts(
			PortletDataContext portletDataContext, String content)
		throws Exception {

		List<String> oldLinksToLayout = new ArrayList<>();
		List<String> newLinksToLayout = new ArrayList<>();

		Matcher matcher = _importLinksToLayoutPattern.matcher(content);

		Map<Long, Long> layoutPlids =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Layout.class);

		String layoutsImportMode = MapUtil.getString(
			portletDataContext.getParameterMap(),
			PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE,
			PortletDataHandlerKeys.LAYOUTS_IMPORT_MODE_MERGE_BY_LAYOUT_UUID);

		while (matcher.find()) {
			long oldPlid = GetterUtil.getLong(matcher.group(4));

			Long newPlid = MapUtil.getLong(layoutPlids, oldPlid);

			if (newPlid == 0) {
				Element missingReferencesElement =
					portletDataContext.getMissingReferencesElement();

				List<Element> elements = missingReferencesElement.elements();

				Layout existingLayout = null;

				for (Element element : elements) {
					String className = element.attributeValue("class-name");

					if (!className.equals(Layout.class.getName())) {
						continue;
					}

					long plid = GetterUtil.getLong(
						element.attributeValue("class-pk"));

					if (oldPlid != plid) {
						continue;
					}

					String uuid = element.attributeValue("uuid");
					String privateLayout = element.attributeValue(
						"private-layout");

					existingLayout =
						_layoutLocalService.fetchLayoutByUuidAndGroupId(
							uuid, portletDataContext.getScopeGroupId(),
							Boolean.valueOf(privateLayout));

					if (existingLayout != null) {
						newPlid = existingLayout.getPlid();

						break;
					}
				}
			}

			long oldGroupId = GetterUtil.getLong(matcher.group(6));

			long newGroupId = oldGroupId;

			long oldLayoutId = GetterUtil.getLong(matcher.group(1));

			long newLayoutId = oldLayoutId;

			Layout layout = _layoutLocalService.fetchLayout(newPlid);

			if (layout != null) {
				newGroupId = layout.getGroupId();
				newLayoutId = layout.getLayoutId();
			}
			else if (_log.isWarnEnabled()) {
				_log.warn("Unable to get layout with PLID " + oldPlid);
			}

			String oldLinkToLayout = matcher.group(0);

			String newLinkToLayout = StringUtil.replaceFirst(
				oldLinkToLayout,
				new String[] {
					StringPool.AT + oldPlid, String.valueOf(oldLayoutId)
				},
				new String[] {StringPool.BLANK, String.valueOf(newLayoutId)});

			if ((layout != null) && layout.isPublicLayout() &&
				layoutsImportMode.equals(
					PortletDataHandlerKeys.
						LAYOUTS_IMPORT_MODE_CREATED_FROM_PROTOTYPE)) {

				newLinkToLayout = StringUtil.replace(
					newLinkToLayout, "private-group", "public");
			}

			if ((oldGroupId != 0) && (oldGroupId != newGroupId)) {
				newLinkToLayout = StringUtil.replaceLast(
					newLinkToLayout, String.valueOf(oldGroupId),
					String.valueOf(newGroupId));
			}

			oldLinksToLayout.add(oldLinkToLayout);
			newLinksToLayout.add(newLinkToLayout);
		}

		return StringUtil.replace(
			content, ArrayUtil.toStringArray(oldLinksToLayout.toArray()),
			ArrayUtil.toStringArray(newLinksToLayout.toArray()));
	}

	private void _validateLinksToLayoutsReferences(String content)
		throws PortalException {

		Matcher matcher = _exportLinksToLayoutPattern.matcher(content);

		while (matcher.find()) {
			long groupId = GetterUtil.getLong(matcher.group(5));

			String type = matcher.group(2);

			boolean privateLayout = type.startsWith("private");

			long layoutId = GetterUtil.getLong(matcher.group(1));

			Layout layout = _layoutLocalService.fetchLayout(
				groupId, privateLayout, layoutId);

			if (layout == null) {
				ExportImportContentValidationException
					exportImportContentValidationException =
						new ExportImportContentValidationException(
							LinksToLayoutsExportImportContentProcessor.class.
								getName());

				exportImportContentValidationException.
					setLayoutReferenceParameters(
						HashMapBuilder.put(
							"groupId", String.valueOf(groupId)
						).put(
							"layoutId", String.valueOf(layoutId)
						).put(
							"privateLayout", String.valueOf(privateLayout)
						).build());

				exportImportContentValidationException.setType(
					ExportImportContentValidationException.LAYOUT_NOT_FOUND);

				throw exportImportContentValidationException;
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LinksToLayoutsExportImportContentProcessor.class);

	private static final Pattern _exportLinksToLayoutPattern = Pattern.compile(
		"\\[([\\d]+)@(private(-group|-user)?|public)(@([\\d]+))?\\]");
	private static final Pattern _importLinksToLayoutPattern = Pattern.compile(
		"\\[([\\d]+)@(private(-group|-user)?|public)@([\\d]+)(@([\\d]+))?\\]");

	private ConfigurationProvider _configurationProvider;

	@Reference
	private LayoutLocalService _layoutLocalService;

}