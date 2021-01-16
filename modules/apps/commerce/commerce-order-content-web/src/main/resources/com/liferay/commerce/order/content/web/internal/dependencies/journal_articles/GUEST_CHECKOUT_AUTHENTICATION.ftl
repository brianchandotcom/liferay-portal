<#if commerceOrderHttpHelper.isGuestCheckoutEnabled(request)>

	<#-- Instant redirect, when the page is hit directly or refreshed -->

	<script>
		if (Liferay.ThemeDisplay.isSignedIn()) {
			window.location.replace("${commerceOrderHttpHelper.getCommerceCheckoutPortletURL(request)}");
		}
	</script>

	<#-- Redirect for Senna (I.E. when you press "Go to Site"). This will cause a flash as the page has to fully load -->

	<@liferay_aui.script>
		if (Liferay.ThemeDisplay.isSignedIn()) {
			window.location.replace("${commerceOrderHttpHelper.getCommerceCheckoutPortletURL(request)}");
		}
	</@>

	<#if validator.isNotNull(Title)>
		<h3 style="font-weight: normal">${Title.getData()}</h3>
	</#if>

	<#if validator.isNotNull(Description)>
		<p>${Description.getData()}</p>
	</#if>

	<a class="btn btn-primary w-100" href="${commerceOrderHttpHelper.getCommerceCheckoutPortletURL(request)}">
		${ButtonLabel.getData()}
	</a>
</#if>