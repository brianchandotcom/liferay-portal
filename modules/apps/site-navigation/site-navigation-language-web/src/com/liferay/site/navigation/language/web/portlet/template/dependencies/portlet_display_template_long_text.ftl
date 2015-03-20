<#assign aui = taglibLiferayHash["/WEB-INF/tld/aui.tld"] />

<#if entries?has_content>
	<#list entries as entry>
		<@aui["a"]
			cssClass="taglib-language-list-text"
			href=entry.getURL()
			label=entry.getLongDisplayName()
			lang=entry.getW3cLanguageId()
		/>
	</#list>
</#if>