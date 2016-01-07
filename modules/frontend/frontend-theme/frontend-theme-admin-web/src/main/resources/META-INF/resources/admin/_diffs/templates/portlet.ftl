<#assign liferay_portlet=PortalJspTagLibs["/WEB-INF/tld/liferay-portlet-ext.tld"]>
<#assign liferay_ui=PortalJspTagLibs["/WEB-INF/tld/liferay-ui.tld"]>

<#assign portlet_display = portletDisplay>

<#assign portlet_icon_options_view = "lexicon">

<#assign portlet_id = htmlUtil.escapeAttribute(portlet_display.getId())>
<#assign portlet_title = htmlUtil.escape(portlet_display.getTitle())>
<#assign portlet_description = portlet_display.getDescription()!>
<#assign portlet_back_url = htmlUtil.escapeHREF(portlet_display.getURLBack())>
<#assign valid_portlet_description = validator.isNotNull(portlet_description) && portlet_description?contains("javax.portlet.description")>

<#assign portlet_topper_css_class = "portlet-topper">

<section class="portlet" id="portlet_${portlet_id}">
	<header class="header-toolbar header-toolbar-default" data-qa-id="header">
		<div class="container-fluid-1280">
			<div class="toolbar-group">
				<div class="toolbar-group-content">
					<a class="icon-monospaced sidenav-toggler" data-content="body" data-qa-id="productMenu" data-target="#sidenavSliderId,#wrapper" data-toggle="sidenav" data-type="fixed-push" data-type-mobile="fixed" data-use-delegate="true" href="#sidenavSliderId" id="sidenavToggleId">
						<div class="toast-animation">
							<div class="pm"></div>
							<div class="cn"></div>
						</div>
					</a>
				</div>

				<#if portlet_display.isShowBackIcon()>
					<div class="toolbar-group-content">
						<a href="${portlet_back_url}"><span class="icon-angle-left icon-monospaced"></span></a>
					</div>
				</#if>
			</div>

			<div class="toolbar-group-right">
				<div class="toolbar-group-content" data-qa-id="headerOptions">
					<@liferay_portlet["icon-options"]
						direction="right"
						markupView="lexicon"
					/>
				</div>
			</div>

			<div class="text-center toolbar-group-expand-text">
				<span class="header-toolbar-title" title="${portlet_title}">${portlet_title}</span>

				<#if valid_portlet_description>
					<@liferay_ui["icon-help"] message=portlet_description />
				</#if>
			</div>
		</div>
	</header>

	${portlet_display.writeContent(writer)}
</section>