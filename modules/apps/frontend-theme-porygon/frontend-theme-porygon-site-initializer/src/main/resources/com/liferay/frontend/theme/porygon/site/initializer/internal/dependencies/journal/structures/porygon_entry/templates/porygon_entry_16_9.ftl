<#assign
	author = requestMap.attributes.author!""
	viewURL = requestMap.attributes.viewURL!""
/>

<div class="asset-abstract grid-col">
	<#if coverImage.getData()?? && coverImage.getData() != "">
		<a class="aspect-ratio aspect-ratio-16-to-9 aspect-ratio-bg-center aspect-ratio-bg-cover" href="${viewURL}" style="background-image: url('${(coverImage.getData()?? && coverImage.getData() != "")?then(coverImage.getData(), '')}')">
		</a>
	</#if>

	<div class="blog-list-card-content">
		<h3 class="asset-title">
			<a href="${viewURL}">
				${title.getData()}
			</a>
		</h3>

		<div class="asset-content">
			<div class="asset-summary">
				${subTitle.getData()}

				<a class="sr-only" href="${viewURL}">
					<@liferay.language key="read-more" /><span class="hide-accessible"><@liferay.language key="about" />${title.getData()}</span> &raquo;
				</a>
			</div>

			<div class="asset-user-name">
				<@liferay.language key="by" />

				${author}
			</div>
		</div>
	</div>
</div>