package com.liferay.headless.batch.engine.internal.vulcan.openapi.contributor;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.vulcan.openapi.OpenAPIContext;
import com.liferay.portal.vulcan.openapi.contributor.OpenAPIContributor;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.osgi.service.component.annotations.Component;

import java.util.List;
import java.util.Objects;

@Component(service = OpenAPIContributor.class)
public class BatchEngineOpenApiContributor implements OpenAPIContributor {
	@Override
	public void contribute(OpenAPI openAPI, OpenAPIContext openAPIContext)
		throws Exception {

		if (openAPIContext == null) {
			return;
		}

		if (!FeatureFlagManagerUtil.isEnabled("LPD-35944")) {
			_lPD35944(openAPI);
		}
	}

	private void _lPD35944(OpenAPI openAPI) {
		if (!Objects.equals(
			openAPI.getInfo(
			).getTitle(),
			"Headless Batch Engine")) {

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
