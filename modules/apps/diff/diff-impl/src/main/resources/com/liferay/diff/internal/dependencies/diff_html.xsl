<?xml version="1.0"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:output method="html" indent="yes"/>

	<xsl:template match="/">
		<xsl:apply-templates select="diffreport/css/node()"/>

		<xsl:apply-templates select="diffreport/diff/node()"/>
	</xsl:template>

	<xsl:template match="@*|node()">
		<xsl:copy>
			<xsl:apply-templates select="@*|node()"/>

			<xsl:if test="not(string(.)) and not(node())">
				<xsl:comment></xsl:comment>
			</xsl:if>
		</xsl:copy>
	</xsl:template>

	<xsl:template match="img">
		<img>
			<xsl:copy-of select="@*"/>
			<xsl:if test="@changeType='diff-removed-image' or @changeType='diff-added-image'">
				<xsl:attribute name="onLoad">updateOverlays()</xsl:attribute>
				<xsl:attribute name="onError">updateOverlays()</xsl:attribute>
				<xsl:attribute name="onAbort">updateOverlays()</xsl:attribute>
			</xsl:if>
		</img>
	</xsl:template>
</xsl:stylesheet>