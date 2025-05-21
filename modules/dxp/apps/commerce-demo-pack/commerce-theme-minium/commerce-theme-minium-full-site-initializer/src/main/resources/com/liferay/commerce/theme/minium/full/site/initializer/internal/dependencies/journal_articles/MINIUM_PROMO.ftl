<style ${nonceAttribute}>
	.minium-promo-background-image {
		background-image: linear-gradient(180deg, rgba(47, 51, 59, 0) 0%, #2F333B 100%), url(${ContentImage.getData()}); border-radius: 8px;
	}
</style>

<div class="banner-content-preview">
	<div class="banner-content-section minium-promo-background-image">
		<div class="banner-content-container">
			<h1 class="content-preview-title minium-h1">
				<#if (Heading.getData())??>
					${Heading.getData()}
				</#if>
			</h1>

			<a class="minium-cta-link" href="#">
				<button class="minium-cta-button">
					<span class="minium-button-text">Discover</span>
				</button>
			</a>
		</div>

		<script ${nonceAttribute}>
			const ctaLink = document.querySelector('.minium-cta-link');

			function getRelativeURL() {
				const ctaURL = "${CTA.getData()}";

				const siteRoot = themeDisplay.getLayoutRelativeURL().split('/');

				window.location.href = ctaURL.replace('group/null', siteRoot[1] + '/' + siteRoot[2]);
			}

			ctaLink.addEventListener('click', getRelativeURL);
		</script>
	</div>
</div>