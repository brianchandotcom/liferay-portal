<?xml version="1.0" encoding="ISO-8859-1"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<html lang="en">
		<head>
			<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" />
			<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css" />
			<meta charset="utf-8" />
			<meta content="IE=edge" http-equiv="X-UA-Compatible" />
			<meta content="initial-scale=1, width=device-width" name="viewport" />
			<style>
				body {
					padding: 10px;
				}

				th.version {
					border-top: none !important;
					font-size: 20px;
					font-weight: bold;
					height: 60px;
					vertical-align: bottom !important;
				}
			</style>


			<title>Liferay Third Party Libraries</title>
		</head>
		<body>
			<div id="container">
				<img alt="Liferay" src="data:image/png;base64,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" />

				<h1>Third Party Software List</h1>

				<table class="table table-condensed">
					<xsl:for-each select="versions/version">
						<tr>
							<th class="version" colspan="5">
								<xsl:value-of select="@name" />
							</th>
						</tr>

						<xsl:choose>
							<xsl:when test="libraries">
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
							</xsl:when>
							<xsl:otherwise>
								<tr>
									<td colspan="5">
										<i>There were no third party library changes in this version.</i>
									</td>
								</tr>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:for-each>
				</table>
			</div>

			<h4>Written Offer for Source Code</h4>

			<p>
				For binaries that you receive from Liferay that are licensed under any version of the GNU General Public License (GPL) or the GNU LGPL, you may receive a complete machine readable copy of the source code by either downloading it from the binary's website or sending a written request to:
			</p>

			<address>
				<b>Liferay, Inc.</b><br />
				Attn: Legal<br />
				1400 Montefino Ave<br />
				Diamond Bar, CA 91765<br />
			</address>

			<p>
				Your request should include: (i) the name of the covered binary, (ii) the name and version number of the Liferay product containing the covered binary, (iii) your name, (iv) your company name (if applicable) and (v) your return mailing and email address (if available).
			</p>

			<p>
				We may charge you a nominal fee to cover the cost of the media and distribution.
			</p>

			<p>
				Your request must be sent within three years of the date you received the GPL or LGPL covered code.
			</p>
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
				<xsl:choose>
					<xsl:when test="project-url">
						<a>
							<xsl:attribute name="href">
								<xsl:value-of disable-output-escaping="yes" select="project-url" />
							</xsl:attribute>
							<xsl:value-of select="project-name" />
						</a>
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="project-name" />
					</xsl:otherwise>
				</xsl:choose>
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