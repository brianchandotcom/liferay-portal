<#--
Web content templates are used to lay out the fields defined in a web
content structure.
Please use the left panel to quickly add commonly used variables.
Autocomplete is also available and can be invoked by typing "${".
-->

<#assign
	normalizer = serviceLocator.findService("com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil")>

<#assign
	browserSnifferCaseOne = serviceLocator.findService("com.liferay.portal.kernel.servlet.BrowserSnifferUtil")
/>

<#assign
	browserSnifferCaseTwo = serviceLocator.findService("com.liferay.portal.kernel.servlet.BrowserSnifferUtil")>

<#assign
	browserSnifferCaseThree = serviceLocator.findService("com.liferay.portal.kernel.servlet.BrowserSnifferUtil")
	variableACaseThree = "variableACaseThree"
	assetEntryLocalService = serviceLocator.findService("com.liferay.asset.kernel.service.AssetEntryLocalService")
/>

<#assign
	browserSnifferCaseFour = serviceLocator.findService("com.liferay.portal.kernel.servlet.BrowserSnifferUtil")
	variableBCaseFour = "variableBCaseFour"
	assetEntryLocalService = serviceLocator.findService("com.liferay.asset.kernel.service.AssetEntryLocalService")>

${browserSnifferCaseOne}
${browserSnifferCaseTwo}
${browserSnifferCaseThree}
${browserSnifferCaseFour}