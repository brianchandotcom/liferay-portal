<!DOCTYPE html>

#parse ($init)

<html class="$root_css_class" dir="#language ("lang.dir")" lang="$w3c_language_id">

<head>
	<title>$the_title - $company_name</title>

	<meta content="initial-scale=1.0, width=device-width" name="viewport" />

	$theme.include($top_head_include)
</head>

<body class="$css_class">

#quick_access("#main-content")

$theme.include($body_top_include)

#product_menu_sidebar($liferay_product_menu_state)

#control_menu()

<div id="wrapper">
	<header class="container-fluid-1280" id="banner" role="banner">
		<div class="row">
			<div class="navbar-header" id="heading">
				<button aria-controls="navigation" aria-expanded="false" class="collapsed navbar-toggle" data-target="#navigationCollapse" data-toggle="collapse" type="button">
					<span class="icon-bar"></span>

					<span class="icon-bar"></span>

					<span class="icon-bar"></span>
				</button>

				<a class="$logo_css_class" href="$site_default_url" title="#language_format ("go-to-x", [$site_name])">
					<img alt="$logo_description" height="64" src="$site_logo" width="64" />
				</a>

				#if ($show_site_name)
					<span class="site-name" title="#language_format ("go-to-x", [$site_name])">
						$site_name
					</span>
				#end
			</div>

			#parse ("$full_templates_path/navigation.vm")
		</div>
	</header>

	<section class="container-fluid-1280" id="content">
		<h1 class="hide-accessible">$the_title</h1>

		#if ($selectable)
			$theme.include($content_include)
		#else
			$portletDisplay.recycle()

			$portletDisplay.setTitle($the_title)

			$theme.wrapPortlet("portlet.vm", $content_include)
		#end
	</section>

	<footer class="container-fluid-1280" id="footer" role="contentinfo">
		<div class="row">
			#language ("powered-by") <a href="http://www.liferay.com" rel="external">Liferay</a>
		</div>
	</footer>
</div>

$theme.include($body_bottom_include)

$theme.include($bottom_include)

</body>

</html>