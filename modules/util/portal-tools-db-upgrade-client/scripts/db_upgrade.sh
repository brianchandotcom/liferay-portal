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

#Check running process
DB_UPGRADE_PID=db_upgrade.pid

if [ -f "$DB_UPGRADE_PID" ]; then
	if [ -s "$DB_UPGRADE_PID" ]; then
		echo "Existing PID file found during start."
		if [ -r "$DB_UPGRADE_PID" ]; then
			PID=`cat "$DB_UPGRADE_PID"`
			ps -p $PID >/dev/null 2>&1
			if [ $? -eq 0 ] ; then
				echo "Upgrade client appears to still be running with PID $PID. Start aborted."
				echo ""
				echo "If the following process is not the upgrade client process, remove the PID file and try again:"
				ps -f -p $PID
				exit 1
			else
				echo "Removing/clearing stale PID file."
				rm -f "$DB_UPGRADE_PID" >/dev/null 2>&1
				if [ $? != 0 ]; then
					if [ -w "$DB_UPGRADE_PID" ]; then
						cat /dev/null > "$DB_UPGRADE_PID"
					else
						echo "Unable to remove or clear stale PID file. Start aborted."
						exit 1
					fi
				fi
			fi
		else
			echo "Unable to read PID file. Start aborted."
			exit 1
		fi
	else
		rm -f "$DB_UPGRADE_PID" >/dev/null 2>&1
		if [ $? != 0 ]; then
			if [ ! -w "$DB_UPGRADE_PID" ]; then
			echo "Unable to remove or write to empty PID file. Start aborted."
			exit 1
			fi
		fi
	fi
fi

echo $$ > $DB_UPGRADE_PID

#Execute upgrade client
java -jar com.liferay.portal.tools.db.upgrade.client.jar "$@"

#Cleanup
rm $DB_UPGRADE_PID
