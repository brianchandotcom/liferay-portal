#!/bin/bash

set -e -x

CURRENT_DIR_NAME=$(dirname ${BASH_SOURCE[0]})

source ${CURRENT_DIR_NAME}/common.sh

stop_app_server