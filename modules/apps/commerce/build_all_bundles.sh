#!/bin/bash

function main {
	#./build_bundle.sh releases.liferay.com/commerce/2.0.7/Liferay%20Commerce%202.0.7%20-%207.2.x.lpkg releases.liferay.com/portal/7.2.1-ga2/liferay-ce-portal-tomcat-7.2.1-ga2-20191111141448326.7z
	#./build_bundle.sh files.liferay.com/private/ee/commerce/2.0.7/Liferay%20Commerce%20Enterprise%202.0.7%20-%207.1.x.lpkg files.liferay.com/private/ee/portal/7.1.10.3/liferay-dxp-tomcat-7.1.10.3-sp3-20191118185746787.7z files.liferay.com/private/ee/fix-packs/7.1.10/dxp/liferay-fix-pack-dxp-16-7110.zip
	./build_bundle.sh files.liferay.com/private/ee/commerce/2.1.2/Liferay%20Commerce%20Enterprise%202.1.2%20-%207.1.x.lpkg files.liferay.com/private/ee/portal/7.1.10.4/liferay-dxp-tomcat-7.1.10.4-sp4-20200331093526761.7z files.liferay.com/private/ee/fix-packs/7.1.10/dxp/liferay-fix-pack-dxp-18-7110.zip
	./build_bundle.sh files.liferay.com/private/ee/commerce/2.1.2/Liferay%20Commerce%20Enterprise%202.1.2%20-%207.2.x.lpkg files.liferay.com/private/ee/portal/7.2.10.2/liferay-dxp-tomcat-7.2.10.2-sp2-20200511121558464.7z files.liferay.com/private/ee/fix-packs/7.2.10/dxp/liferay-fix-pack-dxp-6-7210.zip
}

main