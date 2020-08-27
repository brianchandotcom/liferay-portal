<div class="commerce-1-2-columns_3-9 account-layout commerce-layouttpl"
	id="main-content"
	role="main">

	<div class="container portlet-layout">
		<div class="portlet-layout row">
			<div class="col-12 portlet-column portlet-column-only" id="column-1">
				${processor.processColumn("column-1", "portlet-column-content portlet-column-content-only")}
			</div>
		</div>
	</div>

	<div class="container portlet-layout">
		<div class="portlet-layout row">
			<div class="col-md-3 hidden-sm portlet-column portlet-column-first" id="column-2">
				${processor.processColumn("column-2", "portlet-column-content portlet-column-content-first")}
			</div>

			<div class="col-md-9 col-sm-12 portlet-column portlet-column-last" id="column-3">
				${processor.processColumn("column-3", "portlet-column-content portlet-column-content-last")}
			</div>
		</div>
	</div>
</div>