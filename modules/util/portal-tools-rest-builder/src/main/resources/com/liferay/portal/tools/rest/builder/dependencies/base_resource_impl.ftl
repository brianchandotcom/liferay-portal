package ${configYAML.apiPackagePath}.internal.resource.${escapedVersion};

<#list openAPIYAML.components.schemas?keys as schemaName>
	import ${configYAML.apiPackagePath}.dto.${escapedVersion}.${schemaName};
</#list>

import ${configYAML.apiPackagePath}.resource.${escapedVersion}.${schemaName}Resource;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.LocalDateTimeUtil;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.Dictionary;
import java.util.List;

import javax.annotation.Generated;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * @author ${configYAML.author}
 * @generated
 */
@Generated("")
public abstract class Base${schemaName}ResourceImpl implements AopService, ${schemaName}Resource {

	@Override
	public Dictionary<String, Object> getProperties() {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("api.version", "${openAPIYAML.info.version}");
		properties.put("osgi.jaxrs.application.select", "(osgi.jaxrs.name=${configYAML.application.name})");
		properties.put("osgi.jaxrs.resource", true);

		return properties;
	}

	<#list freeMarkerTool.getResourceJavaMethodSignatures(configYAML, openAPIYAML, schemaName) as javaMethodSignature>
		@Override
		public ${javaMethodSignature.returnType} ${javaMethodSignature.methodName}(${freeMarkerTool.getResourceParameters(javaMethodSignature.javaMethodParameters, javaMethodSignature.operation, false)}) throws Exception {
			<#if stringUtil.equals(javaMethodSignature.returnType, "boolean")>
				return false;
			<#elseif stringUtil.equals(javaMethodSignature.returnType, "java.lang.String")>
				return StringPool.BLANK;
			<#elseif stringUtil.equals(javaMethodSignature.returnType, "javax.ws.rs.core.Response")>
				Response.ResponseBuilder responseBuilder = Response.ok();

				return responseBuilder.build();
			<#elseif javaMethodSignature.returnType?contains("Page<")>
				return Page.of(Collections.emptyList());
			<#elseif freeMarkerTool.hasHTTPMethod(javaMethodSignature, "patch") && !javaMethodSignature.operation.requestBody.content?keys?seq_contains("multipart/form-data")>
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

	@Override
	public void setContextAcceptLanguage(AcceptLanguage contextAcceptLanguage) {
		this.contextAcceptLanguage = contextAcceptLanguage;
	}

	@Override
	public void setContextCompany(Company contextCompany) {
		this.contextCompany = contextCompany;
	}

	@Override
	public void setContextUriInfo(UriInfo contextUriInfo) {
		this.contextUriInfo = contextUriInfo;
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

	protected AcceptLanguage contextAcceptLanguage;

	protected Company contextCompany;

	protected UriInfo contextUriInfo;

}