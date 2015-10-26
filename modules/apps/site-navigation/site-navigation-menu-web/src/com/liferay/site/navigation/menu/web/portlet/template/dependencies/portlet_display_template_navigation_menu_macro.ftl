<#macro displayChildNavigation
	childLayoutLevel
	childNavigationItems
	includeAllChildEntries
>
	<#if childNavigationItems?has_content>
		<ul class="layouts level-${childLayoutLevel}">
			<#list childNavigationItems as childNavigationItem>
				<li class="open">
					<#if childNavigationItem.isBrowsable()>
						<a href="${childNavigationItem.getRegularURL()!""}" ${childNavigationItem.getTarget()}>${htmlUtil.escape(childNavigationItem.getName())}</a>
					<#else>
						${htmlUtil.escape(childNavigationItem.getName())}
					</#if>

					<#if includeAllChildEntries || childNavigationItem.isInNavigation(entries) >
						<@displayChildNavigation
							childLayoutLevel=(childLayoutLevel + 1)
							childNavigationItems=childNavigationItem.getChildren()
							includeAllChildEntries=includeAllChildEntries
						/>
					</#if>
				</li>
			</#list>
		</ul>
	</#if>
</#macro>