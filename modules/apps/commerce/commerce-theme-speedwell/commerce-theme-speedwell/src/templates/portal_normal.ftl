<!DOCTYPE html>
<#include init />
<html class="${root_css_class}" dir="<@liferay.language key="lang.dir" />" lang="${w3c_language_id}">
	<head>
		<title>${the_title} - ${company_name}</title>

		<meta content="initial-scale=1.0, width=device-width" name="viewport" />

		<link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700&display=swap" rel="stylesheet">
		<script type="text/javascript" src="${javascript_folder}/standalone/SpeedwellSlider.js" defer></script>
		<@liferay_util["include"] page=top_head_include />
	</head>

	<body class="speedwell ${css_class}">
		<div id="wrapper">
			<div class="liferay-top">
				<@liferay_ui["quick-access"] contentId="#main-content" />
				<@liferay_util["include"] page=body_top_include />
				<@liferay.control_menu />
			</div>

			<main class="speedwell-frame" id="speedwell">
				<div class="speedwell-frame__topbar">
					<#include "${full_templates_path}/topbar.ftl" />
				</div>

				<#if speedwell_content_css_class?contains("wide")>
				<div class="speedwell-frame speedwell-frame__content--wide">
				<#else>
				<div class="speedwell-frame speedwell-frame__content">
				</#if>
					<a name="speedwell-top"></a>

					<div class="container-fluid ${speedwell_content_css_class}">
						<#if selectable>
							<@liferay_util["include"] page=content_include />
						<#else>
							${portletDisplay.recycle()}
							${portletDisplay.setTitle(the_title)}

							<@liferay_theme["wrap-portlet"] page="portlet.ftl">
								<@liferay_util["include"] page=content_include />
							</@>
						</#if>
					</div>
				</div>

				<footer class="speedwell-footer">
					<!-- div class="speedwell-footer__info">
						<div class="payment-methods">
							<div class="payment-methods__method"><img src="${themeDisplay.getPathThemeImages()}/Visa.png" /></div>
							<div class="payment-methods__method"><img src="${themeDisplay.getPathThemeImages()}/MasterCard.png" /></div>
							<div class="payment-methods__method"><img src="${themeDisplay.getPathThemeImages()}/PayPal.png" /></div>
							<div class="payment-methods__method"><img src="${themeDisplay.getPathThemeImages()}/AmericanExpress.png" /></div>
						</div>
					</div>

					<div class="speedwell-footer__menu">
						<section>
							<h2>COMPANY</h2>

							<nav>
								<a href="#">About us</a>
								<a href="#">Work here</a>
								<a href="#">Team</a>
								<a href="#">Stories</a>
							</nav>
						</section>

						<section>
							<h2>Company</h2>

							<nav>
								<a href="#">About us</a>
								<a href="#">Work here</a>
							</nav>
						</section>

						<section>
							<h2>Company</h2>

							<nav>
								<a href="#">About us</a>
								<a href="#">Work here</a>
								<a href="#">Team</a>
								<a href="#">Stories</a>
								<a href="#">Stories</a>
							</nav>
						</section>

						<section>
							<h2>Company</h2>

							<p>
								Customer service and order hotline<br>
								+44 20 3322 9915<br>
								Monday - Friday 7:00am - 10:00pm<br>
								Saturday 7:00am - 5:00pm
							</p>
						</section>
					</div-->

					<div class="speedwell-footer__closing">
						<img alt="${logo_description}" class="logo" src="${site_logo}" />

						<nav>
							<a href="#">Privacy Policy</a>
							<a href="#">Terms and Conditions</a>
							<a href="#">Legal Notice Patents</a>
						</nav>
					</div>
				</footer>
			</main>

			<div class="liferay-bottom">
				<@liferay_util["include"] page=body_bottom_include />
				<@liferay_util["include"] page=bottom_include />
			</div>
		</div>

		<script type="text/javascript" src="${javascript_folder}/intersection-observer.js"></script>
		<script type="text/javascript" src="${javascript_folder}/features/context.js"></script>
		<script type="text/javascript" src="${javascript_folder}/features/accessibility.js"></script>
		<script type="text/javascript" src="${javascript_folder}/features/scrollHandler.js"></script>
		<script type="text/javascript" src="${javascript_folder}/features/topbar.js"></script>
		<script type="text/javascript" src="${javascript_folder}/features/categoryMenu.js"></script>
		<script type="text/javascript" src="${javascript_folder}/features/mobile.js"></script>
		<script type="text/javascript" src="${javascript_folder}/features/init.js"></script>
	</body>
</html>