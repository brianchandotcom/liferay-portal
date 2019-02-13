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

package com.liferay.portal.workflow.metrics.internal.resource.v1_0;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.TransformUtil;
import com.liferay.portal.workflow.metrics.dto.v1_0.Process;
import com.liferay.portal.workflow.metrics.resource.v1_0.ProcessResource;

import java.util.Collections;
import java.util.List;

import javax.annotation.Generated;
import javax.ws.rs.core.Context;

/**
 * @author Rafael Praxedes
 * @generated
 */
@Generated("")
public abstract class BaseProcessResourceImpl implements ProcessResource {

	public static final String ODATA_ENTITY_MODEL_NAME =
		"com_liferay_portal_workflow_metrics_dto_v1_0_ProcessEntityModel";

	@Override
	public Page<Process> getProcessPage(
			Long companyId, String title, Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	protected <T, R> List<R> transform(
		List<T> list, UnsafeFunction<T, R, Exception> transformFunction) {

		return TransformUtil.transform(list, transformFunction);
	}

	@Context
	protected AcceptLanguage acceptLanguage;

	@Context
	protected Company company;

}