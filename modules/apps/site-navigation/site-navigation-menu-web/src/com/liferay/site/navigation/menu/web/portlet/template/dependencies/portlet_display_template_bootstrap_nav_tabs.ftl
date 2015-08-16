<#assign rootNavigationItems = navItem.fromLayouts(request, themeDisplay.getLayouts(), null) />

<ul aria-label="#language ("site-pages")" class="nav nav-tabs nav-justified" role="menubar">
	<#list rootNavigationItems as rootNavigationItem>
		<#if rootNavigationItem.isBrowsable() || rootNavigationItem.hasBrowsableChildren() >
			<#assign nav_item_attr_has_popup = "" />
			<#assign nav_item_attr_selected = "" />
			<#assign nav_item_caret = "" />
			<#assign nav_item_css_class = "" />
			<#assign nav_item_role = "presentation" />

			<#if rootNavigationItem.isSelected() >
				<#assign nav_item_attr_selected = "aria-selected='true'" />
				<#assign nav_item_css_class = "active" />
			</#if>

			<#if rootNavigationItem.hasBrowsableChildren() >
				<#assign nav_item_attr_has_popup = "aria-expanded='false' aria-haspopup='true' data-toggle='dropdown'" />
				<#assign nav_item_caret = "<span class='caret'></span>" />
				<#assign nav_item_css_class = nav_item_css_class + " dropdown-toggle" />
				<#assign nav_item_role = "button" />
			</#if>

			<#if rootNavigationItem.isBrowsable() >
				<#assign nav_item_href_link = "href='${rootNavigationItem.getURL()}' ${rootNavigationItem.getTarget()}" />
			</#if>

			<li class="${nav_item_css_class}" id="layout_${rootNavigationItem.getLayoutId()}" ${nav_item_attr_selected} role="${nav_item_role}">
				<a aria-labelledby="layout_${rootNavigationItem.getLayoutId()}" ${nav_item_attr_has_popup} ${nav_item_href_link}><span>${rootNavigationItem.getName()} ${nav_item_caret}</span></a>

				<#if rootNavigationItem.hasBrowsableChildren() >
					<ul class="child-menu dropdown-menu" role="menu">
						<#list rootNavigationItem.getBrowsableChildren() as childNavigationItem>
							<#assign nav_child_attr_selected = "" />
							<#assign nav_child_css_class = "" />

							<#if childNavigationItem.isSelected() >
								<#assign nav_child_attr_selected = "aria-selected='true'" />
								<#assign nav_child_css_class = "active" />
							</#if>

							<li class="${nav_child_css_class}" id="layout_${childNavigationItem.getLayoutId()}" ${nav_child_attr_selected} role="presentation">
								<a aria-labelledby="layout_${childNavigationItem.getLayoutId()}" href="${childNavigationItem.getURL()}" ${childNavigationItem.getTarget()} role="menuitem">${childNavigationItem.getName()}</a>
							</li>
						</#list>
					</ul>
				</#if>
			</li>
		</#if>
	</#list>
</ul>