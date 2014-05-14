<ul class="breadcrumb breadcrumb-horizontal">
	<#if entries?size &gt; 1>
		<#assign group = breadcrumbHelper.getScopeGroupBreadcrumbEntry(themeDisplay) />

		<li class="current-parent">
			<a href="${group.getURL()}">Site Home</a>
			&nbsp;&nbsp;|&nbsp;&nbsp;
		</li>
	</#if>

	<li class="active last">
		<a href="${entries?last.getURL()}">${entries?last.getTitle()}</a>
	</li>
</ul>