<#assign liferay_ui=PortalJspTagLibs["/WEB-INF/tld/liferay-ui.tld"]>

<#assign portlet_display = portletDisplay />

<#assign portlet_id = htmlUtil.escapeAttribute(portlet_display.getId()) />
<#assign portlet_title = htmlUtil.escape(portlet_display.getTitle()) />
<#assign portlet_back_url = htmlUtil.escapeHREF(portlet_display.getURLBack()) />

<#if portlet_display.getPortletToolbar()??>
	<#assign portlet_toolbar = portlet_display.getPortletToolbar() />
</#if>

<section class="portlet" id="portlet_${portlet_id}">
	<header class="portlet-topper">
		<h1 class="portlet-title">
			<span class="portlet-title-text">${portlet_title}</span>
		</h1>

		<#if portlet_toolbar??>
			<menu class="portlet-topper-toolbar add-content" id="portlet-topper-toolbar-add-content_${portlet_id}" type="toolbar">
				<@liferay_ui["portlet-toolbar-menu"] portletToolbarMenuItems=portlet_toolbar.getContentAdditionMenuItems(portlet_id, renderRequest) />
			</menu>
		</#if>

		<menu class="portlet-topper-toolbar" id="portlet-topper-toolbar_${portlet_id}" type="toolbar">
			<#if portlet_display.isShowBackIcon()>
				<a href="${portlet_back_url}" class="portlet-icon-back"><@liferay.language key="return-to-full-page" /></a>
			<#else>
				${theme.portletIconOptions()}
			</#if>
		</menu>
	</header>

	<div class="portlet-content">
		${portlet_display.writeContent(writer)}
	</div>
</section>