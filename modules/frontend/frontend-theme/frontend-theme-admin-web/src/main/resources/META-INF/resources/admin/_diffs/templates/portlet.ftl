#set ($portlet_display = $portletDisplay)

#set ($portlet_icon_options_view = "lexicon")

#set ($portlet_id = $htmlUtil.escapeAttribute($portlet_display.getId()))
#set ($portlet_title = $htmlUtil.escape($portlet_display.getTitle()))
#set ($portlet_description = $portlet_display.getDescription())
#set ($portlet_back_url = $htmlUtil.escapeHREF($portlet_display.getURLBack()))
#set ($valid_portlet_description = $validator.isNotNull($portlet_description) && $portlet_description.indexOf('javax.portlet.description') == -1)

#set ($portlet_topper_css_class = "portlet-topper")

<section class="portlet" id="portlet_$portlet_id">
	<header class="header-toolbar header-toolbar-default" data-qa-id="header">
		<div class="container-fluid-1280">
			<div class="toolbar-group">
				<div class="toolbar-group-content">
					<a class="icon-monospaced sidenav-toggler" data-content="body" data-qa-id="productMenu" data-target="#sidenavSliderId,#wrapper" data-toggle="sidenav" data-type="fixed-push" data-type-mobile="fixed" data-use-delegate="true" href="#sidenavSliderId" id="sidenavToggleId">
						<div class="toast-animation">
							<div class="pm"></div>
							<div class="cn"></div>
						</div>
					</a>
				</div>

				#if ($portlet_display.isShowBackIcon())
					<div class="toolbar-group-content">
						<a href="$portlet_back_url"><span class="icon-angle-left icon-monospaced"></span></a>
					</div>
				#end
			</div>

			<div class="toolbar-group-right">
				<div class="toolbar-group-content" data-qa-id="headerOptions">
					$theme.portletIconOptions("right", "lexicon")
				</div>
			</div>

			<div class="text-center toolbar-group-expand-text">
				<span class="header-toolbar-title" title="$portlet_title">$portlet_title</span>

				#if ($valid_portlet_description)
					$theme.iconHelp($portlet_description)
				#end
			</div>
		</div>
	</header>

	$portlet_display.writeContent($writer)
</section>