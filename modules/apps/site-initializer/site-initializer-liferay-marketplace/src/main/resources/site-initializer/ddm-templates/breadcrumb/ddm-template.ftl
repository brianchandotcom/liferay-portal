<#assign
	info_display_object_provider = request.getAttribute("LAYOUT_DISPLAY_PAGE_OBJECT_PROVIDER")!{}
	layoutSet = themeDisplay.getLayoutSet()
	layoutSetDisplayURL = portal.getLayoutSetDisplayURL(layoutSet, true)
	layoutSetCanonicalURL = portal.getCanonicalURL(layoutSetDisplayURL, themeDisplay, layout)
/>

<div class="flex font-size-paragraph-small solutions-details-breadcrumb">
	<div class="breadcrumb-left-desktop flex flex-wrap">
		<a class="flex pt-1" href="${layoutSetCanonicalURL}">
			<svg fill="none" height="16" viewBox="0 0 16 16" width="16" xmlns="http://www.w3.org/2000/svg">
				<mask height="12" id="mask0_10823_47616" maskUnits="userSpaceOnUse" style="mask-type:alpha" width="14" x="1" y="2">
					<path d="M6.66521 12.8852V9.55182H9.33187V12.8852C9.33187 13.2518 9.63187 13.5518 9.99854 13.5518H11.9985C12.3652 13.5518 12.6652 13.2518 12.6652 12.8852V8.21849H13.7985C14.1052 8.21849 14.2519 7.83849 14.0185 7.63849L8.44521 2.61849C8.19187 2.39182 7.80521 2.39182 7.55187 2.61849L1.97854 7.63849C1.75187 7.83849 1.89187 8.21849 2.19854 8.21849H3.33187V12.8852C3.33187 13.2518 3.63187 13.5518 3.99854 13.5518H5.99854C6.36521 13.5518 6.66521 13.2518 6.66521 12.8852Z" fill="black" />
				</mask>

				<g mask="url(#mask0_10823_47616)">
					<rect fill="#858C94" height="16" width="16" />
				</g>
			</svg>
		</a>

		<#list entries as curEntry>
			<#assign
				curTitle = curEntry.getTitle()
				curURL = curEntry.getURL()
			/>

			<#if info_display_object_provider?has_content>
				<#if entries?has_content && stringUtil.equals(curTitle, "Marketplace")>
					<#assign cpDefinition = info_display_object_provider.getDisplayObject() />

					<div class="color-neutral-3">&nbsp;/&nbsp;</div>

					<a class="color-neutral-3" href="/">
						App Marketplace
					</a>

					<div class="color-neutral-3">&nbsp;/&nbsp;</div>

					<a class="color-neutral-3" href="${currentURL}">
						${cpDefinition.getName()}
					</a>
				</#if>
			<#elseif entries?has_content && !stringUtil.equals(curTitle, "Liferay") && !stringUtil.equals(curTitle, "Marketplace")>
				<div class="color-neutral-3">&nbsp;/&nbsp;</div>

				<#if curTitle == "Solutions Marketplace">
					<a class="color-neutral-3" href="/solutions">
				<#else>
					<a class="color-neutral-3" href="${curURL}">
				</#if>
					${curTitle}
				</a>
			</#if>
		</#list>
	</div>
</div>