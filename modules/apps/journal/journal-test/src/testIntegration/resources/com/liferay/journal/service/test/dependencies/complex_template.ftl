<p>Web Content Render</p>

<#if repeatableImage.getSiblings()?has_content>
	<#list repeatableImage.getSiblings() as cur_repeatableImage>
		<#if (cur_repeatableImage.getData())?? && cur_repeatableImage.getData() != "">
			<img alt="${cur_repeatableImage.getAttribute("alt")}" data-fileentryid="${cur_repeatableImage.getAttribute("fileEntryId")}" src="${cur_repeatableImage.getData()}" />
		</#if>
	</#list>
</#if>