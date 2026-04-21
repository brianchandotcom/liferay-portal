#!/bin/sh

until curl \
	--fail \
	--header "Authorization: Bearer ${GATEWAY_TOKEN}" \
	--max-time 5 \
	--output /dev/null \
	--silent \
	"${GATEWAY_URL}"
do
	echo "Waiting for Connect Gateway."

	sleep 10
done
