<#assign
	author = requestMap.attributes.author!""
	viewURL = requestMap.attributes.viewURL!""
/>

<div class="aspect-ratio aspect-ratio-21-to-9 aspect-ratio-bg-center aspect-ratio-bg-cover" style="background-image: url('${(coverImage.getData()?? && coverImage.getData() != '')?then(coverImage.getData(), '')}')">
</div>

<div class="carousel-caption">
	<h4>
		<a href="${viewURL}">
			${title.getData()}
		</a>
	</h4>

	<div class="asset-user-name">
		<@liferay.language key="by" />

		${author}
	</div>

	<small class="sr-only">
		${subTitle.getData()}
	</small>
</div>