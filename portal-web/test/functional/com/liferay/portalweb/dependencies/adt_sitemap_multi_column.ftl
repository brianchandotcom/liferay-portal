<#if entries?has_content>
	<@liferay_aui.row>
		<#list entries as entry>
			<@liferay_aui.col
				width = 25
			>
				<div class="results-header">
					<h3>
						<a

						<#assign layoutType = entry.getLayoutType() />

						<#if layoutType.isBrowsable()>
							href="${portalUtil.getLayoutURL(entry, themeDisplay)}"
						</#if>

						>${entry.getName(locale)}</a>
					</h3>
				</div>

				<@displayPages
					pages = entry.getChildren()
					depth = 1
				/>
			</@liferay_aui.col>
		</#list>
	</@liferay_aui.row>
</#if>

<#macro displayPages
	pages
	depth
>
	<#if pages?has_content && ((depth < displayDepth?number) || (displayDepth?number == 0))>
		<ul class="child-pages">
			<#list pages as page>
				<li>
					<a

					<#assign pageType = page.getLayoutType() />

					<#if pageType.isBrowsable()>
						href="${portalUtil.getLayoutURL(page, themeDisplay)}"
					</#if>

					>${page.getName(locale)}</a>

					<@displayPages
						pages = page.getChildren()
						depth = depth + 1
					/>
				</li>
			</#list>
		</ul>
	</#if>
</#macro>