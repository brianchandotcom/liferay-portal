<#include init />

<section class="portlet" id="portlet_${htmlUtil.escapeAttribute(portletDisplay.getId())}">
	${portletDisplay.writeContent(writer)}
</section>

<#if portletDisplay.isStateMax()>
	<@liferay.control_menu
		original_portlet_display=portletDisplay.clone()
		original_portlet_id=portletDisplay.getRootPortletId()
		original_portlet_request=renderRequest
	/>
</#if>