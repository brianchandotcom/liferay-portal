<#assign aui = taglibLiferayHash["/WEB-INF/tld/liferay-aui.tld"] />

<#if entries?has_content>
	<@aui.layout>
		<#list entries as entry>
		    <@aui.column columnWidth=25>
				<div class="results-header">
					<h3>
						<a

						<#assign layoutType = entry.getLayoutType()>

						<#if layoutType.isBrowsable()>
							href="${portalUtil.getLayoutURL(entry, themeDisplay)}"
						</#if>

						>${entry.getName(locale)}</a>
					</h3>
				</div>

				<@displayPages pages=entry.getChildren() />
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
					<a

					<#assign pageType = page.getLayoutType()>

					<#if pageType.isBrowsable()>
						href="${portalUtil.getLayoutURL(page, themeDisplay)}"
					</#if>

					>${page.getName(locale)}</a>

					<@displayPages pages=page.getChildren() />
				</li>
			</#list>
		</ul>
	</#if>
</#macro>