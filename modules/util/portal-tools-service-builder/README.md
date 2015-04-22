# Liferay Service Builder

> Service Builder is a model-driven code generation tool built by Liferay that
> allows developers to define custom object models called entities. Service
> Builder generates a service layer through object-relational mapping (ORM)
> technology that provides a clean separation between your object model and code
> for the underlying database. This frees you to add the necessary business
> logic for your application. Service Builder takes an XML file as input and
> generates the necessary model, persistence, and service layers for your
> application. These layers provide a clean separation of concerns. Service
> Builder generates most of the common code needed to implement create, read,
> update, delete, and find operations on the database, allowing you to focus on
> the higher level aspects of service design.
> -- [Liferay Developer Network](https://dev.liferay.com/develop/tutorials/-/knowledge_base/6-2/what-is-service-builder)

For the latest information on Service Builder please see the [Service Builder](https://dev.liferay.com/develop/tutorials/-/knowledge_base/6-2/what-is-service-builder) page on the [Liferay Developer Network](https://dev.liferay.com/).

## Ant & Ivy

In `ivy-settings.xml`:

	<resolvers>
		<ibiblio m2compatible="true" name="liferay-public" root="http://cdn.repository.liferay.com/nexus/content/groups/public" />
		...

		<chain dual="true" name="default">
			...

			<resolver ref="liferay-public" />
		</chain>
	</resolvers>

In `build.xml`:

	... load ivy

	<cachepath
		conf="ant"
		inline="true"
		module="com.liferay.portal.tools.service.builder"
		organisation="com.liferay"
		pathid="sb.classpath"
		revision="1.1.0"
		xmlns="antlib:org.apache.ivy.ant"
	/>

	<taskdef classpathref="sb.classpath" resource="com/liferay/portal/tools/service/builder/ant/taskdefs.properties" />

	<target name="build-service">
		<service-builder apiDir="../api/src" />
	</target>

Command line:

	ant build-service

## Configuration

Service builder has the following fields and defaults (same for all plugins):

| attribute (all optional)    | default value                                  |
|-----------------------------|:-----------------------------------------------|
| apiDir                      | `../portal-service/src` |
| autoImportDefaultReferences | `true` |
| autoNamespaceTables         | `false` |
| beanLocatorUtil             | `com.liferay.portal.kernel.bean.PortalBeanLocatorUtil` |
| buildNumber                 | `1` |
| buildNumberIncrement        | `true` |
| hbmFileName                 | `src/META-INF/portal-hbm.xml` |
| implDir                     | `src` |
| inputFileName               | `service.xml` |
| mergeModelHintsConfigs      | comma delimited list of model hints configs to include on top of the defaults |
| mergeReadOnlyPrefixes       | comma delimited list of prefixes to include on top of the defaults |
| mergeResourceActionsConfigs | comma delimited list of resource action configs to include on top of the defaults |
| modelHintsConfigs           | `classpath*:META-INF/portal-model-hints.xml,META-INF/portal-model-hints.xml,classpath*:META-INF/ext-model-hints.xml,META-INF/portlet-model-hints.xml` |
| modelHintsFileName          | `src/META-INF/portal-model-hints.xml` |
| osgiModule                  | `false` |
| pluginName                  | null |
| propsUtil                   | `com.liferay.portal.util.PropsUtil` |
| readOnlyPrefixes            | `fetch,get,has,is,load,reindex,search` |
| remotingFileName            | `../portal-web/docroot/WEB-INF/remoting-servlet.xml` |
| resourceActionsConfigs      | `META-INF/resource-actions/default.xml,resource-actions/default.xml` |
| resourcesDir                | `src` |
| springFileName              | `src/META-INF/portal-spring.xml` |
| springNamespaces            | `beans` |
| sqlDir                      | `../sql` |
| sqlFileName                 | `portal-tables.sql` |
| sqlIndexesFileName          | `indexes.sql` |
| sqlSequencesFileName        | `sequences.sql` |
| targetEntityName            | null |
| testDir                     | `test/integration` |
