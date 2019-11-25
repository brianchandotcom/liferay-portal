#!/bin/bash

if [ -e /proc/$$/fd/255 ]
then
	DB_PARTITION_PATH=`readlink /proc/$$/fd/255 2>/dev/null`
fi

if [ ! -n "${DB_PARTITION_PATH}" ]
then
	DB_PARTITION_PATH="$0"
fi

cd "$(dirname "${DB_PARTITION_PATH}")"

java "$@" -cp "*" com.liferay.portal.tools.db.partition.client.PartitionClient action nextCompanyId