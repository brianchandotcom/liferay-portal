<#include "${templatesPath}/NAVIGATION-MACRO-FTL" />

<#if rootNavigationItems?has_content>
	<#assign layoutLevel = 0 />

	<div class="nav-menu nav-menu-style-${bulletStyle}">
		<ul class="layouts level-${layoutLevel}">
			<#assign rootNavigationItems = navItem.fromLayouts(request, themeDisplay.getLayouts(), null) />

			<#list rootNavigationItems as rootNavigationItem>
				<li class="open">
					<#if rootNavigationItem.isBrowsable()>
						<a href="${rootNavigationItem.getRegularURL()!""}" ${rootNavigationItem.getTarget()}>${htmlUtil.escape(rootNavigationItem.getName())}</a>
					<#else>
						${htmlUtil.escape(rootNavigationItem.getName())}
					</#if>

					<#if rootNavigationItem.isInNavigation(entries) >
						<@displayChildNavigation
							childLayoutLevel=(layoutLevel + 1)
							childNavigationItems=rootNavigationItem.getChildren()
							includeAllChildEntries=false
						/>
					</#if>
				</li>
			</#list>
		</ul>
	</div>
</#if>