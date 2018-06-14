<footer id="footer" role="contentinfo">
	<div class="container-fluid container-fluid-max-xl" id="company-info">
		<div class="text-center" id="footer-brand">
			<img alt="${logo_description}" height="${company_logo_height}" src="${site_logo}" width="${company_logo_width}" />
		</div>

		<#if has_navigation>
			<#include "${full_templates_path}/footer_navigation.ftl" />
		</#if>

		<#include "${full_templates_path}/social_media.ftl" />
	</div>

	<div class="container-fluid container-fluid-max-xl">
		<p id="copyright">
			<small><@liferay.language key="powered-by" /> <a href="http://www.liferay.com" rel="external">Liferay</a></small>
		</p>
	</div>
</footer>