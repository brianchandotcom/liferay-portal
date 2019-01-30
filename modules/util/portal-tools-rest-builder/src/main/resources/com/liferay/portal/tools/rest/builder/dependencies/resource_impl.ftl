package ${configYAML.apiPackagePath}.internal.resource;

<#compress>
	<#list openAPIYAML.components.schemas?keys as schemaName>
		import ${configYAML.apiPackagePath}.dto.${schemaName};
		import ${configYAML.apiPackagePath}.dto.${schemaName}Collection;
	</#list>
</#compress>

import ${configYAML.apiPackagePath}.resource.${resourceName}Resource;

import com.liferay.portal.vulcan.context.Pagination;

import java.util.Collections;

import javax.annotation.Generated;

import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

/**
 * @author ${configYAML.author}
 * @generated
 */
@Component(
	property = {
		JaxrsWhiteboardConstants.JAX_RS_APPLICATION_SELECT + "=(osgi.jaxrs.name=${configYAML.application.name}.rest)",
		JaxrsWhiteboardConstants.JAX_RS_RESOURCE + "=true", "api.version=${openAPIYAML.info.version}"
	},
	scope = ServiceScope.PROTOTYPE, service = ${resourceName}Resource.class)
@Generated("")
public class ${resourceName}ResourceImpl implements ${resourceName}Resource {

	<#list openAPIYAML.pathItems?keys as path>
		<#assign pathItem = openAPIYAML.pathItems[path] />

		<#if pathItem.delete??>
			<#assign
				annotationHTTPMethod = "@DELETE"

				operation = pathItem.delete
				operationName = "delete"
			/>
		<#elseif pathItem.get??>
			<#assign
				annotationHTTPMethod = "@GET"

				operation = pathItem.get
				operationName = "get"
			/>
		<#elseif pathItem.head??>
			<#assign
				annotationHTTPMethod = "@HEAD"

				operation = pathItem.head
				operationName = "head"
			/>
		<#elseif pathItem.options??>
			<#assign
				annotationHTTPMethod = "@OPTIONS"

				operation = pathItem.options
				operationName = "options"
			/>
		<#elseif pathItem.post??>
			<#assign
				annotationHTTPMethod = "@POST"

				operation = pathItem.post
				operationName = "post"
			/>
		<#elseif pathItem.put??>
			<#assign
				annotationHTTPMethod = "@PUT"

				operation = pathItem.put
				operationName = "put"
			/>
		</#if>

		<#assign methodParameters = "" />

		<#if operation.parameters??>
			<#assign methodParameters>
				<@compress single_line=true>
					<#list operation.parameters as parameter>
						${parameter.schema.type?cap_first}

						<#assign parameterName = "" />

						<#list parameter.name?split("[^A-Za-z0-9]", "r") as s>
							<#if s?has_content>
								<#if parameterName?has_content>
									<#assign parameterName = "${parameterName}${s?cap_first}" />
								<#else>
									<#assign parameterName = "${s}" />
								</#if>
							</#if>
						</#list>

						${parameterName},
					</#list>
				</@compress>
			</#assign>

			<#assign methodParameters = "${methodParameters[0..(methodParameters?length - 2)]}" />
		</#if>

		<#assign
			methodReturnType = "Response"
			methodReturnValue = "Response"
		/>

		<#if operation.responses??>
			<#list operation.responses?values as response>
				<#if response.content??>
					<#list response.content?values as content>
						<#if content.schema??>
							<#assign schema = content.schema />

							<#if schema.type??>
								<#if stringUtil.equals(schema.type, "array")>
									<#assign reference = "${schema.items.reference}" />

									<#if reference?contains("/schemas/")>
										<#assign schemaName = "${reference[(reference?last_index_of('/') + 1)..(reference?length - 1)]}" />

										<#assign
											methodReturnType = "${schemaName}Collection"
											methodReturnValue = "${schemaName}Collection<${schemaName}>"
										/>
									</#if>
								</#if>
							</#if>

							<#if schema.reference??>
								<#assign reference = "${schema.reference}" />

								<#if reference?contains("/schemas/")>
									<#assign schemaName = "${reference[(reference?last_index_of('/') + 1)..(reference?length - 1)]}" />

									<#assign
										methodReturnType = "${schemaName}"
										methodReturnValue = "${schemaName}"
									/>
								</#if>
							</#if>
						</#if>
					</#list>
				</#if>
			</#list>
		</#if>

		<#assign name>
			<@compress single_line=true>
				${operationName?lower_case}

				<#list path?replace("\\{.*?\\}", "", "rs")?split("[^A-Za-z0-9]", "r") as s>
					<#if s?has_content>
						${s?cap_first}
					</#if>
				</#list>
			</@compress>
		</#assign>

		<#assign name = "${name?replace(' ', '')}" />

		<#if stringUtil.equals(methodReturnType, "Response")>
			<#assign template>
				@Override
				public ${methodReturnValue} ${name}(${methodParameters}) throws Exception {
					Response.ResponseBuilder responseBuilder = Response.ok();

					return responseBuilder.build();
				}
			</#assign>
		<#elseif methodReturnValue?contains("Collection<")>
			<#assign template>
				@Override
				public ${methodReturnValue} ${name}(${methodParameters}) throws Exception {
					return new ${methodReturnType}(Collections.emptyList(), 0);
				}
			</#assign>
		<#else>
			<#assign template>
				@Override
				public ${methodReturnValue} ${name}(${methodParameters}) throws Exception {
					return new ${methodReturnType}();
				}
			</#assign>
		</#if>

		<#list template?split("\n") as line>
			<#if line?trim?has_content>
${line?replace("^\t\t\t", "", "r")}
			</#if>
		</#list>
	</#list>

}