<#include "${templatesPath}/NAVIGATION-MACRO-FTL" />

<#if entries?? && entries?has_content && (entries?size >= 2) >
	<#assign rootNavigationItem = entries[1] />

	<div class="nav-menu nav-menu-style-${bulletStyle}">
		<h2>
			<a href="${rootNavigationItem.getRegularURL()}" ${rootNavigationItem.getTarget()}>${rootNavigationItem.getName()}</a>
		</h2>

		<@displayChildNavigation childLayoutLevel=2 childNavigationItems=rootNavigationItem.getChildren() includeAllChildEntries=false />
	</div>
</#if>