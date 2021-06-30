<div class="columns-spa" id="main-content" role="main">
	<div class="portlet-layout row">
		<div class="col-md-12 portlet-column portlet-column-only" id="column-1">
			${processor.processColumn("column-1", "portlet-column-content portlet-column-content-only")}
		</div>
	</div>

	<div class="portlet-layout row">
		<div class="col-md-12 portlet-column portlet-column-only spa-additional-portlets" id="column-2">
			<#if themeDisplay??>
				<#assign
					permissionChecker = (themeDisplay.getPermissionChecker())!
					layout = (themeDisplay.getLayout())!
				/>

				<#if permissionChecker?? && layout??>
					<#if (layoutPermission.contains(permissionChecker, layout, "UPDATE"))!>
						<#assign
							hasUpdate = "has-update"
							locale = themeDisplay.getLocale()
						/>

						<div class="navbar">
							<span><@liferay.language key="spa-additional-portlets" /></span>

							<div class="dropdown">
								<button class="dropbtn">
									<@liferay.language key="test-navigation" />
									<!-- <i class="fa fa-caret-down"></i> -->

								</button>

								<div class="dropdown-content" id="spa-additional-portlets-urls">
								</div>
							</div>
						</div>
					</#if>
				</#if>
			</#if>

			${processor.processColumn("column-2", "${hasUpdate!} portlet-column-content portlet-column-content-only")}
		</div>
	</div>
</div>