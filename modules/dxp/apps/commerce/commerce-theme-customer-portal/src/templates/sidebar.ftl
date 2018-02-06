<div class="col sidenav sidenav-b2b">
	<div class="text-right">
		<a href="javascript:void(0)" id="closeSidenav" class="btn btn-sm btn-unstyled text-secondary py-2 px-3">
			<small>Close</small>
		</a>
	</div>

	<div class="sidenav-body">
		<ul class="nav nav-b2b nav-nested">
			<#list nav_items as nav_item>
				<#assign
				nav_item_attr_has_popup = ""
				nav_item_attr_selected = ""
				nav_item_css_class = "nav-item"
				nav_item_layout = nav_item.getLayout()
				/>

				<#if nav_item.isSelected()>
					<#assign
					nav_item_attr_has_popup = "aria-haspopup='true'"
					nav_item_attr_selected = "aria-selected='true'"
					nav_item_css_class = "nav-item selected"
					/>
				</#if>

			<li ${nav_item_attr_selected} class="${nav_item_css_class}" id="layout_${nav_item.getLayoutId()}" role="presentation">
				<a aria-labelledby="layout_${nav_item.getLayoutId()}" class="nav-link active" ${nav_item_attr_has_popup} href="${nav_item.getURL()}" ${nav_item.getTarget()} role="menuitem"><span><@liferay_theme["layout-icon"] layout=nav_item_layout /> ${nav_item.getName()}</span></a>

				<#if nav_item.hasChildren()>
					<ul class="child-menu" role="menu">
						<#list nav_item.getChildren() as nav_child>
							<#assign
							nav_child_attr_selected = ""
							nav_child_css_class = ""
							/>

							<#if nav_item.isSelected()>
								<#assign
								nav_child_attr_selected = "aria-selected='true'"
								nav_child_css_class = "selected"
								/>
							</#if>

							<li ${nav_child_attr_selected} class="${nav_child_css_class}" id="layout_${nav_child.getLayoutId()}" role="presentation">
								<a aria-labelledby="layout_${nav_child.getLayoutId()}" href="${nav_child.getURL()}" ${nav_child.getTarget()} role="menuitem">${nav_child.getName()}</a>
							</li>
						</#list>
					</ul>
				</#if>
			</li>
			</#list>
		</ul>
	</div>
</div>