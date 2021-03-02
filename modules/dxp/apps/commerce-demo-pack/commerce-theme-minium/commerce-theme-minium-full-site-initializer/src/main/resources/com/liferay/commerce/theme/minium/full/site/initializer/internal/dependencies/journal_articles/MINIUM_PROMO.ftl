<style>
	.banner-content-preview {
		display: flex;
	}

	.minium .banner-content-section {
		background-size: cover;
		background-position: 50%;
		height: 100%;
		padding: 5% 56px 56px;
		width: 100%;
	}

	.banner-content-preview .banner-content-section .minium-h1.content-preview-title {
		color: #FFFFFF;
		line-height: 1;
		font-style: normal;
		font-weight: 300;
		font-size: 48px;
		margin-bottom: 25px;
		text-transform: capitalize;
	}

	.minium .banner-content-section .minium-cta-button {
		border: 1px solid rgba(255, 255, 255, 0.5);
		box-sizing: border-box;
		border-radius: 8px;
	}

	.minium .banner-content-section .minium-cta-button:hover {
		background-color: #4886FF;
		border: 1px solid #4886FF;
		transform: none;
	}

	.minium .banner-content-section .minium-sub-heading {
		color: #abadb3;
		padding: 12px 0 12px;
	}

	.minium .banner-content-setion .minium-cta-button {
		color: #FFFFFF;
		cursor: pointer;
		font-size: 10px;
		line-height: 10px;
		letter-spacing: 1px;
		text-transform: uppercase;
	}

	.minium span.minium-button-text {
		color: #FFFFFF;
		font-weight: 900;
		letter-spacing: 1px;
		text-transform: uppercase;
	}

	.minium .banner-content-container .minium-cta-link button {
		cursor: pointer;
	}

</style>

<script>
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