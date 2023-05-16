<div class="columns-max" <#if !layout?? || !layout.isTypeControlPanel()>id="main-content" role="main"</#if>>
	<div class="portlet-layout row">
		<div class="col-md-12 portlet-column portlet-column-only" id="column-1">
			${processor.processMax()}
		</div>
	</div>
</div>