<#assign percentage = backgroundTaskDisplay.getPercentage() />

<div class="background-task-status-in-progress">
	<div class="active progress progress-striped reindex-progress">
		<@liferay_ui.csp>
			<div class="progress-bar" style="width:${percentage}%">
				<span class="progress-percentage">${percentage}%</span>
			</div>
		</@liferay_ui.csp>
	</div>
</div>