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

package com.liferay.portal.workflow.metrics.rest.internal.graphql.servlet.v1_0;

import com.liferay.portal.vulcan.graphql.servlet.ServletData;
import com.liferay.portal.workflow.metrics.rest.internal.graphql.mutation.v1_0.Mutation;
import com.liferay.portal.workflow.metrics.rest.internal.graphql.query.v1_0.Query;

import javax.annotation.Generated;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Component(immediate = true, service = ServletData.class)
@Generated("")
public class ServletDataImpl implements ServletData {

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	@Override
	public String getPath() {
		return "/portal-workflow-metrics-graphql/v1_0";
	}

	@Override
	public Query getQuery() {
		return new Query();
	}

}