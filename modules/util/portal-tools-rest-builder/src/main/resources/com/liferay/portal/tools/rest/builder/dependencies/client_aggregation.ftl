package ${configYAML.apiPackagePath}.client.aggregation;

<#if !freeMarkerTool.isVersionCompatible(configYAML, 10) || (useJavax?string == "true")>
	import javax.annotation.Generated;

<#else>
	import jakarta.annotation.Generated;
</#if>

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