<#assign aui = taglibLiferayHash["/WEB-INF/tld/aui.tld"] />

<#assign layoutEntries = breadcrumbUtil.getLayoutEntries(request) />
<#assign firstLayoutEntry = layoutEntries[0] />
<#assign lastEntry = entries[entries?size - 1] />

<ul class="breadcrumb breadcrumb-horizontal">

	<#if entries?size &gt; 1>
    	<li class="current-parent">
			<a href="${firstLayoutEntry.getURL()}">Home</a>
		</li>
		&nbsp;&nbsp;|&nbsp;&nbsp;
	</#if>

	<li class="active last">
		<a href="${lastEntry.getURL()}">${lastEntry.getTitle()}</a>
	</li>

</ul>
