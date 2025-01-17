<#if (backgroundImage)?? && backgroundImage.getData()?? && backgroundImage.getData() != "">
	<style ${nonceAttribute} type="text/css">
		.newsletter--container {
			background: url(${backgroundImage.getData()}) center center, linear-gradient(180deg, rgba(53, 53, 53, 0.3) 0%, #353535 100%);
		}
	</style>

	<div class="newsletter--container" data-background-image-src="${backgroundImage.getData()}">
		<div class="form-wrapper">
			<form action="${backgroundImage.FormAction.getData()}">
				<h1>${backgroundImage.CallToActionText.getData()}</h1>

				<p>${backgroundImage.CallToActionText.CallToActionSecondaryText.getData()}</p>

				<label for="email-input">
					<input id="email-input" placeholder="${backgroundImage.FormAction.InputPlaceholder.getData()}" value="" />

					<button type="submit"><span>></span></button>
				</label>
			</form>
		</div>
	</div>
</#if>