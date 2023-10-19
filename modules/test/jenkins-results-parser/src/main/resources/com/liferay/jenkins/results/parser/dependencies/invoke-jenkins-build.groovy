import org.jvnet.jenkins.plugins.nodelabelparameter.LabelParameterDefinition;
import org.jvnet.jenkins.plugins.nodelabelparameter.LabelParameterValue;

Map<String, String> parameters = new HashMap<>();

${parameters}

Map<String, TopLevelItem> topLevelItems = Jenkins.instance.getItemMap();

TopLevelItem topLevelItem = topLevelItems.get("${jenkinsJobName}");

List<ParameterValue> parameterValues = new ArrayList<>();

JobProperty jobProperty = topLevelItem.getProperty(
	"hudson.model.ParametersDefinitionProperty");

for (ParameterDefinition parameterDefinition :
		jobProperty.getParameterDefinitions()) {

	String parameterName = parameterDefinition.getName();

	String parameterValue = parameters.get(parameterName);

	if ((parameterValue == null) || parameterValue.isEmpty()) {
		parameterValue = parameterDefinition.defaultValue;
	}

	if (parameterDefinition instanceof StringParameterDefinition) {
		parameterValues.add(
			new StringParameterValue(parameterName, parameterValue));
	}
	else if (parameterDefinition instanceof LabelParameterDefinition) {
		parameterValues.add(
			new LabelParameterValue(parameterName, parameterValue));
	}
}

def waitingItem = Jenkins.instance.queue.schedule(
	topLevelItem, 0, new ParametersAction(parameterValues));

if (waitingItem == null) {
	for (Queue.Item item : Jenkins.instance.queue.getItems()) {
		if (waitingItem != null) {
			break;
		}

		for (Action action : item.getActions()) {
			if (!(action instanceof ParametersAction)) {
				continue;
			}

			if (!parameterValues.equals(action.getAllParameters())) {
				continue;
			}

			waitingItem = item;

			break;
		}
	}
}

def jsonBuilder = new groovy.json.JsonBuilder()

jsonBuilder queueId: waitingItem.getId()

println(jsonBuilder);