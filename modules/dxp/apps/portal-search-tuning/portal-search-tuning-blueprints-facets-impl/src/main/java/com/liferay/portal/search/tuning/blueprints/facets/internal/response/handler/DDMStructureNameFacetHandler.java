/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.blueprints.facets.internal.response.handler;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.search.aggregation.bucket.Bucket;
import com.liferay.portal.search.tuning.blueprints.attributes.BlueprintsAttributes;
import com.liferay.portal.search.tuning.blueprints.facets.constants.FacetsJSONResponseKeys;
import com.liferay.portal.search.tuning.blueprints.facets.spi.response.FacetResponseHandler;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true, property = "name=ddm_structure_name",
	service = FacetResponseHandler.class
)
public class DDMStructureNameFacetHandler
	extends BaseTermsFacetResponseHandler implements FacetResponseHandler {

	@Override
	protected JSONObject createBucketJSONObject(
			Bucket bucket, BlueprintsAttributes blueprintsAttributes,
			ResourceBundle resourceBundle)
		throws Exception {

		Locale locale = blueprintsAttributes.getLocale();

		long frequency = bucket.getDocCount();

		String value = bucket.getKey();

		DDMStructure ddmStructure = _getDDMStructure(value);

		String name = ddmStructure.getName(locale, true);

		Group group = _groupLocalService.getGroup(ddmStructure.getGroupId());

		return JSONUtil.put(
			FacetsJSONResponseKeys.FREQUENCY, bucket.getDocCount()
		).put(
			FacetsJSONResponseKeys.GROUP_NAME, group.getName(locale, true)
		).put(
			FacetsJSONResponseKeys.NAME, name
		).put(
			FacetsJSONResponseKeys.TEXT, getText(name, frequency, null)
		).put(
			FacetsJSONResponseKeys.VALUE, value
		);
	}

	private DDMStructure _getDDMStructure(String ddmStructureKey) {
		DynamicQuery dynamicQuery = _ddmStructureLocalService.dynamicQuery();

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq("structureKey", ddmStructureKey));

		List<DDMStructure> structures = _ddmStructureLocalService.dynamicQuery(
			dynamicQuery);

		return structures.get(0);
	}

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

}