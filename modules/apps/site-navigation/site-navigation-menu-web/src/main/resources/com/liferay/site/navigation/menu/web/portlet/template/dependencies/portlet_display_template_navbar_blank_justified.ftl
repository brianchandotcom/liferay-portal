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
		<ul aria-label="<@liferay.language key="site-pages" />" class="nav nav-justified navbar-blank navbar-nav navbar-site" role="menubar">
			<#assign navItems = entries />

			<#list navItems as navItem>
				<#assign showChildren = (displayDepth != 1) && navItem.hasBrowsableChildren() />

				<#if navItem.isBrowsable() || showChildren>
					<#assign
						nav_item_attr_has_popup = ""
						nav_item_caret = ""
						nav_item_css_class = "lfr-nav-item nav-item"
						nav_item_href_link = ""
						nav_item_link_css_class = "nav-link text-truncate"
					/>

					<#if showChildren>
						<#assign nav_item_attr_has_popup = "aria-haspopup='true'" />
						<#assign nav_item_caret = '<span class="lfr-nav-child-toggle"><i class="icon-caret-down"></i></span>' />
						<#assign
							nav_item_css_class = "${nav_item_css_class} dropdown"
							nav_item_link_css_class = "${nav_item_link_css_class} dropdown-toggle"
						/>
					</#if>

					<#if navItem.isBrowsable()>
						<#assign nav_item_href_link = "href='${navItem.getURL()}'" />
					</#if>

					<#if navItem.isSelected()>
						<#assign
							nav_item_css_class = "${nav_item_css_class} selected active"
						/>
					</#if>

					<li class="${nav_item_css_class}" id="layout_${portletDisplay.getId()}_${navItem.getLayoutId()}" role="presentation">
						<a aria-labelledby="layout_${portletDisplay.getId()}_${navItem.getLayoutId()}" ${nav_item_attr_has_popup} class="${nav_item_link_css_class}" ${nav_item_href_link} ${navItem.getTarget()} role="menuitem">
							<span class="text-truncate"><@liferay_theme["layout-icon"] layout=navItem.getLayout() /> ${navItem.getName()} ${nav_item_caret}</span>
						</a>

						<#if showChildren>
							<ul aria-expanded="false" class="child-menu dropdown-menu" role="menu">
								<#list navItem.getBrowsableChildren() as childNavigationItem>
									<#assign
										nav_child_css_class = ""
									/>

									<#if childNavigationItem.isSelected()>
										<#assign
											nav_child_css_class = "active selected"
										/>
									</#if>

									<li class="${nav_child_css_class}" id="layout_${portletDisplay.getId()}_${childNavigationItem.getLayoutId()}" role="presentation">
										<a aria-labelledby="layout_${portletDisplay.getId()}_${childNavigationItem.getLayoutId()}" class="dropdown-item" href="${childNavigationItem.getURL()}" ${childNavigationItem.getTarget()} role="menuitem">${childNavigationItem.getName()}</a>
									</li>
								</#list>
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