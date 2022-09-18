<style>
	.partner-portal-body {
		height: 160px;
	}
</style>

<#if (Body.getData())??>
		<div class="text-paragraph overflow-auto partner-portal-body mb-3">${Body.getData()}</div>
	</#if>

	<#if (LinkToReadMore.getData())??>
		<a class="btn btn-primary" href="${htmlUtil.escape(LinkToReadMore.getData())}" target="_blank" rel="noopener noreferrer">Read More</a>
	</#if>