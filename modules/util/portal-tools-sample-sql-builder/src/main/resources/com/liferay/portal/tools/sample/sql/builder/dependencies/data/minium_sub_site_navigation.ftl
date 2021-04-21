<#if !entries?has_content>
	<#if themeDisplay.isSignedIn()>
		<div class="alert alert-info">
			<@liferay.language key="there-are-no-menu-items-to-display" />
		</div>
	</#if>
<#else>
	<div aria-label="<@liferay.language key="site-pages" />" class="minium-tabs">
		<#list entries as navItem>

			<#assign
				nav_item_css_class = "minium-tabs__tab"
				nav_item_href = navItem.getRegularURL()
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
				<em>${navItem.getName()}</em>
			</a>
		</#list>
	</div>
</#if>