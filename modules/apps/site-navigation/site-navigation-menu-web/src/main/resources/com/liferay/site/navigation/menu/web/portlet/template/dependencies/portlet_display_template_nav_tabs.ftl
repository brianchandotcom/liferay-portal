<#include "${templatesPath}/NAVIGATION-MACRO-FTL" />

<#if !entries?has_content>
	<#if themeDisplay.isSignedIn()>
		<div class="alert alert-info">
			<@liferay.language key="there-are-no-menu-items-to-display" />
		</div>
	</#if>
<#else>
	<#assign
		portletDisplay = themeDisplay.getPortletDisplay()

		navbarId = "navbar_" + portletDisplay.getId()
	/>

	<div id="${navbarId}">
		<ul aria-label="<@liferay.language key="site-pages" />" class="nav nav-tabs navbar-site" role="menubar">
			<#assign navItems = entries />

			<#list navItems as navItem>
				<#assign showChildrenNavItems = (displayDepth != 1) && navItem.hasBrowsableChildren() />

				<#if navItem.isBrowsable() || showChildrenNavItems>
					<#assign
						nav_item_attr_has_popup = ""
						nav_item_caret = ""
						nav_item_css_class = "lfr-nav-item nav-item"
						nav_item_href_link = ""
						nav_item_link_css_class = "nav-link text-truncate"
					/>

					<#if showChildrenNavItems>
						<#assign
							nav_item_attr_has_popup = "aria-haspopup='true'"
						 	nav_item_caret = '<span class="lfr-nav-child-toggle"><i class="icon-caret-down"></i></span>'
							nav_item_css_class = "${nav_item_css_class} dropdown"
							nav_item_link_css_class = "${nav_item_link_css_class} dropdown-toggle"
						/>
					</#if>

					<#if navItem.isBrowsable()>
						<#assign nav_item_href_link = "href='${navItem.getURL()}'" />
					</#if>

					<#if navItem.isChildSelected() || navItem.isSelected()>
						<#assign
							nav_item_css_class = "${nav_item_css_class} selected active"
						/>
					</#if>

					<li class="nav-item ${nav_item_css_class}" id="layout_${portletDisplay.getId()}_${navItem.getLayoutId()}" role="presentation">
						<a ${nav_item_attr_has_popup} class="nav-link ${nav_item_link_css_class}" ${nav_item_href_link} ${navItem.getTarget()} role="menuitem">
							<span class="text-truncate"><@liferay_theme["layout-icon"] layout=navItem.getLayout() /> ${navItem.getName()} ${nav_item_caret}</span>
						</a>

						<#if showChildrenNavItems>
							<ul aria-expanded="false" class="child-menu dropdown-menu nav-item" role="menu">
								<@buildChildrenNavItems
									displayDepth=displayDepth
									navItem=navItem
								/>
							</ul>
						</#if>
					</li>
				</#if>
			</#list>
		</ul>
	</div>

	<@liferay_aui.script use="liferay-navigation-interaction">
		var navigation = A.one('#${navbarId}');

		Liferay.Data.NAV_INTERACTION_LIST_SELECTOR = '.navbar-site';
		Liferay.Data.NAV_LIST_SELECTOR = '.navbar-site';

		if (navigation) {
			navigation.plug(Liferay.NavigationInteraction);
		}
	</@>
</#if>