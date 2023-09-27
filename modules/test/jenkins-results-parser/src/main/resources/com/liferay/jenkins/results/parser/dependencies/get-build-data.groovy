items = Jenkins.instance.allItems

endDate = new Date()

startDate = endDate.minus(1)

startDate = Date.parse("yyyyMMdd hh:mm:ss", startDate.format("yyyyMMdd") + " 00:00:00")

buildJsons = []

items.each { item ->
	if (item instanceof Job) {
		def builds = item.getBuilds()

		builds.each { build ->
			startTime = build.getTimeInMillis()

			if ((startTime < startDate.getTime()) || (startTime >= endDate.getTime())) {
				return
			}

			def buildJson = new groovy.json.JsonBuilder()

			buildUrl = Jenkins.instance.getRootUrl() + build.getUrl()

			parameters = []

			if (!buildUrl.contains("maintenance") && !buildUrl.contains("verification") && !buildUrl.contains("-controller") && !buildUrl.contains("-propagator")) {
				parameterAction = build.getAction(hudson.model.ParametersAction.class)

				if (parameterAction != null) {
					parameterValues = parameterAction.getParameters()

					parameterValues.each { parameter ->
						parameterValue = parameter.getValue()

						if ((parameterValue != null) && !parameterValue.isEmpty()) {
							parameterName = parameter.getName()

							if ((buildUrl.contains("-batch") || buildUrl.contains("-downstream")) && !(parameterName.equals("DIST_PATH") || parameterName.equals("JOB_VARIANT"))) {
								return
							}

							def parameterJson = new groovy.json.JsonBuilder()

							parameterJson name: parameterName, value: parameterValue

							parameters.add(parameterJson.getContent())
						}

					}

				}
			}

			duration = build.getDuration()

			timeInQueueAction = build.getAction(jenkins.metrics.impl.TimeInQueueAction.class)

			if (timeInQueueAction != null) {
				queueDuration = timeInQueueAction.getQueuingDurationMillis()
			}

			result = build.getResult()

			buildJson url: buildUrl, startTime: build.getTimeInMillis(), result: result.toString(), duration: duration, queueDuration: queueDuration, parameters: parameters, builtOn: build.getBuiltOnStr()

			buildJsons.add(buildJson)
		}
	}
}

println(buildJsons)

return