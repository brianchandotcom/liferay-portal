#!/bin/bash
ORIGIN=build/static/js
TARGET=build/liferay/js

mkdir -p $TARGET

cp $ORIGIN/main*.js $TARGET/main.min.js
cp $ORIGIN/2*.chunk.js $TARGET/2.chunk.min.js
cp $ORIGIN/runtime-main*.js $TARGET/runtime-main.min.js

echo "liferay setup:"
echo "- $TARGET/main.min.js"
echo "- $TARGET/2.chunk.min.js"
echo "- $TARGET/runtime-main.min.js"