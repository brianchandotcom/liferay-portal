<div class="b2b-site-navigation b2b-site-navigation-open sidenav sidenav-b2b" id="b2bSiteNavigation">
	<nav class="tbar">
		<ul class="tbar-nav">
			<li class="tbar-item">
				<a class="b2b-site-navigation-open link-monospaced tbar-link" data-content=".b2b-wrapper" data-open-class="b2b-site-navigation-open" data-target="#b2bSiteNavigation" data-toggle="sidenav" href="/" role="button">
					<svg aria-hidden="true" class="lexicon-icon lexicon-icon-angle-left" focusable="false">
						<use xlink:href="${images_folder}/lexicon/icons.svg#angle-left" />
					</svg>

					<svg aria-hidden="true" class="lexicon-icon lexicon-icon-angle-right" focusable="false">
						<use xlink:href="${images_folder}/lexicon/icons.svg#angle-right" />
					</svg>
				</a>
			</li>
		</ul>
	</nav>

	<ul class="nav nav-nested">
		<#list nav_items as nav_item>
			<#assign
			nav_child_is_selected = false
			nav_item_attr_has_popup = ""
			nav_item_attr_selected = ""
			nav_item_css_class = "nav-item"
			nav_item_layout = nav_item.getLayout()
			/>

			<#if nav_item.hasChildren()>
				<#list nav_item.getChildren() as nav_child>
					<#if nav_child.isSelected()>
						<#assign
						nav_child_is_selected = true
						/>
					</#if>
				</#list>
			</#if>

			<#if nav_item.isSelected() && !nav_child_is_selected>
				<#assign
				nav_item_attr_has_popup = "aria-haspopup='true'"
				nav_item_attr_selected = "aria-selected='true'"
				nav_item_css_class = "nav-item selected"
				/>
			</#if>

		<li ${nav_item_attr_selected} class="${nav_item_css_class}" id="layout_${nav_item.getLayoutId()}" role="presentation">
			<a aria-labelledby="layout_${nav_item.getLayoutId()}" class="nav-link" ${nav_item_attr_has_popup} href="${nav_item.getURL()}" ${nav_item.getTarget()} role="menuitem">
				<#if nav_item_layout.iconImageId != 0>
					<span class="commerce-site-navigation-icon"><@liferay_theme["layout-icon"] layout=nav_item_layout /></span>
				</#if>

				<span class="nav-link-text">${nav_item.getName()}</span>
			</a>

			<#if nav_item.hasChildren()>
				<ul class="child-menu nav" role="menu">
					<#list nav_item.getChildren() as nav_child>
						<#assign
						nav_child_attr_selected = "selected"
						nav_child_css_class = "nav-item"
						nav_child_layout = nav_child.getLayout()
						/>

						<#if nav_child.isSelected()>
							<#assign
							nav_child_attr_selected = "aria-selected='true'"
							nav_child_css_class = "selected"
							/>
						</#if>

						<li ${nav_child_attr_selected} class="${nav_child_css_class}" id="layout_${nav_child.getLayoutId()}" role="presentation">
							<a aria-labelledby="layout_${nav_child.getLayoutId()}" class="nav-link" href="${nav_child.getURL()}" ${nav_child.getTarget()} role="menuitem">
								<#if nav_child_layout.iconImageId != 0>
									<span class="commerce-site-navigation-icon"><@liferay_theme["layout-icon"] layout=nav_child_layout /></span>
								</#if>

								<span class="nav-link-text">${nav_child.getName()}</span>
							</a>
						</li>
					</#list>
				</ul>
			</#if>
		</li>
		</#list>
	</ul>
</div>