<?xml version="1.0" encoding="UTF-8"?>

<html xsl:version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/1999/xhtml">
	<div style="background-color: #EEEEEE; font-family: Arial; font-size: 12pt;">
		<xsl:for-each select="breakfast_menu/food">
			<div style="background-color: teal; color: white; padding: 4px;">
				<span style="font-weight: bold;">
					<xsl:value-of select="name" />
				</span>

				- <xsl:value-of select="price" />
			</div>

			<div style="font-size: 10pt; margin-bottom: 1em; margin-left: 20px;">
				<xsl:value-of select="description" />

				<br />

				<span style="font-style: italic;">
					<xsl:value-of select="calories" /> calories per serving.
				</span>
			</div>
		</xsl:for-each>
	</div>
</html>