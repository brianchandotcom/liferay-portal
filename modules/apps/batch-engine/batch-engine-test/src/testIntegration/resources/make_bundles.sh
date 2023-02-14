#!/bin/bash

DEPENDENCIES=$(pwd)/com/liferay/batch/engine/internal/test/dependencies/

mkdir -p "${DEPENDENCIES}"

rm ${DEPENDENCIES}/*

cp -rf \
	../../../../batch-engine-service/src/test/resources/com/liferay/batch/engine/internal/auto/deploy/* \
	"${DEPENDENCIES}"

BASE_DIR=$(pwd)

(
	cd "${DEPENDENCIES}"

	for i in {1..8}; do
			sed -e "s/%batchPath%/batch${i}/g" ${BASE_DIR}/MANIFEST-TPL.MF > MANIFEST-${i}.MF
			jar -c -f batch${i}.jar -m MANIFEST-${i}.MF batch${i}/*
			rm -rf MANIFEST-${i}.MF batch${i}
	done

	rm *.json
)