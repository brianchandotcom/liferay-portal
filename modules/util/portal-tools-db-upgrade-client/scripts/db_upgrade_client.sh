#!/bin/bash

#
# Ignore SIGHUP to avoid stopping upgrade when terminal disconnects.
#

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

#
# Check running process.
#

DB_UPGRADE_PID=db_upgrade.pid

if [ -f "${DB_UPGRADE_PID}" ]
then
	if [ -s "${DB_UPGRADE_PID}" ]
	then
		if [ -r "${DB_UPGRADE_PID}" ]
		then
			PID=`cat "${DB_UPGRADE_PID}"`

			ps -p ${PID} >/dev/null 2>&1

			if [ $? -eq 0 ]
			then
				echo "Database upgrade client is already running with process ID ${PID}."
				echo ""
				echo "If the following process is not the database upgrade client process, remove ${DB_UPGRADE_PID} and try again."

				ps -f -p ${PID}

				exit 1
			else
				echo "Removing stale ${DB_UPGRADE_PID}."

				rm -f "${DB_UPGRADE_PID}" >/dev/null 2>&1

				if [ $? != 0 ]
				then
					if [ -w "${DB_UPGRADE_PID}" ]
					then
						cat /dev/null > "${DB_UPGRADE_PID}"
					else
						echo "Unable to remove stale ${DB_UPGRADE_PID}."

						exit 1
					fi
				fi
			fi
		else
			echo "Unable to read ${DB_UPGRADE_PID}."

			exit 1
		fi
	else
		rm -f "${DB_UPGRADE_PID}" >/dev/null 2>&1

		if [ $? != 0 ]
		then
			if [ ! -w "${DB_UPGRADE_PID}" ]
			then
				echo "Unable to write to ${DB_UPGRADE_PID}."

				exit 1
			fi
		fi
	fi
fi

echo $$ > ${DB_UPGRADE_PID}

#
# Run database upgrade client.
#

java -jar com.liferay.portal.tools.db.upgrade.client.jar "$@"

#
# Clean up.
#

rm ${DB_UPGRADE_PID}