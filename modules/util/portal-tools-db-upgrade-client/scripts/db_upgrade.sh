#!/bin/bash

#Ignore SIGHUP, to avoid stopping upgrade when terminal disconnects
trap '' 1

if [ -e /proc/$$/fd/255 ]
then
	DB_UPGRADE_PATH=`readlink /proc/$$/fd/255 2>/dev/null`
fi

if [ ! -n "${DB_UPGRADE_PATH}" ]
then
	DB_UPGRADE_PATH="$0"
fi

cd "$(dirname "${DB_UPGRADE_PATH}")"

java -jar com.liferay.portal.tools.db.upgrade.client.jar "$@"