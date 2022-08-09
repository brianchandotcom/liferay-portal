<div>
<#if (courseBannerImage.getData())?? && courseBannerImage.getData() != "">
	<img alt="${courseBannerImage.getAttribute("alt")}" data-fileentryid="${courseBannerImage.getAttribute("fileEntryId")}" src="${courseBannerImage.getData()}" />
</#if>
	<div>
		<p class="courseSubtext">

		<#if (courseSubText.getData())??>
			${courseSubText.getData()}
		</#if>
	</p>

	<h4 class="courseTitle">
		<#if (courseTitleText.getData())??>
			${courseTitleText.getData()}
		</#if>
	</h4>
	</div>

	<a class="btn btn-primary courseButton" href=${courseButtonLink.getData()}>
		<#if (courseButton.getData())??>
			${courseButton.getData()}
		</#if>
	</a>
</div>

<style>

	.courseSubtext{
		color: gray;
		font-weight: bold;
		padding-left: 24px;
		margin-bottom: 8px;
		margin-top: 24px;
		}

	.courseTitle{
		font-weight: bolder;
		padding-left: 24px;
		margin-bottom: 32px;
		}

	.courseButton{
		font-weight: bold;
		margin-left: 24px;
		}
</>