#!/bin/bash

if [ -e /proc/$$/fd/255 ]; then
	scriptpath=`readlink /proc/$$/fd/255 2>/dev/null`
fi

if [ ! -n "$scriptpath" ]; then
	scriptpath="$0"
fi

cd "$(dirname "$scriptpath")"

java -jar com.liferay.portal.tools.db.upgrade.client.jar "$@"
