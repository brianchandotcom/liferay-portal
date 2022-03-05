<style>
	#quote-comparison {
		background-color: #FFF;
		border-radius: 8px;
		border: 1px solid #A6C2FF;
		box-shadow: 0px 10px 30px rgba(77, 133, 255, 0.09);
		height: 576px;
		width: 292px;
	}

	#quote-comparison .most-popular {
		background-color: #F4870B;
		border-radius: 8px 8px 0 0;
		color: #FFF;
		font-size: 18px;
		font-weight: 600;
		height: 25px;
		text-align: center;
	}

	#quote-comparison .no-most-popular {
		background-color: #FFF;
		border-radius: 8px 8px 0 0;
		height: 25px;
	}

	#quote-comparison .quote-content {
		display: flex;
		flex-direction: column;
		height: 549px;
		justify-content: space-between;
		padding: 24px 24px 32px;
	}

	#quote-comparison .quote-header {
		text-align: center;
	}

	#quote-comparison .quote-header .title {
		color: #4D85FF;
		font-size: 24px;
		font-weight: 700;
		line-height: 28px;
	}

	#quote-comparison .quote-header .value {
		color: #09101D;
		display: flex;
		flex-direction: row;
		font-size: 49px;
		font-weight: 800;
		justify-content: center;
		line-height: 56px;
	}

	#quote-comparison .quote-header .value div {
		color: #2F313D;
		font-size: 37px;
		font-weight: 300;
	}

	#quote-comparison .quote-header .subtitle {
		color: #606167;
		font-size: 11px;
		font-weight: 500;
		line-height: 16px;
	}

	#quote-comparison .quote-header .subtitle span {
		color: #4D85FF;
	}

	#quote-comparison ul {
		margin: 0;
		padding: 0;
	}

	#quote-comparison li {
		color: #606167;
		display: flex;
		flex: auto;
		font-size: 13px;
		font-weight: 500;
		justify-content: space-between;
		list-style: none;
	}

	#quote-comparison li:not(:last-child) {
		margin-bottom: 24px;
	}

	#quote-comparison li .checkIcon,
	#quote-comparison li .closeIcon {
		align-items: center;
		display: flex;
	}

	#quote-comparison li .closeIcon {
		color: #a0a0a4;
	}

	<#if checkIcon.getData() ?? && checkIcon.getData() != "">
		#quote-comparison li .checkIcon::before {
			-webkit-mask-size: cover;
			-webkit-mask: url(${checkIcon.getData()}) no-repeat 50% 50%;
			background-color: #4D85FF;
			content: "";
			display: inline-block;
			height: 16px;
			margin-right: 4px;
			mask: url(${checkIcon.getData()}) no-repeat 50% 50%;
			width: 16px;
		}
	</#if>

	<#if closeIcon.getData() ?? && closeIcon.getData() != "">
		#quote-comparison li .closeIcon::before {
			-webkit-mask-size: cover;
			-webkit-mask: url(${closeIcon.getData()}) no-repeat 50% 50%;
			background-color: #a0a0a4;
			content: "";
			display: inline-block;
			height: 16px;
			margin-right: 4px;
			mask: url(${closeIcon.getData()}) no-repeat 50% 50%;
			width: 16px;
		}
	</#if>

	#quote-comparison .quote-footer {
		border: none;
		padding: 0;
	}

	#quote-comparison #purchase {
		background: none;
		border-radius: 4px;
		border: 1px solid #4C85FF;
		color: #4C85FF;
		cursor: pointer;
		font-size: 16px;
		font-weight: 700;
		padding: 0;
		width: 215px;
		height: 56px;
		transition: all 0.2s;
	}

	#quote-comparison #purchase:hover {
		border: 1px solid #295ccc;
		color: #295ccc;
	}

	#quote-comparison #purchase.most-popular {
		background: #4C85FF;
		border-radius: 4px;
		border: 1px solid #4C85FF;
		color: #FFF;
		cursor: pointer;
		font-size: 16px;
		font-weight: 700;
		padding: 0;
		width: 215px;
		height: 56px;
		transition: background 0.2s;
	}

	#quote-comparison #purchase.most-popular:hover {
		background: #295ccc;
	}

	#quote-comparison #details {
		background: none;
		border: none;
		color: #7D7E85;
		cursor: pointer;
		font-size: 16px;
		font-weight: 500;
		padding-top: 16px;
		text-decoration-line: underline;
	}
</style>

<div id="quote-comparison">
	<#if (mostPopular.getData())?? && mostPopular.getData() != "">
		<div class="most-popular">${mostPopular.getData()}</div>
	<#else>
		<div class="no-most-popular"></div>
	</#if>

	<div class="quote-content">
		<div class="quote-header">
			<#if (title.getData())??>
				<div class="title">${title.getData()}</div>
			</#if>
			<#if (value.getData())??>
				<div class="value">${value.getData()}</div>
			</#if>
			<#if (subtitle.getData())??>
				<div class="subtitle">
					${subtitle.getData()}
				</div>
			</#if>
		</div>

		<div class="quote-body">
			<#if (listTerms.getData())??>
				${listTerms.getData()}
			</#if>
		</div>

		<div class="quote-footer">
			<div class="d-flex justify-content-center">
				<button <#if (mostPopular.getData())?? && mostPopular.getData() != "">class="most-popular"</#if> id="purchase" onclick="event.preventDefault();" type="button">PURCHASE THIS POLICY</button>
			</div>

			<div class="d-flex justify-content-center">
				<button type="button" id="details" onclick="event.preventDefault();">Policy Details</button>
			</div>
		</div>
	</div>
</div>