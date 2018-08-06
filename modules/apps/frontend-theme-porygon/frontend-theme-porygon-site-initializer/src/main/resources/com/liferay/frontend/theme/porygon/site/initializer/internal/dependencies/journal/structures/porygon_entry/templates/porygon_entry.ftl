<#assign
	aspectRatio = requestMap.attributes.aspectRatio
	author = requestMap.attributes.author!""
	viewURL = requestMap.attributes.viewURL!""
/>

<div class="blog-list-card grid-col">
	<div class="asset-abstract">
		<div class="aspect-ratio ${aspectRatio} aspect-ratio-bg-center aspect-ratio-bg-cover" style="background-image: url('${(coverImage.getData()?? && coverImage.getData() != "")?then(coverImage.getData(), '')}')">
			<div class="blog-list-card-content grid-col">
				<h2 class="asset-title">
					<a href="${viewURL}">
						${title.getData()}
					</a>
				</h2>

				<div class="asset-content">
					<span class="asset-user-name">
						<@liferay.language key="by" />

						${author}
					</span>
				</div>
			</div>
		</div>
	</div>
</div>