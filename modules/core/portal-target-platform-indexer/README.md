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
	${module_framework_base_dir}/core/org.eclipse.osgi.jar
	${module_framework_base_dir}/static/com.liferay.portal.target.platform.indexer.jar
)

function classpathify { local IFS=":"; echo "$*"; }

CLASSPATH=$(classpathify "${jars[@]}")

JAVA_OPTS="-Dmodule.framework.base.dir=${module_framework_base_dir}"

#JAVA_OPTS="${JAVA_OPTS} -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5010"

#
# Indexing the target platform
# ============================
#
# Execute the following java command and the resulting target platform index
# file will be placed in ${module_framework_base_dir}/target-platform/
#

java -classpath "$CLASSPATH" ${JAVA_OPTS} \
	com.liferay.portal.target.platform.indexer.main.TargetPlatformMain

#
# Indexing LPKG files
# ===================
#
# Execute the following java command and all identified LPKG files will be 
# indexed. Any number of arguments are accepted. They can be any 
# combination of LPKG files or directories containing LPKG files (without 
# recursion). By default the resulting indexes will be placed in
# 
#    ${module_framework_base_dir}/target-platform/
#
# Optionally the property
#
#    -Doutput.dir=<path>
#
# can be used to change the output directory.
#

java -classpath "$CLASSPATH" ${JAVA_OPTS} \
	com.liferay.portal.target.platform.indexer.main.LPKGIndexerMain \
	${module_framework_base_dir}/marketplace/

#
# Validating Index files
# ======================
#
# Execute the following java command to validate index files. Any number of 
# arguments are accepted. They can be either index files, or directories 
# containing index files (no recursion). Any errors will be reported, one 
# per line.
#

java -classpath "$CLASSPATH" ${JAVA_OPTS} \
	com.liferay.portal.target.platform.indexer.main.IndexValidatorMain \
	${module_framework_base_dir}/target-platform/
