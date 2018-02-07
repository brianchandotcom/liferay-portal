import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.io.File;
import java.io.IOException;

import java.lang.Exception;

import java.nio.file.Files;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import org.apache.tools.ant.Project;

/**
 * @author Cesar Polanco
 */
public class JenkinsJobTemplate {

	public static Map getEnvironmentSlavesMap(Map properties) {
		Map environmentSlavesMap = new TreeMap();

		Set propertiesSet = properties.entrySet();

		String key = entry.getKey();

		while (propertiesIterator.hasNext()) {
			Map.Entry entry = (Map.Entry)propertiesIterator.next();

			String key = (String)entry.getKey();

			if (!key.startsWith("environment.slaves(")) {
				continue;
			}

			String propertyName = key.substring(
				key.indexOf("slaves(") + 7, key.indexOf(")"));

			String value = (String)entry.getValue();

			if (value.contains("..")) {
				value = JenkinsResultsParserUtil.expandSlaveRange(value);
			}

			for (String slaveHostname : value.split(",")) {
			  environmentSlavesMap.put(slaveHostname, propertyName);
			}
		}

		String propertyName = key.substring(key.indexOf("slaves(") + 7, key.indexOf(")"));

		String value = entry.getValue();

		if (value.contains("..")) {
			value = JenkinsResultsParserUtil.expandSlaveRange(value);
		}

		for (String slaveHostname : value.split(",")) {
			environmentSlavesMap.put(slaveHostname, propertyName);
		}
	}

	return environmentSlavesMap;
}

