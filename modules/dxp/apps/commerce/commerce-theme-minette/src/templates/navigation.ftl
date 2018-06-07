<#assign
	accountName=languageUtil.get(request, "select-account")
/>

<#if currentOrganization?has_content>
	<#assign
		accountName=currentOrganization.getName()
	/>
</#if>

<nav class="commerce-navigation navbar navbar-collapse-absolute navbar-expand-md navigation-bar navigation-bar-light">
	<div class="container-fluid container-fluid-max-xl">
		<div class="nav-link navbar-breakpoint-d-none">
			<a class="${logo_css_class}" href="${site_default_url}" title="<@liferay.language_format arguments="${site_name}" key="go-to-x" />">
				<#if show_site_name>
					<span class="text-truncate-inline">
						<span class="logo-text-sm text-truncate">${site_name}</span>
					</span>
				<#else>
					<img alt="${logo_description}" class="logo-image-sm" src="${site_logo}" />
				</#if>
			</a>
		</div>

		<a aria-controls="commerceSiteNavigation" aria-expanded="false" aria-label="Toggle Commerce Site Navigation" class="collapsed navbar-toggler navbar-toggler-link" data-toggle="collapse" href="#commerceSiteNavigation" role="button">
			<svg aria-hidden="true" class="lexicon-icon lexicon-icon-bars" focusable="false"><use xlink:href="${images_folder}/lexicon/icons.svg#bars" /></svg>
		</a>

		<div class="collapse navbar-collapse" id="commerceSiteNavigation">
			<div class="container-fluid container-fluid-max-xl">
				<nav class="commerce-site-search-navigation navbar navbar-expand-md navigation-bar">
					<ul class="navbar-nav navbar-nav-expand">
						<li class="nav-item navbar-breakpoint-down-d-none">
							<div class="nav-link">
								<a class="${logo_css_class}" href="${site_default_url}" title="<@liferay.language_format arguments="${site_name}" key="go-to-x" />">
									<#if show_site_name>
										<span class="logo-text-lg text-truncate-inline">
											<span class="text-truncate">${site_name}</span>
										</span>
									<#else>
										<img alt="${logo_description}" class="logo-image-lg" src="${site_logo}" />
									</#if>
								</a>
							</div>
						</li>
						<li class="nav-item nav-item-expand">
							<div class="nav-link portlet-flush portlet-search-inset">
								<#assign
									preferences = freeMarkerPortletPreferences.getPreferences({"portletSetupPortletDecoratorId": "barebone"})
								/>

								<@liferay.search_bar default_preferences="${preferences}" />
							</div>
						</li>
						<li class="nav-item">
							<div class="nav-link user-personal-bar">
								<@liferay.user_personal_bar />
							</div>
						</li>

						<#if is_signed_in>
							<li class="dropdown dropdown-wide nav-item">
								<a aria-expanded="false" aria-haspopup="true" class="dropdown-toggle nav-link" data-toggle="dropdown" href="javascript:;" role="button">
									<span class="commerce-navigation-icon inline-item inline-item-before"><svg aria-hidden="true" class="lexicon-icon lexicon-icon-user" focusable="false"><use xlink:href="${images_folder}/lexicon/icons.svg#user" /></svg></span><span class="navbar-text-truncate">${accountName}</span>
								</a>

								<div class="dropdown-menu dropdown-menu-right portlet-flush">
									<@commerce_search_organization default_preferences=freeMarkerPortletPreferences.getPreferences("portletSetupPortletDecoratorId", "barebone") />
								</div>
							</li>
						</#if>

						<li class="dropdown dropdown-wide nav-item" id="b2b-mini-cart">
							<a aria-expanded="false" aria-haspopup="true" class="animate dropdown-toggle nav-link" data-toggle="dropdown" href="javascript:;" role="button">
								<span class="commerce-navigation-icon inline-item inline-item-before"><svg aria-hidden="true" class="lexicon-icon lexicon-icon-suitcase" focusable="false"><use xlink:href="${images_folder}/lexicon/icons.svg#suitcase" /></svg></span><span class="rounded-circle sticker sticker-dark sticker-lg sticker-success" id="b2b-mini-cart-items-count">${orderItemsQuantity}</span>
							</a>

							<div class="dropdown-menu dropdown-menu-right portlet-flush">
								<@commerce_cart_mini default_preferences=freeMarkerPortletPreferences.getPreferences("portletSetupPortletDecoratorId", "barebone") />
							</div>
						</li>
					</ul>
				</nav>

				<#if has_navigation && is_setup_complete>
					<nav class="commerce-site-navigation navbar navbar-expand-md navigation-bar">
						<ul class="navbar-nav navbar-nav-expand">
							<li class="dropdown dropdown-full nav-item nav-item-expand">
								<a aria-expanded="false" aria-haspopup="true" class="dropdown-toggle nav-link nav-link-primary" data-toggle="dropdown" href="javascript:;" role="button">
									<@liferay.language key="explore-products" /><span class="commerce-dropdown-caret inline-item inline-item-after"><svg aria-hidden="true" class="lexicon-icon lexicon-icon-angle-down" focusable="false"><use xlink:href="${images_folder}/lexicon/icons.svg#angle-down" /></svg></span>
								</a>

								<div aria-labelledby="" class="dropdown-menu portlet-flush">
									<@commerce_category_navigation_menu default_preferences=freeMarkerPortletPreferences.getPreferences("portletSetupPortletDecoratorId", "barebone") />
								</div>
							</li>

							<#list nav_items as nav_item>
								<#assign
									nav_child_is_selected = false
									nav_item_attr_has_popup = ""
									nav_item_attr_selected = ""
									nav_item_attr_data_toggle = ""
									nav_item_css_class = "nav-item"
									nav_item_layout = nav_item.getLayout()
									nav_link_css_class = ""

									dropdown_menu_css_class = ""
								/>

								<#if nav_item.hasChildren()>
									<#assign
										nav_item_attr_data_toggle = 'aria-expanded="false" aria-haspopup="true" data-toggle="dropdown" role="button"'
										nav_item_css_class= "${nav_item_css_class} dropdown"
										nav_link_css_class = "${nav_link_css_class} dropdown-toggle"
									/>

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
										nav_item_css_class = "${nav_item_css_class} selected"
										nav_link_css_class = " active"
									/>
								</#if>

								<#if !nav_item?has_next>
									<#assign
										dropdown_menu_css_class = " dropdown-menu-right"
									/>
								</#if>

								<li ${nav_item_attr_selected} class="${nav_item_css_class}" id="layout_${nav_item.getLayoutId()}">
									<a aria-labelledby="layout_${nav_item.getLayoutId()}" class="nav-link${nav_link_css_class}" ${nav_item_attr_has_popup} ${nav_item_attr_data_toggle} href="${nav_item.getURL()}" ${nav_item.getTarget()}>
										<#if nav_item_layout.iconImageId != 0>
											<span class="commerce-layout-icon"><@liferay_theme["layout-icon"] layout=nav_item_layout /></span>
										</#if>

										<span class="nav-link-text">${nav_item.getName()}</span><#if nav_item.hasChildren()><span class="commerce-dropdown-caret inline-item inline-item-after"><svg aria-hidden="true" class="lexicon-icon lexicon-icon-angle-down" focusable="false"><use xlink:href="${images_folder}/lexicon/icons.svg#angle-down" /></svg></span></#if>
									</a>

									<#if nav_item.hasChildren()>
										<ul class="child-menu dropdown-menu${dropdown_menu_css_class}">
											<#list nav_item.getChildren() as nav_child>
												<#assign
													nav_child_attr_selected = "selected"
													nav_child_css_class = "nav-item"
													nav_child_layout = nav_child.getLayout()
													nav_child_link_css_class = ""
												/>

												<#if nav_child.isSelected()>
													<#assign
														nav_child_attr_selected = "aria-selected='true'"
														nav_child_css_class = "selected"
														nav_child_link_css_class = " active"
													/>
												</#if>

												<li ${nav_child_attr_selected} class="${nav_child_css_class}" id="layout_${nav_child.getLayoutId()}">
													<a aria-labelledby="layout_${nav_child.getLayoutId()}" class="nav-link${nav_child_link_css_class}" href="${nav_child.getURL()}" ${nav_child.getTarget()}>
														<#if nav_child_layout.iconImageId != 0>
															<span class="commerce-layout-icon"><@liferay_theme["layout-icon"] layout=nav_child_layout /></span>
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
					</nav>
				</#if>
			</div>
		</div>
	</div>
</nav>