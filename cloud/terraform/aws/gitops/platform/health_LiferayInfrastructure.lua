if obj == nil or obj.status == nil or obj.status.conditions == nil
then
	return {
		message = "The system is initializing.",
		status = "Progressing"
	}
end

local isReady = false
local progressMessage = ""

for i, condition in ipairs(obj.status.conditions)
do
	if condition.status == "False" and condition.type == "Ready"
	then
		progressMessage = "Still " .. (condition.reason or "Provisioning") .. ": " .. (condition.message or "Not Ready")
	elseif condition.status == "False" and condition.type == "Synced"
	then
		return {
			message = condition.message or "Check Composition Pipeline for errors",
			health = "Degraded"
		}
	elseif condition.status == "True" and condition.type == "Ready"
	then
		isReady = true
	end
end

if isReady and (obj.status.managedServiceDetailsReady or false)
then
	return { message = "Liferay Infrastructure is ready", status = "Health" }
end

return { message = progressMessage, status = "Progressing" }