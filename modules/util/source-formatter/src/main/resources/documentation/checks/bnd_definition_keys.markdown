## BND Definition Keys

Definition keys in *.bnd files have to match either:

* a key defined in
[BUNDLE\_SPECIFIC\_HEADERS](http://grepcode.com/file/repo1.maven.org/maven2/biz.aQute.bnd/bnd/2.1.0/aQute/bnd/osgi/Constants.java#Constants.0BUNDLE_SPECIFIC_HEADERS),
[headers](http://grepcode.com/file/repo1.maven.org/maven2/biz.aQute.bnd/bnd/2.1.0/aQute/bnd/osgi/Constants.java#Constants.0headers)
or
[options](http://grepcode.com/file/repo1.maven.org/maven2/biz.aQute.bnd/bnd/2.1.0/aQute/bnd/osgi/Constants.java#Constants.0options)

or

* a Liferay specific definition key defined in `_APP_BND_DEFINITION_KEYS`,
 `_APP_BND_DEFINITION_KEYS` or `_APP_BND_DEFINITION_KEYS` in
[BNDSourceUtil](https://github.com/liferay/liferay-portal/blob/master/modules/util/source-formatter/src/main/java/com/liferay/source/formatter/checks/util/BNDSourceUtil.java)