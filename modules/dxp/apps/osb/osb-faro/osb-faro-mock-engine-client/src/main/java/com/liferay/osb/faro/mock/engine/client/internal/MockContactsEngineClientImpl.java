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

package com.liferay.osb.faro.mock.engine.client.internal;

import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.engine.client.constants.FieldMappingConstants;
import com.liferay.osb.faro.engine.client.constants.FilterConstants;
import com.liferay.osb.faro.engine.client.model.Field;
import com.liferay.osb.faro.engine.client.model.Individual;
import com.liferay.osb.faro.engine.client.model.Results;
import com.liferay.osb.faro.engine.client.util.FilterBuilder;
import com.liferay.osb.faro.engine.client.util.FilterUtil;
import com.liferay.osb.faro.engine.client.util.OrderByField;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;

/**
 * @author Matthew Kong
 */
@Component(
	property = "service.ranking:Integer=100",
	service = ContactsEngineClient.class
)
public class MockContactsEngineClientImpl
	extends BaseMockContactsEngineClientImpl {

	@Override
	public Results<Individual> getCoworkerIndividuals(
		FaroProject faroProject, String individualId, String query,
		List<String> fields, int cur, int delta,
		List<OrderByField> orderByFields) {

		// PULPO-304

		Individual individual = getIndividual(faroProject, individualId, null);

		Map<String, List<Field>> demographics = individual.getDemographics();

		List<Field> worksFor = demographics.get("worksFor");

		if (ListUtil.isNull(worksFor)) {
			return new Results<>();
		}

		Field field = worksFor.get(0);

		Results<Individual> results = getIndividuals(
			faroProject, field.getName(), field.getValue(), query, fields, cur,
			delta, orderByFields);

		List<Individual> individuals = results.getItems();

		Stream<Individual> stream = individuals.stream();

		stream = stream.filter(
			curIndividual -> {
				String id = curIndividual.getId();

				return !id.equals(individual.getId());
			});

		individuals = stream.collect(Collectors.toList());

		return new Results<>(individuals, individuals.size());
	}

	@Override
	public List<String> getFieldNames(
		FaroProject faroProject, String label, String ownerType,
		Object values) {

		List<String> fieldNames = super.getFieldNames(
			faroProject, label, ownerType, values);

		String fieldName = _liferayFieldNames.get(label);

		if ((fieldName != null) && !fieldNames.contains(fieldName)) {
			fieldNames.add(0, fieldName);
		}

		return fieldNames;
	}

	@Override
	public List<List<String>> getFieldNamesList(
		FaroProject faroProject, List<String> labels, String ownerType,
		List<Object> valuesList) {

		List<List<String>> fieldNamesList = super.getFieldNamesList(
			faroProject, labels, ownerType, valuesList);

		for (int i = 0; i < fieldNamesList.size(); i++) {
			List<String> fieldNames = fieldNamesList.get(i);

			String fieldName = _liferayFieldNames.get(labels.get(i));

			if ((fieldName != null) && !fieldNames.contains(fieldName)) {
				fieldNames.add(0, fieldName);
			}
		}

		return fieldNamesList;
	}

	@Override
	public long getIndividualsCount(
		FaroProject faroProject, boolean includeAnonymousUsers,
		Date startDate) {

		return contactsEngineClient.getIndividualsCount(
			faroProject, includeAnonymousUsers, startDate);
	}

	@Override
	public Results<Individual> getSimilarIndividuals(
		FaroProject faroProject, String individualId, String query,
		List<String> fields, int cur, int delta,
		List<OrderByField> orderByFields) {

		// PULPO-305

		Individual individual = getIndividual(faroProject, individualId, null);

		Map<String, List<Field>> demographics = individual.getDemographics();

		List<Field> jobTitle = demographics.get("jobTitle");

		if (ListUtil.isNull(jobTitle)) {
			return new Results<>();
		}

		Field field = jobTitle.get(0);

		Results<Individual> results = getIndividuals(
			faroProject, field.getName(), field.getValue(), query, fields, cur,
			delta, orderByFields);

		List<Individual> individuals = results.getItems();

		Stream<Individual> stream = individuals.stream();

		stream = stream.filter(
			curIndividual -> {
				String id = curIndividual.getId();

				return !id.equals(individual.getId());
			});

		individuals = stream.collect(Collectors.toList());

		return new Results<>(individuals, individuals.size());
	}

	protected Results<Individual> getIndividuals(
		FaroProject faroProject, String fieldName, String fieldValue,
		String query, List<String> fields, int cur, int delta,
		List<OrderByField> orderByFields) {

		FilterBuilder filterBuilder = new FilterBuilder();

		filterBuilder.addFilter(
			FilterUtil.getFieldName(
				fieldName, FilterConstants.FIELD_NAME_CONTEXT_INDIVIDUAL),
			FilterConstants.COMPARISON_OPERATOR_EQUALS, fieldValue);
		filterBuilder.addSearchFilter(
			query, fields, FilterConstants.FIELD_NAME_CONTEXT_INDIVIDUAL);

		return getIndividuals(
			faroProject, filterBuilder, false, cur, delta, orderByFields);
	}

	private static final Map<String, String> _liferayFieldNames =
		FieldMappingConstants.getLiferayFieldNames();

}