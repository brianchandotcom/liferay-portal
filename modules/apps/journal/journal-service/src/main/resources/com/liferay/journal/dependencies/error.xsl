<?xml version="1.0"?>

<xsl:stylesheet
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
	xmlns:languageUtil="xalan://com.liferay.portal.kernel.language.LanguageUtil"
	xmlns:str="http://exslt.org/strings"
	xmlns:xalan="http://xml.apache.org/xalan"
	exclude-result-prefixes="xalan"
	extension-element-prefixes="languageUtil str xalan">

	<xsl:output method="html" omit-xml-declaration="yes" />

	<xsl:param name="companyId" />
	<xsl:param name="exception" />
	<xsl:param name="line" />
	<xsl:param name="locale" />
	<xsl:param name="script" />

	<xsl:template match="/">
		<div class="journal-template-error">
			<span class="alert alert-error">
				<xsl:value-of select="languageUtil:get($locale, 'an-error-occurred-while-processing-the-template')" />
			</span>

			<pre>
				<xsl:value-of select="$exception" />
			</pre>

			<br/>

			<div class="scroll-pane">
				<div class="inner-scroll-pane">
					<xsl:for-each select="str:split($script, '&#xa;')">
						<pre>
							<xsl:if test="$line = position()">
								<xsl:attribute name="class">
									<xsl:text>error-line</xsl:text>
								</xsl:attribute>
							</xsl:if>
							<span>
								<xsl:value-of select="position()" />
							</span><xsl:value-of select="." />
							<xsl:text>&#160;</xsl:text>
						</pre>
					</xsl:for-each>
				</div>
			</div>
		</div>
	</xsl:template>
</xsl:stylesheet>