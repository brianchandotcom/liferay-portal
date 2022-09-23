<style>
	<#include "${templatesPath}/LEARN-STYLES">
</style>
<#include "${templatesPath}/SVG">

<#assign groupFriendlyURL = "/web" + themeDisplay.getScopeGroup().getFriendlyURL() />

<div class="container-fluid documentations main-content mt-6" role="main">
	<div class="row">
		<div class="col-12 p-0 page-alert" id="pageAlertContainer">
			<div class="page-alert-hidden" id="pageAlert" role="alert">
				<@clay["alert"]
					message=languageUtil.get(locale, "important-as-we-revamp-and-transition-our-documentation-to-this-site", "<strong class=\"lead\">IMPORTANT: </strong>As we revamp and transition our documentation to this site, you may find the articles you need on <a href=\"https://help.liferay.com/hc\"><strong>Liferay's Help Center</strong></a>.")
					displayType="info"
					defaultTitleDisabled=true
					dismissible=true
				/>
			</div>
		</div>

		<div class="col-12 col-md-2 doc-nav-wrapper mobile-nav-hide">
			<div class="doc-nav-wrapper-inner">
				<div class="info-bar">
					<label for="productDocumentationSelector">
						<select class="form-control" id="productDocumentationSelector">
							<option data-href="${groupFriendlyURL}/w/analytics-cloud/index.html" ${(product.getData() == "analytics-cloud")?then("selected", "")} value="analytics-cloud">
								${languageUtil.get(locale, "analytics-cloud", "Analytics Cloud")}
							</option>

							<option data-href="${groupFriendlyURL}/w/commerce/index.html" ${(product.getData() == "commerce")?then("selected", "")} value="commerce">
								${languageUtil.get(locale, "commerce", "Commerce")}
							</option>

							<option data-href="${groupFriendlyURL}/w/dxp/index.html" ${(product.getData() == "dxp")?then("selected", "")} value="dxp">
								${languageUtil.get(locale, "dxp-portal", "DXP / Portal")}
							</option>

							<option data-href="${groupFriendlyURL}/w/dxp-cloud/index.html" ${(product.getData() == "dxp-cloud")?then("selected", "")} value="dxp-cloud">
								${languageUtil.get(locale, "dxp-cloud", "DXP Cloud")}
							</option>

							<option data-href="${groupFriendlyURL}/w/reference/index.html" ${(product.getData() == "reference")?then("selected", "")} value="reference">
								${languageUtil.get(locale, "reference", "Reference")}
							</option>
						</select>
					</label>
				</div>

				<div class="d-md-none mobile-doc-nav-toggler" id="mobileDocNavToggler">${languageUtil.get(locale, "documentation-menu", "Documentation Menu")}
					<button
						aria-label="Expand Documentation Menu" class="btn expand-btn" onclick="javascript:;"
						title="Expand Documentation Menu" type="button">
						<svg class="lexicon-icon lexicon-icon-product-menu" role="presentation">
							<use xlink:href="#product-menu" />
						</svg>
					</button>

					<button
						aria-label="Close Documentation Menu" class="btn collapse-btn" onclick="javascript:;"
						title="Close Documentation Menu" type="button">
						<svg class="lexicon-icon lexicon-icon-times" role="presentation">
							<use xlink:href="#times" />
						</svg>
					</button>
				</div>

				<div class="doc-nav">
					<div class="admonition hide hilighting-alert important" id="highlightAlert">
						<p class="admonition-title">
							<span class="title-text">
								${languageUtil.get(locale, "highlighting", "Highlighting")}

								<span id="highlightTextMatch"></span>
							</span>
						</p>

						<a class="remove-link" href="javascript:;" id="removeHighlightLink">
							${languageUtil.get(locale, "remove-highlighting", "Remove Highlighting")}
						</a>
					</div>

					<a class="back-link btn btn-link btn-monospaced d-flex flex-row justify-content-start" href="${parentMarkdownFilePath.getData()}" id="backLink">
						<svg class="lexicon-icon lexicon-icon-angle-left" role="presentation" viewBox="0 0 512 512">
							<use xlink:href="#angle-left" />
						</svg>
						${languageUtil.get(locale, "go-back", "Go Back")}
					</a>

					<#if (navigationHTML.getData())??>
						${navigationHTML.getData()}
					</#if>
				</div>

				<div class="d-md-flex d-none doc-nav-footer">
					<@liferay_ui["language"]
						ddmTemplateGroupId=groupId
						ddmTemplateKey="LANGUAGE_MENU"
						displayCurrentLocale=true
						languageIds=localeUtil.toLanguageIds(languageUtil.getAvailableLocales(themeDisplay.getSiteGroupId()))
						useNamespace=false
					/>
				</div>
			</div>
		</div>

		<div class="col-12 col-md-10 doc-body">
			<div class="col-12 general-info p-0">
				<div class="col-12 info-bar">
					<div class="breadcrumb-wrapper col-12 col-md-7 offset-md-1">
						<#if (breadcrumbHTML.getData())??>
							${breadcrumbHTML.getData()}
						</#if>
					</div>

					<div class="actions col-md-2 d-md-block d-none offset-md-1">
						<a
							aria-label="${languageUtil.get(locale, 'github-icon', 'Github Icon')}"
							href="<#if (githubEditLink.getData())??> ${githubEditLink.getData()}</#if>"
							title="${languageUtil.get(locale, 'contribute-on-github', 'Contribute on Github')}">
							<svg>
								<use xlink:href="#edit"></use>
							</svg>
						</a>
					</div>
				</div>
			</div>

			<div class="col-12 doc-content" id="docContent">
				<div class="row">
					<div class="article-body col-12 col-md-8">
						<#if (content.getData())??>
							${content.getData()}
						</#if>
						<#if (landingPage.getData())??>
							<#if landingPage.getData() == "true">
								<#include "${templatesPath}/LANDING-PAGE">
							</#if>
						</#if>
						<div class="autofit-padded-no-gutters-x autofit-row help-center-footer">
							<div class="autofit-col">
								<div class="icon-container">
									<svg class="lexicon-icon liferay-waffle-icon" focusable="false" role="presentation" viewBox="0 0 512 512">
										<use xlink:href="#liferay-waffle" />
									</svg>
								</div>
							</div>

							<div class="autofit-col autofit-col-expand">
								<h3>${languageUtil.get(locale, "not-finding-what-you-are-looking-for", "Not finding what you're looking for?")}</h3>

								<p class="text-secondary">${languageUtil.get(locale, "pardon-our-dust-as-we-revamp", "Pardon our dust as we revamp and transition our product documentation to this site. If something seems missing, please check Liferay Help Center documentation for Liferay DXP 7.2 and previous versions.")}</p>

								<a href="https://help.liferay.com/hc/en-us/categories/360001749912">
									<strong>${languageUtil.get(locale, "try-liferays-help-center", "Try Liferay's Help Center")}</strong>

									<svg class="lexicon-icon lexicon-icon-shortcut" focusable="false" role="presentation" viewBox="0 0 512 512">
										<use xlink:href="#shortcut" />
									</svg>
								</a>
							</div>
						</div>
					</div>

					<div class="col-md-4 d-none d-sm-block toc-container">
						<ul class="nav nav-stacked toc" id="articleTOC"></ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<#noparse>
	<script>
		// Table of contents reading indicator

		const headings = document.querySelectorAll('.article-body h2');

		let activeIndex;
		let targets = [];

		if (headings) {
			const articleTOC = document.getElementById('articleTOC');

			headings.forEach(
				heading => {
					const id = heading.querySelector('a').hash.replace('#', '');

					if (articleTOC) {
						articleTOC.innerHTML += `
					<li class="nav-item">
						<a class="nav-link" href="#${id}" id="toc-${id}">
							${heading.innerText}
						</a>
					</li>`;
					}

					targets.push({ id: id, isIntersecting: false });
				}
			);
		}

		const callback = entries => {
			entries.forEach(entry => {
				const index = targets.findIndex(target => target.id === entry.target.id);

				targets[index].isIntersecting = entry.isIntersecting;

				if (!targets[activeIndex] || !targets[activeIndex].isIntersecting) {
					setActiveIndex()
				}
			});

			if (targets[activeIndex]) {
				toggleActiveClass(targets[activeIndex].id);
			}
		};

		// rootMargin of 157px is header height + info bar height + 24px gutter offset

		const observer = new IntersectionObserver(callback, { rootMargin: '-157px', threshold: [0, 0.2, 0.4, 0.6, 0.8, 1] });

		const setActiveIndex = () => {
			activeIndex = targets.findIndex(target => target.isIntersecting === true);
		};

		const toggleActiveClass = id => {
			targets.forEach(target => {
				const node = document.getElementById(`toc-${target.id}`);

				if (node) {
					node.classList.remove('active');
				}
			});

			const activeNode = document.getElementById(`toc-${id}`);

			if (activeNode) {
				activeNode.classList.add('active');
			}
		}

		targets.forEach(target => {
			const node = target.id ? document.getElementById(target.id) : null;

			if (node) {
				observer.observe(node);

				node.style.cssText = "margin-top: -157px; padding-top: 157px;"
			}
		});

		const productDocumentationSelector = document.getElementById('productDocumentationSelector');

		productDocumentationSelector.addEventListener('change', event => {
			var selectedOption = event.target.options[event.target.selectedIndex];

			window.location.pathname = selectedOption.dataset.href;
		});

		// Left Nav mobile interaction

		const docNavWrapper = document.querySelector('.doc-nav-wrapper');
		const mobileDocNavToggler = document.getElementById('mobileDocNavToggler');

		if (docNavWrapper && mobileDocNavToggler) {
			const togglers = mobileDocNavToggler.querySelectorAll('button');

			togglers.forEach(toggler =>
				toggler.addEventListener('click', () => {
					docNavWrapper.classList.toggle('mobile-nav-hide');
				})
			);
		}
	</script>
</#noparse>

<script>
	<#include "${templatesPath}/HIGHLIGHT-JS">
	<#include "${templatesPath}/PAGE-ALERT-JS">
</script>