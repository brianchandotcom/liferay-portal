#!/bin/bash

#
# Ignore SIGHUP to avoid stopping the process when terminal disconnects.
#

trap '' 1

if [ -e /proc/$$/fd/255 ]
then
	DB_SCHEMA_IMPORTER_PATH=`readlink /proc/$$/fd/255 2>/dev/null`
fi

if [ ! -n "${DB_SCHEMA_IMPORTER_PATH}" ]
then
	DB_SCHEMA_IMPORTER_PATH="$0"
fi

cd "$(dirname "${DB_SCHEMA_IMPORTER_PATH}")"

#
# Run database schema importer tool.
#

java -jar com.liferay.portal.tools.db.schema.importer.jar "$@"