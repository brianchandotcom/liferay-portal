<footer class="bg-dark footer py-5 text-white-50">
	<div class="container">
		<div class="row">
			<div class="align-items-center col-12 d-flex flex-col flex-md-row">
				<img alt="${logo_description}" height="${company_logo_height}" src="${site_logo}" width="${company_logo_width}" />

				<#if has_navigation>
					<div class="ml-0 ml-md-3 mt-3 mt-md-0">
						<#include "${full_templates_path}/footer_navigation.ftl" />
					</div>
				</#if>

				<div class="ml-md-auto mt-3 mt-md-0">
					<#include "${full_templates_path}/social_media.ftl" />
				</div>
			</div>

			<div class="border-secondary border-top col-12 mt-5 pt-5">
				<p class="mb-0 small text-center text-md-left"><@liferay.language key="powered-by" /> <a href="http://www.liferay.com" rel="external" target="_blank">Liferay</a></p>
			</div>
		</div>
	</div>
</footer>