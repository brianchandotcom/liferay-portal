<#assign aui = taglibLiferayHash["/WEB-INF/tld/aui.tld"] />
<#assign liferay_portlet = taglibLiferayHash["/WEB-INF/tld/liferay-portlet.tld"] />
<#assign liferay_ui = taglibLiferayHash["/WEB-INF/tld/liferay-ui.tld"] />

<#if entries?has_content>
	<@aui.layout>
		<#list entries as entry>
		    <@aui.column columnWith="25">
				<#assign layout_url = portalUtil.getLayoutURL(entry, themeDisplay) />

				<div class="results-header">
					<h3>
						<a href="${layout_url}">${entry.getName(locale)}</a>
					</h3>
				</div>

				<#assign pages = entry.getChildren() />

				<@page_tree pages=pages />
		    </@aui.column>
		</#list>
	</@aui.layout>
</#if>

<#macro page_tree pages>
	<#if pages?has_content>
		<ul class="child-pages">
			<#list pages as page>
				<#assign page_layout_url = portalUtil.getLayoutURL(page, themeDisplay) />

				<li>
					<a href="${page_layout_url}">${page.getName(locale)}</a>

					<#assign child_pages = page.getChildren() />

					<@page_tree pages=child_pages />
				</li>
			</#list>
		</ul>
	</#if>
</#macro>