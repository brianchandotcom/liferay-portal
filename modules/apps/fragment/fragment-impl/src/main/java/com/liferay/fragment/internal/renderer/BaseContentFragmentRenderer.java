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

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.Serializable;

import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
public abstract class BaseContentFragmentRenderer implements FragmentRenderer {

	protected Tuple getDisplayObject(
		FragmentRendererContext fragmentRendererContext,
		HttpServletRequest httpServletRequest) {

		FragmentEntryLink fragmentEntryLink =
			fragmentRendererContext.getFragmentEntryLink();

		JSONObject jsonObject =
			(JSONObject)fragmentEntryConfigurationParser.getFieldValue(
				getConfiguration(fragmentRendererContext),
				fragmentEntryLink.getEditableValues(),
				fragmentRendererContext.getLocale(), "itemSelector");

		if ((jsonObject != null) && jsonObject.has("className") &&
			jsonObject.has("classPK")) {

			return new Tuple(
				jsonObject.getString("className"),
				jsonObject.getLong("classPK"));
		}

		Optional<InfoItemReference> contextInfoItemReferenceOptional =
			fragmentRendererContext.getContextInfoItemReferenceOptional();

		if (contextInfoItemReferenceOptional.isPresent()) {
			InfoItemReference infoItemReference =
				contextInfoItemReferenceOptional.get();

			InfoItemIdentifier infoItemIdentifier =
				infoItemReference.getInfoItemIdentifier();

			InfoItemObjectProvider<Object> infoItemObjectProvider =
				infoItemServiceTracker.getFirstInfoItemService(
					InfoItemObjectProvider.class,
					infoItemReference.getClassName(),
					infoItemIdentifier.getInfoItemServiceFilter());

			try {
				Object infoItem = infoItemObjectProvider.getInfoItem(
					infoItemIdentifier);

				if (infoItem instanceof ClassedModel) {
					ClassedModel classedModel = (ClassedModel)infoItem;

					Serializable primaryKeyObj =
						classedModel.getPrimaryKeyObj();

					if (!Objects.equals(
							classedModel.getModelClassName(),
							AssetEntry.class.getName())) {

						return new Tuple(
							classedModel.getModelClassName(), primaryKeyObj);
					}

					AssetEntry assetEntry =
						assetEntryLocalService.fetchAssetEntry(
							(Long)primaryKeyObj);

					if (assetEntry != null) {
						return new Tuple(
							portal.getClassName(assetEntry.getClassNameId()),
							assetEntry.getClassPK());
					}
				}
			}
			catch (NoSuchInfoItemException noSuchInfoItemException) {
				if (_log.isDebugEnabled()) {
					_log.debug(noSuchInfoItemException);
				}
			}
		}

		AssetEntry assetEntry = (AssetEntry)httpServletRequest.getAttribute(
			WebKeys.LAYOUT_ASSET_ENTRY);

		if (assetEntry != null) {
			return new Tuple(
				assetEntry.getClassName(), assetEntry.getClassPK());
		}

		return new Tuple(
			fragmentEntryLink.getClassName(), fragmentEntryLink.getClassPK());
	}

	@Reference
	protected AssetEntryLocalService assetEntryLocalService;

	@Reference
	protected FragmentEntryConfigurationParser fragmentEntryConfigurationParser;

	@Reference
	protected InfoItemServiceTracker infoItemServiceTracker;

	@Reference
	protected Portal portal;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseContentFragmentRenderer.class);

}