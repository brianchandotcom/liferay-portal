<#assign
	portlet_display = portletDisplay
	portlet_back_url = htmlUtil.escapeHREF(portlet_display.getURLBack())
	portlet_content_css_class = "portlet-content"
	portlet_display_name = htmlUtil.escape(portlet_display.getPortletDisplayName())
	portlet_display_root_portlet_id = htmlUtil.escapeAttribute(portlet_display.getRootPortletId())
	portlet_id = htmlUtil.escapeAttribute(portlet_display.getId())
	portlet_title = htmlUtil.escape(portlet_display.getTitle())
/>

<section class="portlet" id="portlet_${portlet_id}">
	<#if portlet_display.isPortletDecorate() && !portlet_display.isStateMax() && portlet_display.getPortletConfigurationIconMenu()?? && portlet_display.getPortletToolbar()??>
		<#assign
			portlet_configuration_icon_menu = portlet_display.getPortletConfigurationIconMenu()
			portlet_toolbar = portlet_display.getPortletToolbar()

			portlet_configuration_icons = portlet_configuration_icon_menu.getPortletConfigurationIcons(portlet_display_root_portlet_id, renderRequest, renderResponse)

			portlet_title_menus = portlet_toolbar.getPortletTitleMenus(portlet_display_root_portlet_id, renderRequest, renderResponse)
		/>

		<#if (portlet_configuration_icons?has_content || portlet_title_menus?has_content)>
			<header class="portlet-topper">
				<svg width="18" height="10" fill="none" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" clip-rule="evenodd" d="M2 0H0v2h2V0zM0 4h2v2H0V4zm4 0h2v2H4V4zm6 0H8v2h2V4zm2 0h2v2h-2V4zm6 0h-2v2h2V4zM0 8h2v2H0V8zm6 0H4v2h2V8zm2 0h2v2H8V8zm6 0h-2v2h2V8zm2 0h2v2h-2V8zM4 0h2v2H4V0zm6 0H8v2h2V0zm2 0h2v2h-2V0zm6 0h-2v2h2V0z" fill="#fff"/></svg>
			</header>

			<div class="portlet-title-default">
				${portlet_display_name}
			</div>

			<div class="portlet-topper-toolbar-wrapper">
				<#foreach portletTitleMenu in portlet_title_menus>
					<menu class="portlet-topper-toolbar" id="portlet-title-menu_${portlet_id}_${portletTitleMenu_index}" type="toolbar">
						<@liferay_ui["menu"] menu=portletTitleMenu />
					</menu>
				</#foreach>

				<#if portlet_configuration_icons?has_content>
					<menu class="portlet-topper-toolbar" id="portlet-topper-toolbar_${portlet_id}" type="toolbar">
						<@liferay_portlet["icon-options"] portletConfigurationIcons=portlet_configuration_icons />
					</menu>
				</#if>
			</div>

			<#assign portlet_content_css_class = portlet_content_css_class + " portlet-content-editable" />
		</#if>
	</#if>

	<div class="${portlet_content_css_class}">
		<#if portlet_display.isShowBackIcon()>
			<a class="icon-monospaced portlet-icon-back text-default" href="${portlet_back_url}" title="<@liferay.language key="return-to-full-page" />">
				<@liferay_ui["icon"]
					icon="angle-left"
					markupView="lexicon"
				/>
			</a>
		</#if>

		<#if validator.isNotNull(portlet_display.getPortletDecoratorId()) && !stringUtil.equals(portlet_display.getPortletDecoratorId(), "barebone")>
			<div class="portlet-header">
				<h2 class="portlet-title-text">${portlet_title}</h2>

				<div class="portlet-header-tools">
					<@liferay_util["dynamic-include"] key="portlet_header_${portlet_display_root_portlet_id}" />
				</div>
			</div>
		</#if>

		${portlet_display.writeContent(writer)}
	</div>
</section>