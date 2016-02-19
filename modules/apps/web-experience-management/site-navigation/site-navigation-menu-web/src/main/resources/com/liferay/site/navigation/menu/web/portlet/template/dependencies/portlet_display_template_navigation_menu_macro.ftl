<#macro buildNavigation
	branchNavItems
	cssClass
	includeAllChildNavItems
	navItemLevel
	navItems
>
	<#if navItems?has_content>
		<ul class="${cssClass} level-${navItemLevel}">
			<#list navItems as navItem>
				<#assign nav_item_attr_selected = "" />
				<#assign nav_item_css_class = "lfr-nav-item" />

				<#if includeAllChildNavItems || navItem.isInNavigation(branchNavItems)>
					<#assign nav_item_css_class = "${nav_item_css_class} open" />
				</#if>

				<#if navItem.isSelected()>
					<#assign nav_item_attr_selected = "aria-selected='true'" />
					<#assign nav_item_css_class = "${nav_item_css_class} selected active" />
				</#if>

				<li class="${nav_item_css_class}" ${nav_item_attr_selected}>
					<#if navItem.isBrowsable()>
						<a class="${nav_item_css_class}" href="${navItem.getRegularURL()!""}" ${navItem.getTarget()}>${htmlUtil.escape(navItem.getName())}</a>
					<#else>
						${htmlUtil.escape(navItem.getName())}
					</#if>

					<#if includeAllChildNavItems || navItem.isInNavigation(branchNavItems)>
						<@buildNavigation
							branchNavItems=branchNavItems
							cssClass=cssClass
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