<div class="product-highlight speedwell-slider" data-will-load>
	<#if Image2x9w.getSiblings()?has_content>
<script class="slider-dataset" type="application/ld+json">
			[
			<#list Image2x9w.getSiblings() as cur_Image2x9w>
				{
					"captionText": "${cur_Image2x9w.captionText.getData()}",
					"captionCategory": "${cur_Image2x9w.captionCategory.getData()}",
					"captionButtonLabel": "${cur_Image2x9w.captionButtonLabel.getData()}",
					"captionButtonUrl": "${cur_Image2x9w.captionButtonUrl.getFriendlyUrl()}",
					"backgroundImageUrl": "${cur_Image2x9w.getData()}",
					"captionImageUrl": "${cur_Image2x9w.captionImage.getData()}"
				}<#sep>,
			</#list>
			]
		</script>
	</#if>

	<div class="bg-slider" data-slide-type="backgroundImage"></div>

<div class="container">
<div class="row">
<div class="product-hint">
				<div class="hint-image-slider" data-slide-type="captionImage"></div>

<div class="hint-text">
					<span class="hint-text__category" data-slide-type="captionCategory"></span>
					<span class="hint-text__name-slider" data-slide-type="captionText"></span>
					<span data-slide-type="captionButton"></span>
				</div>

<div class="product-hint__controls">
<button class="product-hint__control product-hint__control--prev">
						<span><</span>
					</button>
<button class="product-hint__control product-hint__control--next">
						<span>></span>
					</button>
				</div>
			</div>
		</div>
	</div>

	<script>
		var Speedwell = Speedwell || { features: {} };

		Speedwell.features.sliderCallbacks =
				Speedwell.features.sliderCallbacks || [];

		Speedwell.features.sliderCallbacks.push(function(component) {
			function setupDOMSlide(state, index, dataset) {
				const backgroundImage = {
					container: this.sliderWrapper.querySelector('[data-slide-type="backgroundImage"]'),
					slideClass: 'bg-slider__slide'
				}, captionImage = {
					container: this.sliderWrapper.querySelector('[data-slide-type="captionImage"]'),
					slideClass: 'hint-image-slider__slide'
				}, captionText = {
					container: this.sliderWrapper.querySelector('[data-slide-type="captionText"]'),
					slideClass: 'hint-text__name'
				}, captionCategory = {
					container: this.sliderWrapper.querySelector('[data-slide-type="captionCategory"]')
				}, captionButton = {
					container: this.sliderWrapper.querySelector('[data-slide-type="captionButton"]'),
					slideClass: 'hint-text__call-to-action'
				};

				const backgroundImageSlide = window.document.createElement('div'),
						captionImageSlide = window.document.createElement('div'),
						captionTextSlide = window.document.createElement('span'),
						captionCategorySlide = window.document.createElement('span'),
						captionButtonSlide = window.document.createElement('a');

				backgroundImageSlide.classList.add(backgroundImage.slideClass);
				captionImageSlide.classList.add(captionImage.slideClass);
				captionTextSlide.classList.add(captionText.slideClass);
				captionButtonSlide.classList.add(captionButton.slideClass);

				const backgroundImageSlideImg = window.document.createElement('div');

				backgroundImageSlideImg.setAttribute('style',
						'background-image: url(' + dataset[index].backgroundImageUrl + ')');
				backgroundImageSlide.appendChild(backgroundImageSlideImg);

				captionImageSlide.setAttribute('style',
						'background-image: url(' + dataset[index].captionImageUrl + ')');
				captionTextSlide.innerText = dataset[index].captionText;

				captionButtonSlide.href = dataset[index].captionButtonUrl;
				captionButtonSlide.innerText = dataset[index].captionButtonLabel;
				captionButtonSlide.role = 'button';
				captionCategorySlide.innerText = dataset[index].captionCategory;

				backgroundImageSlide.dataset.state = state;
				captionButtonSlide.dataset.state = state;
				captionCategorySlide.dataset.state = state;
				captionImageSlide.dataset.state = state;
				captionTextSlide.dataset.state = state;

				backgroundImage.container.appendChild(backgroundImageSlide);
				captionButton.container.appendChild(captionButtonSlide);
				captionCategory.container.appendChild(captionCategorySlide);
				captionImage.container.appendChild(captionImageSlide);
				captionText.container.appendChild(captionTextSlide);
			}

			function renderSlideContent(sliderWrapper, nextSlideContent) {
				const sliderContainers = Array.from(sliderWrapper.querySelectorAll('[data-slide-type]'));

				sliderContainers.forEach(container => {
					const type = container.dataset.slideType;
					const willBeNextElement = container.querySelector('[data-state="will-be-next"]');

					switch (type) {
						case 'backgroundImage':
							willBeNextElement.querySelector('div').setAttribute(
									'style',
									'background-image: url(' + nextSlideContent.backgroundImageUrl + ')'
							);
							break;
						case 'captionImage':
							willBeNextElement.setAttribute(
									'style',
									'background-image: url(' + nextSlideContent.captionImageUrl + ')'
							);
							break;
						case 'captionText':
							willBeNextElement.innerText = nextSlideContent.captionText;
							break;
						case 'captionCategory':
							willBeNextElement.innerText = nextSlideContent.captionCategory;
							break;
						case 'captionButton':
							willBeNextElement.innerText = nextSlideContent.captionButtonLabel;
							willBeNextElement.href = nextSlideContent.captionButtonUrl;
							break;
						default:
							break;
					}
				});
			}

			component.initialize(setupDOMSlide, renderSlideContent);
		});
	</script>
</div>