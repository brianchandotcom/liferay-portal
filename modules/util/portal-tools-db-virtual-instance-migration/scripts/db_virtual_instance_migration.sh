#!/bin/bash

#
# Ignore SIGHUP to avoid stopping migration when terminal disconnects.
#

trap '' 1

if [ -e /proc/$$/fd/255 ]
then
	DB_MIGRATION_PATH=`readlink /proc/$$/fd/255 2>/dev/null`
fi

if [ ! -n "${DB_MIGRATION_PATH}" ]
then
	DB_MIGRATION_PATH="$0"
fi

cd "$(dirname "${DB_MIGRATION_PATH}")"

#
# Run database virtual intance migration tool.
#

java -jar com.liferay.portal.tools.db.virtual.instance.migration.jar "$@"