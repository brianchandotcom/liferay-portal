/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.model.listener;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.service.SXPBlueprintLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Joshua Cords
 */
@Component(enabled = false, service = ModelListener.class)
public class GroupModelListener extends BaseModelListener<Group> {

	@Override
	public void onBeforeRemove(Group group) throws ModelListenerException {
		try {
			DynamicQuery dynamicQuery =
				_sxpBlueprintLocalService.dynamicQuery();

			dynamicQuery.add(
				RestrictionsFactoryUtil.eq("companyId", group.getCompanyId()));
			dynamicQuery.add(
				RestrictionsFactoryUtil.like(
					"configurationJSON",
					StringBundler.concat(
						StringPool.PERCENT, StringPool.QUOTE,
						group.getExternalReferenceCode(), StringPool.QUOTE,
						StringPool.PERCENT)));

			List<SXPBlueprint> sxpBlueprints =
				_sxpBlueprintLocalService.dynamicQuery(dynamicQuery);

			for (SXPBlueprint sxpBlueprint : sxpBlueprints) {
				_removeGroupExternalReferenceCode(
					group.getExternalReferenceCode(), sxpBlueprint);
			}
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	private void _removeGroupExternalReferenceCode(
			String externalReferenceCode, SXPBlueprint sxpBlueprint)
		throws Exception {

		String configurationJSON = sxpBlueprint.getConfigurationJSON();

		if (Validator.isNull(configurationJSON)) {
			return;
		}

		JSONObject configurationJSONObject = _jsonFactory.createJSONObject(
			configurationJSON);

		JSONObject generalConfigurationJSONObject =
			configurationJSONObject.getJSONObject("generalConfiguration");

		if (generalConfigurationJSONObject == null) {
			return;
		}

		JSONArray scopeJSONArray = generalConfigurationJSONObject.getJSONArray(
			"scope");

		if (scopeJSONArray == null) {
			return;
		}

		String[] scopeGroupExternalReferenceCodes = JSONUtil.toStringArray(
			scopeJSONArray);

		if (!ArrayUtil.contains(
				scopeGroupExternalReferenceCodes, externalReferenceCode)) {

			return;
		}

		scopeGroupExternalReferenceCodes = ArrayUtil.remove(
			scopeGroupExternalReferenceCodes, externalReferenceCode);

		if (ArrayUtil.isEmpty(scopeGroupExternalReferenceCodes)) {
			generalConfigurationJSONObject.remove("scope");
		}
		else {
			generalConfigurationJSONObject.put(
				"scope",
				JSONUtil.putAll((Object[])scopeGroupExternalReferenceCodes));
		}

		sxpBlueprint.setConfigurationJSON(configurationJSONObject.toString());

		_sxpBlueprintLocalService.updateSXPBlueprint(sxpBlueprint);
	}

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private SXPBlueprintLocalService _sxpBlueprintLocalService;

}