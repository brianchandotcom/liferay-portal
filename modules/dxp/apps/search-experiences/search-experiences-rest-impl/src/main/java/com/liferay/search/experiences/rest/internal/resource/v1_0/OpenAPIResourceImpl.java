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

package com.liferay.search.experiences.rest.internal.resource.v1_0;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.vulcan.resource.OpenAPIResource;
import com.liferay.search.experiences.rest.dto.v1_0.FieldMappingInfo;
import com.liferay.search.experiences.rest.dto.v1_0.KeywordQueryContributor;
import com.liferay.search.experiences.rest.dto.v1_0.ModelPrefilterContributor;
import com.liferay.search.experiences.rest.dto.v1_0.QueryPrefilterContributor;
import com.liferay.search.experiences.rest.dto.v1_0.SXPBlueprint;
import com.liferay.search.experiences.rest.dto.v1_0.SXPElement;
import com.liferay.search.experiences.rest.dto.v1_0.SXPParameterContributorDefinition;
import com.liferay.search.experiences.rest.dto.v1_0.SearchResponse;
import com.liferay.search.experiences.rest.dto.v1_0.SearchableAssetName;
import com.liferay.search.experiences.rest.dto.v1_0.SearchableAssetNameDisplay;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/openapi.properties",
	service = OpenAPIResourceImpl.class
)
@Generated("")
@OpenAPIDefinition(
	info = @Info(description = "A Java client JAR is available for use with the group ID 'com.liferay', artifact ID 'com.liferay.search.experiences.rest.client', and version '3.0.4'.", license = @License(name = "Apache 2.0", url = "http://www.apache.org/licenses/LICENSE-2.0.html"), title = "", version = "v1.0")
)
@Path("/v1.0")
public class OpenAPIResourceImpl {

	@GET
	@Path("/openapi.{type:json|yaml}")
	@Produces({MediaType.APPLICATION_JSON, "application/yaml"})
	public Response getOpenAPI(@PathParam("type") String type)
		throws Exception {

		try {
			Class<? extends OpenAPIResource> clazz =
				_openAPIResource.getClass();

			clazz.getMethod(
				"getOpenAPI", long.class, Map.class, String.class,
				UriInfo.class);
		}
		catch (NoSuchMethodException noSuchMethodException) {
			return _openAPIResource.getOpenAPI(_resourceClasses.keySet(), type);
		}

		return _openAPIResource.getOpenAPI(
			_company.getCompanyId(), _resourceClasses, type, _uriInfo);
	}

	@Reference
	private OpenAPIResource _openAPIResource;

	@Context
	private UriInfo _uriInfo;

	private final Map<Class<?>, Class<?>> _resourceClasses =
		new HashMap<Class<?>, Class<?>>() {
			{
				put(FieldMappingInfoResourceImpl.class, FieldMappingInfo.class);
				put(
					KeywordQueryContributorResourceImpl.class,
					KeywordQueryContributor.class);
				put(
					ModelPrefilterContributorResourceImpl.class,
					ModelPrefilterContributor.class);
				put(
					QueryPrefilterContributorResourceImpl.class,
					QueryPrefilterContributor.class);
				put(
					SearchableAssetNameDisplayResourceImpl.class,
					SearchableAssetNameDisplay.class);
				put(
					SearchableAssetNameResourceImpl.class,
					SearchableAssetName.class);
				put(SearchResponseResourceImpl.class, SearchResponse.class);
				put(SXPBlueprintResourceImpl.class, SXPBlueprint.class);
				put(SXPElementResourceImpl.class, SXPElement.class);
				put(
					SXPParameterContributorDefinitionResourceImpl.class,
					SXPParameterContributorDefinition.class);

				put(OpenAPIResourceImpl.class, null);
			}
		};

	@Context
	private Company _company;

}