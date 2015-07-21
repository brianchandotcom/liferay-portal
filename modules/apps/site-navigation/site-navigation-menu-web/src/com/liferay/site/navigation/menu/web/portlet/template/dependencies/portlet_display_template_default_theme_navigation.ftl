<#assign liferay_theme = taglibLiferayHash["/WEB-INF/tld/liferay-theme.tld"] />

<#assign nav_css_class = "sort-pages modify-pages navbar site-navigation" />
<#assign nav_items = navItem.fromLayouts(request, themeDisplay.getLayouts(), null) />

<nav class="${nav_css_class}" id="navigation" role="navigation">
	<h1 class="hide-accessible"><@liferay.language key="navigation" /></h1>

	<ul aria-label="<@liferay.language key="site-pages" />" role="menubar" class="nav navbar-nav">
	<#list nav_items as nav_item>
		<#assign nav_item_attr_has_popup = "" />
		<#assign nav_item_attr_selected = "" />
		<#assign nav_item_css_class = "" />

		<#if nav_item.isSelected()>
			<#assign nav_item_attr_has_popup = "aria-haspopup='true'" />
			<#assign nav_item_attr_selected = "aria-selected='true'" />
			<#assign nav_item_css_class = "selected active" />
		</#if>

		<li ${nav_item_attr_selected} class="lfr-nav-item ${nav_item_css_class}" id="layout_${nav_item.getLayoutId()}" role="presentation">
			<a aria-labelledby="layout_${nav_item.getLayoutId()}" ${nav_item_attr_has_popup} href="${nav_item.getURL()}" ${nav_item.getTarget()} role="menuitem" class="dropdown-toggle"><@liferay_theme["layout-icon"] layout=nav_item.getLayout() /><span>${nav_item.getName()}</span></a>

			<#if nav_item.hasChildren()>
				<ul class="child-menu dropdown-menu" role="menu">
					<#list nav_item.getChildren() as nav_child>
						<#assign nav_child_attr_selected = "" />
						<#assign nav_child_css_class = "" />

						<#if nav_item.isSelected()>
							<#assign nav_child_attr_selected = "aria-selected='true'" />
							<#assign nav_child_css_class = "selected" />
						</#if>

						<li ${nav_child_attr_selected} class="lfr-nav-item ${nav_child_css_class}" id="layout_${nav_child.getLayoutId()}" role="presentation">
							<a aria-labelledby="layout_${nav_child.getLayoutId()}" href="${nav_child.getURL()}" ${nav_child.getTarget()} role="menuitem">${nav_child.getName()}</a>
						</li>
					</#list>
				</ul>
			</#if>
		</li>
	</#list>
	</ul>
</nav>