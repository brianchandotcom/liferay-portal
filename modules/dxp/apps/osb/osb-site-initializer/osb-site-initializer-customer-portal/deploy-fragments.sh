#/bin/bash

# Install dependencies

cd extra/react-fragments
yarn install

# Build React Fragments

yarn build

# Match files for build

cd build

for folder in */ ; do
	[ -L "${folder%/}" ] && continue
	cp "${folder}index.js" "../src/${folder}index.build.js"
done

# Somehow we need to copy the folder react-fragments/src/* to src/main/zippableResources/fragments/fragments
# PS: The file index.build.js should be named index.js after copied
# and then run gw forceDeploy (or deploy) and remove the copied files to not be tracked by Git
# This is what we do to run today, but should be better options.

# Would be good if possible, to minify the index.build.js before used in gw forceDeploy