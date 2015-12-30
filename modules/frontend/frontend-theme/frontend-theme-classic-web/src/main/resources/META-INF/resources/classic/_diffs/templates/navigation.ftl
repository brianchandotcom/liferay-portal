#set ($VOID = $velocityPortletPreferences.setValue("portletSetupPortletDecoratorId", "barebone"))

<div aria-expanded="false" class="collapse navbar-collapse" id="navigationCollapse">
	#if ($has_navigation && $is_setup_complete)
		<nav class="$nav_css_class site-navigation" id="navigation" role="navigation">
			<div class="navbar-form navbar-right" role="search">
				#search($velocityPortletPreferences.toString())
			</div>

			<div class="navbar-right">
				#navigation_menu($velocityPortletPreferences.toString())
			</div>
		</nav>
	#end

	<nav class="user-personal-bar">
		<ul class="nav navbar-nav navbar-right">
			#user_personal_bar($velocityPortletPreferences.toString())
		</ul>
	<nav>

</div>

#set ($VOID = $velocityPortletPreferences.reset())