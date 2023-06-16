#!/bin/bash

#
# Ignore SIGHUP to avoid stopping migration when terminal disconnects.
#

trap '' 1

if [ -e /proc/$$/fd/255 ]
then
	DB_PARTITION_MIGRATOR_PATH=`readlink /proc/$$/fd/255 2>/dev/null`
fi

if [ ! -n "${DB_PARTITION_MIGRATOR_PATH}" ]
then
	DB_PARTITION_MIGRATOR_PATH="$0"
fi

cd "$(dirname "${DB_PARTITION_MIGRATOR_PATH}")"

#
# Run database virtual instance migrator tool.
#

java -jar com.liferay.portal.tools.db.partition.virtual.instance.migrator.jar "$@"