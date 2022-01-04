<style>
	.back-to-edit-info {
		background-color: transparent;
		border: 1px solid #7D7E85;
		box-sizing: border-box;
		color: #7D7E85;
		font-size: 14px;
		font-style: normal;
		letter-spacing: 0.03em;
		line-height: 24px;
		padding: 10px
	}

	#tip {
		width: 368px;
	}

	#tip.hide {
		opacity: 0;
		pointer-events: none;
		transition: opacity .5s linear;
	}

	#tip li {
		align-items: center;
		color: #606167;
		display: flex;
		font-size: 16px;
		font-weight: bold;
	}

	#tip li::before {
		-webkit-mask-size: cover;
		-webkit-mask: url(${listIcon.getData()}) no-repeat 50% 50%;
		background-color: #999;
		content: "";
		display: inline-block;
		flex-shrink: 0;
		height: 16px;
		margin-right: 4px;
		mask: url(${listIcon.getData()}) no-repeat 50% 50%;
		width: 16px;
	}

	#tip ul {
		list-style-type: none;
		margin: 0;
		padding: 0;
	}

	#tip #dismiss {
		text-decoration-line: underline;
	}

	<#if titleIcon.getData() ?? && titleIcon.getData() != "">
		#tip .title::before {
			-webkit-mask-size: cover;
			-webkit-mask: url(${titleIcon.getData()}) no-repeat 50% 50%;
			background-color: #98999B;
			content: "";
			display: inline-block;
			height: 20px;
			margin-right: 5px;
			mask: url(${titleIcon.getData()}) no-repeat 50% 50%;
			width: 20px;
		}
	</#if>

	<#if externalLinkIcon.getData() ?? && externalLinkIcon.getData() != "">
		#tip .external_link a::after {
			-webkit-mask-size: cover;
			-webkit-mask: url(${externalLinkIcon.getData()}) no-repeat 50% 50%;
			background-color: #4C85FF;
			content: "";
			display: inline-block;
			height: 15px;
			margin-left: 5px;
			mask: url(${externalLinkIcon.getData()}) no-repeat 50% 50%;
			width: 20px;
		}
	</#if>

	@media only screen and (max-width: 992px) {
		#tip {
			width: 100%;
		}
}
</style>

<#assign applicationNameSpace = randomNamespace />

<script>
	function ${applicationNameSpace}backToEdit() {
		let siteName = '';

		try {
			const {pathname} = new URL(Liferay.ThemeDisplay.getCanonicalURL());
			const urlPaths = pathname.split('/').filter(Boolean);
			siteName = '/' + urlPaths.slice(0, urlPaths.length - 1).join('/');
		} catch (error) {
			console.warn(error);
		}

		window.location.href = siteName + '/get-a-quote';
		localStorage.setItem('raylife-back-to-edit', true);
	}
</script>

<div class="bg-neutral-1 p-4 rounded" id="tip">
	<#if (title.getData())??>
		<h4 class="title font-weight-bolder">${title.getData()}</h4>
	</#if>

	<#if (subtitle.getData())??>
		<p class="subtitle">
			${subtitle.getData()}
		</p>
	</#if>

	<#if (actionList.getData())??>
		${actionList.getData()}
	</#if>

	<#if (description.getData())??>
		<p class="description">
			${description.getData()}
		</p>
	</#if>

	<#if (externalLink.getData())??>
		<div class="external_link">
			${externalLink.getData()}
		</div>
	</#if>

	<#if pageOptions.getData()?contains("showBacktoEdit")>
		<button class="back-to-edit-info btn btn-ghost btn-style-neutral font-weight-bold mb-1 p-2 text-uppercase" onclick="${applicationNameSpace}backToEdit()">
		<#if (backIcon.getData())??>
			<img src="${backIcon.getData()}" />
		</#if>
			Back to Edit Info
		</button>
	</#if>

	<#if pageOptions.getData()?contains("dismissible")>
		<div class="d-flex justify-content-center">
			<button class="btn btn-link font-weight-normal p-0 text-neutral-8" id="dismiss" onclick="event.preventDefault(); document.getElementById('tip').classList.add('hide');" type="button">Dismiss</button>
		</div>
	</#if>
</div>