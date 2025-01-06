<#assign
	percentage = backgroundTaskDisplay.getPercentage()
/>

<style ${nonceAttribute} type="text/css">
	.percent-${percentage} {
		width: ${percentage}%;
	}
</style>

<div class="background-task-status-in-progress">
	<div class="progress-group">
		<div class="progress">
			<div aria-valuemax="100" aria-valuemin="0" aria-valuenow="${percentage}" class="progress-bar percent-${percentage}" role="progressbar"></div>
		</div>

		<div class="progress-group-addon">${percentage}%</div>
	</div>
</div>