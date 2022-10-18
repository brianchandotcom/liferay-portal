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

package com.liferay.fragment.internal.renderer;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemDetails;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.info.item.provider.InfoItemPermissionProvider;
import com.liferay.info.item.renderer.InfoItemRenderer;
import com.liferay.info.item.renderer.InfoItemRendererTracker;
import com.liferay.info.item.renderer.InfoItemTemplatedRenderer;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.layout.display.page.constants.LayoutDisplayPageWebKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(service = FragmentRenderer.class)
public class ContentObjectFragmentRenderer implements FragmentRenderer {

	@Override
	public String getCollectionKey() {
		return "content-display";
	}

	@Override
	public String getConfiguration(
		FragmentRendererContext fragmentRendererContext) {

		return JSONUtil.put(
			"fieldSets",
			JSONUtil.putAll(
				JSONUtil.put(
					"fields",
					JSONUtil.putAll(
						JSONUtil.put(
							"label", "item"
						).put(
							"name", "itemSelector"
						).put(
							"type", "itemSelector"
						).put(
							"typeOptions",
							JSONUtil.put("enableSelectTemplate", true)
						))))
		).toString();
	}

	@Override
	public String getIcon() {
		return "web-content";
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", getClass());

		return _language.get(resourceBundle, "content-display");
	}

	@Override
	public void render(
		FragmentRendererContext fragmentRendererContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		JSONObject jsonObject = _getFieldValueJSONObject(
			fragmentRendererContext);

		Optional<InfoItemReference> contextInfoItemReferenceOptional =
			fragmentRendererContext.getContextInfoItemReferenceOptional();

		if (!contextInfoItemReferenceOptional.isPresent() &&
			((jsonObject == null) || (jsonObject.length() == 0))) {

			if (FragmentRendererUtil.isEditMode(httpServletRequest)) {
				FragmentRendererUtil.printPortletMessageInfo(
					httpServletRequest, httpServletResponse,
					"the-selected-content-will-be-shown-here");
			}

			return;
		}

		String className = StringPool.BLANK;
		Object displayObject = null;

		if (jsonObject != null) {
			className = jsonObject.getString("className");

			displayObject = _getDisplayObject(
				className, jsonObject.getLong("classPK"),
				contextInfoItemReferenceOptional);
		}
		else {
			InfoItemReference infoItemReference =
				contextInfoItemReferenceOptional.orElse(null);

			displayObject = _getInfoItem(infoItemReference);
		}

		if (displayObject == null) {
			if (FragmentRendererUtil.isEditMode(httpServletRequest)) {
				FragmentRendererUtil.printPortletMessageInfo(
					httpServletRequest, httpServletResponse,
					"the-selected-content-is-no-longer-available.-please-" +
						"select-another");
			}

			return;
		}

		Tuple tuple = _getTuple(
			className, displayObject.getClass(), fragmentRendererContext);

		InfoItemRenderer<Object> infoItemRenderer =
			(InfoItemRenderer<Object>)tuple.getObject(0);

		if (infoItemRenderer == null) {
			if (FragmentRendererUtil.isEditMode(httpServletRequest)) {
				FragmentRendererUtil.printPortletMessageInfo(
					httpServletRequest, httpServletResponse,
					"there-are-no-available-renderers-for-the-selected-" +
						"content");
			}

			return;
		}

		if (!_hasPermission(httpServletRequest, className, displayObject)) {
			FragmentRendererUtil.printPortletMessageInfo(
				httpServletRequest, httpServletResponse,
				"you-do-not-have-permission-to-access-the-requested-resource");

			return;
		}

		if (infoItemRenderer instanceof InfoItemTemplatedRenderer) {
			InfoItemTemplatedRenderer<Object> infoItemTemplatedRenderer =
				(InfoItemTemplatedRenderer<Object>)infoItemRenderer;

			if (tuple.getSize() > 1) {
				infoItemTemplatedRenderer.render(
					displayObject, (String)tuple.getObject(1),
					httpServletRequest, httpServletResponse);
			}
			else {
				infoItemTemplatedRenderer.render(
					displayObject, httpServletRequest, httpServletResponse);
			}
		}
		else {
			infoItemRenderer.render(
				displayObject, httpServletRequest, httpServletResponse);
		}
	}

	private Object _getDisplayObject(
		String className, long classPK,
		Optional<InfoItemReference> infoItemReferenceOptional) {

		InfoItemObjectProvider<?> infoItemObjectProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemObjectProvider.class, className);

		InfoItemReference infoItemReference = infoItemReferenceOptional.orElse(
			null);

		if (infoItemObjectProvider == null) {
			return _getInfoItem(infoItemReference);
		}

