package com.liferay.jenkins.results.parser;

public class Result implements Comparable{
	public int compareTo(Object o) {
		return -1 * Float.compare(getDuration(), o.getDuration());
	}

	public String getAxis() {
		return _axis;
	}

	public String getBatch() {
		return _batch;
	}

	public String getClassName() {
		return _class;
	}

	public float getDuration() {
		return _duration;
	}

	public String getName() {
		return _name;
	}

	public String getStatus() {
		return _status;
	}

	public String getUrl() {
		return _url;
	}

	public String toString() {
		return toJSONObject().toString(4);
	}

	public Result(JSONObject caseJSONObject, JSONObject childJSONObject) {
		_batch = project.getProperty("jenkins.build.name");

		extractAxisInfo(childJSONObject);
		extractCaseInfo(caseJSONObject);
		extractURLInfo(childJSONObject);
	}

	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();

		obj.put("axis", _axis);
		obj.put("batch", _batch);
		obj.put("className", _class);
		obj.put("duration", _duration);
		obj.put("name", _name);
		obj.put("status", _status);

		return obj;
	}

	private void extractAxisInfo(JSONObject childJSONObject) {
		String url = childJSONObject.getString("url");

		url = URLDecoder.decode(url);

		int startIdx = url.indexOf("AXIS_VARIABLE");

		url = url.substring(startIdx);

		int endIdx = url.indexOf(",");

		_axis = url.substring(0, endIdx);
	}

	private void extractCaseInfo(JSONObject caseObject) {
		_class = caseObject.getString("className");
		_duration = caseObject.getLong("duration");
		_name = caseObject.getString("name");
		_status = caseObject.getString("status");
	}

	private void extractURLInfo(JSONObject childJSONObject) {
		String url = childJSONObject.getString("url");

		StringBuilder sb = new StringBuilder(url);

		sb.append("testReport/");

		int x = _class.lastIndexOf(".");

		String packageName = _class.substring(0, x);

		String className = _class.substring(x + 1);

		sb.append(packageName);

		sb.append("/");

		sb.append(className);

		sb.append("/");

		if (packageName.contains("poshi")) {
			String poshiName = _name;

			poshiName = poshiName.replaceAll("\\[", "_");
			poshiName = poshiName.replaceAll("\\]", "_");
			poshiName = poshiName.replaceAll("#", "_");

			sb.append(poshiName);
			sb.append("/");
		}
		else {
			sb.append(_name);
		}

		URI uri = new URI(sb.toString());

		_url = uri.toASCIIString();
	}

	private String _axis;
	private String _batch;
	private String _class;
	private float _duration;
	private String _name;
	private String _status;
	private String _url;
}

}
