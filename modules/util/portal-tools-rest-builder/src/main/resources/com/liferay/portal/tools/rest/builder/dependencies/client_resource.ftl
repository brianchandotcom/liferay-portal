package ${configYAML.apiPackagePath}.client.resource.${escapedVersion};

import ${configYAML.apiPackagePath}.client.dto.${escapedVersion}.${schemaName};
import ${configYAML.apiPackagePath}.client.http.HttpInvoker;
import ${configYAML.apiPackagePath}.client.pagination.Page;
import ${configYAML.apiPackagePath}.client.pagination.Pagination;
import ${configYAML.apiPackagePath}.client.serdes.${escapedVersion}.${schemaName}SerDes;

import java.io.File;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Generated;

/**
 * @author ${configYAML.author}
 * @generated
 */
@Generated("")
public class ${schemaName}Resource {
	<#list freeMarkerTool.getResourceTestCaseJavaMethodSignatures(configYAML, openAPIYAML, schemaName) as javaMethodSignature>
		<#assign
			arguments = freeMarkerTool.getResourceTestCaseArguments(javaMethodSignature.javaMethodParameters)
			parameters = freeMarkerTool.getResourceTestCaseParameters(javaMethodSignature.javaMethodParameters, javaMethodSignature.operation, false)
		/>

		public ${javaMethodSignature.returnType?replace(".dto.", ".client.dto.")?replace("com.liferay.portal.vulcan.pagination.", "")} ${javaMethodSignature.methodName}(
				${parameters?replace(".dto.", ".client.dto.")?replace("com.liferay.portal.kernel.search.filter.Filter filter", "String filterString")?replace("com.liferay.portal.vulcan.multipart.MultipartBody multipartBody", "${schemaName} ${schemaVarName}, Map<String, File> files")?replace("com.liferay.portal.vulcan.pagination", "${configYAML.apiPackagePath}.client.pagination")?replace("com.liferay.portal.kernel.search.Sort[] sorts", "String sortString")})
			throws Exception {

			HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

			<#if freeMarkerTool.hasHTTPMethod(javaMethodSignature, "patch", "post", "put") && arguments?ends_with("${schemaVarName}")>
				httpInvoker.body(${schemaName}SerDes.toJSON(${schemaVarName}), "application/json");
			<#elseif freeMarkerTool.hasHTTPMethod(javaMethodSignature, "patch", "post", "put") && arguments?ends_with("multipartBody")>
				httpInvoker.part("${schemaVarName}", ${schemaName}SerDes.toJSON(${schemaVarName}));

				for (Map.Entry<String, File> entry : files.entrySet()) {
					httpInvoker.part(entry.getKey(), entry.getValue());
				}
			</#if>

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.${freeMarkerTool.getHTTPMethod(javaMethodSignature.operation)?upper_case});

			<#if parameters?contains("Filter filter")>
				httpInvoker.parameter("filter", filterString);
			</#if>

			<#if parameters?contains("Pagination pagination")>
				httpInvoker.parameter("page", String.valueOf(pagination.getPage()));
				httpInvoker.parameter("pageSize", String.valueOf(pagination.getPageSize()));
			</#if>

			<#if parameters?contains("Sort[] sorts")>
				httpInvoker.parameter("sort", sortString);
			</#if>

			httpInvoker.path("http://localhost:8080/o${configYAML.application.baseURI}/${openAPIYAML.info.version}${javaMethodSignature.path}"

			<#list javaMethodSignature.pathJavaMethodParameters as javaMethodParameter>
				, ${javaMethodParameter.parameterName}
			</#list>

			);

			httpInvoker.userNameAndPassword("test@liferay.com:test");

			<#if javaMethodSignature.returnType?contains("Page<")>
				HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

				return Page.of(httpResponse.getContent(), ${schemaName}SerDes::toDTO);
			<#elseif javaMethodSignature.returnType?ends_with("String")>
				HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

				return httpResponse.getContent();
			<#elseif !stringUtil.equals(javaMethodSignature.returnType, "void")>
				HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

				try {
					return ${javaMethodSignature.returnType?replace(".dto.", ".client.serdes.")}SerDes.toDTO(httpResponse.getContent());
				}
				catch (Exception e) {
					_logger.log(Level.WARNING, "Unable to process HTTP response: " + httpResponse.getContent(), e);

					throw e;
				}
			<#else>
				httpInvoker.invoke();
			</#if>
		}
	</#list>

	private static final Logger _logger = Logger.getLogger(${schemaName}Resource.class.getName());

}