<div class="border border-light rounded">
	<#if (BannerImage.getData())?? && BannerImage.getData() != "">
		<img alt="${BannerImage.getAttribute("alt")}" data-fileentryid="${BannerImage.getAttribute("fileEntryId")}" src="${BannerImage.getData()}" class="rounded-top"/>
	</#if>
	<div>
		<p class="font-weight-bold mb-2 mt-4 pl-4 text-secondary">
			COURSES
		</p>

		<h4 class="font-weight-bold mb-4 pl-4">
			<#if (Title.getData())??>
				${Title.getData()}
			</#if>
		</h4>
	</div>

	<a class="btn btn-primary font-weight-bold mb-4 ml-4" href=${LinkButton.getData()}>
		Start learning
	</a>
</div>