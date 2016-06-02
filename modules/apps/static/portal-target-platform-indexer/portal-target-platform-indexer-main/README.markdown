#
# Liferay Indexing Process
# ========================
#

#
# Change these vars to reflect the environment.
#

module_framework_base_dir=/home/rotty/AS/liferay-portal/osgi

#
# This is the classpath common to all functions.
#
jars=(
	${module_framework_base_dir}/core/com.liferay.portal.bootstrap.jar
	${module_framework_base_dir}/../tools/portal-target-platform-indexer-main/com.liferay.portal.target.platform.indexer.jar
	${module_framework_base_dir}/../tools/portal-target-platform-indexer-main/com.liferay.portal.target.platform.indexer.main.jar
	${module_framework_base_dir}/core/org.eclipse.osgi.jar
)

function classpathify { local IFS=":"; echo "$*"; }

CLASSPATH=$(classpathify "${jars[@]}")

JAVA_OPTS="-Dmodule.framework.base.dir=${module_framework_base_dir}"

#JAVA_OPTS="${JAVA_OPTS} -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5010"

#
# The following command does 3 things:
# 1. Index the target platform, save the indexes as a zip file. (Under current work dir with name target-platform-indexes-${timestamp}.zip)
# 2. Validate the generated indexes, if fails, prints error messages.
# 3. If passes validation, generates an integrity.properties file. (Under ${module.framework.base.dir}/target-platform/integrity.properties)
#
# This command reads the following system properties:
# 1. module.framework.base.dir, required, points to ${liferay.home}/osgi.
# 2. module.framework.static.dir, optional, defaults to ${module.framework.base.dir}/static.
# 3. module.framework.modules.dir, optional, defaults to ${module.framework.base.dir}/modules.
# 4. module.framework.portal.dir, optional, defaults to ${module.framework.base.dir}/portal.
# 5. module.framework.marketplace.dir, optional, defaults to ${module.framework.base.dir}/marketplace.
# 6. indexes.file, optional, defaults to ${module.framework.base.dir}/target-platform/target-platform-indexes-${timestamp}.zip. When specified will load indexes from the given zip file, rather than indexing from scratch.
# 7. integrity.properties, optional, defaults to ${module.framework.base.dir}/target-platform/integrity.properties.

java -classpath "$CLASSPATH" ${JAVA_OPTS} \
	com.liferay.portal.target.platform.indexer.main.TargetPlatformMain