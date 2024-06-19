<style>
	.carousel-inner {
		border-radius: 10px;
		height: 353px;
		overflow: hidden;
		width: 682.99px;
	}

	.carousel-indicators {
		border-radius: 50% !important;
		bottom: -50px;
	}

	.carousel-indicators li {
		border-radius: 50%;
		height: 10px;
		width: 10px;
	}

	.video-preview {
		border-radius: 10px;
		overflow: hidden;
	}

	.video-thumbnail {
		border-radius: 4px;
		cursor: pointer;
		height: 353px;
		object-fit: cover;
		transition: all 0.3s ease-in-out;
		width: 682.99px;
	}

	.video-thumbnail:hover+.video-thumbnail-play-symbol {
		opacity: 0.7;
		transform: scale(110%);
	}

	.video-thumbnail-play-symbol {
		color: #fff;
		font-size: 70px;
		opacity: 1;
		position: absolute;
		transition: all 0.3s ease-in-out;
	}

	.video-thumbnail-play-symbol:hover {
		opacity: 0.7;
		transform: scale(110%);
	}
</style>

<#if (CPDefinition_cProductId.getData())??>
	<#assign productId = CPDefinition_cProductId.getData() />
</#if>

<#if themeDisplay?has_content>
	<#assign scopeGroupId = themeDisplay.getScopeGroupId() />
</#if>

<#assign channel = restClient.get("/headless-commerce-delivery-catalog/v1.0/channels?accountId=-1&filter=name eq 'Marketplace Channel' and siteGroupId eq '${scopeGroupId}'") />

<#if channel?has_content>
	<#assign channelId = channel.items[0].id />
</#if>

<#assign
	productId = CPDefinition_cProductId.getData()

	product = restClient.get("/headless-commerce-delivery-catalog/v1.0/channels/"+ channelId +"/products/"+ productId +"?accountId=-1&images.accountId=-1&nestedFields=categories,images,productSpecifications")

	catalogName = product.catalogName
	productImage = product.images![]
	solutionHeaderImages = productImage?filter(image -> image.tags?seq_contains("solution-header"))
/>

<#if product.productSpecifications?has_content>
	<#assign
		productSpecifications = product.productSpecifications

		headerDescription = productSpecifications?filter(specification -> specification.specificationKey == "solution-header-description")[0].value
		headerTitle = productSpecifications?filter(specification -> specification.specificationKey == "solution-header-title")[0].value
		hasVideo = productSpecifications?filter(specification -> specification.specificationKey == "solution-header-video-url")
	/>

	<#if hasVideo?has_content>
		<#assign headerVideoUrl = hasVideo[0].value />
	</#if>
</#if>

<#if headerTitle?has_content>
	<div class="header-content">
		<div class="container d-flex">
			<div class="mr-5">
				<#if headerTitle?has_content>
					<h1 class="mb-8">
						${headerTitle}
					</h1>
				</#if>

				<#if headerDescription?has_content>
					${headerDescription}
				</#if>
			</div>

			<div>
				<#if headerVideoUrl?has_content>
					<#assign
						videoThumbnail = headerVideoUrl?split("v=")[1]
						youtubeVideoId = videoThumbnail?split("&")[0]
					/>

					<a href=" ${headerVideoUrl}" target="_blank">
						<div class="align-items-center d-flex justify-content-center position-relative video-preview">
							<img
								class="video-thumbnail"
								aria-label="video-thumbnail"
								src="https://img.youtube.com/vi/${youtubeVideoId}/0.jpg" />

							<div class="position-absolute video-thumbnail-play-symbol">
								<@clay["icon"] symbol="video" />
							</div>
						</div>
					</a>
				<#elseif solutionHeaderImages?has_content>
					<div id="headerCarousel" class="carousel slide" data-ride="carousel">
						<ol class="carousel-indicators">
							<#list solutionHeaderImages as image>
								<li data-target="#carouselExampleIndicators" data-slide-to="${image?index}" class="<#if image?index == 0>active</#if>"></li>
							</#list>
						</ol>

						<div class="align-items-center carousel-inner d-flex">
							<#list solutionHeaderImages as image>
								<#assign imageSourceUrlSplited = image.src?split("/o") />

								<#if imageSourceUrlSplited?has_content>
									<#assign productThumbnail = "/o/${imageSourceUrlSplited[1]}" />

									<div class="carousel-item<#if image?index == 0> active</#if>">
										<img alt="Slide ${image?index}" class="d-block w-100" src="${productThumbnail}">
									</div>
								</#if>
							</#list>
						</div>

						<a class="carousel-control-prev" href="#headerCarousel" role="button" data-slide="prev">
							<span class="carousel-control-prev-icon" aria-hidden="true"></span>
							<span class="sr-only">Previous</span>
						</a>

						<a class="carousel-control-next" href="#headerCarousel" role="button" data-slide="next">
							<span class="carousel-control-next-icon" aria-hidden="true"></span>
							<span class="sr-only">Next</span>
						</a>
					</div>
				</#if>
			</div>
		</div>
	</div>
</#if>