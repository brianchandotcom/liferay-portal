<script ${nonceAttribute}>
	function getRelativeURL() {
		Liferay.ctaURL = "${CTA.getData()}";

		var currentRelativeURL = themeDisplay.getLayoutRelativeURL();

		var relativeURLParts = currentRelativeURL.split('/');

		Liferay.ctaURL = Liferay.ctaURL.replace("null", relativeURLParts[2]);

		window.location.href = Liferay.ctaURL;
	}
</script>

<div class="banner-content-preview">
	<div class="banner-content-section" style="background-image: linear-gradient(180deg, rgba(47, 51, 59, 0) 0%, #2F333B 100%), url(${ContentImage.getData()}); border-radius: 8px;">
		<div class="banner-content-container">
			<h1 class="content-preview-title minium-h1">
				<#if (Heading.getData())??>
					${Heading.getData()}
				</#if>
			</h1>

			<a class="minium-cta-link" onclick="getRelativeURL()">
				<button class="minium-cta-button">
					<span class="minium-button-text">Discover</span>
				</button>
			</a>
		</div>
	</div>
</div>