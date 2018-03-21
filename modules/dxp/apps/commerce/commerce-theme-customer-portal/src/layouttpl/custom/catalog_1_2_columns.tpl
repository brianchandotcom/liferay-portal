<div class="catalog-1-2-columns" id="main-content" role="main">
	<div class="portlet-layout row">
		<div class="portlet-column portlet-column-only" id="column-1">
			${processor.processColumn("column-1", "portlet-column-content portlet-column-content-only")}
		</div>
	</div>
	<div class="portlet-layout row">
		<div class="portlet-column portlet-column-first" id="column-2">
			${processor.processColumn("column-2", "portlet-column-content portlet-column-content-first")}
		</div>
		<div class="portlet-column portlet-column-last" id="column-3">
			${processor.processColumn("column-3", "portlet-column-content portlet-column-content-last")}
		</div>
	</div>
</div>