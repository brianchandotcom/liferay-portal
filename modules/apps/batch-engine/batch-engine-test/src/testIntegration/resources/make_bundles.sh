#!/bin/bash

DEPENDENCIES=$(pwd)/com/liferay/batch/engine/internal/test/dependencies

BASE_DIR=$(pwd)

(
	cd "${DEPENDENCIES}"

	for i in {1..8}; do
		(
			cd batch${i}
			jar -c -f ../batch${i}.jar -m META-INF/MANIFEST.MF batch${i}/*
		)
	done
)