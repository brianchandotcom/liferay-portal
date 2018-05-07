<#assign count = 0 />

<#if entries?has_content>
	<div class="row">
		<#list entries as curDocument>
			<#assign
				image = cpSearchResultsDisplayContext.getProductDefaultImage(curDocument, themeDisplay)

				friendlyURL = cpSearchResultsDisplayContext.getProductFriendlyURL(curDocument)

				name = cpSearchResultsDisplayContext.getName(curDocument)
			/>

			<div class="col-md-4">
				<div>
					<img src="${image}">
				</div>

				<div>
					<a href="${friendlyURL}">
						<strong>${name}</strong>
					</a>
				</div>
			</div>

			<#assign count = count + 1 />

			<#if count gte 3>
				</div>

				<div class="row">

				<#assign count = 0 />
			</#if>
		</#list>
	</div>
<#else>
	<div class="alert alert-info">
		<@liferay_ui["message"] key="no-products-were-found" />
	</div>
</#if>