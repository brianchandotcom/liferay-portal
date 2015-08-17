<#macro displayChildNavigation
	childLayoutLevel
	childNavigationItems
	includeAllChildEntries
>
	<#if childNavigationItems?has_content>
		<ul class="layouts level-${childLayoutLevel}">
			<#list childNavigationItems as childNavigationItem>
				<li class="open">
					<a

					<#if childNavigationItems.isBrowsable()>
						href="${childNavigationItem.getRegularURL()!""} ${rootNavigationItem.getTarget()}"
					</#if>

					>${htmlUtil.escape(childNavigationItem.getName())}</a>

					<#if includeAllChildEntries || childNavigationItem.isInNavigation(entries) >
						<@displayChildNavigation childLayoutLevel=(childLayoutLevel + 1) childNavigationItems=childNavigationItem.getChildren() includeAllChildEntries=includeAllChildEntries/>
					</#if>
				</li>
			</#list>
		</ul>
	</#if>
</#macro>