<#assign aui = taglibLiferayHash["/WEB-INF/tld/liferay-aui.tld"] />

<#if entries?has_content>
	<@aui.layout>
		<#list entries as entry>
		    <@aui.column columnWidth=25>
				<div class="results-header">
					<h3>
						<#assign layoutHrefLink = "">
						<#assign layoutType = entry.getLayoutType()>

						<#if layoutType.isBrowsable()>
							<#assign layoutHrefLink = "href='${portalUtil.getLayoutURL(entry, themeDisplay)}'">
						</#if>

						<a ${layoutHrefLink}>${entry.getName(locale)}</a>
					</h3>
				</div>

				<#assign pages = entry.getChildren()>

				<@displayPages pages = pages />
		    </@aui.column>
		</#list>
	</@aui.layout>
</#if>

<#macro displayPages
	pages
>
	<#if pages?has_content>
		<ul class="child-pages">
			<#list pages as page>
				<li>
					<#assign pageHrefLink = "">
					<#assign pageType = page.getLayoutType()>

					<#if pageType.isBrowsable()>
						<#assign pageHrefLink = "href='${portalUtil.getLayoutURL(page, themeDisplay)}'">
					</#if>

					<a ${pageHrefLink}>${page.getName(locale)}</a>

					<#assign childPages = page.getChildren()>

					<@displayPages pages = childPages />
				</li>
			</#list>
		</ul>
	</#if>
</#macro>