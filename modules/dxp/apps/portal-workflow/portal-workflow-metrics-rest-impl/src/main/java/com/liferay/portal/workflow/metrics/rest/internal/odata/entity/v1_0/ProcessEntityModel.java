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

package com.liferay.portal.workflow.metrics.rest.internal.odata.entity.v1_0;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.IntegerEntityField;
import com.liferay.portal.odata.entity.StringEntityField;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Inácio Nery
 */
public class ProcessEntityModel implements EntityModel {

	public ProcessEntityModel() {
		_entityFieldsMap = Stream.of(
			new IntegerEntityField("instanceCount", locale -> "instanceCount"),
			new IntegerEntityField(
				"onTimeInstanceCount", locale -> "onTimeInstanceCount"),
			new IntegerEntityField(
				"overdueInstanceCount", locale -> "overdueInstanceCount"),
			new StringEntityField(
				"title",
				locale ->
					Field.getLocalizedName(locale, "title") +
						StringPool.PERIOD + "keyword")
		).collect(
			Collectors.toMap(EntityField::getName, Function.identity())
		);
	}

	@Override
	public Map<String, EntityField> getEntityFieldsMap() {
		return _entityFieldsMap;
	}

	@Override
	public String getName() {
		String name = ProcessEntityModel.class.getName();

		return StringUtil.replace(name, CharPool.PERIOD, CharPool.UNDERLINE);
	}

	private final Map<String, EntityField> _entityFieldsMap;

}