<div>
<#if (BannerImage.getData())?? && BannerImage.getData() != "">
	<img alt="${BannerImage.getAttribute("alt")}" data-fileentryid="${BannerImage.getAttribute("fileEntryId")}" src="${BannerImage.getData()}" />
</#if>
	<div>
		<p class="courseSubtext">

<#if (Subtext.getData())??>
	${Subtext.getData()}
</#if>
	</p>

	<h4 class="courseTitle">
<#if (Title.getData())??>
	${Title.getData()}
</#if>
	</h4>
	</div>

	<a class="btn btn-primary courseButton" href=${LinkButton.getData()}>
<#if (Button.getData())??>
	${Button.getData()}
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