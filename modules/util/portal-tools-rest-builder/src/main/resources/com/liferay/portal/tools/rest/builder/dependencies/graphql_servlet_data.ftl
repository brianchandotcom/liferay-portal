package ${configYAML.apiPackagePath}.internal.graphql.servlet.${escapedVersion};

<#list allExternalSchemas?keys as schemaName>
	import ${configYAML.apiPackagePath}.resource.${escapedVersion}.${schemaName}Resource;
</#list>

<#list freeMarkerTool.getSchemas(openAPIYAML)?keys as schemaName>
	import ${configYAML.apiPackagePath}.resource.${escapedVersion}.${schemaName}Resource;
</#list>

import ${configYAML.apiPackagePath}.internal.graphql.mutation.${escapedVersion}.Mutation;
import ${configYAML.apiPackagePath}.internal.graphql.query.${escapedVersion}.Query;

import com.liferay.portal.vulcan.graphql.servlet.ServletData;

import javax.annotation.Generated;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentServiceObjects;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;

/**
 * @author ${configYAML.author}
 * @generated
 */
@Component(immediate = true, service = ServletData.class)
@Generated("")
public class ServletDataImpl implements ServletData {

	<#assign
		mutationSchemaNames = freeMarkerTool.getGraphQLSchemaNames(freeMarkerTool.getGraphQLJavaMethodSignatures(configYAML, "mutation", openAPIYAML))
		querySchemaNames = freeMarkerTool.getGraphQLSchemaNames(freeMarkerTool.getGraphQLJavaMethodSignatures(configYAML, "query", openAPIYAML))
	/>

	@Activate
	public void activate(BundleContext bundleContext) {
		<#list mutationSchemaNames as schemaName>
			Mutation.set${schemaName}ResourceComponentServiceObjects(_${freeMarkerTool.getSchemaVarName(schemaName)}ResourceComponentServiceObjects);
		</#list>

		<#list querySchemaNames as schemaName>
			Query.set${schemaName}ResourceComponentServiceObjects(_${freeMarkerTool.getSchemaVarName(schemaName)}ResourceComponentServiceObjects);
		</#list>
	}

	@Override
	public Mutation getMutation() {
		return new Mutation();
	}

	@Override
	public String getPath() {
		return "${configYAML.application.baseURI}-graphql/${escapedVersion}";
	}

	@Override
	public Query getQuery() {
		return new Query();
	}

	<#if configYAML.graphQLNamespace??>
		@Override
		public String getGraphQLNamespace() {
			return "${configYAML.graphQLNamespace}";
		}
	</#if>

	<#assign schemaNames = mutationSchemaNames />

	<#list querySchemaNames as schemaName>
		<#if !schemaNames?seq_contains(schemaName)>
			<#assign schemaNames = schemaNames + [schemaName] />
		</#if>
	</#list>

	<#list schemaNames as schemaName>
		@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
		private ComponentServiceObjects<${schemaName}Resource> _${freeMarkerTool.getSchemaVarName(schemaName)}ResourceComponentServiceObjects;
	</#list>

}