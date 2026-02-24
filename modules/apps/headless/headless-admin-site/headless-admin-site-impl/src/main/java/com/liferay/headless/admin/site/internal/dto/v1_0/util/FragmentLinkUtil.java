/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.util;

import com.liferay.headless.admin.site.dto.v1_0.FragmentLink;
import com.liferay.headless.admin.site.dto.v1_0.FragmentLinkInlineValue;
import com.liferay.headless.admin.site.dto.v1_0.FragmentLinkMappedValue;
import com.liferay.headless.admin.site.dto.v1_0.FragmentLinkValue;
import com.liferay.headless.admin.site.dto.v1_0.FragmentMappedValueItemReference;
import com.liferay.headless.admin.site.dto.v1_0.Mapping;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

/**
 * @author Mikel Lorza
 */
public class FragmentLinkUtil {

	public static FragmentLink toFragmentLink(
		long companyId, DTOConverterContext dtoConverterContext,
		InfoItemServiceRegistry infoItemServiceRegistry, JSONObject jsonObject,
		long scopeGroupId) {

		if (jsonObject == null) {
			return null;
		}

		boolean mappedValue = FragmentMappingUtil.isMappedValue(jsonObject);

		if (jsonObject.isNull("href") && !mappedValue) {
			return null;
		}

		return new FragmentLink() {
			{
				setTarget(
					() -> {
						String target = jsonObject.getString("target");

						if (Validator.isNull(target)) {
							return null;
						}

						if (StringUtil.equalsIgnoreCase(target, "_parent") ||
							StringUtil.equalsIgnoreCase(target, "_top")) {

							target = "_self";
						}

						return Target.create(
							TargetUtil.toExternalValue(target));
					});
				setValue(
					() -> _toFragmentLinkValue(
						companyId, dtoConverterContext, infoItemServiceRegistry,
						jsonObject, mappedValue, scopeGroupId));
			}
		};
	}

	public static JSONObject toJSONObject(
			long companyId, FragmentLink fragmentLink,
			InfoItemServiceRegistry infoItemServiceRegistry, long scopeGroupId)
		throws PortalException {

		if ((fragmentLink == null) || (fragmentLink.getValue() == null)) {
			return null;
		}

		FragmentLinkValue fragmentLinkValue = fragmentLink.getValue();

		if (fragmentLinkValue == null) {
			return null;
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (fragmentLinkValue instanceof FragmentLinkInlineValue) {
			FragmentLinkInlineValue fragmentLinkInlineValue =
				(FragmentLinkInlineValue)fragmentLinkValue;

			jsonObject.put(
				"href",
				LocalizedValueUtil.toJSONObject(
					fragmentLinkInlineValue.getValue_i18n()));
		}
		else {
			FragmentLinkMappedValue fragmentLinkMappedValue =
				(FragmentLinkMappedValue)fragmentLinkValue;

			jsonObject = FragmentMappingUtil.getFragmentMappedValueJSONObject(
				companyId, infoItemServiceRegistry,
				fragmentLinkMappedValue.getMapping(), scopeGroupId);

			if (jsonObject == null) {
				return null;
			}
		}

		FragmentLink.Target target = fragmentLink.getTarget();

		if (target != null) {
			jsonObject.put(
				"target", TargetUtil.toInternalValue(target.getValue()));
		}

		return jsonObject;
	}

	private static FragmentLinkMappedValue _toFragmentLinkMappedValue(
			long companyId, DTOConverterContext dtoConverterContext,
			InfoItemServiceRegistry infoItemServiceRegistry,
			JSONObject jsonObject, long scopeGroupId)
		throws Exception {

		FragmentMappedValueItemReference fragmentMappedValueItemReference =
			FragmentMappingUtil.getFragmentMappedValueItemReference(
				companyId, infoItemServiceRegistry, jsonObject, scopeGroupId);

		if (fragmentMappedValueItemReference == null) {
			return null;
		}

		FragmentLinkMappedValue fragmentLinkMappedValue =
			new FragmentLinkMappedValue();

		fragmentLinkMappedValue.setMapping(
			() -> new Mapping() {
				{
					setFieldKey(
						() -> FragmentMappingFieldUtil.getFieldKey(
							dtoConverterContext, infoItemServiceRegistry,
							jsonObject, scopeGroupId));
					setItemReference(() -> fragmentMappedValueItemReference);
				}
			});
		fragmentLinkMappedValue.setType(
			() -> FragmentLinkValue.Type.FRAGMENT_MAPPED_VALUE);

		return fragmentLinkMappedValue;
	}

	private static FragmentLinkValue _toFragmentLinkValue(
			long companyId, DTOConverterContext dtoConverterContext,
			InfoItemServiceRegistry infoItemServiceRegistry,
			JSONObject jsonObject, boolean mappedValue, long scopeGroupId)
		throws Exception {

		if (mappedValue) {
			return _toFragmentLinkMappedValue(
				companyId, dtoConverterContext, infoItemServiceRegistry,
				jsonObject, scopeGroupId);
		}

		FragmentLinkInlineValue fragmentLinkInlineValue =
			new FragmentLinkInlineValue();

		fragmentLinkInlineValue.setValue_i18n(
			() -> LocalizedValueUtil.toLocalizedValues(
				jsonObject.getJSONObject("href")));
		fragmentLinkInlineValue.setType(
			() -> FragmentLinkValue.Type.FRAGMENT_INLINE_VALUE);

		return fragmentLinkInlineValue;
	}

}