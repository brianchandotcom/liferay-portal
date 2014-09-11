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
					font-size: 20px;
					font-weight: bold;
					height: 60px;
					vertical-align: bottom !important;
					border-top: none !important;
				}
			</style>


			<title>Liferay Third Party Libraries</title>
		</head>
		<body>
			<div id="container">
				<img src="https://cdn.lfrs.sl/www.liferay.com/osb-community-theme/images/custom/heading.png" />

				<h1>Third Party Software List</h1>

				<table class="table table-condensed">
					<xsl:for-each select="versions/version">
						<tr class="version"><th colspan="5" class="version"><xsl:value-of select="@name" /></th></tr>

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
				For binaries that you receive from Liferay that are licensed under any version of the GNU General Public License (GPL) or the GNU LGPL, you may receive a complete machine-readable copy of the source code by either downloading it from the binary's website or sending a written request to:
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