package ${configYAML.apiPackagePath}.internal.graphql.mutation.${versionDirName};

<#compress>
	<#list openAPIYAML.components.schemas?keys as schemaName>
		import ${configYAML.apiPackagePath}.dto.${versionDirName}.${schemaName};
		import ${configYAML.apiPackagePath}.resource.${versionDirName}.${schemaName}Resource;
	</#list>
</#compress>

import com.liferay.oauth2.provider.scope.RequiresScope;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLInvokeDetached;
import graphql.annotations.annotationTypes.GraphQLName;

import java.util.Collection;
import java.util.Date;

import javax.annotation.Generated;

import javax.ws.rs.core.Response;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author ${configYAML.author}
 * @generated
 */
@Generated("")
public class Mutation {

	<#assign javaMethodSignatures = freeMarkerTool.getGraphQLJavaMethodSignatures(configYAML, openAPIYAML, "mutation", false) />

	<#list javaMethodSignatures as javaMethodSignature>
		${freeMarkerTool.getGraphQLMethodAnnotations(javaMethodSignature)}
		public ${javaMethodSignature.returnType} ${javaMethodSignature.methodName}(
				${freeMarkerTool.getGraphQLParameters(javaMethodSignature.javaParameters, true)})
			throws Exception {

			<#if stringUtil.equals(javaMethodSignature.returnType, "Response")>
				Response.ResponseBuilder responseBuilder = Response.ok();

				return responseBuilder.build();
			<#else>
				return _get${javaMethodSignature.schemaName}Resource().${javaMethodSignature.methodName}(
					${freeMarkerTool.getGraphQLArguments(javaMethodSignature.javaParameters)});
			</#if>
		}
	</#list>

	<#assign schemaNames = freeMarkerTool.getGraphQLSchemaNames(javaMethodSignatures) />

	<#list schemaNames as schemaName>
		private static ${schemaName}Resource _get${schemaName}Resource() {
			return _${schemaName?uncap_first}ResourceServiceTracker.getService();
		}

		private static final ServiceTracker<${schemaName}Resource, ${schemaName}Resource>
			_${schemaName?uncap_first}ResourceServiceTracker;
	</#list>

	<#if schemaNames?size != 0>
		static {
			Bundle bundle = FrameworkUtil.getBundle(Mutation.class);

			<#list schemaNames as schemaName>
				ServiceTracker<${schemaName}Resource, ${schemaName}Resource>
					${schemaName?uncap_first}ResourceServiceTracker =
						new ServiceTracker<>(
							bundle.getBundleContext(),
							${schemaName}Resource.class, null);

				${schemaName?uncap_first}ResourceServiceTracker.open();

				_${schemaName?uncap_first}ResourceServiceTracker =
					${schemaName?uncap_first}ResourceServiceTracker;
			</#list>
		}
	</#if>

}