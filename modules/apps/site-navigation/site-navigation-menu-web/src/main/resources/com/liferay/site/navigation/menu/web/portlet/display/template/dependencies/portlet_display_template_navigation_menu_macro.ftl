<#macro buildChildrenNavItems
	displayDepth
	navItem
	navItemLevel = 2
>
	<#assign
		portletDisplay = themeDisplay.getPortletDisplay()
	/>

	<#list navItem.getChildren() as childNavigationItem>
		<#assign
			nav_child_css_class = ""
		/>

		<#if !childNavigationItem.isChildSelected() && childNavigationItem.isSelected()>
			<#assign
				nav_child_css_class = "active selected"
			/>
		</#if>

		<li class="${nav_child_css_class}" id="layout_${portletDisplay.getId()}_${childNavigationItem.getLayoutId()}" role="presentation">
			<#if childNavigationItem.isBrowsable()>
				<a class="dropdown-item" href="${childNavigationItem.getURL()}" ${childNavigationItem.getTarget()} role="menuitem">${childNavigationItem.getName()}</a>
			<#else>
				<span class="dropdown-item font-weight-semi-bold navigation-menu__submenu">${childNavigationItem.getName()}</span>
			</#if>
		</li>

		<#if childNavigationItem.hasBrowsableChildren() && ((displayDepth == 0) || (navItemLevel < displayDepth))>
			<ul class="list-unstyled pl-3">
				<@buildChildrenNavItems
					displayDepth=displayDepth
					navItem=childNavigationItem
					navItemLevel=(navItemLevel + 1)
				/>
			</ul>
		</#if>
	</#list>
</#macro>

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

				<#if !navItem.isChildSelected() && navItem.isSelected()>
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