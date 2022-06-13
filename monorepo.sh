#!/bin/bash

if [ $# -eq 0 ]
then
  git clone --depth 1 --filter=blob:none --no-checkout git@github.com:liferay/liferay-portal.git
  cd liferay-portal
  git sparse-checkout init --cone
  git sparse-checkout set monorepo.sh
  git checkout master
  echo "Please specify a team. './monorepo.sh <team>' Possible values: portal, devtools, cloud."
  exit 0
fi

DISABLE=false
TEAM=$1

case $TEAM in
"cloud")
	PATTERNS="modules/cloud"
	;;
"devtools")
	PATTERNS="modules/*.gradle modules/*.xml modules/*.properties modules/*.js modules/*.json modules/yarn.lock modules/sdk/ modules/util/"
	;;
"portal")
  DISABLE=true
	;;
*)
	die "Please specify a valid team. `./monorepo.sh <team>` Possible values: cloud, devtools, portal"
	;;
esac

if $DISABLE
then
  git sparse-checkout disable
else
  echo "Running 'git sparse-checkout init --cone'"
  git sparse-checkout init --cone

  echo "Running 'git sparse-checkout set $PATTERNS'"
  git sparse-checkout set $PATTERNS
fi