package ${configYAML.apiPackagePath}.internal.mutation.${versionDirName};

<#compress>
	<#list openAPIYAML.components.schemas?keys as schemaName>
		import ${configYAML.apiPackagePath}.dto.${versionDirName}.${schemaName};
		import ${configYAML.apiPackagePath}.resource.${versionDirName}.${schemaName}Resource;
	</#list>
</#compress>

import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import graphql.schema.DataFetchingEnvironment;

import java.util.Collection;

import javax.annotation.Generated;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author ${configYAML.author}
 * @generated
 */
@Generated("")
public class Mutation {

	<#assign
		javaSignatures = javaTool.getGraphQLJavaSignatures(openAPIYAML)
		schemaNames = ""
	/>

	<#list javaSignatures?keys as schemaName>
		<#list javaSignatures[schemaName] as javaSignature>
			<#if stringUtil.startsWith(javaSignature.methodName, "get")>
				<#continue>
			</#if>

			<#assign schemaNames = stringUtil.add(schemaNames, schemaName) />

			<#compress>
				<#list javaTool.getGraphQLMethodAnnotations(javaSignature) as methodAnnotation>
					${methodAnnotation}
				</#list>

				<@compress single_line=true>
					public ${javaSignature.returnType} ${javaSignature.methodName}(
						<#list javaSignature.javaParameters as javaParameter>
							${javaTool.getGraphQLParameterAnnotation(javaParameter)} ${javaParameter.parameterType} ${javaParameter.parameterName}

							<#if javaParameter_has_next>
								,
							</#if>
						</#list>
					) throws Exception {
				</@compress>
			</#compress>

			<#assign getResourceMethodContent>
				<@compress single_line=true>
					_get${schemaName}Resource().${javaSignature.methodName}(
						<#list javaSignature.javaParameters as javaParameter>
							${javaParameter.parameterName}

							<#if javaParameter_has_next>
								,
							</#if>
						</#list>
					)
				</@compress>
			</#assign>

			<#assign getResourceMethodContent = getResourceMethodContent?replace("perPage , page", "Pagination.of(perPage, page)") />

			<#assign methodBody>
				<#if stringUtil.equals(javaSignature.returnType, "Response")>
					Response.ResponseBuilder responseBuilder = Response.ok();

					return responseBuilder.build();
				<#elseif javaSignature.returnType?contains("Collection<")>
					return ${getResourceMethodContent}.getItems();
				<#else>
					return ${getResourceMethodContent};
				</#if>
			</#assign>

			<#list methodBody?split("\n") as line>
				${line?replace("^\t\t\t", "", "r")}<#lt>
			</#list>

			}

		</#list>
	</#list>

	<#list stringUtil.split(schemaNames) as schemaName>
		private static ${schemaName}Resource _get${schemaName}Resource() {
			return _${schemaName?uncap_first}ResourceServiceTracker.getService();
		}

		private static final ServiceTracker<${schemaName}Resource, ${schemaName}Resource> _${schemaName?uncap_first}ResourceServiceTracker;

	</#list>

	static {
		Bundle bundle = FrameworkUtil.getBundle(Mutation.class);

		<#list stringUtil.split(schemaNames) as schemaName>
		ServiceTracker<${schemaName}Resource, ${schemaName}Resource> ${schemaName?uncap_first}ResourceServiceTracker =
			new ServiceTracker<${schemaName}Resource, ${schemaName}Resource>(bundle.getBundleContext(), ${schemaName}Resource.class, null);

		${schemaName?uncap_first}ResourceServiceTracker.open();

		_${schemaName?uncap_first}ResourceServiceTracker = ${schemaName?uncap_first}ResourceServiceTracker;

		</#list>
	}

}