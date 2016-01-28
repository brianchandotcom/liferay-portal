<#if entries?has_content>
	<@liferay_aui.layout>
		<#list entries as entry>
		    <@liferay_aui.column columnWidth=25>
				<div class="results-header">
					<h3>
						<#assign layoutURL = portalUtil.getLayoutURL(entry, themeDisplay)>

						<a href="${layoutURL}">${entry.getName(locale)}</a>
					</h3>
				</div>

				<#assign pages = entry.getChildren()>

				<@displayPages pages=pages />
		    </@liferay_aui.column>
		</#list>
	</@liferay_aui.layout>
</#if>

<#macro displayPages
	pages
>
	<#if pages?has_content>
		<ul class="child-pages">
			<#list pages as page>
				<li>
					<#assign pageLayoutURL = portalUtil.getLayoutURL(page, themeDisplay)>

					<a href="${pageLayoutURL}">${page.getName(locale)}</a>

					<#assign childPages = page.getChildren()>

					<@displayPages pages=childPages />
				</li>
			</#list>
		</ul>
	</#if>
</#macro>