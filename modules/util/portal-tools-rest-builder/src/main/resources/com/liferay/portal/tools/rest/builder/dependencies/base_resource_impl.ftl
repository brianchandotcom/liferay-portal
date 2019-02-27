package ${configYAML.apiPackagePath}.internal.resource.${versionDirName};

<#compress>
	<#list openAPIYAML.components.schemas?keys as schemaName>
		import ${configYAML.apiPackagePath}.dto.${versionDirName}.${schemaName};
	</#list>
</#compress>

import ${configYAML.apiPackagePath}.resource.${versionDirName}.${schemaName}Resource;

import com.liferay.oauth2.provider.scope.RequiresScope;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.LocalDateTimeUtil;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.net.URI;

import java.util.Collections;
import java.util.List;

import javax.annotation.Generated;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * @author ${configYAML.author}
 * @generated
 */
@Generated("")
@Path("/${openAPIYAML.info.version}")
public abstract class Base${schemaName}ResourceImpl implements ${schemaName}Resource {

	<#list freeMarkerTool.getResourceJavaMethodSignatures(configYAML, openAPIYAML, schemaName, false) as javaMethodSignature>
		@Override
		${freeMarkerTool.getResourceMethodAnnotations(javaMethodSignature)}
		public ${javaMethodSignature.returnType} ${javaMethodSignature.methodName}(${freeMarkerTool.getResourceParameters(javaMethodSignature.javaParameters, true)}) throws Exception {
			<#if stringUtil.equals(javaMethodSignature.returnType, "boolean")>
				return false;
			<#elseif stringUtil.equals(javaMethodSignature.returnType, "String")>
				return StringPool.BLANK;
			<#elseif javaMethodSignature.returnType?contains("Page<")>
				return Page.of(Collections.emptyList());
			<#elseif freeMarkerTool.hasHTTPMethod(javaMethodSignature, "patch") && !javaMethodSignature.operation.requestBody.content?keys?seq_contains("multipart/form-data")>
				<#assign firstJavaParameter = javaMethodSignature.javaParameters[0] />

				${schemaName} existing${schemaName} = get${schemaName}(${firstJavaParameter.parameterName});

				<#list freeMarkerTool.getDTOJavaParameters(configYAML, openAPIYAML, schemaName, false) as javaParameter>
					<#if !freeMarkerTool.isSchemaParameter(javaParameter, openAPIYAML)>
						if (Validator.isNotNull(${schemaVarName}.get${javaParameter.parameterName?cap_first}())) {
							existing${schemaName}.set${javaParameter.parameterName?cap_first}(${schemaVarName}.get${javaParameter.parameterName?cap_first}());
						}
					</#if>
				</#list>

				return put${schemaName}(${firstJavaParameter.parameterName}, existing${schemaName});
			<#else>
				return new ${javaMethodSignature.returnType}();
			</#if>
		}
	</#list>

	public void setContextCompany(Company contextCompany) {
		this.contextCompany = contextCompany;
	}

	protected String getJAXRSLink(String methodName, Object... values) {
		String baseURIString = String.valueOf(
			contextUriInfo.getBaseUri());

		if (baseURIString.endsWith(StringPool.FORWARD_SLASH)) {
			baseURIString = baseURIString.substring(
				0, baseURIString.length() - 1);
		}

		URI resourceURI = UriBuilder.fromResource(
			Base${schemaName}ResourceImpl.class
		).build();

		URI methodURI = UriBuilder.fromMethod(
			Base${schemaName}ResourceImpl.class, methodName
		).build(
			values
		);

		return baseURIString + resourceURI.toString() + methodURI.toString();
	}

	protected <T, R> List<R> transform(List<T> list, UnsafeFunction<T, R, Exception> unsafeFunction) {
		return TransformUtil.transform(list, unsafeFunction);
	}

	@Context
	protected AcceptLanguage contextAcceptLanguage;

	@Context
	protected Company contextCompany;

	@Context
	protected UriInfo contextUriInfo;

}