public Map getLinuxEnvironmentVariablesMap() {
	Map linuxEnvironmentVariablesMap = new TreeMap();

	linuxEnvironmentVariablesMap.put("PATH", "/bin:/opt/android/sdk:/opt/android/sdk/platform-tools:/opt/android/sdk/tools:/opt/ibm/db2/V10.5/bin:/opt/java/ant/bin:/opt/java/jdk/bin:/opt/java/maven/bin:/opt/oracle/bin:/opt/sybase/OCS-16_0/bin:/sbin:/usr/bin:/usr/local/bin:/usr/local/sbin:/usr/sbin");

	return linuxEnvironmentVariablesMap;
}

	public static String getSlaveConfigXMLContent(String slaveHostname, Map properties) {
		String slaveLabel = slaveHostname;

		Map environmentSlavesMap = getEnvironmentSlavesMap(properties);
		Map linuxEnvironmentVariablesMap = getLinuxEnvironmentVariablesMap();
		Map osxEnvironmentVariablesMap = getOSXEnvironmentVariablesMap();
		Map windowsEnvironmentVariablesMap = getWindowsEnvironmentVariablesMap();

		if (environmentSlavesMap.containsKey(slaveHostname)) {
			slaveLabel = (String)environmentSlavesMap.get(slaveHostname);
		}

		String slaveConfigXMLContent = JenkinsResultsParserUtil.combine(
			"<slave>\n\t<name>", slaveHostname, "</name>\n\t<description>",
			"</description>\n\t<remoteFS>/opt/java/jenkins</remoteFS>\n",
			"\t<numExecutors>1</numExecutors>\n\t<mode>NORMAL</mode>\n",
			"\t<retentionStrategy ",
			"class=\"hudson.slaves.RetentionStrategy$Always\"",
			" />\n\t<launcher class=\"hudson.slaves.JNLPLauncher\" />\n",
			"\t<label>", slaveLabel, "</label>\n\t<nodeProperties>\n",
			"\t\t<hudson.slaves.EnvironmentVariablesNodeProperty>\n",
			"\t\t\t<envVars serialization=\"custom\">\n",
			"\t\t\t\t<unserializable-parents />\n",
			"\t\t\t\t<tree-map>\n\t\t\t\t\t<default />\n");

		if (slaveLabel.contains("osx")) {
			slaveConfigXMLContent = JenkinsResultsParserUtil.combine(
				slaveConfigXMLContent,
					getSlaveEnvironmentVariables(
						osxEnvironmentVariablesMap, slaveHostname));
		}
		else if (slaveLabel.contains("win")) {
			slaveConfigXMLContent = JenkinsResultsParserUtil.combine(
				slaveConfigXMLContent,
					getSlaveEnvironmentVariables(
						windowsEnvironmentVariablesMap, slaveHostname));
		}
		else {
			slaveConfigXMLContent = JenkinsResultsParserUtil.combine(
				slaveConfigXMLContent,
					getSlaveEnvironmentVariables(
						linuxEnvironmentVariablesMap, slaveHostname));
		}

public String getSlaveConfigXMLContent(String slaveHostname) {
	String slaveLabel = slaveHostname;

	if (environmentSlavesMap.containsKey(slaveHostname)) {
		slaveLabel = environmentSlavesMap.get(slaveHostname);
	}

	public static String getSlaveEnvironmentVariables(
		Map environmentVariablesMap, String hostname) {

		environmentVariablesMap.put("HOSTNAME", hostname + ".lax.liferay.com");

		String environmentVariablesString = JenkinsResultsParserUtil.combine(
			"\t\t\t\t\t<int>",
			Integer.toString(environmentVariablesMap.size()),
			"</int>\n");

		Set<String> environmentVariables = environmentVariablesMap.keySet();

		for (String environmentVariable : environmentVariables) {
			environmentVariablesString = JenkinsResultsParserUtil.combine(
				environmentVariablesString,	"\t\t\t\t\t<string>",
				environmentVariable, "</string>\n\t\t\t\t\t<string>",
				(String)environmentVariablesMap.get(environmentVariable),
				"</string>\n");
		}

		return environmentVariablesString;
	}

	public static Map getWindowsEnvironmentVariablesMap() {
		Map windowsEnvironmentVariablesMap = new TreeMap();

		String pathValue = JenkinsResultsParserUtil.combine(
			"/c/ant/bin:/c/Perl64/bin:/c/Program Files/7-Zip:",
			"/c/Program Files/IBM/SQLLIB/BIN:",
			"/c/Program Files/Java/jdk1.7.0_55/bin:",
			"/c/Program Files/MySQL/MySQL Server 5.6/bin:",
			"/c/Program Files/Microsoft SQL Server/100/Tools/Binn:",
			"/c/Program Files/Microsoft SQL Server/110/Tools/Binn:",
			"/c/Program Files/Microsoft SQL Server/120/Tools/Binn:",
			"/c/Program Files/Microsoft SQL Server/130/Tools/Binn:",
			"/c/Program Files/Microsoft SQL Server/Client ",
			"SDK/ODBC/110/Tools/Binn:",
			"/c/Program Files/Microsoft SQL Server/Client ",
			"SDK/ODBC/130/Tools/Binn:/c/Windows:/c/Windows/system32");

		windowsEnvironmentVariablesMap.put("HOME", "/c/Users/Administrator");
		windowsEnvironmentVariablesMap.put(
			"JAVA_HOME", "/c/Program Files/Java/jdk1.7.0_55");
		windowsEnvironmentVariablesMap.put("PATH", pathValue);

		return windowsEnvironmentVariablesMap;
	}

	public static String readFileToString(File fileToRead) {
		String result = "";

		try{
			List<String> lines = Files.readAllLines(fileToRead.toPath());

			for (String line : lines) {
				result = JenkinsResultsParserUtil.combine(result, line, "\n");
			}

			result = result.substring(0, result.length() - 1);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return result;
	}

	public static void createTemplate(Project project) throws Exception {
		Map environmentSlavesMap = getEnvironmentSlavesMap(project.getProperties());
		Set globalProperties = new TreeSet();
		Map jobNamesToJobProperties = new TreeMap();
		Map jobNamesToMasterJobProperties = new TreeMap();
		Map linuxEnvironmentVariablesMap = getLinuxEnvironmentVariablesMap();
		Set<String> masterHostnames = new TreeSet();
		Map masterHostnamesToJobNames = new TreeMap();
		Map masterHostnamesToMasterProperties = new TreeMap();
		Map osxEnvironmentVariablesMap = getOSXEnvironmentVariablesMap();
		Set slaveHostnames = new TreeSet();
		Map windowsEnvironmentVariablesMap = getWindowsEnvironmentVariablesMap();

		Map properties = project.getProperties();

		Set propertiesSet = properties.entrySet();

		Iterator propertiesIterator = propertiesSet.iterator();

		while (propertiesIterator.hasNext()) {
			Map.Entry entry = (Map.Entry)propertiesIterator.next();

			String key = (String)entry.getKey();
			String value = (String)entry.getValue();

			if (key.startsWith("global.property(")) {
				String propertyName = key.substring(
					key.indexOf("(") + 1, key.lastIndexOf(")"));

				globalProperties.add(propertyName);

				project.setProperty(
					"global.property.names", StringUtils.join(globalProperties, ","));
			}
			else if (key.startsWith("master.job.names(")) {
				String masterHostname = key.substring(
					key.indexOf("(") + 1, key.indexOf(")"));

				masterHostnames.add(masterHostname);
				masterHostnamesToJobNames.put(masterHostname, value);

				Map childJobNamesMap = new HashMap();
				List<String> parentJobNames = new ArrayList();
				Set<String> topLevelJobNames = new TreeSet();

public String getSlaveEnvironmentVariables(Map environmentVariablesMap, String hostname) {
	environmentVariablesMap.put("HOSTNAME", hostname + ".lax.liferay.com");

	sb = new StringBuilder();

	sb.append("\t\t\t\t\t<int>" + String.valueOf(environmentVariablesMap.size()) + "</int>\n");

	Set environmentVariables = environmentVariablesMap.keySet();

	for (String environmentVariable : environmentVariables) {
		sb.append("\t\t\t\t\t<string>" + environmentVariable + "</string>\n");
		sb.append("\t\t\t\t\t<string>" + environmentVariablesMap.get(environmentVariable) + "</string>\n");
	}

	return sb.toString();
}

public Map getWindowsEnvironmentVariablesMap() {
	Map windowsEnvironmentVariablesMap = new TreeMap();

	windowsEnvironmentVariablesMap.put("HOME", "/c/Users/Administrator");
	windowsEnvironmentVariablesMap.put("JAVA_HOME", "/c/Program Files/Java/jdk1.7.0_55");
	windowsEnvironmentVariablesMap.put("PATH", "/c/ant/bin:/c/Perl64/bin:/c/Program Files/7-Zip:/c/Program Files/IBM/SQLLIB/BIN:/c/Program Files/Java/jdk1.7.0_55/bin:/c/Program Files/MySQL/MySQL Server 5.6/bin:/c/Program Files/Microsoft SQL Server/100/Tools/Binn:/c/Program Files/Microsoft SQL Server/110/Tools/Binn:/c/Program Files/Microsoft SQL Server/120/Tools/Binn:/c/Program Files/Microsoft SQL Server/130/Tools/Binn:/c/Program Files/Microsoft SQL Server/Client SDK/ODBC/110/Tools/Binn:/c/Program Files/Microsoft SQL Server/Client SDK/ODBC/130/Tools/Binn:/c/Windows:/c/Windows/system32");

	return windowsEnvironmentVariablesMap;
}

Map environmentSlavesMap = getEnvironmentSlavesMap();
Set globalProperties = new TreeSet();
Map jobNamesToJobProperties = new TreeMap();
Map jobNamesToMasterJobProperties = new TreeMap();
Map linuxEnvironmentVariablesMap = getLinuxEnvironmentVariablesMap();
Set masterHostnames = new TreeSet();
Map masterHostnamesToJobNames = new TreeMap();
Map masterHostnamesToMasterProperties = new TreeMap();
Map osxEnvironmentVariablesMap = getOSXEnvironmentVariablesMap();
Set slaveHostnames = new TreeSet();
Map windowsEnvironmentVariablesMap = getWindowsEnvironmentVariablesMap();

					int j = jobShortName.indexOf("(");

					if (j != -1) {
						jobShortName = jobShortName.substring(0, j);
					}

Iterator propertiesIterator = propertiesSet.iterator();

					String jobContent = readFileToString(
						new File("template/jobs/" + jobShortName + "/config.xml"));

	String key = entry.getKey();
	String value = entry.getValue();

	if (key.startsWith("global.property(")) {
		String propertyName = key.substring(key.indexOf("(") + 1, key.lastIndexOf(")"));

		globalProperties.add(propertyName);

		project.setProperty("global.property.names", StringUtils.join(globalProperties, ","));
	}
	else if (key.startsWith("master.job.names(")) {
		String masterHostname = key.substring(key.indexOf("(") + 1, key.indexOf(")"));

		masterHostnames.add(masterHostname);
		masterHostnamesToJobNames.put(masterHostname, value);

		Map childJobNamesMap = new HashMap();
		List parentJobNames = new ArrayList();
		Set topLevelJobNames = new TreeSet();

		String currentParentJobName1 = null;
		String currentParentJobName2 = null;

						String patternToFind =
							"hudson\\.plugins\\.parameterizedtrigger\\" +
							".(TriggerBuilder|BuildTrigger)(.*?)/hudson\\.plugins\\" +
							".parameterizedtrigger\\.(TriggerBuilder|BuildTrigger)";

			if (x != -1) {
				int y = jobName.indexOf("_");

				if (y == -1) {
					y = jobName.indexOf(")");
				}

				String jobBranchName = jobName.substring(x + 1, y);

				project.setProperty("job.portal.branch.name(" + jobName + ")", jobBranchName);

							int z = triggerBuilder.indexOf("<projects>");
							int y = triggerBuilder.indexOf("</projects>", z + 1);

							String triggerBuilderChildJobName = triggerBuilder.substring(
								z + 10, y);

				project.setProperty("job.variation.name(" + jobName + ")", jobVariationName);
			}
			else {
				project.setProperty("job.variation.name(" + jobName + ")", "");
			}

			String jobShortName = jobName;

			int x = jobShortName.indexOf("(");

			if (x != -1) {
				jobShortName = jobShortName.substring(0, x);
			}

			project.setProperty("job.short.name(" + jobName + ")", jobShortName);

			String jobContent = FileUtils.readFileToString(new File("template/jobs/" + jobShortName + "/config.xml"));

			if (jobContent.contains("Build Flow") || jobContent.contains("com.cloudbees.plugins.flow.BuildFlow")) {
				project.setProperty("job.content.type(" + jobName + ")", "BuildFlow");

				childJobNamesMap.put(jobName, jobName);
				parentJobNames.add(jobName);
			}
			else if ((jobContent.contains("hudson.plugins.parameterizedtrigger.BlockableBuildTriggerConfig") || jobContent.contains("hudson.plugins.parameterizedtrigger.BuildTriggerConfig")) && (jobName.contains("fixpack") || jobName.contains("maintenance-tags"))) {
				project.setProperty("job.content.type(" + jobName + ")", "BuildTrigger");

				parentJobNames.add(jobName);

				Set triggerBuilderChildJobNames = new TreeSet();

				triggerBuilderChildJobNames.add(jobName);

				Pattern triggerBuilderPattern = Pattern.compile("hudson\\.plugins\\.parameterizedtrigger\\.(TriggerBuilder|BuildTrigger)(.*?)/hudson\\.plugins\\.parameterizedtrigger\\.(TriggerBuilder|BuildTrigger)", Pattern.DOTALL);

				Matcher triggerBuilderMatcher = triggerBuilderPattern.matcher(jobContent);

				while (triggerBuilderMatcher.find()) {
					String triggerBuilder = triggerBuilderMatcher.group(2);

					int x = triggerBuilder.indexOf("<projects>");
					int y = triggerBuilder.indexOf("</projects>", x + 1);

					String triggerBuilderChildJobName = triggerBuilder.substring(x + 10, y);

					if (!triggerBuilderChildJobName.equals("@!child.job.names!@")) {
						triggerBuilderChildJobName = triggerBuilderChildJobName.replace("@!job.variation.name!@", project.getProperty("job.variation.name(" + jobName + ")"));

						triggerBuilderChildJobNames.add(triggerBuilderChildJobName);
					}
				}

					if (currentParentJobName != null) {
						Set childJobNames = new TreeSet();

						String childJobNamesString = (String)childJobNamesMap.get(
							currentParentJobName);

			if (jobContent.contains("Top Level")) {
				topLevelJobNames.add(jobName);
			}

						if (jobName.contains("[component.name]")) {
							String componentNames = (String)properties.get(
							"job.component.names(" + jobName + ")");

			String currentParentJobName = null;

			if (currentParentJobName2 != null) {
				String currentParentJobShortName2 = project.getProperty("job.short.name(" + currentParentJobName2 + ")");
				String currentParentJobVariationName2 = project.getProperty("job.variation.name(" + currentParentJobName2 + ")");
				boolean memberOfCurrentParentJob = false;

				if (jobName.startsWith(currentParentJobShortName2)) {
					if (jobName.endsWith("(" + currentParentJobVariationName2 + ")") || currentParentJobVariationName2.equals("")) {
						memberOfCurrentParentJob = true;
					}
				}

				for (String parentJobName : parentJobNames) {
					String childJobNames = (String)childJobNamesMap.get(parentJobName);

			if ((currentParentJobName == null) && (currentParentJobName1 != null)) {
				String currentParentJobShortName1 = project.getProperty("job.short.name(" + currentParentJobName1 + ")");
				String currentParentJobVariationName1 = project.getProperty("job.variation.name(" + currentParentJobName1 + ")");
				boolean memberOfCurrentParentJob = false;

						throw new Exception();
					}

					project.setProperty(
						"child.job.names(" + parentJobName + ")",
						(String)childJobNamesMap.get(parentJobName));
				}

				StringBuilder sb = new StringBuilder();

				for (String topLevelJobName : topLevelJobNames) {
					String excludeTopLevelView = (String)properties.get(
						"job.property(" + topLevelJobName + "/exclude.top.level.view)");

					if ((excludeTopLevelView == null) ||
						!excludeTopLevelView.equals("true")) {

						sb.append("<string>");
						sb.append(topLevelJobName);
						sb.append("</string>");
					}
				}
			}

			if (currentParentJobName != null) {
				Set childJobNames = new TreeSet();

				String childJobNamesString = childJobNamesMap.get(currentParentJobName);

				for (String childJobName : childJobNamesString.split(",")) {
					childJobNames.add(childJobName);
				}

				if (jobName.contains("[component.name]")) {
					String componentNames = properties.get("job.component.names(" + jobName + ")");

					if (componentNames == null) {
						componentNames = "portal-acceptance";
					}

					for (String componentName : componentNames.split(",")) {
						childJobNames.add(jobName.replace("[component.name]", "[" + componentName + "]"));
					}
				}
				else {
					childJobNames.add(jobName);
				}

				childJobNamesMap.put(currentParentJobName, StringUtils.join(childJobNames, ','));
			}
		}

		for (String parentJobName : parentJobNames) {
			String childJobNames = childJobNamesMap.get(parentJobName);

			if (!childJobNames.contains(",")) {
				System.out.println("ERROR: Parent job is missing children jobs: " + parentJobName + "\n");

				throw Exception();
			}

			project.setProperty("child.job.names(" + parentJobName + ")", childJobNamesMap.get(parentJobName));
		}

		StringBuilder sb = new StringBuilder();

		for (String topLevelJobName : topLevelJobNames) {
			String excludeTopLevelView = properties.get("job.property(" + topLevelJobName + "/exclude.top.level.view)");

			if ((excludeTopLevelView == null) || !excludeTopLevelView.equals("true")) {
				sb.append("<string>");
				sb.append(topLevelJobName);
				sb.append("</string>");
			}
		}

		project.setProperty("master.top.level.jobs.xml(" + masterHostname + ")", sb.toString());

		String masterPrimaryView = project.getProperty("master.primary.view(" + masterHostname + ")");

		if (masterPrimaryView == null) {
			if (topLevelJobNames.isEmpty()) {
				project.setProperty("master.primary.view(" + masterHostname + ")", "All");
			}
			else {
				project.setProperty("master.primary.view(" + masterHostname + ")", "Top Level");
			}
		}

							x = shortChildJobName.indexOf("(");

		for (String parentJobName : parentJobNames) {
			if (sb.length() != 0) {
				sb.append("\t\t");
			}

			sb.append("<listView>\n");
			sb.append("\t\t\t<owner class=\"hudson\" reference=\"../../..\" />\n");
			sb.append("\t\t\t<name>" + parentJobName + "</name>\n");
			sb.append("\t\t\t<filterExecutors>true</filterExecutors>\n");
			sb.append("\t\t\t<filterQueue>true</filterQueue>\n");
			sb.append("\t\t\t<properties class=\"hudson.model.View$PropertyList\" />\n");
			sb.append("\t\t\t<jobNames>\n");
			sb.append("\t\t\t\t<comparator class=\"hudson.util.CaseInsensitiveComparator\" />\n");
			sb.append("\t\t\t\t");

			String childJobNames = project.getProperty("child.job.names(" + parentJobName + ")");

			String[] childJobNamesArray = childJobNames.split(",");

			Arrays.sort(childJobNamesArray);

						String componentNamesIgnore = (String)properties.get(
							"job.component.names.ignore(" + jobName + ")");

			sb.append("\n\t\t\t</jobNames>\n");
			sb.append("\t\t\t<jobFilters />\n");
			sb.append("\t\t\t<columns>\n");
			sb.append("\t\t\t\t<hudson.views.StatusColumn />\n");
			sb.append("\t\t\t\t<hudson.views.WeatherColumn />\n");
			sb.append("\t\t\t\t<hudson.views.JobColumn />\n");
			sb.append("\t\t\t\t<jenkins.plugins.extracolumns.DescriptionColumn plugin=\"extra-columns@1.11\">\n");
			sb.append("\t\t\t\t\t<displayName>false</displayName>\n");
			sb.append("\t\t\t\t\t<trim>false</trim>\n");
			sb.append("\t\t\t\t\t<displayLength>1</displayLength>\n");
			sb.append("\t\t\t\t</jenkins.plugins.extracolumns.DescriptionColumn>\n");
			sb.append("\t\t\t\t<hudson.views.LastSuccessColumn />\n");
			sb.append("\t\t\t\t<hudson.views.LastFailureColumn />\n");
			sb.append("\t\t\t\t<hudson.views.LastDurationColumn />\n");
			sb.append("\t\t\t\t<hudson.views.BuildButtonColumn />\n");
			sb.append("\t\t\t</columns>\n");
			sb.append("\t\t\t<recurse>false</recurse>\n");
			sb.append("\t\t</listView>\n");
		}

		if (sb.length() != 0) {
			sb.deleteCharAt(sb.length() - 1);
		}

		project.setProperty("master.list.views.xml(" + masterHostname + ")", sb.toString());

		for (String parentJobName : parentJobNames) {
			sb = new StringBuilder();

			String childJobNames = project.getProperty("child.job.names(" + parentJobName + ")");
			List childJobNamesList = new ArrayList();

			for (String childJobName : childJobNames.split(",")) {
				if (childJobName.equals(parentJobName)) {
					continue;
				}

						String customJobParameters = (String)properties.get(
							"job.property(" + jobName + "/custom.job.parameters)");

					continue;
				}

				String jobContentType = project.getProperty("job.content.type(" + parentJobName + ")");

				if (jobContentType.equals("BuildTrigger")) {
					String shortParentJobName = project.getProperty("job.short.name(" + parentJobName + ")");

					String shortChildJobName = childJobName;

					int x = shortChildJobName.indexOf("[");

					if (x != -1) {
						shortChildJobName = shortChildJobName.substring(0, x);
					}

					int x = shortChildJobName.indexOf("(");

					if (x != -1) {
						shortChildJobName = shortChildJobName.substring(0, x);
					}

					if (!shortChildJobName.contains(shortParentJobName)) {
						continue;
					}
				}

				boolean ignoreChildJobName = false;

				String jobName = childJobName;

				if (jobName.matches(".*[^\\[]+\\[[a-z\\-]+\\].*")) {
					jobName = jobName.replaceAll("\\[[a-z\\-]+\\]", "[component.name]");
				}

				String componentNamesIgnore = properties.get("job.component.names.ignore(" + jobName + ")");

				Map masterJobProperties = (Map)jobNamesToMasterJobProperties.get(jobName);

							break;
						}
					}
				}

				sb.append("\n");
				sb.append("\t{\n");

				if (ignoreChildJobName) {
					sb.append("\t\tignore(ABORTED) {\n\t");
				}

				sb.append("\t\tJOB_INVOCATION_");
				sb.append(childJobNamesList.size());
				sb.append(" = build(\"");
				sb.append(childJobName);

				String customJobParameters = properties.get("job.property(" + jobName + "/custom.job.parameters)");

				Map masterProperties = (Map)masterHostnamesToMasterProperties.get(
					masterHostname);

					project.setProperty("custom.job.parameters(" + parentJobName + ")", customJobParameters);
				}
				else {
					sb.append("\", @!job.parameters!@)\n");
				}

				if (ignoreChildJobName) {
					sb.append("\t\t}\n");
				}

				sb.append("\t},");

				childJobNamesList.add(childJobName);
			}

			if (sb.length() != 0) {
				sb.deleteCharAt(sb.length() - 1);
			}

			project.setProperty("build.flow.downstream.jobs.xml(" + parentJobName + ")", sb.toString());
			project.setProperty("child.job.names(" + parentJobName + ")", StringUtils.join(childJobNamesList, ","));

			sb = new StringBuilder();

			for (int i = 0; i < childJobNamesList.size(); i++) {
				sb.append("JOB_INVOCATION_");
				sb.append(i);
				sb.append(" = build\n");
			}

			project.setProperty("job.invocation.instantiate.list(" + parentJobName + ")", sb.toString());

						if (environmentSlavesMap.containsKey(slaveHostname)) {
							String environmentSlaveKey = (String)environmentSlavesMap.get(
								slaveHostname);

			for (int i = 0; i < childJobNamesList.size(); i++) {
				sb.append("JOB_INVOCATIONS += JOB_INVOCATION_");
				sb.append(i);
				sb.append("\n");
			}

			project.setProperty("job.invocation.make.list(" + parentJobName + ")", sb.toString());
		}
	}
	else if (key.startsWith("master.job.property(")) {
		int x = key.indexOf("/");
		int y = key.indexOf("/", x + 1);

		String jobName = key.substring(x + 1, y);
		String masterHostname = key.substring(key.indexOf("(") + 1, x);
		String propertyName = key.substring(y + 1, key.lastIndexOf(")"));

		Map masterJobProperties = jobNamesToMasterJobProperties.get(jobName);

		if (masterJobProperties == null) {
			masterJobProperties = new TreeMap();

			jobNamesToMasterJobProperties.put(jobName, masterJobProperties);
		}

		masterJobProperties.put(propertyName, value);

		project.setProperty("master.job.properties(" + masterHostname + "/" + jobName + ")", StringUtils.join(masterJobProperties.keySet(), ","));
	}
	else if (key.startsWith("master.property(")) {
		String masterHostname = key.substring(key.indexOf("(") + 1, key.indexOf("/"));
		String propertyName = key.substring(key.indexOf("/") + 1, key.lastIndexOf(")"));

		Map masterProperties = masterHostnamesToMasterProperties.get(masterHostname);

					String slaveConfigXMLContent = getSlaveConfigXMLContent(
						slaveHostname, properties);

			masterHostnamesToMasterProperties.put(masterHostname, masterProperties);
		}

		masterProperties.put(propertyName, value);

		project.setProperty("master.properties(" + masterHostname + ")", StringUtils.join(masterProperties.keySet(), ","));
	}
	else if (key.startsWith("master.slaves(")) {
		String masterHostname = key.substring(key.indexOf("(") + 1, key.indexOf(")"));

		masterHostnames.add(masterHostname);

		if (value.contains("..")) {
			StringBuilder sb = new StringBuilder();

			value = JenkinsResultsParserUtil.expandSlaveRange(value);

			for (String slaveHostname : value.split(",")) {
				sb.append(slaveHostname);
				sb.append("(");

				String environmentSlaveOSType = "";
				if (environmentSlavesMap.containsKey(slaveHostname)) {
					String environmentSlaveKey = environmentSlavesMap.get(slaveHostname);

					environmentSlaveOSType = environmentSlaveKey.substring(0 , environmentSlaveKey.indexOf("."));
				}

				if (environmentSlaveOSType.contains("osx")) {
					environmentSlaveOSType = "osx";
				}
				else if(environmentSlaveOSType.contains("solaris")) {
					environmentSlaveOSType = "solaris";
				}
				else if (environmentSlaveOSType.contains("win")) {
					environmentSlaveOSType = "windows";
				}
				else {
					environmentSlaveOSType = "linux";
				}

				sb.append(environmentSlaveOSType);

				sb.append("),");
			}

				Map jobProperties = (Map)jobNamesToJobProperties.get(jobName);

		project.setProperty("master.slaves.txt(" + masterHostname + ")", value.replaceAll(",", "\n").trim());

		StringBuilder sb = new StringBuilder();

		sb.append("<slaves>\n");

		for (String slaveHostname : value.split(",")) {
			if (slaveHostname.contains("(")) {
				slaveHostname = slaveHostname.substring(0, slaveHostname.indexOf("("));
			}

			String slaveConfigXMLContent = getSlaveConfigXMLContent(slaveHostname);

			project.setProperty(slaveHostname + ".config.xml.content", slaveConfigXMLContent);

			sb.append(slaveConfigXMLContent);
			sb.append("\n");

			slaveHostnames.add(slaveHostname);

			project.setProperty("slave.master(" + slaveHostname + ")", masterHostname);
		}

		sb.append("\t</slaves>");

		project.setProperty("master.slaves.xml(" + masterHostname + ")", sb.toString());

		sb = new StringBuilder();

		for (String slaveHostname : value.split(",")) {
			if (slaveHostname.contains("(")) {
				slaveHostname = slaveHostname.substring(0, slaveHostname.indexOf("("));
			}

			sb.append("<string>");
			sb.append(slaveHostname);
			sb.append("</string>");
		}

		project.setProperty("master.axes.label.slaves.xml(" + masterHostname + ")", sb.toString());
	}
	else if (key.startsWith("job.property(")) {
		String jobName = key.substring(key.indexOf("(") + 1, key.indexOf("/"));
		String propertyName = key.substring(key.indexOf("/") + 1, key.lastIndexOf(")"));

		Map jobProperties = jobNamesToJobProperties.get(jobName);

		if (jobProperties == null) {
			jobProperties = new TreeMap();

			jobNamesToJobProperties.put(jobName, jobProperties);
		}

		jobProperties.put(propertyName, value);

		project.setProperty("job.properties(" + jobName + ")", StringUtils.join(jobProperties.keySet(), ","));
	}
}

project.setProperty("master.hostnames", StringUtils.join(masterHostnames, ","));
project.setProperty("slave.hostnames", StringUtils.join(slaveHostnames, ","));

int i = 0;

for (String masterHostname : masterHostnames) {
	if (masterHostname.startsWith("test-")){
		project.setProperty("trigger.day." + masterHostname, Integer.toString(i % 7));
		project.setProperty("trigger.hour." + masterHostname, Integer.toString(i % 24));

		i++;
	}
}