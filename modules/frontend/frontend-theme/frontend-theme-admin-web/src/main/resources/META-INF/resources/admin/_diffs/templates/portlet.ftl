<#include init />

<#assign portlet_display = portletDisplay>

<#assign portlet_id = htmlUtil.escapeAttribute(portlet_display.getId())>

<section class="portlet" id="portlet_${portlet_id}">
	${portlet_display.writeContent(writer)}
</section>

<#if portletDisplay.isStateMax()>
	<@liferay.control_menu
		original_portlet_display=portletDisplay.clone()
		original_portlet_id=portlet_display.getRootPortletId()
		original_portlet_request=renderRequest
	/>
</#if>