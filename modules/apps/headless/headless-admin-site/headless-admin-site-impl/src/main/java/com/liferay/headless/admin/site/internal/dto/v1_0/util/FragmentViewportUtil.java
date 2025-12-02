/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.util;

import com.liferay.headless.admin.site.dto.v1_0.FragmentViewport;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Mikel Lorza
 */
public class FragmentViewportUtil {

	public static FragmentViewport[] toFragmentViewports(
		JSONObject jsonObject) {

		if (JSONUtil.isEmpty(jsonObject)) {
			return null;
		}

		List<FragmentViewport> fragmentViewports = new ArrayList<>() {
			{
				FragmentViewport fragmentViewport = _toFragmentViewport(
					FragmentViewport.Id.DESKTOP, jsonObject);

				if (fragmentViewport != null) {
					add(fragmentViewport);
				}

				fragmentViewport = _toFragmentViewport(
					FragmentViewport.Id.LANDSCAPE_MOBILE, jsonObject);

				if (fragmentViewport != null) {
					add(fragmentViewport);
				}

				fragmentViewport = _toFragmentViewport(
					FragmentViewport.Id.PORTRAIT_MOBILE, jsonObject);

				if (fragmentViewport != null) {
					add(fragmentViewport);
				}

				fragmentViewport = _toFragmentViewport(
					FragmentViewport.Id.TABLET, jsonObject);

				if (fragmentViewport != null) {
					add(fragmentViewport);
				}
			}
		};

		if (ListUtil.isEmpty(fragmentViewports)) {
			return null;
		}

		return fragmentViewports.toArray(new FragmentViewport[0]);
	}

	public static JSONObject toFragmentViewportsJSONObject(
			FragmentViewport[] fragmentViewports)
		throws JSONException {

		if (ArrayUtil.isEmpty(fragmentViewports)) {
			return null;
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		for (FragmentViewport fragmentViewport : fragmentViewports) {
			String customCSS = fragmentViewport.getCustomCSS();

			if (Validator.isNull(fragmentViewport.getId()) ||
				(Validator.isNull(customCSS) &&
				 (fragmentViewport.getFragmentViewportStyle() == null))) {

				continue;
			}

			JSONObject viewportJSONObject = JSONUtil.put(
				"customCSS", customCSS
			).put(
				"styles",
				FragmentViewportStyleUtil.toJSONObject(
					fragmentViewport.getFragmentViewportStyle())
			);

			if (Objects.equals(
					fragmentViewport.getId(), FragmentViewport.Id.DESKTOP)) {

				jsonObject = JSONUtil.merge(jsonObject, viewportJSONObject);
			}
			else {
				jsonObject.put(
					ViewportIdUtil.toInternalValue(
						fragmentViewport.getId(
						).getValue()),
					viewportJSONObject);
			}
		}

		return jsonObject;
	}

	private static JSONObject _getViewportJSONObject(
		FragmentViewport.Id fragmentViewportId, JSONObject jsonObject) {

		if (Objects.equals(fragmentViewportId, FragmentViewport.Id.DESKTOP)) {
			return jsonObject;
		}

		String viewportId = ViewportIdUtil.toInternalValue(
			fragmentViewportId.getValue());

		if (!jsonObject.has(viewportId)) {
			return null;
		}

		return jsonObject.getJSONObject(viewportId);
	}

	private static FragmentViewport _toFragmentViewport(
		FragmentViewport.Id fragmentViewportId, JSONObject jsonObject) {

		JSONObject viewportJSONObject = _getViewportJSONObject(
			fragmentViewportId, jsonObject);

		if (JSONUtil.isEmpty(viewportJSONObject) ||
			(Validator.isNull(
				viewportJSONObject.getString("customCSS", null)) &&
			 JSONUtil.isEmpty(viewportJSONObject.getJSONObject("styles")))) {

			return null;
		}

		return new FragmentViewport() {
			{
				setCustomCSS(
					() -> viewportJSONObject.getString("customCSS", null));
				setFragmentViewportStyle(
					() -> FragmentViewportStyleUtil.toFragmentViewportStyle(
						viewportJSONObject.getJSONObject("styles")));
				setId(() -> fragmentViewportId);
			}
		};
	}

}