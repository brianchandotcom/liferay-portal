#
# Liferay Indexing Process
# ========================
#

#
# Change these vars to reflect the environment.
#

liferay_home=/opt/liferay

#
# This is the classpath to TargetPlatformMain
#
jars=(
	${liferay_home}/osgi/core/com.liferay.portal.bootstrap.jar
	${liferay_home}/osgi/static/com.liferay.portal.target.platform.indexer.jar
	${liferay_home}/tools/portal-tools-target-platform-indexer-client/com.liferay.portal.tools.target.platform.indexer.client.jar
	${liferay_home}/osgi/core/org.eclipse.osgi.jar
)

function classpathify { local IFS=":"; echo "$*"; }

CLASSPATH=$(classpathify "${jars[@]}")

JAVA_OPTS="-Dliferay.home=${liferay_home}"

#JAVA_OPTS="${JAVA_OPTS} -agentlib:jdwp=transport=dt_socket,address=5010,server=y,suspend=y"

#
# The following command does 3 things:
# 1. Index the target platform, save the indexes as a zip file. (Under ${liferay.home}/osgi/target-platform with name target-platform-indexes-${timestamp}.zip)
# 2. Validate the generated indexes, if fails, prints error messages.
# 3. If passes validation, generates an integrity.properties file. (Under ${liferay.home}/target-platform/integrity.properties)
#
# This command reads the following system properties:
# 1. liferay.home, required, points to ${liferay.home}.
# 2. module.framework.static.dir, optional, defaults to ${liferay.home}/osgi/static.
# 3. module.framework.modules.dir, optional, defaults to ${liferay.home}/osgi/modules.
# 4. module.framework.portal.dir, optional, defaults to ${liferay.home}/osgi/portal.
# 5. module.framework.marketplace.dir, optional, defaults to ${liferay.home}/osgi/marketplace.
# 6. indexes.file, optional, defaults to ${liferay.home}/osgi/target-platform/target-platform-indexes-${timestamp}.zip. When specified will load indexes from the given zip file, rather than indexing from scratch.
# 7. integrity.properties, optional, defaults to ${liferay.home}/osgi/target-platform/integrity.properties.

java -classpath "$CLASSPATH" ${JAVA_OPTS} \
	com.liferay.portal.tools.target.platform.indexer.client.TargetPlatformMain