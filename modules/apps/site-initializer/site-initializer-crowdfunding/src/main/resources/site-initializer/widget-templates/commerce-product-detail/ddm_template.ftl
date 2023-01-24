<#assign commerce_ui = PortletJspTagLibs["/META-INF/liferay-commerce-ui.tld"]
		 commerceContext = renderRequest.getAttribute("COMMERCE_CONTEXT")
		 commerceChannelId = commerceContext.getCommerceChannelId()
		 commerceAccountId = commerceContext.getCommerceAccount().commerceAccountId 
/>

<style>
	.crowse-product-detail .title{
		color: #28282B;
		font-weight: 600;
		font-size: 2rem;
	}
	
	.crowse-product-detail .sub-info{
		display:flex;
	}
	
	.crowse-product-detail .sub-info span{
		color: #84848B;
		font-size: 1rem;
		margin-right: 15px;
	}
	
	.crowse-product-detail .sidebar{
		background: #FFFFFF;
		border-radius: 12px;
		padding: 30px 25px 20px 25px;
		height: auto;
	}
	
	.crowse-product-detail .text{
		font-size: 1rem;
		font-weight: 400;
		color: #84848B;
		display:block;
	}
	
	.crowse-product-detail h1{
		color: #28282B;
		font-size: 2rem;
		font-weight: 600;
	}
	
	.crowse-product-detail h1.text-sm{
		font-size: 1.5rem;
	}
	
	
	.crowse-product-detail .progress-bar{
		background: #ECEAF8;
		border-radius: 6px;
		height: 12px;
		margin-top: 28px;
	}

	.crowse-product-detail .progress-bar div{
		width: 50%;
		background: var(--color-brand-primary);
		height: 12px;
		border-radius: 6px;
	}
	
	.crowse-product-detail hr{
		background: #F4F7F6;
		height: 2px;
		margin: 20px 0;
	}
	
	.crowse-product-detail .invest{
		border: 2px solid rgba(40, 40, 43, 0.08);
		border-radius: 8px;
		position:relative;
		padding: 10px 15px;
		background: #fff;
	}
	
	.crowse-product-detail .invest input{
		border: none;
	}
	
	.crowse-product-detail .invest input:active,.crowse-product-detail .invest input:focus {
		outline:none;
	}
	
	.crowse-product-detail .invest a{
		position: absolute;
		background: var(--color-brand-primary);
		top: 0;
		bottom:0;
		right: 0;
		border-radius: 0 8px 8px 0;
    	color: #fff;
    	font-weight: 600;
    	width: 100px;
    	display: flex;
    	align-items: center;
    	justify-content: center;
		text-decoration: none;
		cursor: pointer;
	}

	
	.crowse-product-detail .documents{
		padding: 0 20px;
	}

    .crowse-product-detail .documents h1 {
        color: #28282B;
        font-size: 1.5rem;
		margin: 56px 0 10px 0;
    }

    .crowse-product-detail .documents ul {
        list-style: none;
        margin: 0;
        padding: 0;
    }

    .crowse-product-detail .documents ul li a{
        display:flex;
        align-items: center;
		color: #28282B;
    }

    .crowse-product-detail .documents ul li a svg{
        margin-right: 8px;
    }
</style>


