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

## Ant

	<path id="cp">
		<!--
		com.liferay.tools.servicebuilder-*.jar
		commons-io.jar
		dom4j.jar
		jalopy.jar
		jaxen.jar
		log4j.jar
		org.freemarker.jar
		qdox.jar
		-->
	</path>

	<taskdef classpathref="cp" resource="com/liferay/portal/tools/servicebuilder/ant/taskdefs.properties" />

	<target name="build-service">
		<service-builder
			apiDir="api"
		/>
	</target>

The `service-builder` task has the following attributes and defaults:

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
| modelHintsConfigs           | `classpath*:META-INF/portal-model-hints.xml,META-INF/portal-model-hints.xml,classpath*:META-INF/ext-model-hints.xml,META-INF/portlet-model-hints.xml` |
| mergeModelHintsConfigs      | comma delimited list of model hints configs to include on top of the defaults |
| modelHintsFileName          | `src/META-INF/portal-model-hints.xml` |
| osgiModule                  | `false` |
| pluginName                  | null |
| propsUtil                   | `com.liferay.portal.util.PropsUtil` |
| readOnlyPrefixes            | `fetch,get,has,is,load,reindex,search` |
| mergeReadOnlyPrefixes       | comma delimited list of prefixes to include on top of the defaults |
| remotingFileName            | `../portal-web/docroot/WEB-INF/remoting-servlet.xml` |
| resourceActionsConfigs      | `META-INF/resource-actions/default.xml,resource-actions/default.xml` |
| mergeResourceActionsConfigs | comma delimited list of resource action configs to include on top of the defaults |
| resourcesDir                | `src` |
| springFileName              | `src/META-INF/portal-spring.xml` |
| springNamespaces            | `beans` |
| sqlDir                      | `../sql` |
| sqlFileName                 | `portal-tables.sql` |
| sqlIndexesFileName          | `indexes.sql` |
| sqlSequencesFileName        | `sequences.sql` |
| targetEntityName            | null |
| testDir                     | `test/integration` |
