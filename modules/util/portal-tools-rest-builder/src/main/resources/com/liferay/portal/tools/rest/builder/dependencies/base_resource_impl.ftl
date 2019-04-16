package ${configYAML.apiPackagePath}.internal.resource.${escapedVersion};

<#list openAPIYAML.components.schemas?keys as schemaName>
	import ${configYAML.apiPackagePath}.dto.${escapedVersion}.${schemaName};
</#list>

import ${configYAML.apiPackagePath}.resource.${escapedVersion}.${schemaName}Resource;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.LocalDateTimeUtil;
import com.liferay.portal.vulcan.util.TransformUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.Generated;

import javax.validation.constraints.NotNull;

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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * @author ${configYAML.author}
 * @generated
 */
@Generated("")
@Path("/${openAPIYAML.info.version}")
public abstract class Base${schemaName}ResourceImpl implements ${schemaName}Resource {

	<#list freeMarkerTool.getResourceJavaMethodSignatures(configYAML, openAPIYAML, schemaName) as javaMethodSignature>
		@Override
		${freeMarkerTool.getResourceMethodAnnotations(javaMethodSignature)}
		public ${javaMethodSignature.returnType} ${javaMethodSignature.methodName}(${freeMarkerTool.getResourceParameters(javaMethodSignature.javaMethodParameters, javaMethodSignature.operation, true)}) throws Exception {
			<#if stringUtil.equals(javaMethodSignature.returnType, "boolean")>
				return false;
			<#elseif stringUtil.equals(javaMethodSignature.returnType, "java.lang.String")>
				return StringPool.BLANK;
			<#elseif stringUtil.equals(javaMethodSignature.returnType, "javax.ws.rs.core.Response")>
				Response.ResponseBuilder responseBuilder = Response.ok();

				return responseBuilder.build();
			<#elseif javaMethodSignature.returnType?contains("Page<")>
				return Page.of(Collections.emptyList());
			<#elseif freeMarkerTool.hasHTTPMethod(javaMethodSignature, "patch") && !javaMethodSignature.operation.requestBody.content?keys?seq_contains("multipart/form-data") && !stringUtil.equals(javaMethodSignature.returnType, "void")>
				<#assign firstJavaMethodParameter = javaMethodSignature.javaMethodParameters[0] />

				${schemaName} existing${schemaName} = get${schemaName}(${firstJavaMethodParameter.parameterName});

				<#assign properties = freeMarkerTool.getDTOProperties(configYAML, openAPIYAML, schema) />

				<#list properties?keys as propertyName>
					<#if !freeMarkerTool.isDTOSchemaProperty(openAPIYAML, propertyName, schema) && !stringUtil.equals(propertyName, "id")>
						if (${schemaVarName}.get${propertyName?cap_first}() != null) {
							existing${schemaName}.set${propertyName?cap_first}(${schemaVarName}.get${propertyName?cap_first}());
						}
					</#if>
				</#list>

				preparePatch(${schemaVarName}, existing${schemaName});

				return put${schemaName}(${firstJavaMethodParameter.parameterName}, existing${schemaName});
			<#elseif !stringUtil.equals(javaMethodSignature.returnType, "void")>
				return new ${javaMethodSignature.returnType}();
			</#if>
		}
	</#list>

	public void setContextCompany(Company contextCompany) {
		this.contextCompany = contextCompany;
	}

	protected void preparePatch(${schemaName} ${schemaVarName}, ${schemaName} existing${schemaVarName?cap_first}) {
	}

	protected <T, R> List<R> transform(Collection<T> collection, UnsafeFunction<T, R, Exception> unsafeFunction) {
		return TransformUtil.transform(collection, unsafeFunction);
	}

	protected <T, R> R[] transform(T[] array, UnsafeFunction<T, R, Exception> unsafeFunction, Class<?> clazz) {
		return TransformUtil.transform(array, unsafeFunction, clazz);
	}

	protected <T, R> R[] transformToArray(Collection<T> collection, UnsafeFunction<T, R, Exception> unsafeFunction, Class<?> clazz) {
		return TransformUtil.transformToArray(collection, unsafeFunction, clazz);
	}

	protected <T, R> List<R> transformToList(T[] array, UnsafeFunction<T, R, Exception> unsafeFunction) {
		return TransformUtil.transformToList(array, unsafeFunction);
	}

	@Context
	protected AcceptLanguage contextAcceptLanguage;

	@Context
	protected Company contextCompany;

	@Context
	protected UriInfo contextUriInfo;

}