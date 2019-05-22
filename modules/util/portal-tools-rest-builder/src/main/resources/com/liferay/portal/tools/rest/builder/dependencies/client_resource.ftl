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
				httpInvoker.multipart();

				httpInvoker.part("${schemaVarName}", ${schemaName}SerDes.toJSON(${schemaVarName}));

				for (Map.Entry<String, File> entry : files.entrySet()) {
					httpInvoker.part(entry.getKey(), entry.getValue());
				}
			</#if>

			httpInvoker.httpMethod(HttpInvoker.HttpMethod.${freeMarkerTool.getHTTPMethod(javaMethodSignature.operation)?upper_case});

			<#list javaMethodSignature.javaMethodParameters as parameter>
				<#if stringUtil.equals(parameter.parameterName, "filter")>
					if (filterString != null) {
						httpInvoker.parameter("filter", filterString);
					}
				<#elseif stringUtil.equals(parameter.parameterName, "pagination")>
					if (pagination != null) {
						httpInvoker.parameter("page", String.valueOf(pagination.getPage()));
						httpInvoker.parameter("pageSize", String.valueOf(pagination.getPageSize()));
					}
				<#elseif stringUtil.equals(parameter.parameterName, "sorts")>
					if (sortString != null) {
						httpInvoker.parameter("sort", sortString);
					}
				<#elseif freeMarkerTool.isQueryParameter(parameter, javaMethodSignature.operation)>
					if (${parameter.parameterName} != null) {
						<#if stringUtil.startsWith(parameter.parameterType, "[")>
							for (int i = 0; i < ${parameter.parameterName}.length; i++) {
								httpInvoker.parameter("${parameter.parameterName}", String.valueOf(${parameter.parameterName}[i]));
							}
						<#else>
							httpInvoker.parameter("${parameter.parameterName}", String.valueOf(${parameter.parameterName}));
						</#if>
					}
				</#if>
			</#list>

			httpInvoker.path("http://localhost:8080/o${configYAML.application.baseURI}/${openAPIYAML.info.version}${javaMethodSignature.path}"

			<#list javaMethodSignature.pathJavaMethodParameters as javaMethodParameter>
				, ${javaMethodParameter.parameterName}
			</#list>

			);

			httpInvoker.userNameAndPassword("test@liferay.com:test");

			HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

			String content = httpResponse.getContent();

			_logger.fine("HTTP response content: " + content);

			_logger.fine("HTTP response message: " + httpResponse.getMessage());
			_logger.fine("HTTP response status: " + httpResponse.getStatus());

			<#if javaMethodSignature.returnType?contains("Page<")>
				return Page.of(content, ${schemaName}SerDes::toDTO);
			<#elseif javaMethodSignature.returnType?ends_with("String")>
				return content;
			<#elseif !stringUtil.equals(javaMethodSignature.returnType, "void")>
				try {
					return ${javaMethodSignature.returnType?replace(".dto.", ".client.serdes.")}SerDes.toDTO(content);
				}
				catch (Exception e) {
					_logger.log(Level.WARNING, "Unable to process HTTP response: " + content, e);

					throw e;
				}
			</#if>
		}
	</#list>

	private static final Logger _logger = Logger.getLogger(${schemaName}Resource.class.getName());

}