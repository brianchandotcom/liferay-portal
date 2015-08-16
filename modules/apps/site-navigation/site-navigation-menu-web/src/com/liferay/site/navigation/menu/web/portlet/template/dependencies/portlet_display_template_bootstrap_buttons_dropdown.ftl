<#assign rootNavigationItems = navItem.fromLayouts(request, themeDisplay.getLayouts(), null) />

<#list rootNavigationItems as rootNavigationItem>
	<#if rootNavigationItem.isBrowsable() || rootNavigationItem.hasBrowsableChildren() >
		<#assign nav_item_caret = "" />
		<#assign nav_item_css_class = "" />
		<#assign nav_item_href_link = "" />

		<#if rootNavigationItem.isSelected() >
			<#assign nav_item_css_class = "active" />
		</#if>

		<#if rootNavigationItem.hasBrowsableChildren() >
			<#assign nav_item_caret = "<button type='button' class='${nav_item_css_class} btn btn-default dropdown-toggle' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'><span class='caret'></span><span class='sr-only'>Toggle Dropdown</span></button>" />
		</#if>

		<#if rootNavigationItem.isBrowsable() >
			<#assign nav_item_href_link = "href='${rootNavigationItem.getURL()}' ${rootNavigationItem.getTarget()}" />
		</#if>

		<div class="btn-group">
			<a aria-labelledby="layout_${rootNavigationItem.getLayoutId()}" class="${nav_item_css_class} btn btn-default" ${nav_item_href_link}><span>${rootNavigationItem.getName()}</span></a>${nav_item_caret}

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
		</div>
	</#if>
</#list>