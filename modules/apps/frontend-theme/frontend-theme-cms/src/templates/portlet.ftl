<#assign
	portlet_display = portletDisplay

	portlet_back_url = htmlUtil.escapeHREF(portlet_display.getURLBack())
	portlet_display_root_portlet_id = htmlUtil.escapeAttribute(portlet_display.getRootPortletId())
	portlet_id = htmlUtil.escapeAttribute(portlet_display.getId())
	portlet_title = htmlUtil.escape(portlet_display.getTitle())
/>

<section class="portlet" id="portlet_${portlet_id}">
	<div class="portlet-content">
		<@liferay_util["buffer"] var="portlet_header">
			<@liferay_util["dynamic-include"] key="portlet_header_${portlet_display_root_portlet_id}" />
		</@>

		<#assign portlet_header = portlet_header?trim />

		<#if portlet_display.isShowBackIcon() || portlet_display.isShowPortletTitle() || portlet_header?has_content>
			<div class="bg-white cms-control-menu component-tbar portlet-header px-md-4 tbar top-bar">
				<div class="container-fluid">
					<ul class="tbar-nav">
						<#if portlet_display.isShowBackIcon()>
							<li class="tbar-item">
								<a class="component-action" href="${portlet_back_url}" title="<@liferay.language key="back" />">
									<@liferay_ui["icon"]
										icon="angle-left"
										markupView="lexicon"
									/>
								</a>
							</li>
						</#if>

						<#if portlet_display.isShowPortletTitle()>
							<li class="tbar-item tbar-item-expand text-left">
								<div class="tbar-section">
									<h1 class="font-weight-semi-bold m-0 tbar-section text-5 text-dark">${portlet_title}</h1>
								</div>
							</li>
						</#if>

						<#if portlet_header?has_content>
							<li class="tbar-item">
								${portlet_header}
							</li>
						</#if>
					</ul>
				</div>
			</div>
		</#if>

		${portlet_display.writeContent(writer)}
	</div>
</section>