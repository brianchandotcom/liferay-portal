<#assign liferay_ui=PortalJspTagLibs["/WEB-INF/tld/liferay-ui.tld"]>

<#assign portlet_display = portletDisplay />

<#assign portlet_id = htmlUtil.escapeAttribute(portlet_display.getId()) />
<#assign portlet_name = htmlUtil.escape(portalUtil.getPortletTitle(renderRequest)) />
<#assign portlet_title = htmlUtil.escape(portlet_display.getTitle()) />
<#assign portlet_back_url = htmlUtil.escapeHREF(portlet_display.getURLBack()) />

<#assign portlet_toolbar = portlet_display.getPortletToolbar() />

<section class="portlet" id="portlet_${portlet_id}">
	<header class="portlet-topper">
		<div class="portlet-title-default">
			<span class="portlet-name-text">${portlet_name}</span>
		</div>

		<#list portlet_toolbar.getPortletTitleMenus(portlet_id, renderRequest) as portletTitleMenu>
			<menu class="portlet-title-menu portlet-topper-toolbar" id="portlet-title-menu_${portlet_id}_${portletTitleMenu_index + 1}" type="toolbar">
				<@liferay_ui["menu"] menu=portletTitleMenu />
			</menu>
		</#list>

		<menu class="portlet-topper-toolbar" id="portlet-topper-toolbar_${portlet_id}" type="toolbar">
			<#if portlet_display.isShowBackIcon()>
				<a class="portlet-icon-back" href="${portlet_back_url}"><@liferay.language key="return-to-full-page" /></a>
			<#else>
				${theme.portletIconOptions()}
			</#if>
		</menu>
	</header>

	<div class="portlet-content">
		<h2 class="portlet-title-text">${portlet_title}</h2>

		${portlet_display.writeContent(writer)}
	</div>
</section>