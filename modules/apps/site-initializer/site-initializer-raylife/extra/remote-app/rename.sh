#!/bin/bash
ORIGIN=build/static

mv $ORIGIN/js/main*.js $ORIGIN/js/main.js
mv $ORIGIN/js/2*.chunk.js $ORIGIN/js/2.js
mv $ORIGIN/js/runtime-main*.js $ORIGIN/js/runtime-main.js
mv $ORIGIN/css/main*.css $ORIGIN/css/main.css