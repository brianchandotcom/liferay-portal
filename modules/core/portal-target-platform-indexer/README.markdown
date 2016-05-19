#
# Liferay Indexing Process
# ========================
#

#
# Change these vars to reflect the environment.
#
liferay_home=/home/rotty/AS/liferay-portal

app_server_dir=${liferay_home}/tomcat-8.0.32

module_framework_base_dir=${liferay_home}/osgi

#
# This is the classpath common to all functions. Order matters!
#
jars=(
	${app_server_dir}/webapps/ROOT/WEB-INF/lib/commons-beanutils.jar
	${app_server_dir}/webapps/ROOT/WEB-INF/lib/commons-configuration.jar
	${app_server_dir}/webapps/ROOT/WEB-INF/lib/commons-digester.jar
	${app_server_dir}/webapps/ROOT/WEB-INF/lib/commons-io.jar
	${app_server_dir}/webapps/ROOT/WEB-INF/lib/commons-lang.jar
	${app_server_dir}/webapps/ROOT/WEB-INF/lib/commons-logging.jar
	${app_server_dir}/webapps/ROOT/WEB-INF/lib/easyconf.jar
	${app_server_dir}/webapps/ROOT/WEB-INF/lib/portal-impl.jar
	${app_server_dir}/webapps/ROOT/WEB-INF/lib/spring-beans.jar
	${app_server_dir}/webapps/ROOT/WEB-INF/lib/spring-core.jar
	${app_server_dir}/webapps/ROOT/WEB-INF/lib/util-java.jar
	${app_server_dir}/webapps/ROOT/WEB-INF/lib/xpp3.jar
	${app_server_dir}/webapps/ROOT/WEB-INF/lib/xstream.jar
	${app_server_dir}/lib/ext/com.liferay.registry.api.jar
	${app_server_dir}/lib/ext/portal-kernel.jar
	${app_server_dir}/lib/servlet-api.jar
	${module_framework_base_dir}/core/com.liferay.jaxws.osgi.bridge.jar
	${module_framework_base_dir}/core/com.liferay.osgi.service.tracker.collections.jar
	${module_framework_base_dir}/core/com.liferay.portal.app.license.api.jar
	${module_framework_base_dir}/core/com.liferay.portal.bootstrap.jar
	${module_framework_base_dir}/core/com.liferay.registry.impl.jar
	${module_framework_base_dir}/core/org.eclipse.osgi.jar
	${module_framework_base_dir}/static/com.liferay.portal.target.platform.indexer.jar
)

function classpathify { local IFS=":"; echo "$*"; }

CLASSPATH=$(classpathify "${jars[@]}")

#
# Indexing the target platform
# ============================
#
# Execute the following java command and the resulting target platform index
# file will be placed in ${module_framework_base_dir}/target-platform/
#

java \
	-classpath "$CLASSPATH" \
	-Dexternal-properties=${liferay_home}/portal-ext.properties \
	com.liferay.portal.target.platform.indexer.main.TargetPlatformMain

#
# Indexing LPKG files
# ===================
#
# Execute the following java command and the resulting LPKG index files
# will be placed in ${module_framework_base_dir}/target-platform/
#

java \
	-classpath "$CLASSPATH" \
	-Dexternal-properties=${liferay_home}/portal-ext.properties \
	com.liferay.portal.target.platform.indexer.main.LPKGIndexerMain \
	${module_framework_base_dir}/marketplace/

#
# Validating Index files
# ======================
#
# Execute the following java command to validate index files. An empty result
# means the indexes are valid and everything will resolve. Any errors will be
# reported, one per line.
#

java \
	-classpath "$CLASSPATH" \
	-Dexternal-properties=${liferay_home}/portal-ext.properties \
	com.liferay.portal.target.platform.indexer.main.IndexValidatorMain \
	${module_framework_base_dir}/target-platform/
