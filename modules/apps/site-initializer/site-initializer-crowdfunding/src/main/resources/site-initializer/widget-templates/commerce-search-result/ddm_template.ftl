<#assign 
        commerce_ui = PortletJspTagLibs["/META-INF/liferay-commerce-ui.tld"] 
/>

<style>
    .crowse-card > a{
        border-radius: 12px;
        text-decoration: none !important;
        position:relative;
    }

    .crowse-card-thumbnail{
        height: 200px;
        background-size: cover !important;
        background-position: center;
        background-repeat: no-repeat;
        background: gray;
        border-radius: 12px 12px 0 0;
    }

    .crowse-card-thumbnail-infos{
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 14px;
    }
    .crowse-card .add-to-wish-list{
        position: absolute;
        top: 14px;
        right: 30px;
            z-index: 100;
    }

    .crowse-card .add-to-wish-list svg{
        color: #FF7777;
    }


    .crowse-card .add-to-wish-list button span:first-child{
        display:none;
    }

    .crowse-card .add-to-wish-list button:hover{
        background: #fff;
    }

    .crowse-card .add-to-wish-list button span:last-child{
        margin: 0;
        padding: 0 3px;
    }

    .crowse-card-thumbnail-infos>span{
        background: #56BBFF;
        border-radius: 6px;
        display:inline-block;
        padding: 4px 12px;
        font-weight: 600;
        color: #fff;
        font-size: 0.875rem;
    }

    .crowse-card .add-to-wish-list button{
        background: #FFFFFF;
        border-radius: 6px;
        border: none;
        padding: 3px 7px;
    }

    .crowse-card-header{
        padding: 12px 24px;
        background: var(--color-brand-primary);
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .crowse-card-header span{
        color: #fff;
        font-size: 0.875rem;
        display: inline-block;
    }

    .crowse-card-header span.title{
        display:block;
        font-size: 1.4rem;
    }

    .crowse-card-body{
        background: #fff;
        padding: 15px 24px;
        border-radius: 0 0 12px 12px;
    }

    .crowse-card-body .subtitle{
        color: var(--color-brand-primary);
        font-weight: 600;
    }

    .crowse-card-body .title{
        color: #28282B;
        font-weight: 600;
        font-size: 1.5rem;
        height: 60px;
    }

    .crowse-card-body .legend{
        font-weight: 300;
        color: #28282B;
        display:block;
    }

    .crowse-card-body .tag{
        border: 2px solid var(--color-brand-primary);
        font-size: 0.75rem;
        display: inline-block;
        padding: 0 4px;
        border-radius: 6px;
        color: var(--color-brand-primary);
    }

    .crowse-card-body .progress-bar{
        background: #ECEAF8;
        border-radius: 6px;
        height: 12px;
        margin-top: 28px;
    }

    .crowse-card-body .progress-bar div{
        width: 0%;
        background: var(--color-brand-primary);
        height: 12px;
        border-radius: 6px;
        transition: 2s;
    }
</style>

<script>
    function getElements(elementId){
        var progress = document.querySelector("#progress-" + elementId + ">div");
        var progressInfo = document.querySelector("#progress-info-" + elementId);
        
        return {
            progress,
            progressInfo: progressInfo,
            progressPercentage: progressInfo.querySelector("span:first-child"),
            progressAmountLeft: progressInfo.querySelector("span:last-child"),
            totalAmount: progress.dataset.fundingTarget,
        }
    }

    function printProgress(progress, progressPercentage, progressAmountLeft, progressWidth, totalAmount, total){
        var formatter = new Intl.NumberFormat('en-US', {
				        style: 'currency',
				        currency: 'USD',
		                });
        progress.style.width = Math.round(progressWidth) + "%";    
        progressPercentage.innerText = Math.round(progressWidth) + "%";
        progressAmountLeft.innerText = formatter.format(Math.round(+totalAmount - total)) + ' Left'; 
    }

    function calculatePercentage(itemsList, progress, totalAmount, progressPercentage, progressAmountLeft) {
        var total = 0;
        itemsList.forEach(function(item){
            total+= item.investimentCommitment;
        });
                            
        totalAmount = totalAmount.replace('$','').replaceAll(',','');
        var progressWidth = (total/+totalAmount) * 100;
        printProgress(progress, progressPercentage, progressAmountLeft, progressWidth, totalAmount, total);
    }
</script>

<div class="container">
	<div class="row">
		<#if entries?has_content>
			<#list entries as curCPCatalogEntry>
				<#assign images = cpContentHelper.getImages(curCPCatalogEntry.getCPDefinitionId(), themeDisplay) />
				<#assign imgUrl = "" />
				<#list images as img>
					<#if img?is_first>
						<#assign imgUrl = img.getThumbnailURL() />
						<#break>
					</#if>
				</#list>
				<#assign fundingTarget = "" />
				<#assign location = "" />
				<#assign capitalGrowth = "" />
				<#assign yield = "" />
				<#list cpContentHelper.getCPDefinitionSpecificationOptionValues(curCPCatalogEntry.getCPDefinitionId()) as specification>
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
			        </#switch>
				</#list>
                
				<div class="col-sm-12 col-md-4 mb-3 crowse-card">
				 	<@commerce_ui["add-to-wish-list"] 
								CPCatalogEntry=curCPCatalogEntry
								large=false />
					<a href="${cpContentHelper.getFriendlyURL(curCPCatalogEntry,themeDisplay)}">
						<div class="crowse-card-thumbnail" style="background-image: url('${imgUrl}')">
							<div class="crowse-card-thumbnail-infos">
								<span>Funding</span>
							</div>
						</div>
						<div class="crowse-card-header">
                            <div>
                                    <span>Funding</span>
                                    <span class="font-weight-bold title">
                                        ${fundingTarget}
                                    </span>
                            </div>
                            <div class="text-right">
                                <div>
                                    <span>Yield</span>	
                                    <span class="font-weight-bold">7.14%</span>	
                                </div>
                                <div>
                                    <span>Capital Growth</span>	
                                    <span class="font-weight-bold">${capitalGrowth}</span>	
                                </div>
                            </div>
				        </div>
				        <div class="crowse-card-body">
					        <span class="subtitle">${location}</span>
					        <h1 class="title">${curCPCatalogEntry.getName()}</h1>
					        <span class="legend">BDR Capital LLC</span>
					        <span class="tag">Tenured</span>
					        <div id="progress-${curCPCatalogEntry.getCPDefinitionId()+1}" class="progress-bar">
						        <div data-funding-target="${fundingTarget}"></div>
					        </div>
                            <div id="progress-info-${curCPCatalogEntry.getCPDefinitionId()+1}" class="d-flex align-items-center justify-content-between mt-2">
							    <span></span>
							    <span></span>
					        </div>
				        </div>
			        </a>
		        </div>
		        <script>
			        Liferay.Util.fetch('/o/c/crowseinvestmentobjects/?search=${curCPCatalogEntry.getCPDefinitionId()+1}').then(function(response) {
			            return response.json();
			        }).then(function(data) {
                        var {progress,progressInfo,progressPercentage,progressAmountLeft,totalAmount} = getElements(${curCPCatalogEntry.getCPDefinitionId()+1});      
                        calculatePercentage(data.items, progress, totalAmount, progressPercentage, progressAmountLeft);
                        });
		        </script>
		    </#list>
		</#if>
	</div>
</div>