<#assign rootNavigationItems = navItem.fromLayouts(request, themeDisplay.getLayouts(), null) />

<ul aria-label="#language ("site-pages")" class="nav navbar-nav navbar-right navbar-site" role="menubar">
	<h1 class="hide-accessible">#language ("navigation")</h1>

	<#list rootNavigationItems as rootNavigationItem>
		<#if rootNavigationItem.isBrowsable() || rootNavigationItem.hasBrowsableChildren() >
			<#assign nav_item_attr_has_popup = "" />
			<#assign nav_item_attr_selected = "" />
			<#assign nav_item_caret = "" />
			<#assign nav_item_css_class = "" />
			<#assign nav_item_href_link = "" />
			<#assign nav_item_link_css_class = "" />

			<#if rootNavigationItem.hasBrowsableChildren() >
				<#assign nav_item_attr_has_popup = "aria-haspopup='true'" />
				<#assign nav_item_caret = '<span class="lfr-nav-child-toggle"><i class="icon-caret-down"></i></span>' />
				<#assign nav_item_css_class = nav_item_css_class + " dropdown" />
				<#assign nav_item_link_css_class = "dropdown-toggle" />
			</#if>

			<#if rootNavigationItem.isBrowsable() >
				<#assign nav_item_href_link = "href='${rootNavigationItem.getURL()}' ${rootNavigationItem.getTarget()}" />
			</#if>

			<#if rootNavigationItem.isSelected() >
				<#assign nav_item_attr_selected = "aria-selected='true'" />
				<#assign nav_item_css_class = nav_item_css_class + " selected active" />
			</#if>

			<li class="${nav_item_css_class}" id="layout_${rootNavigationItem.getLayoutId()}" ${nav_item_attr_selected} role="presentation">
				<a aria-labelledby="layout_${rootNavigationItem.getLayoutId()}" ${nav_item_attr_has_popup} class="${nav_item_link_css_class}" ${nav_item_href_link} role="menuitem">
					<span>${rootNavigationItem.getName()} ${nav_item_caret}</span>
				</a>

				<#if rootNavigationItem.hasBrowsableChildren() >
					<ul aria-expanded="false" class="child-menu dropdown-menu" role="menu">
						<#list rootNavigationItem.getBrowsableChildren() as childNavigationItem>
							<#assign nav_child_attr_selected = "" />
							<#assign nav_child_css_class = "lfr-nav-item" />
							<#assign nav_child_href_link = "" />

							<#if childNavigationItem.isSelected() >
								<#assign nav_child_attr_selected = "aria-selected='true'" />
								<#assign nav_child_css_class = "active" />
							</#if>

							<li class="${nav_child_css_class}" id="layout_${childNavigationItem.getLayoutId()}" ${nav_child_attr_selected} role="presentation">
								<a aria-labelledby="layout_${childNavigationItem.getLayoutId()}" href="${childNavigationItem.getURL()!""}" ${childNavigationItem.getTarget()} role="menuitem">${childNavigationItem.getName()}</a>
							</li>
						</#list>
					</ul>
				</#if>
			</li>
		</#if>
	</#list>
</ul>