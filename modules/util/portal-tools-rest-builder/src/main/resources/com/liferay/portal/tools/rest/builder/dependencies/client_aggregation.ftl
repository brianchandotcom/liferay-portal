package ${configYAML.apiPackagePath}.client.aggregation;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

/**
 * @author ${configYAML.author}
 * @generated
 */
@Generated("")
public class Aggregation {

	public Map<String, String> getTerms() {
		return _terms;
	}

	public void setTerms(Map<String, String> terms) {
		_terms = terms;
	}

	private Map<String, String> _terms = new HashMap<>();

}