local health = {}

if obj ~= nil and obj.status ~= nil and obj.status.conditions ~= nil then
	local isReady = false
	local progressMessage = ""
	local syncError = ""

	for i, condition in ipairs(obj.status.conditions) do
		if condition.type == "Ready" then
			if condition.status == "True" then
				isReady = true
			else
				progressMessage = "Still " .. (condition.reason or "Provisioning") .. ": " .. (condition.message or "Not Ready")
			end
		end

		if condition.type == "Synced" and condition.status == "False" then
			health.message = condition.message or "Check Composition Pipeline for errors"
			health.status = "Degraded"

			return health
		end
	end

	local isManagedServiceDetailsReady = obj.status.managedServiceDetailsReady or false

	if isReady and isManagedServiceDetailsReady then
		health.status = "Healthy"
		health.message = "Liferay Infrastructure is Ready!"

		return health
	end

	health.message = progressMessage
	health.status = "Progressing"

	return health
end

health.message = "The system is still initializing. Please check back later."
health.status = "Progressing"

return health