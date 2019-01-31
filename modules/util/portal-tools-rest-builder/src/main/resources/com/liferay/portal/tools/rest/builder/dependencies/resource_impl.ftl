package ${configYAML.apiPackagePath}.internal.resource;

<#compress>
	<#list openAPIYAML.components.schemas?keys as schemaName>
		import ${configYAML.apiPackagePath}.dto.${schemaName};
	</#list>
</#compress>

import ${configYAML.apiPackagePath}.resource.${schemaName}Resource;

import com.liferay.portal.vulcan.context.AcceptLanguage;
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
		<#assign
			pathItem = openAPIYAML.pathItems[path]

			operationNames = ""
		/>

		<#if pathItem.delete??>
			<#assign operationNames = "${operationNames},delete" />
		</#if>

		<#if pathItem.get??>
			<#assign operationNames = "${operationNames},get" />
		</#if>

		<#if pathItem.head??>
			<#assign operationNames = "${operationNames},head" />
		</#if>

		<#if pathItem.options??>
			<#assign operationNames = "${operationNames},options" />
		</#if>

		<#if pathItem.post??>
			<#assign operationNames = "${operationNames},post" />
		</#if>

		<#if pathItem.put??>
			<#assign operationNames = "${operationNames},put" />
		</#if>

		<#list stringUtil.split(operationNames, ",") as operationName>
			<#if !operationName?trim?has_content>
				<#continue>
			</#if>

			<#if stringUtil.equals(operationName, "delete")>
				<#assign
					annotationHTTPMethod = "@DELETE"

					operation = pathItem.delete
				/>
			<#elseif stringUtil.equals(operationName, "get")>
				<#assign
					annotationHTTPMethod = "@GET"

					operation = pathItem.get
				/>
			<#elseif stringUtil.equals(operationName, "head")>
				<#assign
					annotationHTTPMethod = "@HEAD"

					operation = pathItem.head
				/>
			<#elseif stringUtil.equals(operationName, "options")>
				<#assign
					annotationHTTPMethod = "@OPTIONS"

					operation = pathItem.options
				/>
			<#elseif stringUtil.equals(operationName, "post")>
				<#assign
					annotationHTTPMethod = "@POST"

					operation = pathItem.post
				/>
			<#elseif stringUtil.equals(operationName, "put")>
				<#assign
					annotationHTTPMethod = "@PUT"

					operation = pathItem.put
				/>
			</#if>

			<#assign methodParameters = "" />

			<#if operation.parameters??>
				<#assign
					acceptLanguageParameter = false
					pageParameter = false
					perPageParameter = false
				/>

				<#list operation.parameters as parameter>
					<#if stringUtil.equals(parameter.name, "Accept-Language")>
						<#assign acceptLanguageParameter = true />
					<#elseif stringUtil.equals(parameter.name, "page")>
						<#assign pageParameter = true />
					<#elseif stringUtil.equals(parameter.name, "per_page")>
						<#assign perPageParameter = true />
					</#if>
				</#list>

				<#assign methodParameters>
					<@compress single_line=true>
						<#list operation.parameters as parameter>
							<#if acceptLanguageParameter && stringUtil.equals(parameter.name, "Accept-Language")>
								<#continue>
							</#if>

							<#if pageParameter && perPageParameter && (stringUtil.equals(parameter.name, "page") || stringUtil.equals(parameter.name, "per_page"))>
								<#continue>
							</#if>

							<#if parameter.schema.type??>
								<#if parameter.schema.format?? && stringUtil.equals(parameter.schema.format, "int64") && stringUtil.equals(parameter.schema.type, "integer")>
									Long
								<#else>
									${parameter.schema.type?cap_first}
								</#if>
							<#elseif parameter.schema.reference??>
								<#assign reference = "${parameter.schema.reference}" />

								${reference[(reference?last_index_of('/') + 1)..(reference?length - 1)]}
							</#if>

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

				<#if acceptLanguageParameter>
					<#assign methodParameters = "${methodParameters} AcceptLanguage acceptLanguage," />
				</#if>

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

			<#if stringUtil.equals(methodReturnValue, schemaName) || stringUtil.equals(methodReturnValue, "Page<${schemaName}>")>
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
${line?replace("^\t\t\t\t\t", "", "r")}
					</#if>
				</#list>
			</#if>
		</#list>
	</#list>

}