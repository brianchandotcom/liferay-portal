package ${configYAML.apiPackagePath}.internal.resource;

<#compress>
	<#list openAPIYAML.components.schemas?keys as schemaName>
		import ${configYAML.apiPackagePath}.dto.${schemaName};
	</#list>
</#compress>

import ${configYAML.apiPackagePath}.resource.${schemaName}Resource;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.vulcan.context.AcceptLanguage;
import com.liferay.portal.vulcan.context.Pagination;
import com.liferay.portal.vulcan.dto.Page;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import javax.annotation.Generated;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 * @author ${configYAML.author}
 * @generated
 */
@Generated("")
public abstract class Base${schemaName}ResourceImpl implements ${schemaName}Resource {

	<#if openAPIYAML.pathItems??>
		<#list openAPIYAML.pathItems?keys as path>
			<#assign pathItem = openAPIYAML.pathItems[path] />

			<#list javaTool.getOperations(pathItem) as operation>
				<#assign javaSignature = javaTool.getJavaSignature(configYAML, openAPIYAML, operation, path, pathItem, schemaName) />

				<#if !stringUtil.equals(javaSignature.returnType, schemaName) && !stringUtil.equals(javaSignature.returnType, "Page<${schemaName}>") && !stringUtil.endsWith(javaSignature.methodName, schemaName)>
					<#continue>
				</#if>

				@Override
				<@compress single_line=true>
					public ${javaSignature.returnType} ${javaSignature.methodName}(
						<#list javaSignature.javaParameters as javaParameter>
							${javaParameter.parameterType} ${javaParameter.parameterName}

							<#if javaParameter_has_next>
								,
							</#if>
						</#list>
					) throws Exception {
				</@compress>

				<#assign methodBody>
					<#if stringUtil.equals(javaSignature.returnType, "Response")>
						Response.ResponseBuilder responseBuilder = Response.ok();

						return responseBuilder.build();
					<#elseif stringUtil.equals(javaSignature.returnType, "Page<" + schemaName + ">")>
						return Page.of(Collections.emptyList());
					<#else>
						return new ${javaSignature.returnType}();
					</#if>
				</#assign>

				<#list methodBody?split("\n") as line>
					${line?replace("^\t\t\t\t", "", "r")}<#lt>
				</#list>

				}
			</#list>
		</#list>
	</#if>

	protected <T, R> List<R> transform(List<T> list, Function<T, R> transformFunction) {
		return TransformUtil.transform(list, transformFunction);
	}

	@Context
	protected AcceptLanguage acceptLanguage;

	@Context
	protected Company company;

}