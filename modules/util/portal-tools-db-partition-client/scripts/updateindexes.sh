#!/bin/bash

#
# Ignore SIGHUP to avoid stopping PARTITION when terminal disconnects.
#

trap '' 1

if [ -e /proc/$$/fd/255 ]
then
	DB_PARTITION_PATH=`readlink /proc/$$/fd/255 2>/dev/null`
fi

if [ ! -n "${DB_PARTITION_PATH}" ]
then
	DB_PARTITION_PATH="$0"
fi

cd "$(dirname "${DB_PARTITION_PATH}")"

#
# Check running process.
#

DB_PARTITION_PID=db_PARTITION.pid

if [ -f "${DB_PARTITION_PID}" ]
then
	if [ -s "${DB_PARTITION_PID}" ]
	then
		if [ -r "${DB_PARTITION_PID}" ]
		then
			PID=`cat "${DB_PARTITION_PID}"`

			ps -p ${PID} >/dev/null 2>&1

			if [ $? -eq 0 ]
			then
				echo "Database PARTITION client is already running with process ID ${PID}."
				echo ""
				echo "If the following process is not the database PARTITION client process, remove ${DB_PARTITION_PID} and try again."

				ps -f -p ${PID}

				exit 1
			else
				echo "Removing stale ${DB_PARTITION_PID}."

				rm -f "${DB_PARTITION_PID}" >/dev/null 2>&1

				if [ $? != 0 ]
				then
					if [ -w "${DB_PARTITION_PID}" ]
					then
						cat /dev/null > "${DB_PARTITION_PID}"
					else
						echo "Unable to remove stale ${DB_PARTITION_PID}."

						exit 1
					fi
				fi
			fi
		else
			echo "Unable to read ${DB_PARTITION_PID}."

			exit 1
		fi
	else
		rm -f "${DB_PARTITION_PID}" >/dev/null 2>&1

		if [ $? != 0 ]
		then
			if [ ! -w "${DB_PARTITION_PID}" ]
			then
				echo "Unable to write to ${DB_PARTITION_PID}."

				exit 1
			fi
		fi
	fi
fi

echo $$ > ${DB_PARTITION_PID}

#
# Run database PARTITION client.
#

java "$@" -cp "*" com.liferay.portal.tools.db.partition.client.PartitionClient action updateIndexes

#
# Clean up.
#

rm ${DB_PARTITION_PID}