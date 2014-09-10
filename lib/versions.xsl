<?xml version="1.0" encoding="ISO-8859-1"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<html lang="en">
		<head>
			<meta charset="utf-8" />
			<meta http-equiv="X-UA-Compatible" content="IE=edge" />
			<meta name="viewport" content="width=device-width, initial-scale=1" />
			<title>Liferay Third-party Libraries</title>
			<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" />
			<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css" />
		</head>
		<body>
		<div id="container">
		<img src="https://cdn.lfrs.sl/www.liferay.com/osb-community-theme/images/custom/heading.png" />

		<h1>Third-Party Software List</h1>

		<xsl:for-each select="versions/version">
			<h2><xsl:value-of select="@name" /></h2>

			<xsl:choose>
				<xsl:when test="libraries">
					<table class="table table-striped table-condensed">
					<tr>
						<th>
							File Name
						</th>
						<th>
							Version
						</th>
						<th>
							Project
						</th>
						<th>
							License
						</th>
						<th>
							Comments
						</th>
					</tr>

					<xsl:apply-templates />

					</table>
				</xsl:when>
				<xsl:otherwise>
					<i>There were no third-party library changes in this version.</i>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:for-each>
		</div>
		</body>

		</html>
	</xsl:template>

	<xsl:template match="library">
		<tr>
			<td nowrap="nowrap">
				<xsl:value-of select="file-name" />
			</td>
			<td nowrap="nowrap">
				<xsl:value-of select="version" />
			</td>
			<td nowrap="nowrap">
				<a>
					<xsl:attribute name="href">
						<xsl:value-of disable-output-escaping="yes" select="project-url" />
					</xsl:attribute>
					<xsl:value-of select="project-name" />
				</a>
			</td>
			<td nowrap="nowrap">
				<xsl:apply-templates select="licenses/license" />
			</td>
			<td>
				<xsl:value-of select="comments" />
			</td>
		</tr>
	</xsl:template>

	<xsl:template match="licenses/license">
		<a>
			<xsl:attribute name="href">
				<xsl:value-of disable-output-escaping="yes" select="license-url" />
			</xsl:attribute>
			<xsl:value-of select="license-name" />
		</a>

		<xsl:if test="copyright-notice">
			<br />
			<xsl:variable name="copyrightNotice" select="copyright-notice" />

			<xsl:copy-of select="$copyrightNotice" />
		</xsl:if>

		<br />
	</xsl:template>
</xsl:stylesheet>