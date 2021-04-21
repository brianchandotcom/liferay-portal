<#if !entries?has_content>
	<#if themeDisplay.isSignedIn()>
		<div class="alert alert-info">
			<@liferay.language key="there-are-no-menu-items-to-display" />
		</div>
	</#if>
<#else>
	<div aria-label="<@liferay.language key="site-pages" />" class="minium-main-navigation">
		<#list entries as navItem>

			<#assign
				nav_item_css_class = "main-link"
				nav_item_href = navItem.getRegularURL()
				nav_item_layout = navItem.getLayout()
			/>

			<#if !navItem.isBrowsable()>
				<#assign
					nav_item_href = "#"
				/>
			</#if>

			<#if navItem.isSelected()>
				<#assign
					nav_item_css_class = "${nav_item_css_class} is-active"
				/>
			</#if>

			<a class="${nav_item_css_class}" href="${nav_item_href!""}" ${navItem.getTarget()}>
				<div class="main-link__icon">
					<#if nav_item_layout.iconImageId != 0>
						<@liferay_theme["layout-icon"] layout=nav_item_layout />
					</#if>
				</div>

				<div class="main-link__label">${navItem.getName()}</div>
			</a>
		</#list>
	</div>
</#if>