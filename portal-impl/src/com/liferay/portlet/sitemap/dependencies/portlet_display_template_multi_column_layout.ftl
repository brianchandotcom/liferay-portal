<#assign aui = PortalJspTagLibs["/WEB-INF/tld/aui.tld"] />
<#assign liferay_portlet = PortalJspTagLibs["/WEB-INF/tld/liferay-portlet.tld"] />
<#assign liferay_ui = PortalJspTagLibs["/WEB-INF/tld/liferay-ui.tld"] />

<#if entries?has_content>
	<@aui.layout>
		<#list entries as entry>
		    <@aui.column columnWith="25">
				<#assign layoutURL = portalUtil.getLayoutURL(entry, themeDisplay)>
				<div class="results-header">
					<h3>
						<a href="${layoutURL}">${entry.getName(locale)}</a>
					</h3>
				</div>
				<#assign pages = entry.getChildren()>
				<@page_tree pages = pages/>
		    </@aui.column>
		</#list>
	</@aui.layout>
</#if>

<#macro page_tree pages>
	<#if pages?has_content>
		<ul class="child-pages">
			<#list pages as page>
				<#assign pageLayoutURL = portalUtil.getLayoutURL(page, themeDisplay)>
				<li>
					<a href="${pageLayoutURL}">${page.getName(locale)}</a>
					<#assign childPages = page.getChildren()>
					<@page_tree pages=childPages/>
				</li>
			</#list>
		</ul>
	</#if>
</#macro>