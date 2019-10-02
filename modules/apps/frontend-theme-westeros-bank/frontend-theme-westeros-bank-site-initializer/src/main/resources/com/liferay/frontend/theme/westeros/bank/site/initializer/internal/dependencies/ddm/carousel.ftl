<style>
	#[@liferay_portlet.namespace].wb-carousel-wrapper .item-wrapper-image {
		background-size: cover;
		padding-bottom: 100%;

		 @media only screen and (min-width: 768px) {
			bottom: 0;
			left: 0;
			padding-bottom: 0;
			position: absolute;
			top: 0;
		}
	}
</style>

<section class="container-fluid container-fluid-max-xl wb-carousel-wrapper" id="<@portlet.namespace />">
	<div class="carousel slide" data-ride="carousel" id="main-carousel">
		<#if ItemHeading.getSiblings()?has_content>

			<ol class="carousel-indicators d-md-block d-none">
				<#list ItemHeading.getSiblings() as cur_ItemCounter>
					<#if cur_ItemCounter?counter == 1>
						<li class="active" data-slide-to="0" data-target="#main-carousel"></li>
					<#else>
						<li data-slide-to="${cur_ItemCounter?counter - 1}" data-target="#main-carousel"></li>
					</#if>
				</#list>
			</ol>

			<div class="carousel-inner gallery-xxl" role="listbox">
				<#list ItemHeading.getSiblings() as cur_ItemHeading>
					<#if cur_ItemHeading?counter == 1>
						<div class="active carousel-item item">
					<#else>
						<div class="carousel-item item">
					</#if>
						<div class="item-wrapper">
							<div class="col-sm-5 item-wrapper-image" style="background-image: url(${cur_ItemHeading.ItemImage.getData()})"></div>

							<div class="col-sm-6 item-wrapper-text ml-auto">
								<p class="small text-uppercase">
									${cur_ItemHeading.OpeningShortText.getData()}
								</p>

								<h2>${cur_ItemHeading.getData()}</h2>

								<p class="lead">
									${cur_ItemHeading.ItemParagraph.getData()}
								</p>

								<p>
									<a class="btn btn-secondary btn-wb-default text-uppercase" href="${cur_ItemHeading.ItemLinkText.ItemLinkURL.getData()}">${cur_ItemHeading.ItemLinkText.getData()}</a>
								</p>
							</div>
						</div>
					</div>
				</#list>
			</div>
		</#if>
	</div>
</section>