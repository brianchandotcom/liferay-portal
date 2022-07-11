<style>
.partner-portal-announcements {
  display: -webkit-box;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

@media (min-width: 320px){
.partner-portal-announcements {
	-webkit-line-clamp: 6;	
	max-height: 150px;
	max-width: 120px;
	}
}
	
@media (min-width: 768px){
.partner-portal-announcements {
	-webkit-line-clamp: 2;	
	max-height: 40px;
	max-width: inherit;
	}
}

.partner-portal-announcements p {
  display: contents;
}
</style>

<div>
	<div class="d-flex align-items-baseline justify-content-between">
		<h4 class="mb-4">
        Announcements
    </h4>
	</div> 

<#if entries?has_content>
        <#list entries as curEntry>
            <div class="d-flex border border-neutral-3 rounded p-3 mb-3 justify-content-between">
							<div class="text-left text-wrap mr-4 pr-2">
								<h6  class="font-weight-bold text-neutral-10 mb-1">
								${curEntry.getTitle(locale)} 
								</h6>  

								<div  class="partner-portal-announcements text-paragraph-sm text-neutral-8">
								<@liferay_asset["asset-display"]  
                                 assetEntry=curEntry
                                />
								
								</div>
								
							</div>
             
							<div class="d-flex col-auto mx-n2"> 
								<@liferay_ui["user-portrait"]
                                size="sm"
                                userId=curEntry.getUserId()
                                userName=curEntry.getUserName()
                                 />
									<div class="flex-wrap ml-3 mt-n1">
										<div class="font-weight-semi-bold text-paragraph-xs text-neutral-10">
											${curEntry.getUserName()}
										</div>
							
										 <div class="text-paragraph-xxs text-neutral-8">
											 ${dateUtil.getDate(curEntry.getPublishDate(), "MMM dd, yyyy", locale)}
											</div>
									</div>
								
							</div>
								 
            </div>
					
        </#list>
    </#if>
	<div  class="text-center">
		<a href="https://www.liferay.com" target="_blank">
			 View All Announcements
	</a>
	</div>
	
</div>