<#include "${templatesPath}/NAVIGATION-MACRO-FTL" />

<#if entries?has_content>
	<#assign rootNavigationItem = entries?first />

	<div class="nav-menu nav-menu-style-${bulletStyle}">
		<@displayChildNavigation childLayoutLevel=1 childNavigationItems=rootNavigationItem.getChildren() includeAllChildEntries=true />
	</div>
</#if>