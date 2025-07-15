#!/bin/bash

for link in $(rg "\"url\"\:\ \"http.*?\"" --glob="*.json" --only-matching | rg --only-matching http.*[a-z0-9])
do

	#echo $link

		if [[ $(curl --head --location --output /dev/null --silent --write-out "%{http_code}" "${link}") == "404" ]]
		then
			_LINK_FILE_NAME=${url}

			echo "broken_link 404: $link"
		fi
done
