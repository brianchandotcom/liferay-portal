#!/bin/bash

#
# Ignore SIGHUP to avoid stopping the process when terminal disconnects.
#

trap '' 1

if [ -e /proc/$$/fd/255 ]
then
	DB_PARTITION_MIGRATION_PATH=`readlink /proc/$$/fd/255 2>/dev/null`
fi

if [ ! -n "${DB_PARTITION_MIGRATION_PATH}" ]
then
	DB_PARTITION_MIGRATION_PATH="$0"
fi

cd "$(dirname "${DB_PARTITION_MIGRATION_PATH}")"

#
# Run database partition virtual instance migration tool.
#

java -jar com.liferay.portal.tools.db.partition.virtual.instance.migration.jar "$@"