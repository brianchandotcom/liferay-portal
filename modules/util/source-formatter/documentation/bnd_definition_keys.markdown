## BND Definition Keys

Definition keys in *.bnd files have to match either:

* a key defined in
[BUNDLE\_SPECIFIC\_HEADERS](http://grepcode.com/file/repo1.maven.org/maven2/biz.aQute.bnd/bnd/2.1.0/aQute/bnd/osgi/Constants.java#Constants.0BUNDLE_SPECIFIC_HEADERS),
[headers](http://grepcode.com/file/repo1.maven.org/maven2/biz.aQute.bnd/bnd/2.1.0/aQute/bnd/osgi/Constants.java#Constants.0headers)
or
[options](http://grepcode.com/file/repo1.maven.org/maven2/biz.aQute.bnd/bnd/2.1.0/aQute/bnd/osgi/Constants.java#Constants.0options)

or

* a Liferay specific definition key:

app.bnd | bnd.bnd | common.bnd
--- | --- | ---
Liferay-Releng-App-Description | -jsp | Git-Descriptor
Liferay-Releng-App-Title | -metatype-inherit | Git-SHA
Liferay-Releng-Bundle | -sass | Javac-Compiler
Liferay-Releng-Category | Can-Redefine-Classes | Javac-Debug
Liferay-Releng-Demo-Url | Can-Retransform-Classes | Javac-Deprecation
Liferay-Releng-Deprecated | Implementation-Version | Javac-Encoding
Liferay-Releng-Labs | JPM-Command | Liferay-Portal-Build-Date
Liferay-Releng-Marketplace | Liferay-Configuration-Path | Liferay-Portal-Build-Number
Liferay-Releng-Portal-Required | Liferay-Export-JS-Submodules | Liferay-Portal-Build-Time
Liferay-Releng-Public | Liferay-JS-Config | Liferay-Portal-Code-Name
Liferay-Releng-Restart-Required | Liferay-Releng-App-Description | Liferay-Portal-Parent-Build-Number
Liferay-Releng-Support-Url | Liferay-Releng-Module-Group-Description | Liferay-Portal-Release-Info
Liferay-Releng-Supported | Liferay-Releng-Module-Group-Title | Liferay-Portal-Server-Info
 | | Liferay-Require-SchemaVersion | Liferay-Portal-Version
 | | Liferay-Service |
 | | Liferay-Theme-Contributor-Type |
 | | Liferay-Theme-Contributor-Weight |
 | | Main-Class |
 | | Premain-Class |
 | | Web-ContextPath |