		try {
			Object infoItem = infoItemObjectProvider.getInfoItem(
				new ClassPKInfoItemIdentifier(classPK));

			if (infoItem == null) {
				return _getInfoItem(infoItemReference);
			}

			return infoItem;
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return _getInfoItem(infoItemReference);
	}

	private JSONObject _getFieldValueJSONObject(
		FragmentRendererContext fragmentRendererContext) {

		FragmentEntryLink fragmentEntryLink =
			fragmentRendererContext.getFragmentEntryLink();

		return (JSONObject)_fragmentEntryConfigurationParser.getFieldValue(
			getConfiguration(fragmentRendererContext),
			fragmentEntryLink.getEditableValues(),
			fragmentRendererContext.getLocale(), "itemSelector");
	}

	private Object _getInfoItem(InfoItemReference infoItemReference) {
		if (infoItemReference == null) {
			return null;
		}

		InfoItemIdentifier infoItemIdentifier =
			infoItemReference.getInfoItemIdentifier();

		InfoItemObjectProvider<Object> infoItemObjectProvider =
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemObjectProvider.class, infoItemReference.getClassName(),
				infoItemIdentifier.getInfoItemServiceFilter());

		try {
			return infoItemObjectProvider.getInfoItem(infoItemIdentifier);
		}
		catch (NoSuchInfoItemException noSuchInfoItemException) {
			if (_log.isDebugEnabled()) {
				_log.debug(noSuchInfoItemException);
			}
		}

		return null;
	}

	private Tuple _getTuple(
		String className, Class<?> displayObjectClass,
		FragmentRendererContext fragmentRendererContext) {

		List<InfoItemRenderer<?>> infoItemRenderers =
			FragmentRendererUtil.getInfoItemRenderers(
				className, displayObjectClass, _infoItemRendererTracker);

		if (infoItemRenderers == null) {
			return null;
		}

		InfoItemRenderer<Object> defaultInfoItemRenderer =
			(InfoItemRenderer<Object>)infoItemRenderers.get(0);

		JSONObject jsonObject = _getFieldValueJSONObject(
			fragmentRendererContext);

		if (jsonObject == null) {
			return new Tuple(defaultInfoItemRenderer);
		}

		JSONObject templateJSONObject = jsonObject.getJSONObject("template");

		if (templateJSONObject == null) {
			return new Tuple(defaultInfoItemRenderer);
		}

		String infoItemRendererKey = templateJSONObject.getString(
			"infoItemRendererKey");

		InfoItemRenderer<Object> infoItemRenderer =
			(InfoItemRenderer<Object>)
				_infoItemRendererTracker.getInfoItemRenderer(
					infoItemRendererKey);

		if (infoItemRenderer != null) {
			return new Tuple(
				infoItemRenderer, templateJSONObject.getString("templateKey"));
		}

		return new Tuple(defaultInfoItemRenderer);
	}

	private boolean _hasPermission(
		HttpServletRequest httpServletRequest, String className,
		Object displayObject) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		InfoItemReference infoItemReference =
			(InfoItemReference)httpServletRequest.getAttribute(
				InfoDisplayWebKeys.INFO_ITEM_REFERENCE);

		if (Validator.isNull(className) &&
			Validator.isNotNull(infoItemReference.getClassName())) {

			className = infoItemReference.getClassName();
		}

		LayoutDisplayPageProvider<?> layoutDisplayPageProvider =
			(LayoutDisplayPageProvider<?>)httpServletRequest.getAttribute(
				LayoutDisplayPageWebKeys.LAYOUT_DISPLAY_PAGE_PROVIDER);

		if (Validator.isNull(className) &&
			(layoutDisplayPageProvider != null)) {

			className = layoutDisplayPageProvider.getClassName();
		}

		InfoItemDetails infoItemDetails =
			(InfoItemDetails)httpServletRequest.getAttribute(
				InfoDisplayWebKeys.INFO_ITEM_DETAILS);

		if (Validator.isNull(className) && (infoItemDetails != null)) {
			className = infoItemDetails.getClassName();
		}

		try {
			InfoItemPermissionProvider infoItemPermissionProvider =
				_infoItemServiceTracker.getFirstInfoItemService(
					InfoItemPermissionProvider.class, className);

			if ((infoItemPermissionProvider != null) &&
				!infoItemPermissionProvider.hasPermission(
					themeDisplay.getPermissionChecker(), displayObject,
					ActionKeys.VIEW)) {

				return false;
			}
		}
		catch (Exception exception) {
			_log.error("Unable to check display object permissions", exception);

			return false;
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ContentObjectFragmentRenderer.class);

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	@Reference
	private InfoItemRendererTracker _infoItemRendererTracker;

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private Language _language;

}