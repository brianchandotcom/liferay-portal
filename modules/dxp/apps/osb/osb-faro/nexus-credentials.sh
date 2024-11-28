cd ../../../../../

NEXUS_USERNAME=$(op read "op://Analytics Cloud Team/Nexus Private Repository Account/username")
NEXUS_PASSWORD=$(op read "op://Analytics Cloud Team/Nexus Private Repository Account/password")
NEXUS_WEBSITE=$(op read "op://Analytics Cloud Team/Nexus Private Repository Account/website")

touch "build.$(whoami).properties"

if [[ $(grep -c "build.repository.private.password" "build.$(whoami).properties") == 0 ]]; then
	echo "build.repository.private.password=${NEXUS_PASSWORD}" >> "build.$(whoami).properties"
	echo "build.repository.private.url=${NEXUS_WEBSITE}" >> "build.$(whoami).properties"
	echo "build.repository.private.username=${NEXUS_USERNAME}" >> "build.$(whoami).properties"
fi

ant update-gradle-properties