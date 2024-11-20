/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.batch.engine.internal.vulcan.openapi.contributor;

import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.openapi.OpenAPIContext;
import com.liferay.portal.vulcan.openapi.contributor.OpenAPIContributor;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.parameters.Parameter;

import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * @author Mauricio Valdivia
 */
@Component(service = OpenAPIContributor.class)
public class BatchEngineOpenAPIContributor implements OpenAPIContributor {

	@Override
	public void contribute(OpenAPI openAPI, OpenAPIContext openAPIContext)
		throws Exception {

		if ((openAPIContext == null) ||
			FeatureFlagManagerUtil.isEnabled("LPD-29367") ||
			!StringUtil.endsWith(
				openAPIContext.getPath(), "/headless-batch-engine/")) {

			return;
		}

		Paths paths = openAPI.getPaths();

		if (paths == null) {
			return;
		}

		PathItem pathItem = paths.get("/v1.0/import-task/{className}");

		if (pathItem == null) {
			return;
		}

		Operation operation = pathItem.getPost();

		if (operation == null) {
			return;
		}

		List<Parameter> parameters = operation.getParameters();

		if (parameters == null) {
			return;
		}

		parameters.removeIf(
			param -> Objects.equals(param.getName(), "restrictedFieldNames"));
	}

}