<#if cpCatalogEntry??>

	<#assign fundingTarget = "" />
	<#assign location = "" />
	<#assign capitalGrowth = "" />
	<#assign yield = "" />
	<#assign floors = "" />
	<#assign area = "" />
	<#assign propertyType = "" />
	<#assign investmentProfile = "" />
	
	<#list cpContentHelper.getCPDefinitionSpecificationOptionValues(cpCatalogEntry.getCPDefinitionId()) as specification>
			<#assign key = specification.getCPSpecificationOption().key />
			
			<#switch key>
				<#case "funding-target">
					<#assign fundingTarget = specification.getValue(locale) />
					<#break>
				<#case "location">
					<#assign location = specification.getValue(locale) />
					<#break>
				<#case "capital-growth">
					<#assign capitalGrowth = specification.getValue(locale) />
					<#break>
				<#case "yield">
					<#assign yield = specification.getValue(locale) />
					<#break>
				<#case "floors">
					<#assign floors = specification.getValue(locale) />
					<#break>
				<#case "area">
					<#assign area = specification.getValue(locale) />
					<#break>
				<#case "property-type">
					<#assign propertyType = specification.getValue(locale) />
					<#break>
				<#case "investiment-profile">
					<#assign investmentProfile = specification.getValue(locale) />
					<#break>
			</#switch>
	</#list>

	<div class="container crowse-product-detail">
		<div class="row">
			<div class="col-sm-12 col-md-8">
				<h1 class="title">${cpCatalogEntry.getName()}</h1>
				<div class="sub-info">
					<span>
						<svg width="16" height="16" viewBox="0 0 16 16" fill="none" xmlns="http://www.w3.org/2000/svg">
							<path d="M0.44751 3.40133L8.00024 0L15.553 3.30129C16.1497 3.60141 16.1497 4.40172 15.553 4.6018L8.00024 8L0.44751 4.70184C-0.14917 4.40172 -0.14917 3.60141 0.44751 3.40133Z" fill="#84848B"/>
							<path d="M15.6213 7.39977L13.7249 6.59945L12.5276 7.09965L14.5247 8L8.03735 10.9011L1.55103 8L3.64771 7.09965L2.35083 6.49941L0.454346 7.39977C-0.14624 7.59984 -0.14624 8.40016 0.454346 8.70027L8.03735 12.0016L15.6213 8.60336C16.1213 8.40016 16.1213 7.59984 15.6213 7.39977Z" fill="#84848B"/>
							<path d="M15.6184 11.3982L13.7092 10.5979L12.5032 11.0981L14.6125 11.9984L7.98462 14.8996L1.46021 11.9984L3.56958 11.0981L2.36353 10.5979L0.454346 11.3982C-0.148193 11.5983 -0.148193 12.3986 0.454346 12.6987L7.98462 16L15.6155 12.6018C16.1213 12.3986 16.1213 11.5983 15.6184 11.3982Z" fill="#84848B"/>
						</svg>
						${floors} Floors
					</span>
					<span>
						<svg width="16" height="16" viewBox="0 0 16 16" fill="none" xmlns="http://www.w3.org/2000/svg">
							<path fill-rule="evenodd" clip-rule="evenodd" d="M5 3H11C12.1035 3 13 3.89687 13 5V16H11V13H5C3.89648 13 3 12.1031 3 11V0H5V3ZM5 5V11H11V5H5ZM14 11H16V13H14V11ZM2 3H0V5H2V3Z" fill="#84848B"/>
						</svg>
						${area}
					</span>
					<span>
						<svg width="16" height="16" viewBox="0 0 16 16" fill="none" xmlns="http://www.w3.org/2000/svg">
							<path fill-rule="evenodd" clip-rule="evenodd" d="M3.5 4.28851C3.5 1.92438 5.51465 0 7.99121 0C10.4678 0 12.4824 1.92438 12.4824 4.29163C12.4824 5.59222 9.55664 13.2086 8.66211 15.5103C8.55273 15.7879 8.28711 15.9719 7.99121 15.9719C7.69531 15.9719 7.42969 15.7879 7.32031 15.5103C6.68457 13.8729 3.5 5.63898 3.5 4.28851ZM5.49609 4.49121C5.49609 5.86975 6.6123 6.98633 7.99121 6.98633C9.37012 6.98633 10.4863 5.86975 10.4863 4.49121C10.4863 3.11267 9.37012 1.99609 7.99121 1.99609C6.6123 1.99609 5.49609 3.11267 5.49609 4.49121Z" fill="#84848B"/>
						</svg>
						${location}
					</span>
				</div>
			</div>
			<div class="col-sm-12 col-md-8">
				<div class="mt-4">
					<@commerce_ui["gallery"] CPDefinitionId=cpCatalogEntry.getCPDefinitionId() />
				</div>
				<div class="my-5">
					${cpCatalogEntry.getDeion()}
				</div>
			</div>
			<div class="col-sm-12 col-md-4">
				<div class="sidebar mt-4">
					<span>Funding Target</span>
					<h1>${fundingTarget}</h1>
					<div class="progress-bar">
						<div></div>
					</div>
					<div class="mt-4">
						<div class="d-flex justify-content-between">
							<span class="text">
								Yield 
								<svg width="12" height="12" viewBox="0 0 12 12" fill="none" xmlns="http://www.w3.org/2000/svg">
										<path d="M5.37852 6.67152V6.96556H6.24078V6.72715C6.24078 6.56821 6.2679 6.43973 6.32213 6.34172C6.37852 6.24371 6.51085 6.10066 6.71909 5.91258C7.02278 5.64768 7.22885 5.40927 7.33731 5.19735C7.44577 4.98543 7.5 4.73378 7.5 4.44238C7.5 4.0053 7.36768 3.65563 7.10304 3.39338C6.84056 3.13113 6.4859 3 6.03905 3C5.49675 3 4.98373 3.16556 4.5 3.49669L4.85466 4.36689C5.27115 4.10729 5.64208 3.97748 5.96746 3.97748C6.15401 3.97748 6.29935 4.02252 6.40347 4.11258C6.50759 4.20265 6.55965 4.33378 6.55965 4.50596C6.55965 4.6596 6.52278 4.79868 6.44902 4.92318C6.37744 5.04768 6.22777 5.20927 6 5.40795C5.76356 5.61987 5.60087 5.81987 5.51193 6.00795C5.42299 6.19603 5.37852 6.41722 5.37852 6.67152Z" fill="#84848B"/>
										<path d="M5.42082 7.81987C5.32321 7.93377 5.2744 8.10199 5.2744 8.3245C5.2744 8.53907 5.3243 8.70596 5.42408 8.82517C5.52386 8.94172 5.66486 9 5.84707 9C6.02495 9 6.16377 8.9404 6.26356 8.82119C6.36334 8.69934 6.41323 8.53378 6.41323 8.3245C6.41323 8.10728 6.36334 7.9404 6.26356 7.82384C6.16594 7.70728 6.02712 7.64901 5.84707 7.64901C5.66052 7.64901 5.51844 7.70596 5.42082 7.81987Z" fill="#84848B"/>
										<path fill-rule="evenodd" clip-rule="evenodd" d="M6 12C9.31371 12 12 9.31371 12 6C12 2.68629 9.31371 0 6 0C2.68629 0 0 2.68629 0 6C0 9.31371 2.68629 12 6 12ZM6 10.5C8.48528 10.5 10.5 8.48528 10.5 6C10.5 3.51472 8.48528 1.5 6 1.5C3.51472 1.5 1.5 3.51472 1.5 6C1.5 8.48528 3.51472 10.5 6 10.5Z" fill="#84848B"/>
								</svg>
							</span>
							<h1 class="text-sm">${yield}</h1>
						</div>
						<div class="d-flex justify-content-between">
							<span class="text">
								Capital Growth 
								<svg width="12" height="12" viewBox="0 0 12 12" fill="none" xmlns="http://www.w3.org/2000/svg">
										<path d="M5.37852 6.67152V6.96556H6.24078V6.72715C6.24078 6.56821 6.2679 6.43973 6.32213 6.34172C6.37852 6.24371 6.51085 6.10066 6.71909 5.91258C7.02278 5.64768 7.22885 5.40927 7.33731 5.19735C7.44577 4.98543 7.5 4.73378 7.5 4.44238C7.5 4.0053 7.36768 3.65563 7.10304 3.39338C6.84056 3.13113 6.4859 3 6.03905 3C5.49675 3 4.98373 3.16556 4.5 3.49669L4.85466 4.36689C5.27115 4.10729 5.64208 3.97748 5.96746 3.97748C6.15401 3.97748 6.29935 4.02252 6.40347 4.11258C6.50759 4.20265 6.55965 4.33378 6.55965 4.50596C6.55965 4.6596 6.52278 4.79868 6.44902 4.92318C6.37744 5.04768 6.22777 5.20927 6 5.40795C5.76356 5.61987 5.60087 5.81987 5.51193 6.00795C5.42299 6.19603 5.37852 6.41722 5.37852 6.67152Z" fill="#84848B"/>
										<path d="M5.42082 7.81987C5.32321 7.93377 5.2744 8.10199 5.2744 8.3245C5.2744 8.53907 5.3243 8.70596 5.42408 8.82517C5.52386 8.94172 5.66486 9 5.84707 9C6.02495 9 6.16377 8.9404 6.26356 8.82119C6.36334 8.69934 6.41323 8.53378 6.41323 8.3245C6.41323 8.10728 6.36334 7.9404 6.26356 7.82384C6.16594 7.70728 6.02712 7.64901 5.84707 7.64901C5.66052 7.64901 5.51844 7.70596 5.42082 7.81987Z" fill="#84848B"/>
										<path fill-rule="evenodd" clip-rule="evenodd" d="M6 12C9.31371 12 12 9.31371 12 6C12 2.68629 9.31371 0 6 0C2.68629 0 0 2.68629 0 6C0 9.31371 2.68629 12 6 12ZM6 10.5C8.48528 10.5 10.5 8.48528 10.5 6C10.5 3.51472 8.48528 1.5 6 1.5C3.51472 1.5 1.5 3.51472 1.5 6C1.5 8.48528 3.51472 10.5 6 10.5Z" fill="#84848B"/>
								</svg>
							</span>
							<h1 class="text-sm">${capitalGrowth}</h1>
						</div>
						<div class="d-flex justify-content-between">
							<span class="text">
								Investors 
							</span>
							<h1 class="text-sm">12</h1>
						</div>
					</div>
                    <hr>
                    <div class="mt-4">
						<div class="d-flex justify-content-between">
							<span class="text">
                                Minimum Investment 
							</span>
							<span class="text font-weight-bold">7.14%</span>
						</div>
						<div class="d-flex justify-content-between">
							<span class="text">
								Property Type 
							</span>
							<span class="text font-weight-bold">${propertyType}</span>
						</div>
						<div class="d-flex justify-content-between">
							<span class="text">
								Investment Profile 
							</span>
							<span class="text font-weight-bold">${investmentProfile}</span>
						</div>
					</div>
                    <div class="invest mt-5">
                        <input id="input-investment" type="text" />
                        <a id="btn-send-investment">Invest</a>
                    </div>
				</div>
                <div class="documents">
                    <h1>Documents</h1>
                    <#assign medias = cpContentHelper.getCPMedias(cpCatalogEntry.getCPDefinitionId(), themeDisplay) />
                    <#if medias??>
                        <ul>
                            <#list medias as media>
                                <li>
                                    <a target="_blank" href="${media.getDownloadURL()}">
                                        <svg width="25" height="32" viewBox="0 0 25 32" fill="none" xmlns="http://www.w3.org/2000/svg">
                                            <g clip-path="url(#clip0_11_1081)">
                                            <path d="M16.1074 0H1.71812C0.76923 0 0 0.76923 0 1.71812V30.2819C0 31.2308 0.76923 32 1.71812 32H22.3356C23.2845 32 24.0537 31.2308 24.0537 30.2819V6.65772L16.1074 0Z" fill="#8BB7DF"/>
                                            <path opacity="0.2" d="M17.8255 6.65772H24.0537L16.1074 0V4.9396C16.1074 5.88849 16.8767 6.65772 17.8255 6.65772Z" fill="#28282B"/>
                                            <path d="M4.27297 13.7H6.40297C6.7563 13.7 7.0763 13.7433 7.36297 13.83C7.64964 13.9167 7.8963 14.0433 8.10297 14.21C8.30964 14.37 8.4663 14.57 8.57297 14.81C8.6863 15.0433 8.74297 15.3133 8.74297 15.62C8.74297 15.9733 8.67964 16.2767 8.55297 16.53C8.4263 16.7833 8.25297 16.9967 8.03297 17.17C7.81297 17.3367 7.55297 17.46 7.25297 17.54C6.95297 17.62 6.62964 17.66 6.28297 17.66H5.49297V20H4.27297V13.7ZM5.49297 16.71H6.05297C6.25297 16.71 6.43964 16.6933 6.61297 16.66C6.7863 16.6267 6.9363 16.57 7.06297 16.49C7.1963 16.41 7.29964 16.3033 7.37297 16.17C7.4463 16.0367 7.48297 15.87 7.48297 15.67C7.48297 15.49 7.44964 15.3367 7.38297 15.21C7.3163 15.0767 7.22297 14.97 7.10297 14.89C6.98964 14.8033 6.8563 14.74 6.70297 14.7C6.54964 14.66 6.38964 14.64 6.22297 14.64H5.49297V16.71ZM11.9015 13.7C12.3748 13.7 12.8082 13.7633 13.2015 13.89C13.5948 14.0167 13.9315 14.2067 14.2115 14.46C14.4982 14.7133 14.7182 15.03 14.8715 15.41C15.0315 15.7833 15.1115 16.22 15.1115 16.72C15.1115 17.3 15.0182 17.8 14.8315 18.22C14.6448 18.6333 14.3915 18.9733 14.0715 19.24C13.7515 19.5 13.3782 19.6933 12.9515 19.82C12.5315 19.94 12.0815 20 11.6015 20H9.75148V13.7H11.9015ZM10.9715 19.04H11.5115C11.8048 19.04 12.0882 19.0067 12.3615 18.94C12.6415 18.8667 12.8882 18.7433 13.1015 18.57C13.3148 18.3967 13.4848 18.16 13.6115 17.86C13.7448 17.56 13.8115 17.18 13.8115 16.72C13.8115 16.0533 13.6215 15.5433 13.2415 15.19C12.8682 14.8367 12.3615 14.66 11.7215 14.66H10.9715V19.04ZM20.1554 13.7V14.66H17.4754V16.45H19.9354V17.4H17.4754V20H16.2554V13.7H20.1554Z" fill="white"/>
                                            </g>
                                            <defs>
                                            <clipPath id="clip0_11_1081">
                                            <rect width="24.0537" height="32" fill="white"/>
                                            </clipPath>
                                            </defs>
                                        </svg>
                                        <span>${media.getTitle()}</span>
                                    </a>
                                </li>
                            </#list>
                        </ul>
                    </#if>
                </div>
			</div>
		</div>
	</div>
	<script>
		const btnSendInvesment = document.querySelector("#btn-send-investment");
		const inputInvesment = document.querySelector("#input-investment");
		btnSendInvesment.addEventListener("click",function(){
			var url = "/group/crowse/checkout?productId=" + ${cpCatalogEntry.getCPDefinitionId()+1} + "&channelId=" + ${commerceChannelId} + "&accountId=" + ${commerceAccountId} + "&investment=" + inputInvesment.value;
			window.location.href = url;
		});
	</script>
</#if>