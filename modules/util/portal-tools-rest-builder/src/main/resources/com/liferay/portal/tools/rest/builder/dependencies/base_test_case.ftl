package ${configYAML.apiPackagePath}.test.${versionDirName};

<#compress>
	<#list openAPIYAML.components.schemas?keys as schemaName>
		import ${configYAML.apiPackagePath}.dto.${versionDirName}.${schemaName};
	</#list>
</#compress>

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.util.StringUtil;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Date;

import javax.annotation.Generated;

import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsNull;

import org.jboss.arquillian.test.api.ArquillianResource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.co.datumedge.hamcrest.json.SameJSONAs;

/**
 * @author ${configYAML.author}
 * @generated
 */
@Generated("")
public abstract class Base${schemaName}TestCase {

	@BeforeClass
	public static void setUpClass() {
		_inputObjectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

		_outputObjectMapper.addMixIn(${schemaName}.class, IgnoreFieldsMixin.class);

		RestAssured.defaultParser = Parser.JSON;
	}

	@Before
	public void setUp() throws MalformedURLException {
		_baseURL = new URL(
			_url.toExternalForm() +
				"/o${configYAML.application.baseURI}/${openAPIYAML.info.version}");

		_group = _addGroup();
	}

	@After
	public void tearDown() throws MalformedURLException {
		_deleteGroup(_group.getGroupId());
	}

	protected void assertThat(
			${schemaName} actual${schemaName}, ${schemaName} expected${schemaName})
		throws JsonProcessingException {

		SameJSONAs<? super String> sameJSONAs = SameJSONAs.sameJSONAs(
			_outputObjectMapper.writeValueAsString(expected${schemaName})
		).allowingExtraUnexpectedFields();

		MatcherAssert.assertThat(
			_outputObjectMapper.writeValueAsString(actual${schemaName}), sameJSONAs);
	}

	protected abstract Response create${schemaName}(${schemaName} ${schemaVarName})
		throws JsonProcessingException, MalformedURLException;

	protected Response create${schemaName}(
			String path, ${schemaName} ${schemaVarName})
		throws JsonProcessingException, MalformedURLException {

		return RestAssured.given(
		).auth(
		).preemptive(
		).basic(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/json"
		).header(
			"Content-Type", "application/json"
		).body(
			_inputObjectMapper.writeValueAsString(${schemaVarName})
		).when(
		).post(
			new URL(_baseURL.toExternalForm() + path)
		).then(
		).statusCode(
			200
		).body(
			"dateCreated", IsNull.notNullValue()
		).body(
			"dateModified", IsNull.notNullValue()
		).body(
			"id", IsNull.notNullValue()
		).extract(
		).response();
	}

	protected Response delete${schemaName}(String path)
		throws MalformedURLException {

		return RestAssured.given(
		).auth(
		).preemptive(
		).basic(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/json"
		).header(
			"Content-Type", "application/json"
		).when(
		).delete(
			new URL(_baseURL.toExternalForm() + path)
		).then(
		).statusCode(
			204
		).extract(
		).response();
	}

	protected Response get${schemaName}(String path) throws MalformedURLException {
		return RestAssured.given(
		).auth(
		).preemptive(
		).basic(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/json"
		).when(
		).get(
			new URL(_baseURL.toExternalForm() + path)
		).then(
		).extract(
		).response();
	}

	protected Group getGroup() {
		return _group;
	}

	protected long getGroupId() {
		return _group.getGroupId();
	}

	protected abstract ${schemaName} new${schemaName}();

	protected Response update${schemaName}(
			String path, ${schemaName} ${schemaVarName})
		throws JsonProcessingException, MalformedURLException {

		return RestAssured.given(
		).auth(
		).preemptive(
		).basic(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/json"
		).header(
			"Content-Type", "application/json"
		).body(
			_inputObjectMapper.writeValueAsString(${schemaVarName})
		).when(
		).put(
			new URL(_baseURL.toExternalForm() + path)
		).then(
		).statusCode(
			200
		).body(
			"dateCreated", IsNull.notNullValue()
		).body(
			"dateModified", IsNull.notNullValue()
		).body(
			"id", IsNull.notNullValue()
		).extract(
		).response();
	}

	private Group _addGroup() throws MalformedURLException {
		return RestAssured.given(
		).auth(
		).preemptive(
		).basic(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/json"
		).when(
		).param(
			"virtualHost", "localhost"
		).param(
			"parentGroupId", 0
		).param(
			"liveGroupId", 0
		).param(
			"name", StringUtil.randomString(10)
		).param(
			"description", ""
		).param(
			"type", 1
		).param(
			"manualMembership", true
		).param(
			"membershipRestriction", 0
		).param(
			"friendlyURL", "/" + StringUtil.randomString(10)
		).param(
			"site", true
		).param(
			"active", true
		).get(
			new URL(_url, "/api/jsonws/group/add-group")
		).then(
		).statusCode(
			200
		).extract(
		).response(
		).as(
			Group.class
		);
	}

	private void _deleteGroup(long groupId) throws MalformedURLException {
		RestAssured.given(
		).auth(
		).preemptive(
		).basic(
			"test@liferay.com", "test"
		).header(
			"Accept", "application/json"
		).when(
		).param(
			"groupId", groupId
		).param(
			"active", true
		).get(
			new URL(_url, "/api/jsonws/group/delete-group")
		).then(
		).statusCode(
			200
		);
	}

	private static final ObjectMapper _inputObjectMapper = new ObjectMapper();
	private static final ObjectMapper _outputObjectMapper = new ObjectMapper();

	private URL _baseURL;
	private Group _group;

	@ArquillianResource
	private URL _url;

	private abstract class IgnoreFieldsMixin {

		@JsonIgnore
		public abstract Date getDateCreated();

		@JsonIgnore
		public abstract Date getDateModified();

		@JsonIgnore
		public abstract Long getId();

	}

}