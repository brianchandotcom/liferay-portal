<!DOCTYPE html>

<#include init />

<html class="${root_css_class}" dir="<@liferay.language key="lang.dir" />" lang="${w3c_language_id}">

<head>
	<title>${the_title} - ${company_name}</title>

	<meta content="initial-scale=1.0, width=device-width" name="viewport" />

	${theme.include(top_head_include)}
</head>

<body class="${css_class}">

<@liferay_ui["quick-access"] contentId="#main-content" />

${theme.include(body_top_include)}

<#assign scope_group = theme_display.getScopeGroup()>

<@liferay.product_menu_sidebar state="${liferay_product_menu_state!}" />

<div id="wrapper">
	<div id="content-wrapper">
		<div id="content">
			<#if selectable>
				${theme.include(content_include)}
			<#else>
				${portletDisplay.recycle()}

				${portletDisplay.setTitle(the_title)}

				<@liferay_theme["wrap-portlet"] page="portlet.ftl">
					${theme.include(content_include)}
				</@>
			</#if>

			<div class="clear"></div>
		</div>
	</div>
</div>

${theme.include(body_bottom_include)}

${theme.include(bottom_include)}

</body>

</html>