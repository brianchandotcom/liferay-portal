<#assign
	liferay_theme = PortletJspTagLibs["/META-INF/liferay-theme.tld"]
	namespace = '_${randomNamespace}'
/>

<@liferay_theme["defineObjects"] />

<div class="row">
	<div class="carousel commerce-carousel slide" data-ride="carousel" id="minetteCarousel${namespace}">
		<div class="carousel-inner">
			<#if CAROUSELIMAGE.getSiblings()?has_content>
				<#assign listSize = CAROUSELIMAGE.getSiblings()?size />

				<#list CAROUSELIMAGE.getSiblings() as cur_CAROUSELIMAGE>
					<#assign
						active = ''
						carouselImageColumnCSSClasses = ''
						carouselTextColumnCSSClasses = ''
					/>

					<#if cur_CAROUSELIMAGE?counter == 1>
						<#assign
							active = ' active'
						/>
					</#if>

					<#if cur_CAROUSELIMAGE.CAROUSELIMAGECOLUMNCSSCLASSES?? && cur_CAROUSELIMAGE.CAROUSELIMAGECOLUMNCSSCLASSES?has_content>
						<#assign
							carouselImageColumnCSSClasses = ' ${cur_CAROUSELIMAGE.CAROUSELIMAGECOLUMNCSSCLASSES.getData()}'
						/>
					</#if>

					<#if cur_CAROUSELIMAGE.TEXTCOLUMNCSSCLASSES?? && cur_CAROUSELIMAGE.TEXTCOLUMNCSSCLASSES?has_content>
						<#assign
							carouselTextColumnCSSClasses = ' ${cur_CAROUSELIMAGE.TEXTCOLUMNCSSCLASSES.getData()}'
						/>
					</#if>

					<div class="carousel-item${active}">
						<div class="autofit-float-sm-down autofit-row autofit-row-center">
							<#assign
								solo = ''
							/>

							<#if cur_CAROUSELIMAGE.TEXTCOLUMNHTMLCONTENT.getData()?? && cur_CAROUSELIMAGE.TEXTCOLUMNHTMLCONTENT.getData()?has_content>
								<#if !cur_CAROUSELIMAGE.getData()?? || !cur_CAROUSELIMAGE.getData()?has_content>
									<#assign
										solo = ' commerce-carousel-single-column'
									/>
								</#if>

								<div class="carousel-text-column autofit-col${carouselTextColumnCSSClasses}${solo}">
									<div class="autofit-section">
										${cur_CAROUSELIMAGE.TEXTCOLUMNHTMLCONTENT.getData()}
										<div class="commerce-carousel-counter">${cur_CAROUSELIMAGE?counter} of ${listSize}</div>
									</div>
								</div>
							</#if>
							<#if cur_CAROUSELIMAGE.getData()?? && cur_CAROUSELIMAGE.getData()?has_content>
								<#if !cur_CAROUSELIMAGE.TEXTCOLUMNHTMLCONTENT.getData()?? || !cur_CAROUSELIMAGE.TEXTCOLUMNHTMLCONTENT.
								getData()?has_content>
									<#assign
										solo = ' commerce-carousel-single-column'
									/>
								</#if>
								<div class="carousel-image-column autofit-col${carouselImageColumnCSSClasses}${solo}">
									<img alt="${cur_CAROUSELIMAGE.getAttribute("alt")}" data-fileentryid="${cur_CAROUSELIMAGE.getAttribute("fileEntryId")}" src="${cur_CAROUSELIMAGE.getData()}" />

									<#if solo?has_content>
										<div class="commerce-carousel-counter">${cur_CAROUSELIMAGE?counter} of ${listSize}</div>
									</#if>
								</div>
							</#if>
						</div>
					</div>
				</#list>
			</#if>
		</div>

		<a class="carousel-control-prev commerce-carousel-control" data-slide="prev" href="#minetteCarousel${namespace}" role="button">
			<svg aria-hidden="true" class="lexicon-icon lexicon-icon-angle-left" focusable="false"><use xlink:href="${themeDisplay.getPathThemeImages()}/lexicon/icons.svg#angle-left" /></svg>
			<span class="sr-only">Previous</span>
		</a>

		<a class="carousel-control-next commerce-carousel-control" data-slide="next" href="#minetteCarousel${namespace}" role="button">
			<svg aria-hidden="true" class="lexicon-icon lexicon-icon-angle-right" focusable="false"><use xlink:href="${themeDisplay.getPathThemeImages()}/lexicon/icons.svg#angle-right" /></svg>
			<span class="sr-only">Next</span>
		</a>
	</div>
</div>