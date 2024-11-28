#!/bin/bash

function main {
	cd ../../../../../

	touch "build.$(whoami).properties"
	
	if [[ $(grep -c "build.repository.private.password" "build.$(whoami).properties") == 0 ]]
	then
		local nexus_password=$(op read "op://Analytics Cloud Team/Nexus Private Repository Account/password")
		
		echo "build.repository.private.password=${nexus_password}" >> "build.$(whoami).properties"
	fi

	if [[ $(grep -c "build.repository.private.url" "build.$(whoami).properties") == 0 ]]
	then
		local nexus_website=$(op read "op://Analytics Cloud Team/Nexus Private Repository Account/website")
		
		echo "build.repository.private.url=${nexus_website}" >> "build.$(whoami).properties"
	fi

	if [[ $(grep -c "build.repository.private.username" "build.$(whoami).properties") == 0 ]]
	then
		local nexus_username=$(op read "op://Analytics Cloud Team/Nexus Private Repository Account/username")
		
		echo "build.repository.private.username=${nexus_username}" >> "build.$(whoami).properties"
	fi

	ant update-gradle-properties
}

main