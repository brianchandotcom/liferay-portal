<#macro buildNavigation
	branchNavItems
	cssClass
	displayDepth
	includeAllChildNavItems
	navItemLevel
	navItems
>
	<#if navItems?has_content && ((displayDepth == 0) || (navItemLevel <= displayDepth))>
		<ul class="${cssClass} level-${navItemLevel}">
			<#list navItems as navItem>
				<#assign
					nav_item_css_class = "lfr-nav-item"
				/>

				<#if includeAllChildNavItems || navItem.isInNavigation(branchNavItems)>
					<#assign nav_item_css_class = "${nav_item_css_class} open" />
				</#if>

				<#if navItem.isSelected()>
					<#assign
						nav_item_css_class = "${nav_item_css_class} selected active"
					/>
				</#if>

				<li class="${nav_item_css_class}">
					<#if navItem.isBrowsable()>
						<a class="${nav_item_css_class}" href="${navItem.getRegularURL()!""}" ${navItem.getTarget()}>${navItem.getName()}</a>
					<#else>
						${navItem.getName()}
					</#if>

					<#if includeAllChildNavItems || navItem.isInNavigation(branchNavItems)>
						<@buildNavigation
							branchNavItems=branchNavItems
							cssClass=cssClass
							displayDepth=displayDepth
							includeAllChildNavItems=includeAllChildNavItems
							navItemLevel=(navItemLevel + 1)
							navItems=navItem.getChildren()
						/>
					</#if>
				</li>
			</#list>
		</ul>
	</#if>
</#macro>