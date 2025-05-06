package ${configYAML.apiPackagePath}.client.aggregation;

<#assign javaEePrefix = freeMarkerTool.getJavaEePrefix(configYAML) />

import ${javaEePrefix}.annotation.Generated;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ${configYAML.author}
 * @generated
 */
@Generated("")
public class Aggregation {

	public Map<String, String> getAggregationTerms() {
		return _aggregationTerms;
	}

	public void setAggregationTerms(Map<String, String> aggregationTerms) {
		_aggregationTerms = aggregationTerms;
	}

	private Map<String, String> _aggregationTerms = new HashMap<>();

}