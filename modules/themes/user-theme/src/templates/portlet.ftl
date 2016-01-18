<#assign liferay_portlet = ThemeJspTaglibs["/WEB-INF/tld/liferay-portlet-ext.tld"]>
<#assign liferay_ui = ThemeJspTaglibs["/WEB-INF/tld/liferay-ui.tld"]>

<#assign portlet_display = portletDisplay>
<#assign portlet_back_url = htmlUtil.escapeHREF(portlet_display.getURLBack())>
<#assign portlet_content_css_class = "portlet-content">
<#assign portlet_display_name = htmlUtil.escape(portlet_display.getPortletDisplayName())>
<#assign portlet_display_root_portlet_id = htmlUtil.escapeAttribute(portlet_display.getRootPortletId())>
<#assign portlet_id = htmlUtil.escapeAttribute(portlet_display.getId())>
<#assign portlet_title = htmlUtil.escape(portlet_display.getTitle())>
<#assign portlet_toolbar = portlet_display.getPortletToolbar()>

<section class="portlet" id="portlet_${portlet_id}">
	<#if portlet_display.isPortletDecorate() && !portlet_display.isStateMax()>
		<header class="portlet-topper">
			<div class="portlet-title-default">
				<span class="portlet-name-text">${portlet_display_name}</span>
			</div>

			<#foreach portletTitleMenu in portlet_toolbar.getPortletTitleMenus(portlet_display_root_portlet_id, renderRequest)>
				<menu class="icon-monospaced portlet-title-menu portlet-topper-toolbar" id="portlet-title-menu_${portlet_id}_${portletTitleMenu_index}" type="toolbar">
					<@liferay_ui["menu"] menu=portletTitleMenu />
				</menu>
			</#foreach>

			<menu class="portlet-topper-toolbar" id="portlet-topper-toolbar_${portlet_id}" type="toolbar">
				<@liferay_portlet["icon-options"] />
			</menu>
		</header>

		<#assign portlet_content_css_class = portlet_content_css_class + " portlet-content-editable">
	</#if>

	<div class="${portlet_content_css_class}">
		<#if portlet_display.getPortletDecoratorId() != "barebone">
			<h2 class="portlet-title-text">${portlet_title}</h2>
		</#if>

		${portlet_display.writeContent(writer)}
	</div>
</section>