<#include init />

<#if portletDisplay.isStateMax()>
	<@liferay.control_menu />

	<div id="main-content" role="main">
		<@displayPortlet/>
	</div>
<#else>
	<@displayPortlet/>
</#if>

<#macro displayPortlet>
	<section class="portlet" id="portlet_${htmlUtil.escapeAttribute(portletDisplay.getId())}">
		${portletDisplay.writeContent(writer)}
	</section>
</#macro>