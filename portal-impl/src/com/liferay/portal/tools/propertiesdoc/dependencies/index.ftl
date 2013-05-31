<html>
	<head>
		<title>Liferay Portal ${lpVersion} Properties</title>
	</head>
	<body>
		<p>
			<strong>Liferay Portal ${lpVersion} Properties</strong>
		</p>

		<p>
			Here is a listing of Liferay Portal ${lpVersion} properties files and properties definition files:
		</p>

		<ul>
			<#list htmlFileNames as fileName>
				<li>
					<a href="${fileName}.html" title="${fileName}.html">${fileName}</a>
				</li>
			</#list>
		</ul>
	</body>
</html>