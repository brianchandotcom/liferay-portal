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

package com.liferay.headless.commerce.machine.learning.internal.graphql.servlet.v1_0;

import com.liferay.headless.commerce.machine.learning.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.headless.commerce.machine.learning.internal.graphql.query.v1_0.Query;
import com.liferay.headless.commerce.machine.learning.internal.resource.v1_0.AccountCategoryForecastResourceImpl;
import com.liferay.headless.commerce.machine.learning.internal.resource.v1_0.AccountForecastResourceImpl;
import com.liferay.headless.commerce.machine.learning.internal.resource.v1_0.SkuForecastResourceImpl;
import com.liferay.headless.commerce.machine.learning.resource.v1_0.AccountCategoryForecastResource;
import com.liferay.headless.commerce.machine.learning.resource.v1_0.AccountForecastResource;
import com.liferay.headless.commerce.machine.learning.resource.v1_0.SkuForecastResource;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.vulcan.graphql.servlet.ServletData;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentServiceObjects;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;

/**
 * @author Riccardo Ferrari
 * @generated
 */
@Component(service = ServletData.class)
@Generated("")
public class ServletDataImpl implements ServletData {

	@Activate
	public void activate(BundleContext bundleContext) {
		Query.setAccountCategoryForecastResourceComponentServiceObjects(
			_accountCategoryForecastResourceComponentServiceObjects);
		Query.setAccountForecastResourceComponentServiceObjects(
			_accountForecastResourceComponentServiceObjects);
		Query.setSkuForecastResourceComponentServiceObjects(
			_skuForecastResourceComponentServiceObjects);
	}

	public String getApplicationName() {
		return "Liferay.Headless.Commerce.Machine.Learning";
	}

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	@Override
	public String getPath() {
		return "/headless-commerce-machine-learning-graphql/v1_0";
	}

	@Override
	public Query getQuery() {
		return new Query();
	}

	public ObjectValuePair<Class<?>, String> getResourceMethodPair(
		String methodName, boolean mutation) {

		if (mutation) {
			return _resourceMethodPairs.get("mutation#" + methodName);
		}

		return _resourceMethodPairs.get("query#" + methodName);
	}

	private static final Map<String, ObjectValuePair<Class<?>, String>>
		_resourceMethodPairs = new HashMap<>();

	static {
		_resourceMethodPairs.put(
			"query#accountCategoryForecastsByMonthlyRevenue",
			new ObjectValuePair<>(
				AccountCategoryForecastResourceImpl.class,
				"getAccountCategoryForecastsByMonthlyRevenuePage"));
		_resourceMethodPairs.put(
			"query#accountForecastsByMonthlyRevenue",
			new ObjectValuePair<>(
				AccountForecastResourceImpl.class,
				"getAccountForecastsByMonthlyRevenuePage"));
		_resourceMethodPairs.put(
			"query#skuForecastsByMonthlyRevenue",
			new ObjectValuePair<>(
				SkuForecastResourceImpl.class,
				"getSkuForecastsByMonthlyRevenuePage"));
	}

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<AccountCategoryForecastResource>
		_accountCategoryForecastResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<AccountForecastResource>
		_accountForecastResourceComponentServiceObjects;

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private ComponentServiceObjects<SkuForecastResource>
		_skuForecastResourceComponentServiceObjects;

}