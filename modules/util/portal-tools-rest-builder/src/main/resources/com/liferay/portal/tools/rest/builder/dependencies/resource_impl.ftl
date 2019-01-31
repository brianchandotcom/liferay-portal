package ${configYAML.apiPackagePath}.internal.resource;

<#compress>
	<#list openAPIYAML.components.schemas?keys as schemaName>
		import ${configYAML.apiPackagePath}.dto.${schemaName};
	</#list>
</#compress>

import ${configYAML.apiPackagePath}.resource.${schemaName}Resource;

import com.liferay.portal.vulcan.context.Pagination;
import com.liferay.portal.vulcan.dto.Page;

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
	scope = ServiceScope.PROTOTYPE, service = ${schemaName}Resource.class)
@Generated("")
public class ${schemaName}ResourceImpl implements ${schemaName}Resource {

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
			<#assign
				pageParameter = false
				perPageParameter = false
			/>

			<#list operation.parameters as parameter>
				<#if stringUtil.equals(parameter.name, "page")>
					<#assign pageParameter = true />
				<#elseif stringUtil.equals(parameter.name, "per_page")>
					<#assign perPageParameter = true />
				</#if>
			</#list>

			<#assign methodParameters>
				<@compress single_line=true>
					<#list operation.parameters as parameter>
						<#if pageParameter && perPageParameter && (stringUtil.equals(parameter.name, "page") || stringUtil.equals(parameter.name, "per_page"))>
							<#continue>
						</#if>

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

			<#if pageParameter && perPageParameter>
				<#assign methodParameters = "${methodParameters} Pagination pagination," />
			</#if>

			<#if methodParameters?has_content>
				<#assign methodParameters = "${methodParameters[0..(methodParameters?length - 2)]}" />
			</#if>
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
										<#assign name = "${reference[(reference?last_index_of('/') + 1)..(reference?length - 1)]}" />

										<#assign
											methodReturnType = "Page"
											methodReturnValue = "Page<${name}>"
										/>
									</#if>
								</#if>
							</#if>

							<#if schema.reference??>
								<#assign reference = "${schema.reference}" />

								<#if reference?contains("/schemas/")>
									<#assign name = "${reference[(reference?last_index_of('/') + 1)..(reference?length - 1)]}" />

									<#assign
										methodReturnType = "${name}"
										methodReturnValue = "${name}"
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

		<#if stringUtil.equals(methodReturnType, "Page") && !stringUtil.endsWith(name, "Page")>
			<#assign name = "${name}Page" />
		<#elseif stringUtil.equals(methodReturnType, schemaName) && stringUtil.endsWith(name, methodReturnType + "s")>
			<#assign name = "${name[0..(name?length - 2)]}" />
		</#if>

		<#if stringUtil.equals(methodReturnType, schemaName) || stringUtil.equals(methodReturnType, "Page")>
			<#if stringUtil.equals(methodReturnType, "Response")>
				<#assign template>
					@Override
					public ${methodReturnValue} ${name}(${methodParameters}) throws Exception {
						Response.ResponseBuilder responseBuilder = Response.ok();

						return responseBuilder.build();
					}
				</#assign>
			<#elseif methodReturnValue?contains("Page<")>
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
${line?replace("^\t\t\t\t", "", "r")}
				</#if>
			</#list>
		</#if>
	</#list>

}