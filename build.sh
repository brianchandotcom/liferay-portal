#
# Execute clean, compile and deploy without allowing ant and gradle to
# run at the same time.
#

*!/bin/bash
#
# Prevent the shell script from continuing if an error occurs.
# add "|| true" to the end of lines where errors may be tolerated.
#
set -e

rm post-build.sh || true

echo "#"
echo "# CLEAN"
echo "#"

ant clean -Dgradle.postpone.flag=true

if [ -f ./post-build.sh ];
then
	./post-build.sh
else
	echo "post-build.sh does not exist."
fi

rm post-build.sh || true

echo "*"
echo "* COMPILE 1"
echo "*"

ant compile-1 -Dgradle.postpone.flag=true

if [ -f ./post-build.sh ];
then
	./post-build.sh
else
	echo "post-build.sh does not exist."
fi

rm post-build.sh || true

echo "*"
echo "* COMPILE 2"
echo "*"

ant compile-2 -Dgradle.postpone.flag=true

if [ -f ./post-build.sh ];
then
	./post-build.sh
else
	echo "post-build.sh does not exist."
fi

rm post-build.sh || true

echo "*"
echo "* DEPLOY"
echo "*"

ant deploy -Dgradle.postpone.flag=true

if [ -f ./post-build.sh ];
then
	./post-build.sh
else
	echo "post-build.sh does not exist."
fi

rm post-build.sh || true

echo "*"
echo "* END"
echo "*"
