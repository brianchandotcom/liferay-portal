<#assign aui = taglibLiferayHash["/WEB-INF/tld/liferay-aui.tld"] />
<#assign liferay_theme = taglibLiferayHash["/WEB-INF/tld/liferay-theme.tld"] />

<#if !entries?has_content>
	<#if preview>
		<div class="alert alert-info">
			<@liferay.language key="there-are-no-pages-to-display-for-the-current-page-level" />
		</div>
	</#if>
<#else>
	<#assign portletDisplay = themeDisplay.getPortletDisplay() />

	<#assign navbarId = "navbar_" + portletDisplay.getId() />

	<div id="${navbarId}">
		<ul aria-label="<@liferay.language key="site-pages" />" class="nav nav-justified nav-pills navbar-site" role="menubar">
			<h1 class="hide-accessible"><@liferay.language key="navigation" /></h1>

			<#assign navItems = entries />

			<#list navItems as navItem>
				<#if navItem.isBrowsable() || navItem.hasBrowsableChildren() >
					<#assign nav_item_attr_has_popup = "" />
					<#assign nav_item_attr_selected = "" />
					<#assign nav_item_caret = "" />
					<#assign nav_item_css_class = "lfr-nav-item" />
					<#assign nav_item_href_link = "" />
					<#assign nav_item_link_css_class = "" />

					<#if navItem.hasBrowsableChildren() >
						<#assign nav_item_attr_has_popup = "aria-haspopup='true'" />
						<#assign nav_item_caret = '<span class="lfr-nav-child-toggle"><i class="icon-caret-down"></i></span>' />
						<#assign nav_item_css_class = "${nav_item_css_class} dropdown" />
						<#assign nav_item_link_css_class = "dropdown-toggle" />
					</#if>

					<#if navItem.isBrowsable() >
						<#assign nav_item_href_link = "href='${navItem.getURL()}'" />
					</#if>

					<#if navItem.isSelected() >
						<#assign nav_item_attr_selected = "aria-selected='true'" />
						<#assign nav_item_css_class = "${nav_item_css_class} selected active" />
					</#if>

					<li class="${nav_item_css_class}" id="layout_${navItem.getLayoutId()}" ${nav_item_attr_selected} role="presentation">
						<a aria-labelledby="layout_${navItem.getLayoutId()}" ${nav_item_attr_has_popup} class="${nav_item_link_css_class}" ${nav_item_href_link} ${navItem.getTarget()} role="menuitem">
							<span><@liferay_theme["layout-icon"] layout=navItem.getLayout() /> ${navItem.getName()} ${nav_item_caret}</span>
						</a>

						<#if navItem.hasBrowsableChildren() >
							<ul aria-expanded="false" class="child-menu dropdown-menu" role="menu">
								<#list navItem.getBrowsableChildren() as childNavigationItem>
									<#assign nav_child_attr_selected = "" />
									<#assign nav_child_css_class = "" />

									<#if childNavigationItem.isSelected() >
										<#assign nav_child_attr_selected = "aria-selected='true'" />
										<#assign nav_child_css_class = "active selected" />
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
	</div>

	<@aui.script use="liferay-navigation-interaction">
		var navigation = A.one('#${navbarId}');

		if (navigation) {
			navigation.plug(Liferay.NavigationInteraction);
		}

		Liferay.Data.NAV_INTERACTION_LIST_SELECTOR = '.navbar-site';
		Liferay.Data.NAV_LIST_SELECTOR = '.navbar-site';
	</@>
</#if>