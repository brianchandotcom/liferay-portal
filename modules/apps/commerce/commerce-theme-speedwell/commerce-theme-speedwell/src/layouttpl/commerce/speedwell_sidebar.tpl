<div class="commerce-1-2-1-columns commerce-catalog commerce-layouttpl container-fluid" id="main-content" role="main">
	<div class="hidden-lg hidden-md hidden-sm mobile-filters-button">
		<div class="filters-icon">
			<svg fill="none" height="17" viewBox="0 0 17 17" width="17" xmlns="http://www.w3.org/2000/svg">
				<path clip-rule="evenodd" d="M3.00001 11.564C2.25901 11.564 1.65601 10.961 1.65601 10.22C1.65601 9.47898 2.25901 8.87598 3.00001 8.87598C3.74101 8.87598 4.34401 9.47898 4.34401 10.22C4.34401 10.961 3.74101 11.564 3.00001 11.564ZM3.857 7.359V0.758C3.857 0.339 3.473 0 3 0C2.527 0 2.143 0.339 2.143 0.758V7.359C0.907 7.73 0 8.863 0 10.22C0 11.577 0.907 12.71 2.143 13.081V16.226C2.143 16.654 2.527 17 3 17C3.473 17 3.857 16.654 3.857 16.226V13.081C5.094 12.71 6 11.577 6 10.22C6 8.863 5.094 7.73 3.857 7.359ZM14 13.844C13.259 13.844 12.656 13.241 12.656 12.5C12.656 11.759 13.259 11.156 14 11.156C14.741 11.156 15.344 11.759 15.344 12.5C15.344 13.241 14.741 13.844 14 13.844ZM14.857 9.639V0.758C14.857 0.339 14.474 0 14 0C13.527 0 13.143 0.339 13.143 0.758V9.639C11.906 10.01 11 11.143 11 12.5C11 13.857 11.906 14.99 13.143 15.36V16.226C13.143 16.654 13.527 17 14 17C14.474 17 14.857 16.654 14.857 16.226V15.36C16.094 14.99 17 13.857 17 12.5C17 11.143 16.094 10.01 14.857 9.639ZM8.50001 5.98701C7.75901 5.98701 7.15601 5.38401 7.15601 4.64301C7.15601 3.90201 7.75901 3.29901 8.50001 3.29901C9.24101 3.29901 9.84401 3.90201 9.84401 4.64301C9.84401 5.38401 9.24101 5.98701 8.50001 5.98701ZM9.357 1.782V0.758C9.357 0.339 8.974 0 8.5 0C8.027 0 7.643 0.339 7.643 0.758V1.782C6.406 2.153 5.5 3.286 5.5 4.643C5.5 6 6.406 7.133 7.643 7.503V16.226C7.643 16.654 8.027 17 8.5 17C8.974 17 9.357 16.654 9.357 16.226V7.503C10.594 7.133 11.5 6 11.5 4.643C11.5 3.286 10.594 2.153 9.357 1.782Z" fill="white" fill-rule="evenodd" />
			</svg>
		</div>
	</div>

	<div class="portlet-layout row">
		<div class="col-lg-3 col-md-4 hidden-sm portlet-column portlet-column-first" id="column-1">
			<div class="hidden-lg hidden-md hidden-sm mobile-filters-header">
				<h1 class="title"></h1>

				<div class="close-button">&#x2715;</div>
			</div>

			${processor.processColumn("column-1", "portlet-column-content portlet-column-content-first")}
		</div>

		<div class="col-lg-9 col-md-8 portlet-column portlet-column-last" id="column-2">
			${processor.processColumn("column-2", "portlet-column-content portlet-column-content-last")}
		</div>
	</div>
</div>