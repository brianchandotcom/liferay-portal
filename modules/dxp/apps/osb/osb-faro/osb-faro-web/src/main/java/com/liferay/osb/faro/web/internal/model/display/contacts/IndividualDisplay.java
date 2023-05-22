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

package com.liferay.osb.faro.web.internal.model.display.contacts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;

import com.liferay.osb.faro.engine.client.model.Field;
import com.liferay.osb.faro.engine.client.model.Individual;
import com.liferay.osb.faro.web.internal.constants.FaroConstants;
import com.liferay.osb.faro.web.internal.model.display.main.FaroEntityDisplay;
import com.liferay.osb.faro.web.internal.util.JSONUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Shinn Lok
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class IndividualDisplay implements FaroEntityDisplay {

	public IndividualDisplay() {
	}

	public IndividualDisplay(Individual individual) {
		Map<String, Object> embeddedResources =
			individual.getEmbeddedResources();

		if (MapUtil.isNotEmpty(embeddedResources)) {
			_accountNames = JSONUtil.convertValue(
				embeddedResources.get("account-names"),
				new TypeReference<List<String>>() {
				});
		}

		_individual = individual;

		_activitiesCount = individual.getActivitiesCount();
		_dataSourceIndividualPKs = individual.getDataSourceIndividualPKs();
		_dateCreated = individual.getDateCreated();
		_id = individual.getId();
		_lastActivityDate = individual.getLastActivityDate();

		StringBundler sb = new StringBundler(3);

		sb.append(GetterUtil.get(getValue("givenName"), StringPool.BLANK));

		if (sb.index() > 0) {
			sb.append(StringPool.SPACE);
		}

		sb.append(GetterUtil.get(getValue("familyName"), StringPool.BLANK));

		_name = sb.toString();

		_type = FaroConstants.TYPE_INDIVIDUAL;

		addProperties(_propertyNames);
	}

	@Override
	public void addProperties(List<String> propertyNames) {
		for (String propertyName : propertyNames) {
			_propertiesMap.put(propertyName, getValue(propertyName));
		}
	}

	@Override
	public String getId() {
		return _id;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public Map<String, Object> getProperties() {
		return _propertiesMap;
	}

	@Override
	public int getType() {
		return _type;
	}

	protected Object getValue(String key) {
		Map<String, List<Field>> fieldsMap = _individual.getDemographics();

		List<Field> properties = fieldsMap.get(key);

		if (ListUtil.isNotEmpty(properties)) {
			Field field = properties.get(0);

			return field.getValue();
		}

		return null;
	}

	private static final List<String> _propertyNames = Arrays.asList(
		"email", "familyName", "givenName", "image", "jobTitle", "worksFor");

	private List<String> _accountNames;
	private long _activitiesCount;
	private List<Individual.DataSourceIndividualPK> _dataSourceIndividualPKs;
	private Date _dateCreated;
	private String _id;

	@JsonIgnore
	private Individual _individual;

	private Date _lastActivityDate;
	private String _name;

	@JsonProperty("properties")
	private Map<String, Object> _propertiesMap = new HashMap<>();

	private int _type;

}