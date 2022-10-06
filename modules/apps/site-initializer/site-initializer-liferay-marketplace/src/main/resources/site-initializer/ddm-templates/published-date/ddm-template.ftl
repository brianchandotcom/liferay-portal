<#if (displayDate.getData())?has_content>
	<#assign date = displayDate.getData()?datetime("EEE MMM dd HH:mm:ss zzz yyyy") />

	<div class="category-container bg-whiteColor d-lg-block mb-lg-1 ml-lg-0 mr-lg-0 mt-lg-0 pb-lg-4 pl-lg-4 pr-lg-4 pt-lg-4 text-lg-left mb-1 pt-4 pb-4 pl-4 pr-4 mb-sm-1 pt-sm-4 pb-sm-4 pl-sm-4 pr-sm-4 mb-md-1 pt-md-4 pb-md-4 pl-md-4 pr-md-4" style="background-color: var(--white);border-radius: var(--border-radius-sm);border-style: solid; border-width: 0px;">
		<div class="f-text font-size-paragraph-base font-size-small-caps">
			${languageUtil.get(locale, "published-date", "Published Date")}
		</div>

		<span class="f-text font-size-paragraph-base">
			${date?string["MMMM dd, yyyy"]}
		</span>
	</div>
</#if>