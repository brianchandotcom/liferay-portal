<#assign
		image = ''
		title = ''
		description = ''
	/>

	<#if cpCategoryContentDisplayContext.getDefaultImageSrc()??>
		<#assign
			image = cpCategoryContentDisplayContext.getDefaultImageSrc()
		/>
	</#if>

	<#if assetCategory??>
		<#assign
			title = assetCategory.getTitle(locale)
			description = assetCategory.getDescription(locale)
		/>
	</#if>


<style>
.product-category-banner__background {
  height: 280px;
	background-position: center;
	background-repeat: no-repeat;
  background-size: cover;
}
</style>

<div class="product-category-content-container">
  <!-- Background image -->
	<div class="container-fluid product-category-banner__background">
		<div class="d-flex d-flex-row align-items-center h-100">
			<div class="container-fluid">
				<div class="row">
					<div class="col-md-6 col-sm-6 col-xs-6">
						  <div class="text-primary text-uppercase mb-2 font-weight-bold">
								Products
							</div>
							<h1 class="mb-3">${htmlUtil.escape(title)}</h1>				
							<p>
								${htmlUtil.escape(description)}
							</p>
					</div>
					<div class="col-md-6 col-sm-6 col-xs-6">		
					</div>						
				</div>
			</div>
		</div>
	</div>
</div>