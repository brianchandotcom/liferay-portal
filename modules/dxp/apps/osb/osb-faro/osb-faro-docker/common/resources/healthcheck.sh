#!/bin/bash

content=$(wget http://localhost:8080 -q -O -)

if [[ -z "${content}" ]] || [[ "${content}" == *connect* ]]; then
	exit 1
else
	exit 0
fi