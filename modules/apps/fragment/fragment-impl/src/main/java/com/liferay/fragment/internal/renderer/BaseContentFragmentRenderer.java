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
import com.liferay.portal.kernel.json.JSONObject;
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
				fragmentEntryLink.getEditableValues(), "itemSelector");

		if ((jsonObject != null) && jsonObject.has("className") &&
			jsonObject.has("classPK")) {

			return new Tuple(
				jsonObject.getString("className"),
				jsonObject.getLong("classPK"));
		}

		Optional<Object> displayObjectOptional =
			fragmentRendererContext.getDisplayObjectOptional();

		if (displayObjectOptional.isPresent()) {
			Object displayObject = displayObjectOptional.get();

			if (displayObject instanceof ClassedModel) {
				ClassedModel classedModel = (ClassedModel)displayObject;

				String modelClassName = classedModel.getModelClassName();
				Serializable primaryKeyObj = classedModel.getPrimaryKeyObj();

				if (!Objects.equals(
						modelClassName, AssetEntry.class.getName())) {

					return new Tuple(modelClassName, primaryKeyObj);
				}

				AssetEntry assetEntry = assetEntryLocalService.fetchAssetEntry(
					(Long)primaryKeyObj);

				if (assetEntry != null) {
					return new Tuple(
						portal.getClassName(assetEntry.getClassNameId()),
						assetEntry.getClassPK());
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
	protected Portal portal;

}