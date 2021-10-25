#!/bin/bash

# Run Yarn Build and then, run this script.

ORIGIN=build/static
TARGET=build/liferay

mkdir -p $TARGET/js $TARGET/css

#
# Rename JS Files
#

cp $ORIGIN/js/main*.js $TARGET/js/main.min.js
cp $ORIGIN/js/2*.chunk.js $TARGET/js/2.chunk.min.js
cp $ORIGIN/js/runtime-main*.js $TARGET/js/runtime-main.min.js

#
# Rename CSS Files
#

cp $ORIGIN/css/main*.css $TARGET/css/main.min.css

echo "liferay setup:"
echo "- $TARGET/css/main.min.css"
echo "- $TARGET/js/2.chunk.min.js"
echo "- $TARGET/js/runtime-main.min.js"
echo "- $TARGET/js/runtime-main.min.js"