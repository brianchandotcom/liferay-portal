<form class="form container container-no-gutters-sm-down container-view" method="post"
	id="[$PORTLET_NAME$]fm" name="[$PORTLET_NAME$]" data-fm-namespace="[$PORTLET_NAME$]"
	action="[$ACTION_URL$]">

	<input class="field form-control" id="[$PORTLET_NAME$]formDate" name="[$PORTLET_NAME$]formDate" type="hidden" value="[$CURRENT_TIME_MILLIS$]" />

	<input class="field form-control" id="[$PORTLET_NAME$]configurationObject" name="[$PORTLET_NAME$]configurationObject" type="hidden" value=""[$FIELDS_JSON_ARRAY$]"" />

	<input class="field form-control" id="[$PORTLET_NAME$][$CONSTANTS_CMD$]" name="[$PORTLET_NAME$][$CONSTANTS_CMD$]" type="hidden" value="[$CONSTANTS_UPDATE$]" />

	<div class="lfr-form-content" id="portlet-configuration">
		<div class="sheet sheet-lg" id="sheet-portlet">
			<div aria-multiselectable="true" class="panel-group" role="tablist">

				[$DDM_FORM_HTML$]

			</div>
		</div>
	</div>

	<div class="button-holder dialog-footer">
		<button class="btn btn-default btn-primary" id="form-button-submit" type="submit">
			<span class="lfr-btn-label">
				[$SAVE$]
			</span>
		</button>
	</div>
</form>