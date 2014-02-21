<?xml version="1.0"?>

 <xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:date="http://exslt.org/dates-and-times" xmlns:dyn="http://exslt.org/dynamic" xmlns:exsl="http://exslt.org/common" xmlns:func="http://exslt.org/functions" xmlns:math="http://exslt.org/math" xmlns:random="http://exslt.org/random" xmlns:regexp="http://exslt.org/regular-expressions" xmlns:set="http://exslt.org/sets" xmlns:str="http://exslt.org/strings" xmlns:xalan="http://xml.apache.org/xalan" version="1.0" exclude-result-prefixes="xalan" extension-element-prefixes="date dyn exsl func math random regexp set str xalan"> <!-- See http://www.exslt.org for details on the use of the above declared extentions. -->

	<xsl:output method="html" omit-xml-declaration="yes"/>
	<xsl:template match="/">
		Text: <xsl:value-of select="/root/dynamic-element[@name='Text']"/>
	</xsl:template>
</xsl:stylesheet>