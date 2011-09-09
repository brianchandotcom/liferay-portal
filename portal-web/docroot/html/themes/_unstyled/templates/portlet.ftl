<#assign portlet_display = portletDisplay />

<#assign portlet_id = htmlUtil.escapeAttribute(portlet_display.getId()) />
<#assign portlet_title = portlet_display.getTitle() />
<#assign portlet_content = portlet_display.getContent() />
<#assign portlet_back_url = htmlUtil.escapeAttribute(portlet_display.getURLBack()) />

<section class="portlet" id="portlet_${portlet_id}">
	<header class="portlet-topper">
		<h1 class="portlet-title">
			${theme.iconPortlet()} <span id="portlet_title_text_$portlet_id" class="portlet-title-text">${portlet_title}</span>
		</h1>

		<menu class="portlet-topper-toolbar" id="portlet-topper-toolbar_${portlet_id}" type="toolbar">
			<#if portlet_display.isShowBackIcon()>
				<a href="${portlet_back_url}" class="portlet-icon-back"><@liferay.language key="return-to-full-page" /></a>
			<#else>
				${theme.iconOptions()}
				${theme.iconMinimize()}
				${theme.iconMaximize()}
				${theme.iconClose()}
			</#if>
		</menu>
	</header>

	<div class="portlet-content">
		${portlet_content}
	</div>
</section>